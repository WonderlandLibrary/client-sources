package ClassSub;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class Class189 extends GuiScreen
{
    private final Class7 manager;
    private GuiTextField nameField;
    private Class35 pwField;
    private String status;
    
    
    public Class189(final Class7 manager) {
        this.status = EnumChatFormatting.GRAY + "Waiting...";
        this.manager = manager;
    }
    
    public void actionPerformed(final GuiButton guiButton) {
        switch (guiButton.id) {
            case 1: {
                this.mc.displayGuiScreen((GuiScreen)this.manager);
                break;
            }
            case 0: {
                this.manager.selectedAlt.setMask(this.nameField.getText());
                this.manager.selectedAlt.setPassword(this.pwField.getText());
                this.status = "Edited!";
                break;
            }
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        Class246.drawRect(0.0f, 0.0f, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0);
        this.drawCenteredString(this.fontRendererObj, "Edit Alt", this.width / 2, 10, -1);
        this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 20, -1);
        this.nameField.drawTextBox();
        this.pwField.drawTextBox();
        if (this.nameField.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "New name", this.width / 2 - 96, 66, -7829368);
        }
        if (this.pwField.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "New password", this.width / 2 - 96, 106, -7829368);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Edit"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Cancel"));
        this.nameField = new GuiTextField(99997, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.pwField = new Class35(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
    }
    
    protected void keyTyped(final char c, final int n) {
        this.nameField.textboxKeyTyped(c, n);
        this.pwField.textboxKeyTyped(c, n);
        if (c == '\t' && (this.nameField.isFocused() || this.pwField.isFocused())) {
            this.nameField.setFocused(!this.nameField.isFocused());
            this.pwField.setFocused(!this.pwField.isFocused());
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) {
        try {
            super.mouseClicked(n, n2, n3);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        this.nameField.mouseClicked(n, n2, n3);
        this.pwField.mouseClicked(n, n2, n3);
    }
}
