package net.minecraft.src;

import java.awt.*;

class CanvasCrashReport extends Canvas
{
    public CanvasCrashReport(final int par1) {
        this.setPreferredSize(new Dimension(par1, par1));
        this.setMinimumSize(new Dimension(par1, par1));
    }
}
