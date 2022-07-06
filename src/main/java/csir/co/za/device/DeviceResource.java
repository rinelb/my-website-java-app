package csir.co.za.device;


import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/device11")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeviceResource {
    private static final Logger LOG = Logger.getLogger(DeviceResource.class);

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findByIdofdevice(@PathParam("id") String id) {
        LOG.info("get id = "+id);
        String answer = Device.findByDeviceId(id).toString();
        LOG.info(answer);
        answer = answer.replace("Device","");
        return answer;
    }

//    @Path("/{deviceId}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Device findByDeviceId(@PathParam("deviceId") String deviceId) {
//        return Device.findByDeviceId(deviceId);
//    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() {
        LOG.info(Device.findAllDevices());
        return "{Device:\"findAllDevices\"}";
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Device update(@PathParam("id") Long id, Device device) {
        Device entity = Device.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }

        // map all appropriate fields from the 'device' parameter to the existing entity
        // For fields without new values coming in, do not overwrite the entity values
        //entity.setXYZ(device.getXYZ());

        // the data will be saved in the database when this method exists, due to the @Transactional.
        return entity;
    }

}
