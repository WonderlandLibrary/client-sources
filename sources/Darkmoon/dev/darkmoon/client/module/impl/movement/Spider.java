package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.manager.notification.Notification;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.manager.notification.NotificationType;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

@ModuleAnnotation(name = "Spider", category = Category.MOVEMENT)
public class Spider extends Module {
    public static ModeSetting spiderSetting = new ModeSetting("Mode", "SunRise", "SunRise", "Matrix");
    public static NumberSetting delay = new NumberSetting("Delay-Blocks", 3.79f, 0.0f, 15.0f, 0.01f, () -> spiderSetting.is("SunRise"));
    private final BooleanSetting checkFlag = new BooleanSetting("Check-Flag", true);
    TimerHelper timerHelper = new TimerHelper();

    @EventTarget
    public void onMotion(EventMotion event) {
        if (mc.player == null) return;
        if (!isEnabled()) return;
        switch (spiderSetting.get()) {
            case "Matrix": {
                if (MovementUtility.isMoving() && mc.player.collidedHorizontally) {
                    if (timerHelper.hasReached(150)) {
                        event.setOnGround(true);
                        mc.player.onGround = true;
                        mc.player.collidedVertically = true;
                        mc.player.collidedHorizontally = true;
                        mc.player.isAirBorne = true;
                        mc.player.jump();
                        timerHelper.reset();
                    }
                }
                break;
            }
            case "SunRise": {
                int var2 = -1;
                int var3;
                for (var3 = 0; var3 < 9; ++var3) {
                    ItemStack var4 = this.mc.player.inventory.getStackInSlot(var3);
                    if (var4.getItem() instanceof ItemBlock) {
                        var2 = var3;
                        break;
                    }
                }

                if (this.mc.playerController.getCurrentGameType() == GameType.ADVENTURE && this.mc.player.collidedHorizontally && this.timerHelper.hasReached(1000.0)) {
                    NotificationManager.notify(NotificationType.WARNING, "Spider", ChatFormatting.RED + "Spider не может работать, если у вас режим Приключения", 3);
                    this.timerHelper.reset();
                }

                if (this.mc.playerController.getCurrentGameType() == GameType.SURVIVAL && this.mc.player.collidedHorizontally && var2 == -1 && this.timerHelper.hasReached(1000.0)) {
                    NotificationManager.notify(NotificationType.WARNING, "Spider", ChatFormatting.RED + "Необходим любой блок в хотбаре, для работы Spider", 3);
                    this.timerHelper.reset();
                } else {
                    if (this.mc.playerController.getCurrentGameType() == GameType.SURVIVAL && this.mc.player.collidedHorizontally && var2 != -1 && this.timerHelper.hasReached(this.delay.get() * 55.0)) {
                        this.mc.player.jump();
                        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.hitVec != null && this.mc.objectMouseOver.getBlockPos() != null && this.mc.objectMouseOver.sideHit != null) {
                            this.mc.player.connection.sendPacket(new CPacketHeldItemChange(var2));
                            float var9 = this.mc.player.rotationPitch;
                            this.mc.player.rotationPitch = -60.0F;
                            this.mc.entityRenderer.getMouseOver(1.0F);
                            Vec3d var10 = this.mc.objectMouseOver.hitVec;
                            BlockPos var5 = this.mc.objectMouseOver.getBlockPos();
                            float var6 = (float) (var10.x - (double) var5.getX());
                            float var7 = (float) (var10.y - (double) var5.getY());
                            float var8 = (float) (var10.z - (double) var5.getZ());
                            this.mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                            if (this.mc.world.getBlockState((new BlockPos(this.mc.player)).add(0, 2, 0)).getBlock() == Blocks.AIR) {
                                this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(var5, this.mc.objectMouseOver.sideHit, EnumHand.MAIN_HAND, var6, var7, var8));
                            } else {
                                this.mc.player.connection.sendPacket(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.START_DESTROY_BLOCK, var5, this.mc.objectMouseOver.sideHit));
                                this.mc.player.connection.sendPacket(new CPacketPlayerDigging(net.minecraft.network.play.client.CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, var5, this.mc.objectMouseOver.sideHit));
                            }

                            this.mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                            this.mc.player.rotationPitch = var9;
                            this.mc.entityRenderer.getMouseOver(1.0F);
                            this.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.mc.player.inventory.currentItem));
                            event.setOnGround(true);
                            mc.player.onGround = true;
                            mc.player.collidedVertically = true;
                            mc.player.collidedHorizontally = true;
                            mc.player.isAirBorne = true;
                            this.timerHelper.reset();
                        }
                    }
                    break;
                }
            }
        }
    }
    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            if (checkFlag.get()) {
                toggle();
            }
        }
    }
}