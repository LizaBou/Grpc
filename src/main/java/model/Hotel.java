package model;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private final IntegerProperty id;  // Identifiant de l'hôtel
    private final StringProperty name;
    private final StringProperty address;
    private final IntegerProperty stars;
    private List<Room> rooms;

    // Constructeur avec une liste de chambres
    public Hotel(String name, String address, int stars, List<Room> rooms) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.stars = new SimpleIntegerProperty(stars);
        this.rooms = new ArrayList<>(rooms); // Copie pour éviter les modifications externes
        this.id = new SimpleIntegerProperty(0); // Valeur par défaut
    }

    // Constructeur sans liste de chambres
    public Hotel(String name, String address, int stars) {
        this(name, address, stars, new ArrayList<>());
    }

    // Constructeur avec id pour la base de données
    public Hotel(int id, String name, String address, int stars) {
        this(name, address, stars, new ArrayList<>());
        this.id.set(id);
    }

    // Méthode pour ajouter une chambre
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Accesseurs et mutateurs avec propriétés JavaFX
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public IntegerProperty starsProperty() {
        return stars;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getAddress() {
        return address.get();
    }

    public int getStars() {
        return stars.get();
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
