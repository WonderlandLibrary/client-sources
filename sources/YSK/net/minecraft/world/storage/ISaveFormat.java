package net.minecraft.world.storage;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public interface ISaveFormat
{
    boolean deleteWorldDirectory(final String p0);
    
    WorldInfo getWorldInfo(final String p0);
    
    boolean func_154335_d(final String p0);
    
    boolean canLoadWorld(final String p0);
    
    String getName();
    
    void flushCache();
    
    boolean isOldMapFormat(final String p0);
    
    boolean func_154334_a(final String p0);
    
    void renameWorld(final String p0, final String p1);
    
    List<SaveFormatComparator> getSaveList() throws AnvilConverterException;
    
    boolean convertMapFormat(final String p0, final IProgressUpdate p1);
    
    ISaveHandler getSaveLoader(final String p0, final boolean p1);
}
