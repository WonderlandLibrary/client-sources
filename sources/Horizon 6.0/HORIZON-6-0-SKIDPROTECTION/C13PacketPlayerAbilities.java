package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C13PacketPlayerAbilities implements Packet
{
    private boolean HorizonCode_Horizon_È;
    private boolean Â;
    private boolean Ý;
    private boolean Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private static final String à = "CL_00001364";
    
    public C13PacketPlayerAbilities() {
    }
    
    public C13PacketPlayerAbilities(final PlayerCapabilities capabilities) {
        this.HorizonCode_Horizon_È(capabilities.HorizonCode_Horizon_È);
        this.Â(capabilities.Â);
        this.Ý(capabilities.Ý);
        this.Ø­áŒŠá(capabilities.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(capabilities.HorizonCode_Horizon_È());
        this.Â(capabilities.Â());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        final byte var2 = data.readByte();
        this.HorizonCode_Horizon_È((var2 & 0x1) > 0);
        this.Â((var2 & 0x2) > 0);
        this.Ý((var2 & 0x4) > 0);
        this.Ø­áŒŠá((var2 & 0x8) > 0);
        this.HorizonCode_Horizon_È(data.readFloat());
        this.Â(data.readFloat());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        byte var2 = 0;
        if (this.HorizonCode_Horizon_È()) {
            var2 |= 0x1;
        }
        if (this.Â()) {
            var2 |= 0x2;
        }
        if (this.Ý()) {
            var2 |= 0x4;
        }
        if (this.Ø­áŒŠá()) {
            var2 |= 0x8;
        }
        data.writeByte(var2);
        data.writeFloat(this.Âµá€);
        data.writeFloat(this.Ó);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180761_1_) {
        p_180761_1_.HorizonCode_Horizon_È(this);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final boolean isInvulnerable) {
        this.HorizonCode_Horizon_È = isInvulnerable;
    }
    
    public boolean Â() {
        return this.Â;
    }
    
    public void Â(final boolean isFlying) {
        this.Â = isFlying;
    }
    
    public boolean Ý() {
        return this.Ý;
    }
    
    public void Ý(final boolean isAllowFlying) {
        this.Ý = isAllowFlying;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public void Ø­áŒŠá(final boolean isCreativeMode) {
        this.Ø­áŒŠá = isCreativeMode;
    }
    
    public void HorizonCode_Horizon_È(final float flySpeedIn) {
        this.Âµá€ = flySpeedIn;
    }
    
    public void Â(final float walkSpeedIn) {
        this.Ó = walkSpeedIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
