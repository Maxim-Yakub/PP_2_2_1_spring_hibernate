package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        User user = null;
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Car where model = :modelValue and series = :seriesValue");

        query.setParameter("modelValue", model);
        query.setParameter("seriesValue", series);
        try {
            user = Optional.ofNullable((Car) query.getSingleResult()).get().getUser();
        } catch (NoResultException e) {
            System.out.println("Данной машины не существует");
        }
        return user;
    }
}

