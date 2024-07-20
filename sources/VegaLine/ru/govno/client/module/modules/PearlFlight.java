/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class PearlFlight
extends Module {
    private final TimerHelper timerHelper = new TimerHelper();
    private boolean pearl = false;
    public static boolean go = false;

    public PearlFlight() {
        super("PearlFlight", 0, Module.Category.MOVEMENT);
        this.settings.add(new Settings("Speed", 1.0f, 1.0f, 0.0f, this));
        this.settings.add(new Settings("BoostTime", 800.0f, 1000.0f, 100.0f, this));
        this.settings.add(new Settings("MotionY", 0.55f, 1.0f, 0.0f, this));
        this.settings.add(new Settings("AutoPearl", true, (Module)this));
        this.settings.add(new Settings("TimerBoost", 0.6f, 2.0f, 0.0f, this));
        this.settings.add(new Settings("AutoDisable", true, (Module)this));
    }

    @Override
    public void onUpdate() {
        if (!go) {
            this.timerHelper.reset();
        }
        if (Minecraft.player.hurtTime != 0) {
            go = true;
        }
        if (go && this.timerHelper.hasReached((int)this.currentFloatValue("BoostTime"))) {
            go = false;
            this.timerHelper.reset();
            Minecraft.player.speedInAir = 0.02f;
            PearlFlight.mc.timer.speed = 1.0;
            if (this.currentBooleanValue("AutoDisable")) {
                this.toggle(false);
            }
        }
        if (go) {
            PearlFlight.mc.timer.speed = 1.0f + this.currentFloatValue("TimerBoost");
            Minecraft.player.speedInAir = 0.8f * this.currentFloatValue("Speed");
            Minecraft.player.motionY = this.currentFloatValue("MotionY");
            if (Minecraft.player.ticksExisted % 3 == 0) {
                MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
            }
        }
        if (this.currentBooleanValue("AutoPearl") && this.pearl) {
            int oldSlot = Minecraft.player.inventory.currentItem;
            float oldPitch = Minecraft.player.rotationPitch;
            boolean can = false;
            for (int i = 0; i < 9; ++i) {
                if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl)) continue;
                Minecraft.player.inventory.currentItem = i;
                can = true;
                PearlFlight.mc.playerController.updateController();
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(Minecraft.player.rotationYaw, 90.0f, Minecraft.player.onGround));
                PearlFlight.mc.playerController.updateController();
                Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                PearlFlight.mc.playerController.updateController();
                Minecraft.player.inventory.currentItem = oldSlot;
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Rotation(Minecraft.player.rotationYaw, oldPitch, Minecraft.player.onGround));
            }
            if (!can) {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lPearlFlight\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u043d\u0435\u0442 \u044d\u043d\u0434\u0435\u0440\u0436\u0435\u043c\u0447\u0443\u0433\u0430.", false);
                this.toggle(false);
            }
            this.pearl = false;
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            this.pearl = true;
            PearlFlight.mc.timer.speed = 1.0;
            this.timerHelper.reset();
        } else {
            Minecraft.player.speedInAir = 0.02f;
            go = false;
        }
        super.onToggled(actived);
    }
}

