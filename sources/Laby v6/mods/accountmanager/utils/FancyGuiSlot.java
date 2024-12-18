package mods.accountmanager.utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public abstract class FancyGuiSlot
{
    protected final Minecraft mc;
    public int width;
    public int height;
    public int top;
    public int bottom;
    public int right;
    public int left;
    public final int slotHeight;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    protected int mouseX;
    protected int mouseY;
    protected boolean field_148163_i = true;
    protected float initialClickY = -2.0F;
    protected float scrollMultiplier;
    protected float amountScrolled;
    protected int selectedElement = -1;
    protected long lastClicked;
    protected boolean field_178041_q = true;
    protected boolean showSelectionBox = true;
    protected boolean hasListHeader;
    public int headerPadding;
    private boolean enabled = true;
    private static final String __OBFID = "CL_00000679";
    private int startX;

    public FancyGuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn, int startX)
    {
        this.mc = mcIn;
        this.width = width;
        this.height = height;
        this.top = topIn;
        this.bottom = bottomIn;
        this.slotHeight = slotHeightIn;
        this.left = startX;
        this.right = this.left + width;
        this.startX = startX;
    }

    public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn)
    {
        this.width = widthIn;
        this.height = heightIn;
        this.top = topIn;
        this.bottom = bottomIn;
        this.left = 0;
        this.right = widthIn;
    }

    public void setShowSelectionBox(boolean showSelectionBoxIn)
    {
        this.showSelectionBox = showSelectionBoxIn;
    }

    protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn)
    {
        this.hasListHeader = hasListHeaderIn;
        this.headerPadding = headerPaddingIn;

        if (!hasListHeaderIn)
        {
            this.headerPadding = 0;
        }
    }

    protected abstract int getSize();

    protected abstract void elementClicked(int var1, boolean var2, int var3, int var4);

    protected abstract boolean isSelected(int var1);

    protected int getContentHeight()
    {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }

    protected abstract void drawBackground();

    protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_)
    {
    }

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6);

    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_)
    {
    }

    protected void func_148132_a(int p_148132_1_, int p_148132_2_)
    {
    }

    protected void func_148142_b(int p_148142_1_, int p_148142_2_)
    {
    }

    public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_)
    {
        int i = this.left + this.width / 2 - this.getListWidth() / 2;
        int j = this.left + this.width / 2 + this.getListWidth() / 2;
        int k = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        int l = k / this.slotHeight;
        return p_148124_1_ < this.getScrollBarX() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < this.getSize() ? l : -1;
    }

    public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn)
    {
        this.scrollUpButtonID = scrollUpButtonIDIn;
        this.scrollDownButtonID = scrollDownButtonIDIn;
    }

    protected void bindAmountScrolled()
    {
        int i = this.func_148135_f();

        if (i < 0)
        {
            i /= 2;
        }

        if (!this.field_148163_i && i < 0)
        {
            i = 0;
        }

        this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, (float)i);
    }

    public int func_148135_f()
    {
        return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
    }

    public int getAmountScrolled()
    {
        return (int)this.amountScrolled;
    }

    public boolean isMouseYWithinSlotBounds(int mouseX, int p_148141_1_)
    {
        return p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && mouseX >= this.left && mouseX <= this.right;
    }

    public void scrollBy(int amount)
    {
        this.amountScrolled += (float)amount;
        this.bindAmountScrolled();
        this.initialClickY = -2.0F;
    }

    public void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            if (button.id == this.scrollUpButtonID)
            {
                this.amountScrolled -= (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
            else if (button.id == this.scrollDownButtonID)
            {
                this.amountScrolled += (float)(this.slotHeight * 2 / 3);
                this.initialClickY = -2.0F;
                this.bindAmountScrolled();
            }
        }
    }

    public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_)
    {
        if (this.field_178041_q)
        {
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            this.drawBackground();
            int i = this.right - 6;
            int j = i + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int k = this.left;
            int l = this.top + 4 - (int)this.amountScrolled;

            if (this.hasListHeader)
            {
                this.drawListHeader(k, l, tessellator);
            }

            this.drawSelectionBox(k, l, mouseXIn, mouseYIn);
            GlStateManager.disableDepth();
            byte b0 = 4;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            int i1 = this.func_148135_f();

            if (i1 > 0)
            {
                int j1 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                j1 = MathHelper.clamp_int(j1, 32, this.bottom - this.top - 8);
                int k1 = (int)this.amountScrolled * (this.bottom - this.top - j1) / i1 + this.top;

                if (k1 < this.top)
                {
                    k1 = this.top;
                }

                Gui.drawRect(i, this.top, j, this.bottom, (new Color(255, 255, 255, 20)).getRGB());
                Gui.drawRect(i, k1, j, k1 + j1, (new Color(192, 192, 192, 100)).getRGB());
            }

            this.func_148142_b(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public void handleMouseInput()
    {
        if (this.isMouseYWithinSlotBounds(this.mouseX, this.mouseY))
        {
            if (Mouse.isButtonDown(0) && this.getEnabled())
            {
                if (this.initialClickY != -1.0F)
                {
                    if (this.initialClickY >= 0.0F)
                    {
                        this.amountScrolled -= ((float)this.mouseY - this.initialClickY) * this.scrollMultiplier;
                        this.initialClickY = (float)this.mouseY;
                    }
                }
                else
                {
                    boolean flag = true;

                    if (this.mouseY >= this.top && this.mouseY <= this.bottom)
                    {
                        int i = this.width / 2 - this.getListWidth() / 2;
                        int j = this.width / 2 + this.getListWidth() / 2;
                        int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                        int l = k / this.slotHeight;

                        if (this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0 && l < this.getSize())
                        {
                            boolean flag1 = l == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                            this.elementClicked(l, flag1, this.mouseX, this.mouseY);
                            this.selectedElement = l;
                            this.lastClicked = Minecraft.getSystemTime();
                        }
                        else if (this.mouseX >= i && this.mouseX <= j && k < 0)
                        {
                            this.func_148132_a(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
                            flag = false;
                        }

                        int i2 = this.right - 6;
                        int i1 = i2 + 6;

                        if (this.mouseX >= i2 && this.mouseX <= i1)
                        {
                            this.scrollMultiplier = -1.0F;
                            int j1 = this.func_148135_f();

                            if (j1 < 1)
                            {
                                j1 = 1;
                            }

                            int k1 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
                            k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
                            this.scrollMultiplier /= (float)(this.bottom - this.top - k1) / (float)j1;
                        }
                        else
                        {
                            this.scrollMultiplier = 1.0F;
                        }

                        if (flag)
                        {
                            this.initialClickY = (float)this.mouseY;
                        }
                        else
                        {
                            this.initialClickY = -2.0F;
                        }
                    }
                    else
                    {
                        this.initialClickY = -2.0F;
                    }
                }
            }
            else
            {
                this.initialClickY = -1.0F;
            }

            int l1 = Mouse.getEventDWheel();

            if (l1 != 0)
            {
                if (l1 > 0)
                {
                    l1 = -1;
                }
                else if (l1 < 0)
                {
                    l1 = 1;
                }

                this.amountScrolled += (float)(l1 * this.slotHeight / 2);
            }
        }
    }

    public void setEnabled(boolean enabledIn)
    {
        this.enabled = enabledIn;
    }

    public boolean getEnabled()
    {
        return this.enabled;
    }

    public int getListWidth()
    {
        return this.width;
    }

    protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn)
    {
        int i = this.getSize();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        for (int j = 0; j < i; ++j)
        {
            int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
            int l = this.slotHeight - 4;

            if (k > this.bottom || k + l < this.top)
            {
                this.func_178040_a(j, p_148120_1_, k);
            }

            if (this.showSelectionBox && this.isSelected(j) && k >= this.top && k <= this.bottom - this.slotHeight + 5)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                Gui.drawRect(this.left, k, this.right - 6, k + l + 2, (new Color(255, 255, 255, 40)).getRGB());
            }

            this.drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
        }
    }

    protected int getScrollBarX()
    {
        return this.right - 6;
    }

    public void setSlotXBoundsFromLeft(int leftIn)
    {
        this.left = leftIn;
        this.right = leftIn + this.width;
    }

    public int getSlotHeight()
    {
        return this.slotHeight;
    }
}
