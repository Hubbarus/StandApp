package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.Consumer;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.websocket.OnOpen;

@ManagedBean(name = "dtoWrapper", eager = true)
@NoArgsConstructor
@Getter
@Setter
public class DTOWrapper {

    @Inject private Consumer consumer;

    private String hello = "hello";

    @OnOpen
    public void start() {
        consumer.start();
    }
}
