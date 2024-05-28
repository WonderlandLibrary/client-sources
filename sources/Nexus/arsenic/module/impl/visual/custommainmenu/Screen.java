package arsenic.module.impl.visual.custommainmenu;

import arsenic.gui.themes.Theme;
import arsenic.main.Nexus;
import arsenic.utils.font.FontRendererExtension;
import arsenic.utils.interfaces.IFontRenderer;
import arsenic.utils.java.ColorUtils;
import arsenic.utils.render.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

import static arsenic.utils.render.ShaderUtils.drawShader;
import static arsenic.utils.render.ShaderUtils.mainMenuProgram;

public class Screen extends GuiScreen {

    @Override
    public void initGui() {
        int p_73969_1_ = this.height / 4 + 48;
        int p_73969_2_ = 24;
        this.buttonList.add(new CustomGuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new CustomGuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new CustomGuiButton(3, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("Alt Manager", new Object[0])));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float time = (System.currentTimeMillis() % 100000) / 1000f;
        int color = Nexus.getNexus().getThemeManager().getCurrentTheme().getMainColor();
        drawShader(mainMenuProgram, time, (float) ColorUtils.getColor(color, 0), (float) ColorUtils.getColor(color, 2), (float) ColorUtils.getColor(color, 3));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
        }
    }

    public static class CustomGuiButton extends GuiButton{

        public CustomGuiButton(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, buttonText);
        }

        public CustomGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
            super(buttonId, x, y, widthIn, heightIn, buttonText);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            FontRendererExtension<?> fontRenderer = ((IFontRenderer) mc.fontRendererObj).getFontRendererExtension();
            Theme theme = Nexus.getNexus().getThemeManager().getCurrentTheme();
            DrawUtils.drawBorderedRoundedRect(xPosition, yPosition, xPosition + width, yPosition + height, 5, 2, theme.getMainColor(), theme.getDarkerColor());
            fontRenderer.drawStringWithShadow(displayString, xPosition + width/2f, yPosition + height/2f, -1, fontRenderer.CENTREX, fontRenderer.CENTREY);
        }
    }

}
