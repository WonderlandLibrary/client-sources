/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.SlotGui;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.gui.GuiShaders;
import net.optifine.util.ResUtils;

class GuiSlotShaders
extends SlotGui {
    private ArrayList shaderslist;
    private int selectedIndex;
    private long lastClicked = Long.MIN_VALUE;
    private long lastClickedCached = 0L;
    final GuiShaders shadersGui;

    public GuiSlotShaders(GuiShaders guiShaders, int n, int n2, int n3, int n4, int n5) {
        super(guiShaders.getMc(), n, n2, n3, n4, n5);
        this.shadersGui = guiShaders;
        this.updateList();
        this.yo = 0.0;
        int n6 = this.selectedIndex * n5;
        int n7 = (n4 - n3) / 2;
        if (n6 > n7) {
            this.scroll(n6 - n7);
        }
    }

    @Override
    public int getRowWidth() {
        return this.width - 20;
    }

    public void updateList() {
        this.shaderslist = Shaders.listOfShaders();
        this.selectedIndex = 0;
        int n = this.shaderslist.size();
        for (int i = 0; i < n; ++i) {
            if (!((String)this.shaderslist.get(i)).equals(Shaders.currentShaderName)) continue;
            this.selectedIndex = i;
            break;
        }
    }

    @Override
    protected int getItemCount() {
        return this.shaderslist.size();
    }

    @Override
    protected boolean selectItem(int n, int n2, double d, double d2) {
        if (n == this.selectedIndex && this.lastClicked == this.lastClickedCached) {
            return true;
        }
        String string = (String)this.shaderslist.get(n);
        IShaderPack iShaderPack = Shaders.getShaderPack(string);
        if (!this.checkCompatible(iShaderPack, n)) {
            return true;
        }
        this.selectIndex(n);
        return false;
    }

    private void selectIndex(int n) {
        this.selectedIndex = n;
        this.lastClickedCached = this.lastClicked;
        Shaders.setShaderPack((String)this.shaderslist.get(n));
        Shaders.uninit();
        this.shadersGui.updateButtons();
    }

    private boolean checkCompatible(IShaderPack iShaderPack, int n) {
        if (iShaderPack == null) {
            return false;
        }
        InputStream inputStream = iShaderPack.getResourceAsStream("/shaders/shaders.properties");
        Properties properties = ResUtils.readProperties(inputStream, "Shaders");
        if (properties == null) {
            return false;
        }
        String string = "version.1.16.5";
        String string2 = properties.getProperty(string);
        if (string2 == null) {
            return false;
        }
        String string3 = "G8";
        int n2 = Config.compareRelease(string3, string2 = string2.trim());
        if (n2 >= 0) {
            return false;
        }
        String string4 = ("HD_U_" + string2).replace('_', ' ');
        String string5 = I18n.format("of.message.shaders.nv1", string4);
        String string6 = I18n.format("of.message.shaders.nv2", new Object[0]);
        BooleanConsumer booleanConsumer = arg_0 -> this.lambda$checkCompatible$0(n, arg_0);
        ConfirmScreen confirmScreen = new ConfirmScreen(booleanConsumer, new StringTextComponent(string5), new StringTextComponent(string6));
        this.minecraft.displayGuiScreen(confirmScreen);
        return true;
    }

    @Override
    protected boolean isSelectedItem(int n) {
        return n == this.selectedIndex;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 6;
    }

    @Override
    public int getItemHeight() {
        return this.getItemCount() * 18;
    }

    @Override
    protected void renderBackground() {
    }

    @Override
    protected void renderItem(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, float f) {
        String string = (String)this.shaderslist.get(n);
        if (string.equals("OFF")) {
            string = Lang.get("of.options.shaders.packNone");
        } else if (string.equals("(internal)")) {
            string = Lang.get("of.options.shaders.packDefault");
        }
        this.shadersGui.drawCenteredString(matrixStack, string, this.width / 2, n3 + 1, 0xE0E0E0);
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        return super.mouseScrolled(d, d2, d3 * 3.0);
    }

    private void lambda$checkCompatible$0(int n, boolean bl) {
        if (bl) {
            this.selectIndex(n);
        }
        this.minecraft.displayGuiScreen(this.shadersGui);
    }
}

