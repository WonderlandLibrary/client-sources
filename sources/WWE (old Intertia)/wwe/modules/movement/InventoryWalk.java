package wwe.modules.movement;

import wwe.modules.*;
import net.minecraft.client.gui.*;
import wwe.utils.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import wwe.*;

public class InventoryWalk extends qMIe
{
    public BooleanValue chat;
    Integer[] eeKl;
    
    public InventoryWalk() {
        super("Inventory Walk", "Allows you to walk in gui", 0, Category.MOVEMENT);
        this.eeKl = new Integer[] { 200, 208, 205, 203 };
    }
    
    @Override
    public void HrDr() {
        this.chat = new BooleanValue("Chat", this, "Move while in chat", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if ((!this.chat.OnGi() && Wrapper.mc.currentScreen instanceof GuiChat) || (!this.chat.OnGi() && Wrapper.mc.currentScreen instanceof GuiEditSign)) {
            return;
        }
        if (Wrapper.mc.currentScreen != null) {
            if (Keyboard.isKeyDown((int)this.eeKl[0])) {
                final EntityPlayerSP player = Wrapper.mc.player;
                player.rotationPitch -= 7.0f;
            }
            if (Keyboard.isKeyDown((int)this.eeKl[1])) {
                final EntityPlayerSP player2 = Wrapper.mc.player;
                player2.rotationPitch += 7.0f;
            }
            if (Keyboard.isKeyDown((int)this.eeKl[2])) {
                final EntityPlayerSP player3 = Wrapper.mc.player;
                player3.rotationYaw += 7.0f;
            }
            if (Keyboard.isKeyDown((int)this.eeKl[3])) {
                final EntityPlayerSP player4 = Wrapper.mc.player;
                player4.rotationYaw -= 7.0f;
            }
            if (Keyboard.isKeyDown(Wrapper.mc.gameSettings.keyBindSprint.getKeyCode())) {
                Wrapper.mc.player.setSprinting(true);
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}
