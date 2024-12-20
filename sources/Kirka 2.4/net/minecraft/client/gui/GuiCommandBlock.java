/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.gui;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock
extends GuiScreen {
    private static final Logger field_146488_a = LogManager.getLogger();
    private GuiTextField commandTextField;
    private GuiTextField field_146486_g;
    private final CommandBlockLogic localCommandBlock;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    private GuiButton field_175390_s;
    private boolean field_175389_t;
    private static final String __OBFID = "CL_00000748";

    public GuiCommandBlock(CommandBlockLogic p_i45032_1_) {
        this.localCommandBlock = p_i45032_1_;
    }

    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.doneBtn = new GuiButton(0, width / 2 - 4 - 150, height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0]));
        this.buttonList.add(this.doneBtn);
        this.cancelBtn = new GuiButton(1, width / 2 + 4, height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0]));
        this.buttonList.add(this.cancelBtn);
        this.field_175390_s = new GuiButton(4, width / 2 + 150 - 20, 150, 20, 20, "O");
        this.buttonList.add(this.field_175390_s);
        this.commandTextField = new GuiTextField(2, this.fontRendererObj, width / 2 - 150, 50, 300, 20);
        this.commandTextField.setMaxStringLength(32767);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.localCommandBlock.getCustomName());
        this.field_146486_g = new GuiTextField(3, this.fontRendererObj, width / 2 - 150, 150, 276, 20);
        this.field_146486_g.setMaxStringLength(32767);
        this.field_146486_g.setEnabled(false);
        this.field_146486_g.setText("-");
        this.field_175389_t = this.localCommandBlock.func_175571_m();
        this.func_175388_a();
        this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.localCommandBlock.func_175573_a(this.field_175389_t);
                this.mc.displayGuiScreen(null);
            } else if (button.id == 0) {
                PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
                var2.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(var2);
                var2.writeString(this.commandTextField.getText());
                var2.writeBoolean(this.localCommandBlock.func_175571_m());
                Minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));
                if (!this.localCommandBlock.func_175571_m()) {
                    this.localCommandBlock.func_145750_b(null);
                }
                this.mc.displayGuiScreen(null);
            } else if (button.id == 4) {
                this.localCommandBlock.func_175573_a(!this.localCommandBlock.func_175571_m());
                this.func_175388_a();
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.commandTextField.textboxKeyTyped(typedChar, keyCode);
        this.field_146486_g.textboxKeyTyped(typedChar, keyCode);
        boolean bl = this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
        if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        } else {
            this.actionPerformed(this.doneBtn);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146486_g.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), width / 2, 20, 16777215);
        GuiCommandBlock.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), width / 2 - 150, 37, 10526880);
        this.commandTextField.drawTextBox();
        int var4 = 75;
        int var5 = 0;
        FontRenderer var10001 = this.fontRendererObj;
        String var10002 = I18n.format("advMode.nearestPlayer", new Object[0]);
        int var10003 = width / 2 - 150;
        int var7 = var5 + 1;
        GuiCommandBlock.drawString(var10001, var10002, var10003, var4 + var5 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        GuiCommandBlock.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        GuiCommandBlock.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        GuiCommandBlock.drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        GuiCommandBlock.drawString(this.fontRendererObj, "", width / 2 - 150, var4 + var7++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        if (this.field_146486_g.getText().length() > 0) {
            int var6 = var4 + var7 * this.fontRendererObj.FONT_HEIGHT + 16;
            GuiCommandBlock.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), width / 2 - 150, var6, 10526880);
            this.field_146486_g.drawTextBox();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void func_175388_a() {
        if (this.localCommandBlock.func_175571_m()) {
            this.field_175390_s.displayString = "O";
            if (this.localCommandBlock.getLastOutput() != null) {
                this.field_146486_g.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
            }
        } else {
            this.field_175390_s.displayString = "X";
            this.field_146486_g.setText("-");
        }
    }
}

