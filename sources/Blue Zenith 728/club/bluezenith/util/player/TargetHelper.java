package club.bluezenith.util.player;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.modules.combat.AntiBot;
import club.bluezenith.util.MinecraftInstance;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;

import java.util.Arrays;
import java.util.Map;

public class TargetHelper extends MinecraftInstance {
    public enum Targets {
        MOBS("Mobs", true),
        PLAYERS("Players", true),
        ANIMALS("Animals", true),
        INVISIBLE("Invisible", true),
        DEAD("Dead", false),
        TEAMS("Teams", false)
        ;
        public String displayName;
        public boolean on;
        Targets(String displayName, boolean on){
            this.displayName = displayName;
            this.on = on;
        }
    }
    public static void fromObject(Map.Entry<String, JsonElement> entry) {
        if (!entry.getValue().isJsonObject()) return;
        entry.getValue().getAsJsonObject().entrySet().forEach(target ->
            Arrays.stream(Targets.values())
                    .filter(ent -> ent.displayName.equals(target.getKey()))
                    .findFirst().ifPresent(tar -> tar.on = target.getValue().getAsBoolean())
        );
    }

    public static JsonObject getTargetsAsObject() {
        final JsonObject obj = new JsonObject();
        Arrays.stream(TargetHelper.Targets.values()).forEach(item -> obj.add(item.displayName, new JsonPrimitive(item.on)));
        return obj;
    }
    public static boolean isTarget(Entity ent) {
        final AntiBot antiBot = BlueZenith.getBlueZenith().getModuleManager().getAndCast(AntiBot.class);
        if(ent == mc.thePlayer || ent instanceof EntityArmorStand)
            return false;
        if(ent instanceof EntityLivingBase && ((EntityLivingBase) ent).getHealth() <= 0 && !Targets.DEAD.on || ent instanceof EntityLivingBase && antiBot.getState() && antiBot.bots.contains(ent))
            return false;
        if(ent instanceof EntityLivingBase){
            Team lol = ((EntityLivingBase) ent).getTeam();
            Team lel = mc.thePlayer.getTeam();
            if(lol != null && lel != null && lol.isSameTeam(lel)){
                return Targets.TEAMS.on;
            }
        }
        if(isMob(ent))
            return Targets.MOBS.on;
        if(ent instanceof EntityPlayer)
            return Targets.PLAYERS.on;
        if(isAnimal(ent))
            return Targets.ANIMALS.on;
        if(ent.isInvisible())
            return Targets.INVISIBLE.on;

        return false;
    }
    public static boolean isAnimal(Entity ent){
        return ent instanceof EntitySheep || ent instanceof EntityCow || ent instanceof EntityPig
                || ent instanceof EntityChicken || ent instanceof EntityRabbit || ent instanceof EntityHorse
                || ent instanceof EntityBat;
    }
    public static boolean isMob(Entity ent){
        return ent instanceof EntityZombie || ent instanceof EntitySkeleton
                || ent instanceof EntityVillager || ent instanceof EntitySlime
                || ent instanceof EntityCreeper || ent instanceof EntityEnderman
                || ent instanceof EntityEndermite || ent instanceof EntitySpider
                || ent instanceof EntityWitch || ent instanceof EntityWither || ent instanceof EntityBlaze;
    }
}
