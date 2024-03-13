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
        accounts.add(this.accountsRepo.save(new Account("Tobi", "Kok", "tobi@example.com")));

        // updates will be persisted by the transaction
        for (Account a : accounts) {
            a.setPassword("test");
            System.out.println("Added account: " + a + " (initial password = 'test')");
        }
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

        dummyAccommodations.add(new Accommodation("Hotel A", 100.0, "Hotel", "Luxurious hotel with a beautiful view", "hotelA.jpg"));
        dummyAccommodations.add(new Accommodation("Resort B", 150.0, "Resort", "Seaside resort with amazing amenities", "resortB.jpg"));
        dummyAccommodations.add(new Accommodation("Cabin C", 80.0, "Cabin", "Cozy cabin in the woods for a peaceful retreat", "cabinC.jpg"));
        dummyAccommodations.add(new Accommodation("Motel D", 60.0, "Motel", "Budget-friendly motel for short stays", "motelD.jpg"));
        dummyAccommodations.add(new Accommodation("Villa E", 200.0, "Villa", "Luxury villa with private pool and garden", "villaE.jpg"));
        dummyAccommodations.add(new Accommodation("Hostel F", 30.0, "Hostel", "Social hostel perfect for backpackers", "hostelF.jpg"));
        dummyAccommodations.add(new Accommodation("Chalet G", 120.0, "Chalet", "Rustic chalet nestled in the mountains", "chaletG.jpg"));
        dummyAccommodations.add(new Accommodation("Inn H", 90.0, "Inn", "Charming inn with cozy rooms and fireplace", "innH.jpg"));
        dummyAccommodations.add(new Accommodation("B&B I", 70.0, "Bed & Breakfast", "Quaint bed and breakfast with homemade breakfast", "bnbI.jpg"));
        dummyAccommodations.add(new Accommodation("Apartment J", 110.0, "Apartment", "Modern apartment in the city center", "apartmentJ.jpg"));

        return dummyAccommodations;
    }



}