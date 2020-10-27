package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Named(value = "jsfModel")
@ApplicationScoped
@NoArgsConstructor
@Getter
@Setter
@Log
public class JSFModel implements Serializable {

    private List<ItemDTO> items = new ArrayList<>();
    private String hello = "Information stand of Your Favorite Online Shop";

    public void update() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/push", "msg");

        System.err.println("Update request was published to client");
        log.log(Level.INFO, "Update request was published to client");
    }
}
