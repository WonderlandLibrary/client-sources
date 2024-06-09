package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.ConfigurableEmitter;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ParticleIO;
import HORIZON-6-0-SKIDPROTECTION.ParticleSystem;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class PedigreeTest extends BasicGame
{
    private Image Ó;
    private GameContainer à;
    private ParticleSystem Ø;
    private ParticleSystem áŒŠÆ;
    private float áˆºÑ¢Õ;
    private float ÂµÈ;
    
    public PedigreeTest() {
        super("Pedigree Test");
        this.ÂµÈ = 900.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.à = container;
        try {
            this.áŒŠÆ = ParticleIO.HorizonCode_Horizon_È("testdata/system.xml");
            this.Ø = ParticleIO.HorizonCode_Horizon_È("testdata/smoketrail.xml");
        }
        catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
        this.Ó = new Image("testdata/rocket.png");
        this.Ó();
    }
    
    private void Ó() {
        this.ÂµÈ = 700.0f;
        this.áˆºÑ¢Õ = (float)(Math.random() * 600.0 + 100.0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        ((ConfigurableEmitter)this.Ø.Â(0)).HorizonCode_Horizon_È(this.áˆºÑ¢Õ + 14.0f, this.ÂµÈ + 35.0f);
        this.Ø.áŒŠÆ();
        this.Ó.HorizonCode_Horizon_È((int)this.áˆºÑ¢Õ, (int)this.ÂµÈ);
        g.Â(400.0f, 300.0f);
        this.áŒŠÆ.áŒŠÆ();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.áŒŠÆ.Ý(delta);
        this.Ø.Ý(delta);
        this.ÂµÈ -= delta * 0.25f;
        if (this.ÂµÈ < -100.0f) {
            this.Ó();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
        super.HorizonCode_Horizon_È(button, x, y);
        for (int i = 0; i < this.áŒŠÆ.Âµá€(); ++i) {
            ((ConfigurableEmitter)this.áŒŠÆ.Â(i)).HorizonCode_Horizon_È(x - 400, y - 300, true);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new PedigreeTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            this.à.áŠ();
        }
    }
}
