package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.omnifaces.cdi.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Named
@ApplicationScoped
@NoArgsConstructor
@Getter
@Setter
@Log
@Startup
public class JSFModel implements Serializable {

    @Inject @Push(channel = "push") PushContext context;
    @Inject private Consumer consumer;

    private List<ItemDTO> items;
    private ItemDTO selectedItem = new ItemDTO();
    private String hello = "Information stand of Your Favorite Online Shop";

    @PostConstruct
    public void init() {
        consumer.start();

        if (items == null) {
            items = new ArrayList<>();
        }
    }

    public void update(List<ItemDTO> items) {
        this.items = items;
        context.send("update form");
        log.log(Level.INFO, "Update request was published to client");
    }
}
