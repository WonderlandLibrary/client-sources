package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Input;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class KeyRepeatTest extends BasicGame
{
    private int Ó;
    private Input à;
    
    public KeyRepeatTest() {
        super("KeyRepeatTest");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        (this.à = container.á€()).Ó(300, 100);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È("Key Press Count: " + this.Ó, 100.0f, 100.0f);
        g.HorizonCode_Horizon_È("Press Space to Toggle Key Repeat", 100.0f, 150.0f);
        g.HorizonCode_Horizon_È("Key Repeat Enabled: " + this.à.¥Æ(), 100.0f, 200.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new KeyRepeatTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        ++this.Ó;
        if (key == 57) {
            if (this.à.¥Æ()) {
                this.à.ˆà();
            }
            else {
                this.à.Ó(300, 100);
            }
        }
    }
}
