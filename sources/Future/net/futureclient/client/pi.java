package net.futureclient.client;

import java.util.Iterator;
import java.util.List;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class pi extends GuiScreen
{
    private pi.lI E;
    private Minecraft A;
    private final aj j;
    private String K;
    private GuiTextField M;
    private static final ResourceLocation d;
    private int a;
    private boolean D;
    private GuiScreen k;
    
    public pi(final GuiScreen k) {
        super();
        this.j = pg.M().M();
        this.A = Minecraft.getMinecraft();
        this.k = k;
    }
    
    static {
        d = new ResourceLocation("textures/gui/server_selection.png");
    }
    
    public static FontRenderer B(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static FontRenderer C(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static FontRenderer c(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static FontRenderer b(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static FontRenderer e(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static FontRenderer i(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static FontRenderer g(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static ResourceLocation M() {
        return pi.d;
    }
    
    public static FontRenderer M(final pi pi) {
        return pi.fontRenderer;
    }
    
    public static aj M(final pi pi) {
        return pi.j;
    }
    
    public static Minecraft M(final pi pi) {
        return pi.A;
    }
    
    public static pi.lI M(final pi pi) {
        return pi.E;
    }
    
    public static FontRenderer h(final pi pi) {
        return pi.fontRenderer;
    }
    
    public void func_73876_c() {
        this.M.updateCursorCounter();
        this.j.e();
    }
    
    public void func_146274_d() throws IOException {
        super.handleMouseInput();
        this.E.handleMouseInput();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        pi pi;
        if (this.E.D < 0) {
            this.buttonList.get(1).enabled = false;
            this.buttonList.get(3).enabled = false;
            this.buttonList.get(6).enabled = false;
            pi = this;
        }
        else {
            this.buttonList.get(1).enabled = !this.j.M;
            this.buttonList.get(3).enabled = true;
            this.buttonList.get(6).enabled = true;
            pi = this;
        }
        ((GuiButton)pi.buttonList.get(4)).enabled = !this.j.M().isEmpty();
        if (!this.j.M().equals(this.A.getSession().getUsername()) && this.E.D != -1) {
            final ci ci;
            if ((ci = this.j.M().get(this.E.D)).M().equals(net.futureclient.client.ci.Wh.a)) {
                ci.M(net.futureclient.client.ci.Wh.d);
            }
            ci.e(this.A.getSession().getUsername());
            this.j.M(this.A.getSession().getUsername());
        }
        this.E.drawScreen(n, n2, n3);
        this.drawCenteredString(this.A.fontRenderer, this.K, this.width / 2, 2 + this.A.fontRenderer.FONT_HEIGHT * 3, 16777215);
        this.j.M();
        final FontRenderer fontRenderer = this.A.fontRenderer;
        final String format = String.format("%sUsername: %s%s", ChatFormatting.GRAY, ChatFormatting.WHITE, this.A.getSession().getUsername());
        final int font_HEIGHT = this.A.fontRenderer.FONT_HEIGHT;
        final int n4 = 2;
        this.drawString(fontRenderer, format, n4, n4 + font_HEIGHT * n4, 16777215);
        this.M.drawTextBox();
        if (!this.M.isFocused()) {
            this.M.setText("");
            this.drawString(this.A.fontRenderer, ChatFormatting.GRAY + "Search", this.D ? (this.a + 4) : (this.width - this.fontRenderer.getStringWidth("Search") - 80), 16, 16777215);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (!this.M.isFocused()) {
            final int d;
            if ((d = this.E.D) < 0) {
                super.keyTyped(c, n);
                return;
            }
            if (n == 200) {
                if (d > 0) {
                    if (isShiftKeyDown()) {
                        final ci ci = this.j.M().get(d);
                        final List m = this.j.M();
                        final List i = this.j.M();
                        final int n2 = d;
                        m.set(n2, i.get(n2 - 1));
                        this.j.M().set(d - 1, ci);
                    }
                    --this.E.D;
                    this.E.scrollBy(-this.E.getSlotHeight());
                }
            }
            else if (n == 208) {
                if (d < this.E.getSize() - 1) {
                    if (isShiftKeyDown()) {
                        final ci ci2 = this.j.M().get(d);
                        final List j = this.j.M();
                        final List k = this.j.M();
                        final int n3 = d;
                        j.set(n3, k.get(n3 + 1));
                        this.j.M().set(d + 1, ci2);
                    }
                    this.E.D = d + 1;
                    this.E.scrollBy(this.E.getSlotHeight());
                }
            }
            else {
                if (n != 28 && n != 156) {
                    super.keyTyped(c, n);
                    return;
                }
                this.actionPerformed((GuiButton)this.buttonList.get(1));
            }
        }
        else {
            super.keyTyped(c, n);
            this.M.textboxKeyTyped(c, n);
            final boolean b = false;
            boolean b2 = false;
            Label_0460: {
                if (this.M.getText().length() > 0) {
                    int d2 = 0;
                    final Iterator<ci> iterator2;
                    Iterator<ci> iterator = iterator2 = this.j.M().iterator();
                    while (iterator.hasNext()) {
                        final ci ci3;
                        if ((ci3 = iterator2.next()).i().toLowerCase().contains(this.M.getText().toLowerCase()) || ci3.B().toLowerCase().contains(this.M.getText().toLowerCase())) {
                            this.E.D = d2;
                            this.E.scrollBy(-this.E.getAmountScrolled() + 36 * this.E.D);
                            b2 = true;
                            break Label_0460;
                        }
                        ++d2;
                        iterator = iterator2;
                    }
                }
                b2 = b;
            }
            if (!b2) {
                this.E.D = -1;
                this.E.scrollBy(-this.E.getAmountScrolled() + 36 * this.E.D);
            }
        }
    }
    
    public void func_73866_w_() {
        final String s = "\u0003T!X7Y6\u0017\u000fV,V%R0\u0017gD\u0019\u00121\u00121\u00121j";
        this.buttonList.add(new GuiButton(0, this.width / 2 - 154, this.height - 48, 73, 20, "Add"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 76, this.height - 48, 73, 20, "Login"));
        final List buttonList = this.buttonList;
        final int width = this.width;
        final int n = 2;
        buttonList.add(new GuiButton(n, width / n, this.height - 48, 73, 20, "Direct"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 78, this.height - 48, 73, 20, "Delete"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 154, this.height - 26, 73, 20, "Random"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 76, this.height - 26, 149, 20, "Back"));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 78, this.height - 26, 73, 20, "Edit"));
        this.K = String.format(aF.M(s), ChatFormatting.GRAY, ChatFormatting.WHITE, this.j.M().size(), ChatFormatting.GRAY);
        this.a = this.width / 2 + this.fontRenderer.getStringWidth(this.K) / 2 + 8;
        this.D = (this.width - 120 < this.a);
        (this.M = new GuiTextField(7, this.fontRenderer, this.D ? this.a : (this.width - 120), 10, this.D ? (this.width - this.a - 10) : 110, 20)).setMaxStringLength(100);
        this.E = new pi.lI(this);
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0:
                this.A.displayGuiScreen((GuiScreen)new FG((GuiScreen)this, 0, this.E.D));
            case 1:
                this.j.M(this.E.D);
            case 2:
                this.A.displayGuiScreen((GuiScreen)new FG((GuiScreen)this, 3, -1));
            case 3:
                this.A.displayGuiScreen((GuiScreen)new FG((GuiScreen)this, 1, this.E.D));
            case 4: {
                final int d = (int)(Math.random() * this.j.M().size());
                this.E.D = d;
                this.j.M(d);
                this.E.scrollBy(-this.E.getAmountScrolled() + 36 * this.E.D);
            }
            case 5:
                this.A.displayGuiScreen(this.k);
            case 6:
                this.A.displayGuiScreen((GuiScreen)new FG((GuiScreen)this, 2, this.E.D));
                break;
        }
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.M.mouseClicked(n, n2, n3);
    }
}
