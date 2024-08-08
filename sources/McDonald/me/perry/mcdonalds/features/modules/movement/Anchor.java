// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.movement;

import java.util.Map;
import java.util.HashMap;
import me.perry.mcdonalds.util.HoleUtil;
import net.minecraft.init.Blocks;
import me.perry.mcdonalds.util.Util;
import net.minecraft.util.math.BlockPos;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class Anchor extends Module
{
    public Setting<Integer> activateHeight;
    public Setting<Boolean> onground;
    BlockPos playerPos;
    
    public Anchor() {
        super("Anchor", "Disables movement over holes", Category.MOVEMENT, true, false, false);
        this.onground = (Setting<Boolean>)this.register(new Setting("Guarantee", (T)true));
        this.activateHeight = (Setting<Integer>)this.register(new Setting("ActivateHeight", (T)2, (T)1, (T)5));
    }
    
    @Override
    public void onUpdate() {
        if (Util.mc.player == null) {
            return;
        }
        if (Util.mc.player.posY < 0.0) {
            return;
        }
        final double blockX = Math.floor(Util.mc.player.posX);
        final double blockZ = Math.floor(Util.mc.player.posZ);
        final double offsetX = Math.abs(Util.mc.player.posX - blockX);
        final double offsetZ = Math.abs(Util.mc.player.posZ - blockZ);
        if (this.onground.getValue() && (offsetX < 0.30000001192092896 || offsetX > 0.699999988079071 || offsetZ < 0.30000001192092896 || offsetZ > 0.699999988079071)) {
            return;
        }
        this.playerPos = new BlockPos(blockX, Util.mc.player.posY, blockZ);
        if (Util.mc.world.getBlockState(this.playerPos).getBlock() != Blocks.AIR) {
            return;
        }
        BlockPos currentBlock = this.playerPos.down();
        for (int i = 0; i < this.activateHeight.getValue(); ++i) {
            currentBlock = currentBlock.down();
            if (Util.mc.world.getBlockState(currentBlock).getBlock() != Blocks.AIR) {
                final HashMap<HoleUtil.BlockOffset, HoleUtil.BlockSafety> sides = HoleUtil.getUnsafeSides(currentBlock.up());
                sides.entrySet().removeIf(entry -> entry.getValue() == HoleUtil.BlockSafety.RESISTANT);
                if (sides.size() == 0) {
                    Util.mc.player.motionX = 0.0;
                    Util.mc.player.motionZ = 0.0;
                }
            }
        }
    }
}
