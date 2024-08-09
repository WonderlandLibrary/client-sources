package fun.ellant.utils.client;

import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.text.GradientUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
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
        ITextComponent text = GradientUtil.gradient("Ellant Client", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255))
                .append(new StringTextComponent(TextFormatting.DARK_GRAY + " -> " + TextFormatting.RESET + input));
        clientMessages.add(text);
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(text, 0);

    }

}
