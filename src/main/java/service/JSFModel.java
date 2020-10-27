package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.push.annotation.PushEndpoint;

import javax.faces.bean.ManagedBean;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dtoWrapper", eager = true)
@NoArgsConstructor
@Getter
@Setter
public class JSFModel {

    @Inject @Push
    PushContext context;
    private List<ItemDTO> items = new ArrayList<>();
    private ItemDTO selectedItem;
    private boolean updated;
    private String hello = "hello";

    public void update() {
        context.send("items");
        updated = false;
    }
}
