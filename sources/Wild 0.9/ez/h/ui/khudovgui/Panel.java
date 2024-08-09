package ez.h.ui.khudovgui;

import ez.h.utils.*;

public class Panel
{
    public float height;
    public float y;
    public float x;
    public float width;
    
    public static void render(final float n, final float n2, final float n3) {
        RenderUtils.drawRoundedRect(n, n2, 100.0, 200.0, 5.0, Integer.MIN_VALUE, true, false, true, false);
    }
}
