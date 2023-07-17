package org.example;

import org.flywaydb.core.Flyway;
public class DatabaseInitService {
    public static void main(String[] args) {
        Flyway flyway = Flyway
                .configure()
                .dataSource("jdbc:h2:/Desktop/goit/h2/6",
                        "sa",
                        "")
                .load();
        flyway.migrate();
    }

}