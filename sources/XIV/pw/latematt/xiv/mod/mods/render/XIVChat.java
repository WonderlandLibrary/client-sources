package pw.latematt.xiv.mod.mods.render;

import net.minecraft.client.gui.GuiNewChat;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.ui.GuiXIVChat;

public class XIVChat extends Mod {
    public XIVChat() {
        super("XIVChat", ModType.RENDER);
    }

    @Override
    public void onEnabled() {
        GuiNewChat chat = new GuiXIVChat(mc);
        chat = copyLines(mc.ingameGUI.getChatGUI(), chat);

        mc.ingameGUI.setChatGUI(chat);
    }

    @Override
    public void onDisabled() {
        GuiNewChat chat = new GuiNewChat(mc);
        chat = copyLines(mc.ingameGUI.getChatGUI(), chat);

        mc.ingameGUI.setChatGUI(chat);
    }

    private GuiNewChat copyLines(GuiNewChat oldChat, GuiNewChat newChat) {
        for (Object o : oldChat.getChatLines()) {
            newChat.getChatLines().add(o);
        }

        for (Object o : oldChat.getField_146253_i()) {
            newChat.getField_146253_i().add(o);
        }

        for (Object o : oldChat.getSentMessages()) {
            newChat.getSentMessages().add(o);
        }

        return newChat;
    }
}
