package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMagmaCube extends EntitySlime {
	public EntityMagmaCube(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
	}

	public static void func_189759_b(DataFixer p_189759_0_) {
		EntityLiving.func_189752_a(p_189759_0_, "LavaSlime");
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Checks that the entity is not colliding with any blocks / liquids
	 */
	@Override
	public boolean isNotColliding() {
		return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()
				&& !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox());
	}

	@Override
	protected void setSlimeSize(int size) {
		super.setSlimeSize(size);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(size * 3);
	}

	@Override
	public int getBrightnessForRender(float partialTicks) {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(float partialTicks) {
		return 1.0F;
	}

	@Override
	protected EnumParticleTypes getParticleType() {
		return EnumParticleTypes.FLAME;
	}

	@Override
	protected EntitySlime createInstance() {
		return new EntityMagmaCube(this.worldObj);
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return this.isSmallSlime() ? LootTableList.EMPTY : LootTableList.ENTITIES_MAGMA_CUBE;
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
	 */
	@Override
	public boolean isBurning() {
		return false;
	}

	/**
	 * Gets the amount of time the slime needs to wait between jumps.
	 */
	@Override
	protected int getJumpDelay() {
		return super.getJumpDelay() * 4;
	}

	@Override
	protected void alterSquishAmount() {
		this.squishAmount *= 0.9F;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	protected void jump() {
		this.motionY = 0.42F + (this.getSlimeSize() * 0.1F);
		this.isAirBorne = true;
	}

	@Override
	protected void handleJumpLava() {
		this.motionY = 0.22F + (this.getSlimeSize() * 0.05F);
		this.isAirBorne = true;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the slime's size)
	 */
	@Override
	protected boolean canDamagePlayer() {
		return true;
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the slime.
	 */
	@Override
	protected int getAttackStrength() {
		return super.getAttackStrength() + 2;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_HURT : SoundEvents.ENTITY_MAGMACUBE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_DEATH : SoundEvents.ENTITY_MAGMACUBE_DEATH;
	}

	@Override
	protected SoundEvent getSquishSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH : SoundEvents.ENTITY_MAGMACUBE_SQUISH;
	}

	@Override
	protected SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_MAGMACUBE_JUMP;
	}
}
