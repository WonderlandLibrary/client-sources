package com.krazzzzymonkey.catalyst.module.modules.gui;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.value.Mode;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import com.krazzzzymonkey.catalyst.utils.visual.ColorUtils;
import net.minecraft.client.gui.GuiChat;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class HUD extends Modules
{

    public BooleanValue effects;

    
    @Override
    public void onRenderGameOverlay(final RenderGameOverlayEvent.Text overlayText) {
        GL11.glPushMatrix();
        GL11.glScalef(1.5f, 1.5f, 1.5f);

        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Catalyst", 4.0f, 4.0f, ClickGui.getColor());

        GL11.glScalef(0.6f, 0.6f, 0.6f);

        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(String.valueOf(new StringBuilder().append("Welcome").append(Minecraft.getMinecraft().player.getName())), 7.0f, 25.0f, ClickGui.getColor());
        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("v0.1.0", 78.0f, 12.0f, ClickGui.getColor());
        
        GL11.glPopMatrix();
        final double xPos = Wrapper.INSTANCE.player().posX;
        final double yPos = Wrapper.INSTANCE.player().posY;
        final double zPos = Wrapper.INSTANCE.player().posZ;
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.INSTANCE.mc());
        final String s = "§7X: §f%s §7Y: §f%s §7Z: §f%s";
        final Object[] array = new Object[3];
        array[0] = RenderUtils.DF((float)xPos, 1);
        array[1] = RenderUtils.DF((float)yPos, 1);
        array[2] = RenderUtils.DF((float)zPos, 1);
        final String stringFormat = String.format(s, array);
        final boolean guiChat = Wrapper.INSTANCE.mc().currentScreen instanceof GuiChat;
        int n;
        if (guiChat) {
            n = scaledResolution.getScaledHeight() - 37;
        }
        else {
            n = scaledResolution.getScaledHeight() - 22;
        }
        final int integer1 = n;
        int n2;
        if (guiChat) {
            n2 = scaledResolution.getScaledHeight() - 23;
        }
        else {
            n2 = scaledResolution.getScaledHeight() - 10;
        }
        final int integer2 = n2;
        final int color1 = ColorUtils.color(0.0f, 0.0f, 0.0f, 0.0f);
        final int color2 = ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f);
        RenderUtils.drawStringWithRect(stringFormat, 4, integer2, ClickGui.getColor(), color1, color2);
        if (Minecraft.getMinecraft().getCurrentServerData() == null) {
            final StringBuilder append = new StringBuilder().append("§7FPS: §f");
            Wrapper.INSTANCE.mc();
            RenderUtils.drawStringWithRect(String.valueOf(append.append(Minecraft.getDebugFPS())), 4, integer1, ClickGui.getColor(), color1, color2);
        }
        else {
            final StringBuilder append2 = new StringBuilder().append("§7FPS: §f");
            Wrapper.INSTANCE.mc();
            RenderUtils.drawStringWithRect(String.valueOf(append2.append(Minecraft.getDebugFPS()).append(" §7PING: §f").append(Minecraft.getMinecraft().player.connection.getPlayerInfo(Minecraft.getMinecraft().player.getUniqueID()).getResponseTime())), 4, integer1, ClickGui.getColor(), color1, color2);
        }
        int integer3 = 35;
        int integer4 = 4;
        final boolean hacks = (boolean)HackManager.getSortedHacks().iterator();
        while (((Iterator)hacks).hasNext()) {
            final Modules modules = ((Iterator<Modules>)hacks).next();
            String str = "";
            final int integer5 = (int)modules.getValues().iterator();
            while (((Iterator)integer5).hasNext()) {
                final Value val = ((Iterator<Value>)integer5).next();
                if (val instanceof ModeValue) {
                    final ModeValue modeValue = (ModeValue)val;
                    if (!(modeValue.getModeName().equals("Priority"))) {
                        final long modes = (Object)modeValue.getModes();
                        final byte modeLength = (byte)modes.length;
                        byte byte1 = (byte)0;
                        while (byte1 < modeLength) {
                            final Mode module = modes[byte1];
                            if (module.isToggled()) {
                                str = String.valueOf(new StringBuilder().append(str).append(" §7").append(module.getName()));
                            }
                            ++byte1;
                    }
                }
            }
            if ((boolean)this.effects.getValue()) {
                integer4 = 6;
                RenderUtils.drawBorderedRect(integer4 - 2, integer3 - 2, integer4 + Wrapper.INSTANCE.fontRenderer().getStringWidth(String.valueOf(new StringBuilder().append(modules.getName()).append(str))) + 2, integer3 + 10, 1.0f, color1, ClickGui.getColor());
            }
            else {
                integer4 = 4;
            }
            RenderUtils.drawStringWithRect(String.valueOf(new StringBuilder().append(modules.getName()).append(str)), integer4, integer3, ClickGui.getColor(), color1, color2);
            if ((boolean)this.effects.getValue()) {
                RenderUtils.drawBorderedRect(integer4 - 2, integer3 - 2, integer4 - 6, integer3 + 10, 1.0f, ClickGui.getColor(), ClickGui.getColor());
            }
            integer3 += 12;
        }
        final Modules modules = HackManager.getToggleHack();
        if (modules != null) {
            String text;
            if (modules.isToggled()) {
                text = String.valueOf(new StringBuilder().append(modules.getName()).append(" - Enabled"));
            }
            else {
                text = String.valueOf(new StringBuilder().append("§7").append(modules.getName()).append(" - Disabled"));
            }
            RenderUtils.drawSplash(text);
        }
        super.onRenderGameOverlay(overlayText);
    }
    
    
    public HUD() {
        super("HUD", ModuleCategory.GUI);
        this.setToggled(true);
        this.setShow(false);
        this.effects = new BooleanValue("Effects", false);
        final Value[] valu = new Value[1];
        valu[0] = this.effects;
        this.addValue(valu);
    }
    
}
