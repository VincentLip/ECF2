package org.example.services;

import org.example.entities.Address;
import org.example.entities.Customer;
import org.example.interfaces.Repository;
import org.hibernate.query.Query;

import java.util.List;

public class AdressService extends BaseService implements Repository<Address> {
    @Override
    public boolean create(Address o) {
        return false;
    }

    @Override
    public boolean update(Address o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


    @Override
    public Address findById(Long id) {
        Address address = null;
        session = sessionFactory.openSession();
        address = (Address) session.get(Address.class, id);
        session.close();
        return address;
    }

    @Override
    public List<Address> findAll() {
        List<Address> addressList = null;
        session = sessionFactory.openSession();
        Query<Address> addressQuery = session.createQuery("from Address");
        addressList = addressQuery.list();
        session.close();
        return addressList;
    }
}
