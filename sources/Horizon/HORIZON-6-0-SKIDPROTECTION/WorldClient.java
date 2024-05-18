package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;
import java.util.Random;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Sets;
import java.util.Set;

public class WorldClient extends World
{
    private NetHandlerPlayClient ÇŽÉ;
    private ChunkProviderClient ˆá;
    private final Set ÇŽÕ;
    private final Set É;
    private final Minecraft áƒ;
    private final Set á€;
    private static final String Õ = "CL_00000882";
    
    public WorldClient(final NetHandlerPlayClient p_i45063_1_, final WorldSettings p_i45063_2_, final int p_i45063_3_, final EnumDifficulty p_i45063_4_, final Profiler p_i45063_5_) {
        super(new SaveHandlerMP(), new WorldInfo(p_i45063_2_, "MpServer"), WorldProvider.HorizonCode_Horizon_È(p_i45063_3_), p_i45063_5_, true);
        this.ÇŽÕ = Sets.newHashSet();
        this.É = Sets.newHashSet();
        this.áƒ = Minecraft.áŒŠà();
        this.á€ = Sets.newHashSet();
        this.ÇŽÉ = p_i45063_1_;
        this.ŒÏ().HorizonCode_Horizon_È(p_i45063_4_);
        this.Ñ¢á(new BlockPos(8, 64, 8));
        this.£à.HorizonCode_Horizon_È(this);
        this.ˆà = this.Â();
        this.Æ = new SaveDataMemoryStorage();
        this.µà();
        this.ˆà();
    }
    
    @Override
    public void r_() {
        super.r_();
        this.Â(this.Šáƒ() + 1L);
        if (this.Çªà¢().Â("doDaylightCycle")) {
            this.HorizonCode_Horizon_È(this.Ï­Ðƒà() + 1L);
        }
        this.Ï­Ðƒà.HorizonCode_Horizon_È("reEntryProcessing");
        for (int var1 = 0; var1 < 10 && !this.É.isEmpty(); ++var1) {
            final Entity var2 = this.É.iterator().next();
            this.É.remove(var2);
            if (!this.Â.contains(var2)) {
                this.HorizonCode_Horizon_È(var2);
            }
        }
        this.Ï­Ðƒà.Ý("chunkCache");
        this.ˆá.Â();
        this.Ï­Ðƒà.Ý("blocks");
        this.Ý();
        this.Ï­Ðƒà.Â();
    }
    
    public void HorizonCode_Horizon_È(final int p_73031_1_, final int p_73031_2_, final int p_73031_3_, final int p_73031_4_, final int p_73031_5_, final int p_73031_6_) {
    }
    
    @Override
    protected IChunkProvider Â() {
        return this.ˆá = new ChunkProviderClient(this);
    }
    
