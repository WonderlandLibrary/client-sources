package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.SimpleDiagramRenderer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.InkscapeLoader;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Diagram;
import HORIZON-6-0-SKIDPROTECTION.SVGMorph;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class MorphSVGTest extends BasicGame
{
    private SVGMorph Ó;
    private Diagram à;
    private float Ø;
    private float áŒŠÆ;
    
    public MorphSVGTest() {
        super("MorphShapeTest");
        this.áŒŠÆ = -300.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.à = InkscapeLoader.HorizonCode_Horizon_È("testdata/svg/walk1.svg");
        (this.Ó = new SVGMorph(this.à)).HorizonCode_Horizon_È(InkscapeLoader.HorizonCode_Horizon_È("testdata/svg/walk2.svg"));
        this.Ó.HorizonCode_Horizon_È(InkscapeLoader.HorizonCode_Horizon_È("testdata/svg/walk3.svg"));
        this.Ó.HorizonCode_Horizon_È(InkscapeLoader.HorizonCode_Horizon_È("testdata/svg/walk4.svg"));
        container.á(true);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.Ó.HorizonCode_Horizon_È(delta * 0.003f);
        this.áŒŠÆ += delta * 0.2f;
        if (this.áŒŠÆ > 550.0f) {
            this.áŒŠÆ = -450.0f;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(this.áŒŠÆ, 0.0f);
        SimpleDiagramRenderer.HorizonCode_Horizon_È(g, this.Ó);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new MorphSVGTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
