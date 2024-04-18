package xyz.mattring.marketdata.eodservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;
import java.util.StringJoiner;

@MappedSuperclass
@IdClass(EodPointId.class)
public class EodPoint {

    @Id
    @Column(name = "DATE", nullable = false)
    protected Integer date;

    @Id
    @Column(name = "SYMBOL", nullable = false, length = 5)
    protected String symbol;

    @Column(name = "OPEN", precision = 10, scale = 2)
    protected BigDecimal open;

    @Column(name = "HIGH", precision = 10, scale = 2)
    protected BigDecimal high;

    @Column(name = "LOW", precision = 10, scale = 2)
    protected BigDecimal low;

    @Column(name = "CLOSE", precision = 10, scale = 2)
    protected BigDecimal close;

    @Column(name = "VOLUME", precision = 15, scale = 2)
    protected BigDecimal volume;

    public String getIdAsString() {
        return date + "_" + symbol;
    }

    public void setIdAsString(String idString) {
        String[] parts = idString.split("_");
        setDate(Integer.parseInt(parts[0]));
        setSymbol(parts[1]);
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getExchange() {
        final String exch = this.getClass().getSimpleName().replace("EodPoint", "");
        return exch.isEmpty() ? null : exch;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EodPoint.class.getSimpleName() + "[", "]")
                .add("date=" + getDate())
                .add("symbol='" + getSymbol() + "'")
                .add("open=" + getOpen())
                .add("high=" + getHigh())
                .add("low=" + getLow())
                .add("close=" + getClose())
                .add("volume=" + getVolume())
                .toString();
    }
}