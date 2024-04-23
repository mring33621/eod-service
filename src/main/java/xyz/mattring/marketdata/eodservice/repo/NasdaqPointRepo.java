package xyz.mattring.marketdata.eodservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.mattring.marketdata.eodservice.domain.EodPointId;
import xyz.mattring.marketdata.eodservice.domain.NasdaqEodPoint;

import java.util.List;

@Repository
public interface NasdaqPointRepo extends JpaRepository<NasdaqEodPoint, EodPointId> {

    List<NasdaqEodPoint> findBySymbol(String symbol);

    @Query("SELECT DISTINCT symbol FROM NasdaqEodPoint")
    List<String> findAllDistinctSymbols();

    @Query("SELECT symbol FROM NasdaqEodPoint ORDER BY date DESC, volume DESC LIMIT 10")
    List<String> findTop10SymbolsByRecentVolume();
}
