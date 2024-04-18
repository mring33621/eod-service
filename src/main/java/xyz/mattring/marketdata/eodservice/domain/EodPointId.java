package xyz.mattring.marketdata.eodservice.domain;

import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class EodPointId implements java.io.Serializable {
    private static final long serialVersionUID = -2097963087367258060L;
    private Integer date;
    private String symbol;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EodPointId entity = (EodPointId) o;
        return Objects.equals(this.date, entity.date) &&
                Objects.equals(this.symbol, entity.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, symbol);
    }

    @Override
    public String toString() {
        return date + "_" + symbol;
    }
}