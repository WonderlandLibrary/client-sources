package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class PureFontTest extends BasicGame
{
    private Font Ó;
    private Image à;
    private static AppGameContainer Ø;
    
    public PureFontTest() {
        super("Hiero Font Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.à = new Image("testdata/sky.jpg");
        this.Ó = new AngelCodeFont("testdata/hiero.fnt", "testdata/hiero.png");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.à.HorizonCode_Horizon_È(0.0f, 0.0f, 800.0f, 600.0f);
        this.Ó.HorizonCode_Horizon_È(100.0f, 32.0f, "On top of old smokey, all");
        this.Ó.HorizonCode_Horizon_È(100.0f, 80.0f, "covered with sand..");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            (PureFontTest.Ø = new AppGameContainer(new PureFontTest())).HorizonCode_Horizon_È(800, 600, false);
            PureFontTest.Ø.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
