package net.SliceClient.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class ModeUtils
{
  public static String speedMode = "vhop";
  public static String phaseMode = "skip";
  public static String laggerMode = "soundbreaker";
  public static String longJumpMode = "medium";
  public static boolean espP = true;
  public static boolean espM = false;
  public static boolean espA = false;
  public static boolean tracerP = true;
  public static boolean tracerM = false;
  public static boolean tracerA = false;
  public static boolean bHit = true;
  public static boolean auraP = true;
  public static boolean auraM = false;
  public static boolean auraA = false;
  
  public ModeUtils() {}
  
  public static boolean isValidForESP(Entity ent) { Minecraft.getMinecraft(); if (ent == Minecraft.thePlayer) {
      return false;
    }
    if ((ent instanceof EntityPlayer)) {
      return espP;
    }
    if (((ent instanceof EntityMob)) || ((ent instanceof EntitySlime))) {
      return espM;
    }
    return (((ent instanceof EntityCreature)) || ((ent instanceof EntitySquid)) || ((ent instanceof EntityBat)) || 
      ((ent instanceof EntityVillager))) && (espA);
  }
  
  public static boolean isValidForTracers(Entity entity)
  {
    Minecraft.getMinecraft(); if (entity == Minecraft.thePlayer) {
      return false;
    }
    if ((entity instanceof EntityPlayer)) {
      return tracerP;
    }
    if (((entity instanceof EntityMob)) || ((entity instanceof EntitySlime))) {
      return tracerM;
    }
    return (((entity instanceof EntityCreature)) || ((entity instanceof EntitySquid)) || ((entity instanceof EntityBat)) || 
      ((entity instanceof EntityVillager))) && (tracerA);
  }
}
