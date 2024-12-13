package model;

import java.util.Scanner;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import hotel.HotelServiceGrpc;
import hotel.HotelServiceOuterClass;

public class HotelGrpcClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Créer un canal de communication avec le serveur gRPC
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        // Créer le stub pour appeler les services
        HotelServiceGrpc.HotelServiceBlockingStub stub = HotelServiceGrpc.newBlockingStub(channel);

        // Requête pour vérifier la disponibilité des chambres
        HotelServiceOuterClass.AvailabilityRequest request = HotelServiceOuterClass.AvailabilityRequest.newBuilder()
                .setAgencyId("agency1")
                .setPassword("password")
                .setStartDate("2024-12-20")
                .setEndDate("2024-12-25")
                .setNumPersons(2)
                .build();

        // Réponse du serveur contenant les offres disponibles
        HotelServiceOuterClass.AvailabilityResponse response = stub.checkAvailability(request);
        System.out.println("Available Offers: ");

        if (response.getOffersList().isEmpty()) {
            System.out.println("No available offers.");
        } else {
            // Afficher toutes les offres disponibles et demander à l'utilisateur de choisir
            for (int i = 0; i < response.getOffersList().size(); i++) {
                HotelServiceOuterClass.Offer offer = response.getOffersList().get(i);
                System.out.println((i + 1) + ". Offer ID: " + offer.getOfferId() + ", Price: " + offer.getPrice() + ", Date: " + offer.getDate());
            }

            // Demander à l'utilisateur de choisir une offre
            System.out.print("Please choose an offer number to reserve: ");
            int choice = scanner.nextInt();

            if (choice > 0 && choice <= response.getOffersList().size()) {
                HotelServiceOuterClass.Offer selectedOffer = response.getOffersList().get(choice - 1);
                // Appel à la méthode de réservation après avoir choisi une offre
                makeReservation(stub, selectedOffer.getOfferId());
            } else {
                System.out.println("Invalid choice.");
            }
        }

        // Fermeture du canal
        channel.shutdown();
    }

    // Méthode pour effectuer une réservation
    private static void makeReservation(HotelServiceGrpc.HotelServiceBlockingStub stub, String offerId) {
        HotelServiceOuterClass.ReservationRequest reservationRequest = HotelServiceOuterClass.ReservationRequest.newBuilder()
                .setOfferId(offerId)  // Utilisation de l'ID de l'offre sélectionnée
                .setCustomerName("John Doe")
                .setCustomerCreditCard("1234567890123456")
                .build();

        HotelServiceOuterClass.ReservationResponse reservationResponse = stub.makeReservation(reservationRequest);
        System.out.println("Reservation Confirmation: " + reservationResponse.getConfirmation());
    }
}
