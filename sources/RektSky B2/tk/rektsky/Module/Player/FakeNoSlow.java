package tk.rektsky.Module.Player;

import tk.rektsky.Module.*;
import net.minecraft.item.*;

public class FakeNoSlow extends Module
{
    public FakeNoSlow() {
        super("NoSlow", "Stop you from slowing done when you're blocking sword", Category.PLAYER);
    }
    
    public boolean doSlowDown() {
        return (this.mc.thePlayer != null && this.mc.theWorld != null && this.mc.thePlayer.getHeldItem() != null && !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) || !this.isToggled();
    }
}
