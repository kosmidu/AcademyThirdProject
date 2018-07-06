import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name= "customerView")
@ViewScoped
public class CustomerView {

    private List<Customer> customers;

    @ManagedProperty("#{customerBean}")
    private CustomerImpl service;

    @PostConstruct
    public void init() {
        customers = service.getCustomers();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setService(CustomerImpl service) {
        this.service = service;
    }

    public CustomerImpl getService() {
        return service;
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
}