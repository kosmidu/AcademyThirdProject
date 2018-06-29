import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "customerBean", eager = true)
@SessionScoped
public class CustomerImpl implements Serializable {

    private static final long serialVersionUID = 1300588905148401215L;

    private static List<Customer> customerList = new ArrayList<>();

    private Customer user = new Customer();

    static {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(RandomDateOfBirth.getRandomDOB());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        customerList.add(new Customer(1L,"Maria", "Kosmidou", "makosmid",
                date, "Greece", "Volos", "Zachou",
                "38333", "6977777777", "makosmid@example.com", true));
        customerList.add(new Customer(2L, "John", "Galanis", "userII",
                date, "United Kingdom", "London", "Craven",
                "21873", "6924354897", "userII@example.com", true));
        customerList.add(new Customer(3L, "John", "Kalomiris", "userIII",
                date, "United Kingdom", "Southampton", "Irving",
                "237100", "6945367877", "userIII@example.com", true));
        customerList.add(new Customer(4L, "Nelson", "Sandoval", "userIV",
                date, "Mexico", "Mexico", "Leon",
                "191917", "6977345677", "userIV@example.com", true));
        customerList.add(new Customer(5L, "Fotis", "Chronopoulos", "userV",
                date, "Greece", "Athens", "Zachou",
                "171717", "6970000007", "userV@example.com", true));
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    void addCustomer(Customer empl) {
        Customer employee = new Customer(empl.getId(), empl.getFirstName(),empl.getLastName(),empl.getUsername(),
                empl.getBirthDate(),empl.getCountry(),empl.getCity(),empl.getStreet(),
                empl.getZipCode(),empl.getPhone(),empl.getEmail(),empl.isAcceptTerms());
        customerList.add(employee);
    }

    void updateCustomer(Customer customer) {
        for(Customer c : customerList){
            if(customer.getId().equals(c.getId())){
                c.setId(customer.getId());
                c.setFirstName(customer.getFirstName());
                c.setLastName(customer.getLastName());
                c.setUsername(customer.getUsername());
                c.setBirthDate(customer.getBirthDate());
                c.setEmail(customer.getEmail());
                c.setCountry(customer.getCountry());
                c.setCity(customer.getCity());
                c.setStreet(customer.getStreet());
                c.setZipCode(customer.getZipCode());
                c.setPhone(customer.getPhone());
                c.setAcceptTerms(customer.isAcceptTerms());
                return;
            }
        }
    }

    public String deleteCustomer(Customer customer) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Succeeded!",
                "The user '" + customer.getUsername() + "' is removed");
        PrimeFaces.current().dialog().showMessageDynamic(message);
        customerList.remove(customer);

        return null;
    }

    List<Customer> getCustomers() {
        return customerList;
    }
}