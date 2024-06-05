package net.minecraft.client.gui.spectator.categories;

import net.minecraft.scoreboard.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.client.gui.spectator.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

class TeamSelectionObject implements ISpectatorMenuObject
{
    private final ScorePlayerTeam field_178676_b;
    private final ResourceLocation field_178677_c;
    private final List field_178675_d;
    
    public TeamSelectionObject(final ScorePlayerTeam p_i45492_2_) {
        this.field_178676_b = p_i45492_2_;
        this.field_178675_d = Lists.newArrayList();
        for (final String var4 : p_i45492_2_.getMembershipCollection()) {
            final NetworkPlayerInfo var5 = Minecraft.getMinecraft().getNetHandler().func_175104_a(var4);
            if (var5 != null) {
                this.field_178675_d.add(var5);
            }
        }
        if (!this.field_178675_d.isEmpty()) {
            final String var6 = this.field_178675_d.get(new Random().nextInt(this.field_178675_d.size())).func_178845_a().getName();
            AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c = AbstractClientPlayer.getLocationSkin(var6), var6);
        }
        else {
            this.field_178677_c = DefaultPlayerSkin.func_177335_a();
        }
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu p_178661_1_) {
        p_178661_1_.func_178647_a(new TeleportToPlayer(this.field_178675_d));
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText(this.field_178676_b.func_96669_c());
    }
    
    @Override
    public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {
        int var3 = -1;
        final String var4 = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
        if (var4.length() >= 2) {
            var3 = Minecraft.getMinecraft().fontRendererObj.func_175064_b(var4.charAt(1));
        }
        if (var3 >= 0) {
            final float var5 = (var3 >> 16 & 0xFF) / 255.0f;
            final float var6 = (var3 >> 8 & 0xFF) / 255.0f;
            final float var7 = (var3 & 0xFF) / 255.0f;
            Gui.drawRect(1, 1, 15, 15, MathHelper.func_180183_b(var5 * p_178663_1_, var6 * p_178663_1_, var7 * p_178663_1_) | p_178663_2_ << 24);
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
        GlStateManager.color(p_178663_1_, p_178663_1_, p_178663_1_, p_178663_2_ / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 8.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 40.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return !this.field_178675_d.isEmpty();
    }
}
