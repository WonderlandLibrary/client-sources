package HORIZON-6-0-SKIDPROTECTION;

public abstract class BasicGame implements Game, InputListener
{
    private static final int Ó = 20;
    private static final int à = 100;
    private String Ø;
    protected boolean[] HorizonCode_Horizon_È;
    protected boolean[] Â;
    protected boolean[] Ý;
    protected boolean[] Ø­áŒŠá;
    protected boolean[][] Âµá€;
    
    public BasicGame(final String title) {
        this.HorizonCode_Horizon_È = new boolean[20];
        this.Â = new boolean[20];
        this.Ý = new boolean[20];
        this.Ø­áŒŠá = new boolean[20];
        this.Âµá€ = new boolean[20][100];
        this.Ø = title;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Input input) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return true;
    }
    
    @Override
    public String Â() {
        return this.Ø;
    }
    
    @Override
    public abstract void HorizonCode_Horizon_È(final GameContainer p0) throws SlickException;
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
    }
    
    @Override
    public void Â(final int key, final char c) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int oldx, final int oldy, final int newx, final int newy) {
    }
    
    @Override
    public void Â(final int oldx, final int oldy, final int newx, final int newy) {
    }
    
    @Override
    public void Ý(final int button, final int x, final int y, final int clickCount) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int x, final int y) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller, final int button) {
        this.Âµá€[controller][button] = true;
    }
    
    @Override
    public void Â(final int controller, final int button) {
        this.Âµá€[controller][button] = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int controller) {
        this.Ø­áŒŠá[controller] = true;
    }
    
    @Override
    public void Â(final int controller) {
        this.Ø­áŒŠá[controller] = false;
    }
    
    @Override
    public void Ý(final int controller) {
        this.HorizonCode_Horizon_È[controller] = true;
    }
    
    @Override
    public void Ø­áŒŠá(final int controller) {
        this.HorizonCode_Horizon_È[controller] = false;
    }
    
    @Override
    public void Âµá€(final int controller) {
        this.Â[controller] = true;
    }
    
    @Override
    public void Ó(final int controller) {
        this.Â[controller] = false;
    }
    
    @Override
    public void à(final int controller) {
        this.Ý[controller] = true;
    }
    
    @Override
    public void Ø(final int controller) {
        this.Ý[controller] = false;
    }
    
    @Override
    public void Â(final int button, final int x, final int y) {
    }
    
    @Override
    public abstract void HorizonCode_Horizon_È(final GameContainer p0, final int p1) throws SlickException;
    
    @Override
    public void áŒŠÆ(final int change) {
    }
    
    @Override
    public boolean Ý() {
        return true;
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    @Override
    public void Âµá€() {
    }
}
