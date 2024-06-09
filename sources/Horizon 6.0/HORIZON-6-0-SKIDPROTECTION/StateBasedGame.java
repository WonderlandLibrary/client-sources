package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.HashMap;

public abstract class StateBasedGame implements Game, InputListener
{
    private HashMap HorizonCode_Horizon_È;
    private GameState Â;
    private GameState Ý;
    private GameContainer Ø­áŒŠá;
    private String Âµá€;
    private Transition Ó;
    private Transition à;
    
    public StateBasedGame(final String name) {
        this.HorizonCode_Horizon_È = new HashMap();
        this.Âµá€ = name;
        this.Â = new BasicGameState() {
            @Override
            public int HorizonCode_Horizon_È() {
                return -1;
            }
            
            @Override
            public void Ý(final GameContainer container, final StateBasedGame game) throws SlickException {
            }
            
            public void HorizonCode_Horizon_È(final StateBasedGame game, final Graphics g) throws SlickException {
            }
            
            @Override
            public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final int delta) throws SlickException {
            }
            
            @Override
            public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final Graphics g) throws SlickException {
            }
        };
    }
    
    @Override
    public void Âµá€() {
    }
    
    public int Ó() {
        return this.HorizonCode_Horizon_È.keySet().size();
    }
    
    public int à() {
        return this.Â.HorizonCode_Horizon_È();
    }
    
    public GameState Ø() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Input input) {
    }
    
    public void HorizonCode_Horizon_È(final GameState state) {
        this.HorizonCode_Horizon_È.put(new Integer(state.HorizonCode_Horizon_È()), state);
        if (this.Â.HorizonCode_Horizon_È() == -1) {
            this.Â = state;
        }
    }
    
    public GameState áˆºÑ¢Õ(final int id) {
        return this.HorizonCode_Horizon_È.get(new Integer(id));
    }
    
    public void ÂµÈ(final int id) {
        this.HorizonCode_Horizon_È(id, new EmptyTransition(), new EmptyTransition());
    }
    
    public void HorizonCode_Horizon_È(final int id, Transition leave, Transition enter) {
        if (leave == null) {
            leave = new EmptyTransition();
        }
        if (enter == null) {
            enter = new EmptyTransition();
        }
        this.à = leave;
        this.Ó = enter;
        this.Ý = this.áˆºÑ¢Õ(id);
        if (this.Ý == null) {
            throw new RuntimeException("No game state registered with the ID: " + id);
        }
        this.à.HorizonCode_Horizon_È(this.Â, this.Ý);
    }
    
    @Override
    public final void HorizonCode_Horizon_È(final GameContainer container) throws SlickException {
        this.Â(this.Ø­áŒŠá = container);
        for (final GameState state : this.HorizonCode_Horizon_È.values()) {
            state.Ý(container, this);
        }
        if (this.Â != null) {
            this.Â.HorizonCode_Horizon_È(container, this);
        }
    }
    
    public abstract void Â(final GameContainer p0) throws SlickException;
    
    @Override
    public final void HorizonCode_Horizon_È(final GameContainer container, final Graphics g) throws SlickException {
        this.Â(container, g);
        if (this.à != null) {
            this.à.Â(this, container, g);
        }
        else if (this.Ó != null) {
            this.Ó.Â(this, container, g);
        }
        this.Â.HorizonCode_Horizon_È(container, this, g);
        if (this.à != null) {
            this.à.HorizonCode_Horizon_È(this, container, g);
        }
        else if (this.Ó != null) {
            this.Ó.HorizonCode_Horizon_È(this, container, g);
        }
        this.Ý(container, g);
    }
    
    protected void Â(final GameContainer container, final Graphics g) throws SlickException {
    }
    
    protected void Ý(final GameContainer container, final Graphics g) throws SlickException {
    }
    
    @Override
    public final void HorizonCode_Horizon_È(final GameContainer container, final int delta) throws SlickException {
        this.Â(container, delta);
        if (this.à != null) {
            this.à.HorizonCode_Horizon_È(this, container, delta);
            if (!this.à.HorizonCode_Horizon_È()) {
                return;
            }
            this.Â.Â(container, this);
            final GameState prevState = this.Â;
            this.Â = this.Ý;
            this.Ý = null;
            this.à = null;
            this.Â.HorizonCode_Horizon_È(container, this);
            if (this.Ó != null) {
                this.Ó.HorizonCode_Horizon_È(this.Â, prevState);
            }
        }
        if (this.Ó != null) {
            this.Ó.HorizonCode_Horizon_È(this, container, delta);
            if (!this.Ó.HorizonCode_Horizon_È()) {
                return;
            }
            this.Ó = null;
        }
        this.Â.HorizonCode_Horizon_È(container, this, delta);
        this.Ý(container, delta);
    }
    
    protected void Â(final GameContainer container, final int delta) throws SlickException {
    }
    
    protected void Ý(final GameContainer container, final int delta) throws SlickException {
    }
    
    private boolean áˆºÑ¢Õ() {
        return this.à != null || this.Ó != null;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return true;
    }
    
    @Override
    public String Â() {
        return this.Âµá€;
    }
    
    public GameContainer áŒŠÆ() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller, final int button) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.HorizonCode_Horizon_È(controller, button);
    }
    
    @Override
    public void Â(final int controller, final int button) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Â(controller, button);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.HorizonCode_Horizon_È(controller);
    }
    
    @Override
    public void Â(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Â(controller);
    }
    
    @Override
    public void Ý(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Ý(controller);
    }
    
    @Override
    public void Ø­áŒŠá(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Ø­áŒŠá(controller);
    }
    
    @Override
    public void Âµá€(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Âµá€(controller);
    }
    
    @Override
    public void Ó(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Ó(controller);
    }
    
    @Override
    public void à(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.à(controller);
    }
    
    @Override
    public void Ø(final int controller) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Ø(controller);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.HorizonCode_Horizon_È(key, c);
    }
    
    @Override
    public void Â(final int key, final char c) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Â(key, c);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int oldx, final int oldy, final int newx, final int newy) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.HorizonCode_Horizon_È(oldx, oldy, newx, newy);
    }
    
    @Override
    public void Â(final int oldx, final int oldy, final int newx, final int newy) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Â(oldx, oldy, newx, newy);
    }
    
    @Override
    public void Ý(final int button, final int x, final int y, final int clickCount) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Ý(button, x, y, clickCount);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.HorizonCode_Horizon_È(button, x, y);
    }
    
    @Override
    public void Â(final int button, final int x, final int y) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.Â(button, x, y);
    }
    
    @Override
    public boolean Ý() {
        return !this.áˆºÑ¢Õ() && this.Â.Ý();
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    @Override
    public void áŒŠÆ(final int newValue) {
        if (this.áˆºÑ¢Õ()) {
            return;
        }
        this.Â.áŒŠÆ(newValue);
    }
}
