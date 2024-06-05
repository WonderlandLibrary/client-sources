package net.shoreline.client.mixin;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author xgraza
 * @since 03/31/24
 */
@Mixin(Main.class)
public final class MixinMain
{
    /**
     * This is what happens when you want to open something with Browser#browse, but Minecraft blueballs you
     */
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Ljava/lang/System;setProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String hookStaticInit(String key, String value)
    {
        return System.setProperty("java.awt.headless", "false");
    }
}
