package HORIZON-6-0-SKIDPROTECTION;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;
import java.util.ArrayList;

public class CompositeImageData implements LoadableImageData
{
    private ArrayList HorizonCode_Horizon_È;
    private LoadableImageData Â;
    
    public CompositeImageData() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final LoadableImageData data) {
        this.HorizonCode_Horizon_È.add(data);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis) throws IOException {
        return this.HorizonCode_Horizon_È(fis, false, null);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream fis, final boolean flipped, final int[] transparent) throws IOException {
        return this.HorizonCode_Horizon_È(fis, flipped, false, transparent);
    }
    
    @Override
    public ByteBuffer HorizonCode_Horizon_È(final InputStream is, final boolean flipped, final boolean forceAlpha, final int[] transparent) throws IOException {
        final CompositeIOException exception = new CompositeIOException();
        ByteBuffer buffer = null;
        final BufferedInputStream in = new BufferedInputStream(is, is.available());
        in.mark(is.available());
        int i = 0;
        while (i < this.HorizonCode_Horizon_È.size()) {
            in.reset();
            try {
                final LoadableImageData data = this.HorizonCode_Horizon_È.get(i);
                buffer = data.HorizonCode_Horizon_È(in, flipped, forceAlpha, transparent);
                this.Â = data;
                break;
            }
            catch (Exception e) {
                Log.Â(this.HorizonCode_Horizon_È.get(i).getClass() + " failed to read the data", e);
                exception.HorizonCode_Horizon_È(e);
                ++i;
            }
        }
        if (this.Â == null) {
            throw exception;
        }
        return buffer;
    }
    
    private void à() {
        if (this.Â == null) {
            throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        this.à();
        return this.Â.HorizonCode_Horizon_È();
    }
    
    @Override
    public int Â() {
        this.à();
        return this.Â.Â();
    }
    
    @Override
    public ByteBuffer Ý() {
        this.à();
        return this.Â.Ý();
    }
    
    @Override
    public int Ø­áŒŠá() {
        this.à();
        return this.Â.Ø­áŒŠá();
    }
    
    @Override
    public int Âµá€() {
        this.à();
        return this.Â.Âµá€();
    }
    
    @Override
    public int Ó() {
        this.à();
        return this.Â.Ó();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean edging) {
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            this.HorizonCode_Horizon_È.get(i).HorizonCode_Horizon_È(edging);
        }
    }
}
