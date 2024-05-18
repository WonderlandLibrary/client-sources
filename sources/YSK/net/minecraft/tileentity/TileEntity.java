package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public abstract class TileEntity
{
    private static final Logger logger;
    private static Map<Class<? extends TileEntity>, String> classToNameMap;
    private static final String[] I;
    private static Map<String, Class<? extends TileEntity>> nameToClassMap;
    protected Block blockType;
    protected BlockPos pos;
    protected boolean tileEntityInvalid;
    protected World worldObj;
    private int blockMetadata;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isInvalid() {
        return this.tileEntityInvalid;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public void validate() {
        this.tileEntityInvalid = ("".length() != 0);
    }
    
    public double getDistanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.pos.getX() + 0.5 - n;
        final double n5 = this.pos.getY() + 0.5 - n2;
        final double n6 = this.pos.getZ() + 0.5 - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public void updateContainingBlockInfo() {
        this.blockType = null;
        this.blockMetadata = -" ".length();
    }
    
    public void addInfoToCrashReport(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable(TileEntity.I[0x10 ^ 0x31], new Callable<String>(this) {
            final TileEntity this$0;
            private static final String[] I;
            
            @Override
            public String call() throws Exception {
                return String.valueOf(TileEntity.access$0().get(this.this$0.getClass())) + TileEntity$1.I["".length()] + this.this$0.getClass().getCanonicalName();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("C@ar", "coNRh");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                I();
            }
        });
        if (this.worldObj != null) {
            CrashReportCategory.addBlockInfo(crashReportCategory, this.pos, this.getBlockType(), this.getBlockMetadata());
            crashReportCategory.addCrashSectionCallable(TileEntity.I[0x2E ^ 0xC], new Callable<String>(this) {
                final TileEntity this$0;
                private static final String[] I;
                
                private static void I() {
                    (I = new String["  ".length()])["".length()] = I("<(CKc\u0011LKM5UCLHc\u0006E", "ulchF");
                    TileEntity$2.I[" ".length()] = I("\f5bN", "EqBmK");
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (true != true) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public String call() throws Exception {
                    final int idFromBlock = Block.getIdFromBlock(this.this$0.worldObj.getBlockState(this.this$0.pos).getBlock());
                    try {
                        final String s = TileEntity$2.I["".length()];
                        final Object[] array = new Object["   ".length()];
                        array["".length()] = idFromBlock;
                        array[" ".length()] = Block.getBlockById(idFromBlock).getUnlocalizedName();
                        array["  ".length()] = Block.getBlockById(idFromBlock).getClass().getCanonicalName();
                        return String.format(s, array);
                    }
                    catch (Throwable t) {
                        return TileEntity$2.I[" ".length()] + idFromBlock;
                    }
                }
                
                static {
                    I();
                }
            });
            crashReportCategory.addCrashSectionCallable(TileEntity.I[0x1D ^ 0x3E], new Callable<String>(this) {
                final TileEntity this$0;
                private static final String[] I;
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (3 < 1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public String call() throws Exception {
                    final IBlockState blockState = this.this$0.worldObj.getBlockState(this.this$0.pos);
                    final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
                    if (metaFromState < 0) {
                        return TileEntity$3.I["".length()] + metaFromState + TileEntity$3.I[" ".length()];
                    }
                    final String s = TileEntity$3.I["  ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = Integer.toBinaryString(metaFromState);
                    final String replace = String.format(s, array).replace(TileEntity$3.I["   ".length()], TileEntity$3.I[0x88 ^ 0x8C]);
                    final String s2 = TileEntity$3.I[0x9A ^ 0x9F];
                    final Object[] array2 = new Object["  ".length()];
                    array2["".length()] = metaFromState;
                    array2[" ".length()] = replace;
                    return String.format(s2, array2);
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                static {
                    I();
                }
                
                private static void I() {
                    (I = new String[0x5B ^ 0x5D])["".length()] = I("\u0001&\u000f;\u001b#&[u\\\u0013'\u0010u", "THdUt");
                    TileEntity$3.I[" ".length()] = I("X", "qkMLE");
                    TileEntity$3.I["  ".length()] = I("Mw?", "hCLTZ");
                    TileEntity$3.I["   ".length()] = I("B", "bhHOx");
                    TileEntity$3.I[0xAE ^ 0xAA] = I("\u007f", "OpkTa");
                    TileEntity$3.I[0x5D ^ 0x58] = I("`gL+WjvX7Rtr0oXef\njEa%", "EVhOw");
                }
            });
        }
    }
    
    public void setWorldObj(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public static TileEntity createAndLoadEntity(final NBTTagCompound nbtTagCompound) {
        TileEntity tileEntity = null;
        try {
            final Class<? extends TileEntity> clazz = TileEntity.nameToClassMap.get(nbtTagCompound.getString(TileEntity.I[0xB5 ^ 0xAB]));
            if (clazz != null) {
                tileEntity = (TileEntity)clazz.newInstance();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (tileEntity != null) {
            tileEntity.readFromNBT(nbtTagCompound);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            TileEntity.logger.warn(TileEntity.I[0x55 ^ 0x4A] + nbtTagCompound.getString(TileEntity.I[0xE7 ^ 0xC7]));
        }
        return tileEntity;
    }
    
    public Packet getDescriptionPacket() {
        return null;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        TileEntity.nameToClassMap = (Map<String, Class<? extends TileEntity>>)Maps.newHashMap();
        TileEntity.classToNameMap = (Map<Class<? extends TileEntity>, String>)Maps.newHashMap();
        addMapping(TileEntityFurnace.class, TileEntity.I["".length()]);
        addMapping(TileEntityChest.class, TileEntity.I[" ".length()]);
        addMapping(TileEntityEnderChest.class, TileEntity.I["  ".length()]);
        addMapping(BlockJukebox.TileEntityJukebox.class, TileEntity.I["   ".length()]);
        addMapping(TileEntityDispenser.class, TileEntity.I[0x81 ^ 0x85]);
        addMapping(TileEntityDropper.class, TileEntity.I[0x4C ^ 0x49]);
        addMapping(TileEntitySign.class, TileEntity.I[0x17 ^ 0x11]);
        addMapping(TileEntityMobSpawner.class, TileEntity.I[0x85 ^ 0x82]);
        addMapping(TileEntityNote.class, TileEntity.I[0x8F ^ 0x87]);
        addMapping(TileEntityPiston.class, TileEntity.I[0x46 ^ 0x4F]);
        addMapping(TileEntityBrewingStand.class, TileEntity.I[0x5A ^ 0x50]);
        addMapping(TileEntityEnchantmentTable.class, TileEntity.I[0x9B ^ 0x90]);
        addMapping(TileEntityEndPortal.class, TileEntity.I[0xAB ^ 0xA7]);
        addMapping(TileEntityCommandBlock.class, TileEntity.I[0x26 ^ 0x2B]);
        addMapping(TileEntityBeacon.class, TileEntity.I[0x5C ^ 0x52]);
        addMapping(TileEntitySkull.class, TileEntity.I[0x4 ^ 0xB]);
        addMapping(TileEntityDaylightDetector.class, TileEntity.I[0x36 ^ 0x26]);
        addMapping(TileEntityHopper.class, TileEntity.I[0x56 ^ 0x47]);
        addMapping(TileEntityComparator.class, TileEntity.I[0xBB ^ 0xA9]);
        addMapping(TileEntityFlowerPot.class, TileEntity.I[0x7E ^ 0x6D]);
        addMapping(TileEntityBanner.class, TileEntity.I[0x4E ^ 0x5A]);
    }
    
    public void markDirty() {
        if (this.worldObj != null) {
            final IBlockState blockState = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = blockState.getBlock().getMetaFromState(blockState);
            this.worldObj.markChunkDirty(this.pos, this);
            if (this.getBlockType() != Blocks.air) {
                this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
            }
        }
    }
    
    public World getWorld() {
        return this.worldObj;
    }
    
    public boolean receiveClientEvent(final int n, final int n2) {
        return "".length() != 0;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.pos = new BlockPos(nbtTagCompound.getInteger(TileEntity.I[0x33 ^ 0x25]), nbtTagCompound.getInteger(TileEntity.I[0x19 ^ 0xE]), nbtTagCompound.getInteger(TileEntity.I[0xB ^ 0x13]));
    }
    
    public int getBlockMetadata() {
        if (this.blockMetadata == -" ".length()) {
            final IBlockState blockState = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = blockState.getBlock().getMetaFromState(blockState);
        }
        return this.blockMetadata;
    }
    
    public void invalidate() {
        this.tileEntityInvalid = (" ".length() != 0);
    }
    
    private static void addMapping(final Class<? extends TileEntity> clazz, final String s) {
        if (TileEntity.nameToClassMap.containsKey(s)) {
            throw new IllegalArgumentException(TileEntity.I[0xA8 ^ 0xBD] + s);
        }
        TileEntity.nameToClassMap.put(s, clazz);
        TileEntity.classToNameMap.put(clazz, s);
    }
    
    private static void I() {
        (I = new String[0x45 ^ 0x61])["".length()] = I("\u000e\u0002(>)+\u0012", "HwZPH");
        TileEntity.I[" ".length()] = I("*\u0019+?\u001b", "iqNLo");
        TileEntity.I["  ".length()] = I("\u0011\u0017<7\u0006\u0017\u0011=!\u0000", "TyXRt");
        TileEntity.I["   ".length()] = I("(\u0001\b\u0007\u001b\u001e4\u0007\t\u0010\u001f\u0016", "zdkhi");
        TileEntity.I[0xC3 ^ 0xC7] = I("\u0016\u00141:", "BfPJB");
        TileEntity.I[0x44 ^ 0x41] = I("\u00031!74\"1", "GCNGD");
        TileEntity.I[0xA2 ^ 0xA4] = I("\u0006\r3\u0019", "UdTwM");
        TileEntity.I[0x9C ^ 0x9B] = I("\u0002?'\t\u0002.'+?\u0000", "OPEZr");
        TileEntity.I[0x8 ^ 0x0] = I("\u001d!)\f\u0007", "PTZed");
        TileEntity.I[0x84 ^ 0x8D] = I("\t!\u001e8\b7", "YHmLg");
        TileEntity.I[0x2A ^ 0x20] = I("\u0017\u0002\u001b.\u0010&\f\u0000", "TcnBt");
        TileEntity.I[0x92 ^ 0x99] = I("=\u0006\u0019\u0011.\u0016\u001c.\u0018-\u0014\r", "xhzyO");
        TileEntity.I[0x1 ^ 0xD] = I("\u0013\u00076\n. \u001a%\u0016", "RnDzA");
        TileEntity.I[0x0 ^ 0xD] = I(":\u0002\u0003\f\u0019\u0016\u0001", "ymmxk");
        TileEntity.I[0x57 ^ 0x59] = I("\u0016=\u0017\n\u0005:", "TXvij");
        TileEntity.I[0x32 ^ 0x3D] = I("'\u0012\u001f\"\u001f", "tyjNs");
        TileEntity.I[0x66 ^ 0x76] = I("\u0000\u000f**\u0001! \u001a \u0007", "DCnOu");
        TileEntity.I[0x93 ^ 0x82] = I("\u001a\u0004\u0001(# ", "RkqXF");
        TileEntity.I[0x21 ^ 0x33] = I("\u0016&?\u0011 '(&\u000e3", "UIRaA");
        TileEntity.I[0x76 ^ 0x65] = I("#!\u0004\u0018\u000f\u0017\u001d\u0004\u001b", "eMkoj");
        TileEntity.I[0xA5 ^ 0xB1] = I("\u0000,\u0004\u001e\u00020", "BMjpg");
        TileEntity.I[0x73 ^ 0x66] = I("\u0011\u000f\u0016:\u00046\u001b\u00123M<\u001e\\v", "UzfVm");
        TileEntity.I[0x73 ^ 0x65] = I("\u001d", "eNdIY");
        TileEntity.I[0x5C ^ 0x4B] = I("3", "JIVbG");
        TileEntity.I[0x6 ^ 0x1E] = I("\u0015", "oBRie");
        TileEntity.I[0x12 ^ 0xB] = I("I\f\u001dC!\u0000\u0016\u001d\n\"\u000eE\u000fC!\b\u0015\u001e\n\"\u000eDN7$\u0000\u0016N\n?I\u0004N\u00019\u000eD", "iencL");
        TileEntity.I[0x7B ^ 0x61] = I("\b ", "aDXyF");
        TileEntity.I[0x6D ^ 0x76] = I("3", "KuWTr");
        TileEntity.I[0x66 ^ 0x7A] = I("\u001e", "glGSg");
        TileEntity.I[0x2 ^ 0x1F] = I("\u001f", "ekqDL");
        TileEntity.I[0x9A ^ 0x84] = I("\u0004\u0003", "mgkCl");
        TileEntity.I[0xAA ^ 0xB5] = I("\u0000\u0004+\u0004!:\u0001%T\u0013?\u0000!\u001f\u0014=\u001b+\u0000(s\u0018+\u00009s\u0006&T", "SoBtQ");
        TileEntity.I[0x4E ^ 0x6E] = I("\u0003\u0010", "jtUss");
        TileEntity.I[0x85 ^ 0xA4] = I("!6\u0005\n", "oWhoI");
        TileEntity.I[0xA0 ^ 0x82] = I("\u0018\u0001\u0002$65B\u0014=8:\tV%.)\u0007", "YbvQW");
        TileEntity.I[0x37 ^ 0x14] = I("$*\"78\ti4.6\u0006\"v&8\u0011(v48\t<3", "eIVBY");
    }
    
    public boolean func_183000_F() {
        return "".length() != 0;
    }
    
    public double getMaxRenderDistanceSquared() {
        return 4096.0;
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        final String s = TileEntity.classToNameMap.get(this.getClass());
        if (s == null) {
            throw new RuntimeException(this.getClass() + TileEntity.I[0x68 ^ 0x71]);
        }
        nbtTagCompound.setString(TileEntity.I[0x75 ^ 0x6F], s);
        nbtTagCompound.setInteger(TileEntity.I[0x9 ^ 0x12], this.pos.getX());
        nbtTagCompound.setInteger(TileEntity.I[0x44 ^ 0x58], this.pos.getY());
        nbtTagCompound.setInteger(TileEntity.I[0xC ^ 0x11], this.pos.getZ());
    }
    
    public TileEntity() {
        this.pos = BlockPos.ORIGIN;
        this.blockMetadata = -" ".length();
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public Block getBlockType() {
        if (this.blockType == null) {
            this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
        }
        return this.blockType;
    }
    
    static Map access$0() {
        return TileEntity.classToNameMap;
    }
    
    public boolean hasWorldObj() {
        if (this.worldObj != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
