package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import java.util.Iterator;
import com.google.common.collect.Sets;
import java.util.Set;

public abstract class GuiContainer extends GuiScreen
{
    protected static final ResourceLocation_1975012498 áŒŠÆ;
    protected int áˆºÑ¢Õ;
    protected int ÂµÈ;
    public static Container á;
    protected int ˆÏ­;
    protected int £á;
    private Slot HorizonCode_Horizon_È;
    private Slot Â;
    private boolean Ý;
    private ItemStack Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private Slot à;
    private long Ø;
    private ItemStack µà;
    private Slot ˆà;
    private long ¥Æ;
    protected final Set Å;
    protected boolean £à;
    private int Ø­à;
    private int µÕ;
    private boolean Æ;
    private int áƒ;
    private long á€;
    private Slot Õ;
    private int à¢;
    private boolean ŠÂµà;
    private ItemStack ¥à;
    private static final String Âµà = "CL_00000737";
    
    static {
        áŒŠÆ = new ResourceLocation_1975012498("textures/gui/container/inventory.png");
    }
    
    public GuiContainer(final Container p_i1072_1_) {
        this.áˆºÑ¢Õ = 176;
        this.ÂµÈ = 166;
        this.Å = Sets.newHashSet();
        GuiContainer.á = p_i1072_1_;
        this.Æ = true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        GuiContainer.Ñ¢á.á.Ï­Ï = GuiContainer.á;
        this.ˆÏ­ = (GuiContainer.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        this.£á = (GuiContainer.Ê - this.ÂµÈ) / 2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        final int var4 = this.ˆÏ­;
        final int var5 = this.£á;
        this.HorizonCode_Horizon_È(partialTicks, mouseX, mouseY);
        GlStateManager.Ñ¢á();
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.Ó();
        GlStateManager.áŒŠÆ();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        RenderHelper.Ý();
        GlStateManager.Çªà¢();
        GlStateManager.Â(var4, var5, 0.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ŠÄ();
        this.HorizonCode_Horizon_È = null;
        final short var6 = 240;
        final short var7 = 240;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var6 / 1.0f, var7 / 1.0f);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        for (int var8 = 0; var8 < GuiContainer.á.Ý.size(); ++var8) {
            final Slot var9 = GuiContainer.á.Ý.get(var8);
            this.HorizonCode_Horizon_È(var9);
            if (this.HorizonCode_Horizon_È(var9, mouseX, mouseY) && var9.Ó()) {
                this.HorizonCode_Horizon_È = var9;
                GlStateManager.Ó();
                GlStateManager.áŒŠÆ();
                final int var10 = var9.Ø­áŒŠá;
                final int var11 = var9.Âµá€;
                GlStateManager.HorizonCode_Horizon_È(true, true, true, false);
                Gui_1808253012.HorizonCode_Horizon_È(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
                GlStateManager.HorizonCode_Horizon_È(true, true, true, true);
                GlStateManager.Âµá€();
                GlStateManager.áˆºÑ¢Õ();
            }
        }
        RenderHelper.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È(mouseX, mouseY);
        RenderHelper.Ý();
        final InventoryPlayer var12 = GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº;
        ItemStack var13 = (this.Ø­áŒŠá == null) ? var12.á() : this.Ø­áŒŠá;
        if (var13 != null) {
            final byte var14 = 8;
            final int var11 = (this.Ø­áŒŠá == null) ? 8 : 16;
            String var15 = null;
            if (this.Ø­áŒŠá != null && this.Ý) {
                var13 = var13.áˆºÑ¢Õ();
                var13.Â = MathHelper.Ó(var13.Â / 2.0f);
            }
            else if (this.£à && this.Å.size() > 1) {
                var13 = var13.áˆºÑ¢Õ();
                var13.Â = this.áƒ;
                if (var13.Â == 0) {
                    var15 = EnumChatFormatting.Å + "0";
                }
            }
            this.HorizonCode_Horizon_È(var13, mouseX - var4 - var14, mouseY - var5 - var11, var15);
        }
        if (this.µà != null) {
            float var16 = (Minecraft.áƒ() - this.Ø) / 100.0f;
            if (var16 >= 1.0f) {
                var16 = 1.0f;
                this.µà = null;
            }
            final int var11 = this.à.Ø­áŒŠá - this.Âµá€;
            final int var17 = this.à.Âµá€ - this.Ó;
            final int var18 = this.Âµá€ + (int)(var11 * var16);
            final int var19 = this.Ó + (int)(var17 * var16);
            this.HorizonCode_Horizon_È(this.µà, var18, var19, null);
        }
        GlStateManager.Ê();
        if (var12.á() == null && this.HorizonCode_Horizon_È != null && this.HorizonCode_Horizon_È.Â()) {
            final ItemStack var20 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
            this.HorizonCode_Horizon_È(var20, mouseX, mouseY);
        }
        GlStateManager.Âµá€();
        GlStateManager.áˆºÑ¢Õ();
        RenderHelper.Â();
    }
    
    private void HorizonCode_Horizon_È(final ItemStack stack, final int x, final int y, final String altText) {
        GlStateManager.Â(0.0f, 0.0f, 32.0f);
        GuiContainer.ŠÄ = 200.0f;
        this.ŒÏ.HorizonCode_Horizon_È = 200.0f;
        this.ŒÏ.Â(stack, x, y);
        this.ŒÏ.HorizonCode_Horizon_È(this.É, stack, x, y - ((this.Ø­áŒŠá == null) ? 0 : 8), altText);
        GuiContainer.ŠÄ = 0.0f;
        this.ŒÏ.HorizonCode_Horizon_È = 0.0f;
    }
    
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
    }
    
    protected abstract void HorizonCode_Horizon_È(final float p0, final int p1, final int p2);
    
    private void HorizonCode_Horizon_È(final Slot slotIn) {
        final int var2 = slotIn.Ø­áŒŠá;
        final int var3 = slotIn.Âµá€;
        ItemStack var4 = slotIn.HorizonCode_Horizon_È();
        boolean var5 = false;
        boolean var6 = slotIn == this.Â && this.Ø­áŒŠá != null && !this.Ý;
        final ItemStack var7 = GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á();
        String var8 = null;
        if (slotIn == this.Â && this.Ø­áŒŠá != null && this.Ý && var4 != null) {
            final ItemStack áˆºÑ¢Õ;
            var4 = (áˆºÑ¢Õ = var4.áˆºÑ¢Õ());
            áˆºÑ¢Õ.Â /= 2;
        }
        else if (this.£à && this.Å.contains(slotIn) && var7 != null) {
            if (this.Å.size() == 1) {
                return;
            }
            if (Container.HorizonCode_Horizon_È(slotIn, var7, true) && GuiContainer.á.HorizonCode_Horizon_È(slotIn)) {
                var4 = var7.áˆºÑ¢Õ();
                var5 = true;
                Container.HorizonCode_Horizon_È(this.Å, this.Ø­à, var4, (slotIn.HorizonCode_Horizon_È() == null) ? 0 : slotIn.HorizonCode_Horizon_È().Â);
                if (var4.Â > var4.Â()) {
                    var8 = new StringBuilder().append(EnumChatFormatting.Å).append(var4.Â()).toString();
                    var4.Â = var4.Â();
                }
                if (var4.Â > slotIn.Ý(var4)) {
                    var8 = new StringBuilder().append(EnumChatFormatting.Å).append(slotIn.Ý(var4)).toString();
                    var4.Â = slotIn.Ý(var4);
                }
            }
            else {
                this.Å.remove(slotIn);
                this.Ó();
            }
        }
        GuiContainer.ŠÄ = 100.0f;
        this.ŒÏ.HorizonCode_Horizon_È = 100.0f;
        if (var4 == null) {
            final String var9 = slotIn.Âµá€();
            if (var9 != null) {
                final TextureAtlasSprite var10 = GuiContainer.Ñ¢á.áŠ().HorizonCode_Horizon_È(var9);
                GlStateManager.Ó();
                GuiContainer.Ñ¢á.¥à().HorizonCode_Horizon_È(TextureMap.à);
                this.HorizonCode_Horizon_È(var2, var3, var10, 16, 16);
                GlStateManager.Âµá€();
                var6 = true;
            }
        }
        if (!var6) {
            if (var5) {
                Gui_1808253012.HorizonCode_Horizon_È(var2, var3, var2 + 16, var3 + 16, -2130706433);
            }
            GlStateManager.áˆºÑ¢Õ();
            this.ŒÏ.Â(var4, var2, var3);
            this.ŒÏ.HorizonCode_Horizon_È(this.É, var4, var2, var3, var8);
        }
        this.ŒÏ.HorizonCode_Horizon_È = 0.0f;
        GuiContainer.ŠÄ = 0.0f;
    }
    
    private void Ó() {
        final ItemStack var1 = GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á();
        if (var1 != null && this.£à) {
            this.áƒ = var1.Â;
            for (final Slot var3 : this.Å) {
                final ItemStack var4 = var1.áˆºÑ¢Õ();
                final int var5 = (var3.HorizonCode_Horizon_È() == null) ? 0 : var3.HorizonCode_Horizon_È().Â;
                Container.HorizonCode_Horizon_È(this.Å, this.Ø­à, var4, var5);
                if (var4.Â > var4.Â()) {
                    var4.Â = var4.Â();
                }
                if (var4.Â > var3.Ý(var4)) {
                    var4.Â = var3.Ý(var4);
                }
                this.áƒ -= var4.Â - var5;
            }
        }
    }
    
    private Slot Â(final int x, final int y) {
        for (int var3 = 0; var3 < GuiContainer.á.Ý.size(); ++var3) {
            final Slot var4 = GuiContainer.á.Ý.get(var3);
            if (this.HorizonCode_Horizon_È(var4, x, y)) {
                return var4;
            }
        }
        return null;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        final boolean var4 = mouseButton == GuiContainer.Ñ¢á.ŠÄ.¥Å.áŒŠÆ() + 100;
        final Slot var5 = this.Â(mouseX, mouseY);
        final long var6 = Minecraft.áƒ();
        this.ŠÂµà = (this.Õ == var5 && var6 - this.á€ < 250L && this.à¢ == mouseButton);
        this.Æ = false;
        if (mouseButton == 0 || mouseButton == 1 || var4) {
            final int var7 = this.ˆÏ­;
            final int var8 = this.£á;
            final boolean var9 = mouseX < var7 || mouseY < var8 || mouseX >= var7 + this.áˆºÑ¢Õ || mouseY >= var8 + this.ÂµÈ;
            int var10 = -1;
            if (var5 != null) {
                var10 = var5.Ý;
            }
            if (var9) {
                var10 = -999;
            }
            if (GuiContainer.Ñ¢á.ŠÄ.ÂµÕ && var9 && GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() == null) {
                GuiContainer.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                return;
            }
            if (var10 != -1) {
                if (GuiContainer.Ñ¢á.ŠÄ.ÂµÕ) {
                    if (var5 != null && var5.Â()) {
                        this.Â = var5;
                        this.Ø­áŒŠá = null;
                        this.Ý = (mouseButton == 1);
                    }
                    else {
                        this.Â = null;
                    }
                }
                else if (!this.£à) {
                    if (GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() == null) {
                        if (mouseButton == GuiContainer.Ñ¢á.ŠÄ.¥Å.áŒŠÆ() + 100) {
                            this.HorizonCode_Horizon_È(var5, var10, mouseButton, 3);
                        }
                        else {
                            final boolean var11 = var10 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            byte var12 = 0;
                            if (var11) {
                                this.¥à = ((var5 != null && var5.Â()) ? var5.HorizonCode_Horizon_È() : null);
                                var12 = 1;
                            }
                            else if (var10 == -999) {
                                var12 = 4;
                            }
                            this.HorizonCode_Horizon_È(var5, var10, mouseButton, var12);
                        }
                        this.Æ = true;
                    }
                    else {
                        this.£à = true;
                        this.µÕ = mouseButton;
                        this.Å.clear();
                        if (mouseButton == 0) {
                            this.Ø­à = 0;
                        }
                        else if (mouseButton == 1) {
                            this.Ø­à = 1;
                        }
                        else if (mouseButton == GuiContainer.Ñ¢á.ŠÄ.¥Å.áŒŠÆ() + 100) {
                            this.Ø­à = 2;
                        }
                    }
                }
            }
        }
        this.Õ = var5;
        this.á€ = var6;
        this.à¢ = mouseButton;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        final Slot var6 = this.Â(mouseX, mouseY);
        final ItemStack var7 = GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á();
        if (this.Â != null && GuiContainer.Ñ¢á.ŠÄ.ÂµÕ) {
            if (clickedMouseButton == 0 || clickedMouseButton == 1) {
                if (this.Ø­áŒŠá == null) {
                    if (var6 != this.Â) {
                        this.Ø­áŒŠá = this.Â.HorizonCode_Horizon_È().áˆºÑ¢Õ();
                    }
                }
                else if (this.Ø­áŒŠá.Â > 1 && var6 != null && Container.HorizonCode_Horizon_È(var6, this.Ø­áŒŠá, false)) {
                    final long var8 = Minecraft.áƒ();
                    if (this.ˆà == var6) {
                        if (var8 - this.¥Æ > 500L) {
                            this.HorizonCode_Horizon_È(this.Â, this.Â.Ý, 0, 0);
                            this.HorizonCode_Horizon_È(var6, var6.Ý, 1, 0);
                            this.HorizonCode_Horizon_È(this.Â, this.Â.Ý, 0, 0);
                            this.¥Æ = var8 + 750L;
                            final ItemStack ø­áŒŠá = this.Ø­áŒŠá;
                            --ø­áŒŠá.Â;
                        }
                    }
                    else {
                        this.ˆà = var6;
                        this.¥Æ = var8;
                    }
                }
            }
        }
        else if (this.£à && var6 != null && var7 != null && var7.Â > this.Å.size() && Container.HorizonCode_Horizon_È(var6, var7, true) && var6.HorizonCode_Horizon_È(var7) && GuiContainer.á.HorizonCode_Horizon_È(var6)) {
            this.Å.add(var6);
            this.Ó();
        }
    }
    
    @Override
    protected void Â(final int mouseX, final int mouseY, final int state) {
        final Slot var4 = this.Â(mouseX, mouseY);
        final int var5 = this.ˆÏ­;
        final int var6 = this.£á;
        final boolean var7 = mouseX < var5 || mouseY < var6 || mouseX >= var5 + this.áˆºÑ¢Õ || mouseY >= var6 + this.ÂµÈ;
        int var8 = -1;
        if (var4 != null) {
            var8 = var4.Ý;
        }
        if (var7) {
            var8 = -999;
        }
        if (this.ŠÂµà && var4 != null && state == 0 && GuiContainer.á.HorizonCode_Horizon_È(null, var4)) {
            if (£à()) {
                if (var4 != null && var4.Â != null && this.¥à != null) {
                    for (final Slot var10 : GuiContainer.á.Ý) {
                        if (var10 != null && var10.HorizonCode_Horizon_È(GuiContainer.Ñ¢á.á) && var10.Â() && var10.Â == var4.Â && Container.HorizonCode_Horizon_È(var10, this.¥à, true)) {
                            this.HorizonCode_Horizon_È(var10, var10.Ý, state, 1);
                        }
                    }
                }
            }
            else {
                this.HorizonCode_Horizon_È(var4, var8, state, 6);
            }
            this.ŠÂµà = false;
            this.á€ = 0L;
        }
        else {
            if (this.£à && this.µÕ != state) {
                this.£à = false;
                this.Å.clear();
                this.Æ = true;
                return;
            }
            if (this.Æ) {
                this.Æ = false;
                return;
            }
            if (this.Â != null && GuiContainer.Ñ¢á.ŠÄ.ÂµÕ) {
                if (state == 0 || state == 1) {
                    if (this.Ø­áŒŠá == null && var4 != this.Â) {
                        this.Ø­áŒŠá = this.Â.HorizonCode_Horizon_È();
                    }
                    final boolean var11 = Container.HorizonCode_Horizon_È(var4, this.Ø­áŒŠá, false);
                    if (var8 != -1 && this.Ø­áŒŠá != null && var11) {
                        this.HorizonCode_Horizon_È(this.Â, this.Â.Ý, state, 0);
                        this.HorizonCode_Horizon_È(var4, var8, 0, 0);
                        if (GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() != null) {
                            this.HorizonCode_Horizon_È(this.Â, this.Â.Ý, state, 0);
                            this.Âµá€ = mouseX - var5;
                            this.Ó = mouseY - var6;
                            this.à = this.Â;
                            this.µà = this.Ø­áŒŠá;
                            this.Ø = Minecraft.áƒ();
                        }
                        else {
                            this.µà = null;
                        }
                    }
                    else if (this.Ø­áŒŠá != null) {
                        this.Âµá€ = mouseX - var5;
                        this.Ó = mouseY - var6;
                        this.à = this.Â;
                        this.µà = this.Ø­áŒŠá;
                        this.Ø = Minecraft.áƒ();
                    }
                    this.Ø­áŒŠá = null;
                    this.Â = null;
                }
            }
            else if (this.£à && !this.Å.isEmpty()) {
                this.HorizonCode_Horizon_È(null, -999, Container.Â(0, this.Ø­à), 5);
                for (final Slot var10 : this.Å) {
                    this.HorizonCode_Horizon_È(var10, var10.Ý, Container.Â(1, this.Ø­à), 5);
                }
                this.HorizonCode_Horizon_È(null, -999, Container.Â(2, this.Ø­à), 5);
            }
            else if (GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() != null) {
                if (state == GuiContainer.Ñ¢á.ŠÄ.¥Å.áŒŠÆ() + 100) {
                    this.HorizonCode_Horizon_È(var4, var8, state, 3);
                }
                else {
                    final boolean var11 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    if (var11) {
                        this.¥à = ((var4 != null && var4.Â()) ? var4.HorizonCode_Horizon_È() : null);
                    }
                    this.HorizonCode_Horizon_È(var4, var8, state, var11 ? 1 : 0);
                }
            }
        }
        if (GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() == null) {
            this.á€ = 0L;
        }
        this.£à = false;
    }
    
    private boolean HorizonCode_Horizon_È(final Slot slotIn, final int mouseX, final int mouseY) {
        return this.Ý(slotIn.Ø­áŒŠá, slotIn.Âµá€, 16, 16, mouseX, mouseY);
    }
    
    protected boolean Ý(final int left, final int top, final int right, final int bottom, int pointX, int pointY) {
        final int var7 = this.ˆÏ­;
        final int var8 = this.£á;
        pointX -= var7;
        pointY -= var8;
        return pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1;
    }
    
    protected void HorizonCode_Horizon_È(final Slot slotIn, int slotId, final int clickedButton, final int clickType) {
        if (slotIn != null) {
            slotId = slotIn.Ý;
        }
        GuiContainer.Ñ¢á.Âµá€.HorizonCode_Horizon_È(GuiContainer.á.Ø­áŒŠá, slotId, clickedButton, clickType, GuiContainer.Ñ¢á.á);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1 || keyCode == GuiContainer.Ñ¢á.ŠÄ.Ï­Ï.áŒŠÆ()) {
            GuiContainer.Ñ¢á.á.ˆà();
        }
        this.HorizonCode_Horizon_È(keyCode);
        if (this.HorizonCode_Horizon_È != null && this.HorizonCode_Horizon_È.Â()) {
            if (keyCode == GuiContainer.Ñ¢á.ŠÄ.¥Å.áŒŠÆ()) {
                this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Ý, 0, 3);
            }
            else if (keyCode == GuiContainer.Ñ¢á.ŠÄ.ˆÐƒØ.áŒŠÆ()) {
                this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Ý, GuiScreen.Å() ? 1 : 0, 4);
            }
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final int keyCode) {
        if (GuiContainer.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() == null && this.HorizonCode_Horizon_È != null) {
            for (int var2 = 0; var2 < 9; ++var2) {
                if (keyCode == GuiContainer.Ñ¢á.ŠÄ.áŒŠÉ[var2].áŒŠÆ()) {
                    this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.HorizonCode_Horizon_È.Ý, var2, 2);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void q_() {
        if (GuiContainer.Ñ¢á.á != null) {
            GuiContainer.á.Â(GuiContainer.Ñ¢á.á);
        }
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    public void Ý() {
        super.Ý();
        if (!GuiContainer.Ñ¢á.á.Œ() || GuiContainer.Ñ¢á.á.ˆáŠ) {
            GuiContainer.Ñ¢á.á.ˆà();
        }
    }
}
