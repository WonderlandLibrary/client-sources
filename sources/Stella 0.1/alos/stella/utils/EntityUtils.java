/*
 * LiquidBounce+ Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/WYSI-Foundation/LiquidBouncePlus/
 */
package alos.stella.utils;

import alos.stella.utils.MinecraftInstance;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;


public final class EntityUtils extends MinecraftInstance {

    public static boolean targetInvisible = false;
    public static boolean targetPlayer = true;
    public static boolean targetMobs = true;
    public static boolean targetAnimals = false;
    public static boolean targetDead = false;



    public static boolean isAnimal(final Entity entity) {
        return entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityGolem ||
                entity instanceof EntityBat;
    }

    public static boolean isMob(final Entity entity) {
        return entity instanceof EntityMob || entity instanceof EntityVillager || entity instanceof EntitySlime ||
                entity instanceof EntityGhast || entity instanceof EntityDragon;
    }

    public static String getName(final NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() :
                ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    public static int getPing(final EntityPlayer entityPlayer) {
        if(entityPlayer == null)
            return 0;

        final NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(entityPlayer.getUniqueID());

        return networkPlayerInfo == null ? 0 : networkPlayerInfo.getResponseTime();
    }
    public static boolean isRendered(Entity entityToCheck){
        return mc.theWorld != null && mc.theWorld.getLoadedEntityList().contains(entityToCheck);
    }


}