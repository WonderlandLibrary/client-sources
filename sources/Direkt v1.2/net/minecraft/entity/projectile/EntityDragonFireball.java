package net.minecraft.entity.projectile;

import java.util.List;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDragonFireball extends EntityFireball {
	public EntityDragonFireball(World worldIn) {
		super(worldIn);
		this.setSize(0.3125F, 0.3125F);
	}

	public EntityDragonFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(worldIn, x, y, z, accelX, accelY, accelZ);
		this.setSize(0.3125F, 0.3125F);
	}

	public EntityDragonFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		super(worldIn, shooter, accelX, accelY, accelZ);
		this.setSize(0.3125F, 0.3125F);
	}

	public static void func_189747_a(DataFixer p_189747_0_) {
		EntityFireball.func_189743_a(p_189747_0_, "DragonFireball");
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.worldObj.isRemote) {
			List<EntityLivingBase> list = this.worldObj.<EntityLivingBase> getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D));
			EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.worldObj, this.posX, this.posY, this.posZ);
			entityareaeffectcloud.setOwner(this.shootingEntity);
			entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
			entityareaeffectcloud.setRadius(3.0F);
			entityareaeffectcloud.setDuration(2400);
			entityareaeffectcloud.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
			entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));

			if (!list.isEmpty()) {
				for (EntityLivingBase entitylivingbase : list) {
					double d0 = this.getDistanceSqToEntity(entitylivingbase);

					if (d0 < 16.0D) {
						entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
						break;
					}
				}
			}

			this.worldObj.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
			this.worldObj.spawnEntityInWorld(entityareaeffectcloud);
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

	@Override
	protected EnumParticleTypes getParticleType() {
		return EnumParticleTypes.DRAGON_BREATH;
	}

	@Override
	protected boolean isFireballFiery() {
		return false;
	}
}
