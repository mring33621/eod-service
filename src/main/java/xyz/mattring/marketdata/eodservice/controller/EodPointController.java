package xyz.mattring.marketdata.eodservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.mattring.marketdata.eodservice.domain.Exchange;
import xyz.mattring.marketdata.eodservice.mapper.EodPointMapper;
import xyz.mattring.marketdata.eodservice.record.EodPointRecord;
import xyz.mattring.marketdata.eodservice.repo.NasdaqPointRepo;

import java.util.Arrays;
import java.util.List;

@RestController
public class EodPointController {

    private static final Logger log = LoggerFactory.getLogger(EodPointController.class);

    private final NasdaqPointRepo nasdaqPointRepo;
    private final EodPointMapper eodPointMapper;

    public EodPointController(NasdaqPointRepo nasdaqPointRepo, EodPointMapper eodPointMapper) {
        this.nasdaqPointRepo = nasdaqPointRepo;
        this.eodPointMapper = eodPointMapper;
    }

    @QueryMapping
    public List<EodPointRecord> queryPointsForSymbol(@Argument String symbol) {
        return listPointsForSymbol(symbol);
    }

    @GetMapping(path = "{symbol}/points", produces = "application/json")
    public List<EodPointRecord> listPointsForSymbol(@PathVariable String symbol) {
        return nasdaqPointRepo.findBySymbol(symbol).stream()
                .map(eodPointMapper::toRecord)
                .toList();
    }

    @GetMapping(path = "{exchange}/symbols", produces = "application/json")
    public List<String> symbolsForExchange(@PathVariable String exchange) {
        final String reqExchange = exchange == null ? "nasdaq" : exchange;
        if ("nasdaq".equalsIgnoreCase(reqExchange)) {
            return nasdaqPointRepo.findAllDistinctSymbols();
        } else {
            return List.of();
        }
    }

    @GetMapping(path = "{exchange}/top10symbols", produces = "application/json")
    public List<String> top10SymbolsForExchange(@PathVariable String exchange) {
        final String reqExchange = exchange == null ? "nasdaq" : exchange;
        if ("nasdaq".equalsIgnoreCase(reqExchange)) {
            return nasdaqPointRepo.findTop10SymbolsByRecentVolume();
        } else {
            return List.of();
        }
    }

    @GetMapping(path = "/exchanges", produces = "application/json")
    public List<String> exchanges() {
        return Arrays.stream(Exchange.values())
                .map(Enum::name)
                .toList();
    }

}
