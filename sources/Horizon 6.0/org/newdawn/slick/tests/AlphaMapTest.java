package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class AlphaMapTest extends BasicGame
{
    private Image Ó;
    private Image à;
    
    public AlphaMapTest() {
        super("AlphaMap Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Image("testdata/alphamap.png");
        this.à = new Image("testdata/grass.png");
        container.ˆáŠ().HorizonCode_Horizon_È(Color.Ø);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È();
        g.HorizonCode_Horizon_È(Graphics.Â);
        this.à.HorizonCode_Horizon_È(10.0f, 50.0f);
        g.Â(Color.Âµá€);
        g.Ø­áŒŠá(290.0f, 40.0f, 200.0f, 200.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(Graphics.Ý);
        this.Ó.HorizonCode_Horizon_È(300.0f, 50.0f);
        g.HorizonCode_Horizon_È(Graphics.Ø­áŒŠá);
        this.à.HorizonCode_Horizon_È(300.0f, 50.0f);
        g.HorizonCode_Horizon_È(Graphics.Â);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new AlphaMapTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
