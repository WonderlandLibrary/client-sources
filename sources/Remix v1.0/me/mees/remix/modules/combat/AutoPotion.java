package me.mees.remix.modules.combat;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.other.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import pw.stamina.causam.scan.method.model.*;

public final class AutoPotion extends Module
{
    public static boolean isPotting;
    private TimerUtil timer;
    private int lockedTicks;
    
    public AutoPotion() {
        super("AutoPotion", 0, Category.COMBAT);
        this.timer = new TimerUtil();
        this.addSetting(new Setting("AutopotHealth", this, 6.0, 1.0, 10.0, false, 0.5));
        this.addSetting(new Setting("AutopotDelay", this, 50.0, 50.0, 2000.0, true, 10.0));
        this.addSetting(new Setting("HeadPot", this, false));
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (this.lockedTicks >= 0) {
            --this.lockedTicks;
            final int potSlot = InventoryUtils.getPotionFromInventory();
            if (potSlot != -1 && AutoPotion.mc.thePlayer.getHealth() <= this.getSettingByModule(this, "AutopotHealth").doubleValue() * 2.0 && this.timer.hasTimeElapsed((double)(long)this.getSettingByModule(this, "AutopotDelay").doubleValue(), true)) {
                AutoPotion.mc.thePlayer.motionX = 0.0;
                AutoPotion.mc.thePlayer.motionZ = 0.0;
            }
        }
        else if (this.lockedTicks < 0) {
            final int potSlot2 = InventoryUtils.getPotionFromInventory();
            if (potSlot2 != -1 && AutoPotion.mc.thePlayer.getHealth() < (float)this.getSettingByModule(this, "AutopotHealth").doubleValue() * 2.0 && this.timer.hasTimeElapsed((double)(long)this.getSettingByModule(this, "AutopotDelay").doubleValue(), true)) {
                if (InventoryUtils.hotBarHasPots()) {
                    for (int i = 36; i < 45; ++i) {
                        final ItemStack stack = AutoPotion.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if (stack != null && InventoryUtils.isSplashPot(stack)) {
                            AutoPotion.isPotting = true;
                            if (this.getSettingByModule(this, "HeadPot").booleanValue() && AutoPotion.mc.thePlayer.onGround) {
                                AutoPotion.mc.thePlayer.motionY = 0.42399999499320984;
                            }
                            final int oldSlot = AutoPotion.mc.thePlayer.inventory.currentItem;
                            AutoPotion.mc.thePlayer.motionX = 0.0;
                            AutoPotion.mc.thePlayer.motionZ = 0.0;
                            if (!this.getSettingByModule(this, "HeadPot").booleanValue()) {
                                AutoPotion.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(AutoPotion.mc.thePlayer.posX, AutoPotion.mc.thePlayer.posY, AutoPotion.mc.thePlayer.posZ, AutoPotion.mc.thePlayer.rotationYaw, 85.0f, AutoPotion.mc.thePlayer.onGround));
                            }
                            else {
                                AutoPotion.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(AutoPotion.mc.thePlayer.posX, AutoPotion.mc.thePlayer.posY, AutoPotion.mc.thePlayer.posZ, AutoPotion.mc.thePlayer.rotationYaw, -85.0f, AutoPotion.mc.thePlayer.onGround));
                            }
                            AutoPotion.mc.thePlayer.sendQueue.sendPacketNoEvent(new C09PacketHeldItemChange(i - 36));
                            AutoPotion.mc.thePlayer.motionX = 0.0;
                            AutoPotion.mc.thePlayer.motionZ = 0.0;
                            AutoPotion.mc.thePlayer.sendQueue.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(stack));
                            AutoPotion.mc.thePlayer.motionX = 0.0;
                            AutoPotion.mc.thePlayer.motionZ = 0.0;
                            AutoPotion.mc.thePlayer.sendQueue.sendPacketNoEvent(new C09PacketHeldItemChange(oldSlot));
                            break;
                        }
                    }
                    AutoPotion.isPotting = false;
                }
                else {
                    AutoPotion.isPotting = true;
                    InventoryUtils.grabPot();
                    AutoPotion.isPotting = false;
                }
            }
        }
    }
}
