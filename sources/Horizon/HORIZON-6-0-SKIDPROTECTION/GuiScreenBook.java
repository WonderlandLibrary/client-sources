package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import java.io.IOException;
import io.netty.buffer.Unpooled;
import org.lwjgl.input.Keyboard;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class GuiScreenBook extends GuiScreen
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private final EntityPlayer Ý;
    private final ItemStack Ø­áŒŠá;
    private final boolean Âµá€;
    private boolean Ó;
    private boolean à;
    private int Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private int á;
    private NBTTagList ˆÏ­;
    private String £á;
    private List Å;
    private int £à;
    private HorizonCode_Horizon_È µà;
    private HorizonCode_Horizon_È ˆà;
    private GuiButton ¥Æ;
    private GuiButton Ø­à;
    private GuiButton µÕ;
    private GuiButton Æ;
    private static final String áƒ = "CL_00000744";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ResourceLocation_1975012498("textures/gui/book.png");
    }
    
    public GuiScreenBook(final EntityPlayer p_i1080_1_, final ItemStack p_i1080_2_, final boolean p_i1080_3_) {
        this.áŒŠÆ = 192;
        this.áˆºÑ¢Õ = 192;
        this.ÂµÈ = 1;
        this.£á = "";
        this.£à = -1;
        this.Ý = p_i1080_1_;
        this.Ø­áŒŠá = p_i1080_2_;
        this.Âµá€ = p_i1080_3_;
        if (p_i1080_2_.£á()) {
            final NBTTagCompound var4 = p_i1080_2_.Å();
            this.ˆÏ­ = var4.Ý("pages", 8);
            if (this.ˆÏ­ != null) {
                this.ˆÏ­ = (NBTTagList)this.ˆÏ­.Â();
                this.ÂµÈ = this.ˆÏ­.Âµá€();
                if (this.ÂµÈ < 1) {
                    this.ÂµÈ = 1;
                }
            }
        }
        if (this.ˆÏ­ == null && p_i1080_3_) {
            (this.ˆÏ­ = new NBTTagList()).HorizonCode_Horizon_È(new NBTTagString(""));
            this.ÂµÈ = 1;
        }
    }
    
    @Override
    public void Ý() {
        super.Ý();
        ++this.Ø;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        Keyboard.enableRepeatEvents(true);
        if (this.Âµá€) {
            this.ÇŽÉ.add(this.Ø­à = new GuiButton(3, GuiScreenBook.Çªà¢ / 2 - 100, 4 + this.áˆºÑ¢Õ, 98, 20, I18n.HorizonCode_Horizon_È("book.signButton", new Object[0])));
            this.ÇŽÉ.add(this.¥Æ = new GuiButton(0, GuiScreenBook.Çªà¢ / 2 + 2, 4 + this.áˆºÑ¢Õ, 98, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
            this.ÇŽÉ.add(this.µÕ = new GuiButton(5, GuiScreenBook.Çªà¢ / 2 - 100, 4 + this.áˆºÑ¢Õ, 98, 20, I18n.HorizonCode_Horizon_È("book.finalizeButton", new Object[0])));
            this.ÇŽÉ.add(this.Æ = new GuiButton(4, GuiScreenBook.Çªà¢ / 2 + 2, 4 + this.áˆºÑ¢Õ, 98, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        }
        else {
            this.ÇŽÉ.add(this.¥Æ = new GuiButton(0, GuiScreenBook.Çªà¢ / 2 - 100, 4 + this.áˆºÑ¢Õ, 200, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        }
        final int var1 = (GuiScreenBook.Çªà¢ - this.áŒŠÆ) / 2;
        final byte var2 = 2;
        this.ÇŽÉ.add(this.µà = new HorizonCode_Horizon_È(1, var1 + 120, var2 + 154, true));
        this.ÇŽÉ.add(this.ˆà = new HorizonCode_Horizon_È(2, var1 + 38, var2 + 154, false));
        this.à();
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    private void à() {
        this.µà.ˆà = (!this.à && (this.á < this.ÂµÈ - 1 || this.Âµá€));
        this.ˆà.ˆà = (!this.à && this.á > 0);
        this.¥Æ.ˆà = (!this.Âµá€ || !this.à);
        if (this.Âµá€) {
            this.Ø­à.ˆà = !this.à;
            this.Æ.ˆà = this.à;
            this.µÕ.ˆà = this.à;
            this.µÕ.µà = (this.£á.trim().length() > 0);
        }
    }
    
    private void HorizonCode_Horizon_È(final boolean p_146462_1_) throws IOException {
        if (this.Âµá€ && this.Ó && this.ˆÏ­ != null) {
            while (this.ˆÏ­.Âµá€() > 1) {
                final String var2 = this.ˆÏ­.Ó(this.ˆÏ­.Âµá€() - 1);
                if (var2.length() != 0) {
                    break;
                }
                this.ˆÏ­.HorizonCode_Horizon_È(this.ˆÏ­.Âµá€() - 1);
            }
            if (this.Ø­áŒŠá.£á()) {
                final NBTTagCompound var3 = this.Ø­áŒŠá.Å();
                var3.HorizonCode_Horizon_È("pages", this.ˆÏ­);
            }
            else {
                this.Ø­áŒŠá.HorizonCode_Horizon_È("pages", this.ˆÏ­);
            }
            String var2 = "MC|BEdit";
            if (p_146462_1_) {
                var2 = "MC|BSign";
                this.Ø­áŒŠá.HorizonCode_Horizon_È("author", new NBTTagString(this.Ý.v_()));
                this.Ø­áŒŠá.HorizonCode_Horizon_È("title", new NBTTagString(this.£á.trim()));
                for (int var4 = 0; var4 < this.ˆÏ­.Âµá€(); ++var4) {
                    String var5 = this.ˆÏ­.Ó(var4);
                    final ChatComponentText var6 = new ChatComponentText(var5);
                    var5 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6);
                    this.ˆÏ­.HorizonCode_Horizon_È(var4, new NBTTagString(var5));
                }
                this.Ø­áŒŠá.HorizonCode_Horizon_È(Items.ÇŽÊ);
            }
            final PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());
            var7.HorizonCode_Horizon_È(this.Ø­áŒŠá);
            GuiScreenBook.Ñ¢á.µÕ().HorizonCode_Horizon_È(new C17PacketCustomPayload(var2, var7));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 0) {
                GuiScreenBook.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                this.HorizonCode_Horizon_È(false);
            }
            else if (button.£à == 3 && this.Âµá€) {
                this.à = true;
            }
            else if (button.£à == 1) {
                if (this.á < this.ÂµÈ - 1) {
                    ++this.á;
                }
                else if (this.Âµá€) {
                    this.Ø();
                    if (this.á < this.ÂµÈ - 1) {
                        ++this.á;
                    }
                }
            }
            else if (button.£à == 2) {
                if (this.á > 0) {
                    --this.á;
                }
            }
            else if (button.£à == 5 && this.à) {
                this.HorizonCode_Horizon_È(true);
                GuiScreenBook.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
            }
            else if (button.£à == 4 && this.à) {
                this.à = false;
            }
            this.à();
        }
    }
    
    private void Ø() {
        if (this.ˆÏ­ != null && this.ˆÏ­.Âµá€() < 50) {
            this.ˆÏ­.HorizonCode_Horizon_È(new NBTTagString(""));
            ++this.ÂµÈ;
            this.Ó = true;
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        super.HorizonCode_Horizon_È(typedChar, keyCode);
        if (this.Âµá€) {
            if (this.à) {
                this.Ý(typedChar, keyCode);
            }
            else {
                this.Â(typedChar, keyCode);
            }
        }
    }
    
    private void Â(final char p_146463_1_, final int p_146463_2_) {
        if (GuiScreen.Âµá€(p_146463_2_)) {
            this.Â(GuiScreen.ÂµÈ());
        }
        else {
            switch (p_146463_2_) {
                case 14: {
                    final String var3 = this.áŒŠÆ();
                    if (var3.length() > 0) {
                        this.HorizonCode_Horizon_È(var3.substring(0, var3.length() - 1));
                    }
                }
                case 28:
                case 156: {
                    this.Â("\n");
                }
                default: {
                    if (ChatAllowedCharacters.HorizonCode_Horizon_È(p_146463_1_)) {
                        this.Â(Character.toString(p_146463_1_));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void Ý(final char p_146460_1_, final int p_146460_2_) throws IOException {
        switch (p_146460_2_) {
            case 14: {
                if (!this.£á.isEmpty()) {
                    this.£á = this.£á.substring(0, this.£á.length() - 1);
                    this.à();
                }
            }
            case 28:
            case 156: {
                if (!this.£á.isEmpty()) {
                    this.HorizonCode_Horizon_È(true);
                    GuiScreenBook.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                }
            }
            default: {
                if (this.£á.length() < 16 && ChatAllowedCharacters.HorizonCode_Horizon_È(p_146460_1_)) {
                    this.£á = String.valueOf(this.£á) + Character.toString(p_146460_1_);
                    this.à();
                    this.Ó = true;
                }
            }
        }
    }
    
    private String áŒŠÆ() {
        return (this.ˆÏ­ != null && this.á >= 0 && this.á < this.ˆÏ­.Âµá€()) ? this.ˆÏ­.Ó(this.á) : "";
    }
    
    private void HorizonCode_Horizon_È(final String p_146457_1_) {
        if (this.ˆÏ­ != null && this.á >= 0 && this.á < this.ˆÏ­.Âµá€()) {
            this.ˆÏ­.HorizonCode_Horizon_È(this.á, new NBTTagString(p_146457_1_));
            this.Ó = true;
        }
    }
    
    private void Â(final String p_146459_1_) {
        final String var2 = this.áŒŠÆ();
        final String var3 = String.valueOf(var2) + p_146459_1_;
        final int var4 = this.É.Â(String.valueOf(var3) + EnumChatFormatting.HorizonCode_Horizon_È + "_", 118);
        if (var4 <= 128 && var3.length() < 256) {
            this.HorizonCode_Horizon_È(var3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenBook.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiScreenBook.Â);
        final int var4 = (GuiScreenBook.Çªà¢ - this.áŒŠÆ) / 2;
        final byte var5 = 2;
        this.Â(var4, var5, 0, 0, this.áŒŠÆ, this.áˆºÑ¢Õ);
        if (this.à) {
            String var6 = this.£á;
            if (this.Âµá€) {
                if (this.Ø / 6 % 2 == 0) {
                    var6 = String.valueOf(var6) + EnumChatFormatting.HorizonCode_Horizon_È + "_";
                }
                else {
                    var6 = String.valueOf(var6) + EnumChatFormatting.Ø + "_";
                }
            }
            final String var7 = I18n.HorizonCode_Horizon_È("book.editTitle", new Object[0]);
            final int var8 = this.É.HorizonCode_Horizon_È(var7);
            this.É.HorizonCode_Horizon_È(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
            final int var9 = this.É.HorizonCode_Horizon_È(var6);
            this.É.HorizonCode_Horizon_È(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
            final String var10 = I18n.HorizonCode_Horizon_È("book.byAuthor", this.Ý.v_());
            final int var11 = this.É.HorizonCode_Horizon_È(var10);
            this.É.HorizonCode_Horizon_È(EnumChatFormatting.áŒŠÆ + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
            final String var12 = I18n.HorizonCode_Horizon_È("book.finalizeWarning", new Object[0]);
            this.É.HorizonCode_Horizon_È(var12, var4 + 36, var5 + 80, 116, 0);
        }
        else {
            final String var6 = I18n.HorizonCode_Horizon_È("book.pageIndicator", this.á + 1, this.ÂµÈ);
            String var7 = "";
            if (this.ˆÏ­ != null && this.á >= 0 && this.á < this.ˆÏ­.Âµá€()) {
                var7 = this.ˆÏ­.Ó(this.á);
            }
            if (this.Âµá€) {
                if (this.É.Â()) {
                    var7 = String.valueOf(var7) + "_";
                }
                else if (this.Ø / 6 % 2 == 0) {
                    var7 = String.valueOf(var7) + EnumChatFormatting.HorizonCode_Horizon_È + "_";
                }
                else {
                    var7 = String.valueOf(var7) + EnumChatFormatting.Ø + "_";
                }
            }
            else if (this.£à != this.á) {
                if (ItemEditableBook.Â(this.Ø­áŒŠá.Å())) {
                    try {
                        final IChatComponent var13 = IChatComponent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var7);
                        this.Å = ((var13 != null) ? GuiUtilRenderComponents.HorizonCode_Horizon_È(var13, 116, this.É, true, true) : null);
                    }
                    catch (JsonParseException var18) {
                        this.Å = null;
                    }
                }
                else {
                    final ChatComponentText var14 = new ChatComponentText(String.valueOf(EnumChatFormatting.Âµá€.toString()) + "* Invalid book tag *");
                    this.Å = Lists.newArrayList((Iterable)var14);
                }
                this.£à = this.á;
            }
            final int var8 = this.É.HorizonCode_Horizon_È(var6);
            this.É.HorizonCode_Horizon_È(var6, var4 - var8 + this.áŒŠÆ - 44, var5 + 16, 0);
            if (this.Å == null) {
                this.É.HorizonCode_Horizon_È(var7, var4 + 36, var5 + 16 + 16, 116, 0);
            }
            else {
                for (int var9 = Math.min(128 / this.É.HorizonCode_Horizon_È, this.Å.size()), var15 = 0; var15 < var9; ++var15) {
                    final IChatComponent var16 = this.Å.get(var15);
                    this.É.HorizonCode_Horizon_È(var16.Ø(), var4 + 36, var5 + 16 + 16 + var15 * this.É.HorizonCode_Horizon_È, 0);
                }
                final IChatComponent var17 = this.HorizonCode_Horizon_È(mouseX, mouseY);
                if (var17 != null) {
                    this.HorizonCode_Horizon_È(var17, mouseX, mouseY);
                }
            }
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            final IChatComponent var4 = this.HorizonCode_Horizon_È(mouseX, mouseY);
            if (this.HorizonCode_Horizon_È(var4)) {
                return;
            }
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final IChatComponent p_175276_1_) {
        final ClickEvent var2 = (p_175276_1_ == null) ? null : p_175276_1_.à().Ø();
        if (var2 == null) {
            return false;
        }
        if (var2.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Ó) {
            final String var3 = var2.Â();
            try {
                final int var4 = Integer.parseInt(var3) - 1;
                if (var4 >= 0 && var4 < this.ÂµÈ && var4 != this.á) {
                    this.á = var4;
                    this.à();
                    return true;
                }
            }
            catch (Throwable t) {}
            return false;
        }
        final boolean var5 = super.HorizonCode_Horizon_È(p_175276_1_);
        if (var5 && var2.HorizonCode_Horizon_È() == ClickEvent.HorizonCode_Horizon_È.Ý) {
            GuiScreenBook.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
        return var5;
    }
    
    public IChatComponent HorizonCode_Horizon_È(final int p_175385_1_, final int p_175385_2_) {
        if (this.Å == null) {
            return null;
        }
        final int var3 = p_175385_1_ - (GuiScreenBook.Çªà¢ - this.áŒŠÆ) / 2 - 36;
        final int var4 = p_175385_2_ - 2 - 16 - 16;
        if (var3 < 0 || var4 < 0) {
            return null;
        }
        final int var5 = Math.min(128 / this.É.HorizonCode_Horizon_È, this.Å.size());
        if (var3 <= 116 && var4 < GuiScreenBook.Ñ¢á.µà.HorizonCode_Horizon_È * var5 + var5) {
            final int var6 = var4 / GuiScreenBook.Ñ¢á.µà.HorizonCode_Horizon_È;
            if (var6 >= 0 && var6 < this.Å.size()) {
                final IChatComponent var7 = this.Å.get(var6);
                int var8 = 0;
                for (final IChatComponent var10 : var7) {
                    if (var10 instanceof ChatComponentText) {
                        var8 += GuiScreenBook.Ñ¢á.µà.HorizonCode_Horizon_È(((ChatComponentText)var10).HorizonCode_Horizon_È());
                        if (var8 > var3) {
                            return var10;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    static class HorizonCode_Horizon_È extends GuiButton
    {
        private final boolean HorizonCode_Horizon_È;
        private static final String Â = "CL_00000745";
        
        public HorizonCode_Horizon_È(final int p_i46316_1_, final int p_i46316_2_, final int p_i46316_3_, final boolean p_i46316_4_) {
            super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
            this.HorizonCode_Horizon_È = p_i46316_4_;
        }
        
        @Override
        public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
            if (this.ˆà) {
                final boolean var4 = mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á;
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                mc.¥à().HorizonCode_Horizon_È(GuiScreenBook.Â);
                int var5 = 0;
                int var6 = 192;
                if (var4) {
                    var5 += 23;
                }
                if (!this.HorizonCode_Horizon_È) {
                    var6 += 13;
                }
                this.Â(this.ˆÏ­, this.£á, var5, var6, 23, 13);
            }
        }
    }
}
