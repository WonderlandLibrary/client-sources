package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ParticleEmitter;
import HORIZON-6-0-SKIDPROTECTION.FireEmitter;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.ParticleSystem;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ParticleTest extends BasicGame
{
    private ParticleSystem Ó;
    private int à;
    
    public ParticleTest() {
        super("Particle Test");
        this.à = 2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        final Image image = new Image("testdata/particle.tga", true);
        (this.Ó = new ParticleSystem(image)).HorizonCode_Horizon_È(new FireEmitter(400, 300, 45.0f));
        this.Ó.HorizonCode_Horizon_È(new FireEmitter(200, 300, 60.0f));
        this.Ó.HorizonCode_Horizon_È(new FireEmitter(600, 300, 30.0f));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        for (int i = 0; i < 100; ++i) {
            g.Â(1.0f, 1.0f);
            this.Ó.áŒŠÆ();
        }
        g.Ø();
        g.HorizonCode_Horizon_È("Press space to toggle blending mode", 200.0f, 500.0f);
        g.HorizonCode_Horizon_È("Particle Count: " + this.Ó.áˆºÑ¢Õ() * 100, 200.0f, 520.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.Ó.Ý(delta);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 57) {
            this.à = ((1 == this.à) ? 2 : 1);
            this.Ó.HorizonCode_Horizon_È(this.à);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new ParticleTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
