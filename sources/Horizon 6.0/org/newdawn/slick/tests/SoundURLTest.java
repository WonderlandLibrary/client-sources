package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ResourceLoader;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.Music;
import HORIZON-6-0-SKIDPROTECTION.Sound;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class SoundURLTest extends BasicGame
{
    private Sound Ó;
    private Sound à;
    private Sound Ø;
    private Music áŒŠÆ;
    private Music áˆºÑ¢Õ;
    private Music ÂµÈ;
    private Sound á;
    private int ˆÏ­;
    
    public SoundURLTest() {
        super("Sound URL Test");
        this.ˆÏ­ = 1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Ó = new Sound(ResourceLoader.Ý("testdata/restart.ogg"));
        this.à = new Sound(ResourceLoader.Ý("testdata/cbrown01.wav"));
        this.á = new Sound(ResourceLoader.Ý("testdata/engine.wav"));
        final Music music = new Music(ResourceLoader.Ý("testdata/restart.ogg"), false);
        this.áˆºÑ¢Õ = music;
        this.áŒŠÆ = music;
        this.ÂµÈ = new Music(ResourceLoader.Ý("testdata/kirby.ogg"), false);
        this.Ø = new Sound(ResourceLoader.Ý("testdata/burp.aif"));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("The OGG loop is now streaming from the file, woot.", 100.0f, 60.0f);
        g.HorizonCode_Horizon_È("Press space for sound effect (OGG)", 100.0f, 100.0f);
        g.HorizonCode_Horizon_È("Press P to pause/resume music (XM)", 100.0f, 130.0f);
        g.HorizonCode_Horizon_È("Press E to pause/resume engine sound (WAV)", 100.0f, 190.0f);
        g.HorizonCode_Horizon_È("Press enter for charlie (WAV)", 100.0f, 160.0f);
        g.HorizonCode_Horizon_È("Press C to change music", 100.0f, 210.0f);
        g.HorizonCode_Horizon_È("Press B to burp (AIF)", 100.0f, 240.0f);
        g.HorizonCode_Horizon_È("Press + or - to change volume of music", 100.0f, 270.0f);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È("Music Volume Level: " + this.ˆÏ­ / 10.0f, 150.0f, 300.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 57) {
            this.Ó.HorizonCode_Horizon_È();
        }
        if (key == 48) {
            this.Ø.HorizonCode_Horizon_È();
        }
        if (key == 30) {
            this.Ó.HorizonCode_Horizon_È(-1.0f, 0.0f, 0.0f);
        }
        if (key == 38) {
            this.Ó.HorizonCode_Horizon_È(1.0f, 0.0f, 0.0f);
        }
        if (key == 28) {
            this.à.HorizonCode_Horizon_È(1.0f, 1.0f);
        }
        if (key == 25) {
            if (this.áŒŠÆ.Ó()) {
                this.áŒŠÆ.Ý();
            }
            else {
                this.áŒŠÆ.Âµá€();
            }
        }
        if (key == 46) {
            this.áŒŠÆ.Ø­áŒŠá();
            if (this.áŒŠÆ == this.áˆºÑ¢Õ) {
                this.áŒŠÆ = this.ÂµÈ;
            }
            else {
                this.áŒŠÆ = this.áˆºÑ¢Õ;
            }
            this.áŒŠÆ.HorizonCode_Horizon_È();
        }
        if (key == 18) {
            if (this.á.Ý()) {
                this.á.Ø­áŒŠá();
            }
            else {
                this.á.Â();
            }
        }
        if (c == '+') {
            ++this.ˆÏ­;
            this.Ó();
        }
        if (c == '-') {
            --this.ˆÏ­;
            this.Ó();
        }
    }
    
    private void Ó() {
        if (this.ˆÏ­ > 10) {
            this.ˆÏ­ = 10;
        }
        else if (this.ˆÏ­ < 0) {
            this.ˆÏ­ = 0;
        }
        this.áŒŠÆ.HorizonCode_Horizon_È(this.ˆÏ­ / 10.0f);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new SoundURLTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
