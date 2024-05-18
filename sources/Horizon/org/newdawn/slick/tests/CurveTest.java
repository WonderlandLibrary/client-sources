package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.Vector2f;
import HORIZON-6-0-SKIDPROTECTION.Curve;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class CurveTest extends BasicGame
{
    private Curve Ó;
    private Vector2f à;
    private Vector2f Ø;
    private Vector2f áŒŠÆ;
    private Vector2f áˆºÑ¢Õ;
    private Polygon ÂµÈ;
    
    public CurveTest() {
        super("Curve Test");
        this.à = new Vector2f(100.0f, 300.0f);
        this.Ø = new Vector2f(100.0f, 100.0f);
        this.áŒŠÆ = new Vector2f(300.0f, 100.0f);
        this.áˆºÑ¢Õ = new Vector2f(300.0f, 300.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        container.ˆáŠ().HorizonCode_Horizon_È(Color.Ý);
        this.Ó = new Curve(this.áˆºÑ¢Õ, this.áŒŠÆ, this.Ø, this.à);
        (this.ÂµÈ = new Polygon()).Â(500.0f, 200.0f);
        this.ÂµÈ.Â(600.0f, 200.0f);
        this.ÂµÈ.Â(700.0f, 300.0f);
        this.ÂµÈ.Â(400.0f, 300.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    private void HorizonCode_Horizon_È(final Graphics g, final Vector2f p) {
        g.Â(p.HorizonCode_Horizon_È - 5.0f, p.Â - 5.0f, 10.0f, 10.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.áŒŠÆ);
        this.HorizonCode_Horizon_È(g, this.à);
        this.HorizonCode_Horizon_È(g, this.áˆºÑ¢Õ);
        g.Â(Color.Âµá€);
        this.HorizonCode_Horizon_È(g, this.Ø);
        this.HorizonCode_Horizon_È(g, this.áŒŠÆ);
        g.Â(Color.Ø);
        g.HorizonCode_Horizon_È(this.Ó);
        g.Â(this.Ó);
        g.HorizonCode_Horizon_È(this.ÂµÈ);
        g.Â(this.ÂµÈ);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new CurveTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
