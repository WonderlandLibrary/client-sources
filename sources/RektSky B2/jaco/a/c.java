package jaco.a;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

final class c implements Icon
{
    private BufferedImage a;
    
    private c(final BufferedImage a, final byte b) {
        this.a = a;
    }
    
    @Override
    public final void paintIcon(final Component component, final Graphics graphics, final int n, final int n2) {
        graphics.drawImage(this.a, n, n2, component);
    }
    
    @Override
    public final int getIconWidth() {
        return this.a.getWidth();
    }
    
    @Override
    public final int getIconHeight() {
        return this.a.getHeight();
    }
}
