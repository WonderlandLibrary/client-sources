package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GraphicsFactory;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageGraphicsTest extends BasicGame
{
    private Image Ó;
    private Image à;
    private Image Ø;
    private Graphics áŒŠÆ;
    private Graphics áˆºÑ¢Õ;
    private Image ÂµÈ;
    private Font á;
    private float ˆÏ­;
    private String £á;
    
    public ImageGraphicsTest() {
        super("Image Graphics Test");
        this.£á = "none";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.ÂµÈ = new Image("testdata/logo.png");
        this.Ó = new Image("testdata/logo.png");
        this.á = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
        this.à = new Image(400, 300);
        this.Ø = new Image(100, 100);
        this.áŒŠÆ = this.à.Ø();
        (this.áˆºÑ¢Õ = this.Ó.Ø()).HorizonCode_Horizon_È("Drawing over a loaded image", 5.0f, 15.0f);
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(5.0f);
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(true);
        this.áˆºÑ¢Õ.Â(Color.Ó.Ý());
        this.áˆºÑ¢Õ.Âµá€(200.0f, 30.0f, 50.0f, 50.0f);
        this.áˆºÑ¢Õ.Â(Color.Ý);
        this.áˆºÑ¢Õ.Â(190.0f, 20.0f, 70.0f, 70.0f);
        this.áˆºÑ¢Õ.Ý();
        if (GraphicsFactory.HorizonCode_Horizon_È()) {
            this.£á = "FBO (Frame Buffer Objects)";
        }
        else if (GraphicsFactory.Â()) {
            this.£á = "Pbuffer (Pixel Buffers)";
        }
        System.out.println(this.Ó.Â(50, 50));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        this.áŒŠÆ.HorizonCode_Horizon_È(new Color(0, 0, 0, 0));
        this.áŒŠÆ.à();
        this.áŒŠÆ.HorizonCode_Horizon_È(200.0f, 160.0f, this.ˆÏ­);
        this.áŒŠÆ.HorizonCode_Horizon_È(this.á);
        this.áŒŠÆ.Ø­áŒŠá(10.0f, 10.0f, 50.0f, 50.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È("HELLO WORLD", 10.0f, 10.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È(this.ÂµÈ, 100.0f, 150.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È(this.ÂµÈ, 100.0f, 50.0f);
        this.áŒŠÆ.HorizonCode_Horizon_È(this.ÂµÈ, 50.0f, 75.0f);
        this.áŒŠÆ.Ý();
        g.Â(Color.Âµá€);
        g.Ø­áŒŠá(250.0f, 50.0f, 200.0f, 200.0f);
        this.à.HorizonCode_Horizon_È(300.0f, 100.0f);
        this.à.HorizonCode_Horizon_È(300.0f, 410.0f, 200.0f, 150.0f);
        this.à.HorizonCode_Horizon_È(505.0f, 410.0f, 100.0f, 75.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Testing On Offscreen Buffer", 300.0f, 80.0f);
        g.Â(Color.à);
        g.Â(300.0f, 100.0f, this.à.ŒÏ(), this.à.Çªà¢());
        g.Â(300.0f, 410.0f, this.à.ŒÏ() / 2, this.à.Çªà¢() / 2);
        g.Â(505.0f, 410.0f, this.à.ŒÏ() / 4, this.à.Çªà¢() / 4);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Testing Font On Back Buffer", 10.0f, 100.0f);
        g.HorizonCode_Horizon_È("Using: " + this.£á, 10.0f, 580.0f);
        g.Â(Color.Âµá€);
        g.Ø­áŒŠá(10.0f, 120.0f, 200.0f, 5.0f);
        final int xp = (int)(60.0 + Math.sin(this.ˆÏ­ / 60.0f) * 50.0);
        g.HorizonCode_Horizon_È(this.Ø, xp, 50);
        this.Ø.HorizonCode_Horizon_È(30.0f, 250.0f);
        g.Â(Color.Ý);
        g.Â(30.0f, 250.0f, this.Ø.ŒÏ(), this.Ø.Çªà¢());
        g.Â(Color.áŒŠÆ);
        g.Â(xp, 50.0f, this.Ø.ŒÏ(), this.Ø.Çªà¢());
        this.Ó.HorizonCode_Horizon_È(2.0f, 400.0f);
        g.Â(Color.Ó);
        g.Â(2.0f, 400.0f, this.Ó.ŒÏ(), this.Ó.Çªà¢());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.ˆÏ­ += delta * 0.1f;
    }
    
    public static void main(final String[] argv) {
        try {
            GraphicsFactory.HorizonCode_Horizon_È(false);
            final AppGameContainer container = new AppGameContainer(new ImageGraphicsTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
