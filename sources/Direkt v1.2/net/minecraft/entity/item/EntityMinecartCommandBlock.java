package net.minecraft.entity.item;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
	private static final DataParameter<String> COMMAND = EntityDataManager.<String> createKey(EntityMinecartCommandBlock.class, DataSerializers.STRING);
	private static final DataParameter<ITextComponent> LAST_OUTPUT = EntityDataManager.<ITextComponent> createKey(EntityMinecartCommandBlock.class, DataSerializers.TEXT_COMPONENT);
	private final CommandBlockBaseLogic commandBlockLogic = new CommandBlockBaseLogic() {
		@Override
		public void updateCommand() {
			EntityMinecartCommandBlock.this.getDataManager().set(EntityMinecartCommandBlock.COMMAND, this.getCommand());
			EntityMinecartCommandBlock.this.getDataManager().set(EntityMinecartCommandBlock.LAST_OUTPUT, this.getLastOutput());
		}

		@Override
		public int getCommandBlockType() {
			return 1;
		}

		@Override
		public void fillInInfo(ByteBuf buf) {
			buf.writeInt(EntityMinecartCommandBlock.this.getEntityId());
		}

		@Override
		public BlockPos getPosition() {
			return new BlockPos(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY + 0.5D, EntityMinecartCommandBlock.this.posZ);
		}

		@Override
		public Vec3d getPositionVector() {
			return new Vec3d(EntityMinecartCommandBlock.this.posX, EntityMinecartCommandBlock.this.posY, EntityMinecartCommandBlock.this.posZ);
		}

		@Override
		public World getEntityWorld() {
			return EntityMinecartCommandBlock.this.worldObj;
		}

		@Override
		public Entity getCommandSenderEntity() {
			return EntityMinecartCommandBlock.this;
		}

		@Override
		public MinecraftServer getServer() {
			return EntityMinecartCommandBlock.this.worldObj.getMinecraftServer();
		}
	};

	/** Cooldown before command block logic runs again in ticks */
	private int activatorRailCooldown;

	public EntityMinecartCommandBlock(World worldIn) {
		super(worldIn);
	}

	public EntityMinecartCommandBlock(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189670_a(DataFixer p_189670_0_) {
		EntityMinecart.func_189669_a(p_189670_0_, "MinecartCommandBlock");
		p_189670_0_.registerWalker(FixTypes.ENTITY, new IDataWalker() {
			@Override
			public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
				if ("MinecartCommandBlock".equals(compound.getString("id"))) {
					compound.setString("id", "Control");
					fixer.process(FixTypes.BLOCK_ENTITY, compound, versionIn);
					compound.setString("id", "MinecartCommandBlock");
				}

				return compound;
			}
		});
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(COMMAND, "");
		this.getDataManager().register(LAST_OUTPUT, new TextComponentString(""));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.commandBlockLogic.readDataFromNBT(compound);
		this.getDataManager().set(COMMAND, this.getCommandBlockLogic().getCommand());
		this.getDataManager().set(LAST_OUTPUT, this.getCommandBlockLogic().getLastOutput());
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		this.commandBlockLogic.func_189510_a(compound);
	}

	@Override
	public EntityMinecart.Type getType() {
		return EntityMinecart.Type.COMMAND_BLOCK;
	}

	@Override
	public IBlockState getDefaultDisplayTile() {
		return Blocks.COMMAND_BLOCK.getDefaultState();
	}

	public CommandBlockBaseLogic getCommandBlockLogic() {
		return this.commandBlockLogic;
	}

	/**
	 * Called every tick the minecart is on an activator rail.
	 */
	@Override
	public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
		if (receivingPower && ((this.ticksExisted - this.activatorRailCooldown) >= 4)) {
			this.getCommandBlockLogic().trigger(this.worldObj);
			this.activatorRailCooldown = this.ticksExisted;
		}
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand) {
		this.commandBlockLogic.tryOpenEditCommandBlock(player);
		return false;
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);

		if (LAST_OUTPUT.equals(key)) {
			try {
				this.commandBlockLogic.setLastOutput(this.getDataManager().get(LAST_OUTPUT));
			} catch (Throwable var3) {
				;
			}
		} else if (COMMAND.equals(key)) {
			this.commandBlockLogic.setCommand(this.getDataManager().get(COMMAND));
		}
	}

	@Override
	public boolean ignoreItemEntityData() {
		return true;
	}
}
