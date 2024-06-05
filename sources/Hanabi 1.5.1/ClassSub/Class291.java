package ClassSub;

import java.io.*;

public class Class291 extends Class237 implements Class79
{
    private InputStream in;
    private String resourceName;
    private boolean flipped;
    private int filter;
    private Class237 target;
    private int[] trans;
    
    
    public Class291(final InputStream in, final String resourceName, final boolean flipped, final int filter, final int[] trans) {
        this.in = in;
        this.resourceName = resourceName;
        this.flipped = flipped;
        this.filter = filter;
        this.trans = trans;
        Class259.get().add(this);
    }
    
    @Override
    public void load() throws IOException {
        final boolean deferredLoading = Class144.get().isDeferredLoading();
        Class144.get().setDeferredLoading(false);
        this.target = Class144.get().getTexture(this.in, this.resourceName, this.flipped, this.filter, this.trans);
        Class144.get().setDeferredLoading(deferredLoading);
    }
    
    private void checkTarget() {
        if (this.target == null) {
            try {
                this.load();
                Class259.get().remove(this);
            }
            catch (IOException ex) {
                throw new RuntimeException("Attempt to use deferred texture before loading and resource not found: " + this.resourceName);
            }
        }
    }
    
    @Override
    public void bind() {
        this.checkTarget();
        this.target.bind();
    }
    
    @Override
    public float getHeight() {
        this.checkTarget();
        return this.target.getHeight();
    }
    
    @Override
    public int getImageHeight() {
        this.checkTarget();
        return this.target.getImageHeight();
    }
    
    @Override
    public int getImageWidth() {
        this.checkTarget();
        return this.target.getImageWidth();
    }
    
    @Override
    public int getTextureHeight() {
        this.checkTarget();
        return this.target.getTextureHeight();
    }
    
    @Override
    public int getTextureID() {
        this.checkTarget();
        return this.target.getTextureID();
    }
    
    @Override
    public String getTextureRef() {
        this.checkTarget();
        return this.target.getTextureRef();
    }
    
    @Override
    public int getTextureWidth() {
        this.checkTarget();
        return this.target.getTextureWidth();
    }
    
    @Override
    public float getWidth() {
        this.checkTarget();
        return this.target.getWidth();
    }
    
    @Override
    public void release() {
        this.checkTarget();
        this.target.release();
    }
    
    @Override
    public void setAlpha(final boolean alpha) {
        this.checkTarget();
        this.target.setAlpha(alpha);
    }
    
    @Override
    public void setHeight(final int height) {
        this.checkTarget();
        this.target.setHeight(height);
    }
    
    @Override
    public void setTextureHeight(final int textureHeight) {
        this.checkTarget();
        this.target.setTextureHeight(textureHeight);
    }
    
    @Override
    public void setTextureID(final int textureID) {
        this.checkTarget();
        this.target.setTextureID(textureID);
    }
    
    @Override
    public void setTextureWidth(final int textureWidth) {
        this.checkTarget();
        this.target.setTextureWidth(textureWidth);
    }
    
    @Override
    public void setWidth(final int width) {
        this.checkTarget();
        this.target.setWidth(width);
    }
    
    @Override
    public byte[] getTextureData() {
        this.checkTarget();
        return this.target.getTextureData();
    }
    
    @Override
    public String getDescription() {
        return this.resourceName;
    }
    
    @Override
    public boolean hasAlpha() {
        this.checkTarget();
        return this.target.hasAlpha();
    }
    
    @Override
    public void setTextureFilter(final int textureFilter) {
        this.checkTarget();
        this.target.setTextureFilter(textureFilter);
    }
}
