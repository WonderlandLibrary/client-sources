package optifine;

import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiMessage extends GuiScreen
{
    private final List listLines2;
    protected String confirmButtonText;
    private GuiScreen parentScreen;
    private String messageLine1;
    private String messageLine2;
    private int ticksUntilEnable;
    
    public GuiMessage(final GuiScreen parentScreen, final String line1, final String line2) {
        this.listLines2 = Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = line1;
        this.messageLine2 = line2;
        this.confirmButtonText = I18n.format("gui.done", new Object[0]);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(0, GuiMessage.width / 2 - 74, GuiMessage.height / 6 + 96, this.confirmButtonText));
        this.listLines2.clear();
        this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, GuiMessage.width - 50));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        Config.getMinecraft().displayGuiScreen(this.parentScreen);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.messageLine1, GuiMessage.width / 2, 70, 16777215);
        int var4 = 90;
        for (final String var6 : this.listLines2) {
            Gui.drawCenteredString(this.fontRendererObj, var6, GuiMessage.width / 2, var4, 16777215);
            var4 += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void setButtonDelay(final int ticksUntilEnable) {
        this.ticksUntilEnable = ticksUntilEnable;
        for (final GuiButton var3 : this.buttonList) {
            var3.enabled = false;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int ticksUntilEnable = this.ticksUntilEnable - 1;
        this.ticksUntilEnable = ticksUntilEnable;
        if (ticksUntilEnable == 0) {
            for (final GuiButton var2 : this.buttonList) {
                var2.enabled = true;
            }
        }
    }
}
