package model;

import hotel.HotelServiceGrpc;
import hotel.HotelServiceOuterClass;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class HotelGrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new HotelServiceImpl())
                .build();

        System.out.println("Server started on port 8080");
        server.start();
        server.awaitTermination();
    }

    static class HotelServiceImpl extends HotelServiceGrpc.HotelServiceImplBase {
        @Override
        public void checkAvailability(HotelServiceOuterClass.AvailabilityRequest request, StreamObserver<HotelServiceOuterClass.AvailabilityResponse> responseObserver) {
            // Ajout de plusieurs offres avec des URL d'image
            HotelServiceOuterClass.Offer offer1 = HotelServiceOuterClass.Offer.newBuilder()
                    .setOfferId("1")
                    .setNumBeds(2)
                    .setDate("2024-12-20")
                    .setPrice(100)
                    .setImageUrl("https://www.google.com/imgres?q=images%20chambre%20de%20luxe&imgurl=https%3A%2F%2Fimg-3.journaldesfemmes.fr%2FIrSleL1PB7xYFmRC6puNhB2Uzow%3D%2F1080x%2Fsmart%2Fc060716785454fcc8215a9ac45d33733%2Fccmcms-jdf%2F37165266.jpg&imgrefurl=https%3A%2F%2Fdeco.journaldesfemmes.fr%2Fguide-amenagement-et-travaux%2F2812229-chambre-de-luxe-idees-deco-modernes-inspirees-des-hotels%2F&docid=0yiPDUVBkN-IuM&tbnid=vn79WA_eOzV2zM&vet=12ahUKEwjsvJSh_46KAxU5SaQEHTciHpEQM3oECBoQAA..i&w=1080&h=810&hcb=2&ved=2ahUKEwjsvJSh_46KAxU5SaQEHTciHpEQM3oECBoQAA")  // URL de l'image de l'offre 1
                    .build();

            HotelServiceOuterClass.Offer offer2 = HotelServiceOuterClass.Offer.newBuilder()
                    .setOfferId("2")
                    .setNumBeds(3)
                    .setDate("2024-12-21")
                    .setPrice(150)
                    .setImageUrl("http://example.com/images/offer2.jpg")  // URL de l'image de l'offre 2
                    .build();

            HotelServiceOuterClass.Offer offer3 = HotelServiceOuterClass.Offer.newBuilder()
                    .setOfferId("3")
                    .setNumBeds(1)
                    .setDate("2024-12-22")
                    .setPrice(80)
                    .setImageUrl("http://example.com/images/offer3.jpg")  // URL de l'image de l'offre 3
                    .build();

            // Ajout des offres dans la réponse
            HotelServiceOuterClass.AvailabilityResponse response = HotelServiceOuterClass.AvailabilityResponse.newBuilder()
                    .addOffers(offer1)
                    .addOffers(offer2)
                    .addOffers(offer3)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void makeReservation(HotelServiceOuterClass.ReservationRequest request, StreamObserver<HotelServiceOuterClass.ReservationResponse> responseObserver) {
            HotelServiceOuterClass.ReservationResponse response = HotelServiceOuterClass.ReservationResponse.newBuilder()
                    .setConfirmation("Reservation Confirmed")
                    .setReservationReference("RES12345")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
