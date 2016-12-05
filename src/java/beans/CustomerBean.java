package beans;

import auto.Customer;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Dmitry
 */
@ManagedBean
@ViewScoped
public class CustomerBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public List<Customer> getCustomers() {
        return em.createQuery("select e from Customer e", Customer.class).getResultList();
    }

    public void deleteCustomer(Long cId) {
        if (cId == null) {
            return;
        }
        try {
            utx.begin();
            Customer c = em.find(Customer.class, cId);
            if(c!= null) {
                em.remove(c);
            }
            
            utx.commit();
        } catch (Exception ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB Error:", ex.getLocalizedMessage()));
            ex.printStackTrace(System.err);
            try {
                utx.rollback();
            } catch (Exception exc) {
                exc.printStackTrace(System.err);
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB Error:", exc.getLocalizedMessage()));
            }
        }
    }
    
    public void createCustomer(CustomerEditBean newC){
        if (newC == null || newC.getCustomerId() == null) {     
            return;
        }
        try {
            utx.begin();
            Customer cu = new Customer();
            cu.setCustomerId(newC.getCustomerId());
            cu.setLogin(newC.getLogin());
            cu.setPassword(newC.getPassword());
            cu.setFirstName(newC.getFirstName());
            cu.setLastName(newC.getLastName());
            DateFormat df1 = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            cu.setBirthDate(df1.format(newC.getBirthDate()));
            cu.setPhoneNumber(newC.getPhoneNumber());
            DateFormat df = new SimpleDateFormat("MM dd hh:mm:ss yyyy", Locale.ENGLISH);
            cu.setRegistrationDate(df.format(newC.getRegistrationDate()));
            if(cu != null) {
                em.persist(cu);
            }
            
            utx.commit();
        } catch (Exception ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "DB Error:", ex.getLocalizedMessage()));
            ex.printStackTrace(System.err);
            try {
                utx.rollback();
            } catch (Exception exc) {              
                exc.printStackTrace(System.err);
            }
        }
    }
}