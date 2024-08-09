package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.player.EventInteractBlock;
import dev.darkmoon.client.event.player.EventMove;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(name = "NoClip", category = Category.PLAYER)
public class NoClip extends Module {
    public static BooleanSetting destroyBlocks = new BooleanSetting("Destroy Blocks", true);
    public static NumberSetting slotSwap = new NumberSetting("Slot-Swap", 1, 1, 9, 1, () -> destroyBlocks.get());
    public TimerHelper timerHelper = new TimerHelper();
    public int itemIndex;
    private boolean swap;
    private long swapDelay;
    private final List<Integer> lastItem = new ArrayList<>();

    @EventTarget
    public void onMove(EventMove eventMove) {
        if (!collisionPredict(eventMove.to())) {
            boolean isMoving = MovementUtility.isMoving() || !mc.player.isActiveItemStackBlocking() || mc.player.movementInput.jump || mc.player.isSneaking();

            if (canMining() && isMoving) {
                mine();
            }
                if (eventMove.isCollidedHorizontal())
                    eventMove.setIgnoreHorizontalCollision();
                if (mc.player.motionY > 0 || mc.player.isSneaking()) {
                    eventMove.setIgnoreVerticalCollision();
                }
                mc.player.motionY = Math.min(mc.player.motionY, false ? 0.39 : 99999);
        }
    }

    @EventTarget
    public void onInteractBlock(EventInteractBlock event) {
        if (!destroyBlocks.get()) return;
        if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null) {
            List<Integer> bestItem = new ArrayList<>();
            float bestSpeed = 1;
            Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            for (int i = 0; i < 9; i++) {
                ItemStack item = mc.player.inventory.getStackInSlot(i);
                float speed = item.getDestroySpeed(block.getDefaultState());
                if (!(mc.player.inventory.getStackInSlot(i).getMaxDamage() - mc.player.inventory.getStackInSlot(i).getItemDamage() > 1))
                    continue;
                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestItem.add(i);
                }
            }
            if (!bestItem.isEmpty()) {
                lastItem.add(mc.player.inventory.currentItem);
                mc.player.inventory.currentItem = bestItem.get(0);
                itemIndex = bestItem.get(0);
                swap = true;
                swapDelay = System.currentTimeMillis();
            }
        }
    }
    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.inventory.currentItem = slotSwap.getInt() - 1;
        }
        super.onDisable();
    }
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (swap && !lastItem.isEmpty() && System.currentTimeMillis() >= swapDelay + 300) {
            if (!mc.gameSettings.keyBindAttack.pressed) {
                mc.player.inventory.currentItem = slotSwap.getInt() - 1;
                swap = false;
            }
        }
    }
    public boolean collisionPredict(Vec3d to) {
        boolean prevCollision = mc.world
                .getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().shrink(0.0625D)).isEmpty();
        Vec3d backUp = new Vec3d(mc.player.posX, mc.player.posY, mc.player.posZ);
        mc.player.setPosition(to.x, to.y, to.z);
        boolean collision = mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().shrink(0.0625D))
                .isEmpty() && prevCollision;
        mc.player.setPosition(backUp.x, backUp.y, backUp.z);
        return collision;
    }

    public boolean canMining() {
        return destroyBlocks.get() && mc.player.ticksExisted > 55;
    }

    private void mine() {
        if (destroyBlocks.get() && DarkMoon.getInstance().getModuleManager().getModule(NoClip.class).isEnabled()) {
            float f = mc.player.rotationYaw * ((float)Math.PI / 180);
            double speed = 0.7D;
            double x = -((double)MathHelper.sin(f) * speed);
            double z = (double)MathHelper.cos(f) * speed;
            if (timerHelper.hasReached((long)mc.player.getDigSpeed(mc.world.getBlockState(new BlockPos(mc.player.posX + x, mc.player.posY + 0.4, mc.player.posZ + z))))) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.playerController.onPlayerDamageBlock(new BlockPos(mc.player.posX + x, mc.player.posY + 0.4, mc.player.posZ + z), mc.player.getHorizontalFacing());
                timerHelper.reset();
            }
        }
    }
}
