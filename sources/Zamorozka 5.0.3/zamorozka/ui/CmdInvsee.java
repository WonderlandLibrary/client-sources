package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.RenderEvent3D;

public class CmdInvsee extends Command {

    private String targetName;

    public CmdInvsee() {
        super("invsee");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (Minecraft.player.capabilities.isCreativeMode) {
                ChatUtils.printChatprefix("Survival mode only.");
                return;
            }

            targetName = args[0];
        } catch (Exception e) {
            ChatUtils.printChatprefix("Usage: " + getSyntax());
        }
    }

    @EventTarget
    public void onRender(RenderEvent3D event) {
        boolean found = false;

        for (Entity entity : mc.world.loadedEntityList) {
            EntityPlayer player = (EntityPlayer) entity;
            String otherPlayerName = player.getName();
            if (!otherPlayerName.equalsIgnoreCase(targetName))
                continue;

            ChatUtils.printChatprefix("Showing inventory of " + otherPlayerName + ".");
            mc.currentScreen = (new GuiInventory(player));
            found = true;
            break;
        }

        if (!found)
            ChatUtils.printChatprefix("Player not found.");

        targetName = null;
    }


    @Override
    public String getDescription() {
        return "See players inventory";
    }

    @Override
    public String getSyntax() {
        return "invsee check <name>";
    }

}