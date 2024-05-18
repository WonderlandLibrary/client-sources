package net.minecraft.src;

import org.lwjgl.input.*;
import java.io.*;

public class GuiCommandBlock extends GuiScreen
{
    private GuiTextField commandTextField;
    private final TileEntityCommandBlock commandBlock;
    private GuiButton doneBtn;
    private GuiButton cancelBtn;
    
    public GuiCommandBlock(final TileEntityCommandBlock par1) {
        this.commandBlock = par1;
    }
    
    @Override
    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("gui.done")));
        this.buttonList.add(this.cancelBtn = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        (this.commandTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 150, 60, 300, 20)).setMaxStringLength(32767);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.commandBlock.getCommand());
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.mc.displayGuiScreen(null);
            }
            else if (par1GuiButton.id == 0) {
                final String var2 = "MC|AdvCdm";
                final ByteArrayOutputStream var3 = new ByteArrayOutputStream();
                final DataOutputStream var4 = new DataOutputStream(var3);
                try {
                    var4.writeInt(this.commandBlock.xCoord);
                    var4.writeInt(this.commandBlock.yCoord);
                    var4.writeInt(this.commandBlock.zCoord);
                    Packet.writeString(this.commandTextField.getText(), var4);
                    this.mc.getNetHandler().addToSendQueue(new Packet250CustomPayload(var2, var3.toByteArray()));
                }
                catch (Exception var5) {
                    var5.printStackTrace();
                }
                this.mc.displayGuiScreen(null);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.commandTextField.textboxKeyTyped(par1, par2);
        this.doneBtn.enabled = (this.commandTextField.getText().trim().length() > 0);
        if (par2 != 28 && par1 != '\r') {
            if (par2 == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        }
        else {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.commandTextField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("advMode.setCommand"), this.width / 2, this.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("advMode.command"), this.width / 2 - 150, 47, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("advMode.nearestPlayer"), this.width / 2 - 150, 97, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("advMode.randomPlayer"), this.width / 2 - 150, 108, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("advMode.allPlayers"), this.width / 2 - 150, 119, 10526880);
        this.commandTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
