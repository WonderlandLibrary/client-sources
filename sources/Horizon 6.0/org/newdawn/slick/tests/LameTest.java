package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class LameTest extends BasicGame
{
    private Polygon Ó;
    private Image à;
    
    public LameTest() {
        super("Lame Test");
        this.Ó = new Polygon();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó.Â(100.0f, 100.0f);
        this.Ó.Â(120.0f, 100.0f);
        this.Ó.Â(120.0f, 120.0f);
        this.Ó.Â(100.0f, 120.0f);
        this.à = new Image("testdata/rocks.png");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(this.Ó, this.à);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new LameTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
