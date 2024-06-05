package club.mega.module.impl.hud;

import club.mega.Mega;
import club.mega.event.impl.EventRender2D;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;
import club.mega.module.setting.impl.TextSetting;
import club.mega.util.ColorUtil;
import club.mega.util.RenderUtil;
import net.minecraft.client.Minecraft;
import rip.hippo.lwjeb.annotation.Handler;

import java.awt.Color;

@Module.ModuleInfo(name = "WaterMark", description = "WaterMark", category = Category.HUD)
public class WaterMark extends Module {

    private final TextSetting text = new TextSetting("Text", this, Mega.INSTANCE.getName());
    private final BooleanSetting blur = new BooleanSetting("Blur", this, true);

    @Handler
    public final void render2D(final EventRender2D event) {
        final String text = "v" + Mega.INSTANCE.getVersion() + " | " + Mega.INSTANCE.getUserName().toLowerCase() + " | " + Minecraft.debugFPS;
        float x = 6;
        float y = 6;

        if (this.text.getText().isEmpty()) {
            if (blur.get()) RenderUtil.drawBlurredRect(x - 2, y - 3, x - 2+ Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").getWidth(Mega.INSTANCE.getName()) + Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth(text), 15, new Color(1,1,1));
            RenderUtil.drawRect(x - 2, y - 3, x - 2 + Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").getWidth(Mega.INSTANCE.getName()) + Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth(text), 15, new Color(1, 1, 1, blur.get() ? 160 : 190));
            RenderUtil.drawRect(x - 1, y - 2, x - 4 + Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").getWidth(Mega.INSTANCE.getName()) + Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth(text), 1, ColorUtil.getMainColor());
            Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawStringWithShadow(Mega.INSTANCE.getName(), x, y, ColorUtil.getMainColor().getRGB());
            Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString(text, x + Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").getWidth(Mega.INSTANCE.getName()), y, -1);
        } else {
            RenderUtil.drawRect(x - 2, y - 3, x - 2 + Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth(this.text.getText()), 15, new Color(1, 1, 1, blur.get() ? 160 : 190));
            Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString(this.text.getText(), x, y, -1);
        }
    }

}
