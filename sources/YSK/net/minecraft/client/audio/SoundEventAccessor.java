package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor<SoundPoolEntry>
{
    private final int weight;
    private final SoundPoolEntry entry;
    
    @Override
    public SoundPoolEntry cloneEntry() {
        return new SoundPoolEntry(this.entry);
    }
    
    @Override
    public Object cloneEntry() {
        return this.cloneEntry();
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getWeight() {
        return this.weight;
    }
    
    SoundEventAccessor(final SoundPoolEntry entry, final int weight) {
        this.entry = entry;
        this.weight = weight;
    }
}
