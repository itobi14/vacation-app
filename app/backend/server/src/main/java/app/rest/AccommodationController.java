package app.rest;

import app.models.Accommodation;
import app.repositories.EntityRepository;
import app.serialization.ViewClasses;
import app.exceptions.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    @Autowired
    EntityRepository<Accommodation> accommodationRepo;

    @JsonView(ViewClasses.Summary.class)
    @GetMapping(path = "", produces = "application/json")
    public List<Accommodation> getAllAccommodations() {
        return this.accommodationRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Accommodation> getAccommodation(@PathVariable() long id) {

        Accommodation accommodation = this.accommodationRepo.findById(id);

        if (accommodation == null) {
            throw new ResourceNotFoundException("Cannot provide an accommodation with id="+id);
        }

        return ResponseEntity.ok().body(accommodation);
    }

}