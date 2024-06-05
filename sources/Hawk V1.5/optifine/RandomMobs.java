package optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RandomMobs {
   private static boolean working = false;
   private static Map locationProperties = new HashMap();
   private static Random random = new Random();
   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
   private static final String[] DEPENDANT_SUFFIXES = new String[]{"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};
   private static boolean initialized = false;
   public static final String SUFFIX_PNG = ".png";
   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
   private static RenderGlobal renderGlobal = null;
   public static final String SUFFIX_PROPERTIES = ".properties";

   private static ResourceLocation getPropertyLocation(ResourceLocation var0) {
      ResourceLocation var1 = getMcpatcherLocation(var0);
      if (var1 == null) {
         return null;
      } else {
         String var2 = var1.getResourceDomain();
         String var3 = var1.getResourcePath();
         String var4 = var3;
         if (var3.endsWith(".png")) {
            var4 = var3.substring(0, var3.length() - ".png".length());
         }

         String var5 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(".properties"));
         ResourceLocation var6 = new ResourceLocation(var2, var5);
         if (Config.hasResource(var6)) {
            return var6;
         } else {
            String var7 = getParentPath(var4);
            if (var7 == null) {
               return null;
            } else {
               ResourceLocation var8 = new ResourceLocation(var2, String.valueOf((new StringBuilder(String.valueOf(var7))).append(".properties")));
               return Config.hasResource(var8) ? var8 : null;
            }
         }
      }
   }

   private static RandomMobsProperties makeProperties(ResourceLocation var0) {
      String var1 = var0.getResourcePath();
      ResourceLocation var2 = getPropertyLocation(var0);
      if (var2 != null) {
         RandomMobsProperties var3 = parseProperties(var2, var0);
         if (var3 != null) {
            return var3;
         }
      }

      ResourceLocation[] var4 = getTextureVariants(var0);
      return new RandomMobsProperties(var1, var4);
   }

   private static ResourceLocation[] getTextureVariants(ResourceLocation var0) {
      ArrayList var1 = new ArrayList();
      var1.add(var0);
      ResourceLocation var2 = getMcpatcherLocation(var0);
      if (var2 == null) {
         return null;
      } else {
         for(int var3 = 1; var3 < var1.size() + 10; ++var3) {
            int var4 = var3 + 1;
            ResourceLocation var5 = getLocationIndexed(var2, var4);
            if (Config.hasResource(var5)) {
               var1.add(var5);
            }
         }

         if (var1.size() <= 1) {
            return null;
         } else {
            ResourceLocation[] var6 = (ResourceLocation[])var1.toArray(new ResourceLocation[var1.size()]);
            Config.dbg(String.valueOf((new StringBuilder("RandomMobs: ")).append(var0.getResourcePath()).append(", variants: ").append(var6.length)));
            return var6;
         }
      }
   }

   private static RandomMobsProperties parseProperties(ResourceLocation var0, ResourceLocation var1) {
      try {
         String var2 = var0.getResourcePath();
         Config.dbg(String.valueOf((new StringBuilder("RandomMobs: ")).append(var1.getResourcePath()).append(", variants: ").append(var2)));
         InputStream var3 = Config.getResourceStream(var0);
         if (var3 == null) {
            Config.warn(String.valueOf((new StringBuilder("RandomMobs properties not found: ")).append(var2)));
            return null;
         } else {
            Properties var4 = new Properties();
            var4.load(var3);
            var3.close();
            RandomMobsProperties var5 = new RandomMobsProperties(var4, var2, var1);
            return !var5.isValid(var2) ? null : var5;
         }
      } catch (FileNotFoundException var6) {
         Config.warn(String.valueOf((new StringBuilder("RandomMobs file not found: ")).append(var1.getResourcePath())));
         return null;
      } catch (IOException var7) {
         var7.printStackTrace();
         return null;
      }
   }

   public static void worldChanged(World var0, World var1) {
      if (var1 != null) {
         List var2 = var1.getLoadedEntityList();

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            Entity var4 = (Entity)var2.get(var3);
            entityLoaded(var4, var1);
         }
      }

   }

   public static ResourceLocation getLocationIndexed(ResourceLocation var0, int var1) {
      if (var0 == null) {
         return null;
      } else {
         String var2 = var0.getResourcePath();
         int var3 = var2.lastIndexOf(46);
         if (var3 < 0) {
            return null;
         } else {
            String var4 = var2.substring(0, var3);
            String var5 = var2.substring(var3);
            String var6 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(var1).append(var5));
            ResourceLocation var7 = new ResourceLocation(var0.getResourceDomain(), var6);
            return var7;
         }
      }
   }

   public static ResourceLocation getTextureLocation(ResourceLocation var0) {
      if (working) {
         return var0;
      } else {
         ResourceLocation var7;
         try {
            working = true;
            if (!initialized) {
               initialize();
            }

            if (renderGlobal == null) {
               var7 = var0;
               return var7;
            }

            Entity var2 = renderGlobal.renderedEntity;
            if (var2 instanceof EntityLiving) {
               EntityLiving var3 = (EntityLiving)var2;
               String var4 = var0.getResourcePath();
               if (!var4.startsWith("textures/entity/")) {
                  var7 = var0;
                  return var7;
               }

               RandomMobsProperties var5 = getProperties(var0);
               if (var5 != null) {
                  ResourceLocation var1 = var5.getTextureLocation(var0, var3);
                  var7 = var1;
                  return var7;
               }

               working = false;
               return var0;
            }

            var7 = var0;
         } finally {
            working = false;
         }

         return var7;
      }
   }

   public static void entityLoaded(Entity var0, World var1) {
      if (var0 instanceof EntityLiving && var1 != null) {
         EntityLiving var2 = (EntityLiving)var0;
         var2.spawnPosition = var2.getPosition();
         var2.spawnBiome = var1.getBiomeGenForCoords(var2.spawnPosition);
         WorldServer var3 = Config.getWorldServer();
         if (var3 != null) {
            Entity var4 = var3.getEntityByID(var0.getEntityId());
            if (var4 instanceof EntityLiving) {
               EntityLiving var5 = (EntityLiving)var4;
               UUID var6 = var5.getUniqueID();
               long var7 = var6.getLeastSignificantBits();
               int var9 = (int)(var7 & 2147483647L);
               var2.randomMobsId = var9;
            }
         }
      }

   }

   public static ResourceLocation getMcpatcherLocation(ResourceLocation var0) {
      String var1 = var0.getResourcePath();
      if (!var1.startsWith("textures/entity/")) {
         return null;
      } else {
         String var2 = String.valueOf((new StringBuilder("mcpatcher/mob/")).append(var1.substring("textures/entity/".length())));
         return new ResourceLocation(var0.getResourceDomain(), var2);
      }
   }

   public static void resetTextures() {
      locationProperties.clear();
      if (Config.isRandomMobs()) {
         initialize();
      }

   }

   private static void initialize() {
      renderGlobal = Config.getRenderGlobal();
      if (renderGlobal != null) {
         initialized = true;
         ArrayList var0 = new ArrayList();
         var0.add("bat");
         var0.add("blaze");
         var0.add("cat/black");
         var0.add("cat/ocelot");
         var0.add("cat/red");
         var0.add("cat/siamese");
         var0.add("chicken");
         var0.add("cow/cow");
         var0.add("cow/mooshroom");
         var0.add("creeper/creeper");
         var0.add("enderman/enderman");
         var0.add("enderman/enderman_eyes");
         var0.add("ghast/ghast");
         var0.add("ghast/ghast_shooting");
         var0.add("iron_golem");
         var0.add("pig/pig");
         var0.add("sheep/sheep");
         var0.add("sheep/sheep_fur");
         var0.add("silverfish");
         var0.add("skeleton/skeleton");
         var0.add("skeleton/wither_skeleton");
         var0.add("slime/slime");
         var0.add("slime/magmacube");
         var0.add("snowman");
         var0.add("spider/cave_spider");
         var0.add("spider/spider");
         var0.add("spider_eyes");
         var0.add("squid");
         var0.add("villager/villager");
         var0.add("villager/butcher");
         var0.add("villager/farmer");
         var0.add("villager/librarian");
         var0.add("villager/priest");
         var0.add("villager/smith");
         var0.add("wither/wither");
         var0.add("wither/wither_armor");
         var0.add("wither/wither_invulnerable");
         var0.add("wolf/wolf");
         var0.add("wolf/wolf_angry");
         var0.add("wolf/wolf_collar");
         var0.add("wolf/wolf_tame");
         var0.add("zombie_pigman");
         var0.add("zombie/zombie");
         var0.add("zombie/zombie_villager");

         for(int var1 = 0; var1 < var0.size(); ++var1) {
            String var2 = (String)var0.get(var1);
            String var3 = String.valueOf((new StringBuilder("textures/entity/")).append(var2).append(".png"));
            ResourceLocation var4 = new ResourceLocation(var3);
            if (!Config.hasResource(var4)) {
               Config.warn(String.valueOf((new StringBuilder("Not found: ")).append(var4)));
            }

            getProperties(var4);
         }
      }

   }

   private static RandomMobsProperties getProperties(ResourceLocation var0) {
      String var1 = var0.getResourcePath();
      RandomMobsProperties var2 = (RandomMobsProperties)locationProperties.get(var1);
      if (var2 == null) {
         var2 = makeProperties(var0);
         locationProperties.put(var1, var2);
      }

      return var2;
   }

   private static String getParentPath(String var0) {
      for(int var1 = 0; var1 < DEPENDANT_SUFFIXES.length; ++var1) {
         String var2 = DEPENDANT_SUFFIXES[var1];
         if (var0.endsWith(var2)) {
            String var3 = var0.substring(0, var0.length() - var2.length());
            return var3;
         }
      }

      return null;
   }
}
