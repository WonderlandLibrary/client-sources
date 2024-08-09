package ez.h.ui.clickgui;

public interface IEventListener
{
    void render(final int p0, final int p1, final float p2);
    
    void keyPressed(final char p0, final int p1);
    
    void mouseClicked(final int p0, final int p1, final int p2);
    
    void mouseRealesed(final int p0, final int p1, final int p2);
}
