package app;

import app.models.Accommodation;
import app.models.Account;
import app.repositories.AccommodationJPARepository;
import app.repositories.AccountJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args)  {

        System.out.println();
        System.out.println("Running CommandLine Startup");

        System.out.println();
        this.createInitialAccounts();

        System.out.println();
        this.createInitialAccommodations();

        System.out.println("Done!");
    }

    @Autowired
    private AccountJPARepository accountsRepo;
    @Autowired
    private AccommodationJPARepository accommodationsRepo;

    private void createInitialAccounts() {

        List<Account> accounts = this.accountsRepo.findAll();
        if (!accounts.isEmpty()) return;

        System.out.println("Configuring some initial accounts in the repository");
        accounts.add(this.accountsRepo.save(new Account("Tobi", "Kok", "tobi@example.com", "tobi")));

        accounts.get(0).setRole("Administrator");
    }

    private void createInitialAccommodations() {

        List<Accommodation> accommodations = this.accommodationsRepo.findAll();
        if (!accommodations.isEmpty()) return;

        System.out.println("Configuring some initial accommodations in the repository");

        List<Accommodation> dummyAccommodations = getDummyAccommodations();
        for (Accommodation accommodation : dummyAccommodations) {
            accommodations.add(this.accommodationsRepo.save(accommodation));
        }

    }

    private List<Accommodation> getDummyAccommodations() {

        List<Accommodation> dummyAccommodations = new ArrayList<>();

        dummyAccommodations.add(new Accommodation("Hotel A", "France", "Paris", "Street 5", "1089AA", 100.0, "Hotel", "Luxurious hotel with a beautiful view", "hotelA.jpg"));
        dummyAccommodations.add(new Accommodation("Resort B", "France", "Nice", "Beach Avenue 10", "06200", 150.0, "Hotel", "Seaside resort with amazing amenities", "resortB.jpg"));
        dummyAccommodations.add(new Accommodation("Cabin C", "Netherlands", "Rotterdam", "Forest Lane 15", "V8E 0L7", 80.0, "Hotel", "Cozy cabin in the woods for a peaceful retreat", "cabinC.jpg"));
        dummyAccommodations.add(new Accommodation("Motel D", "USA", "Miami", "Ocean Drive 5", "33109", 60.0, "Hotel", "Budget-friendly motel for short stays", "motelD.jpg"));
        dummyAccommodations.add(new Accommodation("Villa E", "Italy", "Tuscany", "Vineyard Street 4", "50125", 200.0, "Hotel", "Luxury villa with private pool and garden", "villaE.jpg"));
        dummyAccommodations.add(new Accommodation("Hostel F", "Germany", "Berlin", "Backpacker Alley 18", "10178", 30.0, "Hotel", "Social hostel perfect for backpackers", "hostelF.jpg"));
        dummyAccommodations.add(new Accommodation("Chalet G", "Switzerland", "Zermatt", "Alpine Road 20", "3920", 120.0, "Hotel", "Rustic chalet nestled in the mountains", "chaletG.jpg"));
        dummyAccommodations.add(new Accommodation("Inn H", "UK", "Cotswolds", "Main Street 100", "GL54 1HN", 90.0, "Hotel", "Charming inn with cozy rooms and fireplace", "innH.jpg"));
        dummyAccommodations.add(new Accommodation("B&B I", "Netherlands", "Amsterdam", "Canal Street 16", "1012 TM", 70.0, "Hotel", "Quaint bed and breakfast with homemade breakfast", "bnbI.jpg"));
        dummyAccommodations.add(new Accommodation("Apartment J", "Italy", "Rome", "Broadway 25", "10001", 110.0, "Hotel", "Modern apartment in the city center", "apartmentJ.jpg"));
        dummyAccommodations.add(new Accommodation("Pepijn", "Italy", "Rome", "Broadway 25", "10001", 110.0, "Cottage", "Modern apartment in the city center", "cottage.jpg"));

        return dummyAccommodations;
    }

}