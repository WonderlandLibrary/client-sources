package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class CopyAreaAlphaTest extends BasicGame
{
    private Image Ó;
    private Image à;
    
    public CopyAreaAlphaTest() {
        super("CopyArea Alpha Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Image("testdata/grass.png");
        container.ˆáŠ().HorizonCode_Horizon_È(Color.Ø);
        this.à = new Image(100, 100);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È();
        g.HorizonCode_Horizon_È(Graphics.Â);
        g.Â(Color.Ý);
        g.Ó(100.0f, 100.0f, 150.0f, 150.0f);
        this.Ó.HorizonCode_Horizon_È(10.0f, 50.0f);
        g.HorizonCode_Horizon_È(this.à, 100, 100);
        g.Â(Color.Âµá€);
        g.Ø­áŒŠá(300.0f, 100.0f, 200.0f, 200.0f);
        this.à.HorizonCode_Horizon_È(350.0f, 150.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new CopyAreaAlphaTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
