package jaco.mp3.player.plaf;

import java.awt.image.*;
import jaco.a.*;
import javax.swing.*;
import java.awt.*;

final class a extends JButton
{
    private a(final MP3PlayerUI mp3PlayerUI, final BufferedImage bufferedImage, final byte b) {
        final BufferedImage a = b.a(bufferedImage, 0.05f);
        final BufferedImage b2 = b.b(bufferedImage, 0.05f);
        this.setIcon(jaco.a.a.a(bufferedImage));
        this.setRolloverIcon(jaco.a.a.a(a));
        this.setPressedIcon(jaco.a.a.a(b2));
        this.setCursor(Cursor.getPredefinedCursor(12));
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setContentAreaFilled(false);
        this.setFocusable(false);
        this.setFocusPainted(false);
    }
}
