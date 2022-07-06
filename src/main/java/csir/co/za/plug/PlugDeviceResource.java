package csir.co.za.plug;

import csir.co.za.mqtt.Mqtt;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/plug/device")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlugDeviceResource {
    private static final Logger LOG = Logger.getLogger(PlugDeviceResource.class);


    //example http://localhost:8080/plug/device/plug0
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findById(@PathParam("id") String id) {
        String answer = PlugDevice.findByDeviceKey(id).toString();
        LOG.info(answer);
        answer = answer.replace("Device","");
        return answer;
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public PlugDevice update(@PathParam("id") Long id, PlugDevice device) {
        PlugDevice entity = PlugDevice.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }

        return entity;
    }


    @Path("/posting")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response sendProfile(String body)  {
        try {

            LOG.info("rinel e the body "+ body.toString());
            String entity = body;


            JSONObject jsonObject = new JSONObject(entity);
            String payload = jsonObject.getString("payload");
            String id = jsonObject.getString("id");
            Mqtt mqtt = new Mqtt();
            mqtt.connect();
            mqtt.sendMessage(id,payload);
            mqtt.close();
            LOG.info("rinel vaule "+ payload);
            return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();


        }catch (Exception e) {
            LOG.info("rinel e "+ e.toString());
            return Response.status(Response.Status.NOT_FOUND).entity("Json not praised " + body).build();
        }



    }

}






