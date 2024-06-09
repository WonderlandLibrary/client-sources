package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.AudioLoader;
import HORIZON-6-0-SKIDPROTECTION.ResourceLoader;
import HORIZON-6-0-SKIDPROTECTION.SoundStore;
import HORIZON-6-0-SKIDPROTECTION.Audio;
import HORIZON-6-0-SKIDPROTECTION.Music;
import HORIZON-6-0-SKIDPROTECTION.Sound;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class SoundTest extends BasicGame
{
    private GameContainer Ó;
    private Sound à;
    private Sound Ø;
    private Sound áŒŠÆ;
    private Music áˆºÑ¢Õ;
    private Music ÂµÈ;
    private Music á;
    private Audio ˆÏ­;
    private int £á;
    private int[] Å;
    
    public SoundTest() {
        super("Sound And Music Test");
        this.£á = 10;
        this.Å = new int[3];
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        SoundStore.Å().Â(32);
        this.Ó = container;
        this.à = new Sound("testdata/restart.ogg");
        this.Ø = new Sound("testdata/cbrown01.wav");
        try {
            this.ˆÏ­ = AudioLoader.HorizonCode_Horizon_È("WAV", ResourceLoader.HorizonCode_Horizon_È("testdata/engine.wav"));
        }
        catch (IOException e) {
            throw new SlickException("Failed to load engine", e);
        }
        final Music music = new Music("testdata/SMB-X.XM");
        this.ÂµÈ = music;
        this.áˆºÑ¢Õ = music;
        this.á = new Music("testdata/kirby.ogg", true);
        this.áŒŠÆ = new Sound("testdata/burp.aif");
        this.áˆºÑ¢Õ.Â();
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
        g.HorizonCode_Horizon_È("Press + or - to change global volume of music", 100.0f, 270.0f);
        g.HorizonCode_Horizon_È("Press Y or X to change individual volume of music", 100.0f, 300.0f);
        g.HorizonCode_Horizon_È("Press N or M to change global volume of sound fx", 100.0f, 330.0f);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È("Global Sound Volume Level: " + container.É(), 150.0f, 390.0f);
        g.HorizonCode_Horizon_È("Global Music Volume Level: " + container.ÇŽÕ(), 150.0f, 420.0f);
        g.HorizonCode_Horizon_È("Current Music Volume Level: " + this.áˆºÑ¢Õ.à(), 150.0f, 450.0f);
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
            this.à.HorizonCode_Horizon_È();
        }
        if (key == 48) {
            this.áŒŠÆ.HorizonCode_Horizon_È();
        }
        if (key == 30) {
            this.à.HorizonCode_Horizon_È(-1.0f, 0.0f, 0.0f);
        }
        if (key == 38) {
            this.à.HorizonCode_Horizon_È(1.0f, 0.0f, 0.0f);
        }
        if (key == 28) {
            this.Ø.HorizonCode_Horizon_È(1.0f, 1.0f);
        }
        if (key == 25) {
            if (this.áˆºÑ¢Õ.Ó()) {
                this.áˆºÑ¢Õ.Ý();
            }
            else {
                this.áˆºÑ¢Õ.Âµá€();
            }
        }
        if (key == 46) {
            this.áˆºÑ¢Õ.Ø­áŒŠá();
            if (this.áˆºÑ¢Õ == this.ÂµÈ) {
                this.áˆºÑ¢Õ = this.á;
            }
            else {
                this.áˆºÑ¢Õ = this.ÂµÈ;
            }
            this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
        }
        for (int i = 0; i < 3; ++i) {
            if (key == 2 + i) {
                if (this.Å[i] != 0) {
                    System.out.println("Stop " + i);
                    SoundStore.Å().Ó(this.Å[i]);
                    this.Å[i] = 0;
                }
                else {
                    System.out.println("Start " + i);
                    this.Å[i] = this.ˆÏ­.HorizonCode_Horizon_È(1.0f, 1.0f, true);
                }
            }
        }
        if (c == '+') {
            ++this.£á;
            this.Ó();
        }
        if (c == '-') {
            --this.£á;
            this.Ó();
        }
        if (key == 21) {
            int vol = (int)(this.áˆºÑ¢Õ.à() * 10.0f);
            if (--vol < 0) {
                vol = 0;
            }
            this.áˆºÑ¢Õ.HorizonCode_Horizon_È(vol / 10.0f);
        }
        if (key == 45) {
            int vol = (int)(this.áˆºÑ¢Õ.à() * 10.0f);
            if (++vol > 10) {
                vol = 10;
            }
            this.áˆºÑ¢Õ.HorizonCode_Horizon_È(vol / 10.0f);
        }
        if (key == 49) {
            int vol = (int)(this.Ó.É() * 10.0f);
            if (--vol < 0) {
                vol = 0;
            }
            this.Ó.HorizonCode_Horizon_È(vol / 10.0f);
        }
        if (key == 50) {
            int vol = (int)(this.Ó.É() * 10.0f);
            if (++vol > 10) {
                vol = 10;
            }
            this.Ó.HorizonCode_Horizon_È(vol / 10.0f);
        }
    }
    
    private void Ó() {
        if (this.£á > 10) {
            this.£á = 10;
        }
        else if (this.£á < 0) {
            this.£á = 0;
        }
        this.Ó.Â(this.£á / 10.0f);
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new SoundTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
