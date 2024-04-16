package xyz.mattring.marketdata.eodservice.record;

public record EodPointRecord(
        String id,
        int date,
        String symbol,
        double open,
        double high,
        double low,
        double close,
        double volume,
        String exchange) {
}
