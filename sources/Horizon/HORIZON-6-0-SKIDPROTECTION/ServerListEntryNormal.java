package HORIZON-6-0-SKIDPROTECTION;

import java.awt.image.BufferedImage;
import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.Validate;
import java.io.InputStream;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.base64.Base64;
import io.netty.buffer.Unpooled;
import com.google.common.base.Charsets;
import java.util.List;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended.HorizonCode_Horizon_È
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ThreadPoolExecutor Â;
    private static final ResourceLocation_1975012498 Ý;
    private static final ResourceLocation_1975012498 Ø­áŒŠá;
    private final GuiMultiplayer Âµá€;
    private final Minecraft Ó;
    private final ServerData à;
    private final ResourceLocation_1975012498 Ø;
    private String áŒŠÆ;
    private DynamicTexture áˆºÑ¢Õ;
    private long ÂµÈ;
    private static final String á = "CL_00000817";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
        Ý = new ResourceLocation_1975012498("textures/misc/unknown_server.png");
        Ø­áŒŠá = new ResourceLocation_1975012498("textures/gui/server_selection.png");
    }
    
    protected ServerListEntryNormal(final GuiMultiplayer p_i45048_1_, final ServerData p_i45048_2_) {
        this.Âµá€ = p_i45048_1_;
        this.à = p_i45048_2_;
        this.Ó = Minecraft.áŒŠà();
        this.Ø = new ResourceLocation_1975012498("servers/" + p_i45048_2_.Â + "/icon");
        this.áˆºÑ¢Õ = (DynamicTexture)this.Ó.¥à().Â(this.Ø);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected) {
        if (!this.à.Ø) {
            this.à.Ø = true;
            this.à.Âµá€ = -2L;
            this.à.Ø­áŒŠá = "";
            this.à.Ý = "";
            ServerListEntryNormal.Â.submit(new Runnable() {
                private static final String Â = "CL_00000818";
                
                @Override
                public void run() {
                    try {
                        ServerListEntryNormal.this.Âµá€.Ø().HorizonCode_Horizon_È(ServerListEntryNormal.this.à);
                    }
                    catch (UnknownHostException var2) {
                        ServerListEntryNormal.this.à.Âµá€ = -1L;
                        ServerListEntryNormal.this.à.Ø­áŒŠá = EnumChatFormatting.Âµá€ + "Can't resolve hostname";
                    }
                    catch (Exception var3) {
                        ServerListEntryNormal.this.à.Âµá€ = -1L;
                        ServerListEntryNormal.this.à.Ø­áŒŠá = EnumChatFormatting.Âµá€ + "Can't connect to server.";
                    }
                }
            });
        }
        final boolean var9 = this.à.Ó > 47;
        final boolean var10 = this.à.Ó < 47;
        final boolean var11 = var9 || var10;
        this.Ó.µà.HorizonCode_Horizon_È(this.à.HorizonCode_Horizon_È, x + 32 + 3, y + 1, 16777215);
        final List var12 = this.Ó.µà.Ý(this.à.Ø­áŒŠá, listWidth - 32 - 2);
        for (int var13 = 0; var13 < Math.min(var12.size(), 2); ++var13) {
            this.Ó.µà.HorizonCode_Horizon_È(var12.get(var13), x + 32 + 3, y + 12 + this.Ó.µà.HorizonCode_Horizon_È * var13, 8421504);
        }
        final String var14 = var11 ? (EnumChatFormatting.Âµá€ + this.à.à) : this.à.Ý;
        final int var15 = this.Ó.µà.HorizonCode_Horizon_È(var14);
        this.Ó.µà.HorizonCode_Horizon_È(var14, x + listWidth - var15 - 15 - 2, y + 1, 8421504);
        byte var16 = 0;
        String var17 = null;
        int var18;
        String var19;
        if (var11) {
            var18 = 5;
            var19 = (var9 ? "Client out of date!" : "Server out of date!");
            var17 = this.à.áŒŠÆ;
        }
        else if (this.à.Ø && this.à.Âµá€ != -2L) {
            if (this.à.Âµá€ < 0L) {
                var18 = 5;
            }
            else if (this.à.Âµá€ < 150L) {
                var18 = 0;
            }
            else if (this.à.Âµá€ < 300L) {
                var18 = 1;
            }
            else if (this.à.Âµá€ < 600L) {
                var18 = 2;
            }
            else if (this.à.Âµá€ < 1000L) {
                var18 = 3;
            }
            else {
                var18 = 4;
            }
            if (this.à.Âµá€ < 0L) {
                var19 = "(no connection)";
            }
            else {
                var19 = String.valueOf(this.à.Âµá€) + "ms";
                var17 = this.à.áŒŠÆ;
            }
        }
        else {
            var16 = 1;
            var18 = (int)(Minecraft.áƒ() / 100L + slotIndex * 2 & 0x7L);
            if (var18 > 4) {
                var18 = 8 - var18;
            }
            var19 = "Pinging...";
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.Ó.¥à().HorizonCode_Horizon_È(Gui_1808253012.áŒŠà);
        Gui_1808253012.HorizonCode_Horizon_È(x + listWidth - 15, y, var16 * 10, 176 + var18 * 8, 10, 8, 256.0f, 256.0f);
        if (this.à.Ý() != null && !this.à.Ý().equals(this.áŒŠÆ)) {
            this.áŒŠÆ = this.à.Ý();
            this.Ý();
            this.Âµá€.áŒŠÆ().Â();
        }
        if (this.áˆºÑ¢Õ != null) {
            this.HorizonCode_Horizon_È(x, y, this.Ø);
        }
        else {
            this.HorizonCode_Horizon_È(x, y, ServerListEntryNormal.Ý);
        }
        final int var20 = mouseX - x;
        final int var21 = mouseY - y;
        if (var20 >= listWidth - 15 && var20 <= listWidth - 5 && var21 >= 0 && var21 <= 8) {
            this.Âµá€.HorizonCode_Horizon_È(var19);
        }
        else if (var20 >= listWidth - var15 - 15 - 2 && var20 <= listWidth - 15 - 2 && var21 >= 0 && var21 <= 8) {
            this.Âµá€.HorizonCode_Horizon_È(var17);
        }
        if (this.Ó.ŠÄ.ÂµÕ || isSelected) {
            this.Ó.¥à().HorizonCode_Horizon_È(ServerListEntryNormal.Ø­áŒŠá);
            Gui_1808253012.HorizonCode_Horizon_È(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            final int var22 = mouseX - x;
            final int var23 = mouseY - y;
            if (this.Â()) {
                if (var22 < 32 && var22 > 16) {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            if (this.Âµá€.HorizonCode_Horizon_È(this, slotIndex)) {
                if (var22 < 16 && var23 < 16) {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            if (this.Âµá€.Â(this, slotIndex)) {
                if (var22 < 16 && var23 > 16) {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui_1808253012.HorizonCode_Horizon_È(x, y, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final int p_178012_1_, final int p_178012_2_, final ResourceLocation_1975012498 p_178012_3_) {
        this.Ó.¥à().HorizonCode_Horizon_È(p_178012_3_);
        GlStateManager.á();
        Gui_1808253012.HorizonCode_Horizon_È(p_178012_1_, p_178012_2_, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        GlStateManager.ÂµÈ();
    }
    
    private boolean Â() {
        return true;
    }
    
    private void Ý() {
        if (this.à.Ý() == null) {
            this.Ó.¥à().Ý(this.Ø);
            this.áˆºÑ¢Õ = null;
        }
        else {
            final ByteBuf var2 = Unpooled.copiedBuffer((CharSequence)this.à.Ý(), Charsets.UTF_8);
            final ByteBuf var3 = Base64.decode(var2);
            BufferedImage var4 = null;
            Label_0218: {
                try {
                    var4 = TextureUtil.HorizonCode_Horizon_È((InputStream)new ByteBufInputStream(var3));
                    Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    break Label_0218;
                }
                catch (Exception var5) {
                    ServerListEntryNormal.HorizonCode_Horizon_È.error("Invalid icon for server " + this.à.HorizonCode_Horizon_È + " (" + this.à.Â + ")", (Throwable)var5);
                    this.à.HorizonCode_Horizon_È((String)null);
                }
                finally {
                    var2.release();
                    var3.release();
                }
                return;
            }
            if (this.áˆºÑ¢Õ == null) {
                this.áˆºÑ¢Õ = new DynamicTexture(var4.getWidth(), var4.getHeight());
                this.Ó.¥à().HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ);
            }
            var4.getRGB(0, 0, var4.getWidth(), var4.getHeight(), this.áˆºÑ¢Õ.Ý(), 0, var4.getWidth());
            this.áˆºÑ¢Õ.Â();
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        if (p_148278_5_ <= 32) {
            if (p_148278_5_ < 32 && p_148278_5_ > 16 && this.Â()) {
                this.Âµá€.HorizonCode_Horizon_È(p_148278_1_);
                this.Âµá€.à();
                return true;
            }
            if (p_148278_5_ < 16 && p_148278_6_ < 16 && this.Âµá€.HorizonCode_Horizon_È(this, p_148278_1_)) {
                this.Âµá€.HorizonCode_Horizon_È(this, p_148278_1_, GuiScreen.£à());
                return true;
            }
            if (p_148278_5_ < 16 && p_148278_6_ > 16 && this.Âµá€.Â(this, p_148278_1_)) {
                this.Âµá€.Â(this, p_148278_1_, GuiScreen.£à());
                return true;
            }
        }
        this.Âµá€.HorizonCode_Horizon_È(p_148278_1_);
        if (Minecraft.áƒ() - this.ÂµÈ < 250L) {
            this.Âµá€.à();
        }
        this.ÂµÈ = Minecraft.áƒ();
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {
    }
    
    @Override
    public void Â(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX, final int relativeY) {
    }
    
    public ServerData HorizonCode_Horizon_È() {
        return this.à;
    }
}
