package xyz.mattring.marketdata.eodservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;

import java.math.BigDecimal;
import java.util.StringJoiner;

@MappedSuperclass
public class EodPoint {
    @EmbeddedId
    private EodPointId id;

    @Column(name = "OPEN", precision = 10, scale = 2)
    private BigDecimal open;

    @Column(name = "HIGH", precision = 10, scale = 2)
    private BigDecimal high;

    @Column(name = "LOW", precision = 10, scale = 2)
    private BigDecimal low;

    @Column(name = "CLOSE", precision = 10, scale = 2)
    private BigDecimal close;

    @Column(name = "VOLUME", precision = 15, scale = 2)
    private BigDecimal volume;

    public EodPoint() {
        this.id = new EodPointId();
    }

    public EodPointId getId() {
        return id;
    }

    public void setId(EodPointId id) {
        this.id = id;
    }

    public String getIdAsString() {
        return id.toString();
    }

    public void setIdAsString(String idString) {
        String[] parts = idString.split("_");
        id.setDate(Integer.parseInt(parts[0]));
        id.setSymbol(parts[1]);
    }

    public int getDate() {
        return id.getDate();
    }

    public void setDate(int date) {
        id.setDate(date);
    }

    public String getSymbol() {
        return id.getSymbol();
    }

    public void setSymbol(String symbol) {
        id.setSymbol(symbol);
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
                .add("id=" + getId())
                .add("open=" + getOpen())
                .add("high=" + getHigh())
                .add("low=" + getLow())
                .add("close=" + getClose())
                .add("volume=" + getVolume())
                .toString();
    }
}