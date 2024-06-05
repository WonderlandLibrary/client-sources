package de.tired.api.logger.impl;

import de.tired.api.annotations.LoggerAnnotation;
import de.tired.interfaces.IHook;
import de.tired.tired.Tired;
import net.minecraft.util.ChatComponentText;

@LoggerAnnotation(error = false, minecraft = true)
public enum IngameChatLog implements IHook {

    INGAME_CHAT_LOG;

    public void doLog(String text) {
        MC.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[" + Tired.INSTANCE.CLIENT_NAME +"] " + text));
    }




}
