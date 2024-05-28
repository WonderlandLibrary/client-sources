package arsenic.module.impl.other;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventSilentRotation;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.utils.minecraft.PlayerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

@ModuleInfo(name = "AutoClutch", category = ModuleCategory.Other)
public class AutoClutch extends Module {
    int ogslot;
    float[] angles;
    @EventLink
    public Listener<EventSilentRotation> eventSilentRotationListener = e -> {
        if (mc.thePlayer.onGround) {
            angles = doScaffoldRotations(new Vec3(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)));
        }
        if (!mc.thePlayer.onGround) {
            if (!PlayerUtils.isPlayerHoldingBlocks()) {
                for (int slot = 0; slot <= 8; slot++) {
                    ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                    if (itemInSlot != null && itemInSlot.getItem() instanceof ItemBlock && !itemInSlot.getItem().getRegistryName().equalsIgnoreCase("minecraft:tnt") && !itemInSlot.getItem().getRegistryName().equalsIgnoreCase("minecraft:barrier") && (((ItemBlock) itemInSlot.getItem()).getBlock().isFullBlock() || ((ItemBlock) itemInSlot.getItem()).getBlock().isFullCube())) {
                        if (mc.thePlayer.inventory.currentItem != slot) {
                            mc.thePlayer.inventory.currentItem = slot;
                        }
                    }
                }
            }
            e.setJumpFix(false);
            e.setDoMovementFix(false);
            e.setSpeed(180F);
            e.setYaw(angles[1]);
            e.setPitch(angles[1]);
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), PlayerUtils.isPlayerHoldingBlocks());
        }
    };

    public static float[] doScaffoldRotations(Vec3 vec) {
        double diffX = vec.xCoord - mc.thePlayer.posX;
        double diffY = vec.yCoord - (mc.thePlayer.getEntityBoundingBox().minY);
        double diffZ = vec.zCoord - mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)));
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, dist));
        return new float[]{
                mc.thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw),
                mc.thePlayer.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
    }

    @Override
    protected void onEnable() {
        ogslot = mc.thePlayer.inventory.currentItem;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        mc.thePlayer.inventory.currentItem = ogslot;
        super.onDisable();
    }
}