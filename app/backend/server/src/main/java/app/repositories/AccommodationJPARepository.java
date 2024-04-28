package app.repositories;

import app.models.Accommodation;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Primary
@Repository("ACCOMMODATIONS.JPA")
@Transactional
public class AccommodationJPARepository implements EntityRepository<Accommodation> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Accommodation> findAll() {
        TypedQuery<Accommodation> query = this.entityManager.createQuery("select a from Accommodation a", Accommodation.class);
        return query.getResultList();
    }

//    public List<Accommodation> findByQuery(String jpqlName, Object ...params) {
//        TypedQuery<Accommodation> query =
//                this.entityManager.createNamedQuery(jpqlName, Accommodation.class);
//
//        // TODO resolve all parameter values into the query
//        for (int i = 0; i < params.length; i++) {
//            query.setParameter(i+1, params[i]);
//        }
//        // execute the query and return the result
//        return query.getResultList();
//    }

    @Override
    public Accommodation findById(long id) {
        return this.entityManager.find(Accommodation.class, id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return this.entityManager.merge(accommodation);
    }

    @Override
    public Accommodation deleteById(long id) {
        Accommodation accommodation = this.findById(id);
        entityManager.remove(accommodation);
        return accommodation;
    }

}