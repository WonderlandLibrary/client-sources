package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.Log;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Input;
import HORIZON-6-0-SKIDPROTECTION.Color;
import java.util.ArrayList;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class InputTest extends BasicGame
{
    private String Ó;
    private ArrayList à;
    private boolean Ø;
    private float áŒŠÆ;
    private float áˆºÑ¢Õ;
    private Color[] ÂµÈ;
    private int á;
    private Input ˆÏ­;
    private int £á;
    private AppGameContainer Å;
    private boolean £à;
    private boolean µà;
    private boolean ˆà;
    
    public InputTest() {
        super("Input Test");
        this.Ó = "Press any key, mouse button, or drag the mouse";
        this.à = new ArrayList();
        this.ÂµÈ = new Color[] { Color.Âµá€, Color.à, Color.Ó, Color.Ý, Color.Å, Color.áˆºÑ¢Õ };
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        if (container instanceof AppGameContainer) {
            this.Å = (AppGameContainer)container;
        }
        this.ˆÏ­ = container.á€();
        this.áŒŠÆ = 300.0f;
        this.áˆºÑ¢Õ = 300.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È("left shift down: " + this.µà, 100.0f, 240.0f);
        g.HorizonCode_Horizon_È("right shift down: " + this.ˆà, 100.0f, 260.0f);
        g.HorizonCode_Horizon_È("space down: " + this.£à, 100.0f, 280.0f);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È(this.Ó, 10.0f, 50.0f);
        g.HorizonCode_Horizon_È(new StringBuilder().append(container.á€().ˆÏ­()).toString(), 10.0f, 400.0f);
        g.HorizonCode_Horizon_È("Use the primary gamepad to control the blob, and hit a gamepad button to change the color", 10.0f, 90.0f);
        for (int i = 0; i < this.à.size(); ++i) {
            final HorizonCode_Horizon_È line = this.à.get(i);
            line.HorizonCode_Horizon_È(g);
        }
        g.Â(this.ÂµÈ[this.á]);
        g.Ó((int)this.áŒŠÆ, (int)this.áˆºÑ¢Õ, 50.0f, 50.0f);
        g.Â(Color.Ø­áŒŠá);
        g.Ø­áŒŠá(50.0f, 200 + this.£á, 40.0f, 40.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
        this.µà = container.á€().Ø(42);
        this.ˆà = container.á€().Ø(54);
        this.£à = container.á€().Ø(57);
        if (this.HorizonCode_Horizon_È[0]) {
            this.áŒŠÆ -= delta * 0.1f;
        }
        if (this.Â[0]) {
            this.áŒŠÆ += delta * 0.1f;
        }
        if (this.Ý[0]) {
            this.áˆºÑ¢Õ -= delta * 0.1f;
        }
        if (this.Ø­áŒŠá[0]) {
            this.áˆºÑ¢Õ += delta * 0.1f;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (key == 1) {
            System.exit(0);
        }
        if (key == 59 && this.Å != null) {
            try {
                this.Å.HorizonCode_Horizon_È(600, 600, false);
                this.Å.Ý();
            }
            catch (Exception e) {
                Log.HorizonCode_Horizon_È(e);
            }
        }
    }
    
    @Override
    public void Â(final int key, final char c) {
        this.Ó = "You pressed key code " + key + " (character = " + c + ")";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
        if (button == 0) {
            this.Ø = true;
        }
        this.Ó = "Mouse pressed " + button + " " + x + "," + y;
    }
    
    @Override
    public void Â(final int button, final int x, final int y) {
        if (button == 0) {
            this.Ø = false;
        }
        this.Ó = "Mouse released " + button + " " + x + "," + y;
    }
    
    @Override
    public void Ý(final int button, final int x, final int y, final int clickCount) {
        System.out.println("CLICKED:" + x + "," + y + " " + clickCount);
    }
    
    @Override
    public void áŒŠÆ(final int change) {
        this.Ó = "Mouse wheel moved: " + change;
        if (change < 0) {
            this.£á -= 10;
        }
        if (change > 0) {
            this.£á += 10;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int oldx, final int oldy, final int newx, final int newy) {
        if (this.Ø) {
            this.à.add(new HorizonCode_Horizon_È(oldx, oldy, newx, newy));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller, final int button) {
        super.HorizonCode_Horizon_È(controller, button);
        ++this.á;
        this.á %= this.ÂµÈ.length;
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new InputTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    private class HorizonCode_Horizon_È
    {
        private int Â;
        private int Ý;
        private int Ø­áŒŠá;
        private int Âµá€;
        
        public HorizonCode_Horizon_È(final int oldx, final int oldy, final int newx, final int newy) {
            this.Â = oldx;
            this.Ý = oldy;
            this.Ø­áŒŠá = newx;
            this.Âµá€ = newy;
        }
        
        public void HorizonCode_Horizon_È(final Graphics g) {
            g.HorizonCode_Horizon_È(this.Â, this.Ý, this.Ø­áŒŠá, (float)this.Âµá€);
        }
    }
}
