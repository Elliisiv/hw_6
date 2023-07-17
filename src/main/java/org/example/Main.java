package org.example;

import org.example.Client.ClientService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ClientService clientService = new ClientService();
        try {
            long clientId = clientService.create("Test User");
            System.out.println("Created client with ID: " + clientId);
            String clientName = clientService.getById(clientId);
            System.out.println("Client name: " + clientName);

            clientService.setName(clientId, "Test User 1");
            System.out.println("Client name updated.");

            clientService.deleteById(clientId);
            System.out.println("Client deleted.");

            System.out.println("All clients:");
            clientService.listAll().forEach(client -> System.out.println("ID: " + client.getId() + ", Name: " + client.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
