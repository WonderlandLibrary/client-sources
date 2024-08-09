package mathax.client.utils.misc;

import mathax.client.MatHax;
import net.minecraft.util.Identifier;

public class MatHaxIdentifier extends Identifier {
    public MatHaxIdentifier(String path) {
        super(MatHax.ID,  path);
    }
}
