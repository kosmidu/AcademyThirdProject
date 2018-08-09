package com.afse.academy.service.validator;

import com.afse.academy.model.Customer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;

@FacesValidator("UniqueUsernameValidator")
public class UniqueUsernameValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<Customer> customers = (List<Customer>) component.getAttributes().get("employee");
        for (Customer c : customers) {
            if (c.getUsername().equals(value.toString())) {
                throw new ValidatorException
                        (new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                                "Username already exists. Please try again."));
            }
        }
    }
}
