package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
	public EntitySnowball(World worldIn) {
		super(worldIn);
	}

	public EntitySnowball(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntitySnowball(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityThrowable.func_189661_a(p_189662_0_, "Snowball");
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.entityHit != null) {
			int i = 0;

			if (result.entityHit instanceof EntityBlaze) {
				i = 3;
			}

			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), i);
		}

		for (int j = 0; j < 8; ++j) {
			this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
