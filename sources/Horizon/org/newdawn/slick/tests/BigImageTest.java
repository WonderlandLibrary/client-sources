package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.BigImage;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.SpriteSheet;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class BigImageTest extends BasicGame
{
    private Image Ó;
    private Image à;
    private Image Ø;
    private Image áŒŠÆ;
    private Image áˆºÑ¢Õ;
    private Image ÂµÈ;
    private float á;
    private float ˆÏ­;
    private float £á;
    private SpriteSheet Å;
    
    public BigImageTest() {
        super("Big Image Test");
        this.£á = 30.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        final BigImage bigImage = new BigImage("testdata/bigimage.tga", 2, 512);
        this.à = bigImage;
        this.Ó = bigImage;
        this.áˆºÑ¢Õ = this.à.HorizonCode_Horizon_È(210, 210, 200, 130);
        this.ÂµÈ = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(2.0f);
        this.à = this.à.HorizonCode_Horizon_È(0.3f);
        this.Ø = this.à.HorizonCode_Horizon_È(true, false);
        this.áŒŠÆ = this.Ø.HorizonCode_Horizon_È(true, true);
        this.Å = new SpriteSheet(this.Ó, 16, 16);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.Ó.HorizonCode_Horizon_È(0.0f, 0.0f, new Color(1.0f, 1.0f, 1.0f, 0.4f));
        this.à.HorizonCode_Horizon_È(this.á, this.ˆÏ­);
        this.Ø.HorizonCode_Horizon_È(this.á + 400.0f, this.ˆÏ­);
        this.áŒŠÆ.HorizonCode_Horizon_È(this.á, this.ˆÏ­ + 300.0f);
        this.ÂµÈ.HorizonCode_Horizon_È(this.á + 300.0f, this.ˆÏ­ + 300.0f);
        this.Å.Ø­áŒŠá(7, 5).HorizonCode_Horizon_È(50.0f, 10.0f);
        g.Â(Color.Ý);
        g.Â(50.0f, 10.0f, 64.0f, 64.0f);
        g.HorizonCode_Horizon_È(this.á + 400.0f, this.ˆÏ­ + 165.0f, this.£á);
        g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.á + 300.0f, this.ˆÏ­ + 100.0f);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new BigImageTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.£á += delta * 0.1f;
        if (container.á€().Ø(203)) {
            this.á -= delta * 0.1f;
        }
        if (container.á€().Ø(205)) {
            this.á += delta * 0.1f;
        }
        if (container.á€().Ø(200)) {
            this.ˆÏ­ -= delta * 0.1f;
        }
        if (container.á€().Ø(208)) {
            this.ˆÏ­ += delta * 0.1f;
        }
    }
}
