package cn.Hanabi.modules.Player;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.settings.*;
import ClassSub.*;
import org.lwjgl.input.*;
import com.darkmagician6.eventapi.*;

public class InvMove extends Mod
{
    private boolean isWalking;
    private Value mode;
    
    
    public InvMove() {
        super("InvMove", Category.PLAYER);
        (this.mode = new Value("InvMove", "Mode", 0)).addValue("Vanilla");
        this.mode.addValue("Hypixel");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (InvMove.mc.currentScreen != null && !(InvMove.mc.currentScreen instanceof GuiChat)) {
            this.isWalking = true;
            if (this.mode.isCurrentMode("Hypixel")) {
                InvMove.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(6));
                this.setDisplayName("Hypixel");
            }
            else {
                this.setDisplayName("Vanilla");
            }
            if (Class334.username.length() < 1) {
                System.exit(0);
            }
            KeyBinding[] array;
            for (int length = (array = new KeyBinding[] { InvMove.mc.gameSettings.keyBindForward, InvMove.mc.gameSettings.keyBindBack, InvMove.mc.gameSettings.keyBindLeft, InvMove.mc.gameSettings.keyBindRight, InvMove.mc.gameSettings.keyBindSprint, InvMove.mc.gameSettings.keyBindJump }).length, i = 0; i < length; ++i) {
                if (Class346.abuses < 0) {
                    InvMove.mc.thePlayer.motionY = 0.3;
                }
                final KeyBinding keyBinding = array[i];
                KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
            }
        }
        else if (this.isWalking) {
            if (this.mode.isCurrentMode("Hypixel")) {
                InvMove.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(InvMove.mc.thePlayer.inventory.currentItem));
                this.setDisplayName("Hypixel");
            }
            else {
                this.setDisplayName("Vanilla");
            }
            this.isWalking = false;
        }
    }
}
