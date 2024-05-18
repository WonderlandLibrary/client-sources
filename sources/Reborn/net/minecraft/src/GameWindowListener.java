package net.minecraft.src;

import java.awt.event.*;

public final class GameWindowListener extends WindowAdapter
{
    @Override
    public void windowClosing(final WindowEvent par1WindowEvent) {
        System.err.println("Someone is closing me!");
        System.exit(1);
    }
}
