package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ClipTest extends BasicGame
{
    private float Ó;
    private boolean à;
    private boolean Ø;
    
    public ClipTest() {
        super("Clip Test");
        this.Ó = 0.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.Ó += delta * 0.01f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("1 - No Clipping", 100.0f, 10.0f);
        g.HorizonCode_Horizon_È("2 - Screen Clipping", 100.0f, 30.0f);
        g.HorizonCode_Horizon_È("3 - World Clipping", 100.0f, 50.0f);
        if (this.à) {
            g.HorizonCode_Horizon_È("WORLD CLIPPING ENABLED", 200.0f, 80.0f);
        }
        if (this.Ø) {
            g.HorizonCode_Horizon_È("SCREEN CLIPPING ENABLED", 200.0f, 80.0f);
        }
        g.HorizonCode_Horizon_È(400.0f, 400.0f, this.Ó);
        if (this.à) {
            g.Ý(350.0f, 302.0f, 100.0f, 196.0f);
        }
        if (this.Ø) {
            g.HorizonCode_Horizon_È(350, 302, 100, 196);
        }
        g.Â(Color.Âµá€);
        g.Ó(300.0f, 300.0f, 200.0f, 200.0f);
        g.Â(Color.Ó);
        g.Ø­áŒŠá(390.0f, 200.0f, 20.0f, 400.0f);
        g.ÂµÈ();
        g.á();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 2) {
            this.à = false;
            this.Ø = false;
        }
        if (key == 3) {
            this.à = false;
            this.Ø = true;
        }
        if (key == 4) {
            this.à = true;
            this.Ø = false;
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new ClipTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
