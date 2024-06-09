package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Log;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.Color;
import HORIZON-6-0-SKIDPROTECTION.GUIContext;
import HORIZON-6-0-SKIDPROTECTION.AbstractComponent;
import HORIZON-6-0-SKIDPROTECTION.AngelCodeFont;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Font;
import HORIZON-6-0-SKIDPROTECTION.TextField;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.MouseOverArea;
import HORIZON-6-0-SKIDPROTECTION.Image;
import HORIZON-6-0-SKIDPROTECTION.ComponentListener;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class GUITest extends BasicGame implements ComponentListener
{
    private Image Ó;
    private MouseOverArea[] à;
    private GameContainer Ø;
    private String áŒŠÆ;
    private TextField áˆºÑ¢Õ;
    private TextField ÂµÈ;
    private Image á;
    private Font ˆÏ­;
    private AppGameContainer £á;
    
    public GUITest() {
        super("GUI Test");
        this.à = new MouseOverArea[4];
        this.áŒŠÆ = "Demo Menu System with stock images";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        if (container instanceof AppGameContainer) {
            (this.£á = (AppGameContainer)container).Â("testdata/icon.tga");
        }
        this.ˆÏ­ = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.áˆºÑ¢Õ = new TextField(container, this.ˆÏ­, 150, 20, 500, 35, new ComponentListener() {
            @Override
            public void HorizonCode_Horizon_È(final AbstractComponent source) {
                GUITest.HorizonCode_Horizon_È(GUITest.this, "Entered1: " + GUITest.this.áˆºÑ¢Õ.á());
                GUITest.this.ÂµÈ.HorizonCode_Horizon_È(true);
            }
        });
        (this.ÂµÈ = new TextField(container, this.ˆÏ­, 150, 70, 500, 35, new ComponentListener() {
            @Override
            public void HorizonCode_Horizon_È(final AbstractComponent source) {
                GUITest.HorizonCode_Horizon_È(GUITest.this, "Entered2: " + GUITest.this.ÂµÈ.á());
                GUITest.this.áˆºÑ¢Õ.HorizonCode_Horizon_È(true);
            }
        })).Â(Color.Âµá€);
        this.Ø = container;
        this.Ó = new Image("testdata/logo.tga");
        this.á = new Image("testdata/dungeontiles.gif");
        container.HorizonCode_Horizon_È("testdata/cursor.tga", 0, 0);
        for (int i = 0; i < 4; ++i) {
            (this.à[i] = new MouseOverArea(container, this.Ó, 300, 100 + i * 100, 200, 90, this)).HorizonCode_Horizon_È(new Color(1.0f, 1.0f, 1.0f, 0.8f));
            this.à[i].Â(new Color(1.0f, 1.0f, 1.0f, 0.9f));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.á.HorizonCode_Horizon_È(0.0f, 0.0f, 800.0f, 500.0f);
        for (int i = 0; i < 4; ++i) {
            this.à[i].HorizonCode_Horizon_È(container, g);
        }
        this.áˆºÑ¢Õ.HorizonCode_Horizon_È(container, g);
        this.ÂµÈ.HorizonCode_Horizon_È(container, g);
        g.HorizonCode_Horizon_È(this.ˆÏ­);
        g.HorizonCode_Horizon_È(this.áŒŠÆ, 200.0f, 550.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 60) {
            this.£á.ˆÏ­();
        }
        if (key == 59 && this.£á != null) {
            try {
                this.£á.HorizonCode_Horizon_È(640, 480, false);
            }
            catch (SlickException e) {
                Log.HorizonCode_Horizon_È(e);
            }
        }
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new GUITest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final AbstractComponent source) {
        System.out.println("ACTIVL : " + source);
        for (int i = 0; i < 4; ++i) {
            if (source == this.à[i]) {
                this.áŒŠÆ = "Option " + (i + 1) + " pressed!";
            }
        }
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final GUITest guiTest, final String áœšæ) {
        guiTest.áŒŠÆ = áœšæ;
    }
}
