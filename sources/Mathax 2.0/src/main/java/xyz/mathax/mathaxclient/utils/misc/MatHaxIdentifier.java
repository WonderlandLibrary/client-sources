package xyz.mathax.mathaxclient.utils.misc;

import xyz.mathax.mathaxclient.MatHax;
import net.minecraft.util.Identifier;

public class MatHaxIdentifier extends Identifier {
    public MatHaxIdentifier(String path) {
        super(MatHax.ID,  path);
    }
}
