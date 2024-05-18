package HORIZON-6-0-SKIDPROTECTION;

public abstract class CrossStateTransition implements Transition
{
    private GameState HorizonCode_Horizon_È;
    
    public CrossStateTransition(final GameState secondState) {
        this.HorizonCode_Horizon_È = secondState;
    }
    
    @Override
    public abstract boolean HorizonCode_Horizon_È();
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        this.Ø­áŒŠá(game, container, g);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(container, game, g);
        this.Âµá€(game, container, g);
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        this.Ý(game, container, g);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
    }
    
    public void Ý(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
    }
    
    public void Ø­áŒŠá(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
    }
    
    public void Âµá€(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
    }
}
