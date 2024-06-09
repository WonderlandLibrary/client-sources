package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C03PacketPlayer implements Packet
{
    public double HorizonCode_Horizon_È;
    public double Â;
    protected double Ý;
    public float Ø­áŒŠá;
    public float Âµá€;
    public boolean Ó;
    protected boolean à;
    protected boolean Ø;
    private static final String áŒŠÆ = "CL_00001360";
    
    public C03PacketPlayer() {
    }
    
    public C03PacketPlayer(final boolean p_i45256_1_) {
        this.Ó = p_i45256_1_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Ó = (data.readUnsignedByte() != 0);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.Ó ? 1 : 0);
    }
    
    public double HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public double Â() {
        return this.Â;
    }
    
    public double Ý() {
        return this.Ý;
    }
    
    public float Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public float Âµá€() {
        return this.Âµá€;
    }
    
    public boolean Ó() {
        return this.Ó;
    }
    
    public boolean à() {
        return this.à;
    }
    
    public boolean Ø() {
        return this.Ø;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_149469_1_) {
        this.à = p_149469_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
    
    public static class HorizonCode_Horizon_È extends C03PacketPlayer
    {
        private static final String áŒŠÆ = "CL_00001361";
        
        public HorizonCode_Horizon_È() {
            this.à = true;
        }
        
        public HorizonCode_Horizon_È(final double p_i45942_1_, final double p_i45942_3_, final double p_i45942_5_, final boolean p_i45942_7_) {
            this.HorizonCode_Horizon_È = p_i45942_1_;
            this.Â = p_i45942_3_;
            this.Ý = p_i45942_5_;
            this.Ó = p_i45942_7_;
            this.à = true;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
            this.HorizonCode_Horizon_È = data.readDouble();
            this.Â = data.readDouble();
            this.Ý = data.readDouble();
            super.HorizonCode_Horizon_È(data);
        }
        
        @Override
        public void Â(final PacketBuffer data) throws IOException {
            data.writeDouble(this.HorizonCode_Horizon_È);
            data.writeDouble(this.Â);
            data.writeDouble(this.Ý);
            super.Â(data);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final INetHandler handler) {
            super.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
        }
    }
    
    public static class Â extends C03PacketPlayer
    {
        private static final String áŒŠÆ = "CL_00001363";
        
        public Â() {
            this.Ø = true;
        }
        
        public Â(final float p_i45255_1_, final float p_i45255_2_, final boolean p_i45255_3_) {
            this.Ø­áŒŠá = p_i45255_1_;
            this.Âµá€ = p_i45255_2_;
            this.Ó = p_i45255_3_;
            this.Ø = true;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
            this.Ø­áŒŠá = data.readFloat();
            this.Âµá€ = data.readFloat();
            super.HorizonCode_Horizon_È(data);
        }
        
        @Override
        public void Â(final PacketBuffer data) throws IOException {
            data.writeFloat(this.Ø­áŒŠá);
            data.writeFloat(this.Âµá€);
            super.Â(data);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final INetHandler handler) {
            super.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
        }
    }
    
    public static class Ý extends C03PacketPlayer
    {
        private static final String áŒŠÆ = "CL_00001362";
        
        public Ý() {
            this.à = true;
            this.Ø = true;
        }
        
        public Ý(final double p_i45941_1_, final double p_i45941_3_, final double p_i45941_5_, final float p_i45941_7_, final float p_i45941_8_, final boolean p_i45941_9_) {
            this.HorizonCode_Horizon_È = p_i45941_1_;
            this.Â = p_i45941_3_;
            this.Ý = p_i45941_5_;
            this.Ø­áŒŠá = p_i45941_7_;
            this.Âµá€ = p_i45941_8_;
            this.Ó = p_i45941_9_;
            this.Ø = true;
            this.à = true;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
            this.HorizonCode_Horizon_È = data.readDouble();
            this.Â = data.readDouble();
            this.Ý = data.readDouble();
            this.Ø­áŒŠá = data.readFloat();
            this.Âµá€ = data.readFloat();
            super.HorizonCode_Horizon_È(data);
        }
        
        @Override
        public void Â(final PacketBuffer data) throws IOException {
            data.writeDouble(this.HorizonCode_Horizon_È);
            data.writeDouble(this.Â);
            data.writeDouble(this.Ý);
            data.writeFloat(this.Ø­áŒŠá);
            data.writeFloat(this.Âµá€);
            super.Â(data);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final INetHandler handler) {
            super.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
        }
    }
}
