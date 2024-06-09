package com.wikihacks.utils;

import com.wikihacks.module.Module;
import com.wikihacks.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class ChatUtils {
    public static void printMessage(ChatType type, ITextComponent message) {
        Minecraft.getMinecraft().ingameGUI.addChatMessage(type, message);
    }

    public static void printMessage(ITextComponent message) {
        printMessage(ChatType.SYSTEM, message);
    }

    public static ITextComponent colouredString(String message, String color) {
        return ITextComponent.Serializer.jsonToComponent("{\"text\":\"" + message + "\",\"color\":\"" + color + "\"}");
    }

    public static ITextComponent toggleMessage(Module module) {
        return colouredString(module.getName() + " has been " + (ModuleManager.getModuleByName(module.getName()).isEnabled() ? "enabled" : "disabled"), "red");
    }
}
