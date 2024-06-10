package me.kaimson.melonclient.mixins.accessor;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ bnn.class })
public interface SimpleReloadableResourceManagerAccessor
{
    @Accessor
    Map<String, bnb> getDomainResourceManagers();
}
