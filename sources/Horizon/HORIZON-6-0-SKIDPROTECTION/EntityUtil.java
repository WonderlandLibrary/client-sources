package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil
{
    public static Map<Entity, List<Vec3>> HorizonCode_Horizon_È;
    
    static {
        EntityUtil.HorizonCode_Horizon_È = new HashMap<Entity, List<Vec3>>();
    }
    
    public static void HorizonCode_Horizon_È() {
        EntityUtil.HorizonCode_Horizon_È.clear();
        if (!Horizon.à¢.áˆºÏ.Â("esp").áˆºÑ¢Õ() && !Horizon.à¢.áˆºÏ.Â("nametags").áˆºÑ¢Õ()) {
            return;
        }
        for (final Object object : Minecraft.áŒŠà().áŒŠÆ.Ø­à()) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)object;
                final Vec3 position = Â(entity);
                AxisAlignedBB bounding = entity.£É();
                bounding = bounding.Ý(-Minecraft.áŒŠà().ÇªÓ().ÂµÈ, -Minecraft.áŒŠà().ÇªÓ().á, -Minecraft.áŒŠà().ÇªÓ().ˆÏ­);
                final double ax = (bounding.Ø­áŒŠá - bounding.HorizonCode_Horizon_È) / 2.0;
                final double ay = bounding.Âµá€ - bounding.Â;
                final double az = (bounding.Ó - bounding.Ý) / 2.0;
                final List<Vec3> bounds = new ArrayList<Vec3>();
                bounds.add(position);
                bounds.add(position.Â(0.0, entity.£ÂµÄ / 2.0f, 0.0));
                bounds.add(position.Â(0.0, entity.Ðƒáƒ(), 0.0));
                bounds.add(position.Â(0.0, entity.£ÂµÄ, 0.0));
                bounds.add(position.Â(0.0, entity.£ÂµÄ + 0.2, 0.0));
                bounds.add(position.Â(ax, ay, az));
                bounds.add(position.Â(ax, ay, -az));
                bounds.add(position.Â(-ax, ay, az));
                bounds.add(position.Â(-ax, ay, -az));
                bounds.add(position.Â(ax, 0.0, az));
                bounds.add(position.Â(ax, 0.0, -az));
                bounds.add(position.Â(-ax, 0.0, az));
                bounds.add(position.Â(-ax, 0.0, -az));
                final List<Vec3> data = new ArrayList<Vec3>();
                for (int i = 0; i < bounds.size(); ++i) {
                    final Vec3 coords = MathUtil.HorizonCode_Horizon_È(bounds.get(i));
                    if (coords != null) {
                        data.add(coords);
                    }
                }
                EntityUtil.HorizonCode_Horizon_È.put(entity, data);
            }
        }
    }
    
    public static Vec3 HorizonCode_Horizon_È(final Entity entity) {
        final double partial = Minecraft.áŒŠà().Ø.Âµá€;
        final double x = entity.áˆºáˆºÈ + (entity.ŒÏ - entity.áˆºáˆºÈ) * partial;
        final double y = entity.ÇŽá€ + (entity.Çªà¢ - entity.ÇŽá€) * partial;
        final double z = entity.Ï + (entity.Ê - entity.Ï) * partial;
        return new Vec3(x, y, z);
    }
    
    public static Vec3 Â(final Entity entity) {
        final double partial = Minecraft.áŒŠà().Ø.Ý;
        final double x = entity.áˆºáˆºÈ + (entity.ŒÏ - entity.áˆºáˆºÈ) * partial - Minecraft.áŒŠà().ÇªÓ().ÂµÈ;
        final double y = entity.ÇŽá€ + (entity.Çªà¢ - entity.ÇŽá€) * partial - Minecraft.áŒŠà().ÇªÓ().á;
        final double z = entity.Ï + (entity.Ê - entity.Ï) * partial - Minecraft.áŒŠà().ÇªÓ().ˆÏ­;
        return new Vec3(x, y, z);
    }
    
    public static Vec3 HorizonCode_Horizon_È(final TileEntity entity) {
        final double x = entity.á().HorizonCode_Horizon_È() - Minecraft.áŒŠà().ÇªÓ().ÂµÈ;
        final double y = entity.á().Â() - Minecraft.áŒŠà().ÇªÓ().á;
        final double z = entity.á().Ý() - Minecraft.áŒŠà().ÇªÓ().ˆÏ­;
        return new Vec3(x, y, z);
    }
    
    public static double[] Ý(final Entity entity) {
        final double pX = Minecraft.áŒŠà().á.ŒÏ;
        final double pY = Minecraft.áŒŠà().á.Çªà¢ + Minecraft.áŒŠà().á.Ðƒáƒ();
        final double pZ = Minecraft.áŒŠà().á.Ê;
        final double eX = entity.ŒÏ;
        final double eY = entity.Çªà¢ + entity.£ÂµÄ / 2.0f;
        final double eZ = entity.Ê;
        final double dX = pX - eX;
        final double dY = pY - eY;
        final double dZ = pZ - eZ;
        final double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        final double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        final double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[] { yaw, 90.0 - pitch };
    }
    
    public static double HorizonCode_Horizon_È(final double[] rotation) {
        return Math.sqrt(Math.pow(Math.abs(MathUtil.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.É % 360.0f, rotation[0])), 2.0) + Math.pow(Math.abs(MathUtil.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.áƒ, rotation[1])), 2.0));
    }
    
    public static boolean Ø­áŒŠá(final Entity entity) {
        return entity instanceof EntityAnimal;
    }
    
    public static boolean Âµá€(final Entity entity) {
        return entity instanceof IMob || entity instanceof EntityDragon || entity instanceof EntityGolem;
    }
    
    public static boolean Ó(final Entity entity) {
        return entity instanceof EntityBat || entity instanceof EntitySquid || entity instanceof EntityVillager;
    }
    
    public static boolean à(final Entity entity) {
        return entity instanceof IProjectile || entity instanceof EntityFishHook;
    }
    
    public static String Ø(final Entity entity) {
        String type = null;
        if (Ø­áŒŠá(entity)) {
            type = "animals";
        }
        else if (Âµá€(entity)) {
            type = "monsters";
        }
        else if (Ó(entity)) {
            type = "neutrals";
        }
        else if (à(entity)) {
            type = "projectile";
        }
        else if (entity instanceof EntityPlayer) {
            type = "players";
        }
        return type;
    }
    
    public static String Â(final TileEntity entity) {
        String type = null;
        final Block block = entity.ˆÏ­();
        if (block == Blocks.ˆáƒ || block == Blocks.ÇŽ) {
            type = "chests";
        }
        else if (block == Blocks.¥áŒŠà) {
            type = "enderchests";
        }
        else if (block == Blocks.ÇªÓ) {
            type = "mobspawners";
        }
        else if (block == Blocks.£Ó || block == Blocks.ˆÐƒØ­à || block == Blocks.Ñ¢á || block == Blocks.áŒŠÓ || block == Blocks.áˆºÉ) {
            type = "other";
        }
        return type;
    }
}
