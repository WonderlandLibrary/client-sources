package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Map;

public class RandomMobs
{
    private static Map HorizonCode_Horizon_È;
    private static RenderGlobal Â;
    private static boolean Ý;
    private static Random Ø­áŒŠá;
    private static Field Âµá€;
    private static boolean Ó;
    
    static {
        RandomMobs.HorizonCode_Horizon_È = new HashMap();
        RandomMobs.Â = null;
        RandomMobs.Ý = false;
        RandomMobs.Ø­áŒŠá = new Random();
        RandomMobs.Âµá€ = HorizonCode_Horizon_È(Entity.class, UUID.class);
        RandomMobs.Ó = false;
    }
    
    public static void HorizonCode_Horizon_È(final Entity entity) {
        if (entity instanceof EntityLiving) {
            final EntityLiving el = (EntityLiving)entity;
            final WorldServer ws = Config.áˆº();
            if (ws != null) {
                final Entity es = ws.HorizonCode_Horizon_È(entity.ˆá());
                if (es instanceof EntityLiving) {
                    final EntityLiving els = (EntityLiving)es;
                    if (RandomMobs.Âµá€ != null) {
                        try {
                            final Object e = RandomMobs.Âµá€.get(els);
                            RandomMobs.Âµá€.set(el, e);
                        }
                        catch (Exception var6) {
                            var6.printStackTrace();
                            RandomMobs.Âµá€ = null;
                        }
                    }
                }
            }
        }
    }
    
    private static Field HorizonCode_Horizon_È(final Class cls, final Class fieldType) {
        try {
            final Field[] e = cls.getDeclaredFields();
            for (int i = 0; i < e.length; ++i) {
                final Field field = e[i];
                final Class type = field.getType();
                if (type == fieldType) {
                    field.setAccessible(true);
                    return field;
                }
            }
            return null;
        }
        catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }
    
    public static void HorizonCode_Horizon_È(final World oldWorld, final World newWorld) {
        if (newWorld != null) {
            final List entityList = newWorld.Ø­à();
            for (int e = 0; e < entityList.size(); ++e) {
                final Entity entity = entityList.get(e);
                HorizonCode_Horizon_È(entity);
            }
        }
    }
    
    public static ResourceLocation_1975012498 HorizonCode_Horizon_È(final ResourceLocation_1975012498 loc) {
        if (RandomMobs.Ó) {
            return loc;
        }
        ResourceLocation_1975012498 var6;
        try {
            RandomMobs.Ó = true;
            if (!RandomMobs.Ý) {
                Â();
            }
            if (RandomMobs.Â == null) {
                return loc;
            }
            final Entity entity = RandomMobs.Â.Âµá€;
            if (entity == null) {
                return loc;
            }
            if (!(entity instanceof EntityLiving)) {
                return loc;
            }
            final String name = loc.Â();
            if (!name.startsWith("textures/entity/")) {
                return loc;
            }
            final long uuidLow = entity.£áŒŠá().getLeastSignificantBits();
            final int id = (int)(uuidLow & 0x7FFFFFFFL);
            var6 = HorizonCode_Horizon_È(loc, id);
        }
        finally {
            RandomMobs.Ó = false;
        }
        RandomMobs.Ó = false;
        return var6;
    }
    
    private static ResourceLocation_1975012498 HorizonCode_Horizon_È(final ResourceLocation_1975012498 loc, final int randomId) {
        if (randomId <= 0) {
            return loc;
        }
        final String name = loc.Â();
        ResourceLocation_1975012498[] texLocs = RandomMobs.HorizonCode_Horizon_È.get(name);
        if (texLocs == null) {
            texLocs = Â(loc);
            RandomMobs.HorizonCode_Horizon_È.put(name, texLocs);
        }
        if (texLocs != null && texLocs.length > 0) {
            final int index = randomId % texLocs.length;
            final ResourceLocation_1975012498 texLoc = texLocs[index];
            return texLoc;
        }
        return loc;
    }
    
