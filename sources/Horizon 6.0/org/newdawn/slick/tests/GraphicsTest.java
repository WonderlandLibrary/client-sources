package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.FastTrig;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GraphicsTest extends BasicGame
{
    private boolean Ó;
    private float à;
    private Image Ø;
    private Polygon áŒŠÆ;
    private GameContainer áˆºÑ¢Õ;
    
    public GraphicsTest() {
        super("Graphics Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.áˆºÑ¢Õ = container;
        this.Ø = new Image("testdata/logo.tga", true);
        final Image temp = new Image("testdata/palette_tool.png");
        container.HorizonCode_Horizon_È(temp, 0, 0);
        container.HorizonCode_Horizon_È(new String[] { "testdata/icon.tga" });
        container.Ó(100);
        this.áŒŠÆ = new Polygon();
        float len = 100.0f;
        for (int x = 0; x < 360; x += 30) {
            if (len == 100.0f) {
                len = 50.0f;
            }
            else {
                len = 100.0f;
            }
            this.áŒŠÆ.Â((float)FastTrig.Â(Math.toRadians(x)) * len, (float)FastTrig.HorizonCode_Horizon_È(Math.toRadians(x)) * len);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(true);
        for (int x = 0; x < 360; x += 10) {
            g.HorizonCode_Horizon_È(700.0f, 100.0f, (int)(700.0 + Math.cos(Math.toRadians(x)) * 100.0), (int)(100.0 + Math.sin(Math.toRadians(x)) * 100.0));
        }
        g.HorizonCode_Horizon_È(false);
        g.Â(Color.Ø­áŒŠá);
        g.HorizonCode_Horizon_È("The Graphics Test!", 300.0f, 50.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Space - Toggles clipping", 400.0f, 80.0f);
        g.HorizonCode_Horizon_È("Frame rate capped to 100", 400.0f, 120.0f);
        if (this.Ó) {
            g.Â(Color.áŒŠÆ);
            g.Â(100.0f, 260.0f, 400.0f, 100.0f);
            g.HorizonCode_Horizon_È(100, 260, 400, 100);
        }
        g.Â(Color.Ø­áŒŠá);
        g.Â(100.0f, 120.0f);
        g.Â(this.áŒŠÆ);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È(3.0f);
        g.HorizonCode_Horizon_È(this.áŒŠÆ);
        g.HorizonCode_Horizon_È(1.0f);
        g.Â(0.0f, 230.0f);
        g.HorizonCode_Horizon_È(this.áŒŠÆ);
        g.Ø();
        g.Â(Color.Å);
        g.Ý(10.0f, 10.0f, 100.0f, 100.0f, 10);
        g.Ø­áŒŠá(10.0f, 210.0f, 100.0f, 100.0f, 10);
        g.HorizonCode_Horizon_È(400.0f, 300.0f, this.à);
        g.Â(Color.à);
        g.Â(200.0f, 200.0f, 200.0f, 200.0f);
        g.Â(Color.Ó);
        g.Ø­áŒŠá(250.0f, 250.0f, 100.0f, 100.0f);
        g.HorizonCode_Horizon_È(this.Ø, 300.0f, 270.0f);
        g.Â(Color.Âµá€);
        g.Âµá€(100.0f, 100.0f, 200.0f, 200.0f);
        g.Â(Color.Âµá€.Â());
        g.Ó(300.0f, 300.0f, 150.0f, 100.0f);
        g.HorizonCode_Horizon_È(true);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(5.0f);
        g.Âµá€(300.0f, 300.0f, 150.0f, 100.0f);
        g.HorizonCode_Horizon_È(true);
        g.Ø();
        if (this.Ó) {
            g.ÂµÈ();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.à += delta * 0.1f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 57) {
            this.Ó = !this.Ó;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new GraphicsTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
