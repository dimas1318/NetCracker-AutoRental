package beans;

import auto.Session;
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
public class SessionBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public List<Session> getSessions() {
        return em.createQuery("select s from Session s", Session.class).getResultList();
    }

    public void deleteSession(Long sId) {
        if (sId == null) {
            return;
        }
        try {
            utx.begin();
            Session s = em.find(Session.class, sId);
            if(s!= null) {
                em.remove(s);
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
    
    public void createSession(SessionEditBean newS){
        if (newS == null || newS.getSessionId() == null) {     
            return;
        }
        try {
            utx.begin();
            Session se = new Session();
            se.setSessionId(newS.getSessionId());
            se.setCarId(newS.getCarId());
            se.setStartDate(newS.getStartDate());
            se.setEndDate(newS.getEndDate());
            se.setDriverId(newS.getDriverId());
            se.setCustomerId(newS.getCustomerId());
            if(se != null) {
                em.persist(se);
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