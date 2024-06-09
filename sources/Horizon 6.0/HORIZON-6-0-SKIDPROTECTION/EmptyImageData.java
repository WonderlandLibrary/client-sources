package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;

public class EmptyImageData implements ImageData
{
    private int HorizonCode_Horizon_È;
    private int Â;
    
    public EmptyImageData(final int width, final int height) {
        this.HorizonCode_Horizon_È = width;
        this.Â = height;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 32;
    }
    
    @Override
    public int Â() {
        return this.Â;
    }
    
    @Override
    public ByteBuffer Ý() {
        return BufferUtils.createByteBuffer(this.Âµá€() * this.Ø­áŒŠá() * 4);
    }
    
    @Override
    public int Ø­áŒŠá() {
        return InternalTextureLoader.HorizonCode_Horizon_È(this.Â);
    }
    
    @Override
    public int Âµá€() {
        return InternalTextureLoader.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Ó() {
        return this.HorizonCode_Horizon_È;
    }
}
