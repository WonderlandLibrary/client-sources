package us.dev.direkt.gui.minecraft.override;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.module.internal.core.ChatCommands;
import us.dev.direkt.util.render.OGLRender;

/**
 * @author Foundry
 */
public class GuiDirektChat extends GuiChat {
    private int boxAnimation;

    public GuiDirektChat() {
    }

    public GuiDirektChat(String defaultText) {
        this.defaultInputFieldText = defaultText;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        final String chatPrefix = Direkt.getInstance().getModuleManager().getModule(ChatCommands.class).getPrefix();
        if (this.inputField.getText().startsWith(chatPrefix)) {
            Command commandLookup = null;
            String syntaxString = "", lookupAlias = "";
            if (this.inputField.getCursorPosition() > 1) {
                commandBlock: for (Command command : Direkt.getInstance().getCommandManager().getCommands()) {
                    if (command.getLabel().startsWith(this.inputField.getText().replaceFirst(chatPrefix, ""))) {
                        lookupAlias = chatPrefix + (commandLookup = command).getLabel();
                        break;
                    } else {
                        for (String alias : command.getAliases()) {
                            if (alias.startsWith(this.inputField.getText().replaceFirst(chatPrefix, ""))) {
                                commandLookup = command;
                                lookupAlias = chatPrefix + alias;
                                break commandBlock;
                            }
                        }
                    }
                }
            }

            if (commandLookup != null) {
                syntaxString = lookupAlias + " - " + commandLookup.getFormattedUsage(this.inputField.getText().replaceFirst(chatPrefix, ""), false);
                if (Keyboard.isKeyDown(Wrapper.getGameSettings().keyBindPlayerList.getKeyCode())) {
                    this.inputField.setText(lookupAlias);
                }
            }

            if (boxAnimation < this.width - 2) {
                boxAnimation = Math.min(this.width - 2, boxAnimation + 15);
            }

            syntaxString = (this.inputField.getText().equals(chatPrefix) ? (chatPrefix + " Console engaged - enter a command") : syntaxString);
            this.drawString(this.fontRendererObj, syntaxString, 4, this.height - 12, 0x55FF55);
            OGLRender.drawBorderedRect(1, this.height - 15, boxAnimation, this.height - 2, 1.5f, 0, ClientColors.FADING_GREEN.getColor());
        } else {
            boxAnimation = 10;
        }

        this.inputField.drawTextBox();
        final ITextComponent var4 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (var4 != null && var4.getStyle().getHoverEvent() != null) {
            this.handleComponentHover(var4, mouseX, mouseY);
        }
        for (GuiButton aButtonList : this.buttonList) {
            aButtonList.drawButton(this.mc, mouseX, mouseY);
        }
        for (GuiLabel aLabelList : this.labelList) {
            aLabelList.drawLabel(this.mc, mouseX, mouseY);
        }
    }
}
