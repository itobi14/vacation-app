package app.repositories;

import app.models.Account;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Primary
@Repository("ACCOUNTS.JPA")
@Transactional
public class AccountJPARepository implements EntityRepository<Account> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Account> findAll() {
        TypedQuery<Account> query = this.entityManager.createQuery("select a from Account a", Account.class);
        return query.getResultList();
    }

    public List<Account> findByQuery(String jpqlName, Object ...params) {
        TypedQuery<Account> query =
                this.entityManager.createNamedQuery(jpqlName, Account.class);

        // TODO resolve all parameter values into the query
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i+1, params[i]);
        }
        // execute the query and return the result
        return query.getResultList();
    }

    @Override
    public Account findById(long id) {
        return this.entityManager.find(Account.class, id);
    }

    @Override
    public Account save(Account account) {
        return this.entityManager.merge(account);
    }

    @Override
    public Account deleteById(long id) {
        Account account = this.findById(id);
        entityManager.remove(account);
        return account;
    }

}