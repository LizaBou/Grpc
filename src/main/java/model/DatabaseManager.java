package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:hotel_reservation.db";

    // Méthode pour se connecter à la base de données
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Méthode pour ajouter un hôtel à la base de données
    public void addHotel(String name, String address, int stars) {
        String sql = "INSERT INTO hotels(name, address, stars) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setInt(3, stars);
            pstmt.executeUpdate();
            System.out.println("Hotel ajouté : " + name);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'hôtel : " + e.getMessage());
        }
    }

    // Méthode pour récupérer tous les hôtels de la base de données
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Hotel hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("stars")
                );
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des hôtels : " + e.getMessage());
        }

        return hotels;
    }
}
