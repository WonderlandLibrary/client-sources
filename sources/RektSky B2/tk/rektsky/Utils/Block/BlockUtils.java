package tk.rektsky.Utils.Block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.entity.*;

public class BlockUtils
{
    public static Material getBlockMaterial(final BlockPos blockPos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock().getMaterial();
    }
    
    public static ArrayList<BlockPos> searchForBlock(final String blockName, final float range) {
        final ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        for (float x = -range; x < range - 1.0f; ++x) {
            for (float y = -range; y < range - 1.0f; ++y) {
                for (float z = -range; z < range - 1.0f; ++z) {
                    final BlockPos blockPos = new BlockPos(thePlayer.getPosition().getX() + x, thePlayer.getPosition().getY() + y, thePlayer.getPosition().getZ() + z);
                    if (Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock().getUnlocalizedName().equals(blockName)) {
                        blocks.add(blockPos);
                    }
                }
            }
        }
        return blocks;
    }
}
