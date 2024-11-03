package net.silentclient.client.utils;

import net.minecraft.util.MouseHelper;

public class RawMouseHelper extends MouseHelper {
    @Override
    public void mouseXYChange()
    {
        this.deltaX = RawInputHandler.dx;
        RawInputHandler.dx = 0;
        this.deltaY = -RawInputHandler.dy;
        RawInputHandler.dy = 0;
    }
}
