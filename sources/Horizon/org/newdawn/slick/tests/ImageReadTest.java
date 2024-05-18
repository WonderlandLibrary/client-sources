package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageReadTest extends BasicGame
{
    private Image Ó;
    private Color[] à;
    private Graphics Ø;
    
    public ImageReadTest() {
        super("Image Read Test");
        this.à = new Color[6];
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Image("testdata/testcard.png");
        this.à[0] = this.Ó.Â(0, 0);
        this.à[1] = this.Ó.Â(30, 40);
        this.à[2] = this.Ó.Â(55, 70);
        this.à[3] = this.Ó.Â(80, 90);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.Ø = g;
        this.Ó.HorizonCode_Horizon_È(100.0f, 100.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Move mouse over test image", 200.0f, 20.0f);
        g.Â(this.à[0]);
        g.HorizonCode_Horizon_È(this.à[0].toString(), 100.0f, 300.0f);
        g.Â(this.à[1]);
        g.HorizonCode_Horizon_È(this.à[1].toString(), 150.0f, 320.0f);
        g.Â(this.à[2]);
        g.HorizonCode_Horizon_È(this.à[2].toString(), 200.0f, 340.0f);
        g.Â(this.à[3]);
        g.HorizonCode_Horizon_È(this.à[3].toString(), 250.0f, 360.0f);
        if (this.à[4] != null) {
            g.Â(this.à[4]);
            g.HorizonCode_Horizon_È("On image: " + this.à[4].toString(), 100.0f, 250.0f);
        }
        if (this.à[5] != null) {
            g.Â(Color.Ý);
            g.HorizonCode_Horizon_È("On screen: " + this.à[5].toString(), 100.0f, 270.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        final int mx = container.á€().á();
        final int my = container.á€().ˆÏ­();
        if (mx >= 100 && my >= 100 && mx < 200 && my < 200) {
            this.à[4] = this.Ó.Â(mx - 100, my - 100);
        }
        else {
            this.à[4] = Color.Ø;
        }
        this.à[5] = this.Ø.Â(mx, my);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new ImageReadTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
