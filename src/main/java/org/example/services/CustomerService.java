package org.example.services;

import org.example.entities.Activity;
import org.example.entities.Address;
import org.example.entities.Customer;
import org.example.interfaces.Repository;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerService extends BaseService implements Repository<Customer> {
    @Override
    public boolean create(Customer o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Customer o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }


    public boolean delete(Long idCustomer) {
        session = sessionFactory.openSession();
        NativeQuery query = session.createSQLQuery("delete from customer where id=?1");
        query.setParameter(1,idCustomer);
        session.beginTransaction();
        int success = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return success>0;
    }

    @Override
    public Customer findById(Long id) {
        Customer customer = null;
        session = sessionFactory.openSession();
        customer = (Customer) session.get(Customer.class, id);
        session.close();
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = null;
        session = sessionFactory.openSession();
        Query<Customer> customerQuery = session.createQuery("from Customer");
        customerList = customerQuery.list();
        session.close();
        return customerList;
    }

    public boolean addAdress(Address address, Long id) {
        boolean result = false;
        Customer customer = this.findById(id);
        session =sessionFactory.openSession();
        session.getTransaction().begin();
        if(customer != null) {
            address.setCustomer(customer);
            session.save(address);
            customer.setAddress(address);
            session.update(customer);
            result = true;
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public boolean addActivity(Activity activity, Long id) {
        boolean result = false;
        Customer customer = this.findById(id);
        session =sessionFactory.openSession();
        session.getTransaction().begin();
        if(customer != null) {
            customer.addActivity(activity);
            session.update(customer);
            result = true;
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List filterDurationTotal(Long id){
        session = sessionFactory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT customer.lastName,customer.firstName,SUM(activity.duration) as total FROM customer INNER JOIN customer_activity ON customer.id = customer_id INNER JOIN activity ON activity.id = activity_id where customer.id = ?1 group by customer.lastName");
        query.setParameter(1,id);
        List durationTotal = query.list();
        session.close();
        return durationTotal;
    }

    public List filterTotalPriceByCustomer(Long id){
        session = sessionFactory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT customer.lastName,customer.firstName,SUM(activity.price) as total FROM customer INNER JOIN customer_activity ON customer.id = customer_id INNER JOIN activity ON activity.id = activity_id where customer.id = ?1 group by customer.lastName");
        query.setParameter(1,id);
        List durationTotal = query.list();
        session.close();
        return durationTotal;
    }

    public double filterTotalPrice(){
        session = sessionFactory.openSession();
        NativeQuery query = session.createSQLQuery("SELECT SUM(activity.price) as total FROM customer INNER JOIN customer_activity ON customer.id = customer_id INNER JOIN activity ON activity.id = activity_id ");
        double durationTotal =(double) query.getSingleResult();
        session.close();
        return durationTotal;
    }


    public void begin(){
        session = sessionFactory.openSession();
    }

    public void end(){ sessionFactory.close(); }
}
