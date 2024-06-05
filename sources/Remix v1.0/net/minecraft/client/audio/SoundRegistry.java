package net.minecraft.client.audio;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;

public class SoundRegistry extends RegistrySimple
{
    private Map field_148764_a;
    
    @Override
    protected Map createUnderlyingMap() {
        return this.field_148764_a = Maps.newHashMap();
    }
    
    public void registerSound(final SoundEventAccessorComposite p_148762_1_) {
        this.putObject(p_148762_1_.getSoundEventLocation(), p_148762_1_);
    }
    
    public void clearMap() {
        this.field_148764_a.clear();
    }
}
