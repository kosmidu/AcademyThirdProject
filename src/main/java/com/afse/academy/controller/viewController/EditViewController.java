package com.afse.academy.controller.viewController;

import com.afse.academy.exception.CustomerException;
import com.afse.academy.service.validator.ValidationService;
import com.afse.academy.model.Customer;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ManagedBean(name = "editBean")
@ViewScoped
public class EditViewController implements Serializable {

    private static final long serialVersionUID = -3880091980444766495L;

    // PROPERTIES
    private Long userID; //we save it because we use it often
    private Customer user = new Customer();
    private boolean existID;
    private String country;
    private String city;
    private Map<String, Map<String, String>> data = new HashMap<>();
    private Map<String, String> countries;
    private Map<String, String> cities;

    @ManagedProperty("#{customerBean}")
    private CustomerViewController customerService;

    @ManagedProperty("#{validatorBean}")
    private ValidationService validationService;

    @PostConstruct
    public void init() {
        countries = new HashMap<>();
        countries.put("USA", "USA");
        countries.put("Germany", "Germany");
        countries.put("Brazil", "Brazil");
        countries.put("Greece", "Greece");

        Map<String, String> map = new HashMap<>();
        map.put("New York", "New York");
        map.put("San Francisco", "San Francisco");
        map.put("Denver", "Denver");
        data.put("USA", map);

        map = new HashMap<>();
        map.put("Berlin", "Berlin");
        map.put("Munich", "Munich");
        map.put("Frankfurt", "Frankfurt");
        data.put("Germany", map);

        map = new HashMap<>();
        map.put("Sao Paolo", "Sao Paolo");
        map.put("Rio de Janerio", "Rio de Janerio");
        map.put("Salvador", "Salvador");
        data.put("Brazil", map);

        map = new HashMap<>();
        map.put("Athens", "Athens");
        map.put("Volos", "Volos");
        map.put("Drama", "Drama");
        data.put("Greece", map);
    }

    // GETTERS & SETTERS
    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public Map<String, String> getCities() { return cities; }

    public boolean isExistID() {
        return existID;
    }

    public void setExistID(boolean existID) {
        this.existID = existID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        if (country != null && !country.equals("")) {
            cities = data.get(country);
        } else {
            cities = new HashMap<>();
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public CustomerViewController getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerViewController customerService) {
        this.customerService = customerService;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    // FUNCTIONS
    public void submit() throws CustomerException {
        save(userID);
    }

    public void onCountryChange() {
        if (country != null && !country.equals("")) {
            cities = data.get(country);
        } else {
            cities = new HashMap<>();
        }
    }

    private void save(Long id) throws CustomerException {
        for (Customer c : customerService.getCustomers()) {
            if (id.equals(c.getId())) {
                if (validationService.validateCustomer(c, customerService.getCustomers())) {
                    customerService.updateCustomer(user);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful update!", "Hello, " + user.getFirstName());
                    PrimeFaces.current().dialog().showMessageDynamic(msg);
                    return;
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unsuccessful update!", "Invalid data.");
                PrimeFaces.current().dialog().showMessageDynamic(msg);
                return;
            }
        }
        if (validationService.validateCustomer(user, customerService.getCustomers())) {
            user.setId(userID);
            customerService.addCustomer(user);
            customerService.setUser(user);
            setUser(user);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful insert!", "Welcome :" + user.getFirstName());
            PrimeFaces.current().dialog().showMessageDynamic(msg);
            return;
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unsuccessful insert!", "Invalid data.");
        PrimeFaces.current().dialog().showMessageDynamic(msg);
    }

    public boolean findCustomer() {
        for (Customer c : customerService.getCustomers()) {
            if (c.getId().equals(userID)) {
                setUser(c);
                setExistID(true);

                setCountry(user.getCountry());
                setCity(user.getCity());
                return true;
            }
        }
        setExistID(false);
        return false;
    }
}