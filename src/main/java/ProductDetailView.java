import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;

@ManagedBean (name = "editView")
@ViewScoped
public class ProductDetailView {

    // Properties ---------------------------------------------------------------------------------
    private Long userID; //we save it because we use it often

    private Customer user = new Customer();

    private boolean existID;

    private Map<String, Map<String, String>> data = new HashMap<>();
    private Map<String, String> countries;
    private Map<String, String> cities;

    @ManagedProperty("#{customerBean}")
    private CustomerImpl service;

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

        setUserID(service.getUser().getId());
        existID = findCustomer();
        setCountry(user.getCountry());
        setCity(user.getCity());

        logout();
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public Map<String, String> getCities() { return cities; }

    public String country;

    public String city;

    // Getters & Setters ----------------------------------------------------------------------------

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
        if(country !=null && !country.equals("")) {
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

    public CustomerImpl getService() {
        return service;
    }

    public void setService(CustomerImpl service) {
        this.service = service;
    }

    public Long getUserID() {
        return userID;
    }

    private void setUserID(Long userID) {
        this.userID = userID;
    }

    // Actions ------------------------------------------------------------------------------------
    public void submit() throws CustomerException {
        save(userID);
    }

    private void save(Long id) throws CustomerException {
        for(Customer c : service.getCustomers()) {
            if(id.equals(c.getId())) {
                if(service.validateCustomer(c)) {
                    service.updateCustomer(user);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful update!", "Hello, " + user.getFirstName());
                    PrimeFaces.current().dialog().showMessageDynamic(msg);
                    break;
                }
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unsuccessful update!", "Invalid data.");
                PrimeFaces.current().dialog().showMessageDynamic(msg);
                return;
            }
        }
        if(service.validateCustomer(user)) {
            user.setId(userID);
            service.addCustomer(user);
            service.setUser(user);
            setUser(user);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful insert!", "Welcome :" + user.getFirstName());
            PrimeFaces.current().dialog().showMessageDynamic(msg);
            return;
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unsuccessful insert!", "Invalid data.");
        PrimeFaces.current().dialog().showMessageDynamic(msg);
    }

    private void logout() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //return "/home.xhtml?faces-redirect=true";
        Map session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        session.put("customerBean", new CustomerImpl());
    }

    private boolean findCustomer() {
        for(Customer c: service.getCustomers()) {
            if(c.getId().equals(userID)) {
                setUser(c);
                return true;
            }
        }
        return false;
    }
}