package me.finz0.osiris.module.modules.misc;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CropNuker extends Module {
    public CropNuker() {
        super("CropNuker", Category.MISC, "Destroy crops around you");
        AuroraMod.getInstance().settingsManager.rSetting(range = new Setting("Range", this, 4.5, 0, 10, false, "CropNukerRange"));
        AuroraMod.getInstance().settingsManager.rSetting(tickDelay = new Setting("TickDelay", this, 5, 0, 100, true, "CropNukerTickDelay"));
    }

    int waitCounter = 0;
    Setting range;
    Setting tickDelay;

    public void onUpdate(){
        if(mc.player == null || mc.world == null) return;
        List<BlockPos> blockPosList = getSphere(getPlayerPos(), (float)range.getValDouble(), 1, false, true, 0);
            blockPosList.stream()
                    .sorted(Comparator.comparing(b -> mc.player.getDistance(b.getX(), b.getY(), b.getZ())))
                    .forEach(blockPos -> {
                        if (mc.world.getBlockState(blockPos).getBlock() instanceof BlockCrops) {
                            if (waitCounter < tickDelay.getValInt()) {
                                waitCounter++;
                            } else {
                                mc.playerController.clickBlock(blockPos, EnumFacing.UP);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                waitCounter = 0;
                            }
                        }
                    });
    }

    public void onEnable(){
        waitCounter = 0;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
}
