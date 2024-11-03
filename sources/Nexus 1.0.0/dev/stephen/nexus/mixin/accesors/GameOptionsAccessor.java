package dev.stephen.nexus.mixin.accesors;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface GameOptionsAccessor {
    @Accessor
    SimpleOption<Boolean> getSprintToggled();

    @Mutable
    @Accessor
    void setSprintToggled(SimpleOption<Boolean> sprintToggled);
}
