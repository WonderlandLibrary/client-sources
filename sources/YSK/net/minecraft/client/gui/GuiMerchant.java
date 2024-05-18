package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.village.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;

public class GuiMerchant extends GuiContainer
{
    private int selectedMerchantRecipe;
    private static final ResourceLocation MERCHANT_GUI_TEXTURE;
    private static final String[] I;
    private IMerchant merchant;
    private MerchantButton nextButton;
    private MerchantButton previousButton;
    private static final Logger logger;
    private IChatComponent chatComponent;
    
    private static void I() {
        (I = new String[0x85 ^ 0x81])["".length()] = I("\r\u000e\u0012\u00174\u000b\u000e\u0019L&\f\u0002E\u0000.\u0017\u001f\u000b\n/\u001c\u0019E\u0015(\u0015\u0007\u000b\u0004$\u000bE\u001a\r&", "ykjcA");
        GuiMerchant.I[" ".length()] = I("6\u000b)>3<\n\"8|<\n1/<!\u000b53", "UdGJR");
        GuiMerchant.I["  ".length()] = I("\u001b\u0013;6\u000b\u00055+", "VPGby");
        GuiMerchant.I["   ".length()] = I("\u0003,*,'\u000f',a+\u000b9**,\u000f==+", "nIXOO");
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        MERCHANT_GUI_TEXTURE = new ResourceLocation(GuiMerchant.I["".length()]);
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public IMerchant getMerchant() {
        return this.merchant;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
        if (recipes != null) {
            final MerchantButton nextButton = this.nextButton;
            int enabled;
            if (this.selectedMerchantRecipe < recipes.size() - " ".length()) {
                enabled = " ".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                enabled = "".length();
            }
            nextButton.enabled = (enabled != 0);
            final MerchantButton previousButton = this.previousButton;
            int enabled2;
            if (this.selectedMerchantRecipe > 0) {
                enabled2 = " ".length();
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                enabled2 = "".length();
            }
            previousButton.enabled = (enabled2 != 0);
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.chatComponent.getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / "  ".length() - this.fontRendererObj.getStringWidth(unformattedText) / "  ".length(), 0xC ^ 0xA, 3305877 + 3203165 - 4822778 + 2524488);
        this.fontRendererObj.drawString(I18n.format(GuiMerchant.I[" ".length()], new Object["".length()]), 0x68 ^ 0x60, this.ySize - (0x45 ^ 0x25) + "  ".length(), 4038999 + 765068 - 1122226 + 528911);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int n = (this.width - this.xSize) / "  ".length();
        final int n2 = (this.height - this.ySize) / "  ".length();
        this.buttonList.add(this.nextButton = new MerchantButton(" ".length(), n + (0x1 ^ 0x79) + (0x5 ^ 0x1E), n2 + (0x43 ^ 0x5B) - " ".length(), " ".length() != 0));
        this.buttonList.add(this.previousButton = new MerchantButton("  ".length(), n + (0xA2 ^ 0x86) - (0xB8 ^ 0xAB), n2 + (0x5D ^ 0x45) - " ".length(), "".length() != 0));
        this.nextButton.enabled = ("".length() != 0);
        this.previousButton.enabled = ("".length() != 0);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        int n = "".length();
        if (guiButton == this.nextButton) {
            this.selectedMerchantRecipe += " ".length();
            final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
            if (recipes != null && this.selectedMerchantRecipe >= recipes.size()) {
                this.selectedMerchantRecipe = recipes.size() - " ".length();
            }
            n = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (guiButton == this.previousButton) {
            this.selectedMerchantRecipe -= " ".length();
            if (this.selectedMerchantRecipe < 0) {
                this.selectedMerchantRecipe = "".length();
            }
            n = " ".length();
        }
        if (n != 0) {
            ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.selectedMerchantRecipe);
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.selectedMerchantRecipe);
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(GuiMerchant.I["  ".length()], packetBuffer));
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            final int n4 = (this.width - this.xSize) / "  ".length();
            final int n5 = (this.height - this.ySize) / "  ".length();
            final MerchantRecipe merchantRecipe = recipes.get(this.selectedMerchantRecipe);
            final ItemStack itemToBuy = merchantRecipe.getItemToBuy();
            final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            final ItemStack itemToSell = merchantRecipe.getItemToSell();
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            this.itemRender.zLevel = 100.0f;
            this.itemRender.renderItemAndEffectIntoGUI(itemToBuy, n4 + (0x54 ^ 0x70), n5 + (0x8C ^ 0x94));
            this.itemRender.renderItemOverlays(this.fontRendererObj, itemToBuy, n4 + (0x46 ^ 0x62), n5 + (0x2D ^ 0x35));
            if (secondItemToBuy != null) {
                this.itemRender.renderItemAndEffectIntoGUI(secondItemToBuy, n4 + (0xFA ^ 0xC4), n5 + (0xA9 ^ 0xB1));
                this.itemRender.renderItemOverlays(this.fontRendererObj, secondItemToBuy, n4 + (0x31 ^ 0xF), n5 + (0x67 ^ 0x7F));
            }
            this.itemRender.renderItemAndEffectIntoGUI(itemToSell, n4 + (0xC9 ^ 0xB1), n5 + (0x8C ^ 0x94));
            this.itemRender.renderItemOverlays(this.fontRendererObj, itemToSell, n4 + (0x6F ^ 0x17), n5 + (0xAE ^ 0xB6));
            this.itemRender.zLevel = 0.0f;
            GlStateManager.disableLighting();
            if (this.isPointInRegion(0xBB ^ 0x9F, 0x9F ^ 0x87, 0x2F ^ 0x3F, 0xD3 ^ 0xC3, n, n2) && itemToBuy != null) {
                this.renderToolTip(itemToBuy, n, n2);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else if (secondItemToBuy != null && this.isPointInRegion(0xAD ^ 0x93, 0xA2 ^ 0xBA, 0x8A ^ 0x9A, 0x12 ^ 0x2, n, n2) && secondItemToBuy != null) {
                this.renderToolTip(secondItemToBuy, n, n2);
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else if (itemToSell != null && this.isPointInRegion(0x12 ^ 0x6A, 0x9E ^ 0x86, 0x4D ^ 0x5D, 0xB3 ^ 0xA3, n, n2) && itemToSell != null) {
                this.renderToolTip(itemToSell, n, n2);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (merchantRecipe.isRecipeDisabled() && (this.isPointInRegion(0x69 ^ 0x3A, 0x3C ^ 0x29, 0x3 ^ 0x1F, 0x16 ^ 0x3, n, n2) || this.isPointInRegion(0xCF ^ 0x9C, 0x63 ^ 0x50, 0x26 ^ 0x3A, 0x92 ^ 0x87, n, n2))) {
                this.drawCreativeTabHoveringText(I18n.format(GuiMerchant.I["   ".length()], new Object["".length()]), n, n2);
            }
            GlStateManager.popMatrix();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    static ResourceLocation access$0() {
        return GuiMerchant.MERCHANT_GUI_TEXTURE;
    }
    
    public GuiMerchant(final InventoryPlayer inventoryPlayer, final IMerchant merchant, final World world) {
        super(new ContainerMerchant(inventoryPlayer, merchant, world));
        this.merchant = merchant;
        this.chatComponent = merchant.getDisplayName();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
        this.drawTexturedModalRect((this.width - this.xSize) / "  ".length(), (this.height - this.ySize) / "  ".length(), "".length(), "".length(), this.xSize, this.ySize);
        final MerchantRecipeList recipes = this.merchant.getRecipes(this.mc.thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            final int selectedMerchantRecipe = this.selectedMerchantRecipe;
            if (selectedMerchantRecipe < 0 || selectedMerchantRecipe >= recipes.size()) {
                return;
            }
            if (recipes.get(selectedMerchantRecipe).isRecipeDisabled()) {
                this.mc.getTextureManager().bindTexture(GuiMerchant.MERCHANT_GUI_TEXTURE);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableLighting();
                this.drawTexturedModalRect(this.guiLeft + (0xF7 ^ 0xA4), this.guiTop + (0x6 ^ 0x13), 195 + 59 - 120 + 78, "".length(), 0x34 ^ 0x28, 0x53 ^ 0x46);
                this.drawTexturedModalRect(this.guiLeft + (0xC7 ^ 0x94), this.guiTop + (0xB2 ^ 0x81), 103 + 153 - 44 + 0, "".length(), 0xB3 ^ 0xAF, 0x42 ^ 0x57);
            }
        }
    }
    
    static class MerchantButton extends GuiButton
    {
        private final boolean field_146157_o;
        private static final String[] I;
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("", "taMnI");
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
                if (1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public MerchantButton(final int n, final int n2, final int n3, final boolean field_146157_o) {
            super(n, n2, n3, 0x1C ^ 0x10, 0xBD ^ 0xAE, MerchantButton.I["".length()]);
            this.field_146157_o = field_146157_o;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                minecraft.getTextureManager().bindTexture(GuiMerchant.access$0());
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                int n3;
                if (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
                    n3 = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final int n4 = n3;
                int length = "".length();
                int n5 = 102 + 83 - 73 + 64;
                if (!this.enabled) {
                    n5 += this.width * "  ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (n4 != 0) {
                    n5 += this.width;
                }
                if (!this.field_146157_o) {
                    length += this.height;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, n5, length, this.width, this.height);
            }
        }
    }
}
