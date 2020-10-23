package utils;

import dto.ItemDTO;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UpdateChecker {

    public static boolean checkIfUpdated(List<ItemDTO> oldList, List<ItemDTO> newList) {
        return !oldList.equals(newList);
    }
}
