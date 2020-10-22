package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ItemDTO;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class JsonDeserializer {

    public List<ItemDTO> deserialize(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        List<ItemDTO> items = new ArrayList<>();
        try {
            items = mapper.readValue(jsonString, new TypeReference<List<ItemDTO>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return items;
    }
}
