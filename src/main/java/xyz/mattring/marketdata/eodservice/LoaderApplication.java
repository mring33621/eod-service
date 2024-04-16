package xyz.mattring.marketdata.eodservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.mattring.marketdata.eodservice.domain.NasdaqEodPoint;
import xyz.mattring.marketdata.eodservice.repo.NasdaqPointRepo;

import java.io.BufferedReader;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@SpringBootApplication
public class LoaderApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoaderApplication.class);
    private static final SimpleDateFormat CSV_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
    private static final SimpleDateFormat POINT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    private final NasdaqPointRepo nasdaqPointRepo;

    public LoaderApplication(NasdaqPointRepo nasdaqPointRepo) {
        this.nasdaqPointRepo = nasdaqPointRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(LoaderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File[] loadFiles = listFilesInFolderByLastModifiedAsc("./data/files/inbox/");
        if (loadFiles != null) {
            for (File f : loadFiles) {
                if (f.getName().endsWith(".csv")) {
                    processFile(f);
                }
            }
        }
    }

    static File[] listFilesInFolderByLastModifiedAsc(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        return files;
    }

    void processFile(File file) {
        if (!file.getName().startsWith("NASDAQ")) {
            log.warn("Skipping file: {}", file.getName());
        }
        log.info("Processing file: {}", file.getName());
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            int savedPoints = 0;
            while ((line = br.readLine()) != null) {
                NasdaqEodPoint point = parseNasdaqLine(line);
                if (point != null) {
                    nasdaqPointRepo.save(point);
                    savedPoints++;
                }
            }
            log.info("Saved {} points from file: {}", savedPoints, file.getName());
        } catch (Exception e) {
            log.error("Error processing file: {}", file.getName(), e);
        }
        moveFromInboxToArchive(file);
    }

    void moveFromInboxToArchive(File file) {
        File archiveFolder = new File("./data/files/archive/");
        File archiveFile = new File(archiveFolder, file.getName());
        try {
            Files.move(file.toPath(), archiveFile.toPath());
        } catch (Exception e) {
            log.error("Error moving file to archive: {}", file.getName(), e);
        }
    }

    static NasdaqEodPoint parseNasdaqLine(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }
        if (line.startsWith("Symbol,")) {
            return null;
        }
        String[] parts = line.split(",");
        if (parts.length != 7) {
            return null;
        }
        NasdaqEodPoint point = null;
        try {
            point = new NasdaqEodPoint();
            point.setSymbol(parts[0]);
            Date date = CSV_DATE_FORMAT.parse(parts[1]);
            point.setDate(Integer.parseInt(POINT_DATE_FORMAT.format(date)));
            point.setOpen(new BigDecimal(parts[2]));
            point.setHigh(new BigDecimal(parts[3]));
            point.setLow(new BigDecimal(parts[4]));
            point.setClose(new BigDecimal(parts[5]));
            point.setVolume(new BigDecimal(parts[6]));
        } catch (ParseException e) {
            point = null;
        }
        return point;
    }
}
