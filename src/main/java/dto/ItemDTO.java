package dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
