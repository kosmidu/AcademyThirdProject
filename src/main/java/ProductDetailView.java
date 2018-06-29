import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean (name = "secondView", eager = true)
@ViewScoped
public class ProductDetailView implements Serializable {
    private static final long serialVersionUID = 7057530724883984746L;

    // Properties ---------------------------------------------------------------------------------
    private Long userID; //we save it because we use it often

    private Customer user = new Customer();

    private boolean skip;

    @ManagedProperty("#{customerBean}")
    private CustomerImpl service;


    // Getters & Setters ----------------------------------------------------------------------------
    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public CustomerImpl getService() {
        return service;
    }

    public void setService(CustomerImpl service) {
        this.service = service;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    // Actions ------------------------------------------------------------------------------------

    public void submit() {
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You agreed with me!"));
        //PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "You agreed with me!", null));
        save(userID);
    }

    private void save(Long id) {
        //click();
        for(Customer c : service.getCustomers()) {
            if(id.equals(c.getId())) {

                service.updateCustomer(user);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful update!", "Hello, " + user.getFirstName());
                PrimeFaces.current().dialog().showMessageDynamic(msg);
                return;
            }
        }

        user.setId(userID);
        service.addCustomer(user);
        service.setUser(user);
        setUser(user);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful insert!", "Welcome :" + user.getFirstName());
        PrimeFaces.current().dialog().showMessageDynamic(msg);
    }

    private void click() {
        PrimeFaces.current().ajax().update("form:display");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }

    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false; //reset, in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }

    public void findCustomer() {
        for(Customer c: service.getCustomers()) {
            if(c.getId().equals(userID)) {
                setUser(c);
            }
        }
    }
}