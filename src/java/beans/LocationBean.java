package beans;

import auto.Location;
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
public class LocationBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public List<Location> getLocations() {
        return em.createQuery("select l from Location l", Location.class).getResultList();
    }

    public void deleteLocation(Long lId) {
        if (lId == null) {
            return;
        }
        try {
            utx.begin();
            Location l = em.find(Location.class, lId);
            if(l!= null) {
                em.remove(l);
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
    
    public void createLocation(LocationEditBean newL){
        if (newL == null || newL.getLocationId() == null) {     
            return;
        }
        try {
            utx.begin();
            Location lo = new Location();
            lo.setLocationId(newL.getLocationId());
            lo.setCompanyId(newL.getCompanyId());
            lo.setAddress(newL.getAddress());
            if(lo != null) {
                em.persist(lo);
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