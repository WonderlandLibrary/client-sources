package net.minecraft.client.audio;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class SoundRegistry extends RegistrySimple<ResourceLocation, SoundEventAccessorComposite>
{
    private Map<ResourceLocation, SoundEventAccessorComposite> soundRegistry;
    
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
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void clearMap() {
        this.soundRegistry.clear();
    }
    
    public void registerSound(final SoundEventAccessorComposite soundEventAccessorComposite) {
        this.putObject(soundEventAccessorComposite.getSoundEventLocation(), soundEventAccessorComposite);
    }
    
    @Override
    protected Map<ResourceLocation, SoundEventAccessorComposite> createUnderlyingMap() {
        return this.soundRegistry = (Map<ResourceLocation, SoundEventAccessorComposite>)Maps.newHashMap();
    }
}
