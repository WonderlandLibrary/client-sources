package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;

public class AutoRespawn extends qMIe
{
    int OJXa;
    
    public AutoRespawn() {
        super("Auto Respawn", "Automatically respawns for you", 0, Category.PLAYER);
        this.OJXa = 0;
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.currentScreen instanceof GuiGameOver) {
            Wrapper.mc.player.respawnPlayer();
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
        }
    }
}