    private static ResourceLocation_1975012498[] Â(final ResourceLocation_1975012498 loc) {
        TextureUtils.HorizonCode_Horizon_È(loc);
        ResourceLocation_1975012498[] texLocs = new ResourceLocation_1975012498[0];
        final String name = loc.Â();
        final int pointPos = name.lastIndexOf(46);
        if (pointPos < 0) {
            return texLocs;
        }
        String prefix = name.substring(0, pointPos);
        final String suffix = name.substring(pointPos);
        final String texEntStr = "textures/entity/";
        if (!prefix.startsWith(texEntStr)) {
            return texLocs;
        }
        prefix = prefix.substring(texEntStr.length());
        prefix = "mcpatcher/mob/" + prefix;
        final int countVariants = HorizonCode_Horizon_È(prefix, suffix);
        if (countVariants <= 1) {
            return texLocs;
        }
        texLocs = new ResourceLocation_1975012498[countVariants];
        texLocs[0] = loc;
        for (int i = 1; i < texLocs.length; ++i) {
            final int texNum = i + 1;
            final String texName = String.valueOf(prefix) + texNum + suffix;
            TextureUtils.HorizonCode_Horizon_È(texLocs[i] = new ResourceLocation_1975012498(loc.Ý(), texName));
        }
        Config.HorizonCode_Horizon_È("RandomMobs: " + loc + ", variants: " + texLocs.length);
        return texLocs;
    }
    
    private static int HorizonCode_Horizon_È(final String prefix, final String suffix) {
        final short maxNum = 1000;
        for (int num = 2; num < maxNum; ++num) {
            final String variant = String.valueOf(prefix) + num + suffix;
            final ResourceLocation_1975012498 loc = new ResourceLocation_1975012498(variant);
            if (!Config.Ý(loc)) {
                return num - 1;
            }
        }
        return maxNum;
    }
    
    public static void HorizonCode_Horizon_È() {
        RandomMobs.HorizonCode_Horizon_È.clear();
        if (Config.áˆºÇŽØ()) {
            Â();
        }
    }
    
    private static void Â() {
        RandomMobs.Â = Config.Ä();
        if (RandomMobs.Â != null) {
            RandomMobs.Ý = true;
            final ArrayList list = new ArrayList();
            list.add("bat");
            list.add("blaze");
            list.add("cat/black");
            list.add("cat/ocelot");
            list.add("cat/red");
            list.add("cat/siamese");
            list.add("chicken");
            list.add("cow/cow");
            list.add("cow/mooshroom");
            list.add("creeper/creeper");
            list.add("enderman/enderman");
            list.add("enderman/enderman_eyes");
            list.add("ghast/ghast");
            list.add("ghast/ghast_shooting");
            list.add("iron_golem");
            list.add("pig/pig");
            list.add("sheep/sheep");
            list.add("sheep/sheep_fur");
            list.add("silverfish");
            list.add("skeleton/skeleton");
            list.add("skeleton/wither_skeleton");
            list.add("slime/slime");
            list.add("slime/magmacube");
            list.add("snowman");
            list.add("spider/cave_spider");
            list.add("spider/spider");
            list.add("spider_eyes");
            list.add("squid");
            list.add("villager/villager");
            list.add("villager/butcher");
            list.add("villager/farmer");
            list.add("villager/librarian");
            list.add("villager/priest");
            list.add("villager/smith");
            list.add("wither/wither");
            list.add("wither/wither_armor");
            list.add("wither/wither_invulnerable");
            list.add("wolf/wolf");
            list.add("wolf/wolf_angry");
            list.add("wolf/wolf_collar");
            list.add("wolf/wolf_tame");
            list.add("zombie_pigman");
            list.add("zombie/zombie");
            list.add("zombie/zombie_villager");
            for (int i = 0; i < list.size(); ++i) {
                final String name = list.get(i);
                final String tex = "textures/entity/" + name + ".png";
                final ResourceLocation_1975012498 texLoc = new ResourceLocation_1975012498(tex);
                if (!Config.Ý(texLoc)) {
                    Config.Â("Not found: " + texLoc);
                }
                HorizonCode_Horizon_È(texLoc, 100);
            }
        }
    }
}
