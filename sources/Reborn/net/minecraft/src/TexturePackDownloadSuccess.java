package net.minecraft.src;

import java.io.*;

class TexturePackDownloadSuccess implements IDownloadSuccess
{
    final TexturePackList texturePacks;
    
    TexturePackDownloadSuccess(final TexturePackList par1TexturePackList) {
        this.texturePacks = par1TexturePackList;
    }
    
    @Override
    public void onSuccess(final File par1File) {
        if (TexturePackList.isDownloading(this.texturePacks)) {
            TexturePackList.setSelectedTexturePack(this.texturePacks, new TexturePackCustom(TexturePackList.generateTexturePackID(this.texturePacks, par1File), par1File, TexturePackList.func_98143_h()));
            TexturePackList.getMinecraft(this.texturePacks).scheduleTexturePackRefresh();
        }
    }
}
