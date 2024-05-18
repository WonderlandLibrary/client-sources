package net.minecraft.realms;

import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.entity.*;
import com.mojang.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;

public class RealmsScreen
{
    public int width;
    public static final int SKIN_TEX_HEIGHT;
    public static final int SKIN_HAT_WIDTH;
    protected Minecraft minecraft;
    public static final int SKIN_HAT_U;
    public static final int SKIN_TEX_WIDTH;
    public static final int SKIN_HEAD_V;
    public static final int SKIN_HAT_V;
    public static final int SKIN_HEAD_HEIGHT;
    private GuiScreenRealmsProxy proxy;
    public static final int SKIN_HEAD_WIDTH;
    public static final int SKIN_HAT_HEIGHT;
    public int height;
    public static final int SKIN_HEAD_U;
    
    public void render(final int n, final int n2, final float n3) {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < this.proxy.func_154320_j().size()) {
            this.proxy.func_154320_j().get(i).render(n, n2);
            ++i;
        }
    }
    
    public GuiScreenRealmsProxy getProxy() {
        return this.proxy;
    }
    
    public static RealmsButton newButton(final int n, final int n2, final int n3, final String s) {
        return new RealmsButton(n, n2, n3, s);
    }
    
    public void removed() {
    }
    
    public boolean isPauseScreen() {
        return this.proxy.doesGuiPauseGame();
    }
    
    public void drawCenteredString(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154325_a(s, n, n2, n3);
    }
    
    public void buttonsAdd(final RealmsButton realmsButton) {
        this.proxy.func_154327_a(realmsButton);
    }
    
    public int width() {
        return this.proxy.width;
    }
    
    public void renderTooltip(final ItemStack itemStack, final int n, final int n2) {
        this.proxy.renderToolTip(itemStack, n, n2);
    }
    
    public void blit(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawTexturedModalRect(n, n2, n3, n4, n5, n6);
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    public static String getLocalizedString(final String s) {
        return I18n.format(s, new Object["".length()]);
    }
    
    public static String getLocalizedString(final String s, final Object... array) {
        return I18n.format(s, array);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
    
    public void keyPressed(final char c, final int n) {
    }
    
    public void mouseDragged(final int n, final int n2, final int n3, final long n4) {
    }
    
    public void fontDrawShadow(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154319_c(s, n, n2, n3);
    }
    
    public void buttonClicked(final RealmsButton realmsButton) {
    }
    
    public void renderTooltip(final String s, final int n, final int n2) {
        this.proxy.drawCreativeTabHoveringText(s, n, n2);
    }
    
    public RealmsEditBox newEditBox(final int n, final int n2, final int n3, final int n4, final int n5) {
        return new RealmsEditBox(n, n2, n3, n4, n5);
    }
    
    public int fontWidth(final String s) {
        return this.proxy.func_154326_c(s);
    }
    
    public static void blit(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8) {
        Gui.drawModalRectWithCustomSizedTexture(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public void keyboardEvent() {
    }
    
    public void renderTooltip(final List<String> list, final int n, final int n2) {
        this.proxy.drawHoveringText(list, n, n2);
    }
    
    public List<RealmsButton> buttons() {
        return this.proxy.func_154320_j();
    }
    
    public int fontLineHeight() {
        return this.proxy.func_154329_h();
    }
    
    public void drawString(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154322_b(s, n, n2, n3);
    }
    
    public static void blit(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        Gui.drawScaledCustomSizeModalRect(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }
    
    public RealmsAnvilLevelStorageSource getLevelStorageSource() {
        return new RealmsAnvilLevelStorageSource(Minecraft.getMinecraft().getSaveLoader());
    }
    
    public static void bindFace(final String s, final String s2) {
        ResourceLocation resourceLocation = AbstractClientPlayer.getLocationSkin(s2);
        if (resourceLocation == null) {
            resourceLocation = DefaultPlayerSkin.getDefaultSkin(UUIDTypeAdapter.fromString(s));
        }
        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, s2);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
    }
    
    public void buttonsClear() {
        this.proxy.func_154324_i();
    }
    
    public int height() {
        return this.proxy.height;
    }
    
    public void confirmResult(final boolean b, final int n) {
    }
    
    public void init() {
    }
    
    public void buttonsRemove(final RealmsButton realmsButton) {
        this.proxy.func_154328_b(realmsButton);
    }
    
    public RealmsScreen() {
        this.proxy = new GuiScreenRealmsProxy(this);
    }
    
    public List<String> fontSplit(final String s, final int n) {
        return this.proxy.func_154323_a(s, n);
    }
    
    public void renderBackground() {
        this.proxy.drawDefaultBackground();
    }
    
    static {
        SKIN_HEAD_U = (0x12 ^ 0x1A);
        SKIN_TEX_WIDTH = (0x51 ^ 0x11);
        SKIN_HAT_HEIGHT = (0xAD ^ 0xA5);
        SKIN_HEAD_WIDTH = (0x82 ^ 0x8A);
        SKIN_HEAD_V = (0x6D ^ 0x65);
        SKIN_HAT_V = (0xA5 ^ 0xAD);
        SKIN_TEX_HEIGHT = (0xFB ^ 0xBB);
        SKIN_HEAD_HEIGHT = (0x48 ^ 0x40);
        SKIN_HAT_WIDTH = (0x38 ^ 0x30);
        SKIN_HAT_U = (0xBA ^ 0x92);
    }
    
    public static void bind(final String s) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(s));
    }
    
    public static RealmsButton newButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        return new RealmsButton(n, n2, n3, n4, n5, s);
    }
    
    public void fillGradient(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    public void init(final Minecraft minecraft, final int n, final int n2) {
    }
    
    public void tick() {
    }
    
    public void renderBackground(final int n) {
        this.proxy.drawWorldBackground(n);
    }
    
    public void mouseEvent() {
    }
}
