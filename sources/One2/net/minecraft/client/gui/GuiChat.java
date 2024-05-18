package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.mod.cmd.Cmd;
import com.klintos.twelve.utils.GuiUtils;

import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;

public class GuiChat
extends GuiScreen {
    private static final Logger logger = LogManager.getLogger();
    private String historyBuffer = "";
    private int sentHistoryCursor = -1;
    private boolean playerNamesFound;
    private boolean waitingOnAutocomplete;
    private int autocompleteIndex;
    private List foundPlayerNames = Lists.newArrayList();
    protected GuiTextField inputField;
    private String defaultInputFieldText = "";
    private static final String __OBFID = "CL_00000682";

    public GuiChat() {
    }

    public GuiChat(String p_i1024_1_) {
        this.defaultInputFieldText = p_i1024_1_;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.waitingOnAutocomplete = false;
        if (keyCode == 15) {
            this.autocompletePlayerNames();
        } else {
            this.playerNamesFound = false;
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        } else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.getSentHistory(-1);
            } else if (keyCode == 208) {
                this.getSentHistory(1);
            } else if (keyCode == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            } else if (keyCode == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(- this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            } else {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        } else {
            String var3 = this.inputField.getText().trim();
            if (var3.length() > 0) {
                this.func_175275_f(var3);
            }
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0) {
            if (var1 > 1) {
                var1 = 1;
            }
            if (var1 < -1) {
                var1 = -1;
            }
            if (!GuiChat.isShiftKeyDown()) {
                var1 *= 7;
            }
            this.mc.ingameGUI.getChatGUI().scroll(var1);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        IChatComponent var4;
        this.mc.ingameGUI.persistantChatGUI.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && this.func_175276_a(var4 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY()))) {
            return;
        }
        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        this.mc.ingameGUI.persistantChatGUI.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }

    @Override
    protected void func_175274_a(String p_175274_1_, boolean p_175274_2_) {
        if (p_175274_2_) {
            this.inputField.setText(p_175274_1_);
        } else {
            this.inputField.writeText(p_175274_1_);
        }
    }

    public void autocompletePlayerNames()
    {
        String var3;

        if (this.playerNamesFound)
        {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.autocompleteIndex >= this.foundPlayerNames.size())
            {
                this.autocompleteIndex = 0;
            }
        }
        else
        {
            int var1 = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.foundPlayerNames.clear();
            this.autocompleteIndex = 0;
            String var2 = this.inputField.getText().substring(var1).toLowerCase();
            var3 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.sendAutocompleteRequest(var3, var2);

            if (this.foundPlayerNames.isEmpty())
            {
                return;
            }

            this.playerNamesFound = true;
            this.inputField.deleteFromCursor(var1 - this.inputField.getCursorPosition());
        }

        if (this.foundPlayerNames.size() > 1)
        {
            StringBuilder var4 = new StringBuilder();

            for (Iterator var5 = this.foundPlayerNames.iterator(); var5.hasNext(); var4.append(var3))
            {
                var3 = (String)var5.next();

                if (var4.length() > 0)
                {
                    var4.append(", ");
                }
            }

            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(var4.toString()), 1);
        }

        this.inputField.writeText((String)this.foundPlayerNames.get(this.autocompleteIndex++));
    }


    private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_) {
        if (p_146405_1_.length() >= 1) {
            BlockPos var3 = null;
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                var3 = this.mc.objectMouseOver.func_178782_a();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_, var3));
            this.waitingOnAutocomplete = true;
        }
    }

    public void getSentHistory(int p_146402_1_) {
        int var2 = this.sentHistoryCursor + p_146402_1_;
        int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        if ((var2 = MathHelper.clamp_int(var2, 0, var3)) != this.sentHistoryCursor) {
            if (var2 == var3) {
                this.sentHistoryCursor = var3;
                this.inputField.setText(this.historyBuffer);
            } else {
                if (this.sentHistoryCursor == var3) {
                    this.historyBuffer = this.inputField.getText();
                }
                this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
                this.sentHistoryCursor = var2;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.mc.ingameGUI.persistantChatGUI.drag) {
            this.mc.ingameGUI.persistantChatGUI.drag(mouseX, mouseY);
        }
        GuiChat.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        if (!Twelve.getInstance().ghost && this.inputField.getText().startsWith(Twelve.getInstance().getCmdHandler().getPrefix())) {
            String string = "";
            for (Cmd cmd : Twelve.getInstance().getCmdHandler().getCmds()) {
                if (!cmd.getCmd().startsWith(this.inputField.getText().substring(1, this.inputField.getText().length()))) continue;
                string = String.valueOf(Twelve.getInstance().getCmdHandler().getPrefix()) + cmd.getCmd() + " - " + cmd.getDescription();
            }
            string = this.inputField.getText().equals(Twelve.getInstance().getCmdHandler().getPrefix()) ? String.valueOf(Twelve.getInstance().getCmdHandler().getPrefix()) + " Welcome to ONE2's console." : string;
            GuiUtils.drawBorderedRect(1, this.height - 15, this.width - 2, this.height - 2, -43691, 0);
            this.drawString(this.fontRendererObj, string, 4.0f, this.height - 12, -43691);
        }
        this.inputField.drawTextBox();
        IChatComponent var4 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (var4 != null && var4.getChatStyle().getChatHoverEvent() != null) {
            this.func_175272_a(var4, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void onAutocompleteResponse(String[] p_146406_1_) {
        if (this.waitingOnAutocomplete) {
            this.playerNamesFound = false;
            this.foundPlayerNames.clear();
            String[] var2 = p_146406_1_;
            int var3 = p_146406_1_.length;
            int var4 = 0;
            while (var4 < var3) {
                String var5 = var2[var4];
                if (var5.length() > 0) {
                    this.foundPlayerNames.add(var5);
                }
                ++var4;
            }
            String var6 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
            String var7 = StringUtils.getCommonPrefix((String[])p_146406_1_);
            if (var7.length() > 0 && !var6.equalsIgnoreCase(var7)) {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(var7);
            } else if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = true;
                this.autocompletePlayerNames();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

