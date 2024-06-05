package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.acE;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.optifine.Config;
import net.optifine.MatchBlock;
import net.optifine.RandomMobsProperties;

public class RandomMobs {
   private static Map locationProperties = new HashMap();
   private static RenderGlobal renderGlobal = null;
   private static boolean initialized = false;
   private static Random random = new Random();
   private static boolean working = false;
   public static final String j = ".png";
   public static final String d = ".properties";
   public static final String i = "textures/entity/";
   public static final String c = "mcpatcher/mob/";
   private static final String[] DEPENDANT_SUFFIXES = new String[]{"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};

   public static void entityLoaded(Entity var0, World var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 instanceof EntityLiving) {
         EntityLiving var3 = (EntityLiving)var0;
         var3.spawnPosition = var3.getPosition();
         var3.spawnBiome = var1.getBiomeGenForCoords(var3.spawnPosition);
         WorldServer var4 = Config.ag();
         if(var4 != null) {
            Entity var5 = var4.getEntityByID(var0.getEntityID());
            if(var5 instanceof EntityLiving) {
               EntityLiving var6 = (EntityLiving)var5;
               UUID var7 = var6.getUniqueID();
               long var8 = var7.getLeastSignificantBits();
               int var10 = (int)(var8 & 2147483647L);
               var3.randomMobsId = var10;
            }
         }
      }

   }

   public static void worldChanged(World var0, World var1) {
      acE[] var2 = MatchBlock.b();
      if(var1 != null) {
         List var3 = var1.getLoadedEntityList();
         int var4 = 0;
         if(var4 < var3.size()) {
            Entity var5 = (Entity)var3.get(var4);
            entityLoaded(var5, var1);
            ++var4;
         }
      }

   }

   public static ResourceLocation getTextureLocation(ResourceLocation param0) {
      // $FF: Couldn't be decompiled
   }

   private static RandomMobsProperties getProperties(ResourceLocation var0) {
      MatchBlock.b();
      String var2 = var0.getResourcePath();
      RandomMobsProperties var3 = (RandomMobsProperties)locationProperties.get(var2);
      if(var3 == null) {
         var3 = makeProperties(var0);
         locationProperties.put(var2, var3);
      }

      return var3;
   }

   private static RandomMobsProperties makeProperties(ResourceLocation var0) {
      MatchBlock.b();
      String var2 = var0.getResourcePath();
      ResourceLocation var3 = getPropertyLocation(var0);
      if(var3 != null) {
         RandomMobsProperties var5 = parseProperties(var3, var0);
         return var5;
      } else {
         ResourceLocation[] var4 = d(var0);
         return new RandomMobsProperties(var2, var4);
      }
   }

   private static RandomMobsProperties parseProperties(ResourceLocation var0, ResourceLocation var1) {
      acE[] var2 = MatchBlock.b();

      try {
         String var3 = var0.getResourcePath();
         Config.dbg("RandomMobs: " + var1.getResourcePath() + ", variants: " + var3);
         InputStream var4 = Config.getResourceStream(var0);
         Config.warn("RandomMobs properties not found: " + var3);
         return null;
      } catch (FileNotFoundException var5) {
         Config.warn("RandomMobs file not found: " + var1.getResourcePath());
         return null;
      } catch (IOException var6) {
         var6.printStackTrace();
         return null;
      }
   }

   private static ResourceLocation getPropertyLocation(ResourceLocation var0) {
      MatchBlock.b();
      ResourceLocation var2 = getMcpatcherLocation(var0);
      if(var2 == null) {
         return null;
      } else {
         String var3 = var2.getResourceDomain();
         String var4 = var2.getResourcePath();
         String var5 = var4;
         if(var4.endsWith(".png")) {
            var5 = var4.substring(0, var4.length() - ".png".length());
         }

         String var6 = var5 + ".properties";
         ResourceLocation var7 = new ResourceLocation(var3, var6);
         if(Config.hasResource(var7)) {
            return var7;
         } else {
            String var8 = getParentPath(var5);
            return null;
         }
      }
   }

