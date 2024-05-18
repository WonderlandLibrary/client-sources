package net.minecraft.src;

import java.util.*;

public interface ISaveFormat
{
    ISaveHandler getSaveLoader(final String p0, final boolean p1);
    
    List getSaveList() throws AnvilConverterException;
    
    void flushCache();
    
    WorldInfo getWorldInfo(final String p0);
    
    boolean deleteWorldDirectory(final String p0);
    
    void renameWorld(final String p0, final String p1);
    
    boolean isOldMapFormat(final String p0);
    
    boolean convertMapFormat(final String p0, final IProgressUpdate p1);
    
    boolean canLoadWorld(final String p0);
}
