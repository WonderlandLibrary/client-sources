/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.network.play.client.CPacketChatMessage;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.PointTrace;
import ru.govno.client.module.settings.Settings;

public class Respawn
extends Module {
    public static Respawn get;
    public Settings DeathCoords = new Settings("DeathCoords", true, (Module)this);
    public Settings AutoSethome;
    public Settings AutoHome;
    public Settings AutoWand;
    public Settings DeathPoint;
    boolean doAny;

    public Respawn() {
        super("Respawn", 0, Module.Category.PLAYER);
        this.settings.add(this.DeathCoords);
        this.AutoSethome = new Settings("AutoSethome", false, (Module)this);
        this.settings.add(this.AutoSethome);
        this.AutoHome = new Settings("AutoHome", true, (Module)this);
        this.settings.add(this.AutoHome);
        this.AutoWand = new Settings("AutoWand", false, (Module)this);
        this.settings.add(this.AutoWand);
        this.DeathPoint = new Settings("DeathPoint", true, (Module)this);
        this.settings.add(this.DeathPoint);
        get = this;
    }

    private void setDeathPoint(int[] xyz) {
        String death = "Death";
        PointTrace point = PointTrace.getPointByName(death);
        if (point != null) {
            PointTrace.points.remove(point);
        }
        PointTrace.points.add(new PointTrace(death, xyz[0], xyz[1], xyz[2]));
    }

    @Override
    public void onMovement() {
        if (Respawn.mc.currentScreen instanceof GuiGameOver && this.doAny) {
            int posx = (int)Minecraft.player.posX;
            int posy = (int)Minecraft.player.posY;
            int posz = (int)Minecraft.player.posZ;
            if (this.DeathCoords.bValue) {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lRespawn\u00a7r\u00a77]: \u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b \u0441\u043c\u0435\u0440\u0442\u0438: " + posx + "," + posy + "," + posz + ".", false);
            }
            if (this.DeathPoint.bValue) {
                this.setDeathPoint(new int[]{posx, posy, posz});
            }
            if (this.AutoSethome.bValue) {
                Minecraft.player.connection.sendPacket(new CPacketChatMessage("/sethome home"));
            }
            Minecraft.player.respawnPlayer();
            if (this.AutoHome.bValue) {
                Minecraft.player.connection.sendPacket(new CPacketChatMessage("/home home"));
            }
            if (this.AutoWand.bValue) {
                Minecraft.player.connection.sendPacket(new CPacketChatMessage("//wand"));
            }
            Minecraft.player.closeScreen();
            this.doAny = false;
        } else {
            this.doAny = true;
        }
    }
}

