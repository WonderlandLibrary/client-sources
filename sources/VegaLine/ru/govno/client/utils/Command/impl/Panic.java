/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.ClientRP;
import ru.govno.client.utils.Command.Command;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.TimerHelper;

public class Panic
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static boolean stop = false;
    public static List<Modules> mods = new ArrayList<Modules>();
    public static String lastCode = "";
    public static boolean callReload = true;
    private static String prevClientName;
    private static final String alphabet = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final SecureRandom secureRandom;
    static TimerHelper timeShow;
    static AnimationUtils showAnim;
    static boolean runShowCode;

    public Panic() {
        super("Panic", new String[]{"panic"});
    }

    public static String randomString(int strLength) {
        StringBuilder stringBuilder = new StringBuilder(strLength);
        for (int i = 0; i < strLength; ++i) {
            stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

    public static void enablePanic() {
        if (stop) {
            return;
        }
        lastCode = Panic.randomString((int)(2.0 + 3.0 * Math.random()));
        InputStream inputstream = null;
        InputStream inputstream1 = null;
        try {
            inputstream = Minecraft.getMinecraft().mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputstream1 = Minecraft.getMinecraft().mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Display.setIcon(new ByteBuffer[]{Minecraft.getMinecraft().readImageToBuffer(inputstream), Minecraft.getMinecraft().readImageToBuffer(inputstream1)});
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientRP.getInstance().getDiscordRP().shutdown();
        prevClientName = Client.name;
        Client.name = "Minecraft " + ClientBrandRetriever.getVersionStr();
        Display.setTitle(Client.name);
        for (Module mod : Client.moduleManager.getModuleList()) {
            mods.add(new Modules(mod, mod.actived, mod.bind));
            if (!mod.actived) continue;
            mod.toggleSilent(false);
            mod.bind = 0;
        }
        ClickGui.instance.bind = 0;
        stop = true;
    }

    public static void disablePanic() {
        if (!stop) {
            return;
        }
        lastCode = "";
        InputStream inputstream = null;
        InputStream inputstream1 = null;
        try {
            inputstream = Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(new ResourceLocation("vegaline/system/minecraft/window/windowicons/icon64.png"));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            inputstream1 = Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(new ResourceLocation("vegaline/system/minecraft/window/windowicons/icon32.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (inputstream != null && inputstream1 != null) {
            try {
                Display.setIcon(new ByteBuffer[]{Minecraft.getMinecraft().readImageToBuffer(inputstream), Minecraft.getMinecraft().readImageToBuffer(inputstream1)});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ClientRP.getInstance().init();
        ClientRP.getInstance().getDiscordRP().start();
        ClientRP.getInstance().getDiscordRP().refresh();
        Client.name = prevClientName;
        Display.setTitle(Client.name + " " + Client.version);
        for (Module mod : Client.moduleManager.getModuleList()) {
            for (Modules ms : mods) {
                if (ms.modules != mod) continue;
                mod.toggleSilent(ms.actived);
                mod.setBind(ms.bind);
            }
        }
        Client.configManager.saveConfig("temporaryPanical");
        callReload = true;
        mods.clear();
        stop = false;
    }

    public static void runShowCode() {
        runShowCode = true;
        timeShow.reset();
        Panic.showAnim.to = 0.001f;
    }

    static boolean updateShow() {
        if (runShowCode && timeShow.hasReached(500.0f)) {
            Panic.showAnim.to = 1.0f;
        }
        if (timeShow.hasReached(11000.0f) || !stop) {
            Panic.showAnim.to = 0.0f;
        }
        if ((double)showAnim.getAnim() < 0.05 && Panic.showAnim.to == 0.0f) {
            runShowCode = false;
        }
        return runShowCode;
    }

    public static void onHasShowPanicCode(float x, float y) {
        if (!Panic.updateShow() || !stop) {
            return;
        }
        float w = 110.0f;
        CFontRenderer fontCode = Fonts.mntsb_36;
        String code = ".panic " + lastCode;
        if ((float)(fontCode.getStringWidth(code) + 10) > w) {
            w = fontCode.getStringWidth(code) + 10;
        }
        float h = 57.0f;
        float aPC = showAnim.getAnim();
        int texCol = ColorUtils.swapAlpha(-1, 255.0f * aPC);
        int rectCol = ColorUtils.getColor(70, 70, 70, 210.0f * aPC);
        int rectCol2 = ColorUtils.getColor(0, 0, 0, 120.0f * aPC);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y -= h / 2.0f, (x -= w / 2.0f) + w, y + h, 8.0f, 2.0f, rectCol, rectCol, rectCol, rectCol, false, true, true);
        CFontRenderer fontStart = Fonts.mntsb_14;
        String start2 = "\u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u043a\u043e\u0434 \u043d\u0438\u0436\u0435";
        String start = "\u0414\u043b\u044f \u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u044f \u043f\u0430\u043d\u0438\u043a\u0430";
        float xPod = x + w / 2.0f - (float)fontCode.getStringWidth(code) / 2.0f - 2.0f;
        float xPod2 = x + w / 2.0f + (float)fontCode.getStringWidth(code) / 2.0f + 2.0f;
        float yPod = y + 10.0f + 9.0f + 6.0f;
        float yPod2 = y + 10.0f + 9.0f + 20.0f + 6.0f;
        RenderUtils.drawRect(xPod, yPod, xPod2, yPod2, rectCol2);
        if (255.0f * aPC >= 33.0f) {
            fontStart.drawCenteredString(start, x + w / 2.0f, y + 7.0f, texCol);
            fontStart.drawCenteredString(start2, x + w / 2.0f, y + 7.0f + 10.0f, texCol);
            fontCode.drawCenteredString(code, x + w / 2.0f, y + 7.0f + 10.0f + 9.0f, texCol);
        }
        float timePC = MathUtils.clamp(((float)timeShow.getTime() - 550.0f) / 11000.0f, 0.0f, 1.0f);
        float out = 1.0f - timePC;
        RenderUtils.drawRect(x + 4.0f, y + h - 6.0f, x + 4.0f + (w - 8.0f) * out, y + h - 4.0f, texCol);
        RenderUtils.drawRect(x + 4.0f + (w - 8.0f) * out, y + h - 6.0f, x - 4.0f + w, y + h - 4.0f, rectCol2);
        GlStateManager.enableDepth();
        GL11.glDepthMask(true);
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[1].equalsIgnoreCase("on") && !stop) {
                Panic.enablePanic();
                Panic.runShowCode();
            }
            if (args[1].toUpperCase().equalsIgnoreCase(lastCode) && stop) {
                Panic.disablePanic();
            }
        } catch (Exception formatException) {
            Client.msg("\u00a72\u00a7lPanic:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a72\u00a7lPanic:\u00a7r \u00a77panic [\u00a7lon/code\u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }

    static {
        secureRandom = new SecureRandom();
        timeShow = new TimerHelper();
        showAnim = new AnimationUtils(0.0f, 0.0f, 0.05f);
    }

    public static class Modules {
        Module modules;
        boolean actived;
        int bind;

        public Modules(Module modules, boolean actived, int bind) {
            for (Module mod : Client.moduleManager.getModuleList()) {
                if (!mod.actived) continue;
                this.modules = modules;
                this.actived = actived;
                this.bind = bind;
                break;
            }
        }
    }
}

