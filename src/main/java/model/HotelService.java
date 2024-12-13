package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    // Méthode de connexion à la base de données SQLite
    private Connection connect() throws SQLException {
        String url = "jdbc:sqlite:hotel_reservation.db"; // Assurez-vous que le chemin de votre base de données est correct
        return DriverManager.getConnection(url);
    }

    // Méthode pour initialiser les hôtels dans la base de données
    void initializeHotels() {
        String createHotelsTable = "CREATE TABLE IF NOT EXISTS hotels (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "address TEXT, " +
                "stars INTEGER)";

        String createRoomsTable = "CREATE TABLE IF NOT EXISTS rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hotel_id INTEGER, " +
                "numBeds INTEGER, " +
                "price REAL, " +
                "is_reserved BOOLEAN, " +
                "FOREIGN KEY (hotel_id) REFERENCES hotels(id))";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createHotelsTable);
            stmt.execute(createRoomsTable);
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    // Méthode pour rechercher des hôtels avec les critères donnés
    public List<Hotel> searchHotels(String city, int minStars, double maxPrice, int beds) {
        List<Hotel> result = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE address LIKE ? AND stars >= ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + city + "%");
            pstmt.setInt(2, minStars);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Hotel hotel = new Hotel(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("stars"));
                // Recherche des chambres disponibles dans cet hôtel
                List<Room> rooms = searchRoomsByHotelId(hotel.getId(), beds, maxPrice);
                if (!rooms.isEmpty()) {
                    hotel.setRooms(rooms);
                    result.add(hotel);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching hotels: " + e.getMessage());
        }

        return result;
    }

    // Méthode pour rechercher des chambres par hôtel et critères
    private List<Room> searchRoomsByHotelId(int hotelId, int beds, double maxPrice) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE hotel_id = ? AND numBeds >= ? AND price <= ? AND is_reserved = FALSE";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hotelId);
            pstmt.setInt(2, beds);
            pstmt.setDouble(3, maxPrice);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Room room = new Room(rs.getInt("id"), rs.getInt("hotel_id"), rs.getInt("numBeds"),
                        rs.getDouble("price"), rs.getBoolean("is_reserved"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error searching rooms: " + e.getMessage());
        }

        return rooms;
    }

    // Méthode pour ajouter un hôtel et des chambres à la base de données (exemple)
    public void addHotel(Hotel hotel) {
        String sqlHotel = "INSERT INTO hotels (name, address, stars) VALUES (?, ?, ?)";
        String sqlRoom = "INSERT INTO rooms (hotel_id, numBeds, price, is_reserved) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect()) {
            // Ajouter l'hôtel
            try (PreparedStatement pstmtHotel = conn.prepareStatement(sqlHotel, Statement.RETURN_GENERATED_KEYS)) {
                pstmtHotel.setString(1, hotel.getName());
                pstmtHotel.setString(2, hotel.getAddress());
                pstmtHotel.setInt(3, hotel.getStars());
                pstmtHotel.executeUpdate();

                // Récupérer l'ID de l'hôtel nouvellement inséré
                ResultSet generatedKeys = pstmtHotel.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int hotelId = generatedKeys.getInt(1);

                    // Ajouter les chambres associées à l'hôtel
                    for (Room room : hotel.getRooms()) {
                        try (PreparedStatement pstmtRoom = conn.prepareStatement(sqlRoom)) {
                            pstmtRoom.setInt(1, hotelId);
                            pstmtRoom.setInt(2, room.getNumBeds());
                            pstmtRoom.setDouble(3, room.getPrice());
                            pstmtRoom.setBoolean(4, room.isReserved());
                            pstmtRoom.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding hotel: " + e.getMessage());
        }
    }

    // Méthode pour réserver une chambre
    public boolean reserveRoom(Hotel selectedHotel, int roomId) {
        String sql = "UPDATE rooms SET is_reserved = TRUE WHERE hotel_id = ? AND id = ? AND is_reserved = FALSE";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selectedHotel.getId());
            pstmt.setInt(2, roomId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;  // Si des lignes ont été mises à jour, la réservation a réussi
        } catch (SQLException e) {
            System.out.println("Error reserving room: " + e.getMessage());
            return false;
        }
    }
}
