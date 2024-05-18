package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import com.google.common.collect.Lists;
import java.util.Collection;

public class S3EPacketTeams implements Packet
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private String Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private int Ó;
    private Collection à;
    private int Ø;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001334";
    
    public S3EPacketTeams() {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.Ý = "";
        this.Ø­áŒŠá = "";
        this.Âµá€ = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Âµá€;
        this.Ó = -1;
        this.à = Lists.newArrayList();
    }
    
    public S3EPacketTeams(final ScorePlayerTeam p_i45225_1_, final int p_i45225_2_) {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.Ý = "";
        this.Ø­áŒŠá = "";
        this.Âµá€ = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Âµá€;
        this.Ó = -1;
        this.à = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i45225_1_.HorizonCode_Horizon_È();
        this.Ø = p_i45225_2_;
        if (p_i45225_2_ == 0 || p_i45225_2_ == 2) {
            this.Â = p_i45225_1_.Â();
            this.Ý = p_i45225_1_.Ø­áŒŠá();
            this.Ø­áŒŠá = p_i45225_1_.Âµá€();
            this.áŒŠÆ = p_i45225_1_.áˆºÑ¢Õ();
            this.Âµá€ = p_i45225_1_.Ø().Âµá€;
            this.Ó = p_i45225_1_.ÂµÈ().HorizonCode_Horizon_È();
        }
        if (p_i45225_2_ == 0) {
            this.à.addAll(p_i45225_1_.Ý());
        }
    }
    
    public S3EPacketTeams(final ScorePlayerTeam p_i45226_1_, final Collection p_i45226_2_, final int p_i45226_3_) {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.Ý = "";
        this.Ø­áŒŠá = "";
        this.Âµá€ = Team.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Âµá€;
        this.Ó = -1;
        this.à = Lists.newArrayList();
        if (p_i45226_3_ != 3 && p_i45226_3_ != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        if (p_i45226_2_ != null && !p_i45226_2_.isEmpty()) {
            this.Ø = p_i45226_3_;
            this.HorizonCode_Horizon_È = p_i45226_1_.HorizonCode_Horizon_È();
            this.à.addAll(p_i45226_2_);
            return;
        }
        throw new IllegalArgumentException("Players cannot be null/empty");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(16);
        this.Ø = data.readByte();
        if (this.Ø == 0 || this.Ø == 2) {
            this.Â = data.Ý(32);
            this.Ý = data.Ý(16);
            this.Ø­áŒŠá = data.Ý(16);
            this.áŒŠÆ = data.readByte();
            this.Âµá€ = data.Ý(32);
            this.Ó = data.readByte();
        }
        if (this.Ø == 0 || this.Ø == 3 || this.Ø == 4) {
            for (int var2 = data.Ø­áŒŠá(), var3 = 0; var3 < var2; ++var3) {
                this.à.add(data.Ý(40));
            }
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte(this.Ø);
        if (this.Ø == 0 || this.Ø == 2) {
            data.HorizonCode_Horizon_È(this.Â);
            data.HorizonCode_Horizon_È(this.Ý);
            data.HorizonCode_Horizon_È(this.Ø­áŒŠá);
            data.writeByte(this.áŒŠÆ);
            data.HorizonCode_Horizon_È(this.Âµá€);
            data.writeByte(this.Ó);
        }
        if (this.Ø == 0 || this.Ø == 3 || this.Ø == 4) {
            data.Â(this.à.size());
            for (final String var3 : this.à) {
                data.HorizonCode_Horizon_È(var3);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public String Ý() {
        return this.Ý;
    }
    
    public String Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public Collection Âµá€() {
        return this.à;
    }
    
    public int Ó() {
        return this.Ø;
    }
    
    public int à() {
        return this.áŒŠÆ;
    }
    
    public int Ø() {
        return this.Ó;
    }
    
    public String áŒŠÆ() {
        return this.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
