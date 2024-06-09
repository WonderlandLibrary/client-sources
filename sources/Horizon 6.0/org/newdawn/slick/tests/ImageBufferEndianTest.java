package org.newdawn.slick.tests;

import java.nio.ByteOrder;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.ImageBuffer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageBufferEndianTest extends BasicGame
{
    private ImageBuffer Ó;
    private ImageBuffer à;
    private Image Ø;
    private Image áŒŠÆ;
    private String áˆºÑ¢Õ;
    
    public ImageBufferEndianTest() {
        super("ImageBuffer Endian Test");
    }
    
    public static void main(final String[] args) {
        try {
            final AppGameContainer container = new AppGameContainer(new ImageBufferEndianTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Endianness is " + this.áˆºÑ¢Õ, 10.0f, 100.0f);
        g.HorizonCode_Horizon_È("Image below should be red", 10.0f, 200.0f);
        g.HorizonCode_Horizon_È(this.Ø, 10.0f, 220.0f);
        g.HorizonCode_Horizon_È("Image below should be blue", 410.0f, 200.0f);
        g.HorizonCode_Horizon_È(this.áŒŠÆ, 410.0f, 220.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            this.áˆºÑ¢Õ = "Big endian";
        }
        else if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.áˆºÑ¢Õ = "Little endian";
        }
        else {
            this.áˆºÑ¢Õ = "no idea";
        }
        this.HorizonCode_Horizon_È(this.Ó = new ImageBuffer(100, 100), Color.Âµá€, 100, 100);
        this.HorizonCode_Horizon_È(this.à = new ImageBuffer(100, 100), Color.Ó, 100, 100);
        this.Ø = this.Ó.Ø();
        this.áŒŠÆ = this.à.Ø();
    }
    
    private void HorizonCode_Horizon_È(final ImageBuffer buffer, final Color c, final int width, final int height) {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                buffer.HorizonCode_Horizon_È(x, y, c.Ø­áŒŠá(), c.Âµá€(), c.Ó(), c.à());
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
}
