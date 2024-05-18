package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.CachedRender;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class CachedRenderTest extends BasicGame
{
    private Runnable Ó;
    private CachedRender à;
    private boolean Ø;
    
    public CachedRenderTest() {
        super("Cached Render Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    final int c = i + 100;
                    container.ˆáŠ().Â(new Color(c, c, c, c));
                    container.ˆáŠ().Âµá€(i * 5 + 50, i * 3 + 50, 100.0f, 100.0f);
                }
            }
        };
        this.à = new CachedRender(this.Ó);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (container.á€().Âµá€(57)) {
            this.Ø = !this.Ø;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Press space to toggle caching", 10.0f, 130.0f);
        if (this.Ø) {
            g.HorizonCode_Horizon_È("Drawing from cache", 10.0f, 100.0f);
            this.à.HorizonCode_Horizon_È();
        }
        else {
            g.HorizonCode_Horizon_È("Drawing direct", 10.0f, 100.0f);
            this.Ó.run();
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new CachedRenderTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
