package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.TestState3;
import HORIZON-6-0-SKIDPROTECTION.TestState2;
import HORIZON-6-0-SKIDPROTECTION.GameState;
import HORIZON-6-0-SKIDPROTECTION.TestState1;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.StateBasedGame;

public class StateBasedTest extends StateBasedGame
{
    public StateBasedTest() {
        super("State Based Test");
    }
    
    @Override
    public void Â(final GameContainer container) {
        this.HorizonCode_Horizon_È(new TestState1());
        this.HorizonCode_Horizon_È(new TestState2());
        this.HorizonCode_Horizon_È(new TestState3());
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new StateBasedTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
