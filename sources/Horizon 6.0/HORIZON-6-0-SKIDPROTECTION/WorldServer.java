package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;
import java.util.Random;
import java.util.ArrayList;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public class WorldServer extends World implements IThreadListener
{
    private static final Logger É;
    private final MinecraftServer áƒ;
    private final EntityTracker á€;
    private final PlayerManager Õ;
    private final Set à¢;
    private final TreeSet ŠÂµà;
    private final Map ¥à;
    public ChunkProviderServer ÇŽÉ;
    public boolean ˆá;
    private boolean Âµà;
    private int Ç;
    private final Teleporter È;
    private final SpawnerAnimals ˆáŠ;
    protected final VillageSiege ÇŽÕ;
    private HorizonCode_Horizon_È[] áŒŠ;
    private int £ÂµÄ;
    private static final List Ø­Âµ;
    private List Ä;
    private static final String Ñ¢Â = "CL_00001437";
    
    static {
        É = LogManager.getLogger();
        Ø­Âµ = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.áŒŠà, 0, 1, 3, 10), new WeightedRandomChestContent(Item_1028566121.HorizonCode_Horizon_È(Blocks.à), 0, 1, 3, 10), new WeightedRandomChestContent(Item_1028566121.HorizonCode_Horizon_È(Blocks.¥Æ), 0, 1, 3, 10), new WeightedRandomChestContent(Items.Ø­à, 0, 1, 1, 3), new WeightedRandomChestContent(Items.£à, 0, 1, 1, 5), new WeightedRandomChestContent(Items.¥Æ, 0, 1, 1, 3), new WeightedRandomChestContent(Items.Å, 0, 1, 1, 5), new WeightedRandomChestContent(Items.Âµá€, 0, 2, 3, 5), new WeightedRandomChestContent(Items.Ç, 0, 2, 3, 3), new WeightedRandomChestContent(Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­à), 0, 1, 3, 10) });
    }
    
    public WorldServer(final MinecraftServer server, final ISaveHandler saveHandlerIn, final WorldInfo info, final int dimensionId, final Profiler profilerIn) {
        super(saveHandlerIn, info, WorldProvider.HorizonCode_Horizon_È(dimensionId), profilerIn, false);
        this.à¢ = Sets.newHashSet();
        this.ŠÂµà = new TreeSet();
        this.¥à = Maps.newHashMap();
        this.ˆáŠ = new SpawnerAnimals();
        this.ÇŽÕ = new VillageSiege(this);
        this.áŒŠ = new HorizonCode_Horizon_È[] { new HorizonCode_Horizon_È((Object)null), new HorizonCode_Horizon_È((Object)null) };
        this.Ä = Lists.newArrayList();
        this.áƒ = server;
        this.á€ = new EntityTracker(this);
        this.Õ = new PlayerManager(this);
        this.£à.HorizonCode_Horizon_È(this);
        this.ˆà = this.Â();
        this.È = new Teleporter(this);
        this.µà();
        this.ˆà();
        this.áŠ().HorizonCode_Horizon_È(server.¥Ä());
    }
    
    @Override
    public World Ø() {
        this.Æ = new MapStorage(this.¥Æ);
        final String var1 = VillageCollection.HorizonCode_Horizon_È(this.£à);
        final VillageCollection var2 = (VillageCollection)this.Æ.HorizonCode_Horizon_È(VillageCollection.class, var1);
        if (var2 == null) {
            this.Šáƒ = new VillageCollection(this);
            this.Æ.HorizonCode_Horizon_È(var1, this.Šáƒ);
        }
        else {
            (this.Šáƒ = var2).HorizonCode_Horizon_È(this);
        }
        this.áŒŠà = new ServerScoreboard(this.áƒ);
        ScoreboardSaveData var3 = (ScoreboardSaveData)this.Æ.HorizonCode_Horizon_È(ScoreboardSaveData.class, "scoreboard");
        if (var3 == null) {
            var3 = new ScoreboardSaveData();
            this.Æ.HorizonCode_Horizon_È("scoreboard", var3);
        }
        var3.HorizonCode_Horizon_È(this.áŒŠà);
        ((ServerScoreboard)this.áŒŠà).HorizonCode_Horizon_È(var3);
        this.áŠ().Â(this.Ø­à.ŒÏ(), this.Ø­à.Çªà¢());
        this.áŠ().Ý(this.Ø­à.É());
        this.áŠ().Â(this.Ø­à.ÇŽÕ());
        this.áŠ().Ý(this.Ø­à.áƒ());
        this.áŠ().Â(this.Ø­à.á€());
        if (this.Ø­à.ÇŽÉ() > 0L) {
            this.áŠ().HorizonCode_Horizon_È(this.Ø­à.Ê(), this.Ø­à.ˆá(), this.Ø­à.ÇŽÉ());
        }
        else {
            this.áŠ().HorizonCode_Horizon_È(this.Ø­à.Ê());
        }
        return this;
    }
    
    @Override
    public void r_() {
        super.r_();
        if (this.ŒÏ().¥Æ() && this.ŠÂµà() != EnumDifficulty.Ø­áŒŠá) {
            this.ŒÏ().HorizonCode_Horizon_È(EnumDifficulty.Ø­áŒŠá);
        }
        this.£à.ˆÏ­().Â();
        if (this.ˆáŠ()) {
            if (this.Çªà¢().Â("doDaylightCycle")) {
                final long var1 = this.Ø­à.à() + 24000L;
                this.Ø­à.Â(var1 - var1 % 24000L);
            }
            this.à();
        }
        this.Ï­Ðƒà.HorizonCode_Horizon_È("mobSpawner");
        if (this.Çªà¢().Â("doMobSpawning") && this.Ø­à.Ø­à() != WorldType.Ø) {
            this.ˆáŠ.HorizonCode_Horizon_È(this, this.ŒÏ, this.Çªà¢, this.Ø­à.Ó() % 400L == 0L);
        }
        this.Ï­Ðƒà.Ý("chunkSource");
        this.ˆà.Â();
        final int var2 = this.HorizonCode_Horizon_È(1.0f);
        if (var2 != this.¥à()) {
            this.Ý(var2);
        }
        this.Ø­à.HorizonCode_Horizon_È(this.Ø­à.Ó() + 1L);
        if (this.Çªà¢().Â("doDaylightCycle")) {
            this.Ø­à.Â(this.Ø­à.à() + 1L);
        }
        this.Ï­Ðƒà.Ý("tickPending");
        this.HorizonCode_Horizon_È(false);
        this.Ï­Ðƒà.Ý("tickBlocks");
        this.Ý();
        this.Ï­Ðƒà.Ý("chunkMap");
        this.Õ.Â();
        this.Ï­Ðƒà.Ý("village");
        this.Šáƒ.HorizonCode_Horizon_È();
        this.ÇŽÕ.HorizonCode_Horizon_È();
        this.Ï­Ðƒà.Ý("portalForcer");
        this.È.HorizonCode_Horizon_È(this.Šáƒ());
        this.Ï­Ðƒà.Â();
        this.Ø­á();
    }
    
    public BiomeGenBase.Â HorizonCode_Horizon_È(final EnumCreatureType p_175734_1_, final BlockPos p_175734_2_) {
        final List var3 = this.ŠÄ().HorizonCode_Horizon_È(p_175734_1_, p_175734_2_);
        return (var3 != null && !var3.isEmpty()) ? ((BiomeGenBase.Â)WeightedRandom.HorizonCode_Horizon_È(this.Å, var3)) : null;
    }
    
    public boolean HorizonCode_Horizon_È(final EnumCreatureType p_175732_1_, final BiomeGenBase.Â p_175732_2_, final BlockPos p_175732_3_) {
        final List var4 = this.ŠÄ().HorizonCode_Horizon_È(p_175732_1_, p_175732_3_);
        return var4 != null && !var4.isEmpty() && var4.contains(p_175732_2_);
    }
    
    @Override
    public void Ê() {
        this.Âµà = false;
        if (!this.Ó.isEmpty()) {
            int var1 = 0;
            int var2 = 0;
            for (final EntityPlayer var4 : this.Ó) {
                if (var4.Ø­áŒŠá()) {
                    ++var1;
                }
                else {
                    if (!var4.Ï­Ó()) {
                        continue;
                    }
                    ++var2;
                }
            }
            this.Âµà = (var2 > 0 && var2 >= this.Ó.size() - var1);
        }
    }
    
    protected void à() {
        this.Âµà = false;
        for (final EntityPlayer var2 : this.Ó) {
            if (var2.Ï­Ó()) {
                var2.HorizonCode_Horizon_È(false, false, true);
            }
        }
        this.áˆºÏ();
    }
    
    private void áˆºÏ() {
        this.Ø­à.Ó(0);
        this.Ø­à.Â(false);
        this.Ø­à.Âµá€(0);
        this.Ø­à.HorizonCode_Horizon_È(false);
    }
    
    public boolean ˆáŠ() {
        if (this.Âµà && !this.ŠÄ) {
            for (final EntityPlayer var2 : this.Ó) {
                if (var2.Ø­áŒŠá() || !var2.£áƒ()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void áˆºÑ¢Õ() {
        if (this.Ø­à.Ø­áŒŠá() <= 0) {
            this.Ø­à.Â(64);
        }
        int var1 = this.Ø­à.Ý();
        int var2 = this.Ø­à.Âµá€();
        int var3 = 0;
        while (this.Âµá€(new BlockPos(var1, 0, var2)).Ó() == Material.HorizonCode_Horizon_È) {
            var1 += this.Å.nextInt(8) - this.Å.nextInt(8);
            var2 += this.Å.nextInt(8) - this.Å.nextInt(8);
            if (++var3 == 10000) {
                break;
            }
        }
        this.Ø­à.HorizonCode_Horizon_È(var1);
        this.Ø­à.Ý(var2);
    }
    
    @Override
    protected void Ý() {
        super.Ý();
        if (this.Ø­à.Ø­à() == WorldType.Ø) {
            for (final ChunkCoordIntPair var22 : this.Ñ¢á) {
                this.HorizonCode_Horizon_È(var22.HorizonCode_Horizon_È, var22.Â).Â(false);
            }
        }
        else {
            int var23 = 0;
            int var24 = 0;
            for (final ChunkCoordIntPair var26 : this.Ñ¢á) {
                final int var27 = var26.HorizonCode_Horizon_È * 16;
                final int var28 = var26.Â * 16;
                this.Ï­Ðƒà.HorizonCode_Horizon_È("getChunk");
                final Chunk var29 = this.HorizonCode_Horizon_È(var26.HorizonCode_Horizon_È, var26.Â);
                this.HorizonCode_Horizon_È(var27, var28, var29);
                this.Ï­Ðƒà.Ý("tickChunk");
                var29.Â(false);
                this.Ï­Ðƒà.Ý("thunder");
                if (this.Å.nextInt(100000) == 0 && this.ˆá() && this.ÇŽÉ()) {
                    this.áŒŠÆ = this.áŒŠÆ * 3 + 1013904223;
                    final int var30 = this.áŒŠÆ >> 2;
                    final BlockPos var31 = this.ÇŽÉ(new BlockPos(var27 + (var30 & 0xF), 0, var28 + (var30 >> 8 & 0xF)));
                    if (this.ŒÏ(var31)) {
                        this.Âµá€(new EntityLightningBolt(this, var31.HorizonCode_Horizon_È(), var31.Â(), var31.Ý()));
                    }
                }
                this.Ï­Ðƒà.Ý("iceandsnow");
                if (this.Å.nextInt(16) == 0) {
                    this.áŒŠÆ = this.áŒŠÆ * 3 + 1013904223;
                    final int var30 = this.áŒŠÆ >> 2;
                    final BlockPos var31 = this.µà(new BlockPos(var27 + (var30 & 0xF), 0, var28 + (var30 >> 8 & 0xF)));
                    final BlockPos var32 = var31.Âµá€();
                    if (this.Æ(var32)) {
                        this.Â(var32, Blocks.¥Ï.¥à());
                    }
                    if (this.ˆá() && this.Ó(var31, true)) {
                        this.Â(var31, Blocks.áŒŠá€.¥à());
                    }
                    if (this.ˆá() && this.Ý(var32).Âµá€()) {
                        this.Â(var32).Ý().à(this, var32);
                    }
                }
                this.Ï­Ðƒà.Ý("tickBlocks");
                final int var30 = this.Çªà¢().Ý("randomTickSpeed");
                if (var30 > 0) {
                    for (final ExtendedBlockStorage var36 : var29.Â()) {
                        if (var36 != null && var36.Â()) {
                            for (int var37 = 0; var37 < var30; ++var37) {
                                this.áŒŠÆ = this.áŒŠÆ * 3 + 1013904223;
                                final int var38 = this.áŒŠÆ >> 2;
                                final int var39 = var38 & 0xF;
                                final int var40 = var38 >> 8 & 0xF;
                                final int var41 = var38 >> 16 & 0xF;
                                ++var24;
                                final BlockPos var42 = new BlockPos(var39 + var27, var41 + var36.Ý(), var40 + var28);
                                final IBlockState var43 = var36.HorizonCode_Horizon_È(var39, var41, var40);
                                final Block var44 = var43.Ý();
                                if (var44.ˆÏ­()) {
                                    ++var23;
                                    var44.HorizonCode_Horizon_È(this, var42, var43, this.Å);
                                }
                            }
                        }
                    }
                }
                this.Ï­Ðƒà.Â();
            }
        }
    }
    
    protected BlockPos ÇŽÉ(final BlockPos p_175736_1_) {
        final BlockPos var2 = this.µà(p_175736_1_);
        final AxisAlignedBB var3 = new AxisAlignedBB(var2, new BlockPos(var2.HorizonCode_Horizon_È(), this.É(), var2.Ý())).Â(3.0, 3.0, 3.0);
        final List var4 = this.HorizonCode_Horizon_È(EntityLivingBase.class, var3, (Predicate)new Predicate() {
            private static final String Â = "CL_00001889";
            
            public boolean HorizonCode_Horizon_È(final EntityLivingBase p_180242_1_) {
                return p_180242_1_ != null && p_180242_1_.Œ() && WorldServer.this.áˆºÑ¢Õ(p_180242_1_.£á());
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EntityLivingBase)p_apply_1_);
            }
        });
        return var4.isEmpty() ? var2 : var4.get(this.Å.nextInt(var4.size())).£á();
    }
    
    @Override
    public boolean Ø­áŒŠá(final BlockPos pos, final Block blockType) {
        final NextTickListEntry var3 = new NextTickListEntry(pos, blockType);
        return this.Ä.contains(var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos pos, final Block blockIn, final int delay) {
        this.HorizonCode_Horizon_È(pos, blockIn, delay, 0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BlockPos p_175654_1_, final Block p_175654_2_, int p_175654_3_, final int p_175654_4_) {
        final NextTickListEntry var5 = new NextTickListEntry(p_175654_1_, p_175654_2_);
        byte var6 = 0;
        if (this.HorizonCode_Horizon_È && p_175654_2_.Ó() != Material.HorizonCode_Horizon_È) {
            if (p_175654_2_.á€()) {
                var6 = 8;
                if (this.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È.Â(-var6, -var6, -var6), var5.HorizonCode_Horizon_È.Â(var6, var6, var6))) {
                    final IBlockState var7 = this.Â(var5.HorizonCode_Horizon_È);
                    if (var7.Ý().Ó() != Material.HorizonCode_Horizon_È && var7.Ý() == var5.HorizonCode_Horizon_È()) {
                        var7.Ý().Â(this, var5.HorizonCode_Horizon_È, var7, this.Å);
                    }
                }
                return;
            }
            p_175654_3_ = 1;
        }
        if (this.HorizonCode_Horizon_È(p_175654_1_.Â(-var6, -var6, -var6), p_175654_1_.Â(var6, var6, var6))) {
            if (p_175654_2_.Ó() != Material.HorizonCode_Horizon_È) {
                var5.HorizonCode_Horizon_È(p_175654_3_ + this.Ø­à.Ó());
                var5.HorizonCode_Horizon_È(p_175654_4_);
            }
            if (!this.à¢.contains(var5)) {
                this.à¢.add(var5);
                this.ŠÂµà.add(var5);
            }
        }
    }
    
    @Override
    public void Â(final BlockPos p_180497_1_, final Block p_180497_2_, final int p_180497_3_, final int p_180497_4_) {
        final NextTickListEntry var5 = new NextTickListEntry(p_180497_1_, p_180497_2_);
        var5.HorizonCode_Horizon_È(p_180497_4_);
        if (p_180497_2_.Ó() != Material.HorizonCode_Horizon_È) {
            var5.HorizonCode_Horizon_È(p_180497_3_ + this.Ø­à.Ó());
        }
        if (!this.à¢.contains(var5)) {
            this.à¢.add(var5);
            this.ŠÂµà.add(var5);
        }
    }
    
    @Override
    public void £á() {
        if (this.Ó.isEmpty()) {
            if (this.Ç++ >= 1200) {
                return;
            }
        }
        else {
            this.áŒŠ();
        }
        super.£á();
    }
    
    public void áŒŠ() {
        this.Ç = 0;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final boolean p_72955_1_) {
        if (this.Ø­à.Ø­à() == WorldType.Ø) {
            return false;
        }
        int var2 = this.ŠÂµà.size();
        if (var2 != this.à¢.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (var2 > 1000) {
            var2 = 1000;
        }
        this.Ï­Ðƒà.HorizonCode_Horizon_È("cleaning");
        for (int var3 = 0; var3 < var2; ++var3) {
            final NextTickListEntry var4 = this.ŠÂµà.first();
            if (!p_72955_1_ && var4.Â > this.Ø­à.Ó()) {
                break;
            }
            this.ŠÂµà.remove(var4);
            this.à¢.remove(var4);
            this.Ä.add(var4);
        }
        this.Ï­Ðƒà.Â();
        this.Ï­Ðƒà.HorizonCode_Horizon_È("ticking");
        final Iterator var5 = this.Ä.iterator();
        while (var5.hasNext()) {
            final NextTickListEntry var4 = var5.next();
            var5.remove();
            final byte var6 = 0;
            if (this.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È.Â(-var6, -var6, -var6), var4.HorizonCode_Horizon_È.Â(var6, var6, var6))) {
                final IBlockState var7 = this.Â(var4.HorizonCode_Horizon_È);
                if (var7.Ý().Ó() == Material.HorizonCode_Horizon_È || !Block.HorizonCode_Horizon_È(var7.Ý(), var4.HorizonCode_Horizon_È())) {
                    continue;
                }
                try {
                    var7.Ý().Â(this, var4.HorizonCode_Horizon_È, var7, this.Å);
                    continue;
                }
                catch (Throwable var9) {
                    final CrashReport var8 = CrashReport.HorizonCode_Horizon_È(var9, "Exception while ticking a block");
                    final CrashReportCategory var10 = var8.HorizonCode_Horizon_È("Block being ticked");
                    CrashReportCategory.HorizonCode_Horizon_È(var10, var4.HorizonCode_Horizon_È, var7);
                    throw new ReportedException(var8);
                }
            }
            this.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È, var4.HorizonCode_Horizon_È(), 0);
        }
        this.Ï­Ðƒà.Â();
        this.Ä.clear();
        return !this.ŠÂµà.isEmpty();
    }
    
    @Override
    public List HorizonCode_Horizon_È(final Chunk p_72920_1_, final boolean p_72920_2_) {
        final ChunkCoordIntPair var3 = p_72920_1_.áˆºÑ¢Õ();
        final int var4 = (var3.HorizonCode_Horizon_È << 4) - 2;
        final int var5 = var4 + 16 + 2;
        final int var6 = (var3.Â << 4) - 2;
        final int var7 = var6 + 16 + 2;
        return this.Â(new StructureBoundingBox(var4, 0, var6, var5, 256, var7), p_72920_2_);
    }
    
    @Override
    public List Â(final StructureBoundingBox p_175712_1_, final boolean p_175712_2_) {
        ArrayList var3 = null;
        for (int var4 = 0; var4 < 2; ++var4) {
            Iterator var5;
            if (var4 == 0) {
                var5 = this.ŠÂµà.iterator();
            }
            else {
                var5 = this.Ä.iterator();
                if (!this.Ä.isEmpty()) {
                    WorldServer.É.debug("toBeTicked = " + this.Ä.size());
                }
            }
            while (var5.hasNext()) {
                final NextTickListEntry var6 = var5.next();
                final BlockPos var7 = var6.HorizonCode_Horizon_È;
                if (var7.HorizonCode_Horizon_È() >= p_175712_1_.HorizonCode_Horizon_È && var7.HorizonCode_Horizon_È() < p_175712_1_.Ø­áŒŠá && var7.Ý() >= p_175712_1_.Ý && var7.Ý() < p_175712_1_.Ó) {
                    if (p_175712_2_) {
                        this.à¢.remove(var6);
                        var5.remove();
                    }
                    if (var3 == null) {
                        var3 = Lists.newArrayList();
                    }
                    var3.add(var6);
                }
            }
        }
        return var3;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_72866_1_, final boolean p_72866_2_) {
        if (!this.Œ() && (p_72866_1_ instanceof EntityAnimal || p_72866_1_ instanceof EntityWaterMob)) {
            p_72866_1_.á€();
        }
        if (!this.ˆáƒ() && p_72866_1_ instanceof INpc) {
            p_72866_1_.á€();
        }
        super.HorizonCode_Horizon_È(p_72866_1_, p_72866_2_);
    }
    
    private boolean ˆáƒ() {
        return this.áƒ.áˆºáˆºÈ();
    }
    
    private boolean Œ() {
        return this.áƒ.Ï­à();
    }
    
    @Override
    protected IChunkProvider Â() {
        final IChunkLoader var1 = this.¥Æ.HorizonCode_Horizon_È(this.£à);
        return this.ÇŽÉ = new ChunkProviderServer(this, var1, this.£à.Ý());
    }
    
    public List HorizonCode_Horizon_È(final int p_147486_1_, final int p_147486_2_, final int p_147486_3_, final int p_147486_4_, final int p_147486_5_, final int p_147486_6_) {
        final ArrayList var7 = Lists.newArrayList();
        for (int var8 = 0; var8 < this.Ø­áŒŠá.size(); ++var8) {
            final TileEntity var9 = this.Ø­áŒŠá.get(var8);
            final BlockPos var10 = var9.á();
            if (var10.HorizonCode_Horizon_È() >= p_147486_1_ && var10.Â() >= p_147486_2_ && var10.Ý() >= p_147486_3_ && var10.HorizonCode_Horizon_È() < p_147486_4_ && var10.Â() < p_147486_5_ && var10.Ý() < p_147486_6_) {
                var7.add(var9);
            }
        }
        return var7;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_175660_1_, final BlockPos p_175660_2_) {
        return !this.áƒ.HorizonCode_Horizon_È(this, p_175660_2_, p_175660_1_) && this.áŠ().HorizonCode_Horizon_È(p_175660_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldSettings settings) {
        if (!this.Ø­à.Æ()) {
            try {
                this.Â(settings);
                if (this.Ø­à.Ø­à() == WorldType.Ø) {
                    this.£Ï();
                }
                super.HorizonCode_Horizon_È(settings);
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Exception initializing level");
                try {
                    this.HorizonCode_Horizon_È(var3);
                }
                catch (Throwable t) {}
                throw new ReportedException(var3);
            }
            this.Ø­à.Ø­áŒŠá(true);
        }
    }
    
    private void £Ï() {
        this.Ø­à.Ó(false);
        this.Ø­à.Ý(true);
        this.Ø­à.Â(false);
        this.Ø­à.HorizonCode_Horizon_È(false);
        this.Ø­à.à(1000000000);
        this.Ø­à.Â(6000L);
        this.Ø­à.HorizonCode_Horizon_È(WorldSettings.HorizonCode_Horizon_È.Âµá€);
        this.Ø­à.à(false);
        this.Ø­à.HorizonCode_Horizon_È(EnumDifficulty.HorizonCode_Horizon_È);
        this.Ø­à.Âµá€(true);
        this.Çªà¢().HorizonCode_Horizon_È("doDaylightCycle", "false");
    }
    
    private void Â(final WorldSettings p_73052_1_) {
        if (!this.£à.Âµá€()) {
            this.Ø­à.HorizonCode_Horizon_È(BlockPos.HorizonCode_Horizon_È.Â(this.£à.áŒŠÆ()));
        }
        else if (this.Ø­à.Ø­à() == WorldType.Ø) {
            this.Ø­à.HorizonCode_Horizon_È(BlockPos.HorizonCode_Horizon_È.Ø­áŒŠá());
        }
        else {
            this.µÕ = true;
            final WorldChunkManager var2 = this.£à.ˆÏ­();
            final List var3 = var2.HorizonCode_Horizon_È();
            final Random var4 = new Random(this.Æ());
            final BlockPos var5 = var2.HorizonCode_Horizon_È(0, 0, 256, var3, var4);
            int var6 = 0;
            final int var7 = this.£à.áŒŠÆ();
            int var8 = 0;
            if (var5 != null) {
                var6 = var5.HorizonCode_Horizon_È();
                var8 = var5.Ý();
            }
            else {
                WorldServer.É.warn("Unable to find spawn biome");
            }
            int var9 = 0;
            while (!this.£à.HorizonCode_Horizon_È(var6, var8)) {
                var6 += var4.nextInt(64) - var4.nextInt(64);
                var8 += var4.nextInt(64) - var4.nextInt(64);
                if (++var9 == 1000) {
                    break;
                }
            }
            this.Ø­à.HorizonCode_Horizon_È(new BlockPos(var6, var7, var8));
            this.µÕ = false;
            if (p_73052_1_.Ý()) {
                this.£ÂµÄ();
            }
        }
    }
    
    protected void £ÂµÄ() {
        final WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(WorldServer.Ø­Âµ, 10);
        for (int var2 = 0; var2 < 10; ++var2) {
            final int var3 = this.Ø­à.Ý() + this.Å.nextInt(6) - this.Å.nextInt(6);
            final int var4 = this.Ø­à.Âµá€() + this.Å.nextInt(6) - this.Å.nextInt(6);
            final BlockPos var5 = this.ˆà(new BlockPos(var3, 0, var4)).Ø­áŒŠá();
            if (var1.HorizonCode_Horizon_È(this, this.Å, var5)) {
                break;
            }
        }
    }
    
    public BlockPos Ø­Âµ() {
        return this.£à.Ø();
    }
    
    public void HorizonCode_Horizon_È(final boolean p_73044_1_, final IProgressUpdate p_73044_2_) throws MinecraftException {
        if (this.ˆà.Ý()) {
            if (p_73044_2_ != null) {
                p_73044_2_.Â("Saving level");
            }
            this.Ñ¢Â();
            if (p_73044_2_ != null) {
                p_73044_2_.Ý("Saving chunks");
            }
            this.ˆà.HorizonCode_Horizon_È(p_73044_1_, p_73044_2_);
            final List var3 = this.ÇŽÉ.Ó();
            for (final Chunk var5 : var3) {
                if (!this.Õ.HorizonCode_Horizon_È(var5.HorizonCode_Horizon_È, var5.Â)) {
                    this.ÇŽÉ.Â(var5.HorizonCode_Horizon_È, var5.Â);
                }
            }
        }
    }
    
    public void Ä() {
        if (this.ˆà.Ý()) {
            this.ˆà.HorizonCode_Horizon_È();
        }
    }
    
    protected void Ñ¢Â() throws MinecraftException {
        this.µÕ();
        this.Ø­à.HorizonCode_Horizon_È(this.áŠ().Ø());
        this.Ø­à.Ø­áŒŠá(this.áŠ().HorizonCode_Horizon_È());
        this.Ø­à.Ý(this.áŠ().Â());
        this.Ø­à.Âµá€(this.áŠ().ˆÏ­());
        this.Ø­à.Ó(this.áŠ().£á());
        this.Ø­à.Ø(this.áŠ().µà());
        this.Ø­à.áŒŠÆ(this.áŠ().£à());
        this.Ø­à.Â(this.áŠ().áˆºÑ¢Õ());
        this.Ø­à.Ý(this.áŠ().áŒŠÆ());
        this.¥Æ.HorizonCode_Horizon_È(this.Ø­à, this.áƒ.Œ().Â());
        this.Æ.HorizonCode_Horizon_È();
    }
    
    @Override
    protected void Ý(final Entity p_72923_1_) {
        super.Ý(p_72923_1_);
        this.Ø.HorizonCode_Horizon_È(p_72923_1_.ˆá(), p_72923_1_);
        this.¥à.put(p_72923_1_.£áŒŠá(), p_72923_1_);
        final Entity[] var2 = p_72923_1_.ÇªÔ();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                this.Ø.HorizonCode_Horizon_È(var2[var3].ˆá(), var2[var3]);
            }
        }
    }
    
    @Override
    protected void Ø­áŒŠá(final Entity p_72847_1_) {
        super.Ø­áŒŠá(p_72847_1_);
        this.Ø.Ø­áŒŠá(p_72847_1_.ˆá());
        this.¥à.remove(p_72847_1_.£áŒŠá());
        final Entity[] var2 = p_72847_1_.ÇªÔ();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                this.Ø.Ø­áŒŠá(var2[var3].ˆá());
            }
        }
    }
    
    @Override
    public boolean Âµá€(final Entity p_72942_1_) {
        if (super.Âµá€(p_72942_1_)) {
            this.áƒ.Œ().HorizonCode_Horizon_È(p_72942_1_.ŒÏ, p_72942_1_.Çªà¢, p_72942_1_.Ê, 512.0, this.£à.µà(), new S2CPacketSpawnGlobalEntity(p_72942_1_));
            return true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn, final byte p_72960_2_) {
        this.ÇŽá€().Â(entityIn, new S19PacketEntityStatus(entityIn, p_72960_2_));
    }
    
    @Override
    public Explosion HorizonCode_Horizon_È(final Entity p_72885_1_, final double p_72885_2_, final double p_72885_4_, final double p_72885_6_, final float p_72885_8_, final boolean p_72885_9_, final boolean p_72885_10_) {
        final Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
        var11.HorizonCode_Horizon_È();
        var11.HorizonCode_Horizon_È(false);
        if (!p_72885_10_) {
            var11.Ø­áŒŠá();
        }
        for (final EntityPlayer var13 : this.Ó) {
            if (var13.Âµá€(p_72885_2_, p_72885_4_, p_72885_6_) < 4096.0) {
                ((EntityPlayerMP)var13).HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S27PacketExplosion(p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, var11.Âµá€(), var11.Â().get(var13)));
            }
        }
        return var11;
    }
    
    @Override
    public void Ý(final BlockPos pos, final Block blockIn, final int eventID, final int eventParam) {
        final BlockEventData var5 = new BlockEventData(pos, blockIn, eventID, eventParam);
        for (final BlockEventData var7 : this.áŒŠ[this.£ÂµÄ]) {
            if (var7.equals(var5)) {
                return;
            }
        }
        this.áŒŠ[this.£ÂµÄ].add(var5);
    }
    
    private void Ø­á() {
        while (!this.áŒŠ[this.£ÂµÄ].isEmpty()) {
            final int var1 = this.£ÂµÄ;
            this.£ÂµÄ ^= 0x1;
            for (final BlockEventData var3 : this.áŒŠ[var1]) {
                if (this.HorizonCode_Horizon_È(var3)) {
                    this.áƒ.Œ().HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È().HorizonCode_Horizon_È(), var3.HorizonCode_Horizon_È().Â(), var3.HorizonCode_Horizon_È().Ý(), 64.0, this.£à.µà(), new S24PacketBlockAction(var3.HorizonCode_Horizon_È(), var3.Ø­áŒŠá(), var3.Â(), var3.Ý()));
                }
            }
            this.áŒŠ[var1].clear();
        }
    }
    
    private boolean HorizonCode_Horizon_È(final BlockEventData p_147485_1_) {
        final IBlockState var2 = this.Â(p_147485_1_.HorizonCode_Horizon_È());
        return var2.Ý() == p_147485_1_.Ø­áŒŠá() && var2.Ý().HorizonCode_Horizon_È(this, p_147485_1_.HorizonCode_Horizon_È(), var2, p_147485_1_.Â(), p_147485_1_.Ý());
    }
    
    public void Ï­à() {
        this.¥Æ.HorizonCode_Horizon_È();
    }
    
    @Override
    protected void Âµá€() {
        final boolean var1 = this.ˆá();
        super.Âµá€();
        if (this.ÂµÈ != this.á) {
            this.áƒ.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(7, this.á), this.£à.µà());
        }
        if (this.ˆÏ­ != this.£á) {
            this.áƒ.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(8, this.£á), this.£à.µà());
        }
        if (var1 != this.ˆá()) {
            if (var1) {
                this.áƒ.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(2, 0.0f));
            }
            else {
                this.áƒ.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(1, 0.0f));
            }
            this.áƒ.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(7, this.á));
            this.áƒ.Œ().HorizonCode_Horizon_È(new S2BPacketChangeGameState(8, this.£á));
        }
    }
    
    @Override
    protected int Ó() {
        return this.áƒ.Œ().Ø­à();
    }
    
    public MinecraftServer áˆºáˆºÈ() {
        return this.áƒ;
    }
    
    public EntityTracker ÇŽá€() {
        return this.á€;
    }
    
    public PlayerManager Ô() {
        return this.Õ;
    }
    
    public Teleporter ÇªÓ() {
        return this.È;
    }
    
    public void HorizonCode_Horizon_È(final EnumParticleTypes p_175739_1_, final double p_175739_2_, final double p_175739_4_, final double p_175739_6_, final int p_175739_8_, final double p_175739_9_, final double p_175739_11_, final double p_175739_13_, final double p_175739_15_, final int... p_175739_17_) {
        this.HorizonCode_Horizon_È(p_175739_1_, false, p_175739_2_, p_175739_4_, p_175739_6_, p_175739_8_, p_175739_9_, p_175739_11_, p_175739_13_, p_175739_15_, p_175739_17_);
    }
    
    public void HorizonCode_Horizon_È(final EnumParticleTypes p_180505_1_, final boolean p_180505_2_, final double p_180505_3_, final double p_180505_5_, final double p_180505_7_, final int p_180505_9_, final double p_180505_10_, final double p_180505_12_, final double p_180505_14_, final double p_180505_16_, final int... p_180505_18_) {
        final S2APacketParticles var19 = new S2APacketParticles(p_180505_1_, p_180505_2_, (float)p_180505_3_, (float)p_180505_5_, (float)p_180505_7_, (float)p_180505_10_, (float)p_180505_12_, (float)p_180505_14_, (float)p_180505_16_, p_180505_9_, p_180505_18_);
        for (int var20 = 0; var20 < this.Ó.size(); ++var20) {
            final EntityPlayerMP var21 = this.Ó.get(var20);
            final BlockPos var22 = var21.£á();
            final double var23 = var22.Ý(p_180505_3_, p_180505_5_, p_180505_7_);
            if (var23 <= 256.0 || (p_180505_2_ && var23 <= 65536.0)) {
                var21.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var19);
            }
        }
    }
    
    public Entity Â(final UUID uuid) {
        return this.¥à.get(uuid);
    }
    
    @Override
    public ListenableFuture HorizonCode_Horizon_È(final Runnable runnableToSchedule) {
        return this.áƒ.HorizonCode_Horizon_È(runnableToSchedule);
    }
    
    @Override
    public boolean Ï() {
        return this.áƒ.Ï();
    }
    
    static class HorizonCode_Horizon_È extends ArrayList
    {
        private static final String HorizonCode_Horizon_È = "CL_00001439";
        
        private HorizonCode_Horizon_È() {
        }
        
        HorizonCode_Horizon_È(final Object p_i1521_1_) {
            this();
        }
    }
}
