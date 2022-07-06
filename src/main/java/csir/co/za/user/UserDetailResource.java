package csir.co.za.user;

import csir.co.za.device.Device;
import csir.co.za.managementDevice.ManagementDeviceData;
import csir.co.za.managementDevice.ManagementDeviceDataResource;
import io.vertx.core.json.Json;
import org.apache.camel.util.json.JsonObject;
import org.codehaus.jackson.annotate.JsonValue;
import org.jboss.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDetailResource {
    private static final Logger LOG = Logger.getLogger(UserDetailResource.class);

    @Path("/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findById(@PathParam("email") String email) {
        String answer = UserDetail.findById(email).toString();
        LOG.info(answer);
        answer = answer.replace("UserDetail","");
        return answer;

    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Transactional
    public String update(@Valid String data) throws JSONException {
        LOG.info("data = " + data );
        String answer = null;
        JSONObject dataJson = new JSONObject(data);
        UserDetail entity = new UserDetail();
        LOG.info("dataJson.getString(\"email\") = " + dataJson.getString("email") );
        entity.setEmail(dataJson.getString("email"));
        entity.setName(dataJson.getString("name"));
        answer =  "{\"result\":\"" +UserDetail.createUser(entity)+"\"}";
        if(entity == null) {
            throw new NotFoundException();
        }

        // map all appropriate fields from the 'device' parameter to the existing entity
        // For fields without new values coming in, do not overwrite the entity values
        //entity.setXYZ(device.getXYZ());

        // the data will be saved in the database when this method exists, due to the @Transactional.
        return answer;
    }


}
