package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class CombinedTransition implements Transition
{
    private ArrayList HorizonCode_Horizon_È;
    
    public CombinedTransition() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final Transition t) {
        this.HorizonCode_Horizon_È.add(t);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            if (!this.HorizonCode_Horizon_È.get(i).HorizonCode_Horizon_È()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        for (int i = this.HorizonCode_Horizon_È.size() - 1; i >= 0; --i) {
            this.HorizonCode_Horizon_È.get(i).HorizonCode_Horizon_È(game, container, g);
        }
    }
    
    @Override
    public void Â(final StateBasedGame game, final GameContainer container, final Graphics g) throws SlickException {
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            this.HorizonCode_Horizon_È.get(i).HorizonCode_Horizon_È(game, container, g);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final StateBasedGame game, final GameContainer container, final int delta) throws SlickException {
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            final Transition t = this.HorizonCode_Horizon_È.get(i);
            if (!t.HorizonCode_Horizon_È()) {
                t.HorizonCode_Horizon_È(game, container, delta);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameState firstState, final GameState secondState) {
        for (int i = this.HorizonCode_Horizon_È.size() - 1; i >= 0; --i) {
            this.HorizonCode_Horizon_È.get(i).HorizonCode_Horizon_È(firstState, secondState);
        }
    }
}
