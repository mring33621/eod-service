package xyz.mattring.marketdata.eodservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import xyz.mattring.marketdata.eodservice.mapper.EodPointMapper;
import xyz.mattring.marketdata.eodservice.record.EodPointRecord;
import xyz.mattring.marketdata.eodservice.repo.NasdaqPointRepo;

import java.util.List;

@Controller
public class EodPointController {

    private static final Logger log = LoggerFactory.getLogger(EodPointController.class);

    private final NasdaqPointRepo nasdaqPointRepo;
    private final EodPointMapper eodPointMapper;

    public EodPointController(NasdaqPointRepo nasdaqPointRepo, EodPointMapper eodPointMapper) {
        this.nasdaqPointRepo = nasdaqPointRepo;
        this.eodPointMapper = eodPointMapper;
    }

    @QueryMapping
    public List<EodPointRecord> pointsForSymbol(@Argument String symbol) {
        return nasdaqPointRepo.findBySymbol(symbol).stream()
                .map(eodPointMapper::toRecord)
                .toList();
    }

}