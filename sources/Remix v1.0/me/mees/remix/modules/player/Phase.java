package me.mees.remix.modules.player;

import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.phase.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class Phase extends Module
{
    public Phase() {
        super("Phase", 0, Category.PLAYER);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("HCF");
        options.add("Guardian");
        options.add("Virtue");
        this.addSetting(new Setting("PhaseMode", this, "HCF", options));
        this.addMode(new HCF(this));
        this.addMode(new Guardian(this));
        this.addMode(new Virtue(this));
    }
    
    public boolean isInsideBlock() {
        if (Phase.mc.thePlayer != null && Phase.mc.theWorld != null) {
            for (int x = MathHelper.floor_double(Phase.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(Phase.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
                for (int y = MathHelper.floor_double(Phase.mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(Phase.mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                    for (int z = MathHelper.floor_double(Phase.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Phase.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                        final Block block = Phase.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                        final AxisAlignedBB boundingBox;
                        if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(Phase.mc.theWorld, new BlockPos(x, y, z), Phase.mc.theWorld.getBlockState(new BlockPos(x, y, z)))) != null && Phase.mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
