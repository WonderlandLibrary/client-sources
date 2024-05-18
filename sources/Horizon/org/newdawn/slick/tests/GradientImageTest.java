package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.ShapeFill;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.GradientFill;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GradientImageTest extends BasicGame
{
    private Image Ó;
    private Image à;
    private GradientFill Ø;
    private Shape áŒŠÆ;
    private Polygon áˆºÑ¢Õ;
    private GameContainer ÂµÈ;
    private float á;
    private boolean ˆÏ­;
    
    public GradientImageTest() {
        super("Gradient Image Test");
        this.ˆÏ­ = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.ÂµÈ = container;
        this.Ó = new Image("testdata/grass.png");
        this.à = new Image("testdata/rocks.png");
        this.Ø = new GradientFill(-64.0f, 0.0f, new Color(1.0f, 1.0f, 1.0f, 1.0f), 64.0f, 0.0f, new Color(0, 0, 0, 0));
        this.áŒŠÆ = new Rectangle(336.0f, 236.0f, 128.0f, 128.0f);
        (this.áˆºÑ¢Õ = new Polygon()).Â(320.0f, 220.0f);
        this.áˆºÑ¢Õ.Â(350.0f, 200.0f);
        this.áˆºÑ¢Õ.Â(450.0f, 200.0f);
        this.áˆºÑ¢Õ.Â(480.0f, 220.0f);
        this.áˆºÑ¢Õ.Â(420.0f, 400.0f);
        this.áˆºÑ¢Õ.Â(400.0f, 390.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È("R - Toggle Rotationg", 10.0f, 50.0f);
        g.HorizonCode_Horizon_È(this.Ó, 100.0f, 236.0f);
        g.HorizonCode_Horizon_È(this.à, 600.0f, 236.0f);
        g.Â(0.0f, -150.0f);
        g.HorizonCode_Horizon_È(400.0f, 300.0f, this.á);
        g.HorizonCode_Horizon_È(this.áŒŠÆ, this.à);
        g.HorizonCode_Horizon_È(this.áŒŠÆ, this.Ó, this.Ø);
        g.Ø();
        g.Â(0.0f, 150.0f);
        g.HorizonCode_Horizon_È(400.0f, 300.0f, this.á);
        g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.à);
        g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.Ó, this.Ø);
        g.Ø();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        if (this.ˆÏ­) {
            this.á += delta * 0.1f;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new GradientImageTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 19) {
            this.ˆÏ­ = !this.ˆÏ­;
        }
        if (key == 1) {
            this.ÂµÈ.áŠ();
        }
    }
}
