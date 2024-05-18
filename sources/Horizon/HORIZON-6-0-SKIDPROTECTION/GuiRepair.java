package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiRepair extends GuiContainer implements ICrafting
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private ContainerRepair Â;
    private GuiTextField Ý;
    private InventoryPlayer Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000738";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/anvil.png");
    }
    
    public GuiRepair(final InventoryPlayer p_i45508_1_, final World worldIn) {
        super(new ContainerRepair(p_i45508_1_, worldIn, Minecraft.áŒŠà().á));
        this.Ø­áŒŠá = p_i45508_1_;
        this.Â = (ContainerRepair)GuiRepair.á;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        Keyboard.enableRepeatEvents(true);
        final int var1 = (GuiRepair.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var2 = (GuiRepair.Ê - this.ÂµÈ) / 2;
        (this.Ý = new GuiTextField(0, this.É, var1 + 62, var2 + 24, 103, 12)).à(-1);
        this.Ý.Ø(-1);
        this.Ý.HorizonCode_Horizon_È(false);
        this.Ý.Ó(40);
        GuiRepair.á.Â(this);
        GuiRepair.á.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void q_() {
        super.q_();
        Keyboard.enableRepeatEvents(false);
        GuiRepair.á.Â(this);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        GlStateManager.Ó();
        GlStateManager.ÂµÈ();
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("container.repair", new Object[0]), 60, 6, 4210752);
        if (this.Â.HorizonCode_Horizon_È > 0) {
            int var3 = 8453920;
            boolean var4 = true;
            String var5 = I18n.HorizonCode_Horizon_È("container.repair.cost", this.Â.HorizonCode_Horizon_È);
            if (this.Â.HorizonCode_Horizon_È >= 40 && !GuiRepair.Ñ¢á.á.áˆºáˆºáŠ.Ø­áŒŠá) {
                var5 = I18n.HorizonCode_Horizon_È("container.repair.expensive", new Object[0]);
                var3 = 16736352;
            }
            else if (!this.Â.HorizonCode_Horizon_È(2).Â()) {
                var4 = false;
            }
            else if (!this.Â.HorizonCode_Horizon_È(2).HorizonCode_Horizon_È(this.Ø­áŒŠá.Ø­áŒŠá)) {
                var3 = 16736352;
            }
            if (var4) {
                final int var6 = 0xFF000000 | (var3 & 0xFCFCFC) >> 2 | (var3 & 0xFF000000);
                final int var7 = this.áˆºÑ¢Õ - 8 - this.É.HorizonCode_Horizon_È(var5);
                final byte var8 = 67;
                if (this.É.HorizonCode_Horizon_È()) {
                    Gui_1808253012.HorizonCode_Horizon_È(var7 - 3, var8 - 2, this.áˆºÑ¢Õ - 7, var8 + 10, -16777216);
                    Gui_1808253012.HorizonCode_Horizon_È(var7 - 2, var8 - 1, this.áˆºÑ¢Õ - 8, var8 + 9, -12895429);
                }
                else {
                    this.É.HorizonCode_Horizon_È(var5, var7, var8 + 1, var6);
                    this.É.HorizonCode_Horizon_È(var5, var7 + 1, var8, var6);
                    this.É.HorizonCode_Horizon_È(var5, var7 + 1, var8 + 1, var6);
                }
                this.É.HorizonCode_Horizon_È(var5, var7, var8, var3);
            }
        }
        GlStateManager.Âµá€();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (this.Ý.HorizonCode_Horizon_È(typedChar, keyCode)) {
            this.Ó();
        }
        else {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
    }
    
    private void Ó() {
        String var1 = this.Ý.Ý();
        final Slot var2 = this.Â.HorizonCode_Horizon_È(0);
        if (var2 != null && var2.Â() && !var2.HorizonCode_Horizon_È().¥Æ() && var1.equals(var2.HorizonCode_Horizon_È().µà())) {
            var1 = "";
        }
        this.Â.HorizonCode_Horizon_È(var1);
        GuiRepair.Ñ¢á.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C17PacketCustomPayload("MC|ItemName", new PacketBuffer(Unpooled.buffer()).HorizonCode_Horizon_È(var1)));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        GlStateManager.Ó();
        GlStateManager.ÂµÈ();
        this.Ý.HorizonCode_Horizon_È();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiRepair.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiRepair.HorizonCode_Horizon_È);
        final int var4 = (GuiRepair.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiRepair.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        this.Â(var4 + 59, var5 + 20, 0, this.ÂµÈ + (this.Â.HorizonCode_Horizon_È(0).Â() ? 0 : 16), 110, 16);
        if ((this.Â.HorizonCode_Horizon_È(0).Â() || this.Â.HorizonCode_Horizon_È(1).Â()) && !this.Â.HorizonCode_Horizon_È(2).Â()) {
            this.Â(var4 + 99, var5 + 45, this.áˆºÑ¢Õ, 0, 28, 21);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_71110_1_, final List p_71110_2_) {
        this.HorizonCode_Horizon_È(p_71110_1_, 0, p_71110_1_.HorizonCode_Horizon_È(0).HorizonCode_Horizon_È());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
        if (p_71111_2_ == 0) {
            this.Ý.HorizonCode_Horizon_È((p_71111_3_ == null) ? "" : p_71111_3_.µà());
            this.Ý.Ý(p_71111_3_ != null);
            if (p_71111_3_ != null) {
                this.Ó();
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Container p_175173_1_, final IInventory p_175173_2_) {
    }
}
