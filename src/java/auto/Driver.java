/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Dmitry
 */
@Entity @Table(name="driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (driverId != null ? driverId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Driver)) {
            return false;
        }
        Driver other = (Driver) object;
        return !((this.driverId == null && other.driverId!= null) || (this.driverId != null && !this.driverId.equals(other.driverId)));
    }

    @Override
    public String toString() {
        return "auto.Driver[ id=" + driverId + " ]";
    }
    
}
