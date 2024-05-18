package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.List;

public class S26PacketMapChunkBulk implements Packet
{
    private int[] HorizonCode_Horizon_È;
    private int[] Â;
    private S21PacketChunkData.HorizonCode_Horizon_È[] Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001306";
    
    public S26PacketMapChunkBulk() {
    }
    
    public S26PacketMapChunkBulk(final List p_i45197_1_) {
        final int var2 = p_i45197_1_.size();
        this.HorizonCode_Horizon_È = new int[var2];
        this.Â = new int[var2];
        this.Ý = new S21PacketChunkData.HorizonCode_Horizon_È[var2];
        this.Ø­áŒŠá = !p_i45197_1_.get(0).£à().£à.Å();
        for (int var3 = 0; var3 < var2; ++var3) {
            final Chunk var4 = p_i45197_1_.get(var3);
            final S21PacketChunkData.HorizonCode_Horizon_È var5 = S21PacketChunkData.HorizonCode_Horizon_È(var4, true, this.Ø­áŒŠá, 65535);
            this.HorizonCode_Horizon_È[var3] = var4.HorizonCode_Horizon_È;
            this.Â[var3] = var4.Â;
            this.Ý[var3] = var5;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Ø­áŒŠá = data.readBoolean();
        final int var2 = data.Ø­áŒŠá();
        this.HorizonCode_Horizon_È = new int[var2];
        this.Â = new int[var2];
        this.Ý = new S21PacketChunkData.HorizonCode_Horizon_È[var2];
        for (int var3 = 0; var3 < var2; ++var3) {
            this.HorizonCode_Horizon_È[var3] = data.readInt();
            this.Â[var3] = data.readInt();
            this.Ý[var3] = new S21PacketChunkData.HorizonCode_Horizon_È();
            this.Ý[var3].Â = (data.readShort() & 0xFFFF);
            this.Ý[var3].HorizonCode_Horizon_È = new byte[S21PacketChunkData.HorizonCode_Horizon_È(Integer.bitCount(this.Ý[var3].Â), this.Ø­áŒŠá, true)];
        }
        for (int var3 = 0; var3 < var2; ++var3) {
            data.readBytes(this.Ý[var3].HorizonCode_Horizon_È);
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeBoolean(this.Ø­áŒŠá);
        data.Â(this.Ý.length);
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            data.writeInt(this.HorizonCode_Horizon_È[var2]);
            data.writeInt(this.Â[var2]);
            data.writeShort((short)(this.Ý[var2].Â & 0xFFFF));
        }
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            data.writeBytes(this.Ý[var2].HorizonCode_Horizon_È);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180738_1_) {
        p_180738_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È(final int p_149255_1_) {
        return this.HorizonCode_Horizon_È[p_149255_1_];
    }
    
    public int Â(final int p_149253_1_) {
        return this.Â[p_149253_1_];
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.length;
    }
    
    public byte[] Ý(final int p_149256_1_) {
        return this.Ý[p_149256_1_].HorizonCode_Horizon_È;
    }
    
    public int Ø­áŒŠá(final int p_179754_1_) {
        return this.Ý[p_179754_1_].Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
