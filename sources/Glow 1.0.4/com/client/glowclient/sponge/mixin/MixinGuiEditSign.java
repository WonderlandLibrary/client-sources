package com.client.glowclient.sponge.mixin;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.tileentity.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.util.*;
import java.io.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.text.*;

@SideOnly(Side.CLIENT)
@Mixin({ GuiEditSign.class })
public abstract class MixinGuiEditSign extends GuiScreen
{
    @Shadow
    private TileEntitySign field_146848_f;
    @Shadow
    private int field_146849_g;
    @Shadow
    private int field_146851_h;
    @Shadow
    private GuiButton field_146852_i;
    
    public MixinGuiEditSign() {
        super();
    }
    
    @Overwrite
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 200) {
            this.editLine = (this.editLine - 1 & 0x3);
        }
        if (n == 208 || n == 28 || n == 156) {
            this.editLine = (this.editLine + 1 & 0x3);
        }
        String s;
        if (HookTranslator.v4) {
            s = this.tileSign.signText[this.editLine].getFormattedText();
        }
        else {
            s = this.tileSign.signText[this.editLine].getUnformattedText();
        }
        if (n == 14 && s.length() > 0) {
            final String unformattedText = this.tileSign.signText[this.editLine].getUnformattedText();
            s = unformattedText.substring(0, unformattedText.length() - 1).replace("§r", "");
        }
        if (GuiScreen.isKeyComboCtrlV(n)) {
            s += GuiScreen.getClipboardString();
        }
        if (ChatAllowedCharacters.isAllowedCharacter(c) && this.fontRenderer.getStringWidth(s + c) <= HookTranslator.m14()) {
            s = (s + c).replace("§r", "");
        }
        this.tileSign.signText[this.editLine] = (ITextComponent)new TextComponentString(s);
        if (n == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiEditSign;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V") }, cancellable = true)
    public void inDrawScreen(final int n, final int n2, final float n3, final CallbackInfo callbackInfo) {
        if (HookTranslator.v4) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(HookTranslator.m15());
            final int n4 = 73;
            final int n5 = 300;
            Gui.drawModalRectWithCustomSizedTexture(2, 2, 0.0f, 0.0f, n4, n5, (float)n4, (float)n5);
        }
    }
}
