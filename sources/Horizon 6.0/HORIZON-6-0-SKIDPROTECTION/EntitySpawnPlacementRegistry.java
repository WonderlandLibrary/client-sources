package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Maps;
import java.util.HashMap;

public class EntitySpawnPlacementRegistry
{
    private static final HashMap HorizonCode_Horizon_È;
    private static final String Â = "CL_00002254";
    
    static {
        (HorizonCode_Horizon_È = Maps.newHashMap()).put(EntityBat.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityChicken.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityCow.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityHorse.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityMooshroom.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityOcelot.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityPig.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityRabbit.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySheep.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySnowman.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySquid.class, EntityLiving.HorizonCode_Horizon_È.Ý);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityIronGolem.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityWolf.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityVillager.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityDragon.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityWither.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityBlaze.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityCaveSpider.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityCreeper.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityEnderman.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityEndermite.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityGhast.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityGiantZombie.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityGuardian.class, EntityLiving.HorizonCode_Horizon_È.Ý);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityMagmaCube.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityPigZombie.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySilverfish.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySkeleton.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySlime.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntitySpider.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityWitch.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.put(EntityZombie.class, EntityLiving.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    public static EntityLiving.HorizonCode_Horizon_È HorizonCode_Horizon_È(final Class p_180109_0_) {
        return EntitySpawnPlacementRegistry.HorizonCode_Horizon_È.get(p_180109_0_);
    }
}
