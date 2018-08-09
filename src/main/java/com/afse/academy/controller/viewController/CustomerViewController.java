package com.afse.academy.controller.viewController;

import com.afse.academy.dao.CustomerDao;
import com.afse.academy.dao.CustomerDaoImpl;
import com.afse.academy.model.Customer;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "customerBean", eager = true)
@ViewScoped
public class CustomerViewController implements Serializable {

    private static final long serialVersionUID = 1300588905148401215L;

    // PROPERTIES
    private Customer user = new Customer();
    private Long userID;
    private CustomerDao customerDao = new CustomerDaoImpl();

    @ManagedProperty("#{db.customers}")
    private List<Customer> customers;

    // GETTERS & SETTERS
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void saveUserID(Long id) {
        setUserID(id);
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) { this.user = user; }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    // FUNCTIONS
    void addCustomer(Customer c) {
        Customer customer = new Customer(c.getId(), c.getFirstName(), c.getLastName(), c.getUsername(),
                c.getBirthDate(), c.getCountry(), c.getCity(), c.getStreet(),
                c.getZipCode(), c.getPhone(), c.getEmail(), c.isAcceptTerms());
        customerDao.add(customer);
    }

    void updateCustomer(Customer customer) {
        for (Customer c : customers) {
            if (customer.getId().equals(c.getId())) {
                customerDao.update(customer, c);
                return;
            }
        }
    }

    public String deleteCustomer(Customer customer) {
        customerDao.delete(customer);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Succeeded!",
                "The user '" + customer.getUsername() + "' is removed");
        PrimeFaces.current().dialog().showMessageDynamic(message);

        return null;
    }

    public Long findMax() {
        Long max = customers.get(0).getId();
        for (Customer c : customers) {
            if (c.getId() > max) {
                max = c.getId();
            }
        }
        return max;
    }

    public void saveUser() {
        for (Customer c : customers) {
            if (c.getId().equals(userID)) {
                setUser(c);
            }
        }
    }
}