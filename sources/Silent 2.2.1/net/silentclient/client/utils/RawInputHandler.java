package net.silentclient.client.utils;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MouseHelper;
import net.silentclient.client.Client;

public class RawInputHandler {
    public static Controller[] controllers;
    public static Mouse mouse;
    public static int dx = 0;
    public static int dy = 0;
    public static Thread inputThread;

    public static void init() {
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        startThread();
    }

    public static void reload() {
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
    }

    public static void getMouse() {
        for (int i = 0; i < controllers.length && mouse == null; i++) {
            if (controllers[i].getType() == Controller.Type.MOUSE) {
                controllers[i].poll();
                if (((Mouse) controllers[i]).getX().getPollData() != 0.0 || ((Mouse) controllers[i]).getY().getPollData() != 0.0) {
                    mouse = (Mouse) controllers[i];
                }
            }
        }
    }

    public static void toggleRawInput(boolean enable) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        float saveYaw = player.rotationYaw;
        float savePitch = player.rotationPitch;

        if (Minecraft.getMinecraft().mouseHelper instanceof RawMouseHelper && !enable) {
            Client.logger.info("[SC]: Disabling Raw Mouse Input");
            Minecraft.getMinecraft().mouseHelper = new MouseHelper();
            Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            inputThread.interrupt();
        } else {
            Client.logger.info("[SC]: Enabling Raw Mouse Input");
            Minecraft.getMinecraft().mouseHelper = new RawMouseHelper();
            startThread();
            Minecraft.getMinecraft().mouseHelper.grabMouseCursor();
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
        }
        player.rotationYaw = saveYaw;
        player.rotationPitch = savePitch;
    }

    public static void rescan() {
        RawInputHandler.getMouse();
    }

    public static void startThread() {
        inputThread = new Thread(() -> {
            while(true){
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
//                reload();
//                rescan();
                if (mouse != null && Minecraft.getMinecraft().currentScreen == null) {
                    mouse.poll();
                    dx += (int)mouse.getX().getPollData();
                    dy += (int)mouse.getY().getPollData();
                } else if (mouse != null) {
                    mouse.poll();
                } else {
                    getMouse();
                }

                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                   Client.logger.catching(e);
                }
            }
        });
        inputThread.setName("inputThread");
        inputThread.start();
    }
}