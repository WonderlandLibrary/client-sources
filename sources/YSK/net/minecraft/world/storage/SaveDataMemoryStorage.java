package net.minecraft.world.storage;

import net.minecraft.world.*;

public class SaveDataMemoryStorage extends MapStorage
{
    public SaveDataMemoryStorage() {
        super(null);
    }
    
    @Override
    public void setData(final String s, final WorldSavedData worldSavedData) {
        this.loadedDataMap.put(s, worldSavedData);
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public WorldSavedData loadData(final Class<? extends WorldSavedData> clazz, final String s) {
        return this.loadedDataMap.get(s);
    }
    
    @Override
    public int getUniqueDataId(final String s) {
        return "".length();
    }
    
    @Override
    public void saveAllData() {
    }
}
