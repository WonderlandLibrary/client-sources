package tk.rektsky.Utils;

import java.util.*;
import net.minecraft.inventory.*;

public class ContainerUtil
{
    public static ArrayList<Slot> getAllNonNullItems(final Container c) {
        final ArrayList<Slot> output = new ArrayList<Slot>();
        for (int i = 0; i != c.getInventory().size(); ++i) {
            if (c.getInventory().get(i) != null) {
                output.add(c.getSlot(i));
            }
        }
        return output;
    }
}
