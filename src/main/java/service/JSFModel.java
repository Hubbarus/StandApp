package service;

import dto.ItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dtoWrapper", eager = true)
@NoArgsConstructor
@Getter
@Setter
public class JSFModel {

    private List<ItemDTO> items = new ArrayList<>();
    private ItemDTO selectedItem;
    private boolean updated;
    private String hello = "hello";

    public void update(String msg) {
        this.hello = msg;
        updated = false;
    }
}
