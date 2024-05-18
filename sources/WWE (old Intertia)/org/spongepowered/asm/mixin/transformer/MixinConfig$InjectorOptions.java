package org.spongepowered.asm.mixin.transformer;

import com.google.gson.annotations.*;
import java.util.*;

static class InjectorOptions
{
    @SerializedName("defaultRequire")
    int defaultRequireValue;
    @SerializedName("defaultGroup")
    String defaultGroup;
    @SerializedName("injectionPoints")
    List<String> injectionPoints;
    @SerializedName("maxShiftBy")
    int maxShiftBy;
    
    InjectorOptions() {
        super();
        this.defaultRequireValue = 0;
        this.defaultGroup = "default";
        this.maxShiftBy = 0;
    }
}
