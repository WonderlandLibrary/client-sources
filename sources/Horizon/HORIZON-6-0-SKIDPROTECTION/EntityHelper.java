package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Multimap;
import java.util.Map;

public final class EntityHelper
{
    private static Minecraft HorizonCode_Horizon_È;
    
    static {
        EntityHelper.HorizonCode_Horizon_È = Minecraft.áŒŠà();
    }
    
    public static int HorizonCode_Horizon_È(final Entity target) {
        final int originalSlot = EntityHelper.HorizonCode_Horizon_È.á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
        byte weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (byte slot = 0; slot < 9; ++slot) {
            EntityHelper.HorizonCode_Horizon_È.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = slot;
            final ItemStack itemStack = EntityHelper.HorizonCode_Horizon_È.á.Çª();
            if (itemStack != null) {
                float damage = HorizonCode_Horizon_È(itemStack);
                damage += EnchantmentHelper.HorizonCode_Horizon_È(itemStack, EnumCreatureAttribute.HorizonCode_Horizon_È);
                if (damage > weaponDamage) {
                    weaponDamage = damage;
                    weaponSlot = slot;
                }
            }
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }
    
    public static float[] HorizonCode_Horizon_È(final int x, final int y, final int z, final EnumFacing facing) {
        final EntitySnowball temp = new EntitySnowball(EntityHelper.HorizonCode_Horizon_È.áŒŠÆ);
        temp.ŒÏ = x + 0.5;
        temp.Çªà¢ = y + 0.5;
        temp.Ê = z + 0.5;
        final EntitySnowball entitySnowball = temp;
        entitySnowball.ŒÏ += facing.ˆÏ­().HorizonCode_Horizon_È() * 0.25;
        final EntitySnowball entitySnowball2 = temp;
        entitySnowball2.Çªà¢ += facing.ˆÏ­().Â() * 0.25;
        final EntitySnowball entitySnowball3 = temp;
        entitySnowball3.Ê += facing.ˆÏ­().Ý() * 0.25;
        return Ø­áŒŠá(temp);
    }
    
    public static float Â(final Entity entity) {
        final double deltaX = entity.ŒÏ - EntityHelper.HorizonCode_Horizon_È.á.ŒÏ;
        final double deltaZ = entity.Ê - EntityHelper.HorizonCode_Horizon_È.á.Ê;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.à(-(EntityHelper.HorizonCode_Horizon_È.á.É - (float)yawToEntity));
    }
    
    public static float Ý(final Entity entity) {
        final double deltaX = entity.ŒÏ - EntityHelper.HorizonCode_Horizon_È.á.ŒÏ;
        final double deltaZ = entity.Ê - EntityHelper.HorizonCode_Horizon_È.á.Ê;
        final double deltaY = entity.Çªà¢ - 1.6 + entity.Ðƒáƒ() - EntityHelper.HorizonCode_Horizon_È.á.Çªà¢;
        final double distanceXZ = MathHelper.HorizonCode_Horizon_È(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.à(EntityHelper.HorizonCode_Horizon_È.á.áƒ - (float)pitchToEntity);
    }
    
    public static float[] Ø­áŒŠá(final Entity e) {
        return new float[] { Â(e) + EntityHelper.HorizonCode_Horizon_È.á.É, Ý(e) + EntityHelper.HorizonCode_Horizon_È.á.áƒ };
    }
    
    public static float[] HorizonCode_Horizon_È(final EntityPlayer player, final Entity target) {
        final double var4 = target.ŒÏ - player.ŒÏ;
        final double var5 = target.Ê - player.Ê;
        final double var6 = target.Çªà¢ + target.Ðƒáƒ() / 1.3 - (player.Çªà¢ + player.Ðƒáƒ());
        final double var7 = MathHelper.HorizonCode_Horizon_È(var4 * var4 + var5 * var5);
        final float yaw = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    private float[] HorizonCode_Horizon_È(final int x, final int y, final int z) {
        final double var4 = x - EntityHelper.HorizonCode_Horizon_È.á.ŒÏ + 0.5;
        final double var5 = z - EntityHelper.HorizonCode_Horizon_È.á.Ê + 0.5;
        final double var6 = y - (EntityHelper.HorizonCode_Horizon_È.á.Çªà¢ + EntityHelper.HorizonCode_Horizon_È.á.Ðƒáƒ() - 1.0);
        final double var7 = MathHelper.HorizonCode_Horizon_È(var4 * var4 + var5 * var5);
        final float var8 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[] { var8, (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793)) };
    }
    
    private static float HorizonCode_Horizon_È(final ItemStack itemStack) {
        final Multimap multimap = itemStack.ŒÏ();
        if (!multimap.isEmpty()) {
            final Iterator iterator = multimap.entries().iterator();
            if (iterator.hasNext()) {
                final Map.Entry entry = iterator.next();
                final AttributeModifier attributeModifier = entry.getValue();
                double damage;
                if (attributeModifier.Ý() != 1 && attributeModifier.Ý() != 2) {
                    damage = attributeModifier.Ø­áŒŠá();
                }
                else {
                    damage = attributeModifier.Ø­áŒŠá() * 100.0;
                }
                if (attributeModifier.Ø­áŒŠá() > 1.0) {
                    return 1.0f + (float)damage;
                }
                return 1.0f;
            }
        }
        return 1.0f;
    }
}
