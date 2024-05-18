package net.minecraft.src;

import net.minecraft.client.*;

class NetClientWebTextures extends GuiScreen
{
    final String texturePackName;
    final NetClientHandler netClientHandlerWebTextures;
    
    NetClientWebTextures(final NetClientHandler par1NetClientHandler, final String par2Str) {
        this.netClientHandlerWebTextures = par1NetClientHandler;
        this.texturePackName = par2Str;
    }
    
    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        this.mc = Minecraft.getMinecraft();
        if (this.mc.getServerData() != null) {
            this.mc.getServerData().setAcceptsTextures(par1);
            ServerList.func_78852_b(this.mc.getServerData());
        }
        if (par1) {
            this.mc.texturePackList.requestDownloadOfTexture(this.texturePackName);
        }
        this.mc.displayGuiScreen(null);
    }
}
