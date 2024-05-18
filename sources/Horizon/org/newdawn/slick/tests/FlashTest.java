package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class FlashTest extends BasicGame
{
    private Image Ó;
    private boolean à;
    private GameContainer Ø;
    
    public FlashTest() {
        super("Flash Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ø = container;
        this.Ó = new Image("testdata/logo.tga");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È("Press space to toggle", 10.0f, 50.0f);
        if (this.à) {
            this.Ó.HorizonCode_Horizon_È(100.0f, 100.0f);
        }
        else {
            this.Ó.Â(100.0f, 100.0f, this.Ó.ŒÏ(), this.Ó.Çªà¢(), new Color(1.0f, 0.0f, 1.0f, 1.0f));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new FlashTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 57) {
            this.à = !this.à;
        }
        if (key == 1) {
            this.Ø.áŠ();
        }
    }
}
