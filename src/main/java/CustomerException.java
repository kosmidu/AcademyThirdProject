import org.primefaces.PrimeFaces;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

class CustomerException extends Exception{
        /**
     * This Class represents an exception that we have create. It inherits the Exception class.
     * It prints an error message.
     * <p>
     * @author Kosmidou Maria
     * @version 1.0
     * @since 2018-06-20
     */

        CustomerException(String message) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO,message,null));
        }
}