    @Override
    protected void Ý() {
        super.Ý();
        this.á€.retainAll(this.Ñ¢á);
        if (this.á€.size() == this.Ñ¢á.size()) {
            this.á€.clear();
        }
        int var1 = 0;
        for (final ChunkCoordIntPair var3 : this.Ñ¢á) {
            if (!this.á€.contains(var3)) {
                final int var4 = var3.HorizonCode_Horizon_È * 16;
                final int var5 = var3.Â * 16;
                this.Ï­Ðƒà.HorizonCode_Horizon_È("getChunk");
                final Chunk var6 = this.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È, var3.Â);
                this.HorizonCode_Horizon_È(var4, var5, var6);
                this.Ï­Ðƒà.Â();
                this.á€.add(var3);
                if (++var1 >= 10) {
                    return;
                }
                continue;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_73025_1_, final int p_73025_2_, final boolean p_73025_3_) {
        if (p_73025_3_) {
            this.ˆá.Ý(p_73025_1_, p_73025_2_);
        }
        else {
            this.ˆá.Â(p_73025_1_, p_73025_2_);
        }
        if (!p_73025_3_) {
            this.Â(p_73025_1_ * 16, 0, p_73025_2_ * 16, p_73025_1_ * 16 + 15, 256, p_73025_2_ * 16 + 15);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Entity p_72838_1_) {
        final boolean var2 = super.HorizonCode_Horizon_È(p_72838_1_);
        this.ÇŽÕ.add(p_72838_1_);
        if (!var2) {
            this.É.add(p_72838_1_);
        }
        else if (p_72838_1_ instanceof EntityMinecart) {
            this.áƒ.£ÂµÄ().HorizonCode_Horizon_È(new MovingSoundMinecart((EntityMinecart)p_72838_1_));
        }
        return var2;
    }
    
    @Override
    public void Â(final Entity p_72900_1_) {
        super.Â(p_72900_1_);
        this.ÇŽÕ.remove(p_72900_1_);
    }
    
    @Override
    protected void Ý(final Entity p_72923_1_) {
        super.Ý(p_72923_1_);
        if (this.É.contains(p_72923_1_)) {
            this.É.remove(p_72923_1_);
        }
    }
    
    @Override
    protected void Ø­áŒŠá(final Entity p_72847_1_) {
        super.Ø­áŒŠá(p_72847_1_);
        boolean var2 = false;
        if (this.ÇŽÕ.contains(p_72847_1_)) {
            if (p_72847_1_.Œ()) {
                this.É.add(p_72847_1_);
                var2 = true;
            }
            else {
                this.ÇŽÕ.remove(p_72847_1_);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_73027_1_, final Entity p_73027_2_) {
        final Entity var3 = this.HorizonCode_Horizon_È(p_73027_1_);
        if (var3 != null) {
            this.Â(var3);
        }
        this.ÇŽÕ.add(p_73027_2_);
        p_73027_2_.Ø­áŒŠá(p_73027_1_);
        if (!this.HorizonCode_Horizon_È(p_73027_2_)) {
            this.É.add(p_73027_2_);
        }
        this.Ø.HorizonCode_Horizon_È(p_73027_1_, p_73027_2_);
    }
    
    @Override
    public Entity HorizonCode_Horizon_È(final int p_73045_1_) {
        return (p_73045_1_ == this.áƒ.á.ˆá()) ? this.áƒ.á : super.HorizonCode_Horizon_È(p_73045_1_);
    }
    
    public Entity Â(final int p_73028_1_) {
        final Entity var2 = (Entity)this.Ø.Ø­áŒŠá(p_73028_1_);
        if (var2 != null) {
            this.ÇŽÕ.remove(var2);
            this.Â(var2);
        }
        return var2;
    }
    
    public boolean HorizonCode_Horizon_È(final BlockPos p_180503_1_, final IBlockState p_180503_2_) {
        final int var3 = p_180503_1_.HorizonCode_Horizon_È();
        final int var4 = p_180503_1_.Â();
        final int var5 = p_180503_1_.Ý();
        this.HorizonCode_Horizon_È(var3, var4, var5, var3, var4, var5);
        return super.HorizonCode_Horizon_È(p_180503_1_, p_180503_2_, 3);
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.ÇŽÉ.Â().HorizonCode_Horizon_È(new ChatComponentText("Quitting"));
    }
    
    @Override
    protected void Âµá€() {
    }
    
    @Override
    protected int Ó() {
        return this.áƒ.ŠÄ.Ý;
    }
    
    public void HorizonCode_Horizon_È(final int p_73029_1_, final int p_73029_2_, final int p_73029_3_) {
        final byte var4 = 16;
        final Random var5 = new Random();
        final ItemStack var6 = this.áƒ.á.Çª();
        final boolean var7 = this.áƒ.Âµá€.á() == WorldSettings.HorizonCode_Horizon_È.Ý && var6 != null && Block.HorizonCode_Horizon_È(var6.HorizonCode_Horizon_È()) == Blocks.¥ÇªÅ;
        final BlockPosM blockPosM = new BlockPosM(0, 0, 0, 3);
        for (int var8 = 0; var8 < 1000; ++var8) {
            final int var9 = p_73029_1_ + this.Å.nextInt(var4) - this.Å.nextInt(var4);
            final int var10 = p_73029_2_ + this.Å.nextInt(var4) - this.Å.nextInt(var4);
            final int var11 = p_73029_3_ + this.Å.nextInt(var4) - this.Å.nextInt(var4);
            blockPosM.HorizonCode_Horizon_È(var9, var10, var11);
            final IBlockState var12 = this.Â(blockPosM);
            var12.Ý().Ý(this, blockPosM, var12, var5);
            if (var7 && var12.Ý() == Blocks.¥ÇªÅ) {
                this.HorizonCode_Horizon_È(EnumParticleTypes.á€, var9 + 0.5f, var10 + 0.5f, var11 + 0.5f, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    public void à() {
        this.Â.removeAll(this.Ý);
        for (int var1 = 0; var1 < this.Ý.size(); ++var1) {
            final Entity var2 = this.Ý.get(var1);
            final int var3 = var2.£Õ;
            final int var4 = var2.Œà;
            if (var2.ˆÐƒØ­à && this.Â(var3, var4, true)) {
                this.HorizonCode_Horizon_È(var3, var4).Â(var2);
            }
        }
        for (int var1 = 0; var1 < this.Ý.size(); ++var1) {
            this.Ø­áŒŠá(this.Ý.get(var1));
        }
        this.Ý.clear();
        for (int var1 = 0; var1 < this.Â.size(); ++var1) {
            final Entity var2 = this.Â.get(var1);
            if (var2.Æ != null) {
                if (!var2.Æ.ˆáŠ && var2.Æ.µÕ == var2) {
                    continue;
                }
                var2.Æ.µÕ = null;
                var2.Æ = null;
            }
            if (var2.ˆáŠ) {
                final int var3 = var2.£Õ;
                final int var4 = var2.Œà;
                if (var2.ˆÐƒØ­à && this.Â(var3, var4, true)) {
                    this.HorizonCode_Horizon_È(var3, var4).Â(var2);
                }
                this.Â.remove(var1--);
                this.Ø­áŒŠá(var2);
            }
        }
    }
    
    @Override
    public CrashReportCategory HorizonCode_Horizon_È(final CrashReport report) {
        final CrashReportCategory var2 = super.HorizonCode_Horizon_È(report);
        var2.HorizonCode_Horizon_È("Forced entities", new Callable() {
            private static final String Â = "CL_00000883";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(WorldClient.this.ÇŽÕ.size()) + " total; " + WorldClient.this.ÇŽÕ.toString();
            }
        });
        var2.HorizonCode_Horizon_È("Retry entities", new Callable() {
            private static final String Â = "CL_00000884";
            
            public String HorizonCode_Horizon_È() {
                return String.valueOf(WorldClient.this.É.size()) + " total; " + WorldClient.this.É.toString();
            }
        });
        var2.HorizonCode_Horizon_È("Server brand", new Callable() {
            private static final String Â = "CL_00000885";
            
            public String HorizonCode_Horizon_È() {
                return WorldClient.this.áƒ.á.Ï­Ðƒà();
            }
        });
        var2.HorizonCode_Horizon_È("Server type", new Callable() {
            private static final String Â = "CL_00000886";
            
            public String HorizonCode_Horizon_È() {
                return (WorldClient.this.áƒ.ˆá() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
            }
        });
        return var2;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_175731_1_, final String p_175731_2_, final float p_175731_3_, final float p_175731_4_, final boolean p_175731_5_) {
        this.HorizonCode_Horizon_È(p_175731_1_.HorizonCode_Horizon_È() + 0.5, p_175731_1_.Â() + 0.5, p_175731_1_.Ý() + 0.5, p_175731_2_, p_175731_3_, p_175731_4_, p_175731_5_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final String soundName, final float volume, final float pitch, final boolean distanceDelay) {
        final double var11 = this.áƒ.ÇŽá€().Âµá€(x, y, z);
        final PositionedSoundRecord var12 = new PositionedSoundRecord(new ResourceLocation_1975012498(soundName), volume, pitch, (float)x, (float)y, (float)z);
        if (distanceDelay && var11 > 100.0) {
            final double var13 = Math.sqrt(var11) / 40.0;
            this.áƒ.£ÂµÄ().HorizonCode_Horizon_È(var12, (int)(var13 * 20.0));
        }
        else {
            this.áƒ.£ÂµÄ().HorizonCode_Horizon_È(var12);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final NBTTagCompound compund) {
        this.áƒ.Å.HorizonCode_Horizon_È(new EntityFireworkStarterFX(this, x, y, z, motionX, motionY, motionZ, this.áƒ.Å, compund));
    }
    
    public void HorizonCode_Horizon_È(final Scoreboard p_96443_1_) {
        this.áŒŠà = p_96443_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(long time) {
        if (time < 0L) {
            time = -time;
            this.Çªà¢().HorizonCode_Horizon_È("doDaylightCycle", "false");
        }
        else {
            this.Çªà¢().HorizonCode_Horizon_È("doDaylightCycle", "true");
        }
        super.HorizonCode_Horizon_È(time);
    }
}
