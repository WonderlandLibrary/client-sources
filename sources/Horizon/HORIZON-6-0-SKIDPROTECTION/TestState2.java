package HORIZON-6-0-SKIDPROTECTION;

public class TestState2 extends BasicGameState
{
    public static final int HorizonCode_Horizon_È = 2;
    private Font Â;
    private Image Ý;
    private float Ø­áŒŠá;
    private StateBasedGame Âµá€;
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public void Ý(final GameContainer container, final StateBasedGame game) throws SlickException {
        this.Âµá€ = game;
        this.Â = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.Ý = new Image("testdata/logo.tga");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final Graphics g) {
        g.HorizonCode_Horizon_È(this.Â);
        g.Â(Color.à);
        g.HorizonCode_Horizon_È("This is State 2", 200.0f, 50.0f);
        g.HorizonCode_Horizon_È(400.0f, 300.0f, this.Ø­áŒŠá);
        g.HorizonCode_Horizon_È(this.Ý, 400 - this.Ý.ŒÏ() / 2, (float)(300 - this.Ý.Çªà¢() / 2));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final int delta) {
        this.Ø­áŒŠá += delta * 0.1f;
    }
    
    @Override
    public void Â(final int key, final char c) {
        if (key == 2) {
            this.Âµá€.HorizonCode_Horizon_È(1, new FadeOutTransition(Color.Ø), new FadeInTransition(Color.Ø));
        }
        if (key == 4) {
            this.Âµá€.HorizonCode_Horizon_È(3, new FadeOutTransition(Color.Ø), new FadeInTransition(Color.Ø));
        }
    }
}
