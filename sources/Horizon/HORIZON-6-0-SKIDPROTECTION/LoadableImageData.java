package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;

public interface LoadableImageData extends ImageData
{
    void HorizonCode_Horizon_È(final boolean p0);
    
    ByteBuffer HorizonCode_Horizon_È(final InputStream p0) throws IOException;
    
    ByteBuffer HorizonCode_Horizon_È(final InputStream p0, final boolean p1, final int[] p2) throws IOException;
    
    ByteBuffer HorizonCode_Horizon_È(final InputStream p0, final boolean p1, final boolean p2, final int[] p3) throws IOException;
}
