package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.BigImage;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.SpriteSheet;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class BigSpriteSheetTest extends BasicGame
{
    private Image Ó;
    private SpriteSheet à;
    private boolean Ø;
    
    public BigSpriteSheetTest() {
        super("Big SpriteSheet Test");
        this.Ø = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new BigImage("testdata/bigimage.tga", 2, 256);
        this.à = new SpriteSheet(this.Ó, 16, 16);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        if (this.Ø) {
            for (int x = 0; x < 43; ++x) {
                for (int y = 0; y < 27; ++y) {
                    this.à.Ø­áŒŠá(x, y).HorizonCode_Horizon_È(10 + x * 18, 50 + y * 18);
                }
            }
        }
        else {
            this.à.Ó();
            for (int x = 0; x < 43; ++x) {
                for (int y = 0; y < 27; ++y) {
                    this.à.Â(10 + x * 18, 50 + y * 18, x, y);
                }
            }
            this.à.Âµá€();
        }
        g.HorizonCode_Horizon_È("Press space to toggle rendering method", 10.0f, 30.0f);
        container.Ê().HorizonCode_Horizon_È(10.0f, 100.0f, "TEST");
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new BigSpriteSheetTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (container.á€().Âµá€(57)) {
            this.Ø = !this.Ø;
        }
    }
}
