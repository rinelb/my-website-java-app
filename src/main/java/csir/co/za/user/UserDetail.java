package csir.co.za.user;


import csir.co.za.plug.PlugDevice;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.jboss.logging.Logger;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name="UserDetail",
        uniqueConstraints= @UniqueConstraint(columnNames={"email"}))
public class UserDetail extends PanacheEntity {
    private static final Logger LOG = Logger.getLogger(UserDetail.class);

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

    @Column(name="name")
    private String name = null;

    @Column(name="password")
    private String password = null;


    public String  getEmail() {
        return email;
    }

    public void  setEmail(String email) {
        this.email = email;
    }

    public String  getName() {
        return name;
    }

    public void  setName(String name) {
        this.name = name;
    }

    public String  getPassword() {
        return password;
    }

    public void  setPassword(String password) {
        this.password = password;
    }


    @Transactional
    public static String createUser(UserDetail userDetail ) {
        LOG.info(" userDetail.getMail(): "+ userDetail.getEmail()   );
        boolean exists = (null != findByEmail(userDetail.getEmail()));
        String answer = null;
        LOG.info(" createDevice Exists: "+ exists   );
        if (false == exists) {
//            plugDevice.setAccessToken(propertiesUtility.getHawkbitDeviceAcessToken());
//            plugDevice.setFirmwareStatus("New_device_running");
            userDetail.persist(); //insert
            LOG.info(userDetail.getEmail() + " created.");
            answer = "created";
        } else {
            LOG.info(userDetail.getEmail() + " already exists. updating.");
            answer = "user_already_exist";
            //device.setFirmwareVersion(device.getFirmwareVersion());
            //update
            //device.setFirmwareVersion("V1.20");
           //UserDetail.updateDevice(userDetail);
        }

        return answer;
    }


    @Transactional
    public static UserDetail findByEmail(String email){
        LOG.info("User Email = "+ email);
        //LOG.info( "find = "+PlugDevice.find("SELECT * FROM plugdevice WHERE plug_device_key ='"+plugDeviceKey+"'").firstResult());
        //LOG.info( "find = "+PlugDevice.find("plug_device_key ",plugDeviceKey).firstResult());
        //return  find("plug_device_key",plugDeviceKey).firstResult();

        return find("email ",email).firstResult();
    }



}
