package mathax.client.mixin;

import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.misc.NameProtect;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TextVisitFactory.class)
public abstract class TextVisitFactoryMixin {
    @ModifyArg(at = @At(value = "INVOKE",
        target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
        ordinal = 0),
        method = {
            "visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
        index = 0)
    private static String adjustText(String text) {
        if (Modules.get() != null) return Modules.get().get(NameProtect.class).replaceName(text);
        else return text;
    }
}
