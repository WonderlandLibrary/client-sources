package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.ParticleEmitter;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ParticleIO;
import HORIZON-6-0-SKIDPROTECTION.ConfigurableEmitter;
import HORIZON-6-0-SKIDPROTECTION.ParticleSystem;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class DuplicateEmitterTest extends BasicGame
{
    private GameContainer Ó;
    private ParticleSystem à;
    private ConfigurableEmitter Ø;
    
    public DuplicateEmitterTest() {
        super("DuplicateEmitterTest");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = container;
        try {
            this.à = ParticleIO.HorizonCode_Horizon_È("testdata/endlessexplosion.xml");
            (this.Ø = (ConfigurableEmitter)this.à.Â(0)).HorizonCode_Horizon_È(400.0f, 100.0f);
            for (int i = 0; i < 5; ++i) {
                final ConfigurableEmitter newOne = this.Ø.áŒŠÆ();
                if (newOne == null) {
                    throw new SlickException("Failed to duplicate explosionEmitter");
                }
                newOne.ŠÄ = String.valueOf(newOne.ŠÄ) + "_" + i;
                newOne.HorizonCode_Horizon_È((i + 1) * 133, 400.0f);
                this.à.HorizonCode_Horizon_È(newOne);
            }
        }
        catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.à.Ý(delta);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        this.à.áŒŠÆ();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            this.Ó.áŠ();
        }
        if (key == 37) {
            this.Ø.ˆÏ­();
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new DuplicateEmitterTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
