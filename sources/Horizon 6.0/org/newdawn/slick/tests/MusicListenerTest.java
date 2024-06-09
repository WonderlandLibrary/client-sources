package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Music;
import HORIZON-6-0-SKIDPROTECTION.MusicListener;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class MusicListenerTest extends BasicGame implements MusicListener
{
    private boolean Ó;
    private boolean à;
    private Music Ø;
    private Music áŒŠÆ;
    
    public MusicListenerTest() {
        super("Music Listener Test");
        this.Ó = false;
        this.à = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ø = new Music("testdata/restart.ogg", false);
        this.áŒŠÆ = new Music("testdata/restart.ogg", false);
        this.Ø.HorizonCode_Horizon_È(this);
        this.áŒŠÆ.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Music music) {
        this.Ó = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Music music, final Music newMusic) {
        this.à = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        g.HorizonCode_Horizon_È("Press M to play music", 100.0f, 100.0f);
        g.HorizonCode_Horizon_È("Press S to stream music", 100.0f, 150.0f);
        if (this.Ó) {
            g.HorizonCode_Horizon_È("Music Ended", 100.0f, 200.0f);
        }
        if (this.à) {
            g.HorizonCode_Horizon_È("Music Swapped", 100.0f, 250.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 50) {
            this.Ó = false;
            this.à = false;
            this.Ø.Â();
        }
        if (key == 31) {
            this.Ó = false;
            this.à = false;
            this.áŒŠÆ.Â();
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new MusicListenerTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
