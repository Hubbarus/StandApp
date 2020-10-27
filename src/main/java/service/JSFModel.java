package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import utils.JsonDeserializer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "dtoWrapper")
@ApplicationScoped
@NoArgsConstructor
@Getter
@Setter
public class JSFModel implements Serializable {

    @Inject JsonDeserializer deserializer;

    private List<ItemDTO> items = new ArrayList<>();
    private ItemDTO selectedItem;
    private int count;
    private String hello = "hello";

    public void update() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/push", "msg");
    }
}
