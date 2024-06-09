package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface SoftOverride {
}
