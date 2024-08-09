package ez.h.ui.clickgui.element;

import ez.h.ui.clickgui.*;
import ez.h.ui.clickgui.options.*;

public class Element implements IEventListener
{
    public float x;
    public Panel panel;
    public float width;
    public Option displayOption;
    public float height;
    public boolean isOpen;
    public float y;
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
    }
    
    public boolean isHover(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return n >= n3 && n <= n3 + n5 && n2 >= n4 && n2 <= n4 + n6;
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
    }
    
    public Element(final Panel panel) {
        this.panel = panel;
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
}
