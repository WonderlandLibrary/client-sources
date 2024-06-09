package HORIZON-6-0-SKIDPROTECTION;

public class TestState1 extends BasicGameState
{
    public static final int HorizonCode_Horizon_È = 1;
    private Font Â;
    private StateBasedGame Ý;
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 1;
    }
    
    @Override
    public void Ý(final GameContainer container, final StateBasedGame game) throws SlickException {
        this.Ý = game;
        this.Â = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final Graphics g) {
        g.HorizonCode_Horizon_È(this.Â);
        g.Â(Color.Ý);
        g.HorizonCode_Horizon_È("State Based Game Test", 100.0f, 100.0f);
        g.HorizonCode_Horizon_È("Numbers 1-3 will switch between states.", 150.0f, 300.0f);
        g.Â(Color.Âµá€);
        g.HorizonCode_Horizon_È("This is State 1", 200.0f, 50.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final int delta) {
    }
    
    @Override
    public void Â(final int key, final char c) {
        if (key == 3) {
            final GameState target = this.Ý.áˆºÑ¢Õ(2);
            final long start = System.currentTimeMillis();
            final CrossStateTransition t = new CrossStateTransition(target) {
                @Override
                public boolean HorizonCode_Horizon_È() {
                    return System.currentTimeMillis() - start > 2000L;
                }
                
                @Override
                public void HorizonCode_Horizon_È(final GameState firstState, final GameState secondState) {
                }
            };
            this.Ý.HorizonCode_Horizon_È(2, t, new EmptyTransition());
        }
        if (key == 4) {
            this.Ý.HorizonCode_Horizon_È(3, new FadeOutTransition(Color.Ø), new FadeInTransition(Color.Ø));
        }
    }
}
