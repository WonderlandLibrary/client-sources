package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Log;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.SpriteSheetFont;
import HORIZON-6-0-SKIDPROTECTION.SpriteSheet;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class SpriteSheetFontTest extends BasicGame
{
    private Font Ó;
    private static AppGameContainer à;
    
    public SpriteSheetFontTest() {
        super("SpriteSheetFont Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        final SpriteSheet sheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
        this.Ó = new SpriteSheetFont(sheet, ' ');
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È(Color.áŒŠÆ);
        this.Ó.HorizonCode_Horizon_È(80.0f, 5.0f, "A FONT EXAMPLE", Color.Âµá€);
        this.Ó.HorizonCode_Horizon_È(100.0f, 50.0f, "A MORE COMPLETE LINE");
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
                SpriteSheetFontTest.à.HorizonCode_Horizon_È(640, 480, false);
            }
            catch (SlickException e) {
                Log.HorizonCode_Horizon_È(e);
            }
        }
    }
    
    public static void main(final String[] argv) {
        try {
            (SpriteSheetFontTest.à = new AppGameContainer(new SpriteSheetFontTest())).HorizonCode_Horizon_È(800, 600, false);
            SpriteSheetFontTest.à.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
