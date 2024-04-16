CREATE TABLE IF NOT EXISTS Nasdaq_EOD (
    Date INT,
    Symbol VARCHAR(5),
    Open DECIMAL(10, 2),
    High DECIMAL(10, 2),
    Low DECIMAL(10, 2),
    Close DECIMAL(10, 2),
    Volume DECIMAL(15, 2),
    PRIMARY KEY (Date, Symbol)
);

create user if not exists data_admin password 'H4ppyDay5' admin;

drop user "";