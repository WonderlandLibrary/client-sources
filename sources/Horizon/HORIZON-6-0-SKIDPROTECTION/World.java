package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import java.util.Set;
import java.util.Calendar;
import java.util.Random;
import java.util.List;

public abstract class World implements IBlockAccess
{
    protected boolean HorizonCode_Horizon_È;
    public final List Â;
    protected final List Ý;
    public final List Ø­áŒŠá;
    public final List Âµá€;
    private final List ÇŽÉ;
    private final List ˆá;
    public final List Ó;
    public final List à;
    protected final IntHashMap Ø;
    private long ÇŽÕ;
    private int É;
    protected int áŒŠÆ;
    protected final int áˆºÑ¢Õ = 1013904223;
    protected float ÂµÈ;
    protected float á;
    protected float ˆÏ­;
    protected float £á;
    private int áƒ;
    public final Random Å;
    public final WorldProvider £à;
    protected List µà;
    protected IChunkProvider ˆà;
    protected final ISaveHandler ¥Æ;
    protected WorldInfo Ø­à;
    protected boolean µÕ;
    protected MapStorage Æ;
    protected VillageCollection Šáƒ;
    public final Profiler Ï­Ðƒà;
    private final Calendar á€;
    protected Scoreboard áŒŠà;
    public final boolean ŠÄ;
    protected Set Ñ¢á;
    private int Õ;
    protected boolean ŒÏ;
    protected boolean Çªà¢;
    private boolean à¢;
    private final WorldBorder ŠÂµà;
    int[] Ê;
    private static final String ¥à = "CL_00000140";
    
    protected World(final ISaveHandler saveHandlerIn, final WorldInfo info, final WorldProvider providerIn, final Profiler profilerIn, final boolean client) {
        this.Â = Lists.newArrayList();
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Lists.newArrayList();
        this.ÇŽÉ = Lists.newArrayList();
        this.ˆá = Lists.newArrayList();
        this.Ó = Lists.newArrayList();
        this.à = Lists.newArrayList();
        this.Ø = new IntHashMap();
        this.ÇŽÕ = 16777215L;
        this.áŒŠÆ = new Random().nextInt();
        this.Å = new Random();
        this.µà = Lists.newArrayList();
        this.á€ = Calendar.getInstance();
        this.áŒŠà = new Scoreboard();
        this.Ñ¢á = Sets.newHashSet();
        this.Õ = this.Å.nextInt(12000);
        this.ŒÏ = true;
        this.Çªà¢ = true;
        this.Ê = new int[32768];
        this.¥Æ = saveHandlerIn;
        this.Ï­Ðƒà = profilerIn;
        this.Ø­à = info;
        this.£à = providerIn;
        this.ŠÄ = client;
        this.ŠÂµà = providerIn.ˆà();
    }
    
    public World Ø() {
        return this;
    }
    
    @Override
    public BiomeGenBase Ý(final BlockPos pos) {
        if (this.Ó(pos)) {
            final Chunk var2 = this.à(pos);
            try {
                return var2.HorizonCode_Horizon_È(pos, this.£à.ˆÏ­());
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Getting biome");
                final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Coordinates of biome request");
                var5.HorizonCode_Horizon_È("Location", new Callable() {
                    private static final String Â = "CL_00000141";
                    
                    public String HorizonCode_Horizon_È() {
                        return CrashReportCategory.HorizonCode_Horizon_È(pos);
                    }
                });
                throw new ReportedException(var3);
            }
        }
        return this.£à.ˆÏ­().HorizonCode_Horizon_È(pos, BiomeGenBase.µà);
    }
    
    public WorldChunkManager áŒŠÆ() {
        return this.£à.ˆÏ­();
    }
    
    protected abstract IChunkProvider Â();
    
    public void HorizonCode_Horizon_È(final WorldSettings settings) {
        this.Ø­à.Ø­áŒŠá(true);
    }
    
    public void áˆºÑ¢Õ() {
        this.Ñ¢á(new BlockPos(8, 64, 8));
    }
    
    public Block Âµá€(final BlockPos pos) {
        BlockPos var2;
        for (var2 = new BlockPos(pos.HorizonCode_Horizon_È(), 63, pos.Ý()); !this.Ø­áŒŠá(var2.Ø­áŒŠá()); var2 = var2.Ø­áŒŠá()) {}
        return this.Â(var2).Ý();
    }
    
    private boolean ÇŽÉ(final BlockPos pos) {
        return pos.HorizonCode_Horizon_È() >= -30000000 && pos.Ý() >= -30000000 && pos.HorizonCode_Horizon_È() < 30000000 && pos.Ý() < 30000000 && pos.Â() >= 0 && pos.Â() < 256;
    }
    
    @Override
    public boolean Ø­áŒŠá(final BlockPos pos) {
        return this.Â(pos).Ý().Ó() == Material.HorizonCode_Horizon_È;
    }
    
    public boolean Ó(final BlockPos pos) {
        return this.HorizonCode_Horizon_È(pos, true);
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos pos, final boolean p_175668_2_) {
        return this.ÇŽÉ(pos) && this.Â(pos.HorizonCode_Horizon_È() >> 4, pos.Ý() >> 4, p_175668_2_);
    }
    
    public boolean Â(final BlockPos p_175697_1_, final int radius) {
        return this.HorizonCode_Horizon_È(p_175697_1_, radius, true);
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_175648_1_, final int radius, final boolean p_175648_3_) {
        return this.HorizonCode_Horizon_È(p_175648_1_.HorizonCode_Horizon_È() - radius, p_175648_1_.Â() - radius, p_175648_1_.Ý() - radius, p_175648_1_.HorizonCode_Horizon_È() + radius, p_175648_1_.Â() + radius, p_175648_1_.Ý() + radius, p_175648_3_);
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_175707_1_, final BlockPos p_175707_2_) {
        return this.HorizonCode_Horizon_È(p_175707_1_, p_175707_2_, true);
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_175706_1_, final BlockPos p_175706_2_, final boolean p_175706_3_) {
        return this.HorizonCode_Horizon_È(p_175706_1_.HorizonCode_Horizon_È(), p_175706_1_.Â(), p_175706_1_.Ý(), p_175706_2_.HorizonCode_Horizon_È(), p_175706_2_.Â(), p_175706_2_.Ý(), p_175706_3_);
    }
    
    public boolean HorizonCode_Horizon_È(final StructureBoundingBox p_175711_1_) {
        return this.HorizonCode_Horizon_È(p_175711_1_, true);
    }
    
    public boolean HorizonCode_Horizon_È(final StructureBoundingBox p_175639_1_, final boolean p_175639_2_) {
        return this.HorizonCode_Horizon_È(p_175639_1_.HorizonCode_Horizon_È, p_175639_1_.Â, p_175639_1_.Ý, p_175639_1_.Ø­áŒŠá, p_175639_1_.Âµá€, p_175639_1_.Ó, p_175639_2_);
    }
    
