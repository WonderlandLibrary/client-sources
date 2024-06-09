package com.client.glowclient;

import com.client.glowclient.commands.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.utils.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;

public class uA
{
    private static String B;
    private static String b;
    
    static {
        uA.B = "";
        uA.b = "";
    }
    
    public static void M(final GuiTextField guiTextField) {
        if (guiTextField.getText().startsWith(Command.B.e()) && !guiTextField.getText().equals(Command.B.e())) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            final int x = guiTextField.x;
            final int y = guiTextField.y;
            if (!uA.B.equalsIgnoreCase(guiTextField.getText())) {
                uA.B = guiTextField.getText();
                uA.b = zA.M(uA.B);
            }
            final FontRenderer fontRenderer = Wrapper.mc.fontRenderer;
            final String b = uA.b;
            final float n = (float)x;
            final float n2 = (float)y;
            final int n3 = 200;
            fontRenderer.drawString(b, n, n2, ga.M(n3, n3, n3, 150), false);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
    
    public uA() {
        super();
    }
    
    public static void M(final GuiTextField guiTextField, final int n, final CallbackInfo callbackInfo) {
        if ((n == 28 || n == 156) && guiTextField.getText().startsWith(Command.B.e())) {
            Wrapper.mc.ingameGUI.getChatGUI().addToSentMessages(guiTextField.getText());
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
            zA.M(guiTextField.getText());
            callbackInfo.cancel();
        }
    }
}
