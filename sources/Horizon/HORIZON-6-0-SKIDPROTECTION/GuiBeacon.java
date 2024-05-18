package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiBeacon extends GuiContainer
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private IInventory Ý;
    private Ý Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00000739";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ResourceLocation_1975012498("textures/gui/container/beacon.png");
    }
    
    public GuiBeacon(final InventoryPlayer p_i45507_1_, final IInventory p_i45507_2_) {
        super(new ContainerBeacon(p_i45507_1_, p_i45507_2_));
        this.Ý = p_i45507_2_;
        this.áˆºÑ¢Õ = 230;
        this.ÂµÈ = 219;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        this.ÇŽÉ.add(this.Ø­áŒŠá = new Ý(-1, this.ˆÏ­ + 164, this.£á + 107));
        this.ÇŽÉ.add(new Â(-2, this.ˆÏ­ + 190, this.£á + 107));
        this.Âµá€ = true;
        this.Ø­áŒŠá.µà = false;
    }
    
    @Override
    public void Ý() {
        super.Ý();
        final int var1 = this.Ý.HorizonCode_Horizon_È(0);
        final int var2 = this.Ý.HorizonCode_Horizon_È(1);
        final int var3 = this.Ý.HorizonCode_Horizon_È(2);
        if (this.Âµá€ && var1 >= 0) {
            this.Âµá€ = false;
            for (int var4 = 0; var4 <= 2; ++var4) {
                final int var5 = TileEntityBeacon.Âµá€[var4].length;
                final int var6 = var5 * 22 + (var5 - 1) * 2;
                for (int var7 = 0; var7 < var5; ++var7) {
                    final int var8 = TileEntityBeacon.Âµá€[var4][var7].É;
                    final Ø­áŒŠá var9 = new Ø­áŒŠá(var4 << 8 | var8, this.ˆÏ­ + 76 + var7 * 24 - var6 / 2, this.£á + 22 + var4 * 25, var8, var4);
                    this.ÇŽÉ.add(var9);
                    if (var4 >= var1) {
                        var9.µà = false;
                    }
                    else if (var8 == var2) {
                        var9.Â(true);
                    }
                }
            }
            final byte var10 = 3;
            final int var5 = TileEntityBeacon.Âµá€[var10].length + 1;
            final int var6 = var5 * 22 + (var5 - 1) * 2;
            for (int var7 = 0; var7 < var5 - 1; ++var7) {
                final int var8 = TileEntityBeacon.Âµá€[var10][var7].É;
                final Ø­áŒŠá var9 = new Ø­áŒŠá(var10 << 8 | var8, this.ˆÏ­ + 167 + var7 * 24 - var6 / 2, this.£á + 47, var8, var10);
                this.ÇŽÉ.add(var9);
                if (var10 >= var1) {
                    var9.µà = false;
                }
                else if (var8 == var3) {
                    var9.Â(true);
                }
            }
            if (var2 > 0) {
                final Ø­áŒŠá var11 = new Ø­áŒŠá(var10 << 8 | var2, this.ˆÏ­ + 167 + (var5 - 1) * 24 - var6 / 2, this.£á + 47, var2, var10);
                this.ÇŽÉ.add(var11);
                if (var10 >= var1) {
                    var11.µà = false;
                }
                else if (var2 == var3) {
                    var11.Â(true);
                }
            }
        }
        this.Ø­áŒŠá.µà = (this.Ý.á(0) != null && var2 > 0);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == -2) {
            GuiBeacon.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
        else if (button.£à == -1) {
            final String var2 = "MC|Beacon";
            final PacketBuffer var3 = new PacketBuffer(Unpooled.buffer());
            var3.writeInt(this.Ý.HorizonCode_Horizon_È(1));
            var3.writeInt(this.Ý.HorizonCode_Horizon_È(2));
            GuiBeacon.Ñ¢á.µÕ().HorizonCode_Horizon_È(new C17PacketCustomPayload(var2, var3));
            GuiBeacon.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
        else if (button instanceof Ø­áŒŠá) {
            if (((Ø­áŒŠá)button).HorizonCode_Horizon_È()) {
                return;
            }
            final int var4 = button.£à;
            final int var5 = var4 & 0xFF;
            final int var6 = var4 >> 8;
            if (var6 < 3) {
                this.Ý.HorizonCode_Horizon_È(1, var5);
            }
            else {
                this.Ý.HorizonCode_Horizon_È(2, var5);
            }
            this.ÇŽÉ.clear();
            this.HorizonCode_Horizon_È();
            this.Ý();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        RenderHelper.HorizonCode_Horizon_È();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
        for (final GuiButton var4 : this.ÇŽÉ) {
            if (var4.Ý()) {
                var4.Â(mouseX - this.ˆÏ­, mouseY - this.£á);
                break;
            }
        }
        RenderHelper.Ý();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiBeacon.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiBeacon.Â);
        final int var4 = (GuiBeacon.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiBeacon.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        this.ŒÏ.HorizonCode_Horizon_È = 100.0f;
        this.ŒÏ.Â(new ItemStack(Items.µ), var4 + 42, var5 + 109);
        this.ŒÏ.Â(new ItemStack(Items.áŒŠÆ), var4 + 42 + 22, var5 + 109);
        this.ŒÏ.Â(new ItemStack(Items.ÂµÈ), var4 + 42 + 44, var5 + 109);
        this.ŒÏ.Â(new ItemStack(Items.áˆºÑ¢Õ), var4 + 42 + 66, var5 + 109);
        this.ŒÏ.HorizonCode_Horizon_È = 0.0f;
    }
    
    static class HorizonCode_Horizon_È extends GuiButton
    {
        private final ResourceLocation_1975012498 HorizonCode_Horizon_È;
        private final int Â;
        private final int Ý;
        private boolean Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000743";
        
        protected HorizonCode_Horizon_È(final int p_i1077_1_, final int p_i1077_2_, final int p_i1077_3_, final ResourceLocation_1975012498 p_i1077_4_, final int p_i1077_5_, final int p_i1077_6_) {
            super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
            this.HorizonCode_Horizon_È = p_i1077_4_;
            this.Â = p_i1077_5_;
            this.Ý = p_i1077_6_;
        }
        
        @Override
        public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
            if (this.ˆà) {
                mc.¥à().HorizonCode_Horizon_È(GuiBeacon.Â);
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                this.¥Æ = (mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á);
                final short var4 = 219;
                int var5 = 0;
                if (!this.µà) {
                    var5 += this.ÂµÈ * 2;
                }
                else if (this.Ø­áŒŠá) {
                    var5 += this.ÂµÈ * 1;
                }
                else if (this.¥Æ) {
                    var5 += this.ÂµÈ * 3;
                }
                this.Â(this.ˆÏ­, this.£á, var5, var4, this.ÂµÈ, this.á);
                if (!GuiBeacon.Â.equals(this.HorizonCode_Horizon_È)) {
                    mc.¥à().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
                }
                this.Â(this.ˆÏ­ + 2, this.£á + 2, this.Â, this.Ý, 18, 18);
            }
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá;
        }
        
        public void Â(final boolean p_146140_1_) {
            this.Ø­áŒŠá = p_146140_1_;
        }
    }
    
    class Â extends HorizonCode_Horizon_È
    {
        private static final String Â = "CL_00000740";
        
        public Â(final int p_i1074_2_, final int p_i1074_3_, final int p_i1074_4_) {
            super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.Â, 112, 220);
        }
        
        @Override
        public void Â(final int mouseX, final int mouseY) {
            GuiScreen.this.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0]), mouseX, mouseY);
        }
    }
    
    class Ý extends HorizonCode_Horizon_È
    {
        private static final String Â = "CL_00000741";
        
        public Ý(final int p_i1075_2_, final int p_i1075_3_, final int p_i1075_4_) {
            super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.Â, 90, 220);
        }
        
        @Override
        public void Â(final int mouseX, final int mouseY) {
            GuiScreen.this.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("gui.done", new Object[0]), mouseX, mouseY);
        }
    }
    
    class Ø­áŒŠá extends HorizonCode_Horizon_È
    {
        private final int Â;
        private final int Ý;
        private static final String Ø­áŒŠá = "CL_00000742";
        
        public Ø­áŒŠá(final int p_i1076_2_, final int p_i1076_3_, final int p_i1076_4_, final int p_i1076_5_, final int p_i1076_6_) {
            super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.áŒŠÆ, 0 + Potion.HorizonCode_Horizon_È[p_i1076_5_].Ó() % 8 * 18, 198 + Potion.HorizonCode_Horizon_È[p_i1076_5_].Ó() / 8 * 18);
            this.Â = p_i1076_5_;
            this.Ý = p_i1076_6_;
        }
        
        @Override
        public void Â(final int mouseX, final int mouseY) {
            String var3 = I18n.HorizonCode_Horizon_È(Potion.HorizonCode_Horizon_È[this.Â].Ø­áŒŠá(), new Object[0]);
            if (this.Ý >= 3 && this.Â != Potion.á.É) {
                var3 = String.valueOf(var3) + " II";
            }
            GuiScreen.this.HorizonCode_Horizon_È(var3, mouseX, mouseY);
        }
    }
}
