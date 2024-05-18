package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class GlowESP extends Module {
    public GlowESP() {
        super("GlowESP", Category.RENDER, "Gives entities the glowing effect");
    }

    Setting players;
    Setting passive;
    Setting monsters;
    Setting items;
    Setting xpBottles;
    Setting crystals;

    List<Entity> entities;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(players = new Setting("Players", this, true, "GlowEspPlayers"));
        AuroraMod.getInstance().settingsManager.rSetting(passive = new Setting("Passive", this, false, "GlowEspPassive"));
        AuroraMod.getInstance().settingsManager.rSetting(monsters = new Setting("Monsters", this, false, "GlowEspMonsters"));
        AuroraMod.getInstance().settingsManager.rSetting(items = new Setting("Items", this, false, "GlowEspItems"));
        AuroraMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, false, "GlowEspCrystals"));
        AuroraMod.getInstance().settingsManager.rSetting(xpBottles = new Setting("XpBottles", this, false, "GlowEspXpBottles"));
    }

    public void onUpdate(){
        entities = mc.world.loadedEntityList.stream()
                .filter(e -> e != mc.player)
                .collect(Collectors.toList());
        entities.forEach(e -> {
            if(e instanceof EntityPlayer && players.getValBoolean())
                e.setGlowing(true);

            if(isPassive(e) && passive.getValBoolean())
                e.setGlowing(true);

            if(e instanceof EntityExpBottle && xpBottles.getValBoolean())
                e.setGlowing(true);

            if(isMonster(e) && monsters.getValBoolean())
                e.setGlowing(true);

            if(e instanceof EntityItem && items.getValBoolean())
                e.setGlowing(true);

            if(e instanceof EntityEnderCrystal && crystals.getValBoolean())
                e.setGlowing(true);
        });
    }

    public void onDisable(){
        entities.forEach(p -> p.setGlowing(false));
    }

    public static boolean isPassive(Entity e) {
        if (e instanceof EntityWolf && ((EntityWolf) e).isAngry()) return false;
        if (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid)
            return true;
        if (e instanceof EntityIronGolem && ((EntityIronGolem) e).getRevengeTarget() == null) return true;
        return false;
    }

    public static boolean isMonster(Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }

}
