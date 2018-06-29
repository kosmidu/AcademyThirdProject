import org.primefaces.PrimeFaces;

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
        List<Customer> c = (List<Customer>) component.getAttributes().get("employee");
        for(Customer cc : c ){
            if(cc.getUsername().equals(value.toString())){
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO,"EXISTS",null));
                break;
            }
        }
    }
}
