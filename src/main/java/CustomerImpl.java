import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "customerBean", eager = true)
@SessionScoped
public class CustomerImpl implements Serializable {

    private static final long serialVersionUID = 1300588905148401215L;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String TEXT_PATTERN = "^[A-Za-z]+$";

    private static final String USERNAME_PATTERN = "^[A-Za-z][A-Za-z0-9]+$";

    private static final String NUMBER_PATTERN = "^[0-9]+$";

    private static List<Customer> customerList = new ArrayList<>();

    private Customer user = new Customer();

    @PostConstruct
    public void init() {

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
                date, "Germany", "Munich", "Craven",
                "21873", "6924354897", "userII@example.com", true));
        customerList.add(new Customer(3L, "John", "Kalomiris", "userIII",
                date, "USA", "New York", "Irving",
                "237100", "6945367877", "userIII@example.com", true));
        customerList.add(new Customer(4L, "Nelson", "Sandoval", "userIV",
                date, "Brazil", "Sao Paolo", "Leon",
                "191917", "6977345677", "userIV@example.com", true));
        customerList.add(new Customer(5L, "Fotis", "Chronopoulos", "userV",
                date, "Greece", "Athens", "Zachou",
                "171717", "6970000007", "userV@example.com", true));
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) { this.user = user; }

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
        customerList.remove(customer);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Succeeded!",
                "The user '" + customer.getUsername() + "' is removed");
        PrimeFaces.current().dialog().showMessageDynamic(message);

        return null;
    }

    boolean validateCustomer(Customer customer) throws CustomerException {
        return (validateMail(customer) & validateText(customer) & validateNumber(customer) & validateUsername(customer));
    }

    private boolean validateMail(Customer customer) throws CustomerException {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(EMAIL_PATTERN);
        try {
                matcher = pattern.matcher(customer.getEmail());
                return matcher.matches();
        } catch(NullPointerException e) {
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

    private boolean validateUsername(Customer customer) throws CustomerException {
        Pattern pattern;
        Matcher matcher;
        boolean resultRegex;
        boolean resultUnique;

        pattern = Pattern.compile(USERNAME_PATTERN);
        try {
                matcher=pattern.matcher(customer.getUsername());
                resultRegex =  matcher.matches();
        } catch (NullPointerException e) {
            throw new CustomerException("Null Pointer Exception !");
        }
        resultUnique = isUnique(customer);

        return resultRegex & resultUnique;
    }

    private boolean isUnique(Customer customer) {
        for(Customer c: customerList) {
            if(c.getId().equals(customer.getId()) & c.getUsername().equals(customer.getUsername()))
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

    List<Customer> getCustomers() {
        return customerList;
    }

    public void logout() {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //return "/home.xhtml?faces-redirect=true";
        Map session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        session.put("customerBean", new CustomerImpl());
    }
}