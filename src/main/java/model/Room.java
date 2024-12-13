package model;

public class Room {
    private int id;  // Identifiant unique pour la chambre
    private int hotelId;  // L'identifiant de l'hôtel auquel cette chambre appartient
    private int numBeds;
    private double price;
    private boolean reserved; // Marque si la chambre est réservée

    // Constructeur de base
    public Room(int numBeds, double price) {
        this.numBeds = numBeds;
        this.price = price;
        this.reserved = false; // Par défaut, la chambre n'est pas réservée
    }

    // Constructeur avec tous les champs, utile pour la base de données
    public Room(int id, int hotelId, int numBeds, double price, boolean isReserved) {
        this.id = id;
        this.hotelId = hotelId;
        this.numBeds = numBeds;
        this.price = price;
        this.reserved = isReserved;
    }

    // Accesseurs et mutateurs
    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getNumberOfBeds() {
        return numBeds;
    }

    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotelId;
    }
}
