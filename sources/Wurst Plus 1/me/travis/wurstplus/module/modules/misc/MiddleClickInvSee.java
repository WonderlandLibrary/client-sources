// package me.travis.wurstplus.module.modules.misc;

// import net.minecraft.client.entity.EntityPlayerSP;
// import net.minecraft.client.gui.GuiScreen;
// import net.minecraft.client.gui.inventory.GuiInventory;
// import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
// import org.lwjgl.input.Mouse;
// import net.minecraftforge.client.event.MouseEvent;
// import net.minecraft.entity.player.EntityPlayer;
// import me.travis.wurstplus.command.Command;
// import me.travis.wurstplus.module.Module;
// import me.travis.wurstplus.util.Invdraw;

// @Module.Info(name = "MiddleClick Inv", category = Module.Category.RENDER)
// public class MiddleClickInvSee extends Module
// {
//     private EntityPlayer player;
    
//     @SubscribeEvent
//     public void onMouseEvent(final MouseEvent event) {
//         if (isEnabled()) {
//             Command.sendChatMessage("Middle Click register");
//             try {
//                 this.player = (EntityPlayer)MiddleClickInvSee.mc.objectMouseOver.entityHit;
//             } catch (Exception e) {
//                 Command.sendChatMessage("Error: "+e);
//                 this.disable();
//             }
            
//         }
//     }
    
//     public void onRender() {
//         if (this.player != null) {
//             try {
//                 Command.sendChatMessage("Attempting draw - mc");

//                 Invdraw i = new Invdraw();
//                 i.drawInventory(50, 50, (EntityPlayerSP) this.player);

//                 this.player = null;
//             }
//             catch (Exception e) {
//                 e.printStackTrace();
//                 Command.sendChatMessage("Cannot send message: "+e);
//             }
//         }
//     }

// }
