package ClassSub;

import java.util.*;
import java.nio.*;
import java.io.*;

public class Class58 implements Class96
{
    private ArrayList sources;
    private Class96 picked;
    
    
    public Class58() {
        this.sources = new ArrayList();
    }
    
    public void add(final Class96 class96) {
        this.sources.add(class96);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream) throws IOException {
        return this.loadImage(inputStream, false, null);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream, final boolean b, final int[] array) throws IOException {
        return this.loadImage(inputStream, b, false, array);
    }
    
    @Override
    public ByteBuffer loadImage(final InputStream inputStream, final boolean b, final boolean b2, final int[] array) throws IOException {
        final Class261 class261 = new Class261();
        ByteBuffer loadImage = null;
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, inputStream.available());
        bufferedInputStream.mark(inputStream.available());
        int i = 0;
        while (i < this.sources.size()) {
            bufferedInputStream.reset();
            try {
                final Class96 picked = this.sources.get(i);
                loadImage = picked.loadImage(bufferedInputStream, b, b2, array);
                this.picked = picked;
            }
            catch (Exception ex) {
                Class301.warn(this.sources.get(i).getClass() + " failed to read the data", ex);
                class261.addException(ex);
                ++i;
                continue;
            }
            break;
        }
        if (this.picked == null) {
            throw class261;
        }
        return loadImage;
    }
    
    private void checkPicked() {
        if (this.picked == null) {
            throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
        }
    }
    
    @Override
    public int getDepth() {
        this.checkPicked();
        return this.picked.getDepth();
    }
    
    @Override
    public int getHeight() {
        this.checkPicked();
        return this.picked.getHeight();
    }
    
    @Override
    public ByteBuffer getImageBufferData() {
        this.checkPicked();
        return this.picked.getImageBufferData();
    }
    
    @Override
    public int getTexHeight() {
        this.checkPicked();
        return this.picked.getTexHeight();
    }
    
    @Override
    public int getTexWidth() {
        this.checkPicked();
        return this.picked.getTexWidth();
    }
    
    @Override
    public int getWidth() {
        this.checkPicked();
        return this.picked.getWidth();
    }
    
    @Override
    public void configureEdging(final boolean b) {
        for (int i = 0; i < this.sources.size(); ++i) {
            ((Class96)this.sources.get(i)).configureEdging(b);
        }
    }
}
