package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventAction;
import dev.darkmoon.client.event.player.EventMove;
import dev.darkmoon.client.event.player.EventPostMove;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.combat.KillAura;
import dev.darkmoon.client.module.impl.combat.TargetStrafe;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.move.MovementUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import static dev.darkmoon.client.utility.move.MovementUtility.isMoving;

@ModuleAnnotation(name = "Strafe", category = Category.MOVEMENT)
public class Strafe extends Module {
    public ModeSetting modeSetting = new ModeSetting("Mode", "SunRise-Elytra", "SunRise-Elytra");
    public NumberSetting setSpeed = new NumberSetting("Move-Speed", 1.3F, 0.0F, 2.0F, 0.1F);
    private final BooleanSetting boost = new BooleanSetting("Boost-SunRise", true);
    private final BooleanSetting sunrise = new BooleanSetting("SunRise-Disabler", true);
    public static double speedCounter;
    public static double contextFriction;
    public static boolean needSwap;
    public static boolean needSprintState;
    public static boolean disabled;
    public static int noSlowTicks;
    public static int counter;
    static long disableTime;

    public static float getFrictionFactor(EntityPlayer entityPlayer, EventMove eventMove) {
        BlockPos.PooledMutableBlockPos pooledMutableBlockPos = BlockPos.PooledMutableBlockPos.retain(eventMove.from().x, eventMove.getAABBFrom().minY - 1.0, eventMove.from().z);
        return entityPlayer.world.getBlockState(pooledMutableBlockPos).getBlock().slipperiness * 0.91F;
    }
    @EventTarget
    public double calculateSpeed(EventMove move) {
        float speedAttributes = this.getAIMoveSpeed();
        float frictionFactor = getFrictionFactor(mc.player, move);
        float n6 = mc.player.isPotionActive(MobEffects.JUMP_BOOST) && mc.player.isHandActive() ? 0.88F : (float)(speedCounter > 0.32 && mc.player.isHandActive() ? 0.88 : 0.9100000262260437);
        if (mc.player.onGround) {
            n6 = frictionFactor;
        }

        float n7 = (float)(0.163100004196167 / Math.pow((double)n6, 3.0));
        float n8;
        if (mc.player.onGround) {
            n8 = speedAttributes * n7;
            if (move.motion().y > 0.0) {
                n8 += this.boost.get() && InventoryUtility.getElytra() != -1 && disabled && System.currentTimeMillis() - disableTime < 300L ? 0.65F : 0.2F;
            }

            disabled = false;
        } else {
            n8 = 0.0255F;
        }

        boolean noslow = false;
        double max2 = speedCounter + (double)n8;
        double max = 0.0;
        if (mc.player.isHandActive() && move.motion().y <= 0.0 && !this.sunrise.get()) {
            double n10 = speedCounter + (double)n8 * 0.25;
            double motionY2 = move.motion().y;
            if (motionY2 != 0.0 && Math.abs(motionY2) < 0.08) {
                n10 += 0.055;
            }

            if (max2 > (max = Math.max(0.043, n10))) {
                noslow = true;
                ++noSlowTicks;
            } else {
                noSlowTicks = Math.max(noSlowTicks - 1, 0);
            }
        } else {
            noSlowTicks = 0;
        }

        if (noSlowTicks > 3) {
            max2 = max - 0.019;
        } else {
            max2 = Math.max(noslow ? 0.0 : 0.25, max2) - (counter++ % 2 == 0 ? 0.001 : 0.002);
        }

        contextFriction = (double)n6;
        if (!mc.player.onGround) {
            needSprintState = !mc.player.serverSprintState;
            needSwap = true;
        } else {
            needSprintState = false;
        }

        return max2;
    }

    public float getAIMoveSpeed() {
        boolean prevSprinting = mc.player.isSprinting();
        mc.player.setSprinting(false);
        float speed = mc.player.getAIMoveSpeed() * 1.3F;
        mc.player.setSprinting(prevSprinting);
        return speed;
    }

