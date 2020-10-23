package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.OnOpen;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dtoWrapper", eager = true)
@ViewScoped
@NoArgsConstructor
@Getter
@Setter
public class DTOWrapper {

    @Inject private Consumer consumer;

    private List<ItemDTO> items = new ArrayList<>();
    private String hello = "hello";
    private int number;

    @OnOpen
    public void start() {
        consumer.start();
    }

    public void increment() {
        number++;
        items = consumer.getItems();
    }

    public int getSize() {
        return items.size();
    }
}
