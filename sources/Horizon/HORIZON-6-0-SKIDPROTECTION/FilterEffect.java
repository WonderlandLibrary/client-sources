package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class FilterEffect implements Effect
{
    private BufferedImageOp HorizonCode_Horizon_È;
    
    public FilterEffect() {
    }
    
    public FilterEffect(final BufferedImageOp filter) {
        this.HorizonCode_Horizon_È = filter;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final BufferedImage image, final Graphics2D g, final UnicodeFont unicodeFont, final Glyph glyph) {
        final BufferedImage scratchImage = EffectUtil.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È.filter(image, scratchImage);
        image.getGraphics().drawImage(scratchImage, 0, 0, null);
    }
    
    public BufferedImageOp HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final BufferedImageOp filter) {
        this.HorizonCode_Horizon_È = filter;
    }
}
