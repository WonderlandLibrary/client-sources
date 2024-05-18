package HORIZON-6-0-SKIDPROTECTION;

import java.util.Map;
import org.lwjgl.input.Mouse;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import java.util.List;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static InventoryBasic Â;
    private static int Ý;
    private float Ø­áŒŠá;
    private boolean Âµá€;
    private boolean Ó;
    private GuiTextField à;
    private List Ø;
    private Slot µà;
    private boolean ˆà;
    private CreativeCrafting ¥Æ;
    private static final String Ø­à = "CL_00000752";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/creative_inventory/tabs.png");
        GuiContainerCreative.Â = new InventoryBasic("tmp", true, 45);
        GuiContainerCreative.Ý = CreativeTabs.Â.HorizonCode_Horizon_È();
    }
    
    public GuiContainerCreative(final EntityPlayer p_i1088_1_) {
        super(new HorizonCode_Horizon_È(p_i1088_1_));
        p_i1088_1_.Ï­Ï = GuiContainerCreative.á;
        this.ÇŽÕ = true;
        this.ÂµÈ = 136;
        this.áˆºÑ¢Õ = 195;
    }
    
    @Override
    public void Ý() {
        if (!GuiContainerCreative.Ñ¢á.Âµá€.Ø()) {
            GuiContainerCreative.Ñ¢á.HorizonCode_Horizon_È(new GuiInventory(GuiContainerCreative.Ñ¢á.á));
        }
        this.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final Slot slotIn, final int slotId, final int clickedButton, int clickType) {
        this.ˆà = true;
        final boolean var5 = clickType == 1;
        clickType = ((slotId == -999 && clickType == 0) ? 4 : clickType);
        if (slotIn == null && GuiContainerCreative.Ý != CreativeTabs.ˆÏ­.HorizonCode_Horizon_È() && clickType != 5) {
            final InventoryPlayer var6 = GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº;
            if (var6.á() != null) {
                if (clickedButton == 0) {
                    GuiContainerCreative.Ñ¢á.á.HorizonCode_Horizon_È(var6.á(), true);
                    GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(var6.á());
                    var6.Â((ItemStack)null);
                }
                if (clickedButton == 1) {
                    final ItemStack var7 = var6.á().HorizonCode_Horizon_È(1);
                    GuiContainerCreative.Ñ¢á.á.HorizonCode_Horizon_È(var7, true);
                    GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(var7);
                    if (var6.á().Â == 0) {
                        var6.Â((ItemStack)null);
                    }
                }
            }
        }
        else if (slotIn == this.µà && var5) {
            for (int var8 = 0; var8 < GuiContainerCreative.Ñ¢á.á.ŒÂ.Â().size(); ++var8) {
                GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(null, var8);
            }
        }
        else if (GuiContainerCreative.Ý == CreativeTabs.ˆÏ­.HorizonCode_Horizon_È()) {
            if (slotIn == this.µà) {
                GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.Â((ItemStack)null);
            }
            else if (clickType == 4 && slotIn != null && slotIn.Â()) {
                final ItemStack var9 = slotIn.HorizonCode_Horizon_È((clickedButton == 0) ? 1 : slotIn.HorizonCode_Horizon_È().Â());
                GuiContainerCreative.Ñ¢á.á.HorizonCode_Horizon_È(var9, true);
                GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(var9);
            }
            else if (clickType == 4 && GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á() != null) {
                GuiContainerCreative.Ñ¢á.á.HorizonCode_Horizon_È(GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á(), true);
                GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.á());
                GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.Â((ItemStack)null);
            }
            else {
                GuiContainerCreative.Ñ¢á.á.ŒÂ.HorizonCode_Horizon_È((slotIn == null) ? slotId : ((Â)slotIn).Ó.Ý, clickedButton, clickType, GuiContainerCreative.Ñ¢á.á);
                GuiContainerCreative.Ñ¢á.á.ŒÂ.Ý();
            }
        }
        else if (clickType != 5 && slotIn.Â == GuiContainerCreative.Â) {
            final InventoryPlayer var6 = GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº;
            ItemStack var7 = var6.á();
            final ItemStack var10 = slotIn.HorizonCode_Horizon_È();
            if (clickType == 2) {
                if (var10 != null && clickedButton >= 0 && clickedButton < 9) {
                    final ItemStack var11 = var10.áˆºÑ¢Õ();
                    var11.Â = var11.Â();
                    GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº.Ý(clickedButton, var11);
                    GuiContainerCreative.Ñ¢á.á.ŒÂ.Ý();
                }
                return;
            }
            if (clickType == 3) {
                if (var6.á() == null && slotIn.Â()) {
                    final ItemStack var11 = slotIn.HorizonCode_Horizon_È().áˆºÑ¢Õ();
                    var11.Â = var11.Â();
                    var6.Â(var11);
                }
                return;
            }
            if (clickType == 4) {
                if (var10 != null) {
                    final ItemStack var11 = var10.áˆºÑ¢Õ();
                    var11.Â = ((clickedButton == 0) ? 1 : var11.Â());
                    GuiContainerCreative.Ñ¢á.á.HorizonCode_Horizon_È(var11, true);
                    GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(var11);
                }
                return;
            }
            if (var7 != null && var10 != null && var7.HorizonCode_Horizon_È(var10)) {
                if (clickedButton == 0) {
                    if (var5) {
                        var7.Â = var7.Â();
                    }
                    else if (var7.Â < var7.Â()) {
                        final ItemStack itemStack = var7;
                        ++itemStack.Â;
                    }
                }
                else if (var7.Â <= 1) {
                    var6.Â((ItemStack)null);
                }
                else {
                    final ItemStack itemStack2 = var7;
                    --itemStack2.Â;
                }
            }
            else if (var10 != null && var7 == null) {
                var6.Â(ItemStack.Â(var10));
                var7 = var6.á();
                if (var5) {
                    var7.Â = var7.Â();
                }
            }
            else {
                var6.Â((ItemStack)null);
            }
        }
        else {
            GuiContainerCreative.á.HorizonCode_Horizon_È((slotIn == null) ? slotId : slotIn.Ý, clickedButton, clickType, GuiContainerCreative.Ñ¢á.á);
            if (Container.Ý(clickedButton) == 2) {
                for (int var8 = 0; var8 < 9; ++var8) {
                    GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(GuiContainerCreative.á.HorizonCode_Horizon_È(45 + var8).HorizonCode_Horizon_È(), 36 + var8);
                }
            }
            else if (slotIn != null) {
                final ItemStack var9 = GuiContainerCreative.á.HorizonCode_Horizon_È(slotIn.Ý).HorizonCode_Horizon_È();
                GuiContainerCreative.Ñ¢á.Âµá€.HorizonCode_Horizon_È(var9, slotIn.Ý - GuiContainerCreative.á.Ý.size() + 9 + 36);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (GuiContainerCreative.Ñ¢á.Âµá€.Ø()) {
            super.HorizonCode_Horizon_È();
            this.ÇŽÉ.clear();
            Keyboard.enableRepeatEvents(true);
            (this.à = new GuiTextField(0, this.É, this.ˆÏ­ + 82, this.£á + 6, 89, this.É.HorizonCode_Horizon_È)).Ó(15);
            this.à.HorizonCode_Horizon_È(false);
            this.à.Âµá€(false);
            this.à.à(16777215);
            final int var1 = GuiContainerCreative.Ý;
            GuiContainerCreative.Ý = -1;
            this.Â(CreativeTabs.HorizonCode_Horizon_È[var1]);
            this.¥Æ = new CreativeCrafting(GuiContainerCreative.Ñ¢á);
            GuiContainerCreative.Ñ¢á.á.ŒÂ.HorizonCode_Horizon_È(this.¥Æ);
        }
        else {
            GuiContainerCreative.Ñ¢á.HorizonCode_Horizon_È(new GuiInventory(GuiContainerCreative.Ñ¢á.á));
        }
    }
    
    @Override
    public void q_() {
        super.q_();
        if (GuiContainerCreative.Ñ¢á.á != null && GuiContainerCreative.Ñ¢á.á.Ø­Ñ¢Ï­Ø­áˆº != null) {
            GuiContainerCreative.Ñ¢á.á.ŒÂ.Â(this.¥Æ);
        }
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (GuiContainerCreative.Ý != CreativeTabs.à.HorizonCode_Horizon_È()) {
            if (GameSettings.HorizonCode_Horizon_È(GuiContainerCreative.Ñ¢á.ŠÄ.Œá)) {
                this.Â(CreativeTabs.à);
            }
            else {
                super.HorizonCode_Horizon_È(typedChar, keyCode);
            }
        }
        else {
            if (this.ˆà) {
                this.ˆà = false;
                this.à.HorizonCode_Horizon_È("");
            }
            if (!this.HorizonCode_Horizon_È(keyCode)) {
                if (this.à.HorizonCode_Horizon_È(typedChar, keyCode)) {
                    this.áŒŠÆ();
                }
                else {
                    super.HorizonCode_Horizon_È(typedChar, keyCode);
                }
            }
        }
    }
    
    private void áŒŠÆ() {
        final HorizonCode_Horizon_È var1 = (HorizonCode_Horizon_È)GuiContainerCreative.á;
        var1.HorizonCode_Horizon_È.clear();
        for (final Item_1028566121 var3 : Item_1028566121.HorizonCode_Horizon_È) {
            if (var3 != null && var3.£á() != null) {
                var3.HorizonCode_Horizon_È(var3, null, var1.HorizonCode_Horizon_È);
            }
        }
        for (final Enchantment var7 : Enchantment.Â) {
            if (var7 != null && var7.Çªà¢ != null) {
                Items.Çªáˆºá.HorizonCode_Horizon_È(var7, var1.HorizonCode_Horizon_È);
            }
        }
        final Iterator var2 = var1.HorizonCode_Horizon_È.iterator();
        final String var8 = this.à.Ý().toLowerCase();
        while (var2.hasNext()) {
            final ItemStack var9 = var2.next();
            boolean var10 = false;
            for (final String var12 : var9.HorizonCode_Horizon_È(GuiContainerCreative.Ñ¢á.á, GuiContainerCreative.Ñ¢á.ŠÄ.¥É)) {
                if (!EnumChatFormatting.HorizonCode_Horizon_È(var12).toLowerCase().contains(var8)) {
                    continue;
                }
                var10 = true;
                break;
            }
            if (!var10) {
                var2.remove();
            }
        }
        var1.HorizonCode_Horizon_È(this.Ø­áŒŠá = 0.0f);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        final CreativeTabs var3 = CreativeTabs.HorizonCode_Horizon_È[GuiContainerCreative.Ý];
        if (var3.Ø()) {
            GlStateManager.ÂµÈ();
            this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È(var3.Ý(), new Object[0]), 8, 6, 4210752);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            final int var4 = mouseX - this.ˆÏ­;
            final int var5 = mouseY - this.£á;
            for (final CreativeTabs var9 : CreativeTabs.HorizonCode_Horizon_È) {
                if (this.HorizonCode_Horizon_È(var9, var4, var5)) {
                    return;
                }
            }
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void Â(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            final int var4 = mouseX - this.ˆÏ­;
            final int var5 = mouseY - this.£á;
            for (final CreativeTabs var9 : CreativeTabs.HorizonCode_Horizon_È) {
                if (this.HorizonCode_Horizon_È(var9, var4, var5)) {
                    this.Â(var9);
                    return;
                }
            }
        }
        super.Â(mouseX, mouseY, state);
    }
    
    private boolean ˆà() {
        return GuiContainerCreative.Ý != CreativeTabs.ˆÏ­.HorizonCode_Horizon_È() && CreativeTabs.HorizonCode_Horizon_È[GuiContainerCreative.Ý].áˆºÑ¢Õ() && ((HorizonCode_Horizon_È)GuiContainerCreative.á).HorizonCode_Horizon_È();
    }
    
    private void Â(final CreativeTabs p_147050_1_) {
        final int var2 = GuiContainerCreative.Ý;
        GuiContainerCreative.Ý = p_147050_1_.HorizonCode_Horizon_È();
        final HorizonCode_Horizon_È var3 = (HorizonCode_Horizon_È)GuiContainerCreative.á;
        this.Å.clear();
        var3.HorizonCode_Horizon_È.clear();
        p_147050_1_.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È);
        if (p_147050_1_ == CreativeTabs.ˆÏ­) {
            final Container var4 = GuiContainerCreative.Ñ¢á.á.ŒÂ;
            if (this.Ø == null) {
                this.Ø = var3.Ý;
            }
            var3.Ý = Lists.newArrayList();
            for (int var5 = 0; var5 < var4.Ý.size(); ++var5) {
                final Â var6 = new Â(var4.Ý.get(var5), var5);
                var3.Ý.add(var6);
                if (var5 >= 5 && var5 < 9) {
                    final int var7 = var5 - 5;
                    final int var8 = var7 / 2;
                    final int var9 = var7 % 2;
                    var6.Ø­áŒŠá = 9 + var8 * 54;
                    var6.Âµá€ = 6 + var9 * 27;
                }
                else if (var5 >= 0 && var5 < 5) {
                    var6.Âµá€ = -2000;
                    var6.Ø­áŒŠá = -2000;
                }
                else if (var5 < var4.Ý.size()) {
                    final int var7 = var5 - 9;
                    final int var8 = var7 % 9;
                    final int var9 = var7 / 9;
                    var6.Ø­áŒŠá = 9 + var8 * 18;
                    if (var5 >= 36) {
                        var6.Âµá€ = 112;
                    }
                    else {
                        var6.Âµá€ = 54 + var9 * 18;
                    }
                }
            }
            this.µà = new Slot(GuiContainerCreative.Â, 0, 173, 112);
            var3.Ý.add(this.µà);
        }
        else if (var2 == CreativeTabs.ˆÏ­.HorizonCode_Horizon_È()) {
            var3.Ý = this.Ø;
            this.Ø = null;
        }
        if (this.à != null) {
            if (p_147050_1_ == CreativeTabs.à) {
                this.à.Âµá€(true);
                this.à.Ø­áŒŠá(false);
                this.à.Â(true);
                this.à.HorizonCode_Horizon_È("");
                this.áŒŠÆ();
            }
            else {
                this.à.Âµá€(false);
                this.à.Ø­áŒŠá(true);
                this.à.Â(false);
            }
        }
        var3.HorizonCode_Horizon_È(this.Ø­áŒŠá = 0.0f);
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0 && this.ˆà()) {
            final int var2 = ((HorizonCode_Horizon_È)GuiContainerCreative.á).HorizonCode_Horizon_È.size() / 9 - 5 + 1;
            if (var1 > 0) {
                var1 = 1;
            }
            if (var1 < 0) {
                var1 = -1;
            }
            this.Ø­áŒŠá -= var1 / var2;
            this.Ø­áŒŠá = MathHelper.HorizonCode_Horizon_È(this.Ø­áŒŠá, 0.0f, 1.0f);
            ((HorizonCode_Horizon_È)GuiContainerCreative.á).HorizonCode_Horizon_È(this.Ø­áŒŠá);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        final boolean var4 = Mouse.isButtonDown(0);
        final int var5 = this.ˆÏ­;
        final int var6 = this.£á;
        final int var7 = var5 + 175;
        final int var8 = var6 + 18;
        final int var9 = var7 + 14;
        final int var10 = var8 + 112;
        if (!this.Ó && var4 && mouseX >= var7 && mouseY >= var8 && mouseX < var9 && mouseY < var10) {
            this.Âµá€ = this.ˆà();
        }
        if (!var4) {
            this.Âµá€ = false;
        }
        this.Ó = var4;
        if (this.Âµá€) {
            this.Ø­áŒŠá = (mouseY - var8 - 7.5f) / (var10 - var8 - 15.0f);
            this.Ø­áŒŠá = MathHelper.HorizonCode_Horizon_È(this.Ø­áŒŠá, 0.0f, 1.0f);
            ((HorizonCode_Horizon_È)GuiContainerCreative.á).HorizonCode_Horizon_È(this.Ø­áŒŠá);
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        for (final CreativeTabs var14 : CreativeTabs.HorizonCode_Horizon_È) {
            if (this.Â(var14, mouseX, mouseY)) {
                break;
            }
        }
        if (this.µà != null && GuiContainerCreative.Ý == CreativeTabs.ˆÏ­.HorizonCode_Horizon_È() && this.Ý(this.µà.Ø­áŒŠá, this.µà.Âµá€, 16, 16, mouseX, mouseY)) {
            this.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("inventory.binSlot", new Object[0]), mouseX, mouseY);
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Ó();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final ItemStack itemIn, final int x, final int y) {
        if (GuiContainerCreative.Ý == CreativeTabs.à.HorizonCode_Horizon_È()) {
            final List var4 = itemIn.HorizonCode_Horizon_È(GuiContainerCreative.Ñ¢á.á, GuiContainerCreative.Ñ¢á.ŠÄ.¥É);
            CreativeTabs var5 = itemIn.HorizonCode_Horizon_È().£á();
            if (var5 == null && itemIn.HorizonCode_Horizon_È() == Items.Çªáˆºá) {
                final Map var6 = EnchantmentHelper.HorizonCode_Horizon_È(itemIn);
                if (var6.size() == 1) {
                    final Enchantment var7 = Enchantment.HorizonCode_Horizon_È(var6.keySet().iterator().next());
                    for (final CreativeTabs var11 : CreativeTabs.HorizonCode_Horizon_È) {
                        if (var11.HorizonCode_Horizon_È(var7.Çªà¢)) {
                            var5 = var11;
                            break;
                        }
                    }
                }
            }
            if (var5 != null) {
                var4.add(1, new StringBuilder().append(EnumChatFormatting.ˆà).append(EnumChatFormatting.áˆºÑ¢Õ).append(I18n.HorizonCode_Horizon_È(var5.Ý(), new Object[0])).toString());
            }
            for (int var12 = 0; var12 < var4.size(); ++var12) {
                if (var12 == 0) {
                    var4.set(var12, itemIn.µÕ().Âµá€ + var4.get(var12));
                }
                else {
                    var4.set(var12, EnumChatFormatting.Ø + var4.get(var12));
                }
            }
            this.HorizonCode_Horizon_È(var4, x, y);
        }
        else {
            super.HorizonCode_Horizon_È(itemIn, x, y);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.Ý();
        final CreativeTabs var4 = CreativeTabs.HorizonCode_Horizon_È[GuiContainerCreative.Ý];
        for (final CreativeTabs var8 : CreativeTabs.HorizonCode_Horizon_È) {
            GuiContainerCreative.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiContainerCreative.HorizonCode_Horizon_È);
            if (var8.HorizonCode_Horizon_È() != GuiContainerCreative.Ý) {
                this.HorizonCode_Horizon_È(var8);
            }
        }
        GuiContainerCreative.Ñ¢á.¥à().HorizonCode_Horizon_È(new ResourceLocation_1975012498("textures/gui/container/creative_inventory/tab_" + var4.à()));
        this.Â(this.ˆÏ­, this.£á, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        this.à.HorizonCode_Horizon_È();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        final int var9 = this.ˆÏ­ + 175;
        final int var6 = this.£á + 18;
        final int var7 = var6 + 112;
        GuiContainerCreative.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiContainerCreative.HorizonCode_Horizon_È);
        if (var4.áˆºÑ¢Õ()) {
            this.Â(var9, var6 + (int)((var7 - var6 - 17) * this.Ø­áŒŠá), 232 + (this.ˆà() ? 0 : 12), 0, 12, 15);
        }
        this.HorizonCode_Horizon_È(var4);
        if (var4 == CreativeTabs.ˆÏ­) {
            GuiInventory.HorizonCode_Horizon_È(this.ˆÏ­ + 43, this.£á + 45, 20, this.ˆÏ­ + 43 - mouseX, this.£á + 45 - 30 - mouseY, GuiContainerCreative.Ñ¢á.á);
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final CreativeTabs p_147049_1_, final int p_147049_2_, final int p_147049_3_) {
        final int var4 = p_147049_1_.á();
        int var5 = 28 * var4;
        final byte var6 = 0;
        if (var4 == 5) {
            var5 = this.áˆºÑ¢Õ - 28 + 2;
        }
        else if (var4 > 0) {
            var5 += var4;
        }
        int var7;
        if (p_147049_1_.ˆÏ­()) {
            var7 = var6 - 32;
        }
        else {
            var7 = var6 + this.ÂµÈ;
        }
        return p_147049_2_ >= var5 && p_147049_2_ <= var5 + 28 && p_147049_3_ >= var7 && p_147049_3_ <= var7 + 32;
    }
    
    protected boolean Â(final CreativeTabs p_147052_1_, final int p_147052_2_, final int p_147052_3_) {
        final int var4 = p_147052_1_.á();
        int var5 = 28 * var4;
        final byte var6 = 0;
        if (var4 == 5) {
            var5 = this.áˆºÑ¢Õ - 28 + 2;
        }
        else if (var4 > 0) {
            var5 += var4;
        }
        int var7;
        if (p_147052_1_.ˆÏ­()) {
            var7 = var6 - 32;
        }
        else {
            var7 = var6 + this.ÂµÈ;
        }
        if (this.Ý(var5 + 3, var7 + 3, 23, 27, p_147052_2_, p_147052_3_)) {
            this.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È(p_147052_1_.Ý(), new Object[0]), p_147052_2_, p_147052_3_);
            return true;
        }
        return false;
    }
    
    protected void HorizonCode_Horizon_È(final CreativeTabs p_147051_1_) {
        final boolean var2 = p_147051_1_.HorizonCode_Horizon_È() == GuiContainerCreative.Ý;
        final boolean var3 = p_147051_1_.ˆÏ­();
        final int var4 = p_147051_1_.á();
        final int var5 = var4 * 28;
        int var6 = 0;
        int var7 = this.ˆÏ­ + 28 * var4;
        int var8 = this.£á;
        final byte var9 = 32;
        if (var2) {
            var6 += 32;
        }
        if (var4 == 5) {
            var7 = this.ˆÏ­ + this.áˆºÑ¢Õ - 28;
        }
        else if (var4 > 0) {
            var7 += var4;
        }
        if (var3) {
            var8 -= 28;
        }
        else {
            var6 += 64;
            var8 += this.ÂµÈ - 4;
        }
        GlStateManager.Ó();
        this.Â(var7, var8, var5, var6, 28, var9);
        GuiContainerCreative.ŠÄ = 100.0f;
        this.ŒÏ.HorizonCode_Horizon_È = 100.0f;
        var7 += 6;
        var8 += 8 + (var3 ? 1 : -1);
        GlStateManager.Âµá€();
        GlStateManager.ŠÄ();
        final ItemStack var10 = p_147051_1_.Ø­áŒŠá();
        this.ŒÏ.Â(var10, var7, var8);
        this.ŒÏ.HorizonCode_Horizon_È(this.É, var10, var7, var8);
        GlStateManager.Ó();
        this.ŒÏ.HorizonCode_Horizon_È = 0.0f;
        GuiContainerCreative.ŠÄ = 0.0f;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 0) {
            GuiContainerCreative.Ñ¢á.HorizonCode_Horizon_È(new GuiAchievements(this, GuiContainerCreative.Ñ¢á.á.c_()));
        }
        if (button.£à == 1) {
            GuiContainerCreative.Ñ¢á.HorizonCode_Horizon_È(new GuiStats(this, GuiContainerCreative.Ñ¢á.á.c_()));
        }
    }
    
    public int Ó() {
        return GuiContainerCreative.Ý;
    }
    
    static class HorizonCode_Horizon_È extends Container
    {
        public List HorizonCode_Horizon_È;
        private static final String Ó = "CL_00000753";
        
        public HorizonCode_Horizon_È(final EntityPlayer p_i1086_1_) {
            this.HorizonCode_Horizon_È = Lists.newArrayList();
            final InventoryPlayer var2 = p_i1086_1_.Ø­Ñ¢Ï­Ø­áˆº;
            for (int var3 = 0; var3 < 5; ++var3) {
                for (int var4 = 0; var4 < 9; ++var4) {
                    this.Â(new Slot(GuiContainerCreative.Â, var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
                }
            }
            for (int var3 = 0; var3 < 9; ++var3) {
                this.Â(new Slot(var2, var3, 9 + var3 * 18, 112));
            }
            this.HorizonCode_Horizon_È(0.0f);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
            return true;
        }
        
        public void HorizonCode_Horizon_È(final float p_148329_1_) {
            final int var2 = (this.HorizonCode_Horizon_È.size() + 8) / 9 - 5;
            int var3 = (int)(p_148329_1_ * var2 + 0.5);
            if (var3 < 0) {
                var3 = 0;
            }
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var5 = 0; var5 < 9; ++var5) {
                    final int var6 = var5 + (var4 + var3) * 9;
                    if (var6 >= 0 && var6 < this.HorizonCode_Horizon_È.size()) {
                        GuiContainerCreative.Â.Ý(var5 + var4 * 9, this.HorizonCode_Horizon_È.get(var6));
                    }
                    else {
                        GuiContainerCreative.Â.Ý(var5 + var4 * 9, null);
                    }
                }
            }
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È.size() > 45;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_75133_1_, final int p_75133_2_, final boolean p_75133_3_, final EntityPlayer p_75133_4_) {
        }
        
        @Override
        public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
            if (index >= this.Ý.size() - 9 && index < this.Ý.size()) {
                final Slot var3 = this.Ý.get(index);
                if (var3 != null && var3.Â()) {
                    var3.Â(null);
                }
            }
            return null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final ItemStack p_94530_1_, final Slot p_94530_2_) {
            return p_94530_2_.Âµá€ > 90;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final Slot p_94531_1_) {
            return p_94531_1_.Â instanceof InventoryPlayer || (p_94531_1_.Âµá€ > 90 && p_94531_1_.Ø­áŒŠá <= 162);
        }
    }
    
    class Â extends Slot
    {
        private final Slot Ó;
        private static final String à = "CL_00000754";
        
        public Â(final Slot p_i46313_2_, final int p_i46313_3_) {
            super(p_i46313_2_.Â, p_i46313_3_, 0, 0);
            this.Ó = p_i46313_2_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
            this.Ó.HorizonCode_Horizon_È(playerIn, stack);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final ItemStack stack) {
            return this.Ó.HorizonCode_Horizon_È(stack);
        }
        
        @Override
        public ItemStack HorizonCode_Horizon_È() {
            return this.Ó.HorizonCode_Horizon_È();
        }
        
        @Override
        public boolean Â() {
            return this.Ó.Â();
        }
        
        @Override
        public void Â(final ItemStack p_75215_1_) {
            this.Ó.Â(p_75215_1_);
        }
        
        @Override
        public void Ý() {
            this.Ó.Ý();
        }
        
        @Override
        public int Ø­áŒŠá() {
            return this.Ó.Ø­áŒŠá();
        }
        
        @Override
        public int Ý(final ItemStack p_178170_1_) {
            return this.Ó.Ý(p_178170_1_);
        }
        
        @Override
        public String Âµá€() {
            return this.Ó.Âµá€();
        }
        
        @Override
        public ItemStack HorizonCode_Horizon_È(final int p_75209_1_) {
            return this.Ó.HorizonCode_Horizon_È(p_75209_1_);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final IInventory p_75217_1_, final int p_75217_2_) {
            return this.Ó.HorizonCode_Horizon_È(p_75217_1_, p_75217_2_);
        }
    }
}
