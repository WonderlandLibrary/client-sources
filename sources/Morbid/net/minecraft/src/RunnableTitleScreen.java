package net.minecraft.src;

import java.net.*;

class RunnableTitleScreen implements Runnable
{
    final GuiMainMenu field_104058_d;
    
    RunnableTitleScreen(final GuiMainMenu par1GuiMainMenu) {
        this.field_104058_d = par1GuiMainMenu;
    }
    
    @Override
    public void run() {
        try {
            String var1 = HttpUtil.func_104145_a(new URL("http://assets.minecraft.net/1_6_has_been_released.flag"));
            if (var1 != null && var1.length() > 0) {
                var1 = var1.trim();
            }
        }
        catch (Throwable var2) {
            var2.printStackTrace();
        }
    }
}
