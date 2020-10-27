package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "jsfModel")
@ApplicationScoped
@NoArgsConstructor
@Getter
@Setter
public class JSFModel implements Serializable {

    private List<ItemDTO> items = new ArrayList<>();
    private String hello = "Information stand of Your Favorite Online Shop";

    public void update() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/push", "msg");
    }
}
