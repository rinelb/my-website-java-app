package csir.co.za.user;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.jboss.logging.Logger;

import javax.persistence.*;

@Entity
@Table(name="UserDevice")
public class UserDevice extends PanacheEntity {
    private static final Logger LOG = Logger.getLogger(UserDevice.class);

    @SequenceGenerator(name = "UserDetailSeq", sequenceName = "user_email_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "UserDetailSeq")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    // Device Basic Details
    // Device Basic Details
    @Column(name="email")
    private String email = null;

    @Column(name="Management_device_key")
    private String maangementDeviceId = null;

    @Column(name="plug_device_key")
    private String plugDeviceId = null;



    public String  getEmail() {
        return email;
    }

    public void  setEmail(String email) {
        this.email = email;
    }

}
