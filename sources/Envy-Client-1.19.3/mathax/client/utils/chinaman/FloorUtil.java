package mathax.client.utils.chinaman;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;

public class FloorUtil {
    public static BlockPos ofFloored(double x, double y, double z) {
        return new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public static BlockPos ofFloored(Position pos) {
        return ofFloored(pos.getX(), pos.getY(), pos.getZ());
    }
}