   public static ResourceLocation getMcpatcherLocation(ResourceLocation var0) {
      MatchBlock.b();
      String var2 = var0.getResourcePath();
      if(!var2.startsWith("textures/entity/")) {
         return null;
      } else {
         String var3 = "mcpatcher/mob/" + var2.substring("textures/entity/".length());
         return new ResourceLocation(var0.getResourceDomain(), var3);
      }
   }

   public static ResourceLocation getLocationIndexed(ResourceLocation var0, int var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         return null;
      } else {
         String var3 = var0.getResourcePath();
         int var4 = var3.lastIndexOf(46);
         return null;
      }
   }

   private static String getParentPath(String var0) {
      MatchBlock.b();
      int var2 = 0;
      if(var2 < DEPENDANT_SUFFIXES.length) {
         String var3 = DEPENDANT_SUFFIXES[var2];
         if(var0.endsWith(var3)) {
            String var4 = var0.substring(0, var0.length() - var3.length());
            return var4;
         }

         ++var2;
      }

      return null;
   }

   private static ResourceLocation[] d(ResourceLocation var0) {
      MatchBlock.b();
      ArrayList var2 = new ArrayList();
      var2.add(var0);
      ResourceLocation var3 = getMcpatcherLocation(var0);
      return null;
   }

   public static void resetTextures() {
      locationProperties.clear();
      if(Config.isRandomMobs()) {
         initialize();
      }

   }

   private static void initialize() {
      MatchBlock.b();
      renderGlobal = Config.getRenderGlobal();
      if(renderGlobal != null) {
         initialized = true;
         ArrayList var1 = new ArrayList();
         var1.add("bat");
         var1.add("blaze");
         var1.add("cat/black");
         var1.add("cat/ocelot");
         var1.add("cat/red");
         var1.add("cat/siamese");
         var1.add("chicken");
         var1.add("cow/cow");
         var1.add("cow/mooshroom");
         var1.add("creeper/creeper");
         var1.add("enderman/enderman");
         var1.add("enderman/enderman_eyes");
         var1.add("ghast/ghast");
         var1.add("ghast/ghast_shooting");
         var1.add("iron_golem");
         var1.add("pig/pig");
         var1.add("sheep/sheep");
         var1.add("sheep/sheep_fur");
         var1.add("silverfish");
         var1.add("skeleton/skeleton");
         var1.add("skeleton/wither_skeleton");
         var1.add("slime/slime");
         var1.add("slime/magmacube");
         var1.add("snowman");
         var1.add("spider/cave_spider");
         var1.add("spider/spider");
         var1.add("spider_eyes");
         var1.add("squid");
         var1.add("villager/villager");
         var1.add("villager/butcher");
         var1.add("villager/farmer");
         var1.add("villager/librarian");
         var1.add("villager/priest");
         var1.add("villager/smith");
         var1.add("wither/wither");
         var1.add("wither/wither_armor");
         var1.add("wither/wither_invulnerable");
         var1.add("wolf/wolf");
         var1.add("wolf/wolf_angry");
         var1.add("wolf/wolf_collar");
         var1.add("wolf/wolf_tame");
         var1.add("zombie_pigman");
         var1.add("zombie/zombie");
         var1.add("zombie/zombie_villager");
         int var2 = 0;
         if(var2 < var1.size()) {
            String var3 = (String)var1.get(var2);
            String var4 = "textures/entity/" + var3 + ".png";
            ResourceLocation var5 = new ResourceLocation(var4);
            if(!Config.hasResource(var5)) {
               Config.warn("Not found: " + var5);
            }

            getProperties(var5);
            ++var2;
         }
      }

   }

   private static FileNotFoundException a(FileNotFoundException var0) {
      return var0;
   }
}
