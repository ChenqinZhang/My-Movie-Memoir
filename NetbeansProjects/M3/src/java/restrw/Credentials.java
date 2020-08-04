/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restrw;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seaph
 */
@Entity
@Table(name = "CREDENTIALS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credentials.findAll", query = "SELECT c FROM Credentials c")
    , @NamedQuery(name = "Credentials.findByCredentialsId", query = "SELECT c FROM Credentials c WHERE c.credentialsId = :credentialsId")
    , @NamedQuery(name = "Credentials.findByUsername", query = "SELECT c FROM Credentials c WHERE c.username = :username")
    , @NamedQuery(name = "Credentials.findByPassword", query = "SELECT c FROM Credentials c WHERE c.password = :password")
    , @NamedQuery(name = "Credentials.findBySignDate", query = "SELECT c FROM Credentials c WHERE c.signDate = :signDate")})
public class Credentials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CREDENTIALS_ID")
    private Integer credentialsId;
    @Size(max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 32)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "SIGN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signDate;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne(cascade = CascadeType.PERSIST) 
    private Person personId;

    public Credentials() {
    }

    public Credentials(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (credentialsId != null ? credentialsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credentials)) {
            return false;
        }
        Credentials other = (Credentials) object;
        if ((this.credentialsId == null && other.credentialsId != null) || (this.credentialsId != null && !this.credentialsId.equals(other.credentialsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restrw.Credentials[ credentialsId=" + credentialsId + " ]";
    }
    
}
