package beans;

import auto.Car;
import auto.Session;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author Dmitry
 */
@ManagedBean
@ViewScoped
public class CarBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public List<Car> getCars() {
        return em.createQuery("select c from Car c", Car.class).getResultList();
    }

    public void deleteCar(Long cId) {
        if (cId == null) {
            return;
        }
        try {
            utx.begin();
            Car c = em.find(Car.class, cId);
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
    
    public void createCar(CarEditBean newC){
        if (newC == null || newC.getCarId() == null) {     
            return;
        }
        try {
            utx.begin();
            Car ca = new Car();
            ca.setCarId(newC.getCarId());
            ca.setCompanyId(newC.getCompanyId());
            ca.setBrand(newC.getBrand());
            ca.setName(newC.getName());
            ca.setColor(newC.getColor());
            ca.setPhoto(newC.getPhoto());
            ca.setPrice(newC.getPrice());
            if(ca != null) {
                em.persist(ca);
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
    
    public String searchNameBrand(Long id) {
        if(id == null){
            return null;
        }
        
        Session s;        
        if((s = em.find(Session.class, id)) == null){
            return "Not found";
        }
        
        Car c;
        if((c = em.find(Car.class, s.getCarId())) == null){
            return "Not found";
        }
        return c.getBrand() + " " + c.getName();
    }
}
