package koks.command;

import koks.Koks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public abstract class Command {

    private String name,alias;

    public Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String alias){
        this.name = name;
        this.alias = alias;
    }

    public abstract void execute(String[] args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int KeytoInt(String Key) {
        for (int i = 0; i < Keyboard.KEYBOARD_SIZE; i++) {
            if (Keyboard.getKeyName(i) != null
                    && Keyboard.getKeyName(i).equalsIgnoreCase(Key.toUpperCase())) {
                return i;
            } else if (Keyboard.getKeyName(i) == null) {
                break;
            }
        }
        return -1;
    }

    public void sendmsg(String msg, boolean prefix) {
        String pref = prefix ? Koks.getKoks().PREFIX : "";
        mc.thePlayer.addChatMessage(new ChatComponentText(pref + msg));
    }

    public void sendError(String errorType, String fix, boolean prefix) {
        String pref = prefix ? Koks.getKoks().PREFIX : "";
        String message = pref + "§c§lERROR §e" + errorType.toUpperCase() + "§7: " + fix;
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public void sendServerMessage(String message) {
        mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }

}