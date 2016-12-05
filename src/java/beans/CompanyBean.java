package beans;

import auto.Company;
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
public class CompanyBean implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @Resource
    UserTransaction utx;

    public List<Company> getCompanies() {
        return em.createQuery("select d from Company d", Company.class).getResultList();
    }

    public void deleteCompany(Long cId) {
        if (cId == null) {
            return;
        }
        try {
            utx.begin();
            Company c = em.find(Company.class, cId);
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
    
    public void createCompany(CompanyEditBean newC){
        if (newC == null || newC.getCompanyId() == null) {     
            return;
        }
        try {
            utx.begin();
            Company co = new Company();
            co.setCompanyId(newC.getCompanyId());
            co.setName(newC.getName());
            co.setSite(newC.getSite());
            co.setPhoneNumber(newC.getPhoneNumber());
            if(co != null) {
                em.persist(co);
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