package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Renderer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.RoundedRectangle;
import HORIZON-6-0-SKIDPROTECTION.Ellipse;
import HORIZON-6-0-SKIDPROTECTION.Transform;
import HORIZON-6-0-SKIDPROTECTION.Circle;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GeomTest extends BasicGame
{
    private Shape Ó;
    private Shape à;
    private Shape Ø;
    private Shape áŒŠÆ;
    private Shape áˆºÑ¢Õ;
    private Shape ÂµÈ;
    private Shape á;
    private Shape ˆÏ­;
    private Shape £á;
    private Shape Å;
    
    public GeomTest() {
        super("Geom Test");
        this.Ó = new Rectangle(100.0f, 100.0f, 100.0f, 100.0f);
        this.à = new Circle(500.0f, 200.0f, 50.0f);
        this.Ø = new Rectangle(150.0f, 120.0f, 50.0f, 100.0f).HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È(50.0f, 50.0f));
        this.áŒŠÆ = new Rectangle(310.0f, 210.0f, 50.0f, 100.0f).HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È((float)Math.toRadians(45.0), 335.0f, 260.0f));
        this.áˆºÑ¢Õ = new Circle(150.0f, 90.0f, 30.0f);
        this.ÂµÈ = new Circle(310.0f, 110.0f, 70.0f);
        this.á = new Ellipse(510.0f, 150.0f, 70.0f, 70.0f);
        this.ˆÏ­ = new Ellipse(510.0f, 350.0f, 30.0f, 30.0f).HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È(-510.0f, -350.0f)).HorizonCode_Horizon_È(Transform.Â(2.0f, 2.0f)).HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È(510.0f, 350.0f));
        this.£á = new RoundedRectangle(50.0f, 175.0f, 100.0f, 100.0f, 20.0f);
        this.Å = new RoundedRectangle(50.0f, 280.0f, 50.0f, 50.0f, 20.0f, 20, 5);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Red indicates a collision, green indicates no collision", 50.0f, 420.0f);
        g.HorizonCode_Horizon_È("White are the targets", 50.0f, 435.0f);
        g.ˆà();
        g.Â(100.0f, 100.0f);
        g.ˆà();
        g.Â(-50.0f, -50.0f);
        g.HorizonCode_Horizon_È(10.0f, 10.0f);
        g.Â(Color.Âµá€);
        g.Ø­áŒŠá(0.0f, 0.0f, 5.0f, 5.0f);
        g.Â(Color.Ý);
        g.Â(0.0f, 0.0f, 5.0f, 5.0f);
        g.¥Æ();
        g.Â(Color.à);
        g.Ø­áŒŠá(20.0f, 20.0f, 50.0f, 50.0f);
        g.¥Æ();
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(this.Ó);
        g.HorizonCode_Horizon_È(this.à);
        g.Â(this.Ø.HorizonCode_Horizon_È(this.Ó) ? Color.Âµá€ : Color.à);
        g.HorizonCode_Horizon_È(this.Ø);
        g.Â(this.áŒŠÆ.HorizonCode_Horizon_È(this.Ó) ? Color.Âµá€ : Color.à);
        g.HorizonCode_Horizon_È(this.áŒŠÆ);
        g.Â(this.£á.HorizonCode_Horizon_È(this.Ó) ? Color.Âµá€ : Color.à);
        g.HorizonCode_Horizon_È(this.£á);
        g.Â(this.áˆºÑ¢Õ.HorizonCode_Horizon_È(this.Ó) ? Color.Âµá€ : Color.à);
        g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ);
        g.Â(this.ÂµÈ.HorizonCode_Horizon_È(this.Ó) ? Color.Âµá€ : Color.à);
        g.HorizonCode_Horizon_È(this.ÂµÈ);
        g.Â(this.á.HorizonCode_Horizon_È(this.à) ? Color.Âµá€ : Color.à);
        g.Â(this.á);
        g.Â(this.ˆÏ­.HorizonCode_Horizon_È(this.à) ? Color.Âµá€ : Color.à);
        g.HorizonCode_Horizon_È(this.ˆÏ­);
        g.Â(this.Å);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È(this.Å);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È(new Circle(100.0f, 100.0f, 50.0f));
        g.Â(50.0f, 50.0f, 100.0f, 100.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            Renderer.HorizonCode_Horizon_È(2);
            final AppGameContainer container = new AppGameContainer(new GeomTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
