package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Esp extends Module {
    public Esp() {
        super("Esp", Category.RENDER, "Draws a box around entities");
        AuroraMod.getInstance().settingsManager.rSetting(players = new Setting("Players", this, false, "BoxEspPlayers"));
        AuroraMod.getInstance().settingsManager.rSetting(passive = new Setting("Passive", this, false, "BoxEspPassive"));
        AuroraMod.getInstance().settingsManager.rSetting(mobs = new Setting("Mobs", this, false, "BoxEspMobs"));
        AuroraMod.getInstance().settingsManager.rSetting(exp = new Setting("XpBottles", this, false, "BoxEspXpBottles"));
        AuroraMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, false, "BoxEspCrystals"));
        AuroraMod.getInstance().settingsManager.rSetting(items = new Setting("Items", this, false, "BoxEspItems"));
        rSetting(orbs = new Setting("XpOrbs", this, false, "BoxEspXpOrbs"));
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "BoxEspRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(r = new Setting("Red", this, 255, 1, 255, true, "BoxEspRed"));
        AuroraMod.getInstance().settingsManager.rSetting(g = new Setting("Green", this, 255, 1, 255, true, "BoxEspGreen"));
        AuroraMod.getInstance().settingsManager.rSetting(b = new Setting("Blue", this, 255, 1, 255, true, "BoxEspBlue"));
        AuroraMod.getInstance().settingsManager.rSetting(a = new Setting("Alpha", this, 50, 1, 255, true, "BoxEspAlpha"));
    }

    Setting players;
    Setting passive;
    Setting mobs;
    Setting exp;
    Setting crystals;
    Setting items;
    Setting orbs;

    Setting rainbow;
    Setting r;
    Setting g;
    Setting b;
    Setting a;

    public void onWorldRender(RenderEvent event){
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), a.getValInt());
        if(rainbow.getValBoolean()) c = new Color(Rainbow.getColor().getRed(), Rainbow.getColor().getGreen(), Rainbow.getColor().getBlue(), a.getValInt());
        Color enemy = new Color(255, 0, 0, a.getValInt());
        Color friend = new Color(0, 255, 255, a.getValInt());
        Color finalC = c;
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    OsirisTessellator.prepare(GL11.GL_QUADS);
                    if(players.getValBoolean() && e instanceof EntityPlayer) {
                        if (Friends.isFriend(e.getName())) OsirisTessellator.drawBox(e.getRenderBoundingBox(), friend.getRGB(), GeometryMasks.Quad.ALL);
                        else OsirisTessellator.drawBox(e.getRenderBoundingBox(), enemy.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(mobs.getValBoolean() && GlowESP.isMonster(e)){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(passive.getValBoolean() && GlowESP.isPassive(e)){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(exp.getValBoolean() && e instanceof EntityExpBottle){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(crystals.getValBoolean() && e instanceof EntityEnderCrystal){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(items.getValBoolean() && e instanceof EntityItem){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    if(orbs.getValBoolean() && e instanceof EntityXPOrb){
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                    }
                    OsirisTessellator.release();
                });
    }
}
