package net.minecraft.src;

import java.io.*;
import java.util.*;

public class RandomMobs
{
    private static Map textureVariantsMap;
    private static RenderGlobal renderGlobal;
    private static boolean initialized;
    private static Random random;
    private static boolean working;
    
    static {
        RandomMobs.textureVariantsMap = new HashMap();
        RandomMobs.renderGlobal = null;
        RandomMobs.initialized = false;
        RandomMobs.random = new Random();
        RandomMobs.working = false;
    }
    
    public static void entityLoaded(final Entity var0) {
        if (var0 instanceof EntityLiving && !(var0 instanceof EntityPlayer)) {
            final EntityLiving var = (EntityLiving)var0;
            final WorldServer var2 = Config.getWorldServer();
            if (var2 != null) {
                final Entity var3 = var2.getEntityByID(var0.entityId);
                if (var3 instanceof EntityLiving) {
                    final EntityLiving var4 = (EntityLiving)var3;
                    final int var5 = var4.persistentId;
                    var.persistentId = var5;
                }
            }
        }
    }
    
    public static void worldChanged(final World var0, final World var1) {
        if (var1 != null) {
            final List var2 = var1.getLoadedEntityList();
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final Entity var4 = var2.get(var3);
                entityLoaded(var4);
            }
        }
    }
    
    public static String getTexture(final String var0) {
        if (RandomMobs.working) {
            return var0;
        }
        String var2;
        try {
            RandomMobs.working = true;
            if (!RandomMobs.initialized) {
                initialize();
            }
            if (RandomMobs.renderGlobal == null) {
                return var0;
            }
            final Entity var = RandomMobs.renderGlobal.renderedEntity;
            if (var == null) {
                var2 = var0;
                return var2;
            }
            if (var instanceof EntityLiving) {
                final EntityLiving var3 = (EntityLiving)var;
                if (!var0.startsWith("/mob/")) {
                    return var0;
                }
                final String var4 = getTexture(var0, var3.persistentId);
                return var4;
            }
            else {
                var2 = var0;
            }
        }
        finally {
            RandomMobs.working = false;
        }
        RandomMobs.working = false;
        return var2;
    }
    
    private static String getTexture(final String var0, final int var1) {
        if (var1 <= 0) {
            return var0;
        }
        String[] var2 = RandomMobs.textureVariantsMap.get(var0);
        if (var2 == null) {
            var2 = getTextureVariants(var0);
            RandomMobs.textureVariantsMap.put(var0, var2);
        }
        if (var2 != null && var2.length > 0) {
            final int var3 = var1 % var2.length;
            final String var4 = var2[var3];
            return var4;
        }
        return var0;
    }
    
    private static String[] getTextureVariants(final String var0) {
        final RenderEngine var = Config.getRenderEngine();
        var.getTexture(var0);
        String[] var2 = new String[0];
        final int var3 = var0.lastIndexOf(46);
        if (var3 < 0) {
            return var2;
        }
        final String var4 = var0.substring(0, var3);
        final String var5 = var0.substring(var3);
        final int var6 = getCountTextureVariants(var0, var4, var5);
        if (var6 <= 1) {
            return var2;
        }
        var2 = new String[var6];
        var2[0] = var0;
        for (int var7 = 1; var7 < var2.length; ++var7) {
            final int var8 = var7 + 1;
            final String var9 = String.valueOf(var4) + var8 + var5;
            var.getTexture(var2[var7] = var9);
        }
        Config.dbg("RandomMobs: " + var0 + ", variants: " + var2.length);
        return var2;
    }
    
    private static int getCountTextureVariants(final String var0, final String var1, final String var2) {
        final RenderEngine var3 = Config.getRenderEngine();
        final short var4 = 1000;
        for (int var5 = 2; var5 < var4; ++var5) {
            final String var6 = String.valueOf(var1) + var5 + var2;
            try {
                final InputStream var7 = var3.texturePack.getSelectedTexturePack().getResourceAsStream(var6);
                if (var7 == null) {
                    return var5 - 1;
                }
                var7.close();
            }
            catch (IOException var8) {
                return var5 - 1;
            }
        }
        return var4;
    }
    
    public static void resetTextures() {
        RandomMobs.textureVariantsMap.clear();
        if (Config.isRandomMobs()) {
            initialize();
        }
    }
    
    private static void initialize() {
        RandomMobs.renderGlobal = Config.getRenderGlobal();
        if (RandomMobs.renderGlobal != null) {
            RandomMobs.initialized = true;
            final ArrayList var0 = new ArrayList();
            var0.add("bat");
            var0.add("cat_black");
            var0.add("cat_red");
            var0.add("cat_siamese");
            var0.add("cavespider");
            var0.add("chicken");
            var0.add("cow");
            var0.add("creeper");
            var0.add("enderman");
            var0.add("enderman_eyes");
            var0.add("fire");
            var0.add("ghast");
            var0.add("ghast_fire");
            var0.add("lava");
            var0.add("ozelot");
            var0.add("pig");
            var0.add("pigman");
            var0.add("pigzombie");
            var0.add("redcow");
            var0.add("saddle");
            var0.add("sheep");
            var0.add("sheep_fur");
            var0.add("silverfish");
            var0.add("skeleton");
            var0.add("skeleton_wither");
            var0.add("slime");
            var0.add("snowman");
            var0.add("spider");
            var0.add("spider_eyes");
            var0.add("squid");
            var0.add("villager");
            var0.add("villager_golem");
            var0.add("wither");
            var0.add("wither_invul");
            var0.add("wolf");
            var0.add("wolf_angry");
            var0.add("wolf_collar");
            var0.add("wolf_tame");
            var0.add("zombie");
            var0.add("zombie_villager");
            for (int var2 = 0; var2 < var0.size(); ++var2) {
                final String var3 = var0.get(var2);
                final String var4 = "/mob/" + var3 + ".png";
                getTexture(var4, 100);
            }
        }
    }
}
