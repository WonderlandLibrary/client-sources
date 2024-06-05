package me.superskidder.lune.modules.player;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

/**
 * @description: 自动下蹲
 * @author: QianXia
 * @create: 2020/10/11 17:27
 **/
public class Eagle extends Mod {
    public Eagle() {
        super("Eagle", ModCategory.Player,"Auto Sneak when you walk to edge of a block");
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.thePlayer == null) {
            return;
        }

        IBlockState underBlock = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));
        boolean flag = underBlock.getBlock().equals(Blocks.air) && mc.thePlayer.onGround;

        if (flag) {
            mc.gameSettings.keyBindSneak.pressed = true;
        } else {
            mc.gameSettings.keyBindSneak.pressed = false;
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null) {
            return;
        }

        mc.gameSettings.keyBindSneak.pressed = false;
    }
}