    private boolean HorizonCode_Horizon_È(int p_175663_1_, final int p_175663_2_, int p_175663_3_, int p_175663_4_, final int p_175663_5_, int p_175663_6_, final boolean p_175663_7_) {
        if (p_175663_5_ >= 0 && p_175663_2_ < 256) {
            p_175663_1_ >>= 4;
            p_175663_3_ >>= 4;
            p_175663_4_ >>= 4;
            p_175663_6_ >>= 4;
            for (int var8 = p_175663_1_; var8 <= p_175663_4_; ++var8) {
                for (int var9 = p_175663_3_; var9 <= p_175663_6_; ++var9) {
                    if (!this.Â(var8, var9, p_175663_7_)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    protected boolean Â(final int x, final int z, final boolean allowEmpty) {
        return this.ˆà.HorizonCode_Horizon_È(x, z) && (allowEmpty || !this.ˆà.Ø­áŒŠá(x, z).Ø());
    }
    
    public Chunk à(final BlockPos pos) {
        return this.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() >> 4, pos.Ý() >> 4);
    }
    
    public Chunk HorizonCode_Horizon_È(final int chunkX, final int chunkZ) {
        return this.ˆà.Ø­áŒŠá(chunkX, chunkZ);
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos pos, final IBlockState newState, final int flags) {
        if (!this.ÇŽÉ(pos)) {
            return false;
        }
        if (!this.ŠÄ && this.Ø­à.Ø­à() == WorldType.Ø) {
            return false;
        }
        final Chunk var4 = this.à(pos);
        final Block var5 = newState.Ý();
        final IBlockState var6 = var4.HorizonCode_Horizon_È(pos, newState);
        if (var6 == null) {
            return false;
        }
        final Block var7 = var6.Ý();
        if (var5.Â() != var7.Â() || var5.Ø­áŒŠá() != var7.Ø­áŒŠá()) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È("checkLight");
            this.Šáƒ(pos);
            this.Ï­Ðƒà.Â();
        }
        if ((flags & 0x2) != 0x0 && (!this.ŠÄ || (flags & 0x4) == 0x0) && var4.áŒŠÆ()) {
            this.áŒŠÆ(pos);
        }
        if (!this.ŠÄ && (flags & 0x1) != 0x0) {
            this.HorizonCode_Horizon_È(pos, var6.Ý());
            if (var5.Õ()) {
                this.Âµá€(pos, var5);
            }
        }
        return true;
    }
    
    public boolean Ø(final BlockPos pos) {
        return this.HorizonCode_Horizon_È(pos, Blocks.Â.¥à(), 3);
    }
    
    public boolean Â(final BlockPos pos, final boolean dropBlock) {
        final IBlockState var3 = this.Â(pos);
        final Block var4 = var3.Ý();
        if (var4.Ó() == Material.HorizonCode_Horizon_È) {
            return false;
        }
        this.Â(2001, pos, Block.HorizonCode_Horizon_È(var3));
        if (dropBlock) {
            var4.HorizonCode_Horizon_È(this, pos, var3, 0);
        }
        return this.HorizonCode_Horizon_È(pos, Blocks.Â.¥à(), 3);
    }
    
    public boolean Â(final BlockPos pos, final IBlockState state) {
        return this.HorizonCode_Horizon_È(pos, state, 3);
    }
    
    public void áŒŠÆ(final BlockPos pos) {
        for (int var2 = 0; var2 < this.µà.size(); ++var2) {
            this.µà.get(var2).HorizonCode_Horizon_È(pos);
        }
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos, final Block blockType) {
        if (this.Ø­à.Ø­à() != WorldType.Ø) {
            this.Â(pos, blockType);
        }
    }
    
    public void HorizonCode_Horizon_È(final int x1, final int z1, int x2, int z2) {
        if (x2 > z2) {
            final int var5 = z2;
            z2 = x2;
            x2 = var5;
        }
        if (!this.£à.Å()) {
            for (int var5 = x2; var5 <= z2; ++var5) {
                this.Ý(EnumSkyBlock.HorizonCode_Horizon_È, new BlockPos(x1, var5, z1));
            }
        }
        this.Â(x1, x2, z1, x1, z2, z1);
    }
    
    public void Â(final BlockPos rangeMin, final BlockPos rangeMax) {
        this.Â(rangeMin.HorizonCode_Horizon_È(), rangeMin.Â(), rangeMin.Ý(), rangeMax.HorizonCode_Horizon_È(), rangeMax.Â(), rangeMax.Ý());
    }
    
    public void Â(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        for (int var7 = 0; var7 < this.µà.size(); ++var7) {
            this.µà.get(var7).HorizonCode_Horizon_È(x1, y1, z1, x2, y2, z2);
        }
    }
    
    public void Â(final BlockPos pos, final Block blockType) {
        this.Ý(pos.Ø(), blockType);
        this.Ý(pos.áŒŠÆ(), blockType);
        this.Ý(pos.Âµá€(), blockType);
        this.Ý(pos.Ø­áŒŠá(), blockType);
        this.Ý(pos.Ó(), blockType);
        this.Ý(pos.à(), blockType);
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos, final Block blockType, final EnumFacing skipSide) {
        if (skipSide != EnumFacing.Âµá€) {
            this.Ý(pos.Ø(), blockType);
        }
        if (skipSide != EnumFacing.Ó) {
            this.Ý(pos.áŒŠÆ(), blockType);
        }
        if (skipSide != EnumFacing.HorizonCode_Horizon_È) {
            this.Ý(pos.Âµá€(), blockType);
        }
        if (skipSide != EnumFacing.Â) {
            this.Ý(pos.Ø­áŒŠá(), blockType);
        }
        if (skipSide != EnumFacing.Ý) {
            this.Ý(pos.Ó(), blockType);
        }
        if (skipSide != EnumFacing.Ø­áŒŠá) {
            this.Ý(pos.à(), blockType);
        }
    }
    
    public void Ý(final BlockPos pos, final Block blockIn) {
        if (!this.ŠÄ) {
            final IBlockState var3 = this.Â(pos);
            try {
                var3.Ý().HorizonCode_Horizon_È(this, pos, var3, blockIn);
            }
            catch (Throwable var5) {
                final CrashReport var4 = CrashReport.HorizonCode_Horizon_È(var5, "Exception while updating neighbours");
                final CrashReportCategory var6 = var4.HorizonCode_Horizon_È("Block being updated");
                var6.HorizonCode_Horizon_È("Source block type", new Callable() {
                    private static final String Â = "CL_00000142";
                    
                    public String HorizonCode_Horizon_È() {
                        try {
                            return String.format("ID #%d (%s // %s)", Block.HorizonCode_Horizon_È(blockIn), blockIn.Çªà¢(), blockIn.getClass().getCanonicalName());
                        }
                        catch (Throwable var2) {
                            return "ID #" + Block.HorizonCode_Horizon_È(blockIn);
                        }
                    }
                });
                CrashReportCategory.HorizonCode_Horizon_È(var6, pos, var3);
                throw new ReportedException(var4);
            }
        }
    }
    
    public boolean Ø­áŒŠá(final BlockPos pos, final Block blockType) {
        return false;
    }
    
    public boolean áˆºÑ¢Õ(final BlockPos pos) {
        return this.à(pos).Ó(pos);
    }
    
    public boolean ÂµÈ(final BlockPos pos) {
        if (pos.Â() >= 63) {
            return this.áˆºÑ¢Õ(pos);
        }
        BlockPos var2 = new BlockPos(pos.HorizonCode_Horizon_È(), 63, pos.Ý());
        if (!this.áˆºÑ¢Õ(var2)) {
            return false;
        }
        for (var2 = var2.Âµá€(); var2.Â() > pos.Â(); var2 = var2.Âµá€()) {
            final Block var3 = this.Â(var2).Ý();
            if (var3.Â() > 0 && !var3.Ó().HorizonCode_Horizon_È()) {
                return false;
            }
        }
        return true;
    }
    
    public int á(BlockPos pos) {
        if (pos.Â() < 0) {
            return 0;
        }
        if (pos.Â() >= 256) {
            pos = new BlockPos(pos.HorizonCode_Horizon_È(), 255, pos.Ý());
        }
        return this.à(pos).HorizonCode_Horizon_È(pos, 0);
    }
    
    public int ˆÏ­(final BlockPos pos) {
        return this.Ý(pos, true);
    }
    
    public int Ý(BlockPos pos, final boolean checkNeighbors) {
        if (pos.HorizonCode_Horizon_È() < -30000000 || pos.Ý() < -30000000 || pos.HorizonCode_Horizon_È() >= 30000000 || pos.Ý() >= 30000000) {
            return 15;
        }
        if (checkNeighbors && this.Â(pos).Ý().Âµá€()) {
            int var8 = this.Ý(pos.Ø­áŒŠá(), false);
            final int var9 = this.Ý(pos.áŒŠÆ(), false);
            final int var10 = this.Ý(pos.Ø(), false);
            final int var11 = this.Ý(pos.à(), false);
            final int var12 = this.Ý(pos.Ó(), false);
            if (var9 > var8) {
                var8 = var9;
            }
            if (var10 > var8) {
                var8 = var10;
            }
            if (var11 > var8) {
                var8 = var11;
            }
            if (var12 > var8) {
                var8 = var12;
            }
            return var8;
        }
        if (pos.Â() < 0) {
            return 0;
        }
        if (pos.Â() >= 256) {
            pos = new BlockPos(pos.HorizonCode_Horizon_È(), 255, pos.Ý());
        }
        final Chunk var13 = this.à(pos);
        return var13.HorizonCode_Horizon_È(pos, this.É);
    }
    
    public BlockPos £á(final BlockPos pos) {
        int var2;
        if (pos.HorizonCode_Horizon_È() >= -30000000 && pos.Ý() >= -30000000 && pos.HorizonCode_Horizon_È() < 30000000 && pos.Ý() < 30000000) {
            if (this.Â(pos.HorizonCode_Horizon_È() >> 4, pos.Ý() >> 4, true)) {
                var2 = this.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() >> 4, pos.Ý() >> 4).Â(pos.HorizonCode_Horizon_È() & 0xF, pos.Ý() & 0xF);
            }
            else {
                var2 = 0;
            }
        }
        else {
            var2 = 64;
        }
        return new BlockPos(pos.HorizonCode_Horizon_È(), var2, pos.Ý());
    }
    
    public int Â(final int x, final int z) {
        if (x < -30000000 || z < -30000000 || x >= 30000000 || z >= 30000000) {
            return 64;
        }
        if (!this.Â(x >> 4, z >> 4, true)) {
            return 0;
        }
        final Chunk var3 = this.HorizonCode_Horizon_È(x >> 4, z >> 4);
        return var3.Æ();
    }
    
    public int HorizonCode_Horizon_È(final EnumSkyBlock type, BlockPos p_175705_2_) {
        if (this.£à.Å() && type == EnumSkyBlock.HorizonCode_Horizon_È) {
            return 0;
        }
        if (p_175705_2_.Â() < 0) {
            p_175705_2_ = new BlockPos(p_175705_2_.HorizonCode_Horizon_È(), 0, p_175705_2_.Ý());
        }
        if (!this.ÇŽÉ(p_175705_2_)) {
            return type.Ý;
        }
        if (!this.Ó(p_175705_2_)) {
            return type.Ý;
        }
        if (this.Â(p_175705_2_).Ý().Âµá€()) {
            int var8 = this.Â(type, p_175705_2_.Ø­áŒŠá());
            final int var9 = this.Â(type, p_175705_2_.áŒŠÆ());
            final int var10 = this.Â(type, p_175705_2_.Ø());
            final int var11 = this.Â(type, p_175705_2_.à());
            final int var12 = this.Â(type, p_175705_2_.Ó());
            if (var9 > var8) {
                var8 = var9;
            }
            if (var10 > var8) {
                var8 = var10;
            }
            if (var11 > var8) {
                var8 = var11;
            }
            if (var12 > var8) {
                var8 = var12;
            }
            return var8;
        }
        final Chunk var13 = this.à(p_175705_2_);
        return var13.HorizonCode_Horizon_È(type, p_175705_2_);
    }
    
    public int Â(final EnumSkyBlock type, BlockPos pos) {
        if (pos.Â() < 0) {
            pos = new BlockPos(pos.HorizonCode_Horizon_È(), 0, pos.Ý());
        }
        if (!this.ÇŽÉ(pos)) {
            return type.Ý;
        }
        if (!this.Ó(pos)) {
            return type.Ý;
        }
        final Chunk var3 = this.à(pos);
        return var3.HorizonCode_Horizon_È(type, pos);
    }
    
    public void HorizonCode_Horizon_È(final EnumSkyBlock type, final BlockPos pos, final int lightValue) {
        if (this.ÇŽÉ(pos) && this.Ó(pos)) {
            final Chunk var4 = this.à(pos);
            var4.HorizonCode_Horizon_È(type, pos, lightValue);
            this.Å(pos);
        }
    }
    
    public void Å(final BlockPos pos) {
        for (int var2 = 0; var2 < this.µà.size(); ++var2) {
            this.µà.get(var2).Â(pos);
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final BlockPos p_175626_1_, final int p_175626_2_) {
        final int var3 = this.HorizonCode_Horizon_È(EnumSkyBlock.HorizonCode_Horizon_È, p_175626_1_);
        int var4 = this.HorizonCode_Horizon_È(EnumSkyBlock.Â, p_175626_1_);
        if (var4 < p_175626_2_) {
            var4 = p_175626_2_;
        }
        return var3 << 20 | var4 << 4;
    }
    
    public float £à(final BlockPos pos) {
        return this.£à.£à()[this.ˆÏ­(pos)];
    }
    
    @Override
    public IBlockState Â(final BlockPos pos) {
        if (!this.ÇŽÉ(pos)) {
            return Blocks.Â.¥à();
        }
        final Chunk var2 = this.à(pos);
        return var2.Ø­áŒŠá(pos);
    }
    
    public boolean ÂµÈ() {
        return this.É < 4;
    }
    
    public MovingObjectPosition HorizonCode_Horizon_È(final Vec3 p_72933_1_, final Vec3 p_72933_2_) {
        return this.HorizonCode_Horizon_È(p_72933_1_, p_72933_2_, false, false, false);
    }
    
    public MovingObjectPosition HorizonCode_Horizon_È(final Vec3 p_72901_1_, final Vec3 p_72901_2_, final boolean p_72901_3_) {
        return this.HorizonCode_Horizon_È(p_72901_1_, p_72901_2_, p_72901_3_, false, false);
    }
    
    public MovingObjectPosition HorizonCode_Horizon_È(Vec3 p_147447_1_, final Vec3 p_147447_2_, final boolean p_147447_3_, final boolean p_147447_4_, final boolean p_147447_5_) {
        if (Double.isNaN(p_147447_1_.HorizonCode_Horizon_È) || Double.isNaN(p_147447_1_.Â) || Double.isNaN(p_147447_1_.Ý)) {
            return null;
        }
        if (!Double.isNaN(p_147447_2_.HorizonCode_Horizon_È) && !Double.isNaN(p_147447_2_.Â) && !Double.isNaN(p_147447_2_.Ý)) {
            final int var6 = MathHelper.Ý(p_147447_2_.HorizonCode_Horizon_È);
            final int var7 = MathHelper.Ý(p_147447_2_.Â);
            final int var8 = MathHelper.Ý(p_147447_2_.Ý);
            int var9 = MathHelper.Ý(p_147447_1_.HorizonCode_Horizon_È);
            int var10 = MathHelper.Ý(p_147447_1_.Â);
            int var11 = MathHelper.Ý(p_147447_1_.Ý);
            BlockPos var12 = new BlockPos(var9, var10, var11);
            new BlockPos(var6, var7, var8);
            final IBlockState var13 = this.Â(var12);
            final Block var14 = var13.Ý();
            if ((!p_147447_4_ || var14.HorizonCode_Horizon_È(this, var12, var13) != null) && var14.HorizonCode_Horizon_È(var13, p_147447_3_)) {
                final MovingObjectPosition var15 = var14.HorizonCode_Horizon_È(this, var12, p_147447_1_, p_147447_2_);
                if (var15 != null) {
                    return var15;
                }
            }
            MovingObjectPosition var16 = null;
            int var17 = 200;
            while (var17-- >= 0) {
                if (Double.isNaN(p_147447_1_.HorizonCode_Horizon_È) || Double.isNaN(p_147447_1_.Â) || Double.isNaN(p_147447_1_.Ý)) {
                    return null;
                }
                if (var9 == var6 && var10 == var7 && var11 == var8) {
                    return p_147447_5_ ? var16 : null;
                }
                boolean var18 = true;
                boolean var19 = true;
                boolean var20 = true;
                double var21 = 999.0;
                double var22 = 999.0;
                double var23 = 999.0;
                if (var6 > var9) {
                    var21 = var9 + 1.0;
                }
                else if (var6 < var9) {
                    var21 = var9 + 0.0;
                }
                else {
                    var18 = false;
                }
                if (var7 > var10) {
                    var22 = var10 + 1.0;
                }
                else if (var7 < var10) {
                    var22 = var10 + 0.0;
                }
                else {
                    var19 = false;
                }
                if (var8 > var11) {
                    var23 = var11 + 1.0;
                }
                else if (var8 < var11) {
                    var23 = var11 + 0.0;
                }
                else {
                    var20 = false;
                }
                double var24 = 999.0;
                double var25 = 999.0;
                double var26 = 999.0;
                final double var27 = p_147447_2_.HorizonCode_Horizon_È - p_147447_1_.HorizonCode_Horizon_È;
                final double var28 = p_147447_2_.Â - p_147447_1_.Â;
                final double var29 = p_147447_2_.Ý - p_147447_1_.Ý;
                if (var18) {
                    var24 = (var21 - p_147447_1_.HorizonCode_Horizon_È) / var27;
                }
                if (var19) {
                    var25 = (var22 - p_147447_1_.Â) / var28;
                }
                if (var20) {
                    var26 = (var23 - p_147447_1_.Ý) / var29;
                }
                if (var24 == -0.0) {
                    var24 = -1.0E-4;
                }
                if (var25 == -0.0) {
                    var25 = -1.0E-4;
                }
                if (var26 == -0.0) {
                    var26 = -1.0E-4;
                }
                EnumFacing var30;
                if (var24 < var25 && var24 < var26) {
                    var30 = ((var6 > var9) ? EnumFacing.Âµá€ : EnumFacing.Ó);
                    p_147447_1_ = new Vec3(var21, p_147447_1_.Â + var28 * var24, p_147447_1_.Ý + var29 * var24);
                }
                else if (var25 < var26) {
                    var30 = ((var7 > var10) ? EnumFacing.HorizonCode_Horizon_È : EnumFacing.Â);
                    p_147447_1_ = new Vec3(p_147447_1_.HorizonCode_Horizon_È + var27 * var25, var22, p_147447_1_.Ý + var29 * var25);
                }
                else {
                    var30 = ((var8 > var11) ? EnumFacing.Ý : EnumFacing.Ø­áŒŠá);
                    p_147447_1_ = new Vec3(p_147447_1_.HorizonCode_Horizon_È + var27 * var26, p_147447_1_.Â + var28 * var26, var23);
                }
                var9 = MathHelper.Ý(p_147447_1_.HorizonCode_Horizon_È) - ((var30 == EnumFacing.Ó) ? 1 : 0);
                var10 = MathHelper.Ý(p_147447_1_.Â) - ((var30 == EnumFacing.Â) ? 1 : 0);
                var11 = MathHelper.Ý(p_147447_1_.Ý) - ((var30 == EnumFacing.Ø­áŒŠá) ? 1 : 0);
                var12 = new BlockPos(var9, var10, var11);
                final IBlockState var31 = this.Â(var12);
                final Block var32 = var31.Ý();
                if (p_147447_4_ && var32.HorizonCode_Horizon_È(this, var12, var31) == null) {
                    continue;
                }
                if (var32.HorizonCode_Horizon_È(var31, p_147447_3_)) {
                    final MovingObjectPosition var33 = var32.HorizonCode_Horizon_È(this, var12, p_147447_1_, p_147447_2_);
                    if (var33 != null) {
                        return var33;
                    }
                    continue;
                }
                else {
                    var16 = new MovingObjectPosition(MovingObjectPosition.HorizonCode_Horizon_È.HorizonCode_Horizon_È, p_147447_1_, var30, var12);
                }
            }
            return p_147447_5_ ? var16 : null;
        }
        return null;
    }
    
    public void HorizonCode_Horizon_È(final Entity p_72956_1_, final String p_72956_2_, final float p_72956_3_, final float p_72956_4_) {
        for (int var5 = 0; var5 < this.µà.size(); ++var5) {
            this.µà.get(var5).HorizonCode_Horizon_È(p_72956_2_, p_72956_1_.ŒÏ, p_72956_1_.Çªà¢, p_72956_1_.Ê, p_72956_3_, p_72956_4_);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_85173_1_, final String p_85173_2_, final float p_85173_3_, final float p_85173_4_) {
        for (int var5 = 0; var5 < this.µà.size(); ++var5) {
            this.µà.get(var5).HorizonCode_Horizon_È(p_85173_1_, p_85173_2_, p_85173_1_.ŒÏ, p_85173_1_.Çªà¢, p_85173_1_.Ê, p_85173_3_, p_85173_4_);
        }
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final String soundName, final float volume, final float pitch) {
        for (int var10 = 0; var10 < this.µà.size(); ++var10) {
            this.µà.get(var10).HorizonCode_Horizon_È(soundName, x, y, z, volume, pitch);
        }
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final String soundName, final float volume, final float pitch, final boolean distanceDelay) {
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_175717_1_, final String p_175717_2_) {
        for (int var3 = 0; var3 < this.µà.size(); ++var3) {
            this.µà.get(var3).HorizonCode_Horizon_È(p_175717_2_, p_175717_1_);
        }
    }
    
    public void HorizonCode_Horizon_È(final EnumParticleTypes p_175688_1_, final double p_175688_2_, final double p_175688_4_, final double p_175688_6_, final double p_175688_8_, final double p_175688_10_, final double p_175688_12_, final int... p_175688_14_) {
        this.HorizonCode_Horizon_È(p_175688_1_.Ý(), p_175688_1_.Âµá€(), p_175688_2_, p_175688_4_, p_175688_6_, p_175688_8_, p_175688_10_, p_175688_12_, p_175688_14_);
    }
    
    public void HorizonCode_Horizon_È(final EnumParticleTypes p_175682_1_, final boolean p_175682_2_, final double p_175682_3_, final double p_175682_5_, final double p_175682_7_, final double p_175682_9_, final double p_175682_11_, final double p_175682_13_, final int... p_175682_15_) {
        this.HorizonCode_Horizon_È(p_175682_1_.Ý(), p_175682_1_.Âµá€() | p_175682_2_, p_175682_3_, p_175682_5_, p_175682_7_, p_175682_9_, p_175682_11_, p_175682_13_, p_175682_15_);
    }
    
    private void HorizonCode_Horizon_È(final int p_175720_1_, final boolean p_175720_2_, final double p_175720_3_, final double p_175720_5_, final double p_175720_7_, final double p_175720_9_, final double p_175720_11_, final double p_175720_13_, final int... p_175720_15_) {
        for (int var16 = 0; var16 < this.µà.size(); ++var16) {
            this.µà.get(var16).HorizonCode_Horizon_È(p_175720_1_, p_175720_2_, p_175720_3_, p_175720_5_, p_175720_7_, p_175720_9_, p_175720_11_, p_175720_13_, p_175720_15_);
        }
    }
    
    public boolean Âµá€(final Entity p_72942_1_) {
        this.à.add(p_72942_1_);
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_72838_1_) {
        final int var2 = MathHelper.Ý(p_72838_1_.ŒÏ / 16.0);
        final int var3 = MathHelper.Ý(p_72838_1_.Ê / 16.0);
        boolean var4 = p_72838_1_.Šáƒ;
        if (p_72838_1_ instanceof EntityPlayer) {
            var4 = true;
        }
        if (!var4 && !this.Â(var2, var3, true)) {
            return false;
        }
        if (p_72838_1_ instanceof EntityPlayer) {
            final EntityPlayer var5 = (EntityPlayer)p_72838_1_;
            this.Ó.add(var5);
            this.Ê();
        }
        this.HorizonCode_Horizon_È(var2, var3).HorizonCode_Horizon_È(p_72838_1_);
        this.Â.add(p_72838_1_);
        this.Ý(p_72838_1_);
        return true;
    }
    
    protected void Ý(final Entity p_72923_1_) {
        for (int var2 = 0; var2 < this.µà.size(); ++var2) {
            this.µà.get(var2).HorizonCode_Horizon_È(p_72923_1_);
        }
    }
    
    protected void Ø­áŒŠá(final Entity p_72847_1_) {
        for (int var2 = 0; var2 < this.µà.size(); ++var2) {
            this.µà.get(var2).Â(p_72847_1_);
        }
    }
    
    public void Â(final Entity p_72900_1_) {
        if (p_72900_1_.µÕ != null) {
            p_72900_1_.µÕ.HorizonCode_Horizon_È((Entity)null);
        }
        if (p_72900_1_.Æ != null) {
            p_72900_1_.HorizonCode_Horizon_È((Entity)null);
        }
        p_72900_1_.á€();
        if (p_72900_1_ instanceof EntityPlayer) {
            this.Ó.remove(p_72900_1_);
            this.Ê();
            this.Ø­áŒŠá(p_72900_1_);
        }
    }
    
    public void Ó(final Entity p_72973_1_) {
        p_72973_1_.á€();
        if (p_72973_1_ instanceof EntityPlayer) {
            this.Ó.remove(p_72973_1_);
            this.Ê();
        }
        final int var2 = p_72973_1_.£Õ;
        final int var3 = p_72973_1_.Œà;
        if (p_72973_1_.ˆÐƒØ­à && this.Â(var2, var3, true)) {
            this.HorizonCode_Horizon_È(var2, var3).Â(p_72973_1_);
        }
        this.Â.remove(p_72973_1_);
        this.Ø­áŒŠá(p_72973_1_);
    }
    
    public void HorizonCode_Horizon_È(final IWorldAccess p_72954_1_) {
        this.µà.add(p_72954_1_);
    }
    
    public void Â(final IWorldAccess p_72848_1_) {
        this.µà.remove(p_72848_1_);
    }
    
    public List HorizonCode_Horizon_È(final Entity p_72945_1_, final AxisAlignedBB p_72945_2_) {
        final ArrayList var3 = Lists.newArrayList();
        final int var4 = MathHelper.Ý(p_72945_2_.HorizonCode_Horizon_È);
        final int var5 = MathHelper.Ý(p_72945_2_.Ø­áŒŠá + 1.0);
        final int var6 = MathHelper.Ý(p_72945_2_.Â);
        final int var7 = MathHelper.Ý(p_72945_2_.Âµá€ + 1.0);
        final int var8 = MathHelper.Ý(p_72945_2_.Ý);
        final int var9 = MathHelper.Ý(p_72945_2_.Ó + 1.0);
        for (int var10 = var4; var10 < var5; ++var10) {
            for (int var11 = var8; var11 < var9; ++var11) {
                if (this.Ó(new BlockPos(var10, 64, var11))) {
                    for (int var12 = var6 - 1; var12 < var7; ++var12) {
                        final BlockPos var13 = new BlockPos(var10, var12, var11);
                        final boolean var14 = p_72945_1_.Ðƒà();
                        final boolean var15 = this.HorizonCode_Horizon_È(this.áŠ(), p_72945_1_);
                        if (var14 && var15) {
                            p_72945_1_.áŒŠÆ(false);
                        }
                        else if (!var14 && !var15) {
                            p_72945_1_.áŒŠÆ(true);
                        }
                        IBlockState var16;
                        if (!this.áŠ().HorizonCode_Horizon_È(var13) && var15) {
                            var16 = Blocks.Ý.¥à();
                        }
                        else {
                            var16 = this.Â(var13);
                        }
                        var16.Ý().HorizonCode_Horizon_È(this, var13, var16, p_72945_2_, var3, p_72945_1_);
                    }
                }
            }
        }
        final double var17 = 0.25;
        final List var18 = this.Â(p_72945_1_, p_72945_2_.Â(var17, var17, var17));
        for (int var19 = 0; var19 < var18.size(); ++var19) {
            if (p_72945_1_.µÕ != var18 && p_72945_1_.Æ != var18) {
                AxisAlignedBB var20 = var18.get(var19).t_();
                if (var20 != null && var20.Â(p_72945_2_)) {
                    var3.add(var20);
                }
                var20 = p_72945_1_.à(var18.get(var19));
                if (var20 != null && var20.Â(p_72945_2_)) {
                    var3.add(var20);
                }
            }
        }
        return var3;
    }
    
    public boolean HorizonCode_Horizon_È(final WorldBorder p_175673_1_, final Entity p_175673_2_) {
        double var3 = p_175673_1_.Ø­áŒŠá();
        double var4 = p_175673_1_.Âµá€();
        double var5 = p_175673_1_.Ó();
        double var6 = p_175673_1_.à();
        if (p_175673_2_.Ðƒà()) {
            ++var3;
            ++var4;
            --var5;
            --var6;
        }
        else {
            --var3;
            --var4;
            ++var5;
            ++var6;
        }
        return p_175673_2_.ŒÏ > var3 && p_175673_2_.ŒÏ < var5 && p_175673_2_.Ê > var4 && p_175673_2_.Ê < var6;
    }
    
    public List HorizonCode_Horizon_È(final AxisAlignedBB p_147461_1_) {
        final ArrayList var2 = Lists.newArrayList();
        final int var3 = MathHelper.Ý(p_147461_1_.HorizonCode_Horizon_È);
        final int var4 = MathHelper.Ý(p_147461_1_.Ø­áŒŠá + 1.0);
        final int var5 = MathHelper.Ý(p_147461_1_.Â);
        final int var6 = MathHelper.Ý(p_147461_1_.Âµá€ + 1.0);
        final int var7 = MathHelper.Ý(p_147461_1_.Ý);
        final int var8 = MathHelper.Ý(p_147461_1_.Ó + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var7; var10 < var8; ++var10) {
                if (this.Ó(new BlockPos(var9, 64, var10))) {
                    for (int var11 = var5 - 1; var11 < var6; ++var11) {
                        final BlockPos var12 = new BlockPos(var9, var11, var10);
                        IBlockState var13;
                        if (var9 >= -30000000 && var9 < 30000000 && var10 >= -30000000 && var10 < 30000000) {
                            var13 = this.Â(var12);
                        }
                        else {
                            var13 = Blocks.áŒŠÆ.¥à();
                        }
                        var13.Ý().HorizonCode_Horizon_È(this, var12, var13, p_147461_1_, var2, null);
                    }
                }
            }
        }
        return var2;
    }
    
    public int HorizonCode_Horizon_È(final float p_72967_1_) {
        final float var2 = this.Ý(p_72967_1_);
        float var3 = 1.0f - (MathHelper.Â(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f);
        var3 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 1.0f);
        var3 = 1.0f - var3;
        var3 *= (float)(1.0 - this.áˆºÑ¢Õ(p_72967_1_) * 5.0f / 16.0);
        var3 *= (float)(1.0 - this.Ø(p_72967_1_) * 5.0f / 16.0);
        var3 = 1.0f - var3;
        return (int)(var3 * 11.0f);
    }
    
    public float Â(final float p_72971_1_) {
        final float var2 = this.Ý(p_72971_1_);
        float var3 = 1.0f - (MathHelper.Â(var2 * 3.1415927f * 2.0f) * 2.0f + 0.2f);
        var3 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 1.0f);
        var3 = 1.0f - var3;
        var3 *= (float)(1.0 - this.áˆºÑ¢Õ(p_72971_1_) * 5.0f / 16.0);
        var3 *= (float)(1.0 - this.Ø(p_72971_1_) * 5.0f / 16.0);
        return var3 * 0.8f + 0.2f;
    }
    
    public Vec3 HorizonCode_Horizon_È(final Entity p_72833_1_, final float p_72833_2_) {
        final float var3 = this.Ý(p_72833_2_);
        float var4 = MathHelper.Â(var3 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        var4 = MathHelper.HorizonCode_Horizon_È(var4, 0.0f, 1.0f);
        final int var5 = MathHelper.Ý(p_72833_1_.ŒÏ);
        final int var6 = MathHelper.Ý(p_72833_1_.Çªà¢);
        final int var7 = MathHelper.Ý(p_72833_1_.Ê);
        final BlockPos var8 = new BlockPos(var5, var6, var7);
        final BiomeGenBase var9 = this.Ý(var8);
        final float var10 = var9.HorizonCode_Horizon_È(var8);
        final int var11 = var9.HorizonCode_Horizon_È(var10);
        float var12 = (var11 >> 16 & 0xFF) / 255.0f;
        float var13 = (var11 >> 8 & 0xFF) / 255.0f;
        float var14 = (var11 & 0xFF) / 255.0f;
        var12 *= var4;
        var13 *= var4;
        var14 *= var4;
        final float var15 = this.áˆºÑ¢Õ(p_72833_2_);
        if (var15 > 0.0f) {
            final float var16 = (var12 * 0.3f + var13 * 0.59f + var14 * 0.11f) * 0.6f;
            final float var17 = 1.0f - var15 * 0.75f;
            var12 = var12 * var17 + var16 * (1.0f - var17);
            var13 = var13 * var17 + var16 * (1.0f - var17);
            var14 = var14 * var17 + var16 * (1.0f - var17);
        }
        final float var16 = this.Ø(p_72833_2_);
        if (var16 > 0.0f) {
            final float var17 = (var12 * 0.3f + var13 * 0.59f + var14 * 0.11f) * 0.2f;
            final float var18 = 1.0f - var16 * 0.75f;
            var12 = var12 * var18 + var17 * (1.0f - var18);
            var13 = var13 * var18 + var17 * (1.0f - var18);
            var14 = var14 * var18 + var17 * (1.0f - var18);
        }
        if (this.áƒ > 0) {
            float var17 = this.áƒ - p_72833_2_;
            if (var17 > 1.0f) {
                var17 = 1.0f;
            }
            var17 *= 0.45f;
            var12 = var12 * (1.0f - var17) + 0.8f * var17;
            var13 = var13 * (1.0f - var17) + 0.8f * var17;
            var14 = var14 * (1.0f - var17) + 1.0f * var17;
        }
        return new Vec3(var12, var13, var14);
    }
    
    public float Ý(final float p_72826_1_) {
        return this.£à.HorizonCode_Horizon_È(this.Ø­à.à(), p_72826_1_);
    }
    
    public int á() {
        return this.£à.HorizonCode_Horizon_È(this.Ø­à.à());
    }
    
    public float ˆÏ­() {
        return WorldProvider.HorizonCode_Horizon_È[this.£à.HorizonCode_Horizon_È(this.Ø­à.à())];
    }
    
    public float Ø­áŒŠá(final float p_72929_1_) {
        final float var2 = this.Ý(p_72929_1_);
        return var2 * 3.1415927f * 2.0f;
    }
    
    public Vec3 Âµá€(final float p_72824_1_) {
        final float var2 = this.Ý(p_72824_1_);
        float var3 = MathHelper.Â(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        var3 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 1.0f);
        float var4 = (this.ÇŽÕ >> 16 & 0xFFL) / 255.0f;
        float var5 = (this.ÇŽÕ >> 8 & 0xFFL) / 255.0f;
        float var6 = (this.ÇŽÕ & 0xFFL) / 255.0f;
        final float var7 = this.áˆºÑ¢Õ(p_72824_1_);
        if (var7 > 0.0f) {
            final float var8 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.6f;
            final float var9 = 1.0f - var7 * 0.95f;
            var4 = var4 * var9 + var8 * (1.0f - var9);
            var5 = var5 * var9 + var8 * (1.0f - var9);
            var6 = var6 * var9 + var8 * (1.0f - var9);
        }
        var4 *= var3 * 0.9f + 0.1f;
        var5 *= var3 * 0.9f + 0.1f;
        var6 *= var3 * 0.85f + 0.15f;
        final float var8 = this.Ø(p_72824_1_);
        if (var8 > 0.0f) {
            final float var9 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.2f;
            final float var10 = 1.0f - var8 * 0.95f;
            var4 = var4 * var10 + var9 * (1.0f - var10);
            var5 = var5 * var10 + var9 * (1.0f - var10);
            var6 = var6 * var10 + var9 * (1.0f - var10);
        }
        return new Vec3(var4, var5, var6);
    }
    
    public Vec3 Ó(final float p_72948_1_) {
        final float var2 = this.Ý(p_72948_1_);
        return this.£à.Â(var2, p_72948_1_);
    }
    
    public BlockPos µà(final BlockPos p_175725_1_) {
        return this.à(p_175725_1_).Ø(p_175725_1_);
    }
    
    public BlockPos ˆà(final BlockPos p_175672_1_) {
        final Chunk var2 = this.à(p_175672_1_);
        BlockPos var3;
        BlockPos var4;
        for (var3 = new BlockPos(p_175672_1_.HorizonCode_Horizon_È(), var2.HorizonCode_Horizon_È() + 16, p_175672_1_.Ý()); var3.Â() >= 0; var3 = var4) {
            var4 = var3.Âµá€();
            final Material var5 = var2.Ý(var4).Ó();
            if (var5.Ø­áŒŠá() && var5 != Material.áˆºÑ¢Õ) {
                break;
            }
        }
        return var3;
    }
    
    public float à(final float p_72880_1_) {
        final float var2 = this.Ý(p_72880_1_);
        float var3 = 1.0f - (MathHelper.Â(var2 * 3.1415927f * 2.0f) * 2.0f + 0.25f);
        var3 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 1.0f);
        return var3 * var3 * 0.5f;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos pos, final Block blockIn, final int delay) {
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_175654_1_, final Block p_175654_2_, final int p_175654_3_, final int p_175654_4_) {
    }
    
    public void Â(final BlockPos p_180497_1_, final Block p_180497_2_, final int p_180497_3_, final int p_180497_4_) {
    }
    
    public void £á() {
        this.Ï­Ðƒà.HorizonCode_Horizon_È("entities");
        this.Ï­Ðƒà.HorizonCode_Horizon_È("global");
        for (int var1 = 0; var1 < this.à.size(); ++var1) {
            final Entity var2 = this.à.get(var1);
            try {
                final Entity entity = var2;
                ++entity.Œ;
                var2.á();
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Ticking entity");
                final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Entity being ticked");
                if (var2 == null) {
                    var5.HorizonCode_Horizon_È("Entity", "~~NULL~~");
                }
                else {
                    var2.HorizonCode_Horizon_È(var5);
                }
                throw new ReportedException(var3);
            }
            if (var2.ˆáŠ) {
                this.à.remove(var1--);
            }
        }
        this.Ï­Ðƒà.Ý("remove");
        this.Â.removeAll(this.Ý);
        for (int var1 = 0; var1 < this.Ý.size(); ++var1) {
            final Entity var2 = this.Ý.get(var1);
            final int var6 = var2.£Õ;
            final int var7 = var2.Œà;
            if (var2.ˆÐƒØ­à && this.Â(var6, var7, true)) {
                this.HorizonCode_Horizon_È(var6, var7).Â(var2);
            }
        }
        for (int var1 = 0; var1 < this.Ý.size(); ++var1) {
            this.Ø­áŒŠá(this.Ý.get(var1));
        }
        this.Ý.clear();
        this.Ï­Ðƒà.Ý("regular");
        for (int var1 = 0; var1 < this.Â.size(); ++var1) {
            final Entity var2 = this.Â.get(var1);
            if (var2.Æ != null) {
                if (!var2.Æ.ˆáŠ && var2.Æ.µÕ == var2) {
                    continue;
                }
                var2.Æ.µÕ = null;
                var2.Æ = null;
            }
            this.Ï­Ðƒà.HorizonCode_Horizon_È("tick");
            if (!var2.ˆáŠ) {
                try {
                    this.à(var2);
                }
                catch (Throwable var8) {
                    final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var8, "Ticking entity");
                    final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Entity being ticked");
                    var2.HorizonCode_Horizon_È(var5);
                    throw new ReportedException(var3);
                }
            }
            this.Ï­Ðƒà.Â();
            this.Ï­Ðƒà.HorizonCode_Horizon_È("remove");
            if (var2.ˆáŠ) {
                final int var6 = var2.£Õ;
                final int var7 = var2.Œà;
                if (var2.ˆÐƒØ­à && this.Â(var6, var7, true)) {
                    this.HorizonCode_Horizon_È(var6, var7).Â(var2);
                }
                this.Â.remove(var1--);
                this.Ø­áŒŠá(var2);
            }
            this.Ï­Ðƒà.Â();
        }
        this.Ï­Ðƒà.Ý("blockEntities");
        this.à¢ = true;
        final Iterator var9 = this.Âµá€.iterator();
        while (var9.hasNext()) {
            final TileEntity var10 = var9.next();
            if (!var10.Å() && var10.Ø()) {
                final BlockPos var11 = var10.á();
                if (this.Ó(var11) && this.ŠÂµà.HorizonCode_Horizon_È(var11)) {
                    try {
                        ((IUpdatePlayerListBox)var10).HorizonCode_Horizon_È();
                    }
                    catch (Throwable var13) {
                        final CrashReport var12 = CrashReport.HorizonCode_Horizon_È(var13, "Ticking block entity");
                        final CrashReportCategory var14 = var12.HorizonCode_Horizon_È("Block entity being ticked");
                        var10.HorizonCode_Horizon_È(var14);
                        throw new ReportedException(var12);
                    }
                }
            }
            if (var10.Å()) {
                var9.remove();
                this.Ø­áŒŠá.remove(var10);
                if (!this.Ó(var10.á())) {
                    continue;
                }
                this.à(var10.á()).à(var10.á());
            }
        }
        this.à¢ = false;
        if (!this.ˆá.isEmpty()) {
            this.Âµá€.removeAll(this.ˆá);
            this.Ø­áŒŠá.removeAll(this.ˆá);
            this.ˆá.clear();
        }
        this.Ï­Ðƒà.Ý("pendingBlockEntities");
        if (!this.ÇŽÉ.isEmpty()) {
            for (int var15 = 0; var15 < this.ÇŽÉ.size(); ++var15) {
                final TileEntity var16 = this.ÇŽÉ.get(var15);
                if (!var16.Å()) {
                    if (!this.Ø­áŒŠá.contains(var16)) {
                        this.HorizonCode_Horizon_È(var16);
                    }
                    if (this.Ó(var16.á())) {
                        this.à(var16.á()).HorizonCode_Horizon_È(var16.á(), var16);
                    }
                    this.áŒŠÆ(var16.á());
                }
            }
            this.ÇŽÉ.clear();
        }
        this.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.Â();
    }
    
    public boolean HorizonCode_Horizon_È(final TileEntity tile) {
        final boolean var2 = this.Ø­áŒŠá.add(tile);
        if (var2 && tile instanceof IUpdatePlayerListBox) {
            this.Âµá€.add(tile);
        }
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final Collection tileEntityCollection) {
        if (this.à¢) {
            this.ÇŽÉ.addAll(tileEntityCollection);
        }
        else {
            for (final TileEntity var3 : tileEntityCollection) {
                this.Ø­áŒŠá.add(var3);
                if (var3 instanceof IUpdatePlayerListBox) {
                    this.Âµá€.add(var3);
                }
            }
        }
    }
    
    public void à(final Entity ent) {
        this.HorizonCode_Horizon_È(ent, true);
    }
    
    public void HorizonCode_Horizon_È(final Entity p_72866_1_, final boolean p_72866_2_) {
        final int var3 = MathHelper.Ý(p_72866_1_.ŒÏ);
        final int var4 = MathHelper.Ý(p_72866_1_.Ê);
        final byte var5 = 32;
        if (!p_72866_2_ || this.HorizonCode_Horizon_È(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5, true)) {
            p_72866_1_.áˆºáˆºÈ = p_72866_1_.ŒÏ;
            p_72866_1_.ÇŽá€ = p_72866_1_.Çªà¢;
            p_72866_1_.Ï = p_72866_1_.Ê;
            p_72866_1_.á€ = p_72866_1_.É;
            p_72866_1_.Õ = p_72866_1_.áƒ;
            if (p_72866_2_ && p_72866_1_.ˆÐƒØ­à) {
                ++p_72866_1_.Œ;
                if (p_72866_1_.Æ != null) {
                    p_72866_1_.Ø­á();
                }
                else {
                    p_72866_1_.á();
                }
            }
            this.Ï­Ðƒà.HorizonCode_Horizon_È("chunkCheck");
            if (Double.isNaN(p_72866_1_.ŒÏ) || Double.isInfinite(p_72866_1_.ŒÏ)) {
                p_72866_1_.ŒÏ = p_72866_1_.áˆºáˆºÈ;
            }
            if (Double.isNaN(p_72866_1_.Çªà¢) || Double.isInfinite(p_72866_1_.Çªà¢)) {
                p_72866_1_.Çªà¢ = p_72866_1_.ÇŽá€;
            }
            if (Double.isNaN(p_72866_1_.Ê) || Double.isInfinite(p_72866_1_.Ê)) {
                p_72866_1_.Ê = p_72866_1_.Ï;
            }
            if (Double.isNaN(p_72866_1_.áƒ) || Double.isInfinite(p_72866_1_.áƒ)) {
                p_72866_1_.áƒ = p_72866_1_.Õ;
            }
            if (Double.isNaN(p_72866_1_.É) || Double.isInfinite(p_72866_1_.É)) {
                p_72866_1_.É = p_72866_1_.á€;
            }
            final int var6 = MathHelper.Ý(p_72866_1_.ŒÏ / 16.0);
            final int var7 = MathHelper.Ý(p_72866_1_.Çªà¢ / 16.0);
            final int var8 = MathHelper.Ý(p_72866_1_.Ê / 16.0);
            if (!p_72866_1_.ˆÐƒØ­à || p_72866_1_.£Õ != var6 || p_72866_1_.Ï­Ô != var7 || p_72866_1_.Œà != var8) {
                if (p_72866_1_.ˆÐƒØ­à && this.Â(p_72866_1_.£Õ, p_72866_1_.Œà, true)) {
                    this.HorizonCode_Horizon_È(p_72866_1_.£Õ, p_72866_1_.Œà).HorizonCode_Horizon_È(p_72866_1_, p_72866_1_.Ï­Ô);
                }
                if (this.Â(var6, var8, true)) {
                    p_72866_1_.ˆÐƒØ­à = true;
                    this.HorizonCode_Horizon_È(var6, var8).HorizonCode_Horizon_È(p_72866_1_);
                }
                else {
                    p_72866_1_.ˆÐƒØ­à = false;
                }
            }
            this.Ï­Ðƒà.Â();
            if (p_72866_2_ && p_72866_1_.ˆÐƒØ­à && p_72866_1_.µÕ != null) {
                if (!p_72866_1_.µÕ.ˆáŠ && p_72866_1_.µÕ.Æ == p_72866_1_) {
                    this.à(p_72866_1_.µÕ);
                }
                else {
                    p_72866_1_.µÕ.Æ = null;
                    p_72866_1_.µÕ = null;
                }
            }
        }
    }
    
    public boolean Â(final AxisAlignedBB p_72855_1_) {
        return this.HorizonCode_Horizon_È(p_72855_1_, (Entity)null);
    }
    
    public boolean HorizonCode_Horizon_È(final AxisAlignedBB p_72917_1_, final Entity p_72917_2_) {
        final List var3 = this.Â(null, p_72917_1_);
        for (int var4 = 0; var4 < var3.size(); ++var4) {
            final Entity var5 = var3.get(var4);
            if (!var5.ˆáŠ && var5.Ø­à && var5 != p_72917_2_ && (p_72917_2_ == null || (p_72917_2_.Æ != var5 && p_72917_2_.µÕ != var5))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean Ý(final AxisAlignedBB p_72829_1_) {
        final int var2 = MathHelper.Ý(p_72829_1_.HorizonCode_Horizon_È);
        final int var3 = MathHelper.Ý(p_72829_1_.Ø­áŒŠá);
        final int var4 = MathHelper.Ý(p_72829_1_.Â);
        final int var5 = MathHelper.Ý(p_72829_1_.Âµá€);
        final int var6 = MathHelper.Ý(p_72829_1_.Ý);
        final int var7 = MathHelper.Ý(p_72829_1_.Ó);
        for (int var8 = var2; var8 <= var3; ++var8) {
            for (int var9 = var4; var9 <= var5; ++var9) {
                for (int var10 = var6; var10 <= var7; ++var10) {
                    final Block var11 = this.Â(new BlockPos(var8, var9, var10)).Ý();
                    if (var11.Ó() != Material.HorizonCode_Horizon_È) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean Ø­áŒŠá(final AxisAlignedBB p_72953_1_) {
        final int var2 = MathHelper.Ý(p_72953_1_.HorizonCode_Horizon_È);
        final int var3 = MathHelper.Ý(p_72953_1_.Ø­áŒŠá);
        final int var4 = MathHelper.Ý(p_72953_1_.Â);
        final int var5 = MathHelper.Ý(p_72953_1_.Âµá€);
        final int var6 = MathHelper.Ý(p_72953_1_.Ý);
        final int var7 = MathHelper.Ý(p_72953_1_.Ó);
        for (int var8 = var2; var8 <= var3; ++var8) {
            for (int var9 = var4; var9 <= var5; ++var9) {
                for (int var10 = var6; var10 <= var7; ++var10) {
                    final Block var11 = this.Â(new BlockPos(var8, var9, var10)).Ý();
                    if (var11.Ó().HorizonCode_Horizon_È()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean Âµá€(final AxisAlignedBB p_147470_1_) {
        final int var2 = MathHelper.Ý(p_147470_1_.HorizonCode_Horizon_È);
        final int var3 = MathHelper.Ý(p_147470_1_.Ø­áŒŠá + 1.0);
        final int var4 = MathHelper.Ý(p_147470_1_.Â);
        final int var5 = MathHelper.Ý(p_147470_1_.Âµá€ + 1.0);
        final int var6 = MathHelper.Ý(p_147470_1_.Ý);
        final int var7 = MathHelper.Ý(p_147470_1_.Ó + 1.0);
        if (this.HorizonCode_Horizon_È(var2, var4, var6, var3, var5, var7, true)) {
            for (int var8 = var2; var8 < var3; ++var8) {
                for (int var9 = var4; var9 < var5; ++var9) {
                    for (int var10 = var6; var10 < var7; ++var10) {
                        final Block var11 = this.Â(new BlockPos(var8, var9, var10)).Ý();
                        if (var11 == Blocks.Ô || var11 == Blocks.á || var11 == Blocks.ˆÏ­) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean HorizonCode_Horizon_È(final AxisAlignedBB p_72918_1_, final Material p_72918_2_, final Entity p_72918_3_) {
        final int var4 = MathHelper.Ý(p_72918_1_.HorizonCode_Horizon_È);
        final int var5 = MathHelper.Ý(p_72918_1_.Ø­áŒŠá + 1.0);
        final int var6 = MathHelper.Ý(p_72918_1_.Â);
        final int var7 = MathHelper.Ý(p_72918_1_.Âµá€ + 1.0);
        final int var8 = MathHelper.Ý(p_72918_1_.Ý);
        final int var9 = MathHelper.Ý(p_72918_1_.Ó + 1.0);
        if (!this.HorizonCode_Horizon_È(var4, var6, var8, var5, var7, var9, true)) {
            return false;
        }
        boolean var10 = false;
        Vec3 var11 = new Vec3(0.0, 0.0, 0.0);
        for (int var12 = var4; var12 < var5; ++var12) {
            for (int var13 = var6; var13 < var7; ++var13) {
                for (int var14 = var8; var14 < var9; ++var14) {
                    final BlockPos var15 = new BlockPos(var12, var13, var14);
                    final IBlockState var16 = this.Â(var15);
                    final Block var17 = var16.Ý();
                    if (var17.Ó() == p_72918_2_) {
                        final double var18 = var13 + 1 - BlockLiquid.Âµá€((int)var16.HorizonCode_Horizon_È(BlockLiquid.à¢));
                        if (var7 >= var18) {
                            var10 = true;
                            var11 = var17.HorizonCode_Horizon_È(this, var15, p_72918_3_, var11);
                        }
                    }
                }
            }
        }
        if (var11.Â() > 0.0 && p_72918_3_.áˆº()) {
            var11 = var11.HorizonCode_Horizon_È();
            final double var19 = 0.014;
            p_72918_3_.ÇŽÉ += var11.HorizonCode_Horizon_È * var19;
            p_72918_3_.ˆá += var11.Â * var19;
            p_72918_3_.ÇŽÕ += var11.Ý * var19;
        }
        return var10;
    }
    
    public boolean HorizonCode_Horizon_È(final AxisAlignedBB p_72875_1_, final Material p_72875_2_) {
        final int var3 = MathHelper.Ý(p_72875_1_.HorizonCode_Horizon_È);
        final int var4 = MathHelper.Ý(p_72875_1_.Ø­áŒŠá + 1.0);
        final int var5 = MathHelper.Ý(p_72875_1_.Â);
        final int var6 = MathHelper.Ý(p_72875_1_.Âµá€ + 1.0);
        final int var7 = MathHelper.Ý(p_72875_1_.Ý);
        final int var8 = MathHelper.Ý(p_72875_1_.Ó + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    if (this.Â(new BlockPos(var9, var10, var11)).Ý().Ó() == p_72875_2_) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean Â(final AxisAlignedBB p_72830_1_, final Material p_72830_2_) {
        final int var3 = MathHelper.Ý(p_72830_1_.HorizonCode_Horizon_È);
        final int var4 = MathHelper.Ý(p_72830_1_.Ø­áŒŠá + 1.0);
        final int var5 = MathHelper.Ý(p_72830_1_.Â);
        final int var6 = MathHelper.Ý(p_72830_1_.Âµá€ + 1.0);
        final int var7 = MathHelper.Ý(p_72830_1_.Ý);
        final int var8 = MathHelper.Ý(p_72830_1_.Ó + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    final BlockPos var12 = new BlockPos(var9, var10, var11);
                    final IBlockState var13 = this.Â(var12);
                    final Block var14 = var13.Ý();
                    if (var14.Ó() == p_72830_2_) {
                        final int var15 = (int)var13.HorizonCode_Horizon_È(BlockLiquid.à¢);
                        double var16 = var10 + 1;
                        if (var15 < 8) {
                            var16 = var10 + 1 - var15 / 8.0;
                        }
                        if (var16 >= p_72830_1_.Â) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public Explosion HorizonCode_Horizon_È(final Entity p_72876_1_, final double p_72876_2_, final double p_72876_4_, final double p_72876_6_, final float p_72876_8_, final boolean p_72876_9_) {
        return this.HorizonCode_Horizon_È(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
    }
    
    public Explosion HorizonCode_Horizon_È(final Entity p_72885_1_, final double p_72885_2_, final double p_72885_4_, final double p_72885_6_, final float p_72885_8_, final boolean p_72885_9_, final boolean p_72885_10_) {
        final Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
        var11.HorizonCode_Horizon_È();
        var11.HorizonCode_Horizon_È(true);
        return var11;
    }
    
    public float HorizonCode_Horizon_È(final Vec3 p_72842_1_, final AxisAlignedBB p_72842_2_) {
        final double var3 = 1.0 / ((p_72842_2_.Ø­áŒŠá - p_72842_2_.HorizonCode_Horizon_È) * 2.0 + 1.0);
        final double var4 = 1.0 / ((p_72842_2_.Âµá€ - p_72842_2_.Â) * 2.0 + 1.0);
        final double var5 = 1.0 / ((p_72842_2_.Ó - p_72842_2_.Ý) * 2.0 + 1.0);
        if (var3 >= 0.0 && var4 >= 0.0 && var5 >= 0.0) {
            int var6 = 0;
            int var7 = 0;
            for (float var8 = 0.0f; var8 <= 1.0f; var8 += (float)var3) {
                for (float var9 = 0.0f; var9 <= 1.0f; var9 += (float)var4) {
                    for (float var10 = 0.0f; var10 <= 1.0f; var10 += (float)var5) {
                        final double var11 = p_72842_2_.HorizonCode_Horizon_È + (p_72842_2_.Ø­áŒŠá - p_72842_2_.HorizonCode_Horizon_È) * var8;
                        final double var12 = p_72842_2_.Â + (p_72842_2_.Âµá€ - p_72842_2_.Â) * var9;
                        final double var13 = p_72842_2_.Ý + (p_72842_2_.Ó - p_72842_2_.Ý) * var10;
                        if (this.HorizonCode_Horizon_È(new Vec3(var11, var12, var13), p_72842_1_) == null) {
                            ++var6;
                        }
                        ++var7;
                    }
                }
            }
            return var6 / var7;
        }
        return 0.0f;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_175719_1_, BlockPos p_175719_2_, final EnumFacing p_175719_3_) {
        p_175719_2_ = p_175719_2_.HorizonCode_Horizon_È(p_175719_3_);
        if (this.Â(p_175719_2_).Ý() == Blocks.Ô) {
            this.HorizonCode_Horizon_È(p_175719_1_, 1004, p_175719_2_, 0);
            this.Ø(p_175719_2_);
            return true;
        }
        return false;
    }
    
    public String Å() {
        return "All: " + this.Â.size();
    }
    
    public String £à() {
        return this.ˆà.Ø­áŒŠá();
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final BlockPos pos) {
        if (!this.ÇŽÉ(pos)) {
            return null;
        }
        TileEntity var2 = null;
        if (this.à¢) {
            for (int var3 = 0; var3 < this.ÇŽÉ.size(); ++var3) {
                final TileEntity var4 = this.ÇŽÉ.get(var3);
                if (!var4.Å() && var4.á().equals(pos)) {
                    var2 = var4;
                    break;
                }
            }
        }
        if (var2 == null) {
            var2 = this.à(pos).HorizonCode_Horizon_È(pos, Chunk.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        if (var2 == null) {
            for (int var3 = 0; var3 < this.ÇŽÉ.size(); ++var3) {
                final TileEntity var4 = this.ÇŽÉ.get(var3);
                if (!var4.Å() && var4.á().equals(pos)) {
                    var2 = var4;
                    break;
                }
            }
        }
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_175690_1_, final TileEntity p_175690_2_) {
        if (p_175690_2_ != null && !p_175690_2_.Å()) {
            if (this.à¢) {
                p_175690_2_.HorizonCode_Horizon_È(p_175690_1_);
                final Iterator var3 = this.ÇŽÉ.iterator();
                while (var3.hasNext()) {
                    final TileEntity var4 = var3.next();
                    if (var4.á().equals(p_175690_1_)) {
                        var4.£à();
                        var3.remove();
                    }
                }
                this.ÇŽÉ.add(p_175690_2_);
            }
            else {
                this.HorizonCode_Horizon_È(p_175690_2_);
                this.à(p_175690_1_).HorizonCode_Horizon_È(p_175690_1_, p_175690_2_);
            }
        }
    }
    
    public void ¥Æ(final BlockPos pos) {
        final TileEntity var2 = this.HorizonCode_Horizon_È(pos);
        if (var2 != null && this.à¢) {
            var2.£à();
            this.ÇŽÉ.remove(var2);
        }
        else {
            if (var2 != null) {
                this.ÇŽÉ.remove(var2);
                this.Ø­áŒŠá.remove(var2);
                this.Âµá€.remove(var2);
            }
            this.à(pos).à(pos);
        }
    }
    
    public void Â(final TileEntity tileEntityIn) {
        this.ˆá.add(tileEntityIn);
    }
    
    public boolean Ø­à(final BlockPos p_175665_1_) {
        final IBlockState var2 = this.Â(p_175665_1_);
        final AxisAlignedBB var3 = var2.Ý().HorizonCode_Horizon_È(this, p_175665_1_, var2);
        return var3 != null && var3.HorizonCode_Horizon_È() >= 1.0;
    }
    
    public static boolean HorizonCode_Horizon_È(final IBlockAccess p_175683_0_, final BlockPos p_175683_1_) {
        final IBlockState var2 = p_175683_0_.Â(p_175683_1_);
        final Block var3 = var2.Ý();
        return (var3.Ó().áˆºÑ¢Õ() && var3.áˆºÑ¢Õ()) || ((var3 instanceof BlockStairs) ? (var2.HorizonCode_Horizon_È(BlockStairs.à¢) == BlockStairs.HorizonCode_Horizon_È.HorizonCode_Horizon_È) : ((var3 instanceof BlockSlab) ? (var2.HorizonCode_Horizon_È(BlockSlab.Õ) == BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È) : (var3 instanceof BlockHopper || (var3 instanceof BlockSnow && (int)var2.HorizonCode_Horizon_È(BlockSnow.Õ) == 7))));
    }
    
    public boolean Ø­áŒŠá(final BlockPos p_175677_1_, final boolean p_175677_2_) {
        if (!this.ÇŽÉ(p_175677_1_)) {
            return p_175677_2_;
        }
        final Chunk var3 = this.ˆà.HorizonCode_Horizon_È(p_175677_1_);
        if (var3.Ø()) {
            return p_175677_2_;
        }
        final Block var4 = this.Â(p_175677_1_).Ý();
        return var4.Ó().áˆºÑ¢Õ() && var4.áˆºÑ¢Õ();
    }
    
    public void µà() {
        final int var1 = this.HorizonCode_Horizon_È(1.0f);
        if (var1 != this.É) {
            this.É = var1;
        }
    }
    
    public void HorizonCode_Horizon_È(final boolean hostile, final boolean peaceful) {
        this.ŒÏ = hostile;
        this.Çªà¢ = peaceful;
    }
    
    public void r_() {
        this.Âµá€();
    }
    
    protected void ˆà() {
        if (this.Ø­à.Å()) {
            this.á = 1.0f;
            if (this.Ø­à.ˆÏ­()) {
                this.£á = 1.0f;
            }
        }
    }
    
    protected void Âµá€() {
        if (!this.£à.Å() && !this.ŠÄ) {
            int var1 = this.Ø­à.ŠÄ();
            if (var1 > 0) {
                --var1;
                this.Ø­à.à(var1);
                this.Ø­à.Âµá€(this.Ø­à.ˆÏ­() ? 1 : 2);
                this.Ø­à.Ó(this.Ø­à.Å() ? 1 : 2);
            }
            int var2 = this.Ø­à.£á();
            if (var2 <= 0) {
                if (this.Ø­à.ˆÏ­()) {
                    this.Ø­à.Âµá€(this.Å.nextInt(12000) + 3600);
                }
                else {
                    this.Ø­à.Âµá€(this.Å.nextInt(168000) + 12000);
                }
            }
            else {
                --var2;
                this.Ø­à.Âµá€(var2);
                if (var2 <= 0) {
                    this.Ø­à.HorizonCode_Horizon_È(!this.Ø­à.ˆÏ­());
                }
            }
            this.ˆÏ­ = this.£á;
            if (this.Ø­à.ˆÏ­()) {
                this.£á += 0.01;
            }
            else {
                this.£á -= 0.01;
            }
            this.£á = MathHelper.HorizonCode_Horizon_È(this.£á, 0.0f, 1.0f);
            int var3 = this.Ø­à.£à();
            if (var3 <= 0) {
                if (this.Ø­à.Å()) {
                    this.Ø­à.Ó(this.Å.nextInt(12000) + 12000);
                }
                else {
                    this.Ø­à.Ó(this.Å.nextInt(168000) + 12000);
                }
            }
            else {
                --var3;
                this.Ø­à.Ó(var3);
                if (var3 <= 0) {
                    this.Ø­à.Â(!this.Ø­à.Å());
                }
            }
            this.ÂµÈ = this.á;
            if (this.Ø­à.Å()) {
                this.á += 0.01;
            }
            else {
                this.á -= 0.01;
            }
            this.á = MathHelper.HorizonCode_Horizon_È(this.á, 0.0f, 1.0f);
        }
    }
    
    protected void ¥Æ() {
        this.Ñ¢á.clear();
        this.Ï­Ðƒà.HorizonCode_Horizon_È("buildList");
        for (int var1 = 0; var1 < this.Ó.size(); ++var1) {
            final EntityPlayer var2 = this.Ó.get(var1);
            final int var3 = MathHelper.Ý(var2.ŒÏ / 16.0);
            final int var4 = MathHelper.Ý(var2.Ê / 16.0);
            for (int var5 = this.Ó(), var6 = -var5; var6 <= var5; ++var6) {
                for (int var7 = -var5; var7 <= var5; ++var7) {
                    this.Ñ¢á.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
                }
            }
        }
        this.Ï­Ðƒà.Â();
        if (this.Õ > 0) {
            --this.Õ;
        }
        this.Ï­Ðƒà.HorizonCode_Horizon_È("playerCheckLight");
        if (!this.Ó.isEmpty()) {
            final int var1 = this.Å.nextInt(this.Ó.size());
            final EntityPlayer var2 = this.Ó.get(var1);
            final int var3 = MathHelper.Ý(var2.ŒÏ) + this.Å.nextInt(11) - 5;
            final int var4 = MathHelper.Ý(var2.Çªà¢) + this.Å.nextInt(11) - 5;
            final int var5 = MathHelper.Ý(var2.Ê) + this.Å.nextInt(11) - 5;
            this.Šáƒ(new BlockPos(var3, var4, var5));
        }
        this.Ï­Ðƒà.Â();
    }
    
    protected abstract int Ó();
    
    protected void HorizonCode_Horizon_È(final int p_147467_1_, final int p_147467_2_, final Chunk p_147467_3_) {
        this.Ï­Ðƒà.Ý("moodSound");
        if (this.Õ == 0 && !this.ŠÄ) {
            this.áŒŠÆ = this.áŒŠÆ * 3 + 1013904223;
            final int var4 = this.áŒŠÆ >> 2;
            int var5 = var4 & 0xF;
            int var6 = var4 >> 8 & 0xF;
            final int var7 = var4 >> 16 & 0xFF;
            final BlockPos var8 = new BlockPos(var5, var7, var6);
            final Block var9 = p_147467_3_.Ý(var8);
            var5 += p_147467_1_;
            var6 += p_147467_2_;
            if (var9.Ó() == Material.HorizonCode_Horizon_È && this.á(var8) <= this.Å.nextInt(8) && this.Â(EnumSkyBlock.HorizonCode_Horizon_È, var8) <= 0) {
                final EntityPlayer var10 = this.HorizonCode_Horizon_È(var5 + 0.5, var7 + 0.5, var6 + 0.5, 8.0);
                if (var10 != null && var10.Âµá€(var5 + 0.5, var7 + 0.5, var6 + 0.5) > 4.0) {
                    this.HorizonCode_Horizon_È(var5 + 0.5, var7 + 0.5, var6 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.Å.nextFloat() * 0.2f);
                    this.Õ = this.Å.nextInt(12000) + 6000;
                }
            }
        }
        this.Ï­Ðƒà.Ý("checkLight");
        p_147467_3_.ˆÏ­();
    }
    
    protected void Ý() {
        this.¥Æ();
    }
    
    public void HorizonCode_Horizon_È(final Block p_175637_1_, final BlockPos p_175637_2_, final Random p_175637_3_) {
        this.HorizonCode_Horizon_È = true;
        p_175637_1_.Â(this, p_175637_2_, this.Â(p_175637_2_), p_175637_3_);
        this.HorizonCode_Horizon_È = false;
    }
    
    public boolean µÕ(final BlockPos p_175675_1_) {
        return this.Âµá€(p_175675_1_, false);
    }
    
    public boolean Æ(final BlockPos p_175662_1_) {
        return this.Âµá€(p_175662_1_, true);
    }
    
    public boolean Âµá€(final BlockPos p_175670_1_, final boolean p_175670_2_) {
        final BiomeGenBase var3 = this.Ý(p_175670_1_);
        final float var4 = var3.HorizonCode_Horizon_È(p_175670_1_);
        if (var4 > 0.15f) {
            return false;
        }
        if (p_175670_1_.Â() >= 0 && p_175670_1_.Â() < 256 && this.Â(EnumSkyBlock.Â, p_175670_1_) < 10) {
            final IBlockState var5 = this.Â(p_175670_1_);
            final Block var6 = var5.Ý();
            if ((var6 == Blocks.ÂµÈ || var6 == Blocks.áˆºÑ¢Õ) && (int)var5.HorizonCode_Horizon_È(BlockLiquid.à¢) == 0) {
                if (!p_175670_2_) {
                    return true;
                }
                final boolean var7 = this.ˆá(p_175670_1_.Ø()) && this.ˆá(p_175670_1_.áŒŠÆ()) && this.ˆá(p_175670_1_.Ó()) && this.ˆá(p_175670_1_.à());
                if (!var7) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean ˆá(final BlockPos p_175696_1_) {
        return this.Â(p_175696_1_).Ý().Ó() == Material.Ø;
    }
    
    public boolean Ó(final BlockPos p_175708_1_, final boolean p_175708_2_) {
        final BiomeGenBase var3 = this.Ý(p_175708_1_);
        final float var4 = var3.HorizonCode_Horizon_È(p_175708_1_);
        if (var4 > 0.15f) {
            return false;
        }
        if (!p_175708_2_) {
            return true;
        }
        if (p_175708_1_.Â() >= 0 && p_175708_1_.Â() < 256 && this.Â(EnumSkyBlock.Â, p_175708_1_) < 10) {
            final Block var5 = this.Â(p_175708_1_).Ý();
            if (var5.Ó() == Material.HorizonCode_Horizon_È && Blocks.áŒŠá€.Ø­áŒŠá(this, p_175708_1_)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean Šáƒ(final BlockPos p_175664_1_) {
        boolean var2 = false;
        if (!this.£à.Å()) {
            var2 |= this.Ý(EnumSkyBlock.HorizonCode_Horizon_È, p_175664_1_);
        }
        var2 |= this.Ý(EnumSkyBlock.Â, p_175664_1_);
        return var2;
    }
    
    private int HorizonCode_Horizon_È(final BlockPos p_175638_1_, final EnumSkyBlock p_175638_2_) {
        if (p_175638_2_ == EnumSkyBlock.HorizonCode_Horizon_È && this.áˆºÑ¢Õ(p_175638_1_)) {
            return 15;
        }
        final Block var3 = this.Â(p_175638_1_).Ý();
        int var4 = (p_175638_2_ == EnumSkyBlock.HorizonCode_Horizon_È) ? 0 : var3.Ø­áŒŠá();
        int var5 = var3.Â();
        if (var5 >= 15 && var3.Ø­áŒŠá() > 0) {
            var5 = 1;
        }
        if (var5 < 1) {
            var5 = 1;
        }
        if (var5 >= 15) {
            return 0;
        }
        if (var4 >= 14) {
            return var4;
        }
        for (final EnumFacing var9 : EnumFacing.values()) {
            final BlockPos var10 = p_175638_1_.HorizonCode_Horizon_È(var9);
            final int var11 = this.Â(p_175638_2_, var10) - var5;
            if (var11 > var4) {
                var4 = var11;
            }
            if (var4 >= 14) {
                return var4;
            }
        }
        return var4;
    }
    
    public boolean Ý(final EnumSkyBlock p_180500_1_, final BlockPos p_180500_2_) {
        if (!this.HorizonCode_Horizon_È(p_180500_2_, 17, false)) {
            return false;
        }
        int var3 = 0;
        int var4 = 0;
        this.Ï­Ðƒà.HorizonCode_Horizon_È("getBrightness");
        final int var5 = this.Â(p_180500_1_, p_180500_2_);
        final int var6 = this.HorizonCode_Horizon_È(p_180500_2_, p_180500_1_);
        final int var7 = p_180500_2_.HorizonCode_Horizon_È();
        final int var8 = p_180500_2_.Â();
        final int var9 = p_180500_2_.Ý();
        if (var6 > var5) {
            this.Ê[var4++] = 133152;
        }
        else if (var6 < var5) {
            this.Ê[var4++] = (0x20820 | var5 << 18);
            while (var3 < var4) {
                final int var10 = this.Ê[var3++];
                final int var11 = (var10 & 0x3F) - 32 + var7;
                final int var12 = (var10 >> 6 & 0x3F) - 32 + var8;
                final int var13 = (var10 >> 12 & 0x3F) - 32 + var9;
                final int var14 = var10 >> 18 & 0xF;
                final BlockPos var15 = new BlockPos(var11, var12, var13);
                int var16 = this.Â(p_180500_1_, var15);
                if (var16 == var14) {
                    this.HorizonCode_Horizon_È(p_180500_1_, var15, 0);
                    if (var14 <= 0) {
                        continue;
                    }
                    final int var17 = MathHelper.HorizonCode_Horizon_È(var11 - var7);
                    final int var18 = MathHelper.HorizonCode_Horizon_È(var12 - var8);
                    final int var19 = MathHelper.HorizonCode_Horizon_È(var13 - var9);
                    if (var17 + var18 + var19 >= 17) {
                        continue;
                    }
                    for (final EnumFacing var23 : EnumFacing.values()) {
                        final int var24 = var11 + var23.Ø();
                        final int var25 = var12 + var23.áŒŠÆ();
                        final int var26 = var13 + var23.áˆºÑ¢Õ();
                        final BlockPos var27 = new BlockPos(var24, var25, var26);
                        final int var28 = Math.max(1, this.Â(var27).Ý().Â());
                        var16 = this.Â(p_180500_1_, var27);
                        if (var16 == var14 - var28 && var4 < this.Ê.length) {
                            this.Ê[var4++] = (var24 - var7 + 32 | var25 - var8 + 32 << 6 | var26 - var9 + 32 << 12 | var14 - var28 << 18);
                        }
                    }
                }
            }
            var3 = 0;
        }
        this.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.HorizonCode_Horizon_È("checkedPosition < toCheckCount");
        while (var3 < var4) {
            final int var10 = this.Ê[var3++];
            final int var11 = (var10 & 0x3F) - 32 + var7;
            final int var12 = (var10 >> 6 & 0x3F) - 32 + var8;
            final int var13 = (var10 >> 12 & 0x3F) - 32 + var9;
            final BlockPos var29 = new BlockPos(var11, var12, var13);
            final int var30 = this.Â(p_180500_1_, var29);
            final int var16 = this.HorizonCode_Horizon_È(var29, p_180500_1_);
            if (var16 != var30) {
                this.HorizonCode_Horizon_È(p_180500_1_, var29, var16);
                if (var16 <= var30) {
                    continue;
                }
                final int var17 = Math.abs(var11 - var7);
                final int var18 = Math.abs(var12 - var8);
                final int var19 = Math.abs(var13 - var9);
                final boolean var31 = var4 < this.Ê.length - 6;
                if (var17 + var18 + var19 >= 17 || !var31) {
                    continue;
                }
                if (this.Â(p_180500_1_, var29.Ø()) < var16) {
                    this.Ê[var4++] = var11 - 1 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                }
                if (this.Â(p_180500_1_, var29.áŒŠÆ()) < var16) {
                    this.Ê[var4++] = var11 + 1 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                }
                if (this.Â(p_180500_1_, var29.Âµá€()) < var16) {
                    this.Ê[var4++] = var11 - var7 + 32 + (var12 - 1 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                }
                if (this.Â(p_180500_1_, var29.Ø­áŒŠá()) < var16) {
                    this.Ê[var4++] = var11 - var7 + 32 + (var12 + 1 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                }
                if (this.Â(p_180500_1_, var29.Ó()) < var16) {
                    this.Ê[var4++] = var11 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - 1 - var9 + 32 << 12);
                }
                if (this.Â(p_180500_1_, var29.à()) >= var16) {
                    continue;
                }
                this.Ê[var4++] = var11 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 + 1 - var9 + 32 << 12);
            }
        }
        this.Ï­Ðƒà.Â();
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final boolean p_72955_1_) {
        return false;
    }
    
    public List HorizonCode_Horizon_È(final Chunk p_72920_1_, final boolean p_72920_2_) {
        return null;
    }
    
    public List Â(final StructureBoundingBox p_175712_1_, final boolean p_175712_2_) {
        return null;
    }
    
    public List Â(final Entity p_72839_1_, final AxisAlignedBB p_72839_2_) {
        return this.HorizonCode_Horizon_È(p_72839_1_, p_72839_2_, IEntitySelector.Ø­áŒŠá);
    }
    
    public List HorizonCode_Horizon_È(final Entity p_175674_1_, final AxisAlignedBB p_175674_2_, final Predicate p_175674_3_) {
        final ArrayList var4 = Lists.newArrayList();
        final int var5 = MathHelper.Ý((p_175674_2_.HorizonCode_Horizon_È - 2.0) / 16.0);
        final int var6 = MathHelper.Ý((p_175674_2_.Ø­áŒŠá + 2.0) / 16.0);
        final int var7 = MathHelper.Ý((p_175674_2_.Ý - 2.0) / 16.0);
        final int var8 = MathHelper.Ý((p_175674_2_.Ó + 2.0) / 16.0);
        for (int var9 = var5; var9 <= var6; ++var9) {
            for (int var10 = var7; var10 <= var8; ++var10) {
                if (this.Â(var9, var10, true)) {
                    this.HorizonCode_Horizon_È(var9, var10).HorizonCode_Horizon_È(p_175674_1_, p_175674_2_, var4, p_175674_3_);
                }
            }
        }
        return var4;
    }
    
    public List HorizonCode_Horizon_È(final Class p_175644_1_, final Predicate p_175644_2_) {
        final ArrayList var3 = Lists.newArrayList();
        for (final Entity var5 : this.Â) {
            if (p_175644_1_.isAssignableFrom(var5.getClass()) && p_175644_2_.apply((Object)var5)) {
                var3.add(var5);
            }
        }
        return var3;
    }
    
    public List Â(final Class p_175661_1_, final Predicate p_175661_2_) {
        final ArrayList var3 = Lists.newArrayList();
        for (final Entity var5 : this.Ó) {
            if (p_175661_1_.isAssignableFrom(var5.getClass()) && p_175661_2_.apply((Object)var5)) {
                var3.add(var5);
            }
        }
        return var3;
    }
    
    public List HorizonCode_Horizon_È(final Class p_72872_1_, final AxisAlignedBB p_72872_2_) {
        return this.HorizonCode_Horizon_È(p_72872_1_, p_72872_2_, IEntitySelector.Ø­áŒŠá);
    }
    
    public List HorizonCode_Horizon_È(final Class p_175647_1_, final AxisAlignedBB p_175647_2_, final Predicate p_175647_3_) {
        final int var4 = MathHelper.Ý((p_175647_2_.HorizonCode_Horizon_È - 2.0) / 16.0);
        final int var5 = MathHelper.Ý((p_175647_2_.Ø­áŒŠá + 2.0) / 16.0);
        final int var6 = MathHelper.Ý((p_175647_2_.Ý - 2.0) / 16.0);
        final int var7 = MathHelper.Ý((p_175647_2_.Ó + 2.0) / 16.0);
        final ArrayList var8 = Lists.newArrayList();
        for (int var9 = var4; var9 <= var5; ++var9) {
            for (int var10 = var6; var10 <= var7; ++var10) {
                if (this.Â(var9, var10, true)) {
                    this.HorizonCode_Horizon_È(var9, var10).HorizonCode_Horizon_È(p_175647_1_, p_175647_2_, var8, p_175647_3_);
                }
            }
        }
        return var8;
    }
    
    public Entity HorizonCode_Horizon_È(final Class p_72857_1_, final AxisAlignedBB p_72857_2_, final Entity p_72857_3_) {
        final List var4 = this.HorizonCode_Horizon_È(p_72857_1_, p_72857_2_);
        Entity var5 = null;
        double var6 = Double.MAX_VALUE;
        for (int var7 = 0; var7 < var4.size(); ++var7) {
            final Entity var8 = var4.get(var7);
            if (var8 != p_72857_3_ && IEntitySelector.Ø­áŒŠá.apply((Object)var8)) {
                final double var9 = p_72857_3_.Âµá€(var8);
                if (var9 <= var6) {
                    var5 = var8;
                    var6 = var9;
                }
            }
        }
        return var5;
    }
    
    public Entity HorizonCode_Horizon_È(final int p_73045_1_) {
        return (Entity)this.Ø.HorizonCode_Horizon_È(p_73045_1_);
    }
    
    public List Ø­à() {
        return this.Â;
    }
    
    public void Â(final BlockPos p_175646_1_, final TileEntity p_175646_2_) {
        if (this.Ó(p_175646_1_)) {
            this.à(p_175646_1_).à();
        }
    }
    
    public int HorizonCode_Horizon_È(final Class entityType) {
        int var2 = 0;
        for (final Entity var4 : this.Â) {
            if ((!(var4 instanceof EntityLiving) || !((EntityLiving)var4).ÇªÉ()) && entityType.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
        }
        return var2;
    }
    
    public void Â(final Collection entityCollection) {
        this.Â.addAll(entityCollection);
        for (final Entity var3 : entityCollection) {
            this.Ý(var3);
        }
    }
    
    public void Ý(final Collection entityCollection) {
        this.Ý.addAll(entityCollection);
    }
    
    public boolean HorizonCode_Horizon_È(final Block p_175716_1_, final BlockPos p_175716_2_, final boolean p_175716_3_, final EnumFacing p_175716_4_, final Entity p_175716_5_, final ItemStack p_175716_6_) {
        final Block var7 = this.Â(p_175716_2_).Ý();
        final AxisAlignedBB var8 = p_175716_3_ ? null : p_175716_1_.HorizonCode_Horizon_È(this, p_175716_2_, p_175716_1_.¥à());
        return (var8 == null || this.HorizonCode_Horizon_È(var8, p_175716_5_)) && ((var7.Ó() == Material.µà && p_175716_1_ == Blocks.ÇªÅ) || (var7.Ó().áŒŠÆ() && p_175716_1_.HorizonCode_Horizon_È(this, p_175716_2_, p_175716_4_, p_175716_6_)));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final BlockPos pos, final EnumFacing direction) {
        final IBlockState var3 = this.Â(pos);
        return var3.Ý().Â(this, pos, var3, direction);
    }
    
    @Override
    public WorldType s_() {
        return this.Ø­à.Ø­à();
    }
    
    public int Ï­Ðƒà(final BlockPos pos) {
        final byte var2 = 0;
        int var3 = Math.max(var2, this.HorizonCode_Horizon_È(pos.Âµá€(), EnumFacing.HorizonCode_Horizon_È));
        if (var3 >= 15) {
            return var3;
        }
        var3 = Math.max(var3, this.HorizonCode_Horizon_È(pos.Ø­áŒŠá(), EnumFacing.Â));
        if (var3 >= 15) {
            return var3;
        }
        var3 = Math.max(var3, this.HorizonCode_Horizon_È(pos.Ó(), EnumFacing.Ý));
        if (var3 >= 15) {
            return var3;
        }
        var3 = Math.max(var3, this.HorizonCode_Horizon_È(pos.à(), EnumFacing.Ø­áŒŠá));
        if (var3 >= 15) {
            return var3;
        }
        var3 = Math.max(var3, this.HorizonCode_Horizon_È(pos.Ø(), EnumFacing.Âµá€));
        if (var3 >= 15) {
            return var3;
        }
        var3 = Math.max(var3, this.HorizonCode_Horizon_È(pos.áŒŠÆ(), EnumFacing.Ó));
        return (var3 >= 15) ? var3 : var3;
    }
    
    public boolean Â(final BlockPos p_175709_1_, final EnumFacing p_175709_2_) {
        return this.Ý(p_175709_1_, p_175709_2_) > 0;
    }
    
    public int Ý(final BlockPos pos, final EnumFacing facing) {
        final IBlockState var3 = this.Â(pos);
        final Block var4 = var3.Ý();
        return var4.Ø() ? this.Ï­Ðƒà(pos) : var4.HorizonCode_Horizon_È(this, pos, var3, facing);
    }
    
    public boolean áŒŠà(final BlockPos pos) {
        return this.Ý(pos.Âµá€(), EnumFacing.HorizonCode_Horizon_È) > 0 || this.Ý(pos.Ø­áŒŠá(), EnumFacing.Â) > 0 || this.Ý(pos.Ó(), EnumFacing.Ý) > 0 || this.Ý(pos.à(), EnumFacing.Ø­áŒŠá) > 0 || this.Ý(pos.Ø(), EnumFacing.Âµá€) > 0 || this.Ý(pos.áŒŠÆ(), EnumFacing.Ó) > 0;
    }
    
    public int ŠÄ(final BlockPos p_175687_1_) {
        int var2 = 0;
        for (final EnumFacing var6 : EnumFacing.values()) {
            final int var7 = this.Ý(p_175687_1_.HorizonCode_Horizon_È(var6), var6);
            if (var7 >= 15) {
                return 15;
            }
            if (var7 > var2) {
                var2 = var7;
            }
        }
        return var2;
    }
    
    public EntityPlayer HorizonCode_Horizon_È(final Entity entityIn, final double distance) {
        return this.HorizonCode_Horizon_È(entityIn.ŒÏ, entityIn.Çªà¢, entityIn.Ê, distance);
    }
    
    public EntityPlayer HorizonCode_Horizon_È(final double x, final double y, final double z, final double distance) {
        double var9 = -1.0;
        EntityPlayer var10 = null;
        for (int var11 = 0; var11 < this.Ó.size(); ++var11) {
            final EntityPlayer var12 = this.Ó.get(var11);
            if (IEntitySelector.Ø­áŒŠá.apply((Object)var12)) {
                final double var13 = var12.Âµá€(x, y, z);
                if ((distance < 0.0 || var13 < distance * distance) && (var9 == -1.0 || var13 < var9)) {
                    var9 = var13;
                    var10 = var12;
                }
            }
        }
        return var10;
    }
    
    public boolean Â(final double p_175636_1_, final double p_175636_3_, final double p_175636_5_, final double p_175636_7_) {
        for (int var9 = 0; var9 < this.Ó.size(); ++var9) {
            final EntityPlayer var10 = this.Ó.get(var9);
            if (IEntitySelector.Ø­áŒŠá.apply((Object)var10)) {
                final double var11 = var10.Âµá€(p_175636_1_, p_175636_3_, p_175636_5_);
                if (p_175636_7_ < 0.0 || var11 < p_175636_7_ * p_175636_7_) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public EntityPlayer HorizonCode_Horizon_È(final String name) {
        for (int var2 = 0; var2 < this.Ó.size(); ++var2) {
            final EntityPlayer var3 = this.Ó.get(var2);
            if (name.equals(var3.v_())) {
                return var3;
            }
        }
        return null;
    }
    
    public EntityPlayer HorizonCode_Horizon_È(final UUID uuid) {
        for (int var2 = 0; var2 < this.Ó.size(); ++var2) {
            final EntityPlayer var3 = this.Ó.get(var2);
            if (uuid.equals(var3.£áŒŠá())) {
                return var3;
            }
        }
        return null;
    }
    
    public void Ø­áŒŠá() {
    }
    
    public void µÕ() throws MinecraftException {
        this.¥Æ.Ø­áŒŠá();
    }
    
    public void Â(final long p_82738_1_) {
        this.Ø­à.HorizonCode_Horizon_È(p_82738_1_);
    }
    
    public long Æ() {
        return this.Ø­à.Â();
    }
    
    public long Šáƒ() {
        return this.Ø­à.Ó();
    }
    
    public long Ï­Ðƒà() {
        return this.Ø­à.à();
    }
    
    public void HorizonCode_Horizon_È(final long time) {
        this.Ø­à.Â(time);
    }
    
    public BlockPos áŒŠà() {
        BlockPos var1 = new BlockPos(this.Ø­à.Ý(), this.Ø­à.Ø­áŒŠá(), this.Ø­à.Âµá€());
        if (!this.áŠ().HorizonCode_Horizon_È(var1)) {
            var1 = this.£á(new BlockPos(this.áŠ().HorizonCode_Horizon_È(), 0.0, this.áŠ().Â()));
        }
        return var1;
    }
    
    public void Ñ¢á(final BlockPos p_175652_1_) {
        this.Ø­à.HorizonCode_Horizon_È(p_175652_1_);
    }
    
    public void Ø(final Entity entityIn) {
        final int var2 = MathHelper.Ý(entityIn.ŒÏ / 16.0);
        final int var3 = MathHelper.Ý(entityIn.Ê / 16.0);
        final byte var4 = 2;
        for (int var5 = var2 - var4; var5 <= var2 + var4; ++var5) {
            for (int var6 = var3 - var4; var6 <= var3 + var4; ++var6) {
                this.HorizonCode_Horizon_È(var5, var6);
            }
        }
        if (!this.Â.contains(entityIn)) {
            this.Â.add(entityIn);
        }
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_175660_1_, final BlockPos p_175660_2_) {
        return true;
    }
    
    public void HorizonCode_Horizon_È(final Entity entityIn, final byte p_72960_2_) {
    }
    
    public IChunkProvider ŠÄ() {
        return this.ˆà;
    }
    
    public void Ý(final BlockPos pos, final Block blockIn, final int eventID, final int eventParam) {
        blockIn.HorizonCode_Horizon_È(this, pos, this.Â(pos), eventID, eventParam);
    }
    
    public ISaveHandler Ñ¢á() {
        return this.¥Æ;
    }
    
    public WorldInfo ŒÏ() {
        return this.Ø­à;
    }
    
    public GameRules Çªà¢() {
        return this.Ø­à.Šáƒ();
    }
    
    public void Ê() {
    }
    
    public float Ø(final float p_72819_1_) {
        return (this.ˆÏ­ + (this.£á - this.ˆÏ­) * p_72819_1_) * this.áˆºÑ¢Õ(p_72819_1_);
    }
    
    public void áŒŠÆ(final float p_147442_1_) {
        this.ˆÏ­ = p_147442_1_;
        this.£á = p_147442_1_;
    }
    
    public float áˆºÑ¢Õ(final float p_72867_1_) {
        return this.ÂµÈ + (this.á - this.ÂµÈ) * p_72867_1_;
    }
    
    public void ÂµÈ(final float strength) {
        this.ÂµÈ = strength;
        this.á = strength;
    }
    
    public boolean ÇŽÉ() {
        return this.Ø(1.0f) > 0.9;
    }
    
    public boolean ˆá() {
        return this.áˆºÑ¢Õ(1.0f) > 0.2;
    }
    
    public boolean ŒÏ(final BlockPos p_175727_1_) {
        if (!this.ˆá()) {
            return false;
        }
        if (!this.áˆºÑ¢Õ(p_175727_1_)) {
            return false;
        }
        if (this.µà(p_175727_1_).Â() > p_175727_1_.Â()) {
            return false;
        }
        final BiomeGenBase var2 = this.Ý(p_175727_1_);
        return !var2.Ø­áŒŠá() && !this.Ó(p_175727_1_, false) && var2.Âµá€();
    }
    
    public boolean Çªà¢(final BlockPos p_180502_1_) {
        final BiomeGenBase var2 = this.Ý(p_180502_1_);
        return var2.Ó();
    }
    
    public MapStorage ÇŽÕ() {
        return this.Æ;
    }
    
    public void HorizonCode_Horizon_È(final String p_72823_1_, final WorldSavedData p_72823_2_) {
        this.Æ.HorizonCode_Horizon_È(p_72823_1_, p_72823_2_);
    }
    
    public WorldSavedData HorizonCode_Horizon_È(final Class p_72943_1_, final String p_72943_2_) {
        return this.Æ.HorizonCode_Horizon_È(p_72943_1_, p_72943_2_);
    }
    
    public int Â(final String p_72841_1_) {
        return this.Æ.HorizonCode_Horizon_È(p_72841_1_);
    }
    
    public void HorizonCode_Horizon_È(final int p_175669_1_, final BlockPos p_175669_2_, final int p_175669_3_) {
        for (int var4 = 0; var4 < this.µà.size(); ++var4) {
            this.µà.get(var4).HorizonCode_Horizon_È(p_175669_1_, p_175669_2_, p_175669_3_);
        }
    }
    
    public void Â(final int p_175718_1_, final BlockPos p_175718_2_, final int p_175718_3_) {
        this.HorizonCode_Horizon_È(null, p_175718_1_, p_175718_2_, p_175718_3_);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_180498_1_, final int p_180498_2_, final BlockPos p_180498_3_, final int p_180498_4_) {
        try {
            for (int var5 = 0; var5 < this.µà.size(); ++var5) {
                this.µà.get(var5).HorizonCode_Horizon_È(p_180498_1_, p_180498_2_, p_180498_3_, p_180498_4_);
            }
        }
        catch (Throwable var7) {
            final CrashReport var6 = CrashReport.HorizonCode_Horizon_È(var7, "Playing level event");
            final CrashReportCategory var8 = var6.HorizonCode_Horizon_È("Level event being played");
            var8.HorizonCode_Horizon_È("Block coordinates", CrashReportCategory.HorizonCode_Horizon_È(p_180498_3_));
            var8.HorizonCode_Horizon_È("Event source", p_180498_1_);
            var8.HorizonCode_Horizon_È("Event type", p_180498_2_);
            var8.HorizonCode_Horizon_È("Event data", p_180498_4_);
            throw new ReportedException(var6);
        }
    }
    
    public int É() {
        return 256;
    }
    
    public int áƒ() {
        return this.£à.Å() ? 128 : 256;
    }
    
    public Random Â(final int p_72843_1_, final int p_72843_2_, final int p_72843_3_) {
        final long var4 = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + this.ŒÏ().Â() + p_72843_3_;
        this.Å.setSeed(var4);
        return this.Å;
    }
    
    public BlockPos HorizonCode_Horizon_È(final String p_180499_1_, final BlockPos p_180499_2_) {
        return this.ŠÄ().HorizonCode_Horizon_È(this, p_180499_1_, p_180499_2_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return false;
    }
    
    public double á€() {
        return (this.Ø­à.Ø­à() == WorldType.Ø­áŒŠá) ? 0.0 : 63.0;
    }
    
    public CrashReportCategory HorizonCode_Horizon_È(final CrashReport report) {
        final CrashReportCategory var2 = report.HorizonCode_Horizon_È("Affected level", 1);
        var2.HorizonCode_Horizon_È("Level name", (this.Ø­à == null) ? "????" : this.Ø­à.áˆºÑ¢Õ());
        var2.HorizonCode_Horizon_È("All players", new Callable() {
            private static final String Â = "CL_00000143";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(World.this.Ó.size()) + " total; " + World.this.Ó.toString();
            }
        });
        var2.HorizonCode_Horizon_È("Chunk stats", new Callable() {
            private static final String Â = "CL_00000144";
            
            public String HorizonCode_Horizon_È() {
                return World.this.ˆà.Ø­áŒŠá();
            }
        });
        try {
            this.Ø­à.HorizonCode_Horizon_È(var2);
        }
        catch (Throwable var3) {
            var2.HorizonCode_Horizon_È("Level Data Unobtainable", var3);
        }
        return var2;
    }
    
    public void Ý(final int breakerId, final BlockPos pos, final int progress) {
        for (int var4 = 0; var4 < this.µà.size(); ++var4) {
            final IWorldAccess var5 = this.µà.get(var4);
            var5.Â(breakerId, pos, progress);
        }
    }
    
    public Calendar Õ() {
        if (this.Šáƒ() % 600L == 0L) {
            this.á€.setTimeInMillis(MinecraftServer.Œà());
        }
        return this.á€;
    }
    
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final NBTTagCompound compund) {
    }
    
    public Scoreboard à¢() {
        return this.áŒŠà;
    }
    
    public void Âµá€(final BlockPos pos, final Block blockIn) {
        for (final EnumFacing var4 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            BlockPos var5 = pos.HorizonCode_Horizon_È(var4);
            if (this.Ó(var5)) {
                IBlockState var6 = this.Â(var5);
                if (Blocks.ÐƒÇŽà.Âµá€(var6.Ý())) {
                    var6.Ý().HorizonCode_Horizon_È(this, var5, var6, blockIn);
                }
                else {
                    if (!var6.Ý().Ø()) {
                        continue;
                    }
                    var5 = var5.HorizonCode_Horizon_È(var4);
                    var6 = this.Â(var5);
                    if (!Blocks.ÐƒÇŽà.Âµá€(var6.Ý())) {
                        continue;
                    }
                    var6.Ý().HorizonCode_Horizon_È(this, var5, var6, blockIn);
                }
            }
        }
    }
    
    public DifficultyInstance Ê(final BlockPos pos) {
        long var2 = 0L;
        float var3 = 0.0f;
        if (this.Ó(pos)) {
            var3 = this.ˆÏ­();
            var2 = this.à(pos).Šáƒ();
        }
        return new DifficultyInstance(this.ŠÂµà(), this.Ï­Ðƒà(), var2, var3);
    }
    
    public EnumDifficulty ŠÂµà() {
        return this.ŒÏ().Ï­Ðƒà();
    }
    
    public int ¥à() {
        return this.É;
    }
    
    public void Ý(final int newSkylightSubtracted) {
        this.É = newSkylightSubtracted;
    }
    
    public int Âµà() {
        return this.áƒ;
    }
    
    public void Ø­áŒŠá(final int lastLightningBoltIn) {
        this.áƒ = lastLightningBoltIn;
    }
    
    public boolean Ç() {
        return this.µÕ;
    }
    
    public VillageCollection È() {
        return this.Šáƒ;
    }
    
    public WorldBorder áŠ() {
        return this.ŠÂµà;
    }
    
    public boolean Ý(final int x, final int z) {
        final BlockPos var3 = this.áŒŠà();
        final int var4 = x * 16 + 8 - var3.HorizonCode_Horizon_È();
        final int var5 = z * 16 + 8 - var3.Ý();
        final short var6 = 128;
        return var4 >= -var6 && var4 <= var6 && var5 >= -var6 && var5 <= var6;
    }
}
