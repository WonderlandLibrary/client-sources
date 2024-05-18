/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;

public class SoundRegistry
extends RegistrySimple {
    private Map field_148764_a;
    private static final String __OBFID = "CL_00001151";

    @Override
    protected Map createUnderlyingMap() {
        this.field_148764_a = Maps.newHashMap();
        return this.field_148764_a;
    }

    public void registerSound(SoundEventAccessorComposite p_148762_1_) {
        this.putObject(p_148762_1_.getSoundEventLocation(), p_148762_1_);
    }

    public void clearMap() {
        this.field_148764_a.clear();
    }
}

