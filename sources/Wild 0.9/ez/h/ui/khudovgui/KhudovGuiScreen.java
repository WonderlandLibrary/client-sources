package ez.h.ui.khudovgui;

import java.io.*;

public class KhudovGuiScreen extends blk
{
    protected void a(final int n, final int n2, final int n3) throws IOException {
        super.a(n, n2, n3);
    }
    
    public void a(final int n, final int n2, final float n3) {
        Panel.render((float)n, (float)n2, n3);
        super.a(n, n2, n3);
    }
    
    protected void b(final int n, final int n2, final int n3) {
        super.b(n, n2, n3);
    }
}
