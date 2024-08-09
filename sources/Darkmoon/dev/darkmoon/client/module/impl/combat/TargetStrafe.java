package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@ModuleAnnotation(name = "TargetStrafe", category = Category.COMBAT)
public class TargetStrafe extends Module {
    private final NumberSetting distance = new NumberSetting("Distance", 2, 0.1f, 6, 0.5f);
    private final NumberSetting speedSetting = new NumberSetting("Speed", 0.23f, 0.1f, 1.5f, 0.01f);
    private final BooleanSetting damageBoost = new BooleanSetting("Damage Boost", false);
    private final NumberSetting boostSpeed = new NumberSetting("Boost Speed", 0.5f, 0.1f, 1.5f, 0.1f, () -> damageBoost.get());
    private boolean switchDir = true;

    private boolean checkAir() {
        for(int i = (int)mc.player.posY; i > 0; --i) {
            if (!(mc.world.getBlockState(new BlockPos(mc.player.posX, i, mc.player.posZ)).getBlock() instanceof BlockAir)) {
                return false;
            }
        }

        return true;
    }

    public boolean switchCheck(double x, double z) {
        if (!mc.player.collidedHorizontally && !mc.gameSettings.keyBindLeft.isPressed() && !mc.gameSettings.keyBindRight.isPressed()) {
            for(int i = (int)(mc.player.posY + 4.0D); i >= 0; --i) {
                BlockPos blockPos = new BlockPos(x, i, z);
                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.LAVA) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.FIRE)) {
                    return true;
                }

                if (mc.world.getBlockState(blockPos).getBlock() == Blocks.WEB) {
                    return true;
                }

                if (checkAir()) {
                    return true;
                }

                if (!mc.world.isAirBlock(blockPos)) {
                    return false;
                }
            }

        }
        return true;
    }

    private double wrapToDegrees(double x, double z) {
        double diffX = x - mc.player.posX;
        double diffZ = z - mc.player.posZ;
        return Math.toDegrees(Math.atan2(diffZ, diffX)) - 90;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        EntityLivingBase entity = KillAura.targetEntity;

        if (entity == null || !entity.isEntityAlive()) {
            return;
        }

        if (mc.player.onGround) {
            mc.gameSettings.keyBindJump.pressed = false;
            mc.player.jump();
            return;
        }

        float auraRange = KillAura.range.get() + KillAura.rotateRange.get();
        float distanceToEntity = mc.player.getDistance(entity);
        if (distanceToEntity > auraRange) {
            return;
        }

        mc.gameSettings.keyBindForward.pressed = false;

        float speed = speedSetting.get();
        if (damageBoost.get() && mc.player.hurtTime > 0 && mc.player.isEntityAlive()) {
            speed += boostSpeed.get();
        }

        double wrap = Math.atan2(mc.player.posZ - entity.posZ, mc.player.posX - entity.posX);
        float additionalWrap = (float) MathHelper.clamp(speed / MathHelper.clamp(distanceToEntity, 0.01D, auraRange), 0.01F, 1.0F);
        wrap += switchDir ? additionalWrap : -additionalWrap;

        double x = entity.posX + distance.get() * Math.cos(wrap);
        double z = entity.posZ + distance.get() * Math.sin(wrap);

        if (switchCheck(x, z)) {
            switchDir = !switchDir;
            wrap += 2 * (switchDir ? additionalWrap : -additionalWrap);
            x = entity.posX + distance.get() * Math.cos(wrap);
            z = entity.posZ + distance.get() * Math.sin(wrap);
        }

        mc.player.motionX = speed * -Math.sin(Math.toRadians(wrapToDegrees(x, z)));
        mc.player.motionZ = speed * Math.cos(Math.toRadians(wrapToDegrees(x, z)));
    }
}
