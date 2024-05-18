package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ImageBuffer;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageBufferTest extends BasicGame
{
    private Image Ó;
    
    public ImageBufferTest() {
        super("Image Buffer Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        final ImageBuffer buffer = new ImageBuffer(320, 200);
        for (int x = 0; x < 320; ++x) {
            for (int y = 0; y < 200; ++y) {
                if (y == 20) {
                    buffer.HorizonCode_Horizon_È(x, y, 255, 255, 255, 255);
                }
                else {
                    buffer.HorizonCode_Horizon_È(x, y, x, y, 0, 255);
                }
            }
        }
        this.Ó = buffer.Ø();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.Ó.HorizonCode_Horizon_È(50.0f, 50.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new ImageBufferTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
