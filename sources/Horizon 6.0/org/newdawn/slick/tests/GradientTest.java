package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Renderer;
import HORIZON-6-0-SKIDPROTECTION.ShapeFill;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Polygon;
import HORIZON-6-0-SKIDPROTECTION.RoundedRectangle;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.GradientFill;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GradientTest extends BasicGame
{
    private GameContainer Ó;
    private GradientFill à;
    private GradientFill Ø;
    private GradientFill áŒŠÆ;
    private Rectangle áˆºÑ¢Õ;
    private Rectangle ÂµÈ;
    private RoundedRectangle á;
    private RoundedRectangle ˆÏ­;
    private Polygon £á;
    private float Å;
    
    public GradientTest() {
        super("Gradient Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = container;
        this.áˆºÑ¢Õ = new Rectangle(400.0f, 100.0f, 200.0f, 150.0f);
        this.á = new RoundedRectangle(150.0f, 100.0f, 200.0f, 150.0f, 50.0f);
        this.ˆÏ­ = new RoundedRectangle(150.0f, 300.0f, 200.0f, 150.0f, 50.0f);
        this.ÂµÈ = new Rectangle(350.0f, 250.0f, 100.0f, 100.0f);
        (this.£á = new Polygon()).Â(400.0f, 350.0f);
        this.£á.Â(550.0f, 320.0f);
        this.£á.Â(600.0f, 380.0f);
        this.£á.Â(620.0f, 450.0f);
        this.£á.Â(500.0f, 450.0f);
        this.à = new GradientFill(0.0f, -75.0f, Color.Âµá€, 0.0f, 75.0f, Color.Ø­áŒŠá, true);
        this.Ø = new GradientFill(0.0f, -75.0f, Color.Ó, 0.0f, 75.0f, Color.Ý, true);
        this.áŒŠÆ = new GradientFill(-50.0f, -40.0f, Color.à, 50.0f, 40.0f, Color.áˆºÑ¢Õ, true);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È(400.0f, 300.0f, this.Å);
        g.Â(this.áˆºÑ¢Õ, this.à);
        g.Â(this.á, this.à);
        g.Â(this.£á, this.Ø);
        g.Â(this.ÂµÈ, this.áŒŠÆ);
        g.HorizonCode_Horizon_È(true);
        g.HorizonCode_Horizon_È(10.0f);
        g.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø);
        g.HorizonCode_Horizon_È(2.0f);
        g.HorizonCode_Horizon_È(this.£á, this.à);
        g.HorizonCode_Horizon_È(false);
        g.Â(this.ÂµÈ, this.áŒŠÆ);
        g.HorizonCode_Horizon_È(true);
        g.Â(Color.Ø);
        g.HorizonCode_Horizon_È((Shape)this.ÂµÈ);
        g.HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.Å += delta * 0.01f;
    }
    
    public static void main(final String[] argv) {
        try {
            Renderer.HorizonCode_Horizon_È(2);
            final AppGameContainer container = new AppGameContainer(new GradientTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            this.Ó.áŠ();
        }
    }
}
