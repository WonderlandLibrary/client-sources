package HORIZON-6-0-SKIDPROTECTION;

public class TestState3 extends BasicGameState
{
    public static final int HorizonCode_Horizon_È = 3;
    private Font Â;
    private String[] Ý;
    private int Ø­áŒŠá;
    private StateBasedGame Âµá€;
    
    public TestState3() {
        this.Ý = new String[] { "Start Game", "Credits", "Highscores", "Instructions", "Exit" };
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public void Ý(final GameContainer container, final StateBasedGame game) throws SlickException {
        this.Â = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.Âµá€ = game;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final Graphics g) {
        g.HorizonCode_Horizon_È(this.Â);
        g.Â(Color.Ó);
        g.HorizonCode_Horizon_È("This is State 3", 200.0f, 50.0f);
        g.Â(Color.Ý);
        for (int i = 0; i < this.Ý.length; ++i) {
            g.HorizonCode_Horizon_È(this.Ý[i], 400 - this.Â.Ý(this.Ý[i]) / 2, 200 + i * 50);
            if (this.Ø­áŒŠá == i) {
                g.Â(200.0f, 190 + i * 50, 400.0f, 50.0f);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GameContainer container, final StateBasedGame game, final int delta) {
    }
    
    @Override
    public void Â(final int key, final char c) {
        if (key == 208) {
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá >= this.Ý.length) {
                this.Ø­áŒŠá = 0;
            }
        }
        if (key == 200) {
            --this.Ø­áŒŠá;
            if (this.Ø­áŒŠá < 0) {
                this.Ø­áŒŠá = this.Ý.length - 1;
            }
        }
        if (key == 2) {
            this.Âµá€.HorizonCode_Horizon_È(1, new FadeOutTransition(Color.Ø), new FadeInTransition(Color.Ø));
        }
        if (key == 3) {
            this.Âµá€.HorizonCode_Horizon_È(2, new FadeOutTransition(Color.Ø), new FadeInTransition(Color.Ø));
        }
    }
}
