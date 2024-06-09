package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageTest extends BasicGame
{
    private Image à;
    private Image Ø;
    private Image áŒŠÆ;
    private Image áˆºÑ¢Õ;
    private Image ÂµÈ;
    private Image á;
    private Image ˆÏ­;
    private float £á;
    public static boolean Ó;
    
    static {
        ImageTest.Ó = true;
    }
    
    public ImageTest() {
        super("Image Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        final Image image = new Image("testdata/logo.png");
        this.à = image;
        this.ÂµÈ = image;
        this.ˆÏ­ = new Image("testdata/logo.png");
        this.ˆÏ­ = this.ˆÏ­.Ý(this.ˆÏ­.ŒÏ() / 2, this.ˆÏ­.Çªà¢() / 2);
        this.Ø = new Image("testdata/logo.tga", true, 2);
        (this.áˆºÑ¢Õ = new Image("testdata/logo.gif")).£á();
        this.áˆºÑ¢Õ = new Image("testdata/logo.gif");
        this.áŒŠÆ = this.áˆºÑ¢Õ.Ý(120, 120);
        this.á = this.ÂµÈ.HorizonCode_Horizon_È(200, 0, 70, 260);
        this.£á = 0.0f;
        if (ImageTest.Ó) {
            container.áŠ();
        }
        final Image test = this.à.HorizonCode_Horizon_È(50, 50, 50, 50);
        System.out.println(test.Â(50, 50));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.Â(0.0f, 0.0f, this.ÂµÈ.ŒÏ(), this.ÂµÈ.Çªà¢());
        this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 0.0f);
        this.ÂµÈ.HorizonCode_Horizon_È(500.0f, 0.0f, 200.0f, 100.0f);
        this.Ø.HorizonCode_Horizon_È(500.0f, 100.0f, 200.0f, 100.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È(400.0f, 500.0f);
        final Image flipped = this.áŒŠÆ.HorizonCode_Horizon_È(true, false);
        flipped.HorizonCode_Horizon_È(520.0f, 500.0f);
        final Image flipped2 = flipped.HorizonCode_Horizon_È(false, true);
        flipped2.HorizonCode_Horizon_È(520.0f, 380.0f);
        final Image flipped3 = flipped2.HorizonCode_Horizon_È(true, false);
        flipped3.HorizonCode_Horizon_È(400.0f, 380.0f);
        for (int i = 0; i < 3; ++i) {
            this.á.HorizonCode_Horizon_È(200 + i * 30, 300.0f);
        }
        g.Â(500.0f, 200.0f);
        g.HorizonCode_Horizon_È(50.0f, 50.0f, this.£á);
        g.HorizonCode_Horizon_È(0.3f, 0.3f);
        this.ÂµÈ.Ø­áŒŠá();
        g.Ø();
        this.ˆÏ­.Â(this.£á);
        this.ˆÏ­.HorizonCode_Horizon_È(100.0f, 200.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.£á += delta * 0.1f;
        if (this.£á > 360.0f) {
            this.£á -= 360.0f;
        }
    }
    
    public static void main(final String[] argv) {
        final boolean sharedContextTest = false;
        try {
            ImageTest.Ó = false;
            if (sharedContextTest) {
                GameContainer.Šáƒ();
                ImageTest.Ó = true;
            }
            AppGameContainer container = new AppGameContainer(new ImageTest());
            container.Ø­áŒŠá(!sharedContextTest);
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
            if (sharedContextTest) {
                System.out.println("Exit first instance");
                ImageTest.Ó = false;
                container = new AppGameContainer(new ImageTest());
                container.HorizonCode_Horizon_È(800, 600, false);
                container.Ø­áŒŠá();
            }
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 57) {
            if (this.ÂµÈ == this.áˆºÑ¢Õ) {
                this.ÂµÈ = this.à;
            }
            else {
                this.ÂµÈ = this.áˆºÑ¢Õ;
            }
        }
    }
}
