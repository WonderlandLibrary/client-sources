package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Log;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class FontTest extends BasicGame
{
    private AngelCodeFont Ó;
    private AngelCodeFont à;
    private Image Ø;
    private static AppGameContainer áŒŠÆ;
    
    public FontTest() {
        super("Font Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.à = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
        this.Ø = new Image("testdata/demo2_00.tga", false);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.Ó.HorizonCode_Horizon_È(80.0f, 5.0f, "A Font Example", Color.Âµá€);
        this.Ó.HorizonCode_Horizon_È(100.0f, 32.0f, "We - AV - Here is a more complete line that hopefully");
        this.Ó.HorizonCode_Horizon_È(100.0f, 36 + this.Ó.Â("We Here is a more complete line that hopefully"), "will show some kerning.");
        this.à.HorizonCode_Horizon_È(80.0f, 85.0f, "A Font Example", Color.Âµá€);
        this.à.HorizonCode_Horizon_È(100.0f, 132.0f, "We - AV - Here is a more complete line that hopefully");
        this.à.HorizonCode_Horizon_È(100.0f, 136 + this.à.Â("We - Here is a more complete line that hopefully"), "will show some kerning.");
        this.Ø.HorizonCode_Horizon_È(100.0f, 400.0f);
        final String testStr = "Testing Font";
        this.à.HorizonCode_Horizon_È(100.0f, 300.0f, testStr);
        g.Â(Color.Ý);
        g.Â(100.0f, 300 + this.à.HorizonCode_Horizon_È(testStr), this.à.Ý(testStr), this.à.Â(testStr) - this.à.HorizonCode_Horizon_È(testStr));
        this.Ó.HorizonCode_Horizon_È(500.0f, 300.0f, testStr);
        g.Â(Color.Ý);
        g.Â(500.0f, 300 + this.Ó.HorizonCode_Horizon_È(testStr), this.Ó.Ý(testStr), this.Ó.Â(testStr) - this.Ó.HorizonCode_Horizon_È(testStr));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 57) {
            try {
                FontTest.áŒŠÆ.HorizonCode_Horizon_È(640, 480, false);
            }
            catch (SlickException e) {
                Log.HorizonCode_Horizon_È(e);
            }
        }
    }
    
    public static void main(final String[] argv) {
        try {
            (FontTest.áŒŠÆ = new AppGameContainer(new FontTest())).HorizonCode_Horizon_È(800, 600, false);
            FontTest.áŒŠÆ.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
