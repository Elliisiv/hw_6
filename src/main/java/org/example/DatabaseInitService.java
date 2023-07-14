package org.example;

import org.flywaydb.core.Flyway;
public class DatabaseInitService {
    public void initDb(String connectionUrl, String username, String password) {

    Flyway flyway = Flyway
            .configure()
            .dataSource(connectionUrl, username, password)
            .baselineOnMigrate(true)
            .load();

        flyway.migrate();
}
}