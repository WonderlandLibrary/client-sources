package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Surrogate {
}
