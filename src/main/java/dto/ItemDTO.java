package dto;

import lombok.Data;

import javax.ejb.Stateful;
import java.math.BigDecimal;

@Stateful
@Data
public class ItemDTO {
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
