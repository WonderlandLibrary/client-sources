package me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(value = { GuiTextField.class }, priority = 9999)
public interface InterfaceMixinGuiTextField
{
    @Accessor("isEnabled")
    boolean getIsEnabled();
    
    @Accessor("enabledColor")
    int getEnabledColor();
    
    @Accessor("disabledColor")
    int getDisabledColor();
    
    @Accessor("cursorPosition")
    int getCursorPosition();
    
    @Accessor("lineScrollOffset")
    int getLineScrollOffset();
    
    @Accessor("isFocused")
    boolean getIsFocused();
    
    @Accessor("cursorCounter")
    int getCursorCounter();
    
    @Accessor("enableBackgroundDrawing")
    boolean getEnableBackgroundDrawing();
    
    @Accessor("fontRenderer")
    FontRenderer getFontRenderer();
    
    @Accessor("text")
    String getText();
    
    @Accessor("selectionEnd")
    int getSelectionEnd();
    
    @Accessor("canLoseFocus")
    boolean getCanLoseFocus();
    
    @Invoker("drawSelectionBox")
    void invokeDrawSelectionBox(final int p0, final int p1, final int p2, final int p3);
}
