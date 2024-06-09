// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.cape;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.DynamicTexture;
import java.awt.image.BufferedImage;

public class ImageFrame
{
    private final int delay;
    private final BufferedImage image;
    private final String disposal;
    private DynamicTexture texture;
    private ResourceLocation location;
    
    public ImageFrame(final BufferedImage image, final int delay, final String disposal, final String name) {
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        this.texture = new DynamicTexture(image);
        this.location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("GIF", this.texture);
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public String getDisposal() {
        return this.disposal;
    }
    
    public ResourceLocation getLocation() {
        return this.location;
    }
}
