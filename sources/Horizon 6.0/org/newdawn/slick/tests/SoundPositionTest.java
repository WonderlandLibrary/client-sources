package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.SoundStore;
import HORIZON-6-0-SKIDPROTECTION.Music;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class SoundPositionTest extends BasicGame
{
    private GameContainer Ó;
    private Music à;
    private int[] Ø;
    
    public SoundPositionTest() {
        super("Music Position Test");
        this.Ø = new int[3];
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        SoundStore.Å().Â(32);
        this.Ó = container;
        (this.à = new Music("testdata/kirby.ogg", true)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("Position: " + this.à.Ø(), 100.0f, 100.0f);
        g.HorizonCode_Horizon_È("Space - Pause/Resume", 100.0f, 130.0f);
        g.HorizonCode_Horizon_È("Right Arrow - Advance 5 seconds", 100.0f, 145.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 57) {
            if (this.à.Ó()) {
                this.à.Ý();
            }
            else {
                this.à.Âµá€();
            }
        }
        if (key == 205) {
            this.à.Â(this.à.Ø() + 5.0f);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new SoundPositionTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
