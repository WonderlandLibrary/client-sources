package de.labystudio.listener;

import de.labystudio.gui.GuiStopWatch;
import de.labystudio.utils.AutoTextLoader;
import org.lwjgl.input.Keyboard;

public class KeyListener
{
    private static int key = -1;

    public static void handle()
    {
        if (Keyboard.isCreated() && Keyboard.getEventKeyState())
        {
            if (key != Keyboard.getEventKey())
            {
                key = Keyboard.getEventKey();
                handleKeyboardInput(key);
            }
        }
        else
        {
            key = -1;
        }

        AutoTextLoader.handleKeyboardInput(key);
        GuiStopWatch.handleKeys(key);
    }

    private static void handleKeyboardInput(int key)
    {
    }
}
