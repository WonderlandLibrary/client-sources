package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

class GuiTexturePackSlot extends GuiSlot
{
    final GuiTexturePacks parentTexturePackGui;
    
    public GuiTexturePackSlot(final GuiTexturePacks par1GuiTexturePacks) {
        super(GuiTexturePacks.func_73950_a(par1GuiTexturePacks), par1GuiTexturePacks.width, par1GuiTexturePacks.height, 32, par1GuiTexturePacks.height - 55 + 4, 36);
        this.parentTexturePackGui = par1GuiTexturePacks;
    }
    
    @Override
    protected int getSize() {
        return GuiTexturePacks.func_73955_b(this.parentTexturePackGui).texturePackList.availableTexturePacks().size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
        final List var3 = GuiTexturePacks.func_73958_c(this.parentTexturePackGui).texturePackList.availableTexturePacks();
        try {
            GuiTexturePacks.func_73951_d(this.parentTexturePackGui).texturePackList.setTexturePack(var3.get(par1));
            GuiTexturePacks.func_73952_e(this.parentTexturePackGui).renderEngine.refreshTextures();
            GuiTexturePacks.func_73962_f(this.parentTexturePackGui).renderGlobal.loadRenderers();
        }
        catch (Exception var4) {
            GuiTexturePacks.func_73959_g(this.parentTexturePackGui).texturePackList.setTexturePack(var3.get(0));
            GuiTexturePacks.func_73957_h(this.parentTexturePackGui).renderEngine.refreshTextures();
            GuiTexturePacks.func_73956_i(this.parentTexturePackGui).renderGlobal.loadRenderers();
        }
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        final List var2 = GuiTexturePacks.func_73953_j(this.parentTexturePackGui).texturePackList.availableTexturePacks();
        return GuiTexturePacks.func_73961_k(this.parentTexturePackGui).texturePackList.getSelectedTexturePack() == var2.get(par1);
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 36;
    }
    
    @Override
    protected void drawBackground() {
        this.parentTexturePackGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final ITexturePack var6 = GuiTexturePacks.func_96143_l(this.parentTexturePackGui).texturePackList.availableTexturePacks().get(par1);
        var6.bindThumbnailTexture(GuiTexturePacks.func_96142_m(this.parentTexturePackGui).renderEngine);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        par5Tessellator.startDrawingQuads();
        par5Tessellator.setColorOpaque_I(16777215);
        par5Tessellator.addVertexWithUV(par2, par3 + par4, 0.0, 0.0, 1.0);
        par5Tessellator.addVertexWithUV(par2 + 32, par3 + par4, 0.0, 1.0, 1.0);
        par5Tessellator.addVertexWithUV(par2 + 32, par3, 0.0, 1.0, 0.0);
        par5Tessellator.addVertexWithUV(par2, par3, 0.0, 0.0, 0.0);
        par5Tessellator.draw();
        String var7 = var6.getTexturePackFileName();
        if (!var6.isCompatible()) {
            var7 = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("texturePack.incompatible") + " - " + var7;
        }
        if (var7.length() > 32) {
            var7 = String.valueOf(var7.substring(0, 32).trim()) + "...";
        }
        this.parentTexturePackGui.drawString(GuiTexturePacks.func_73954_n(this.parentTexturePackGui), var7, par2 + 32 + 2, par3 + 1, 16777215);
        this.parentTexturePackGui.drawString(GuiTexturePacks.func_96145_o(this.parentTexturePackGui), var6.getFirstDescriptionLine(), par2 + 32 + 2, par3 + 12, 8421504);
        this.parentTexturePackGui.drawString(GuiTexturePacks.func_96144_p(this.parentTexturePackGui), var6.getSecondDescriptionLine(), par2 + 32 + 2, par3 + 12 + 10, 8421504);
    }
}
