package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S14PacketEntity implements Packet
{
    protected int HorizonCode_Horizon_È;
    protected byte Â;
    protected byte Ý;
    protected byte Ø­áŒŠá;
    protected byte Âµá€;
    protected byte Ó;
    protected boolean à;
    protected boolean Ø;
    private static final String áŒŠÆ = "CL_00001312";
    
    public S14PacketEntity() {
    }
    
    public S14PacketEntity(final int p_i45206_1_) {
        this.HorizonCode_Horizon_È = p_i45206_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    public Entity HorizonCode_Horizon_È(final World worldIn) {
        return worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public byte HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public byte Â() {
        return this.Ý;
    }
    
    public byte Ý() {
        return this.Ø­áŒŠá;
    }
    
    public byte Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public byte Âµá€() {
        return this.Ó;
    }
    
    public boolean Ó() {
        return this.Ø;
    }
    
    public boolean à() {
        return this.à;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public static class HorizonCode_Horizon_È extends S14PacketEntity
    {
        private static final String áŒŠÆ = "CL_00001313";
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final int p_i45974_1_, final byte p_i45974_2_, final byte p_i45974_3_, final byte p_i45974_4_, final boolean p_i45974_5_) {
            super(p_i45974_1_);
            this.Â = p_i45974_2_;
            this.Ý = p_i45974_3_;
            this.Ø­áŒŠá = p_i45974_4_;
            this.à = p_i45974_5_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
            super.HorizonCode_Horizon_È(data);
            this.Â = data.readByte();
            this.Ý = data.readByte();
            this.Ø­áŒŠá = data.readByte();
            this.à = data.readBoolean();
        }
        
        @Override
        public void Â(final PacketBuffer data) throws IOException {
            super.Â(data);
            data.writeByte(this.Â);
            data.writeByte(this.Ý);
            data.writeByte(this.Ø­áŒŠá);
            data.writeBoolean(this.à);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final INetHandler handler) {
            super.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
        }
    }
    
    public static class Â extends S14PacketEntity
    {
        private static final String áŒŠÆ = "CL_00001315";
        
        public Â() {
            this.Ø = true;
        }
        
        public Â(final int p_i45972_1_, final byte p_i45972_2_, final byte p_i45972_3_, final boolean p_i45972_4_) {
            super(p_i45972_1_);
            this.Âµá€ = p_i45972_2_;
            this.Ó = p_i45972_3_;
            this.Ø = true;
            this.à = p_i45972_4_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
            super.HorizonCode_Horizon_È(data);
            this.Âµá€ = data.readByte();
            this.Ó = data.readByte();
            this.à = data.readBoolean();
        }
        
        @Override
        public void Â(final PacketBuffer data) throws IOException {
            super.Â(data);
            data.writeByte(this.Âµá€);
            data.writeByte(this.Ó);
            data.writeBoolean(this.à);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final INetHandler handler) {
            super.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
        }
    }
    
    public static class Ý extends S14PacketEntity
    {
        private static final String áŒŠÆ = "CL_00001314";
        
        public Ý() {
            this.Ø = true;
        }
        
        public Ý(final int p_i45973_1_, final byte p_i45973_2_, final byte p_i45973_3_, final byte p_i45973_4_, final byte p_i45973_5_, final byte p_i45973_6_, final boolean p_i45973_7_) {
            super(p_i45973_1_);
            this.Â = p_i45973_2_;
            this.Ý = p_i45973_3_;
            this.Ø­áŒŠá = p_i45973_4_;
            this.Âµá€ = p_i45973_5_;
            this.Ó = p_i45973_6_;
            this.à = p_i45973_7_;
            this.Ø = true;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
            super.HorizonCode_Horizon_È(data);
            this.Â = data.readByte();
            this.Ý = data.readByte();
            this.Ø­áŒŠá = data.readByte();
            this.Âµá€ = data.readByte();
            this.Ó = data.readByte();
            this.à = data.readBoolean();
        }
        
        @Override
        public void Â(final PacketBuffer data) throws IOException {
            super.Â(data);
            data.writeByte(this.Â);
            data.writeByte(this.Ý);
            data.writeByte(this.Ø­áŒŠá);
            data.writeByte(this.Âµá€);
            data.writeByte(this.Ó);
            data.writeBoolean(this.à);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final INetHandler handler) {
            super.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
        }
    }
}
