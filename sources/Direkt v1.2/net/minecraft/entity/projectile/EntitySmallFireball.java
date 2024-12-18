package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {
	public EntitySmallFireball(World worldIn) {
		super(worldIn);
		this.setSize(0.3125F, 0.3125F);
	}

	public EntitySmallFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		super(worldIn, shooter, accelX, accelY, accelZ);
		this.setSize(0.3125F, 0.3125F);
	}

	public EntitySmallFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(worldIn, x, y, z, accelX, accelY, accelZ);
		this.setSize(0.3125F, 0.3125F);
	}

	public static void func_189745_a(DataFixer p_189745_0_) {
		EntityFireball.func_189743_a(p_189745_0_, "SmallFireball");
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.worldObj.isRemote) {
			if (result.entityHit != null) {
				if (!result.entityHit.isImmuneToFire()) {
					boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F);

					if (flag) {
						this.applyEnchantments(this.shootingEntity, result.entityHit);
						result.entityHit.setFire(5);
					}
				}
			} else {
				boolean flag1 = true;

				if ((this.shootingEntity != null) && (this.shootingEntity instanceof EntityLiving)) {
					flag1 = this.worldObj.getGameRules().getBoolean("mobGriefing");
				}

				if (flag1) {
					BlockPos blockpos = result.getBlockPos().offset(result.sideHit);

					if (this.worldObj.isAirBlock(blockpos)) {
						this.worldObj.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
					}
				}
			}

			this.setDead();
		}
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}
}
