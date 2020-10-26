package dto;

import lombok.Data;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.math.BigDecimal;

@Stateful
@Data
public class ItemDTO implements Serializable {
    private long id;
    private String itemName;
    private String itemGroup;
    private BigDecimal price;
    private String description;
    private double weight;
    private double volume;
    private int stock;
    private String pathToIMG;
}
