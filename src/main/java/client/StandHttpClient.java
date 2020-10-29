package client;

import dto.ItemDTO;
import lombok.extern.java.Log;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.JsonDeserializer;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Singleton
@Log
public class StandHttpClient {

    @Inject private JsonDeserializer deserializer;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public List<ItemDTO> sendGet() {
        HttpGet request = new HttpGet("http://localhost:8080/stand");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return deserializer.deserialize(result);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Data can not be reach with exception: " + e);
        }

        return Collections.emptyList();
    }
}