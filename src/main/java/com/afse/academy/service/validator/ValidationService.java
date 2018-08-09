package com.afse.academy.service.validator;

import com.afse.academy.exception.CustomerException;
import com.afse.academy.model.Customer;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "validatorBean", eager = true)
@ViewScoped
public class ValidationService implements Serializable {

    private static final long serialVersionUID = -3029114119655447704L;

    // REGEX PATTERNS
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String TEXT_PATTERN = "^[A-Za-z]+$";

    private static final String USERNAME_PATTERN = "^[A-Za-z][A-Za-z0-9]+$";

    private static final String NUMBER_PATTERN = "^[0-9]+$";

    public boolean validateCustomer(Customer customer, List<Customer> customers) throws CustomerException {
        return (validateMail(customer) & validateText(customer) & validateNumber(customer) & validateUsername(customer, customers));
    }

    private boolean validateMail(Customer customer) throws CustomerException {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(EMAIL_PATTERN);
        try {
            matcher = pattern.matcher(customer.getEmail());
            return matcher.matches();
        } catch (NullPointerException e) {
            throw new CustomerException("Null Pointer Exception !");
        }
    }

    private boolean validateText(Customer customer) throws CustomerException {
        Pattern pattern;
        Matcher matcherFname;
        Matcher matcherLname;
        Matcher matcherStreet;

        pattern = Pattern.compile(TEXT_PATTERN);
        try {
            matcherFname = pattern.matcher(customer.getFirstName());
            matcherLname = pattern.matcher(customer.getLastName());
            matcherStreet = pattern.matcher(customer.getStreet());
            return (matcherFname.matches() & matcherLname.matches() & matcherStreet.matches());
        } catch (NullPointerException e) {
            throw new CustomerException("Null Pointer Exception !");
        }
    }

    private boolean validateNumber(Customer customer) throws CustomerException {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(NUMBER_PATTERN);
        try {
            matcher = pattern.matcher(customer.getZipCode());
            return matcher.matches();
        } catch (NullPointerException e) {
            throw new CustomerException("Null Pointer Exception !");
        }
    }

    private boolean validateUsername(Customer customer, List<Customer> customers) throws CustomerException {
        Pattern pattern;
        Matcher matcher;
        boolean resultRegex;
        boolean resultUnique;

        pattern = Pattern.compile(USERNAME_PATTERN);
        try {
            matcher = pattern.matcher(customer.getUsername());
            resultRegex = matcher.matches();
        } catch (NullPointerException e) {
            throw new CustomerException("Null Pointer Exception !");
        }
        resultUnique = isUnique(customer, customers);

        return resultRegex & resultUnique;
    }

    private boolean isUnique(Customer customer, List<Customer> customers) {
        for (Customer c : customers) {
            if (c.getId().equals(customer.getId()) & c.getUsername().equals(customer.getUsername()))
                return true;

            if (c.getUsername().equals(customer.getUsername())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "This username is exists already!",
                        "Try another username, please.");
                PrimeFaces.current().dialog().showMessageDynamic(message);
                return false;
            }
        }
        return true;
    }
}
