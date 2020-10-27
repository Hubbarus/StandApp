package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ItemDTO;
import lombok.extern.java.Log;

import javax.ejb.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Singleton
@Log
public class JsonDeserializer {

    private ObjectMapper mapper = new ObjectMapper();

    public List<ItemDTO> deserialize(String jsonString) {
        List<ItemDTO> items = new ArrayList<>();
        try {
            items = mapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, String.format("Error while converting string %s to object", jsonString));
        }
        return items;
    }

    public String serialize(List<ItemDTO> list) {
        String resultStr = "";
        try {
            resultStr = mapper.writeValueAsString(list);
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, String.format("Error while converting object %s to JSON string", list));
        }
        return resultStr;
    }
}
