package com.afse.academy.dao;

import com.afse.academy.model.Customer;
import com.afse.academy.service.RandomDateOfBirth;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "db", eager = true)
@ViewScoped
public class CustomerDaoImpl implements CustomerDao, Serializable {

    private static final long serialVersionUID = -3707777831199365865L;
    private static List<Customer> customers = new ArrayList<>();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(RandomDateOfBirth.getRandomDOB());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        customers.add(new Customer(1L, "Maria", "Kosmidou", "makosmid",
                date, "Greece", "Volos", "Zachou",
                "38333", "6977777777", "makosmid@example.com", true));
        customers.add(new Customer(2L, "John", "Galanis", "userII",
                date, "Germany", "Munich", "Craven",
                "21873", "6924354897", "userII@example.com", true));
        customers.add(new Customer(3L, "John", "Kalomiris", "userIII",
                date, "USA", "New York", "Irving",
                "237100", "6945367877", "userIII@example.com", true));
        customers.add(new Customer(4L, "Nelson", "Sandoval", "userIV",
                date, "Brazil", "Sao Paolo", "Leon",
                "191917", "6977345677", "userIV@example.com", true));
        customers.add(new Customer(5L, "Fotis", "Chronopoulos", "userV",
                date, "Greece", "Athens", "Zachou",
                "171717", "6970000007", "userV@example.com", true));
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        CustomerDaoImpl.customers = customers;
    }

    @Override
    public void add(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void update(Customer customerNew, Customer customerCurrent) {
        customerCurrent.setId(customerNew.getId());
        customerCurrent.setFirstName(customerNew.getFirstName());
        customerCurrent.setLastName(customerNew.getLastName());
        customerCurrent.setUsername(customerNew.getUsername());
        customerCurrent.setBirthDate(customerNew.getBirthDate());
        customerCurrent.setEmail(customerNew.getEmail());
        customerCurrent.setCountry(customerNew.getCountry());
        customerCurrent.setCity(customerNew.getCity());
        customerCurrent.setStreet(customerNew.getStreet());
        customerCurrent.setZipCode(customerNew.getZipCode());
        customerCurrent.setPhone(customerNew.getPhone());
        customerCurrent.setAcceptTerms(customerNew.isAcceptTerms());
    }

    @Override
    public void delete(Customer customer) {
        customers.remove(customer);
    }

}