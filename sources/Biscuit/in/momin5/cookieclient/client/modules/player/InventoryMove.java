package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

public class InventoryMove extends Module {
    public InventoryMove(){
        super("InventoryMove", Category.PLAYER);
    }

    @Override
    public void onUpdate(){
        if (mc.currentScreen != null){
            if (!(mc.currentScreen instanceof GuiChat)){
                if (Keyboard.isKeyDown(200)){
                    mc.player.rotationPitch -= 5;
                }
                if (Keyboard.isKeyDown(208)){
                    mc.player.rotationPitch += 5;
                }
                if (Keyboard.isKeyDown(205)){
                    mc.player.rotationYaw += 5;
                }
                if (Keyboard.isKeyDown(203)){
                    mc.player.rotationYaw -= 5;
                }
                if (mc.player.rotationPitch > 90){
                    mc.player.rotationPitch = 90;
                }
                if (mc.player.rotationPitch < -90){
                    mc.player.rotationPitch = -90;
                }
            }
        }
    }

}
