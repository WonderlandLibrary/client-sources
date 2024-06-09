package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.ScalableGame;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ScalableTest extends BasicGame
{
    public ScalableTest() {
        super("Scalable Test For Widescreen");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.Â(new Color(0.4f, 0.6f, 0.8f));
        g.Ø­áŒŠá(0.0f, 0.0f, 1024.0f, 568.0f);
        g.Â(Color.Ý);
        g.Â(5.0f, 5.0f, 1014.0f, 558.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(String.valueOf(container.á€().á()) + "," + container.á€().ˆÏ­(), 10.0f, 400.0f);
        g.Â(Color.Âµá€);
        g.Ó(container.á€().á() - 10, container.á€().ˆÏ­() - 10, 20.0f, 20.0f);
    }
    
    public static void main(final String[] argv) {
        try {
            final ScalableGame game = new ScalableGame(new ScalableTest(), 1024, 568, true) {
                @Override
                protected void Â(final GameContainer container, final Graphics g) {
                    g.Â(Color.Ý);
                    g.HorizonCode_Horizon_È("Outside The Game", 350.0f, 10.0f);
                    g.HorizonCode_Horizon_È(String.valueOf(container.á€().á()) + "," + container.á€().ˆÏ­(), 400.0f, 20.0f);
                }
            };
            final AppGameContainer container = new AppGameContainer(game);
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
