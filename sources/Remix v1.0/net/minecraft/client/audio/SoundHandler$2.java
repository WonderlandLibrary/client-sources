package net.minecraft.client.audio;

import net.minecraft.util.*;

class SoundHandler$2 implements ISoundEventAccessor {
    final ResourceLocation field_148726_a = new ResourceLocation(this.val$var9, this.val$var6.getSoundEntryName());
    final /* synthetic */ String val$var9;
    final /* synthetic */ SoundList.SoundEntry val$var6;
    
    @Override
    public int getWeight() {
        final SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.access$000(SoundHandler.this).getObject(this.field_148726_a);
        return (var1 == null) ? 0 : var1.getWeight();
    }
    
    public SoundPoolEntry getEntry() {
        final SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.access$000(SoundHandler.this).getObject(this.field_148726_a);
        return (SoundPoolEntry)((var1 == null) ? SoundHandler.missing_sound : var1.cloneEntry());
    }
    
    @Override
    public Object cloneEntry() {
        return this.getEntry();
    }
}