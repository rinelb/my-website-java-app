package csir.co.za.sanskrit;



        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import csir.co.za.camel.PropertiesUtility;
        import csir.co.za.device.Device;
        import csir.co.za.plug.PlugDevice;
        import io.quarkus.hibernate.orm.panache.PanacheEntity;
        import org.jboss.logging.Logger;

        import javax.persistence.*;
        import javax.transaction.Transactional;
        import java.util.List;
        import java.util.Objects;

@Entity
@Table(name="sankrit")
public class Sankrit extends PanacheEntity {
    private static final Logger LOG = Logger.getLogger(csir.co.za.sanskrit.Sankrit.class);
    private static PropertiesUtility propertiesUtility = new PropertiesUtility();
    @SequenceGenerator(name = "user_idSeq", sequenceName = "user_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "user_idSeq")


    // Device Status Details
    @Column(name="knowledge_origin")
    private String KnowledgeOrigin = null;


    @Column(name="words")
    private String Words = null;

    @Column(name="phonics")
    private String Phonics = null;


    @Column(name="meaning")
    private String Meaning = null;


    @Column(name="sloka_info")
    private String SlokaInfo = null;


    @Column(name="has_audio")
    private String HasAudio = null;

    @Column(name="audio_location")
    private String AudioLocation = null;


    public void setPhonics(String Phonics) {
        this.Phonics = Phonics;
    }

    public String getPhonics() { return Phonics;    }


    public void setWords(String Words) {
        this.Words = Words;
    }

    public String getWords() { return Words;    }


    public String getKnowledgeOrigin() { return KnowledgeOrigin;    }


    public void setKnowledgeOrigin(String KnowledgeOrigin) {
        this.KnowledgeOrigin = KnowledgeOrigin;
    }




    public void setMeaning(String Meaning) {
        this.Meaning = Meaning;
    }

    public String getMeaning() { return Meaning;    }



    public void setSlokaInfo(String SlokaInfo) {
        this.SlokaInfo = SlokaInfo;
    }

    public String getSlokaInfo() { return SlokaInfo;    }



    public void setHasAudio(String HasAudio) {
        this.HasAudio = HasAudio;
    }

    public String getHasAudio() { return HasAudio;    }

    public void setAudioLocation(String AudioLocation) {
        this.AudioLocation = AudioLocation;
    }

    public String getAudioLocation() { return AudioLocation;    }


    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(this);

    }

    public static Sankrit findByDeviceId(String plugDeviceKey){
        find("plug_device_id", plugDeviceKey).firstResult();
        return find("plug_device_id", plugDeviceKey).firstResult();
    }


    @Transactional
    public static Sankrit insertData(Sankrit device) {
//        LOG.info(" device.getDeviceId(): "+ device.getDeviceId()   );

        device.persist(); //insert
//

        return device;
    }


    @Transactional
    public static String createword(Sankrit sankrit ) {


//        LOG.info(" createDevice Exists: "+ exists   );
//        if (false == exists) {
//            plugDevice.setAccessToken(propertiesUtility.getHawkbitDeviceAcessToken());
//            plugDevice.setFirmwareStatus("New_device_running");
            sankrit.persist(); //insert
            LOG.info(sankrit.getWords() + " created.");
//        } else {
//            LOG.info(plugDevice.getPlugDeviceKey() + " already exists. updating.");
//            //device.setFirmwareVersion(device.getFirmwareVersion());
//            //update
//            //device.setFirmwareVersion("V1.20");
//            PlugDevice.updateDevice(plugDevice);
//        }

        return "plugDevice";
    }

    @Transactional
    public static String findByword(String word){
        LOG.info("plugDeviceKey = "+ word);
        //LOG.info( "find = "+PlugDevice.find("SELECT * FROM plugdevice WHERE plug_device_key ='"+plugDeviceKey+"'").firstResult());
        //LOG.info( "find = "+PlugDevice.find("plug_device_key ",plugDeviceKey).firstResult());
        //return  find("plug_device_key",plugDeviceKey).firstResult();



//        return find("words ",word).firstResult();
        List<Sankrit> allwords = find("words ",word).list();
        String jsonBuild = "[";
        for (int j=0;j<allwords.size();j++){
            jsonBuild = jsonBuild + "{";
            jsonBuild = jsonBuild + "\"meaning\":\""+allwords.get(j).Meaning+"\",";
            jsonBuild = jsonBuild + "\"word\":\""+allwords.get(j).Words+"\",";
            jsonBuild = jsonBuild + "\"has\":\""+allwords.get(j).HasAudio+"\",";
            jsonBuild = jsonBuild + "\"audio\":\""+allwords.get(j).AudioLocation+"\",";
            jsonBuild = jsonBuild + "\"sloka\":\""+allwords.get(j).SlokaInfo+"\",";
            jsonBuild = jsonBuild + "\"knowledge\":\""+allwords.get(j).KnowledgeOrigin+"\",";
            jsonBuild = jsonBuild + "\"phonic\":\""+allwords.get(j).Phonics+"\"}";
            if (j < allwords.size()-1){
                jsonBuild = jsonBuild + ",";
            }
            LOG.info(j + " "+allwords.get(j).Meaning);
        }
        jsonBuild = jsonBuild + "]";

        return jsonBuild;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

//        PlugData device = (PlugData) o;

//        if (!Objects.equals(plugDeviceId, device.plugDeviceId)) return false;
//        if (!Objects.equals(timestamp, device.timestamp)) return false;
//        if (!Objects.equals(voltage, device.voltage)) return false;
//        if (!Objects.equals(current, device.current)) return false;
//        if (!Objects.equals(actuation, device.actuation)) return false;
//
//    return Objects.equals(status, device.status);
        return false;
    }

    @Override
    public int hashCode() {
//        int result = plugDeviceId != null ? plugDeviceId.hashCode() : 0;
//        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
//        result = 31 * result + (voltage != null ? voltage.hashCode() : 0);
//        result = 31 * result + (current != null ? current.hashCode() : 0);
//        result = 31 * result + (actuation != null ? actuation.hashCode() : 0);
//        //        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
////        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
////        result = 31 * result + (deploymentUrl != null ? deploymentUrl.hashCode() : 0);
////        result = 31 * result + (firwareError != null ? firwareError.hashCode() : 0);
////        result = 31 * result + (rolloutId != null ? rolloutId.hashCode() : 0);
////        result = 31 * result + (firmwareStatus != null ? firmwareStatus.hashCode() : 0);
//        result = 31 * result + (status != null ? status.hashCode() : 0);
        return 0;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "id=" + id +
//                ", plugDeviceId='" + plugDeviceId + '\'' +
//                ", timestamp=" + timestamp + '\'' +
//                ", voltage=" +  voltage + '\'' +
//                ", current=" +  current + '\'' +
//                ", actuation=" +  actuation + '\'' +
//                ", status='" + status + '\'' +
//                '}';
//    }




}
