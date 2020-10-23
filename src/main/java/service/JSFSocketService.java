package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.websocket.OnOpen;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dtoWrapper", eager = true)
@ViewScoped
@NoArgsConstructor
@Getter
@Setter
public class JSFSocketService {

    @Inject private Consumer consumer;

    private List<ItemDTO> items = new ArrayList<>();
    private ItemDTO selectedItem;
    private boolean updated;
    private String hello = "hello";

    @OnOpen
    public void start() {
        consumer.start();
    }

    public void update() {
        items = consumer.getItems();
        updated = false;
    }
}
