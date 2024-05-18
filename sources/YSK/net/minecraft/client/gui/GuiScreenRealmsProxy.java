package net.minecraft.client.gui;

import net.minecraft.realms.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;
import java.io.*;

public class GuiScreenRealmsProxy extends GuiScreen
{
    private RealmsScreen field_154330_a;
    
    public void func_154324_i() {
        super.buttonList.clear();
    }
    
    public void func_154328_b(final RealmsButton realmsButton) {
        super.buttonList.remove(realmsButton);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_154330_a.render(n, n2, n3);
    }
    
    public RealmsScreen func_154321_a() {
        return this.field_154330_a;
    }
    
    @Override
    public void updateScreen() {
        this.field_154330_a.tick();
        super.updateScreen();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
    }
    
    public int func_154329_h() {
        return this.fontRendererObj.FONT_HEIGHT;
    }
    
    public List<RealmsButton> func_154320_j() {
        final ArrayList arrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(super.buttonList.size());
        final Iterator<GuiButton> iterator = super.buttonList.iterator();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayListWithExpectedSize.add(((GuiButtonRealmsProxy)iterator.next()).getRealmsButton());
        }
        return (List<RealmsButton>)arrayListWithExpectedSize;
    }
    
    public GuiScreenRealmsProxy(final RealmsScreen field_154330_a) {
        this.field_154330_a = field_154330_a;
        super.buttonList = Collections.synchronizedList((List<GuiButton>)Lists.newArrayList());
    }
    
    @Override
    public void initGui() {
        this.field_154330_a.init();
        super.initGui();
    }
    
    public void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        super.renderToolTip(itemStack, n, n2);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return super.doesGuiPauseGame();
    }
    
    public void func_154322_b(final String s, final int n, final int n2, final int n3) {
        super.drawString(this.fontRendererObj, s, n, n2, n3);
    }
    
    public void func_154327_a(final RealmsButton realmsButton) {
        super.buttonList.add(realmsButton.getProxy());
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
        this.field_154330_a.mouseReleased(n, n2, n3);
    }
    
    public void func_154319_c(final String s, final int n, final int n2, final int n3) {
        this.fontRendererObj.drawStringWithShadow(s, n, n2, n3);
    }
    
    public void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        this.field_154330_a.mouseDragged(n, n2, n3, n4);
    }
    
    public final void actionPerformed(final GuiButton guiButton) throws IOException {
        this.field_154330_a.buttonClicked(((GuiButtonRealmsProxy)guiButton).getRealmsButton());
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        this.field_154330_a.confirmResult(b, n);
    }
    
    @Override
    public void handleKeyboardInput() throws IOException {
        this.field_154330_a.keyboardEvent();
        super.handleKeyboardInput();
    }
    
    public void drawHoveringText(final List<String> list, final int n, final int n2) {
        super.drawHoveringText(list, n, n2);
    }
    
    public void keyTyped(final char c, final int n) throws IOException {
        this.field_154330_a.keyPressed(c, n);
    }
    
    public int func_154326_c(final String s) {
        return this.fontRendererObj.getStringWidth(s);
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.field_154330_a.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        this.field_154330_a.mouseEvent();
        super.handleMouseInput();
    }
    
    public void func_154325_a(final String s, final int n, final int n2, final int n3) {
        super.drawCenteredString(this.fontRendererObj, s, n, n2, n3);
    }
    
    public void drawGradientRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        super.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    @Override
    public void drawTexturedModalRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_154330_a.blit(n, n2, n3, n4, n5, n6);
        super.drawTexturedModalRect(n, n2, n3, n4, n5, n6);
    }
    
    public void drawCreativeTabHoveringText(final String s, final int n, final int n2) {
        super.drawCreativeTabHoveringText(s, n, n2);
    }
    
    @Override
    public void onGuiClosed() {
        this.field_154330_a.removed();
        super.onGuiClosed();
    }
    
    public List<String> func_154323_a(final String s, final int n) {
        return (List<String>)this.fontRendererObj.listFormattedStringToWidth(s, n);
    }
    
    @Override
    public void drawWorldBackground(final int n) {
        super.drawWorldBackground(n);
    }
}
