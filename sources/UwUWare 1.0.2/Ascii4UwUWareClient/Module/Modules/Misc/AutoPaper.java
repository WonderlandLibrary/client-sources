package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.*;
import org.lwjgl.input.Mouse;

import static Ascii4UwUWareClient.Util.Wrapper.getPlayer;

public class AutoPaper extends Module {

    public boolean shouldEnable;
    public boolean hasGottenText;
    public boolean hasAlreadyToggled;
    public boolean haveAlreadyClicked;

    public AutoPaper() {
        super("AutoPaper", new String[]{"Paper", "AutoPaper"}, ModuleType.Misc);
    }

    @Override
    public void onEnable() {
        hasGottenText = false;
        super.onEnable();
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event){
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat chat = (S02PacketChat) event.getPacket();
            IChatComponent chatComponent = chat.getChatComponent();
            if (chatComponent instanceof ChatComponentText) {
                String text = ((ChatComponentText) chatComponent).getUnformattedText();

                if(text.contains(Minecraft.thePlayer.getName() + " has joined") ) {
                    hasAlreadyToggled = false;
                    haveAlreadyClicked = false;
                    hasGottenText = true;
                }

                if(text.contains("You have activated") ) {

                    hasAlreadyToggled = true;
                }

                if(text.contains("The game starts") ) {


                    if(!haveAlreadyClicked) {
                        shouldEnable = true;

                    }
                    hasGottenText = true;
                }

                if(text.contains("Teaming is not allowed") && !hasGottenText) {
                    Helper.sendMessage("Joined too late to toggle Paper Challenge. Sorry!");

                }
            }
        }
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        ItemStack s7 = Minecraft.thePlayer.inventoryContainer.getSlot(7).getStack();


        if (shouldEnable) {

            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                if (chest.lowerChestInventory.getName().contains("Chest")) {
                    if (!Mouse.isGrabbed()) {
                        mc.mouseHelper.grabMouseCursor();
                    }

                    mc.inGameHasFocus = true;
                }


            }


            if (Minecraft.thePlayer.ticksExisted % 2 == 0) {

                Minecraft.thePlayer.sendQueue.addToSendQueueSilent(new C09PacketHeldItemChange(7));

                haveAlreadyClicked = false;
            }
            if (Minecraft.thePlayer.ticksExisted % 4 == 0) {
                Minecraft.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, s7, new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 2, Minecraft.thePlayer.posZ), EnumFacing.UP, new Vec3(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 2, Minecraft.thePlayer.posZ));
                // mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, s7, 0, 0, 0));
                Minecraft.thePlayer.sendQueue.addToSendQueueSilent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1),
                        255,
                        Minecraft.thePlayer.inventory.getStackInSlot(7),
                        0,
                        0,
                        0));
            }

            if (Minecraft.thePlayer.ticksExisted % 5 == 0) {

                if (!haveAlreadyClicked) {
                    //mc.thePlayer.sendQueue.addToSendQueueSilent(new C0EPacketClickWindow(1, 7, 6, 0, new ItemStack(Items.paper), (short) 1));
                    Minecraft.playerController.windowClick(1, 7, 0, 2, Minecraft.thePlayer);
                    haveAlreadyClicked = true;
                }


                if (hasAlreadyToggled) {
                    Minecraft.thePlayer.sendQueue.addToSendQueueSilent(new C0DPacketCloseWindow(Minecraft.thePlayer.openContainer.windowId));
                    Minecraft.thePlayer.closeScreen();
                    shouldEnable = false;

                }
            }
        }
    }
}
