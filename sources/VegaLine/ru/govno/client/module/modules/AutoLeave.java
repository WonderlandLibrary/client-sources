/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AntiBot;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.settings.Settings;

public class AutoLeave
extends Module {
    Settings LeaveType = new Settings("LeaveType", "/spawn", (Module)this, new String[]{"/spawn", "/lobby", "/logout", "disconnect"});
    Settings SethomePreLeave;
    Settings LeaveOnDistance;
    Settings LeaveOnHealth;
    String ab = "";

    public AutoLeave() {
        super("AutoLeave", 0, Module.Category.PLAYER);
        this.settings.add(this.LeaveType);
        this.SethomePreLeave = new Settings("SethomePreLeave", true, (Module)this);
        this.settings.add(this.SethomePreLeave);
        this.LeaveOnDistance = new Settings("LeaveOnDistance", 40.0f, 120.0f, 10.0f, this);
        this.settings.add(this.LeaveOnDistance);
        this.LeaveOnHealth = new Settings("LeaveOnHealth", 5.0f, 20.0f, 0.0f, this);
        this.settings.add(this.LeaveOnHealth);
    }

    private final EntityPlayer getMe() {
        return FreeCam.fakePlayer != null && FreeCam.get.actived ? FreeCam.fakePlayer : Minecraft.player;
    }

    private final boolean playersIsInRange(float range) {
        for (Entity e : AutoLeave.mc.world.getLoadedEntityList()) {
            EntityPlayer player = null;
            if (e != null && e instanceof EntityOtherPlayerMP) {
                player = (EntityPlayer)e;
            }
            if (player == null || Client.friendManager.isFriend(player.getName()) || player == FreeCam.fakePlayer || player.getEntityId() == 462462999 || AntiBot.entityIsBotAdded(player) || this.getMe() == null || !(this.getMe().getDistanceToEntity(player) < range)) continue;
            this.msg(player);
            return true;
        }
        return false;
    }

    private boolean leaveByHp() {
        return Minecraft.player.getHealth() < this.LeaveOnHealth.fValue;
    }

    private final void msg(EntityPlayer e) {
        if (e == null) {
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lAutoLeave\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u043e\u0441\u0442\u0430\u043b\u043e\u0441\u044c " + (int)Minecraft.player.getHealth() + "\u0425\u041f.", false);
            return;
        }
        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lAutoLeave\u00a7r\u00a77]: \u0420\u044f\u0434\u043e\u043c \u0441 \u0432\u0430\u043c\u0438 \u043d\u0435\u0436\u0435\u043b\u0430\u0442\u0435\u043b\u044c\u043d\u044b\u0439 \u0438\u0433\u0440\u043e\u043a", false);
        Client.msg("\u00a77\u0415\u0433\u043e \u0438\u043c\u044f: " + e.getDisplayName().getFormattedText(), false);
        this.ab = e.getDisplayName().getFormattedText();
    }

    private final void doLeave(boolean isPlayer) {
        String type2 = this.LeaveType.currentMode;
        if (type2.equalsIgnoreCase("/spawn")) {
            Minecraft.player.connection.preSendPacket(new CPacketChatMessage("/spawn"));
        } else if (type2.equalsIgnoreCase("/lobby")) {
            Minecraft.player.connection.preSendPacket(new CPacketChatMessage("/hub"));
        } else if (type2.equalsIgnoreCase("/logout")) {
            Minecraft.player.connection.preSendPacket(new CPacketChatMessage("/logout"));
        } else if (type2.equalsIgnoreCase("disconnect")) {
            if (isPlayer) {
                AutoLeave.mc.world.sendQuittingDisconnectingPacket("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lAutoLeave\u00a7r\u00a77]: \u041e\u0431\u043d\u0430\u0440\u0443\u0436\u0435\u043d \u043d\u0435\u0436\u0435\u043b\u0430\u0442\u0435\u043b\u044c\u043d\u044b\u0439 \u0438\u0433\u0440\u043e\u043a \u0441 \u043d\u0438\u043a\u043e\u043c: " + this.ab);
            } else {
                AutoLeave.mc.world.sendQuittingDisconnectingPacket("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lAutoLeave\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u043e\u0441\u0442\u0430\u043b\u043e\u0441\u044c " + (int)Minecraft.player.getHealth() + "\u0425\u041f.");
            }
        }
        this.toggle(false);
    }

    private final void doSethome() {
        Minecraft.player.sendChatMessage("/sethome home");
    }

    @Override
    public void onUpdate() {
        if (this.playersIsInRange(this.LeaveOnDistance.fValue)) {
            if (this.SethomePreLeave.bValue) {
                this.doSethome();
            }
            this.doLeave(true);
            return;
        }
        if (this.leaveByHp()) {
            if (this.SethomePreLeave.bValue) {
                this.doSethome();
            }
            this.doLeave(false);
        }
    }
}

