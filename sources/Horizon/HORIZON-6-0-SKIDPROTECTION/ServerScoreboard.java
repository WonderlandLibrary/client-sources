package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import com.google.common.collect.Sets;
import java.util.Set;

public class ServerScoreboard extends Scoreboard
{
    private final MinecraftServer HorizonCode_Horizon_È;
    private final Set Â;
    private ScoreboardSaveData Ý;
    private static final String Ø­áŒŠá = "CL_00001424";
    
    public ServerScoreboard(final MinecraftServer p_i1501_1_) {
        this.Â = Sets.newHashSet();
        this.HorizonCode_Horizon_È = p_i1501_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Score p_96536_1_) {
        super.HorizonCode_Horizon_È(p_96536_1_);
        if (this.Â.contains(p_96536_1_.Ý())) {
            this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3CPacketUpdateScore(p_96536_1_));
        }
        this.à();
    }
    
    @Override
    public void à(final String p_96516_1_) {
        super.à(p_96516_1_);
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3CPacketUpdateScore(p_96516_1_));
        this.à();
    }
    
    @Override
    public void Ø­áŒŠá(final String p_178820_1_, final ScoreObjective p_178820_2_) {
        super.Ø­áŒŠá(p_178820_1_, p_178820_2_);
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3CPacketUpdateScore(p_178820_1_, p_178820_2_));
        this.à();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_96530_1_, final ScoreObjective p_96530_2_) {
        final ScoreObjective var3 = this.HorizonCode_Horizon_È(p_96530_1_);
        super.HorizonCode_Horizon_È(p_96530_1_, p_96530_2_);
        if (var3 != p_96530_2_ && var3 != null) {
            if (this.áˆºÑ¢Õ(var3) > 0) {
                this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
            }
            else {
                this.áŒŠÆ(var3);
            }
        }
        if (p_96530_2_ != null) {
            if (this.Â.contains(p_96530_2_)) {
                this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
            }
            else {
                this.à(p_96530_2_);
            }
        }
        this.à();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final String p_151392_1_, final String p_151392_2_) {
        if (super.HorizonCode_Horizon_È(p_151392_1_, p_151392_2_)) {
            final ScorePlayerTeam var3 = this.Ý(p_151392_2_);
            this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3EPacketTeams(var3, Arrays.asList(p_151392_1_), 3));
            this.à();
            return true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_96512_1_, final ScorePlayerTeam p_96512_2_) {
        super.HorizonCode_Horizon_È(p_96512_1_, p_96512_2_);
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3EPacketTeams(p_96512_2_, Arrays.asList(p_96512_1_), 4));
        this.à();
    }
    
    @Override
    public void Ý(final ScoreObjective p_96522_1_) {
        super.Ý(p_96522_1_);
        this.à();
    }
    
    @Override
    public void Ø­áŒŠá(final ScoreObjective p_96532_1_) {
        super.Ø­áŒŠá(p_96532_1_);
        if (this.Â.contains(p_96532_1_)) {
            this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3BPacketScoreboardObjective(p_96532_1_, 2));
        }
        this.à();
    }
    
    @Override
    public void Âµá€(final ScoreObjective p_96533_1_) {
        super.Âµá€(p_96533_1_);
        if (this.Â.contains(p_96533_1_)) {
            this.áŒŠÆ(p_96533_1_);
        }
        this.à();
    }
    
    @Override
    public void Â(final ScorePlayerTeam p_96523_1_) {
        super.Â(p_96523_1_);
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3EPacketTeams(p_96523_1_, 0));
        this.à();
    }
    
    @Override
    public void Ý(final ScorePlayerTeam p_96538_1_) {
        super.Ý(p_96538_1_);
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3EPacketTeams(p_96538_1_, 2));
        this.à();
    }
    
    @Override
    public void Ø­áŒŠá(final ScorePlayerTeam p_96513_1_) {
        super.Ø­áŒŠá(p_96513_1_);
        this.HorizonCode_Horizon_È.Œ().HorizonCode_Horizon_È(new S3EPacketTeams(p_96513_1_, 1));
        this.à();
    }
    
    public void HorizonCode_Horizon_È(final ScoreboardSaveData p_96547_1_) {
        this.Ý = p_96547_1_;
    }
    
    protected void à() {
        if (this.Ý != null) {
            this.Ý.Ø­áŒŠá();
        }
    }
    
    public List Ó(final ScoreObjective p_96550_1_) {
        final ArrayList var2 = Lists.newArrayList();
        var2.add(new S3BPacketScoreboardObjective(p_96550_1_, 0));
        for (int var3 = 0; var3 < 19; ++var3) {
            if (this.HorizonCode_Horizon_È(var3) == p_96550_1_) {
                var2.add(new S3DPacketDisplayScoreboard(var3, p_96550_1_));
            }
        }
        for (final Score var5 : this.HorizonCode_Horizon_È(p_96550_1_)) {
            var2.add(new S3CPacketUpdateScore(var5));
        }
        return var2;
    }
    
    public void à(final ScoreObjective p_96549_1_) {
        final List var2 = this.Ó(p_96549_1_);
        for (final EntityPlayerMP var4 : this.HorizonCode_Horizon_È.Œ().Âµá€) {
            for (final Packet var6 : var2) {
                var4.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6);
            }
        }
        this.Â.add(p_96549_1_);
    }
    
    public List Ø(final ScoreObjective p_96548_1_) {
        final ArrayList var2 = Lists.newArrayList();
        var2.add(new S3BPacketScoreboardObjective(p_96548_1_, 1));
        for (int var3 = 0; var3 < 19; ++var3) {
            if (this.HorizonCode_Horizon_È(var3) == p_96548_1_) {
                var2.add(new S3DPacketDisplayScoreboard(var3, p_96548_1_));
            }
        }
        return var2;
    }
    
    public void áŒŠÆ(final ScoreObjective p_96546_1_) {
        final List var2 = this.Ø(p_96546_1_);
        for (final EntityPlayerMP var4 : this.HorizonCode_Horizon_È.Œ().Âµá€) {
            for (final Packet var6 : var2) {
                var4.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6);
            }
        }
        this.Â.remove(p_96546_1_);
    }
    
    public int áˆºÑ¢Õ(final ScoreObjective p_96552_1_) {
        int var2 = 0;
        for (int var3 = 0; var3 < 19; ++var3) {
            if (this.HorizonCode_Horizon_È(var3) == p_96552_1_) {
                ++var2;
            }
        }
        return var2;
    }
}
