package xyz.mattring.marketdata.eodservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.mattring.marketdata.eodservice.domain.EodPointId;
import xyz.mattring.marketdata.eodservice.domain.NasdaqEodPoint;

import java.util.List;

@Repository
public interface NasdaqPointRepo extends JpaRepository<NasdaqEodPoint, EodPointId> {

    List<NasdaqEodPoint> findBySymbol(String symbol);
}
