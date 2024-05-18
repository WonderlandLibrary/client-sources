package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class AntiAliasTest extends BasicGame
{
    public AntiAliasTest() {
        super("AntiAlias Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        container.ˆáŠ().HorizonCode_Horizon_È(Color.à);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È(true);
        g.Â(Color.Âµá€);
        g.Âµá€(100.0f, 100.0f, 100.0f, 100.0f);
        g.Ó(300.0f, 100.0f, 100.0f, 100.0f);
        g.HorizonCode_Horizon_È(false);
        g.Â(Color.Âµá€);
        g.Âµá€(100.0f, 300.0f, 100.0f, 100.0f);
        g.Ó(300.0f, 300.0f, 100.0f, 100.0f);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new AntiAliasTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
