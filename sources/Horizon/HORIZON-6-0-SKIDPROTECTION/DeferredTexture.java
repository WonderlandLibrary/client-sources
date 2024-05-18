package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.io.InputStream;

public class DeferredTexture extends TextureImpl implements DeferredResource
{
    private InputStream Ý;
    private String Ø­áŒŠá;
    private boolean Âµá€;
    private int Ó;
    private TextureImpl à;
    private int[] Ø;
    
    public DeferredTexture(final InputStream in, final String resourceName, final boolean flipped, final int filter, final int[] trans) {
        this.Ý = in;
        this.Ø­áŒŠá = resourceName;
        this.Âµá€ = flipped;
        this.Ó = filter;
        this.Ø = trans;
        LoadingList.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È() throws IOException {
        final boolean before = InternalTextureLoader.HorizonCode_Horizon_È().Â();
        InternalTextureLoader.HorizonCode_Horizon_È().Â(false);
        this.à = InternalTextureLoader.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.Ý, this.Ø­áŒŠá, this.Âµá€, this.Ó, this.Ø);
        InternalTextureLoader.HorizonCode_Horizon_È().Â(before);
    }
    
    private void ¥Æ() {
        if (this.à == null) {
            try {
                this.HorizonCode_Horizon_È();
                LoadingList.HorizonCode_Horizon_È().Â(this);
            }
            catch (IOException e) {
                throw new RuntimeException("Attempt to use deferred texture before loading and resource not found: " + this.Ø­áŒŠá);
            }
        }
    }
    
    @Override
    public void Ý() {
        this.¥Æ();
        this.à.Ý();
    }
    
    @Override
    public float Ø­áŒŠá() {
        this.¥Æ();
        return this.à.Ø­áŒŠá();
    }
    
    @Override
    public int Âµá€() {
        this.¥Æ();
        return this.à.Âµá€();
    }
    
    @Override
    public int Ó() {
        this.¥Æ();
        return this.à.Ó();
    }
    
    @Override
    public int à() {
        this.¥Æ();
        return this.à.à();
    }
    
    @Override
    public int Ø() {
        this.¥Æ();
        return this.à.Ø();
    }
    
    @Override
    public String áŒŠÆ() {
        this.¥Æ();
        return this.à.áŒŠÆ();
    }
    
    @Override
    public int áˆºÑ¢Õ() {
        this.¥Æ();
        return this.à.áˆºÑ¢Õ();
    }
    
    @Override
    public float ÂµÈ() {
        this.¥Æ();
        return this.à.ÂµÈ();
    }
    
    @Override
    public void á() {
        this.¥Æ();
        this.à.á();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean alpha) {
        this.¥Æ();
        this.à.HorizonCode_Horizon_È(alpha);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int height) {
        this.¥Æ();
        this.à.HorizonCode_Horizon_È(height);
    }
    
    @Override
    public void Â(final int texHeight) {
        this.¥Æ();
        this.à.Â(texHeight);
    }
    
    @Override
    public void Ý(final int textureID) {
        this.¥Æ();
        this.à.Ý(textureID);
    }
    
    @Override
    public void Ø­áŒŠá(final int texWidth) {
        this.¥Æ();
        this.à.Ø­áŒŠá(texWidth);
    }
    
    @Override
    public void Âµá€(final int width) {
        this.¥Æ();
        this.à.Âµá€(width);
    }
    
    @Override
    public byte[] ˆÏ­() {
        this.¥Æ();
        return this.à.ˆÏ­();
    }
    
    @Override
    public String Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public boolean £á() {
        this.¥Æ();
        return this.à.£á();
    }
    
    @Override
    public void Ó(final int textureFilter) {
        this.¥Æ();
        this.à.Ó(textureFilter);
    }
}
