package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Bootstrap;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.TiledMap;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class IsoTiledTest extends BasicGame
{
    private TiledMap Ó;
    
    public IsoTiledTest() {
        super("Isometric Tiled Map Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new TiledMap("testdata/isoexample.tmx", "testdata/");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        this.Ó.HorizonCode_Horizon_È(350, 150);
    }
    
    public static void main(final String[] argv) {
        Bootstrap.HorizonCode_Horizon_È(new IsoTiledTest(), 800, 600, false);
    }
}
