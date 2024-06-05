package net.minecraft.entity;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;

public class EntitySpawnPlacementRegistry
{
    private static final HashMap field_180110_a;
    
    public static EntityLiving.SpawnPlacementType func_180109_a(final Class p_180109_0_) {
        return EntitySpawnPlacementRegistry.field_180110_a.get(p_180109_0_);
    }
    
    static {
        (field_180110_a = Maps.newHashMap()).put(EntityBat.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityChicken.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityCow.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityHorse.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityMooshroom.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityOcelot.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityPig.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityRabbit.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySheep.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySnowman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySquid.class, EntityLiving.SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityIronGolem.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityWolf.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityVillager.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityDragon.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityWither.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityBlaze.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityCaveSpider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityEnderman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityEndermite.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityGhast.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityGiantZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityGuardian.class, EntityLiving.SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityMagmaCube.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityPigZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySilverfish.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySkeleton.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySlime.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntitySpider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityWitch.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        EntitySpawnPlacementRegistry.field_180110_a.put(EntityZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
    }
}
