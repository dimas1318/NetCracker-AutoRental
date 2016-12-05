package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author Dmitry
 */
@ViewScoped @ManagedBean
public class DriverEditBean implements Serializable {
    private Long driverId;
    private long companyId;
    private String firstName;
    private String lastName;
    private long drivingExperience;

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(long drivingExperience) {
        this.drivingExperience = drivingExperience;
    }
}
