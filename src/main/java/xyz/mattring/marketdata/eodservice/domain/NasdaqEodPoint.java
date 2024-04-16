package xyz.mattring.marketdata.eodservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "NASDAQ_EOD")
public class NasdaqEodPoint extends EodPoint {
}
