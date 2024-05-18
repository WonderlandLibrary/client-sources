package net.minecraft.src;

import java.io.*;

class AnvilSaveConverterFileFilter implements FilenameFilter
{
    final AnvilSaveConverter parent;
    
    AnvilSaveConverterFileFilter(final AnvilSaveConverter par1AnvilSaveConverter) {
        this.parent = par1AnvilSaveConverter;
    }
    
    @Override
    public boolean accept(final File par1File, final String par2Str) {
        return par2Str.endsWith(".mcr");
    }
}
