package net.minecraft.realms;

import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class RealmsAnvilLevelStorageSource
{
    private ISaveFormat levelStorageSource;
    
    public boolean levelExists(final String s) {
        return this.levelStorageSource.canLoadWorld(s);
    }
    
    public boolean isNewLevelIdAcceptable(final String s) {
        return this.levelStorageSource.func_154335_d(s);
    }
    
    public String getName() {
        return this.levelStorageSource.getName();
    }
    
    public void renameLevel(final String s, final String s2) {
        this.levelStorageSource.renameWorld(s, s2);
    }
    
    public void clearAll() {
        this.levelStorageSource.flushCache();
    }
    
    public List<RealmsLevelSummary> getLevelList() throws AnvilConverterException {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<SaveFormatComparator> iterator = this.levelStorageSource.getSaveList().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(new RealmsLevelSummary(iterator.next()));
        }
        return (List<RealmsLevelSummary>)arrayList;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean isConvertible(final String s) {
        return this.levelStorageSource.func_154334_a(s);
    }
    
    public boolean convertLevel(final String s, final IProgressUpdate progressUpdate) {
        return this.levelStorageSource.convertMapFormat(s, progressUpdate);
    }
    
    public boolean requiresConversion(final String s) {
        return this.levelStorageSource.isOldMapFormat(s);
    }
    
    public boolean deleteLevel(final String s) {
        return this.levelStorageSource.deleteWorldDirectory(s);
    }
    
    public RealmsAnvilLevelStorageSource(final ISaveFormat levelStorageSource) {
        this.levelStorageSource = levelStorageSource;
    }
}
