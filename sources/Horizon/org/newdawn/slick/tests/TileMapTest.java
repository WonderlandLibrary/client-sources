package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.TiledMap;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class TileMapTest extends BasicGame
{
    private TiledMap Ó;
    private String à;
    private String Ø;
    private String áŒŠÆ;
    private String áˆºÑ¢Õ;
    private int ÂµÈ;
    private static int á;
    private int ˆÏ­;
    
    static {
        TileMapTest.á = 1000;
    }
    
    public TileMapTest() {
        super("Tile Map Test");
        this.ÂµÈ = 0;
        this.ˆÏ­ = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new TiledMap("testdata/testmap.tmx", "testdata");
        this.à = this.Ó.HorizonCode_Horizon_È("name", "Unknown map name");
        this.Ø = this.Ó.HorizonCode_Horizon_È(0, "monsters", "easy peasy");
        this.áŒŠÆ = this.Ó.HorizonCode_Horizon_È("zaphod", "Undefined map property");
        this.áˆºÑ¢Õ = this.Ó.HorizonCode_Horizon_È(1, "beeblebrox", "Undefined layer property");
        this.ˆÏ­ = this.Ó.Â(10, 10, 0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.Ó.HorizonCode_Horizon_È(10, 10, 4, 4, 15, 15);
        g.HorizonCode_Horizon_È(0.35f, 0.35f);
        this.Ó.HorizonCode_Horizon_È(1400, 0);
        g.Ø();
        g.HorizonCode_Horizon_È("map name: " + this.à, 10.0f, 500.0f);
        g.HorizonCode_Horizon_È("monster difficulty: " + this.Ø, 10.0f, 550.0f);
        g.HorizonCode_Horizon_È("non existing map property: " + this.áŒŠÆ, 10.0f, 525.0f);
        g.HorizonCode_Horizon_È("non existing layer property: " + this.áˆºÑ¢Õ, 10.0f, 575.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.ÂµÈ += delta;
        if (this.ÂµÈ > TileMapTest.á) {
            this.ÂµÈ -= TileMapTest.á;
            final int currentTileID = this.Ó.Â(10, 10, 0);
            if (currentTileID != this.ˆÏ­) {
                this.Ó.HorizonCode_Horizon_È(10, 10, 0, this.ˆÏ­);
            }
            else {
                this.Ó.HorizonCode_Horizon_È(10, 10, 0, 1);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new TileMapTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
