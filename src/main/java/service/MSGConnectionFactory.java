package service;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.ejb.Singleton;
import java.net.URI;

@Singleton
public class MSGConnectionFactory extends ActiveMQConnectionFactory {

    public MSGConnectionFactory() {
        super(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                ActiveMQConnection.DEFAULT_BROKER_URL);
    }


}
