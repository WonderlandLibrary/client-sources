package ez.h.ui.flatclickgui;

import ez.h.ui.clickgui.*;
import ez.h.ui.clickgui.options.*;

public class FlatElement implements IEventListener
{
    public float width;
    public float y;
    public Option displayOption;
    public boolean isOpen;
    public float height;
    public float x;
    
    @Override
    public void keyPressed(final char c, final int n) {
    }
    
    public static boolean isHover(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        return n >= n3 && n <= n3 + n5 && n2 >= n4 && n2 <= n4 + n6;
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
    
    @Override
    public void mouseRealesed(final int n, final int n2, final int n3) {
    }
}
