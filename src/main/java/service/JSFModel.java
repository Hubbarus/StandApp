package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject @Push(channel = "push") PushContext context;

    private List<ItemDTO> items = new ArrayList<>();
    private ItemDTO selectedItem = new ItemDTO();
    private String hello = "Information stand of Your Favorite Online Shop";

    public void update(List<ItemDTO> items) {
        this.items = items;
        hello = "hell";
        context.send("update form");
        log.log(Level.INFO, "Update request was published to client");
    }
}
