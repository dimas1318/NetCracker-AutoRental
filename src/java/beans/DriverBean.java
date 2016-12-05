package beans;

import auto.Driver;
import java.io.Serializable;
import java.util.List;
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
public class DriverBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public List<Driver> getDrivers() {
        return em.createQuery("select d from Driver d", Driver.class).getResultList();
    }

    public void deleteDriver(Long dId) {
        if (dId == null) {
            return;
        }
        try {
            utx.begin();
            Driver d = em.find(Driver.class, dId);
            if(d!= null) {
                em.remove(d);
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
    
    public void createDriver(DriverEditBean newD){
        if (newD == null || newD.getDriverId() == null) {     
            return;
        }
        try {
            utx.begin();
            Driver dr = new Driver();
            dr.setDriverId(newD.getDriverId());
            dr.setCompanyId(newD.getCompanyId());
            dr.setFirstName(newD.getFirstName());
            dr.setLastName(newD.getLastName());
            dr.setDrivingExperience(newD.getDrivingExperience());
            if(dr != null) {
                em.persist(dr);
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