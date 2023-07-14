package Client;

import org.example.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Database database;
    private Connection connection;
    private PreparedStatement createSt;
    private PreparedStatement getByNameSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement setNameSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement listAllSt;

    public ClientService(Database database) {
        this.database = database;
        connection = database.getConnection();
        try {
            createSt = connection.prepareStatement("INSERT INTO client(name) VALUES (?)");
            getByNameSt = connection.prepareStatement("SELECT id FROM client WHERE name = ?");
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
        getByNameSt.setString(1, name);

        try (ResultSet rs = getByNameSt.executeQuery()) {
            if (!rs.next()) {
                throw new SQLException("Client creation failed.");
            }
            long id = rs.getLong("id");
            return id;
        }
    }

    public String getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            String name = rs.getString("name");
            return name;
        }
    }

    public void setName(long id, String name) throws SQLException {
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        setNameSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (ResultSet rs = listAllSt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Client client = new Client(id, name);
                clients.add(client);
            }
        }
        return clients;
    }
}
