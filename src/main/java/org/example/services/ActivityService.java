package org.example.services;

import org.example.entities.Activity;
import org.example.entities.Address;
import org.example.entities.Customer;
import org.example.interfaces.Repository;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class ActivityService extends BaseService implements Repository<Activity> {
    @Override
    public boolean create(Activity o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Activity o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Long idActivity) {
        session = sessionFactory.openSession();
        NativeQuery query = session.createSQLQuery("delete from activity where id=?1");
        query.setParameter(1,idActivity);
        session.beginTransaction();
        int success = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return success>0;
    }

    public boolean deleteCustomerActivity(Long idCustomer,Long idActivity) {
        session = sessionFactory.openSession();
        NativeQuery query = session.createSQLQuery("delete from customer_activity where customer_id=?1 and activity_id = ?2");
        query.setParameter(1,idCustomer);
        query.setParameter(2,idActivity);
        session.beginTransaction();
        int success = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return success>0;
    }

    @Override
    public Activity findById(Long id) {
        Activity activity = null;
        session = sessionFactory.openSession();
        activity = (Activity) session.get(Activity.class, id);
        session.close();
        return activity;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activityList = null;
        session = sessionFactory.openSession();
        Query<Activity> activityQuery = session.createQuery("from Activity");
        activityList = activityQuery.list();
        session.close();
        return activityList;
    }


}