    public static void disabler(int elytra) {
        if (elytra != -1) {
            if (System.currentTimeMillis() - disableTime > 190L) {
                if (elytra != -2) {
                    mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                }

                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING.START_FALL_FLYING));
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING.START_FALL_FLYING));
                if (elytra != -2) {
                    mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
                }

                disableTime = System.currentTimeMillis();
            }

            disabled = true;
        }

    }

    public void onEnable() {
        speedCounter = 0.10000000149011612;
        super.onEnable();
    }

    public void onDisable() {
        if (mc.player != null) {
            EntityPlayerSP var10000 = mc.player;
            var10000.motionX *= 0.699999988079071;
            var10000 = mc.player;
            var10000.motionZ *= 0.699999988079071;
        }

        super.onDisable();
    }

    public boolean canStrafe() {
        if (mc.player == null) {
            return false;
        } else if (DarkMoon.getInstance().getModuleManager().getModule(ElytraFirework.class).isEnabled()) {
            return false;
        } else if (mc.player.isElytraFlying()) {
            return false;
        } else if (DarkMoon.getInstance().getModuleManager().getModule(TargetStrafe.class).isEnabled() && KillAura.targetEntity != null) {
            return false;
        } else if (mc.player.isSneaking()) {
            return false;
        } else if (mc.player.isInLava()) {
            return false;
        } else if (!mc.player.isInWater()) {
            return !mc.player.capabilities.isFlying;
        } else {
            return false;
        }
    }

    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(mc.player.posX - 0.1, mc.player.posY, mc.player.posZ - 0.1, mc.player.posX + 0.1, mc.player.posY + 1.0, mc.player.posZ + 0.1);
    }

    @EventTarget
    public void onMove(EventMove event) {
        int elytraSlot = InventoryUtility.getElytra();
        if (this.boost.get() && elytraSlot != -1 && MovementUtility.isMoving() && !mc.player.onGround && (double)mc.player.fallDistance >= 0.5 && mc.world.getCollisionBoxes(mc.player, this.getBoundingBox().offset(0.0, event.motion().y, 0.0)).iterator().hasNext() && disabled) {
            speedCounter = (double)this.setSpeed.get() / 1.06;
        }

        if (this.canStrafe()) {
            if (MovementUtility.isMoving()) {
                double[] motions = MovementUtility.forward(this.calculateSpeed(event));
                event.motion().x = motions[0];
                event.motion().z = motions[1];
            } else {
                speedCounter = 0.0;
                event.motion().x = 0.0;
                event.motion().z = 0.0;
            }
        } else {
            speedCounter = 0.0;
        }

    }

    public void useElytra() {
        if (this.boost.get()) {
            int elytra = InventoryUtility.getElytra();
            if (!mc.player.isInWater() && !mc.player.isInLava() && elytra != -1 && !mc.player.isInWeb) {
                if (mc.player.fallDistance != 0.0F && (double)mc.player.fallDistance < 0.1 && mc.player.motionY < -0.1) {
                    if (elytra != -2) {
                        mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                    }

                    mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    if (elytra != -2) {
                        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
                    }
                }

            }
        }
    }

    @EventTarget
    public void onCheck(EventUpdate e) {
        speedCounter = Math.hypot(mc.player.posX - mc.player.prevPosX, mc.player.posZ - mc.player.prevPosZ) * contextFriction;
    }

    @EventTarget
    public void postMove(EventPostMove event) {
        if (this.boost.get() && !mc.player.onGround && mc.player.fallDistance > 0.0F && !disabled && !mc.world.getCollisionBoxes(mc.player, this.getBoundingBox().offset(0.0, 0.0, 0.0)).iterator().hasNext()) {
            disabler(InventoryUtility.getElytra());
        }

    }

    public AxisAlignedBB getBoundingBox(EntityPlayerSP player) {
        return player.getEntityBoundingBox();
    }
}
