package net.minecraft.entity.item;

import javax.annotation.Nullable;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
	private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean> createKey(EntityMinecartFurnace.class, DataSerializers.BOOLEAN);
	private int fuel;
	public double pushX;
	public double pushZ;

	public EntityMinecartFurnace(World worldIn) {
		super(worldIn);
	}

	public EntityMinecartFurnace(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189671_a(DataFixer p_189671_0_) {
		EntityMinecart.func_189669_a(p_189671_0_, "MinecartFurnace");
	}

	@Override
	public EntityMinecart.Type getType() {
		return EntityMinecart.Type.FURNACE;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(POWERED, Boolean.valueOf(false));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.fuel > 0) {
			--this.fuel;
		}

		if (this.fuel <= 0) {
			this.pushX = 0.0D;
			this.pushZ = 0.0D;
		}

		this.setMinecartPowered(this.fuel > 0);

		if (this.isMinecartPowered() && (this.rand.nextInt(4) == 0)) {
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	/**
	 * Get's the maximum speed for a minecart
	 */
	@Override
	protected double getMaximumSpeed() {
		return 0.2D;
	}

	@Override
	public void killMinecart(DamageSource source) {
		super.killMinecart(source);

		if (!source.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			this.entityDropItem(new ItemStack(Blocks.FURNACE, 1), 0.0F);
		}
	}

	@Override
	protected void moveAlongTrack(BlockPos p_180460_1_, IBlockState p_180460_2_) {
		super.moveAlongTrack(p_180460_1_, p_180460_2_);
		double d0 = (this.pushX * this.pushX) + (this.pushZ * this.pushZ);

		if ((d0 > 1.0E-4D) && (((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) > 0.001D)) {
			d0 = MathHelper.sqrt_double(d0);
			this.pushX /= d0;
			this.pushZ /= d0;

			if (((this.pushX * this.motionX) + (this.pushZ * this.motionZ)) < 0.0D) {
				this.pushX = 0.0D;
				this.pushZ = 0.0D;
			} else {
				double d1 = d0 / this.getMaximumSpeed();
				this.pushX *= d1;
				this.pushZ *= d1;
			}
		}
	}

	@Override
	protected void applyDrag() {
		double d0 = (this.pushX * this.pushX) + (this.pushZ * this.pushZ);

		if (d0 > 1.0E-4D) {
			d0 = MathHelper.sqrt_double(d0);
			this.pushX /= d0;
			this.pushZ /= d0;
			double d1 = 1.0D;
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.800000011920929D;
			this.motionX += this.pushX * 1.0D;
			this.motionZ += this.pushZ * 1.0D;
		} else {
			this.motionX *= 0.9800000190734863D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.9800000190734863D;
		}

		super.applyDrag();
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand) {
		if ((stack != null) && (stack.getItem() == Items.COAL) && ((this.fuel + 3600) <= 32000)) {
			if (!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}

			this.fuel += 3600;
		}

		this.pushX = this.posX - player.posX;
		this.pushZ = this.posZ - player.posZ;
		return true;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setDouble("PushX", this.pushX);
		compound.setDouble("PushZ", this.pushZ);
		compound.setShort("Fuel", (short) this.fuel);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.pushX = compound.getDouble("PushX");
		this.pushZ = compound.getDouble("PushZ");
		this.fuel = compound.getShort("Fuel");
	}

	protected boolean isMinecartPowered() {
		return this.dataManager.get(POWERED).booleanValue();
	}

	protected void setMinecartPowered(boolean p_94107_1_) {
		this.dataManager.set(POWERED, Boolean.valueOf(p_94107_1_));
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return (this.isMinecartPowered() ? Blocks.LIT_FURNACE : Blocks.FURNACE).getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
	}
}
