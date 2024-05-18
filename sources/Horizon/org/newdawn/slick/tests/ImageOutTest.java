package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.ImageOut;
import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ParticleIO;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.ParticleSystem;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class ImageOutTest extends BasicGame
{
    private GameContainer Ó;
    private ParticleSystem à;
    private Graphics Ø;
    private Image áŒŠÆ;
    private String áˆºÑ¢Õ;
    
    public ImageOutTest() {
        super("Image Out Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = container;
        try {
            this.à = ParticleIO.HorizonCode_Horizon_È("testdata/system.xml");
        }
        catch (IOException e) {
            throw new SlickException("Failed to load particle systems", e);
        }
        this.áŒŠÆ = new Image(400, 300);
        final String[] formats = ImageOut.HorizonCode_Horizon_È();
        this.áˆºÑ¢Õ = "Formats supported: ";
        for (int i = 0; i < formats.length; ++i) {
            this.áˆºÑ¢Õ = String.valueOf(this.áˆºÑ¢Õ) + formats[i];
            if (i < formats.length - 1) {
                this.áˆºÑ¢Õ = String.valueOf(this.áˆºÑ¢Õ) + ",";
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È("T - TGA Snapshot", 10.0f, 50.0f);
        g.HorizonCode_Horizon_È("J - JPG Snapshot", 10.0f, 70.0f);
        g.HorizonCode_Horizon_È("P - PNG Snapshot", 10.0f, 90.0f);
        g.HorizonCode_Horizon_È(Graphics.Ó);
        g.HorizonCode_Horizon_È(this.áŒŠÆ, 200.0f, 300.0f);
        g.HorizonCode_Horizon_È(Graphics.Â);
        g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, 10.0f, 400.0f);
        g.Â(200.0f, 0.0f, 400.0f, 300.0f);
        g.Â(400.0f, 250.0f);
        this.à.áŒŠÆ();
        this.Ø = g;
    }
    
    private void HorizonCode_Horizon_È(final String fname) throws SlickException {
        this.Ø.HorizonCode_Horizon_È(this.áŒŠÆ, 200, 0);
        ImageOut.HorizonCode_Horizon_È(this.áŒŠÆ, fname);
        this.áˆºÑ¢Õ = "Written " + fname;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.à.Ý(delta);
        if (container.á€().Âµá€(25)) {
            this.HorizonCode_Horizon_È("ImageOutTest.png");
        }
        if (container.á€().Âµá€(36)) {
            this.HorizonCode_Horizon_È("ImageOutTest.jpg");
        }
        if (container.á€().Âµá€(20)) {
            this.HorizonCode_Horizon_È("ImageOutTest.tga");
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new ImageOutTest());
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
            this.Ó.áŠ();
        }
    }
}
