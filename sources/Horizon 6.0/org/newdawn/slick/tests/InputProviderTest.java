package org.newdawn.slick.tests;

import HORIZON-6-0-SKIDPROTECTION.Game;
import HORIZON-6-0-SKIDPROTECTION.AppGameContainer;
import HORIZON-6-0-SKIDPROTECTION.Graphics;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.ControllerButtonControl;
import HORIZON-6-0-SKIDPROTECTION.MouseButtonControl;
import HORIZON-6-0-SKIDPROTECTION.ControllerDirectionControl;
import HORIZON-6-0-SKIDPROTECTION.Control;
import HORIZON-6-0-SKIDPROTECTION.KeyControl;
import HORIZON-6-0-SKIDPROTECTION.GameContainer;
import HORIZON-6-0-SKIDPROTECTION.BasicCommand;
import HORIZON-6-0-SKIDPROTECTION.InputProvider;
import HORIZON-6-0-SKIDPROTECTION.Command;
import HORIZON-6-0-SKIDPROTECTION.InputProviderListener;
import HORIZON-6-0-SKIDPROTECTION.BasicGame;

public class InputProviderTest extends BasicGame implements InputProviderListener
{
    private Command Ó;
    private Command à;
    private Command Ø;
    private InputProvider áŒŠÆ;
    private String áˆºÑ¢Õ;
    
    public InputProviderTest() {
        super("InputProvider Test");
        this.Ó = new BasicCommand("attack");
        this.à = new BasicCommand("jump");
        this.Ø = new BasicCommand("run");
        this.áˆºÑ¢Õ = "";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        (this.áŒŠÆ = new InputProvider(container.á€())).HorizonCode_Horizon_È(this);
        this.áŒŠÆ.HorizonCode_Horizon_È(new KeyControl(203), this.Ø);
        this.áŒŠÆ.HorizonCode_Horizon_È(new KeyControl(30), this.Ø);
        this.áŒŠÆ.HorizonCode_Horizon_È(new ControllerDirectionControl(0, ControllerDirectionControl.Ó), this.Ø);
        this.áŒŠÆ.HorizonCode_Horizon_È(new KeyControl(200), this.à);
        this.áŒŠÆ.HorizonCode_Horizon_È(new KeyControl(17), this.à);
        this.áŒŠÆ.HorizonCode_Horizon_È(new ControllerDirectionControl(0, ControllerDirectionControl.à), this.à);
        this.áŒŠÆ.HorizonCode_Horizon_È(new KeyControl(57), this.Ó);
        this.áŒŠÆ.HorizonCode_Horizon_È(new MouseButtonControl(0), this.Ó);
        this.áŒŠÆ.HorizonCode_Horizon_È(new ControllerButtonControl(0, 1), this.Ó);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) {
        g.HorizonCode_Horizon_È("Press A, W, Left, Up, space, mouse button 1,and gamepad controls", 10.0f, 50.0f);
        g.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, 100.0f, 150.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final int delta) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Command command) {
        this.áˆºÑ¢Õ = "Pressed: " + command;
    }
    
    @Override
    public void Â(final Command command) {
        this.áˆºÑ¢Õ = "Released: " + command;
    }
    
    public static void main(final String[] argv) {
        try {
            final AppGameContainer container = new AppGameContainer(new InputProviderTest());
            container.HorizonCode_Horizon_È(800, 600, false);
            container.Ø­áŒŠá();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
