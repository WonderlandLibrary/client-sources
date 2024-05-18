package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import java.io.IOException;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.LoadingList;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.DeferredResource;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.Sound;
import HORIZON-6-0-SKIDPROTECTION.Music;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class DeferredLoadingTest extends BasicGame
{
    private Music Ó;
    private Sound à;
    private Image Ø;
    private Font áŒŠÆ;
    private DeferredResource áˆºÑ¢Õ;
    private boolean ÂµÈ;
    
    public DeferredLoadingTest() {
        super("Deferred Loading Test");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        LoadingList.HorizonCode_Horizon_È(true);
        new Sound("testdata/cbrown01.wav");
        new Sound("testdata/engine.wav");
        this.à = new Sound("testdata/restart.ogg");
        new Music("testdata/testloop.ogg");
        this.Ó = new Music("testdata/SMB-X.XM");
        new Image("testdata/cursor.png");
        new Image("testdata/cursor.tga");
        new Image("testdata/cursor.png");
        new Image("testdata/cursor.png");
        new Image("testdata/dungeontiles.gif");
        new Image("testdata/logo.gif");
        this.Ø = new Image("testdata/logo.tga");
        new Image("testdata/logo.png");
        new Image("testdata/rocket.png");
        new Image("testdata/testpack.png");
        this.áŒŠÆ = new AngelCodeFont("testdata/demo.fnt", "testdata/demo_00.tga");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        if (this.áˆºÑ¢Õ != null) {
            g.HorizonCode_Horizon_È("Loading: " + this.áˆºÑ¢Õ.Â(), 100.0f, 100.0f);
        }
        final int total = LoadingList.HorizonCode_Horizon_È().Ý();
        final int loaded = LoadingList.HorizonCode_Horizon_È().Ý() - LoadingList.HorizonCode_Horizon_È().Ø­áŒŠá();
        final float bar = loaded / total;
        g.Ø­áŒŠá(100.0f, 150.0f, loaded * 40, 20.0f);
        g.Â(100.0f, 150.0f, total * 40, 20.0f);
        if (this.ÂµÈ) {
            this.Ø.HorizonCode_Horizon_È(100.0f, 200.0f);
            this.áŒŠÆ.HorizonCode_Horizon_È(100.0f, 500.0f, "LOADING COMPLETE");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        if (this.áˆºÑ¢Õ != null) {
            try {
                this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
                try {
                    Thread.sleep(50L);
                }
                catch (Exception ex) {}
            }
            catch (IOException e) {
                throw new SlickException("Failed to load: " + this.áˆºÑ¢Õ.Â(), e);
            }
            this.áˆºÑ¢Õ = null;
        }
        if (LoadingList.HorizonCode_Horizon_È().Ø­áŒŠá() > 0) {
            this.áˆºÑ¢Õ = LoadingList.HorizonCode_Horizon_È().Âµá€();
        }
        else if (!this.ÂµÈ) {
            this.ÂµÈ = true;
            this.Ó.HorizonCode_Horizon_È();
            this.à.HorizonCode_Horizon_È();
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new DeferredLoadingTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
    }
}
