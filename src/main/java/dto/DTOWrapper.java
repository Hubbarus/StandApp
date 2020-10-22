package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

@Stateful
@NoArgsConstructor
@Getter
@Setter
public class DTOWrapper {

    private List<ItemDTO> items = new ArrayList<>();

    public void addItem(ItemDTO item) {
        items.add(item);
    }
}
