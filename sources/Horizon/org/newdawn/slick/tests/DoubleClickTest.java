package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class DoubleClickTest extends BasicGame
{
    private String Ó;
    
    public DoubleClickTest() {
        super("Double Click Test");
        this.Ó = "Click or Double Click";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È(this.Ó, 100.0f, 100.0f);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new DoubleClickTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void Ý(final int button, final int x, final int y, final int clickCount) {
        if (clickCount == 1) {
            this.Ó = "Single Click: " + button + " " + x + "," + y;
        }
        if (clickCount == 2) {
            this.Ó = "Double Click: " + button + " " + x + "," + y;
        }
    }
}
