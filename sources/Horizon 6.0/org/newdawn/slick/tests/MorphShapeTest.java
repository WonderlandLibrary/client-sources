package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Transform;
import HORIZON-6-0-SKIDPROTECTION.Rectangle;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.MorphShape;
import HORIZON-6-0-SKIDPROTECTION.Shape;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class MorphShapeTest extends BasicGame
{
    private Shape Ó;
    private Shape à;
    private Shape Ø;
    private MorphShape áŒŠÆ;
    private float áˆºÑ¢Õ;
    
    public MorphShapeTest() {
        super("MorphShapeTest");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Rectangle(100.0f, 100.0f, 50.0f, 200.0f);
        this.Ó = this.Ó.HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È(0.1f, 100.0f, 100.0f));
        this.à = new Rectangle(200.0f, 100.0f, 50.0f, 200.0f);
        this.à = this.à.HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È(-0.6f, 100.0f, 100.0f));
        this.Ø = new Rectangle(300.0f, 100.0f, 50.0f, 200.0f);
        this.Ø = this.Ø.HorizonCode_Horizon_È(Transform.HorizonCode_Horizon_È(-0.2f, 100.0f, 100.0f));
        (this.áŒŠÆ = new MorphShape(this.Ó)).Â(this.à);
        this.áŒŠÆ.Â(this.Ø);
        container.á(true);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.áˆºÑ¢Õ += delta * 0.001f;
        this.áŒŠÆ.HorizonCode_Horizon_È(this.áˆºÑ¢Õ);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.à);
        g.HorizonCode_Horizon_È(this.Ó);
        g.Â(Color.Âµá€);
        g.HorizonCode_Horizon_È(this.à);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È(this.Ø);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(this.áŒŠÆ);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new MorphShapeTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
