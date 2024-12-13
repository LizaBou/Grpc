package model;



public class Reservation {
    private String customerName;
    private String customerCreditCard;
    private final Hotel hotel;
    private Room room;

    public Reservation(String customerName, String customerCreditCard, Hotel hotel, Room room) {
        this.customerName = customerName;
        this.customerCreditCard = customerCreditCard;
        this.hotel = hotel;
        this.room = room;
    }


    public Reservation(Hotel hotel1, Room selectedRoom, String johnDoe, String s, Hotel hotel) {
        this.hotel = hotel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerCreditCard() {
        return customerCreditCard;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room getRoom() {
        return room;
    }
}
