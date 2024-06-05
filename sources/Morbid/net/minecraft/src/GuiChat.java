package net.minecraft.src;

import java.net.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import java.util.*;
import java.io.*;

public class GuiChat extends GuiScreen
{
    private String field_73898_b;
    private int sentHistoryCursor;
    private boolean field_73897_d;
    private boolean field_73905_m;
    private int field_73903_n;
    private List field_73904_o;
    private URI clickedURI;
    protected GuiTextField inputField;
    private String defaultInputFieldText;
    
    public GuiChat() {
        this.field_73898_b = "";
        this.sentHistoryCursor = -1;
        this.field_73897_d = false;
        this.field_73905_m = false;
        this.field_73903_n = 0;
        this.field_73904_o = new ArrayList();
        this.clickedURI = null;
        this.defaultInputFieldText = "";
    }
    
    public GuiChat(final String par1Str) {
        this.field_73898_b = "";
        this.sentHistoryCursor = -1;
        this.field_73897_d = false;
        this.field_73905_m = false;
        this.field_73903_n = 0;
        this.field_73904_o = new ArrayList();
        this.clickedURI = null;
        this.defaultInputFieldText = "";
        this.defaultInputFieldText = par1Str;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GuiTextField(this.fontRenderer, 4, this.height - 12, this.width - 4, 12)).setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }
    
    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.field_73905_m = false;
        if (par2 == 15) {
            this.completePlayerName();
        }
        else {
            this.field_73897_d = false;
        }
        if (par2 == 1) {
            this.mc.displayGuiScreen(null);
        }
        else if (par2 == 28) {
            final String var3 = this.inputField.getText().trim();
            if (var3.length() > 0) {
                this.mc.ingameGUI.getChatGUI().addToSentMessages(var3);
                if (!this.mc.handleClientCommand(var3)) {
                    Minecraft.thePlayer.sendChatMessage(var3);
                }
            }
            this.mc.displayGuiScreen(null);
        }
        else if (par2 == 200) {
            this.getSentHistory(-1);
        }
        else if (par2 == 208) {
            this.getSentHistory(1);
        }
        else if (par2 == 201) {
            this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().func_96127_i() - 1);
        }
        else if (par2 == 209) {
            this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().func_96127_i() + 1);
        }
        else {
            this.inputField.textboxKeyTyped(par1, par2);
        }
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0) {
            if (var1 > 1) {
                var1 = 1;
            }
            if (var1 < -1) {
                var1 = -1;
            }
            if (!GuiScreen.isShiftKeyDown()) {
                var1 *= 7;
            }
            this.mc.ingameGUI.getChatGUI().scroll(var1);
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (par3 == 0 && this.mc.gameSettings.chatLinks) {
            final ChatClickData var4 = this.mc.ingameGUI.getChatGUI().func_73766_a(Mouse.getX(), Mouse.getY());
            if (var4 != null) {
                final URI var5 = var4.getURI();
                if (var5 != null) {
                    if (this.mc.gameSettings.chatLinksPrompt) {
                        this.clickedURI = var5;
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var4.getClickedUrl(), 0, false));
                    }
                    else {
                        this.func_73896_a(var5);
                    }
                    return;
                }
            }
        }
        this.inputField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        if (par2 == 0) {
            if (par1) {
                this.func_73896_a(this.clickedURI);
            }
            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void func_73896_a(final URI par1URI) {
        try {
            final Class var2 = Class.forName("java.awt.Desktop");
            final Object var3 = var2.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
            var2.getMethod("browse", URI.class).invoke(var3, par1URI);
        }
        catch (Throwable var4) {
            var4.printStackTrace();
        }
    }
    
    public void completePlayerName() {
        if (this.field_73897_d) {
            this.inputField.deleteFromCursor(this.inputField.func_73798_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            if (this.field_73903_n >= this.field_73904_o.size()) {
                this.field_73903_n = 0;
            }
        }
        else {
            final int var2 = this.inputField.func_73798_a(-1, this.inputField.getCursorPosition(), false);
            this.field_73904_o.clear();
            this.field_73903_n = 0;
            final String var3 = this.inputField.getText().substring(var2).toLowerCase();
            final String var4 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.func_73893_a(var4, var3);
            if (this.field_73904_o.isEmpty()) {
                return;
            }
            this.field_73897_d = true;
            this.inputField.deleteFromCursor(var2 - this.inputField.getCursorPosition());
        }
        if (this.field_73904_o.size() > 1) {
            final StringBuilder var5 = new StringBuilder();
            for (final String var4 : this.field_73904_o) {
                if (var5.length() > 0) {
                    var5.append(", ");
                }
                var5.append(var4);
            }
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(var5.toString(), 1);
        }
        this.inputField.writeText(this.field_73904_o.get(this.field_73903_n++));
    }
    
    private void func_73893_a(final String par1Str, final String par2Str) {
        if (par1Str.length() >= 1) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new Packet203AutoComplete(par1Str));
            this.field_73905_m = true;
        }
    }
    
    public void getSentHistory(final int par1) {
        int var2 = this.sentHistoryCursor + par1;
        final int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        if (var2 < 0) {
            var2 = 0;
        }
        if (var2 > var3) {
            var2 = var3;
        }
        if (var2 != this.sentHistoryCursor) {
            if (var2 == var3) {
                this.sentHistoryCursor = var3;
                this.inputField.setText(this.field_73898_b);
            }
            else {
                if (this.sentHistoryCursor == var3) {
                    this.field_73898_b = this.inputField.getText();
                }
                this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
                this.sentHistoryCursor = var2;
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        Gui.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
    
    public void func_73894_a(final String[] par1ArrayOfStr) {
        if (this.field_73905_m) {
            this.field_73904_o.clear();
            for (final String var5 : par1ArrayOfStr) {
                if (var5.length() > 0) {
                    this.field_73904_o.add(var5);
                }
            }
            if (this.field_73904_o.size() > 0) {
                this.field_73897_d = true;
                this.completePlayerName();
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
