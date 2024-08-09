package im.expensive.utils.client;

import com.mojang.blaze3d.systems.IRenderCall;

import im.expensive.utils.TPSCalc;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.text.GradientUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public interface IMinecraft {
    Minecraft mc = Minecraft.getInstance();

    MainWindow window = mc.getMainWindow();
    BufferBuilder buffer = Tessellator.getInstance().getBuffer();
    Tessellator tessellator = Tessellator.getInstance();
    List<ITextComponent> clientMessages = new ArrayList<>();
    default void print(String input) {
        if (mc.player == null)
            return;
        ITextComponent text = GradientUtil.gradient("Expensive Client")
                .append(new StringTextComponent(TextFormatting.DARK_GRAY + " -> " + TextFormatting.RESET + input));
        clientMessages.add(text);
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(text, 0);

    }

}
