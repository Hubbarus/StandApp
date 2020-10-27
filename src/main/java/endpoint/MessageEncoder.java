package endpoint;

import dto.ItemDTO;
import service.JSFModel;
import utils.JsonDeserializer;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;

public class MessageEncoder implements Encoder.Text<List<ItemDTO>> {

    @Inject private JsonDeserializer deserializer;

    @Override
    public String encode(List<ItemDTO> itemDTOS) throws EncodeException {
        return deserializer.serialize(itemDTOS);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
