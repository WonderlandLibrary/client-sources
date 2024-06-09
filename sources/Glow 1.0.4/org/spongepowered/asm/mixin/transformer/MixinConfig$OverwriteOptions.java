package org.spongepowered.asm.mixin.transformer;

import com.google.gson.annotations.*;

static class OverwriteOptions
{
    @SerializedName("conformVisibility")
    boolean conformAccessModifiers;
    @SerializedName("requireAnnotations")
    boolean requireOverwriteAnnotations;
    
    OverwriteOptions() {
        super();
    }
}
