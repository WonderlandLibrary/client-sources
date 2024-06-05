package me.rich.module.movement;

import java.util.Objects;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WaterLeave extends Feature {

	public WaterLeave() {
		super("WaterLeave", 0, Category.MOVEMENT);
		Main.settingsManager.rSetting(new Setting("MotionY", this, 10, 5, 20, true));
	}


	@EventTarget
    public void onUpdate(EventUpdate event) {
        final BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);
        final Block block = mc.world.getBlockState(blockPos).getBlock();
        if (block instanceof BlockLiquid) {
            if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.01, mc.player.posZ)).getBlock() == Block.getBlockById(9) && mc.player.isInsideOfMaterial(Material.AIR)) {
            	mc.player.motionY = 0.08;
            }
            if (mc.player.fallDistance > 0.0f && mc.player.motionY < 0.01) {
                mc.player.motionY = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(WaterLeave.class), "MotionY").getValDouble();
            }
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}
