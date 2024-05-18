package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public class SoundList
{
    private final List<SoundEntry> soundList;
    private SoundCategory category;
    private boolean replaceExisting;
    
    public SoundCategory getSoundCategory() {
        return this.category;
    }
    
    public boolean canReplaceExisting() {
        return this.replaceExisting;
    }
    
    public SoundList() {
        this.soundList = (List<SoundEntry>)Lists.newArrayList();
    }
    
    public void setSoundCategory(final SoundCategory category) {
        this.category = category;
    }
    
    public void setReplaceExisting(final boolean replaceExisting) {
        this.replaceExisting = replaceExisting;
    }
    
    public List<SoundEntry> getSoundList() {
        return this.soundList;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class SoundEntry
    {
        private float pitch;
        private boolean streaming;
        private float volume;
        private Type type;
        private String name;
        private int weight;
        
        public void setSoundEntryName(final String name) {
            this.name = name;
        }
        
        public void setSoundEntryVolume(final float volume) {
            this.volume = volume;
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
                if (false == true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean isStreaming() {
            return this.streaming;
        }
        
        public int getSoundEntryWeight() {
            return this.weight;
        }
        
        public void setSoundEntryPitch(final float pitch) {
            this.pitch = pitch;
        }
        
        public void setStreaming(final boolean streaming) {
            this.streaming = streaming;
        }
        
        public float getSoundEntryPitch() {
            return this.pitch;
        }
        
        public Type getSoundEntryType() {
            return this.type;
        }
        
        public void setSoundEntryType(final Type type) {
            this.type = type;
        }
        
        public String getSoundEntryName() {
            return this.name;
        }
        
        public float getSoundEntryVolume() {
            return this.volume;
        }
        
        public SoundEntry() {
            this.volume = 1.0f;
            this.pitch = 1.0f;
            this.weight = " ".length();
            this.type = Type.FILE;
            this.streaming = ("".length() != 0);
        }
        
        public void setSoundEntryWeight(final int weight) {
            this.weight = weight;
        }
        
        public enum Type
        {
            private final String field_148583_c;
            
            SOUND_EVENT(Type.I["  ".length()], " ".length(), Type.I["   ".length()]), 
            FILE(Type.I["".length()], "".length(), Type.I[" ".length()]);
            
            private static final Type[] ENUM$VALUES;
            private static final String[] I;
            
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
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0x58 ^ 0x5C])["".length()] = I("!\u000f=?", "gFqzM");
                Type.I[" ".length()] = I("0&9/", "VOUJU");
                Type.I["  ".length()] = I("\u0016)?\u0019%\u001a#<\u0012/\u0011", "EfjWa");
                Type.I["   ".length()] = I("(\u000e(-\f", "MxMCx");
            }
            
            public static Type getType(final String s) {
                final Type[] values;
                final int length = (values = values()).length;
                int i = "".length();
                "".length();
                if (1 < 1) {
                    throw null;
                }
                while (i < length) {
                    final Type type = values[i];
                    if (type.field_148583_c.equals(s)) {
                        return type;
                    }
                    ++i;
                }
                return null;
            }
            
            static {
                I();
                final Type[] enum$VALUES = new Type["  ".length()];
                enum$VALUES["".length()] = Type.FILE;
                enum$VALUES[" ".length()] = Type.SOUND_EVENT;
                ENUM$VALUES = enum$VALUES;
            }
            
            private Type(final String s, final int n, final String field_148583_c) {
                this.field_148583_c = field_148583_c;
            }
        }
    }
}
