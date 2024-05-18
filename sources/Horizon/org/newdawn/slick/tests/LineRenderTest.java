package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Renderer;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Path;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class LineRenderTest extends BasicGame
{
    private Polygon Ó;
    private Path à;
    private float Ø;
    private boolean áŒŠÆ;
    
    public LineRenderTest() {
        super("LineRenderTest");
        this.Ó = new Polygon();
        this.à = new Path(100.0f, 100.0f);
        this.Ø = 10.0f;
        this.áŒŠÆ = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó.Â(100.0f, 100.0f);
        this.Ó.Â(200.0f, 80.0f);
        this.Ó.Â(320.0f, 150.0f);
        this.Ó.Â(230.0f, 210.0f);
        this.Ó.Â(170.0f, 260.0f);
        this.à.HorizonCode_Horizon_È(200.0f, 200.0f, 200.0f, 100.0f, 100.0f, 200.0f);
        this.à.HorizonCode_Horizon_È(400.0f, 100.0f, 400.0f, 200.0f, 200.0f, 100.0f);
        this.à.HorizonCode_Horizon_È(500.0f, 500.0f, 400.0f, 200.0f, 200.0f, 100.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (container.á€().Âµá€(57)) {
            this.áŒŠÆ = !this.áŒŠÆ;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È(this.áŒŠÆ);
        g.HorizonCode_Horizon_È(50.0f);
        g.Â(Color.Âµá€);
        g.HorizonCode_Horizon_È(this.à);
    }
    
    public static void main(final String[] argv) {
        try {
            Renderer.Â(4);
            Renderer.Â().Â(true);
            final AppGameContainer container = new AppGameContainer(new LineRenderTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
