package csir.co.za.camel;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import org.apache.camel.main.Main;
import org.jboss.logging.Logger;
import io.quarkus.scheduler.Scheduler;

import javax.enterprise.context.ApplicationScoped;

// https://github.com/apache/camel-examples/blob/main/examples/main-artemis/src/main/java/org/apache/camel/example/MyApplication.java

@ApplicationScoped
public class QuarkusCamelApplication implements QuarkusApplication {
    private static final Logger LOG = Logger.getLogger(QuarkusCamelApplication.class);

    private QuarkusCamelApplication() {

    }

    @Override
    public int run(String... args) throws Exception {
        LOG.info("Run Camel ...");
        runCamel(args);
        LOG.info("*****************************************");
        LOG.info("*****************************************");
        LOG.info("*****************************************");
        LOG.info(" ");
        LOG.info("               Application started");
        LOG.info(" ");
        LOG.info("*****************************************");
        LOG.info("*****************************************");
        LOG.info("*****************************************");
        Quarkus.waitForExit();
        LOG.info("Quarkus waiting for exit ...");
        return 0;
    }

    public static void runCamel(String[] args) throws Exception {
        // use Camels Main class
        Main main = new Main();

        // lets use a configuration class (you can specify multiple classes)
        // (properties are automatic loaded from application.properties)
        main.configure().addConfigurationClass(QuarkusCamelApplicationConfiguration.class);

        main.configure().addRoutesBuilder(KapuaPlugDataBuilder.class);

        // and add the routes (you can specify multiple classes)
        //main.configure().addRoutesBuilder(KapuaRouteBuilder.class);
        LOG.info(" *********  adding Birth Certificate Router");
        main.configure().addRoutesBuilder(KapuaBirthCertificateRouteBuilder.class);



        // now keep the application running until the JVM is terminated (ctrl + c or sigterm)
        main.run(args);
    }
}
