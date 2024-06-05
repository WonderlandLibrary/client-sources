package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;
import java.util.concurrent.*;

public class TileEntity
{
    private static Map nameToClassMap;
    private static Map classToNameMap;
    protected World worldObj;
    public int xCoord;
    public int yCoord;
    public int zCoord;
    protected boolean tileEntityInvalid;
    public int blockMetadata;
    public Block blockType;
    
    static {
        TileEntity.nameToClassMap = new HashMap();
        TileEntity.classToNameMap = new HashMap();
        addMapping(TileEntityFurnace.class, "Furnace");
        addMapping(TileEntityChest.class, "Chest");
        addMapping(TileEntityEnderChest.class, "EnderChest");
        addMapping(TileEntityRecordPlayer.class, "RecordPlayer");
        addMapping(TileEntityDispenser.class, "Trap");
        addMapping(TileEntityDropper.class, "Dropper");
        addMapping(TileEntitySign.class, "Sign");
        addMapping(TileEntityMobSpawner.class, "MobSpawner");
        addMapping(TileEntityNote.class, "Music");
        addMapping(TileEntityPiston.class, "Piston");
        addMapping(TileEntityBrewingStand.class, "Cauldron");
        addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
        addMapping(TileEntityEndPortal.class, "Airportal");
        addMapping(TileEntityCommandBlock.class, "Control");
        addMapping(TileEntityBeacon.class, "Beacon");
        addMapping(TileEntitySkull.class, "Skull");
        addMapping(TileEntityDaylightDetector.class, "DLDetector");
        addMapping(TileEntityHopper.class, "Hopper");
        addMapping(TileEntityComparator.class, "Comparator");
    }
    
    public TileEntity() {
        this.blockMetadata = -1;
    }
    
    private static void addMapping(final Class par0Class, final String par1Str) {
        if (TileEntity.nameToClassMap.containsKey(par1Str)) {
            throw new IllegalArgumentException("Duplicate id: " + par1Str);
        }
        TileEntity.nameToClassMap.put(par1Str, par0Class);
        TileEntity.classToNameMap.put(par0Class, par1Str);
    }
    
    public World getWorldObj() {
        return this.worldObj;
    }
    
    public void setWorldObj(final World par1World) {
        this.worldObj = par1World;
    }
    
    public boolean func_70309_m() {
        return this.worldObj != null;
    }
    
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        this.xCoord = par1NBTTagCompound.getInteger("x");
        this.yCoord = par1NBTTagCompound.getInteger("y");
        this.zCoord = par1NBTTagCompound.getInteger("z");
    }
    
    public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        final String var2 = TileEntity.classToNameMap.get(this.getClass());
        if (var2 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        par1NBTTagCompound.setString("id", var2);
        par1NBTTagCompound.setInteger("x", this.xCoord);
        par1NBTTagCompound.setInteger("y", this.yCoord);
        par1NBTTagCompound.setInteger("z", this.zCoord);
    }
    
    public void updateEntity() {
    }
    
    public static TileEntity createAndLoadEntity(final NBTTagCompound par0NBTTagCompound) {
        TileEntity var1 = null;
        try {
            final Class var2 = TileEntity.nameToClassMap.get(par0NBTTagCompound.getString("id"));
            if (var2 != null) {
                var1 = var2.newInstance();
            }
        }
        catch (Exception var3) {
            var3.printStackTrace();
        }
        if (var1 != null) {
            var1.readFromNBT(par0NBTTagCompound);
        }
        else {
            MinecraftServer.getServer().getLogAgent().logWarning("Skipping TileEntity with id " + par0NBTTagCompound.getString("id"));
        }
        return var1;
    }
    
    public int getBlockMetadata() {
        if (this.blockMetadata == -1) {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }
        return this.blockMetadata;
    }
    
    public void onInventoryChanged() {
        if (this.worldObj != null) {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.updateTileEntityChunkAndDoNothing(this.xCoord, this.yCoord, this.zCoord, this);
            if (this.getBlockType() != null) {
                this.worldObj.func_96440_m(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
            }
        }
    }
    
    public double getDistanceFrom(final double par1, final double par3, final double par5) {
        final double var7 = this.xCoord + 0.5 - par1;
        final double var8 = this.yCoord + 0.5 - par3;
        final double var9 = this.zCoord + 0.5 - par5;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double getMaxRenderDistanceSquared() {
        return 4096.0;
    }
    
    public Block getBlockType() {
        if (this.blockType == null) {
            this.blockType = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
        }
        return this.blockType;
    }
    
    public Packet getDescriptionPacket() {
        return null;
    }
    
    public boolean isInvalid() {
        return this.tileEntityInvalid;
    }
    
    public void invalidate() {
        this.tileEntityInvalid = true;
    }
    
    public void validate() {
        this.tileEntityInvalid = false;
    }
    
    public boolean receiveClientEvent(final int par1, final int par2) {
        return false;
    }
    
    public void updateContainingBlockInfo() {
        this.blockType = null;
        this.blockMetadata = -1;
    }
    
    public void func_85027_a(final CrashReportCategory par1CrashReportCategory) {
        par1CrashReportCategory.addCrashSectionCallable("Name", new CallableTileEntityName(this));
        CrashReportCategory.func_85068_a(par1CrashReportCategory, this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, this.getBlockMetadata());
        par1CrashReportCategory.addCrashSectionCallable("Actual block type", new CallableTileEntityID(this));
        par1CrashReportCategory.addCrashSectionCallable("Actual block data value", new CallableTileEntityData(this));
    }
    
    static Map getClassToNameMap() {
        return TileEntity.classToNameMap;
    }
}
