package csir.co.za.sanskrit;

import csir.co.za.mqtt.Mqtt;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



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

@Path("/sanskrit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SankritResource {
    private static final Logger LOG = Logger.getLogger(csir.co.za.sanskrit.SankritResource.class);


    //example http://localhost:8080/plug/device/plug0
    @Path("/word/{word}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findByWord(@PathParam("word") String word) {
        String answer = Sankrit.findByword(word);
        LOG.info(answer);
//        answer = answer.replace("Device","");
        return answer;
    }

    @Path("/knowledge/{word}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findByKnowledge(@PathParam("word") String word) {
        String answer = Sankrit.findByword(word);
        LOG.info(answer);
//        answer = answer.replace("Device","");
        return answer;
    }

//    @Path("/{id}")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Transactional
//    public PlugDevice update(@PathParam("id") Long id, PlugDevice device) {
//        PlugDevice entity = PlugDevice.findById(id);
//        if(entity == null) {
//            throw new NotFoundException();
//        }
//
//        return entity;
//    }


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


            LOG.info("rinel vaule "+ jsonObject.getString("know")+" "
                    + jsonObject.getString("word")+" "
                    + jsonObject.getString("mean")+" "
                    + jsonObject.getString("phonic")+" "
                    + jsonObject.getString("sanskrit")+" "
                    + jsonObject.getString("sloka")+" "
                    + jsonObject.getString("has")+" "
                    + jsonObject.getString("audio")+" " );
            Sankrit sankrit = new Sankrit();
            sankrit.setAudioLocation(jsonObject.getString("audio"));
            sankrit.setMeaning(jsonObject.getString("mean"));
            sankrit.setWords(jsonObject.getString("word"));
            sankrit.setSlokaInfo(jsonObject.getString("sloka"));
            sankrit.setHasAudio(jsonObject.getString("has"));
            sankrit.setSanskrit("स्");
            sankrit.setKnowledgeOrigin(jsonObject.getString("know"));
            sankrit.setPhonics(jsonObject.getString("phonic"));
            String ans = Sankrit.createword(sankrit);

            return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();


        }catch (Exception e) {
            LOG.info("rinel e "+ e.toString());
            return Response.status(Response.Status.NOT_FOUND).entity("Json not praised " + body).build();
        }



    }

}






