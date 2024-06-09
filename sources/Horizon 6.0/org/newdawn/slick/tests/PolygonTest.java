package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class PolygonTest extends BasicGame
{
    private Polygon Ó;
    private boolean à;
    private float Ø;
    
    public PolygonTest() {
        super("Polygon Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        (this.Ó = new Polygon()).Â(300.0f, 100.0f);
        this.Ó.Â(320.0f, 200.0f);
        this.Ó.Â(350.0f, 210.0f);
        this.Ó.Â(280.0f, 250.0f);
        this.Ó.Â(300.0f, 200.0f);
        this.Ó.Â(240.0f, 150.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.à = this.Ó.HorizonCode_Horizon_È(container.á€().á(), container.á€().ˆÏ­());
        this.Ó.à(0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        if (this.à) {
            g.Â(Color.Âµá€);
            g.Â(this.Ó);
        }
        g.Â(Color.Ø­áŒŠá);
        g.Ó(this.Ó.HorizonCode_Horizon_È() - 3.0f, this.Ó.Â() - 3.0f, 6.0f, 6.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(this.Ó);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new PolygonTest(), 640, 480, false);
            container.Ø­áŒŠá();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
