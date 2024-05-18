package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class OutLineESP extends Module {
    public OutLineESP() {
        super("OutLineESP", Category.RENDER);
        AuroraMod.getInstance().settingsManager.rSetting(players = new Setting("Players", this, false, "HitBoxEspPlayers"));
        AuroraMod.getInstance().settingsManager.rSetting(passive = new Setting("Passive", this, false, "HitBoxEspPassive"));
        AuroraMod.getInstance().settingsManager.rSetting(mobs = new Setting("Mobs", this, false, "HitBoxEspMobs"));
        AuroraMod.getInstance().settingsManager.rSetting(exp = new Setting("XpBottles", this, false, "HitBoxEspXpBottles"));
        rSetting(orbs = new Setting("XpOrbs", this, false, "HitBoxEspXpOrbs"));
        AuroraMod.getInstance().settingsManager.rSetting(epearls = new Setting("Epearls", this, false, "HitBoxEspEpearls"));
        AuroraMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, false, "HitBoxEspCrystals"));
        AuroraMod.getInstance().settingsManager.rSetting(items = new Setting("Items", this, false, "HitBoxEspItems"));
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "HitBoxEspRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(r = new Setting("Red", this, 255, 1, 255, true, "HitBoxEspRed"));
        AuroraMod.getInstance().settingsManager.rSetting(g = new Setting("Green", this, 255, 1, 255, true, "HitBoxEspGreen"));
        AuroraMod.getInstance().settingsManager.rSetting(b = new Setting("Blue", this, 255, 1, 255, true, "HitBoxEspBlue"));
        AuroraMod.getInstance().settingsManager.rSetting(a = new Setting("Alpha", this, 50, 1, 255, true, "HitBoxEspAlpha"));
    }

    Setting players;
    Setting passive;
    Setting mobs;
    Setting exp;
    Setting epearls;
    Setting crystals;
    Setting items;
    Setting orbs;

    Setting rainbow;
    Setting r;
    Setting g;
    Setting b;
    Setting a;
    Color c;

    public void onWorldRender(RenderEvent event){
        c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), a.getValInt());
        if(rainbow.getValBoolean()) c = new Color(Rainbow.getColor().getRed(), Rainbow.getColor().getGreen(), Rainbow.getColor().getBlue(), a.getValInt());
        Color enemy = new Color(255, 0, 0, a.getValInt());
        Color friend = new Color(0, 255, 255, a.getValInt());
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    OsirisTessellator.prepareGL();
                    if(players.getValBoolean() && e instanceof EntityPlayer) {
                        if (Friends.isFriend(e.getName())) OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, friend.getRGB());
                        else OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, enemy.getRGB());
                    }
                    if(mobs.getValBoolean() && GlowESP.isMonster(e)){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(passive.getValBoolean() && GlowESP.isPassive(e)){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(exp.getValBoolean() && e instanceof EntityExpBottle){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(epearls.getValBoolean() && e instanceof EntityEnderPearl){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(crystals.getValBoolean() && e instanceof EntityEnderCrystal){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(items.getValBoolean() && e instanceof EntityItem){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    if(orbs.getValBoolean() && e instanceof EntityXPOrb){
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, c.getRGB());
                    }
                    OsirisTessellator.releaseGL();
                });
    }
}
