package net.minecraft.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map < String, Class <? extends TileEntity >> nameToClassMap = Maps. < String, Class <? extends TileEntity >> newHashMap();
    private static final Map < Class <? extends TileEntity > , String > classToNameMap = Maps. < Class <? extends TileEntity > , String > newHashMap();

    /** the instance of the world the tile entity is in. */
    protected World worldObj;
    protected BlockPos pos = BlockPos.ORIGIN;
    protected boolean tileEntityInvalid;
    private int blockMetadata = -1;

    /** the Block type that this TileEntity is contained within */
    protected Block blockType;

    /**
     * Adds a new two-way mapping between the class and its string name in both hashmaps.
     */
    private static void addMapping(Class <? extends TileEntity > cl, String id)
    {
        if (nameToClassMap.containsKey(id))
        {
            throw new IllegalArgumentException("Duplicate id: " + id);
        }
        else
        {
            nameToClassMap.put(id, cl);
            classToNameMap.put(cl, id);
        }
    }

    /**
     * Returns the worldObj for this tileEntity.
     */
    public World getWorld()
    {
        return this.worldObj;
    }

    /**
     * Sets the worldObj for this tileEntity.
     */
    public void setWorldObj(World worldIn)
    {
        this.worldObj = worldIn;
    }

    /**
     * Returns true if the worldObj isn't null.
     */
    public boolean hasWorldObj()
    {
        return this.worldObj != null;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        this.pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
    }

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        return this.func_189516_d(p_189515_1_);
    }

    private NBTTagCompound func_189516_d(NBTTagCompound p_189516_1_)
    {
        String s = (String)classToNameMap.get(this.getClass());

        if (s == null)
        {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        else
        {
            p_189516_1_.setString("id", s);
            p_189516_1_.setInteger("x", this.pos.getX());
            p_189516_1_.setInteger("y", this.pos.getY());
            p_189516_1_.setInteger("z", this.pos.getZ());
            return p_189516_1_;
        }
    }

    public static TileEntity func_190200_a(World p_190200_0_, NBTTagCompound p_190200_1_)
    {
        TileEntity tileentity = null;
        String s = p_190200_1_.getString("id");

        try
        {
            Class <? extends TileEntity > oclass = (Class)nameToClassMap.get(s);

            if (oclass != null)
            {
                tileentity = (TileEntity)oclass.newInstance();
            }
        }
        catch (Throwable throwable1)
        {
            LOGGER.error("Failed to create block entity {}", new Object[] {s, throwable1});
        }

        if (tileentity != null)
        {
            try
            {
                tileentity.func_190201_b(p_190200_0_);
                tileentity.readFromNBT(p_190200_1_);
            }
            catch (Throwable throwable)
            {
                LOGGER.error("Failed to load data for block entity {}", new Object[] {s, throwable});
                tileentity = null;
            }
        }
        else
        {
            LOGGER.warn("Skipping BlockEntity with id {}", new Object[] {s});
        }

        return tileentity;
    }

    protected void func_190201_b(World p_190201_1_)
    {
    }

    public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
        }

        return this.blockMetadata;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty()
    {
        if (this.worldObj != null)
        {
            IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
            this.worldObj.markChunkDirty(this.pos, this);

            if (this.getBlockType() != Blocks.AIR)
            {
                this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
            }
        }
    }

    /**
     * Returns the square of the distance between this entity and the passed in coordinates.
     */
    public double getDistanceSq(double x, double y, double z)
    {
        double d0 = (double)this.pos.getX() + 0.5D - x;
        double d1 = (double)this.pos.getY() + 0.5D - y;
        double d2 = (double)this.pos.getZ() + 0.5D - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double getMaxRenderDistanceSquared()
    {
        return 4096.0D;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    /**
     * Gets the block type at the location of this entity (client-only).
     */
    public Block getBlockType()
    {
        if (this.blockType == null && this.worldObj != null)
        {
            this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
        }

        return this.blockType;
    }

    @Nullable
    public SPacketUpdateTileEntity func_189518_D_()
    {
        return null;
    }

    public NBTTagCompound func_189517_E_()
    {
        return this.func_189516_d(new NBTTagCompound());
    }

    public boolean isInvalid()
    {
        return this.tileEntityInvalid;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        this.tileEntityInvalid = true;
    }

    /**
     * validates a tile entity
     */
    public void validate()
    {
        this.tileEntityInvalid = false;
    }

    public boolean receiveClientEvent(int id, int type)
    {
        return false;
    }

    public void updateContainingBlockInfo()
    {
        this.blockType = null;
        this.blockMetadata = -1;
    }

    public void addInfoToCrashReport(CrashReportCategory reportCategory)
    {
        reportCategory.func_189529_a("Name", new ICrashReportDetail<String>()
        {
            public String call() throws Exception
            {
                return (String)TileEntity.classToNameMap.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
            }
        });

        if (this.worldObj != null)
        {
            CrashReportCategory.addBlockInfo(reportCategory, this.pos, this.getBlockType(), this.getBlockMetadata());
            reportCategory.func_189529_a("Actual block type", new ICrashReportDetail<String>()
            {
                public String call() throws Exception
                {
                    int i = Block.getIdFromBlock(TileEntity.this.worldObj.getBlockState(TileEntity.this.pos).getBlock());

                    try
                    {
                        return String.format("ID #%d (%s // %s)", new Object[] {Integer.valueOf(i), Block.getBlockById(i).getUnlocalizedName(), Block.getBlockById(i).getClass().getCanonicalName()});
                    }
                    catch (Throwable var3)
                    {
                        return "ID #" + i;
                    }
                }
            });
            reportCategory.func_189529_a("Actual block data value", new ICrashReportDetail<String>()
            {
                public String call() throws Exception
                {
                    IBlockState iblockstate = TileEntity.this.worldObj.getBlockState(TileEntity.this.pos);
                    int i = iblockstate.getBlock().getMetaFromState(iblockstate);

                    if (i < 0)
                    {
                        return "Unknown? (Got " + i + ")";
                    }
                    else
                    {
                        String s = String.format("%4s", new Object[] {Integer.toBinaryString(i)}).replace(" ", "0");
                        return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] {Integer.valueOf(i), s});
                    }
                }
            });
        }
    }

    public void setPos(BlockPos posIn)
    {
        if (posIn instanceof BlockPos.MutableBlockPos || posIn instanceof BlockPos.PooledMutableBlockPos)
        {
            LOGGER.warn((String)"Tried to assign a mutable BlockPos to a block entity...", (Throwable)(new Error(posIn.getClass().toString())));
            posIn = new BlockPos(posIn);
        }

        this.pos = posIn;
    }

    public boolean onlyOpsCanSetNbt()
    {
        return false;
    }

    @Nullable

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        return null;
    }

    public void func_189667_a(Rotation p_189667_1_)
    {
    }

    public void func_189668_a(Mirror p_189668_1_)
    {
    }

    static
    {
        addMapping(TileEntityFurnace.class, "Furnace");
        addMapping(TileEntityChest.class, "Chest");
        addMapping(TileEntityEnderChest.class, "EnderChest");
        addMapping(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
        addMapping(TileEntityDispenser.class, "Trap");
        addMapping(TileEntityDropper.class, "Dropper");
        addMapping(TileEntitySign.class, "Sign");
        addMapping(TileEntityMobSpawner.class, "MobSpawner");
        addMapping(TileEntityNote.class, "Music");
        addMapping(TileEntityPiston.class, "Piston");
        addMapping(TileEntityBrewingStand.class, "Cauldron");
        addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
        addMapping(TileEntityEndPortal.class, "Airportal");
        addMapping(TileEntityBeacon.class, "Beacon");
        addMapping(TileEntitySkull.class, "Skull");
        addMapping(TileEntityDaylightDetector.class, "DLDetector");
        addMapping(TileEntityHopper.class, "Hopper");
        addMapping(TileEntityComparator.class, "Comparator");
        addMapping(TileEntityFlowerPot.class, "FlowerPot");
        addMapping(TileEntityBanner.class, "Banner");
        addMapping(TileEntityStructure.class, "Structure");
        addMapping(TileEntityEndGateway.class, "EndGateway");
        addMapping(TileEntityCommandBlock.class, "Control");
    }
}
