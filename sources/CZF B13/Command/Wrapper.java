package gq.vapu.czfclient.Command;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.GameSettings;

public class Wrapper {

    public static volatile Wrapper INSTANCE = new Wrapper();

    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }

//    public EntityPlayerSP player() {
//        return Wrapper.INSTANCE.mc().player;
//    }
//
//    public WorldClient world() {
//        return Wrapper.INSTANCE.mc().world;
//    }

    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().gameSettings;
    }

//    public FontRenderer fontRenderer() {
//        return Wrapper.INSTANCE.mc().fontRenderer;
//    }

//    public void sendPacket(Packet packet) {
//        this.player().connection.sendPacket(packet);
//    }
//
//    public InventoryPlayer inventory() {
//        return this.player().inventory;
//    }

    public PlayerControllerMP controller() {
        return Wrapper.INSTANCE.mc().playerController;
    }
}

