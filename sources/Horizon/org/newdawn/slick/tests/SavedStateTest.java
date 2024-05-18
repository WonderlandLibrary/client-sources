package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.AbstractComponent;
import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GUIContext;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.SavedState;
import HORIZON-6-0-SKIDPROTECTION.TextField;
import HORIZON-6-0-SKIDPROTECTION.ComponentListener;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class SavedStateTest extends BasicGame implements ComponentListener
{
    private TextField Ó;
    private TextField à;
    private String Ø;
    private int áŒŠÆ;
    private SavedState áˆºÑ¢Õ;
    private String ÂµÈ;
    private static AppGameContainer á;
    
    public SavedStateTest() {
        super("Saved State Test");
        this.Ø = "none";
        this.áŒŠÆ = 0;
        this.ÂµÈ = "Enter a name and age to store";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.áˆºÑ¢Õ = new SavedState("testdata");
        this.Ø = this.áˆºÑ¢Õ.HorizonCode_Horizon_È("name", "DefaultName");
        this.áŒŠÆ = (int)this.áˆºÑ¢Õ.HorizonCode_Horizon_È("age", 64.0);
        this.Ó = new TextField(container, container.Ê(), 100, 100, 300, 20, this);
        this.à = new TextField(container, container.Ê(), 100, 150, 201, 20, this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        this.Ó.HorizonCode_Horizon_È(container, g);
        this.à.HorizonCode_Horizon_È(container, g);
        container.Ê().HorizonCode_Horizon_È(100.0f, 300.0f, "Stored Name: " + this.Ø);
        container.Ê().HorizonCode_Horizon_È(100.0f, 350.0f, "Stored Age: " + this.áŒŠÆ);
        container.Ê().HorizonCode_Horizon_È(200.0f, 500.0f, this.ÂµÈ);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
    }
    
    public static void main(final String[] argv) {
        try {
            (SavedStateTest.á = new AppGameContainer(new SavedStateTest())).HorizonCode_Horizon_È(800, 600, false);
            SavedStateTest.á.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final AbstractComponent source) {
        if (source == this.Ó) {
            this.Ø = this.Ó.á();
            this.áˆºÑ¢Õ.Â("name", this.Ø);
        }
        if (source == this.à) {
            try {
                this.áŒŠÆ = Integer.parseInt(this.à.á());
                this.áˆºÑ¢Õ.Â("age", this.áŒŠÆ);
            }
            catch (NumberFormatException ex) {}
        }
        try {
            this.áˆºÑ¢Õ.HorizonCode_Horizon_È();
        }
        catch (Exception e) {
            this.ÂµÈ = String.valueOf(System.currentTimeMillis()) + " : Failed to save state";
        }
    }
}
