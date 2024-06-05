package me.mees.remix.modules.combat.killaura;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import me.satisfactory.base.utils.timer.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.satisfactory.base.events.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.client.multiplayer.*;

public class Tick extends Mode<Killaura>
{
    private TimerUtil timer;
    
    public Tick(final Killaura parent) {
        super(parent, "Tick");
        this.timer = new TimerUtil();
    }
    
    public void attack(final Entity target2, final boolean crit) {
        this.mc.thePlayer.swingItem();
        if (crit) {
            this.crit();
        }
        else {
            this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
        }
        if (this.mc.thePlayer.isBlocking()) {
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.fromAngle(-255.0)));
        }
        this.mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target2, C02PacketUseEntity.Action.ATTACK));
    }
    
    private void crit() {
        if (this.mc.thePlayer.fallDistance <= 0.0f) {
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.063, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.11E-4, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.keyBindUseItem.pressed = false;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        final Killaura killaura = (Killaura)this.parent;
        if (Killaura.target != null) {
            final Killaura killaura2 = (Killaura)this.parent;
            if (Killaura.target.getHealth() > 0.0f) {
                final boolean blocking = ((Killaura)this.parent).findSettingByName("AutoBlock").booleanValue() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && !this.mc.thePlayer.isBlocking();
                if (blocking) {
                    this.mc.gameSettings.keyBindUseItem.pressed = true;
                    this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
                    this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), 71999);
                }
                if (this.timer.hasTimeElapsed(493.0, true)) {
                    this.mc.thePlayer.swingItem();
                    this.swap(9, this.mc.thePlayer.inventory.currentItem);
                    final Killaura killaura3 = (Killaura)this.parent;
                    this.attack(Killaura.target, false);
                    final Killaura killaura4 = (Killaura)this.parent;
                    this.attack(Killaura.target, false);
                    final Killaura killaura5 = (Killaura)this.parent;
                    this.attack(Killaura.target, false);
                    this.swap(9, this.mc.thePlayer.inventory.currentItem);
                    final Killaura killaura6 = (Killaura)this.parent;
                    this.attack(Killaura.target, false);
                    final Killaura killaura7 = (Killaura)this.parent;
                    this.attack(Killaura.target, false);
                    final Killaura killaura8 = (Killaura)this.parent;
                    Killaura.target = null;
                    this.mc.gameSettings.keyBindUseItem.pressed = false;
                    ((Killaura)this.parent).changeTarget();
                }
                ((Killaura)this.parent).timer.reset();
            }
        }
    }
    
    public void swap(final int slot, final int hotbarNum) {
        final PlayerControllerMP playerController = this.mc.playerController;
        final int windowId = this.mc.thePlayer.inventoryContainer.windowId;
        playerController.windowClick(windowId, slot, hotbarNum, 2, this.mc.thePlayer);
    }
}
