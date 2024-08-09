/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.utils.text.GradientUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public interface IMinecraft {
    public static final Minecraft mc = Minecraft.getInstance();
    public static final MainWindow window = mc.getMainWindow();
    public static final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    public static final Tessellator tessellator = Tessellator.getInstance();
    public static final List<ITextComponent> clientMessages = new ArrayList<ITextComponent>();

    default public void print(String string) {
        if (IMinecraft.mc.player == null) {
            return;
        }
        IFormattableTextComponent iFormattableTextComponent = GradientUtil.gradient("Venus Free").append(new StringTextComponent(TextFormatting.DARK_GRAY + " -> " + TextFormatting.RESET + string));
        clientMessages.add(iFormattableTextComponent);
        IMinecraft.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(iFormattableTextComponent, 0);
    }
}

