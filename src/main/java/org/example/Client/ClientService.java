package org.example.Client;

import org.example.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Database database;
    private Connection connection;
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement setNameSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement listAllSt;

    public ClientService() {
        database = Database.getInstance();
        connection = database.getConnection();
        try {
            createSt = connection.prepareStatement("INSERT INTO client(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            getByIdSt = connection.prepareStatement("SELECT name FROM client WHERE id = ?");
            setNameSt = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
            deleteByIdSt = connection.prepareStatement("DELETE FROM client WHERE id = ?");
            listAllSt = connection.prepareStatement("SELECT * FROM client");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public long create(String name) throws SQLException {
        createSt.setString(1, name);
        createSt.executeUpdate();

        try (ResultSet generatedKeys = createSt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating client failed, no ID obtained.");
            }
        }
    }

    public String getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("name");
            } else {
                throw new SQLException("Client not found.");
            }
        }
    }

    public void setName(long id, String name) throws SQLException {
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        int rowsAffected = 0;
        try {
            rowsAffected = setNameSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rowsAffected == 0) {
            throw new SQLException("Updating client failed, no rows affected.");
        }
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        int rowsAffected = 0;
        try {
            rowsAffected = deleteByIdSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rowsAffected == 0) {
            throw new SQLException("Deleting client failed, no rows affected.");
        }
    }

    public List<Client> listAll()  {
        List<Client> clients = new ArrayList<>();
        try (ResultSet rs = listAllSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Client client = new Client(id, name);
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }
}
