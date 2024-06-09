package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import org.lwjgl.opengl.GL11;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class DistanceFieldTest extends BasicGame
{
    private AngelCodeFont Ó;
    
    public DistanceFieldTest() {
        super("DistanceMapTest Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new AngelCodeFont("testdata/distance.fnt", "testdata/distance-dis.png");
        container.ˆáŠ().HorizonCode_Horizon_È(Color.Ø);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        final String text = "abc";
        this.Ó.HorizonCode_Horizon_È(610.0f, 100.0f, text);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(518, 0.5f);
        this.Ó.HorizonCode_Horizon_È(610.0f, 150.0f, text);
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        g.Â(-50.0f, -130.0f);
        g.HorizonCode_Horizon_È(10.0f, 10.0f);
        this.Ó.HorizonCode_Horizon_È(0.0f, 0.0f, text);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(518, 0.5f);
        this.Ó.HorizonCode_Horizon_È(0.0f, 26.0f, text);
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        g.Ø();
        g.Â(Color.á);
        g.HorizonCode_Horizon_È("Original Size on Sheet", 620.0f, 210.0f);
        g.HorizonCode_Horizon_È("10x Scale Up", 40.0f, 575.0f);
        g.Â(Color.ÂµÈ);
        g.Â(40.0f, 40.0f, 560.0f, 530.0f);
        g.Â(610.0f, 105.0f, 150.0f, 100.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("512x512 Font Sheet", 620.0f, 300.0f);
        g.HorizonCode_Horizon_È("NEHE Charset", 620.0f, 320.0f);
        g.HorizonCode_Horizon_È("4096x4096 (8x) Source Image", 620.0f, 340.0f);
        g.HorizonCode_Horizon_È("ScanSize = 20", 620.0f, 360.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new DistanceFieldTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
