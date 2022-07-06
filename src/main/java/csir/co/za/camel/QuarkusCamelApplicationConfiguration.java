package csir.co.za.camel;

import io.agroal.pool.ConnectionFactory;
import org.apache.camel.BindToRegistry;
import org.apache.camel.PropertyInject;

/**
 * Class to configure the Camel application.
 */

// https://github.com/apache/camel-examples/blob/main/examples/main-artemis/src/main/java/org/apache/camel/example/MyConfiguration.java

public class QuarkusCamelApplicationConfiguration {
    /**
     * Creates the Artemis JMS ConnectionFactory and bind it to the Camel registry
     * so we can do autowiring on the Camel JMS component.
     * See more details in the application.properties file.
     */
//    @BindToRegistry
//    public ConnectionFactory myArtemisClient(@PropertyInject("artemisBroker") String brokerUrl) {
//        ActiveMQConnectionFactory cf = new ActiveMQJMSConnectionFactory(brokerUrl);
//        cf.setUser("admin");
//        cf.setPassword("admin");
//        return cf;
//    }



    public void configure() {
        // this method is optional and can be removed if no additional configuration is needed.
    }

}
