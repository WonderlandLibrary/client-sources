package net.minecraft.src;

import java.io.*;

public class AnvilSaveHandler extends SaveHandler
{
    public AnvilSaveHandler(final File par1File, final String par2Str, final boolean par3) {
        super(par1File, par2Str, par3);
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider par1WorldProvider) {
        final File var2 = this.getWorldDirectory();
        if (par1WorldProvider instanceof WorldProviderHell) {
            final File var3 = new File(var2, "DIM-1");
            var3.mkdirs();
            return new AnvilChunkLoader(var3);
        }
        if (par1WorldProvider instanceof WorldProviderEnd) {
            final File var3 = new File(var2, "DIM1");
            var3.mkdirs();
            return new AnvilChunkLoader(var3);
        }
        return new AnvilChunkLoader(var2);
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo par1WorldInfo, final NBTTagCompound par2NBTTagCompound) {
        par1WorldInfo.setSaveVersion(19133);
        super.saveWorldInfoWithPlayer(par1WorldInfo, par2NBTTagCompound);
    }
    
    @Override
    public void flush() {
        try {
            ThreadedFileIOBase.threadedIOInstance.waitForFinish();
        }
        catch (InterruptedException var2) {
            var2.printStackTrace();
        }
        RegionFileCache.clearRegionFileReferences();
    }
}
