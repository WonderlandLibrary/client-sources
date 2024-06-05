package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import me.satisfactory.base.gui.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class GuiShareToLan extends GuiScreen
{
    private final GuiScreen field_146598_a;
    private GuiButton field_146596_f;
    private GuiButton field_146597_g;
    private String field_146599_h;
    private boolean field_146600_i;
    
    public GuiShareToLan(final GuiScreen p_i1055_1_) {
        this.field_146599_h = "survival";
        this.field_146598_a = p_i1055_1_;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new DarkButton(101, GuiShareToLan.width / 2 - 155, GuiShareToLan.height - 28, 150, 20, I18n.format("lanServer.start", new Object[0])));
        this.buttonList.add(new DarkButton(102, GuiShareToLan.width / 2 + 5, GuiShareToLan.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(this.field_146597_g = new DarkButton(104, GuiShareToLan.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
        this.buttonList.add(this.field_146596_f = new DarkButton(103, GuiShareToLan.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
        this.func_146595_g();
    }
    
    private void func_146595_g() {
        this.field_146597_g.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + " " + I18n.format("selectWorld.gameMode." + this.field_146599_h, new Object[0]);
        this.field_146596_f.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";
        if (this.field_146600_i) {
            this.field_146596_f.displayString += I18n.format("options.on", new Object[0]);
        }
        else {
            this.field_146596_f.displayString += I18n.format("options.off", new Object[0]);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 102) {
            GuiShareToLan.mc.displayGuiScreen(this.field_146598_a);
        }
        else if (button.id == 104) {
            if (this.field_146599_h.equals("spectator")) {
                this.field_146599_h = "creative";
            }
            else if (this.field_146599_h.equals("creative")) {
                this.field_146599_h = "adventure";
            }
            else if (this.field_146599_h.equals("adventure")) {
                this.field_146599_h = "survival";
            }
            else {
                this.field_146599_h = "spectator";
            }
            this.func_146595_g();
        }
        else if (button.id == 103) {
            this.field_146600_i = !this.field_146600_i;
            this.func_146595_g();
        }
        else if (button.id == 101) {
            GuiShareToLan.mc.displayGuiScreen(null);
            final String var2 = GuiShareToLan.mc.getIntegratedServer().shareToLAN(WorldSettings.GameType.getByName(this.field_146599_h), this.field_146600_i);
            Object var3;
            if (var2 != null) {
                var3 = new ChatComponentTranslation("commands.publish.started", new Object[] { var2 });
            }
            else {
                var3 = new ChatComponentText("commands.publish.failed");
            }
            GuiShareToLan.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)var3);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.title", new Object[0]), GuiShareToLan.width / 2, 50, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("lanServer.otherPlayers", new Object[0]), GuiShareToLan.width / 2, 82, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
