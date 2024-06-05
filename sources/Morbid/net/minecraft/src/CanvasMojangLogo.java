package net.minecraft.src;

import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

class CanvasMojangLogo extends Canvas
{
    private BufferedImage logo;
    
    public CanvasMojangLogo() {
        try {
            this.logo = ImageIO.read(PanelCrashReport.class.getResource("/gui/crash_logo.png"));
        }
        catch (IOException ex) {}
        final byte var1 = 100;
        this.setPreferredSize(new Dimension(var1, var1));
        this.setMinimumSize(new Dimension(var1, var1));
    }
    
    @Override
    public void paint(final Graphics par1Graphics) {
        super.paint(par1Graphics);
        par1Graphics.drawImage(this.logo, this.getWidth() / 2 - this.logo.getWidth() / 2, 32, null);
    }
}
