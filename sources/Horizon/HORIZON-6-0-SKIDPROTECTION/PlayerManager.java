package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class PlayerManager
{
    private static final Logger HorizonCode_Horizon_È;
    private final WorldServer Â;
    private final List Ý;
    private final LongHashMap Ø­áŒŠá;
    private final List Âµá€;
    private final List Ó;
    private int à;
    private long Ø;
    private final int[][] áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001434";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public PlayerManager(final WorldServer p_i1176_1_) {
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = new LongHashMap();
        this.Âµá€ = Lists.newArrayList();
        this.Ó = Lists.newArrayList();
        this.áŒŠÆ = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        this.Â = p_i1176_1_;
        this.HorizonCode_Horizon_È(p_i1176_1_.áˆºáˆºÈ().Œ().Ø­à());
    }
    
    public WorldServer HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public void Â() {
        final long var1 = this.Â.Šáƒ();
        if (var1 - this.Ø > 8000L) {
            this.Ø = var1;
            for (int var2 = 0; var2 < this.Ó.size(); ++var2) {
                final HorizonCode_Horizon_È var3 = this.Ó.get(var2);
                var3.Â();
                var3.HorizonCode_Horizon_È();
            }
        }
        else {
            for (int var2 = 0; var2 < this.Âµá€.size(); ++var2) {
                final HorizonCode_Horizon_È var3 = this.Âµá€.get(var2);
                var3.Â();
            }
        }
        this.Âµá€.clear();
        if (this.Ý.isEmpty()) {
            final WorldProvider var4 = this.Â.£à;
            if (!var4.Âµá€()) {
                this.Â.ÇŽÉ.à();
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final int p_152621_1_, final int p_152621_2_) {
        final long var3 = p_152621_1_ + 2147483647L | p_152621_2_ + 2147483647L << 32;
        return this.Ø­áŒŠá.HorizonCode_Horizon_È(var3) != null;
    }
    
    private HorizonCode_Horizon_È HorizonCode_Horizon_È(final int p_72690_1_, final int p_72690_2_, final boolean p_72690_3_) {
        final long var4 = p_72690_1_ + 2147483647L | p_72690_2_ + 2147483647L << 32;
        HorizonCode_Horizon_È var5 = (HorizonCode_Horizon_È)this.Ø­áŒŠá.HorizonCode_Horizon_È(var4);
        if (var5 == null && p_72690_3_) {
            var5 = new HorizonCode_Horizon_È(p_72690_1_, p_72690_2_);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(var4, var5);
            this.Ó.add(var5);
        }
        return var5;
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180244_1_) {
        final int var2 = p_180244_1_.HorizonCode_Horizon_È() >> 4;
        final int var3 = p_180244_1_.Ý() >> 4;
        final HorizonCode_Horizon_È var4 = this.HorizonCode_Horizon_È(var2, var3, false);
        if (var4 != null) {
            var4.HorizonCode_Horizon_È(p_180244_1_.HorizonCode_Horizon_È() & 0xF, p_180244_1_.Â(), p_180244_1_.Ý() & 0xF);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP p_72683_1_) {
        final int var2 = (int)p_72683_1_.ŒÏ >> 4;
        final int var3 = (int)p_72683_1_.Ê >> 4;
        p_72683_1_.Ø­áŒŠá = p_72683_1_.ŒÏ;
        p_72683_1_.Âµá€ = p_72683_1_.Ê;
        for (int var4 = var2 - this.à; var4 <= var2 + this.à; ++var4) {
            for (int var5 = var3 - this.à; var5 <= var3 + this.à; ++var5) {
                this.HorizonCode_Horizon_È(var4, var5, true).HorizonCode_Horizon_È(p_72683_1_);
            }
        }
        this.Ý.add(p_72683_1_);
        this.Â(p_72683_1_);
    }
    
    public void Â(final EntityPlayerMP p_72691_1_) {
        final ArrayList var2 = Lists.newArrayList((Iterable)p_72691_1_.Ó);
        int var3 = 0;
        final int var4 = this.à;
        final int var5 = (int)p_72691_1_.ŒÏ >> 4;
        final int var6 = (int)p_72691_1_.Ê >> 4;
        int var7 = 0;
        int var8 = 0;
        ChunkCoordIntPair var9 = this.HorizonCode_Horizon_È(var5, var6, true).Ý;
        p_72691_1_.Ó.clear();
        if (var2.contains(var9)) {
            p_72691_1_.Ó.add(var9);
        }
        for (int var10 = 1; var10 <= var4 * 2; ++var10) {
            for (int var11 = 0; var11 < 2; ++var11) {
                final int[] var12 = this.áŒŠÆ[var3++ % 4];
                for (int var13 = 0; var13 < var10; ++var13) {
                    var7 += var12[0];
                    var8 += var12[1];
                    var9 = this.HorizonCode_Horizon_È(var5 + var7, var6 + var8, true).Ý;
                    if (var2.contains(var9)) {
                        p_72691_1_.Ó.add(var9);
                    }
                }
            }
        }
        var3 %= 4;
        for (int var10 = 0; var10 < var4 * 2; ++var10) {
            var7 += this.áŒŠÆ[var3][0];
            var8 += this.áŒŠÆ[var3][1];
            var9 = this.HorizonCode_Horizon_È(var5 + var7, var6 + var8, true).Ý;
            if (var2.contains(var9)) {
                p_72691_1_.Ó.add(var9);
            }
        }
    }
    
    public void Ý(final EntityPlayerMP p_72695_1_) {
        final int var2 = (int)p_72695_1_.Ø­áŒŠá >> 4;
        final int var3 = (int)p_72695_1_.Âµá€ >> 4;
        for (int var4 = var2 - this.à; var4 <= var2 + this.à; ++var4) {
            for (int var5 = var3 - this.à; var5 <= var3 + this.à; ++var5) {
                final HorizonCode_Horizon_È var6 = this.HorizonCode_Horizon_È(var4, var5, false);
                if (var6 != null) {
                    var6.Â(p_72695_1_);
                }
            }
        }
        this.Ý.remove(p_72695_1_);
    }
    
    private boolean HorizonCode_Horizon_È(final int p_72684_1_, final int p_72684_2_, final int p_72684_3_, final int p_72684_4_, final int p_72684_5_) {
        final int var6 = p_72684_1_ - p_72684_3_;
        final int var7 = p_72684_2_ - p_72684_4_;
        return var6 >= -p_72684_5_ && var6 <= p_72684_5_ && (var7 >= -p_72684_5_ && var7 <= p_72684_5_);
    }
    
    public void Ø­áŒŠá(final EntityPlayerMP p_72685_1_) {
        final int var2 = (int)p_72685_1_.ŒÏ >> 4;
        final int var3 = (int)p_72685_1_.Ê >> 4;
        final double var4 = p_72685_1_.Ø­áŒŠá - p_72685_1_.ŒÏ;
        final double var5 = p_72685_1_.Âµá€ - p_72685_1_.Ê;
        final double var6 = var4 * var4 + var5 * var5;
        if (var6 >= 64.0) {
            final int var7 = (int)p_72685_1_.Ø­áŒŠá >> 4;
            final int var8 = (int)p_72685_1_.Âµá€ >> 4;
            final int var9 = this.à;
            final int var10 = var2 - var7;
            final int var11 = var3 - var8;
            if (var10 != 0 || var11 != 0) {
                for (int var12 = var2 - var9; var12 <= var2 + var9; ++var12) {
                    for (int var13 = var3 - var9; var13 <= var3 + var9; ++var13) {
                        if (!this.HorizonCode_Horizon_È(var12, var13, var7, var8, var9)) {
                            this.HorizonCode_Horizon_È(var12, var13, true).HorizonCode_Horizon_È(p_72685_1_);
                        }
                        if (!this.HorizonCode_Horizon_È(var12 - var10, var13 - var11, var2, var3, var9)) {
                            final HorizonCode_Horizon_È var14 = this.HorizonCode_Horizon_È(var12 - var10, var13 - var11, false);
                            if (var14 != null) {
                                var14.Â(p_72685_1_);
                            }
                        }
                    }
                }
                this.Â(p_72685_1_);
                p_72685_1_.Ø­áŒŠá = p_72685_1_.ŒÏ;
                p_72685_1_.Âµá€ = p_72685_1_.Ê;
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayerMP p_72694_1_, final int p_72694_2_, final int p_72694_3_) {
        final HorizonCode_Horizon_È var4 = this.HorizonCode_Horizon_È(p_72694_2_, p_72694_3_, false);
        return var4 != null && var4.Â.contains(p_72694_1_) && !p_72694_1_.Ó.contains(var4.Ý);
    }
    
    public void HorizonCode_Horizon_È(int p_152622_1_) {
        p_152622_1_ = MathHelper.HorizonCode_Horizon_È(p_152622_1_, 3, 32);
        if (p_152622_1_ != this.à) {
            final int var2 = p_152622_1_ - this.à;
            final ArrayList var3 = Lists.newArrayList((Iterable)this.Ý);
            for (final EntityPlayerMP var5 : var3) {
                final int var6 = (int)var5.ŒÏ >> 4;
                final int var7 = (int)var5.Ê >> 4;
                if (var2 > 0) {
                    for (int var8 = var6 - p_152622_1_; var8 <= var6 + p_152622_1_; ++var8) {
                        for (int var9 = var7 - p_152622_1_; var9 <= var7 + p_152622_1_; ++var9) {
                            final HorizonCode_Horizon_È var10 = this.HorizonCode_Horizon_È(var8, var9, true);
                            if (!var10.Â.contains(var5)) {
                                var10.HorizonCode_Horizon_È(var5);
                            }
                        }
                    }
                }
                else {
                    for (int var8 = var6 - this.à; var8 <= var6 + this.à; ++var8) {
                        for (int var9 = var7 - this.à; var9 <= var7 + this.à; ++var9) {
                            if (!this.HorizonCode_Horizon_È(var8, var9, var6, var7, p_152622_1_)) {
                                this.HorizonCode_Horizon_È(var8, var9, true).Â(var5);
                            }
                        }
                    }
                }
            }
            this.à = p_152622_1_;
        }
    }
    
    public static int Â(final int p_72686_0_) {
        return p_72686_0_ * 16 - 16;
    }
    
    class HorizonCode_Horizon_È
    {
        private final List Â;
        private final ChunkCoordIntPair Ý;
        private short[] Ø­áŒŠá;
        private int Âµá€;
        private int Ó;
        private long à;
        private static final String Ø = "CL_00001435";
        
        public HorizonCode_Horizon_È(final int p_i1518_2_, final int p_i1518_3_) {
            this.Â = Lists.newArrayList();
            this.Ø­áŒŠá = new short[64];
            this.Ý = new ChunkCoordIntPair(p_i1518_2_, p_i1518_3_);
            PlayerManager.this.HorizonCode_Horizon_È().ÇŽÉ.Ý(p_i1518_2_, p_i1518_3_);
        }
        
        public void HorizonCode_Horizon_È(final EntityPlayerMP p_73255_1_) {
            if (this.Â.contains(p_73255_1_)) {
                PlayerManager.HorizonCode_Horizon_È.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { p_73255_1_, this.Ý.HorizonCode_Horizon_È, this.Ý.Â });
            }
            else {
                if (this.Â.isEmpty()) {
                    this.à = PlayerManager.this.Â.Šáƒ();
                }
                this.Â.add(p_73255_1_);
                p_73255_1_.Ó.add(this.Ý);
            }
        }
        
        public void Â(final EntityPlayerMP p_73252_1_) {
            if (this.Â.contains(p_73252_1_)) {
                final Chunk var2 = PlayerManager.this.Â.HorizonCode_Horizon_È(this.Ý.HorizonCode_Horizon_È, this.Ý.Â);
                if (var2.áŒŠÆ()) {
                    p_73252_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S21PacketChunkData(var2, true, 0));
                }
                this.Â.remove(p_73252_1_);
                p_73252_1_.Ó.remove(this.Ý);
                if (this.Â.isEmpty()) {
                    final long var3 = this.Ý.HorizonCode_Horizon_È + 2147483647L | this.Ý.Â + 2147483647L << 32;
                    this.HorizonCode_Horizon_È(var2);
                    PlayerManager.this.Ø­áŒŠá.Ø­áŒŠá(var3);
                    PlayerManager.this.Ó.remove(this);
                    if (this.Âµá€ > 0) {
                        PlayerManager.this.Âµá€.remove(this);
                    }
                    PlayerManager.this.HorizonCode_Horizon_È().ÇŽÉ.Â(this.Ý.HorizonCode_Horizon_È, this.Ý.Â);
                }
            }
        }
        
        public void HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È(PlayerManager.this.Â.HorizonCode_Horizon_È(this.Ý.HorizonCode_Horizon_È, this.Ý.Â));
        }
        
        private void HorizonCode_Horizon_È(final Chunk p_111196_1_) {
            p_111196_1_.Ý(p_111196_1_.Šáƒ() + PlayerManager.this.Â.Šáƒ() - this.à);
            this.à = PlayerManager.this.Â.Šáƒ();
        }
        
        public void HorizonCode_Horizon_È(final int p_151253_1_, final int p_151253_2_, final int p_151253_3_) {
            if (this.Âµá€ == 0) {
                PlayerManager.this.Âµá€.add(this);
            }
            this.Ó |= 1 << (p_151253_2_ >> 4);
            if (this.Âµá€ < 64) {
                final short var4 = (short)(p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);
                for (int var5 = 0; var5 < this.Âµá€; ++var5) {
                    if (this.Ø­áŒŠá[var5] == var4) {
                        return;
                    }
                }
                this.Ø­áŒŠá[this.Âµá€++] = var4;
            }
        }
        
        public void HorizonCode_Horizon_È(final Packet p_151251_1_) {
            for (int var2 = 0; var2 < this.Â.size(); ++var2) {
                final EntityPlayerMP var3 = this.Â.get(var2);
                if (!var3.Ó.contains(this.Ý)) {
                    var3.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_151251_1_);
                }
            }
        }
        
        public void Â() {
            if (this.Âµá€ != 0) {
                if (this.Âµá€ == 1) {
                    final int var1 = (this.Ø­áŒŠá[0] >> 12 & 0xF) + this.Ý.HorizonCode_Horizon_È * 16;
                    final int var2 = this.Ø­áŒŠá[0] & 0xFF;
                    final int var3 = (this.Ø­áŒŠá[0] >> 8 & 0xF) + this.Ý.Â * 16;
                    final BlockPos var4 = new BlockPos(var1, var2, var3);
                    this.HorizonCode_Horizon_È(new S23PacketBlockChange(PlayerManager.this.Â, var4));
                    if (PlayerManager.this.Â.Â(var4).Ý().£á()) {
                        this.HorizonCode_Horizon_È(PlayerManager.this.Â.HorizonCode_Horizon_È(var4));
                    }
                }
                else if (this.Âµá€ == 64) {
                    final int var1 = this.Ý.HorizonCode_Horizon_È * 16;
                    final int var2 = this.Ý.Â * 16;
                    this.HorizonCode_Horizon_È(new S21PacketChunkData(PlayerManager.this.Â.HorizonCode_Horizon_È(this.Ý.HorizonCode_Horizon_È, this.Ý.Â), false, this.Ó));
                    for (int var3 = 0; var3 < 16; ++var3) {
                        if ((this.Ó & 1 << var3) != 0x0) {
                            final int var5 = var3 << 4;
                            final List var6 = PlayerManager.this.Â.HorizonCode_Horizon_È(var1, var5, var2, var1 + 16, var5 + 16, var2 + 16);
                            for (int var7 = 0; var7 < var6.size(); ++var7) {
                                this.HorizonCode_Horizon_È(var6.get(var7));
                            }
                        }
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(new S22PacketMultiBlockChange(this.Âµá€, this.Ø­áŒŠá, PlayerManager.this.Â.HorizonCode_Horizon_È(this.Ý.HorizonCode_Horizon_È, this.Ý.Â)));
                    for (int var1 = 0; var1 < this.Âµá€; ++var1) {
                        final int var2 = (this.Ø­áŒŠá[var1] >> 12 & 0xF) + this.Ý.HorizonCode_Horizon_È * 16;
                        final int var3 = this.Ø­áŒŠá[var1] & 0xFF;
                        final int var5 = (this.Ø­áŒŠá[var1] >> 8 & 0xF) + this.Ý.Â * 16;
                        final BlockPos var8 = new BlockPos(var2, var3, var5);
                        if (PlayerManager.this.Â.Â(var8).Ý().£á()) {
                            this.HorizonCode_Horizon_È(PlayerManager.this.Â.HorizonCode_Horizon_È(var8));
                        }
                    }
                }
                this.Âµá€ = 0;
                this.Ó = 0;
            }
        }
        
        private void HorizonCode_Horizon_È(final TileEntity p_151252_1_) {
            if (p_151252_1_ != null) {
                final Packet var2 = p_151252_1_.£á();
                if (var2 != null) {
                    this.HorizonCode_Horizon_È(var2);
                }
            }
        }
    }
}
