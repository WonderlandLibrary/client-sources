package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.CustomName;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TextVisitFactory.class)
public abstract class TextVisitFactoryMixin {

    @ModifyArg(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
            ordinal = 0
        ),
        method = "visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
        index = 0
    )
    private static String adjustText(String text) {
        if (ModuleManager.isEnabled(CustomName.class)) {
            if (C.p() != null) {
                String playerName = C.p().getName().getLiteralString();
                text = text.replaceAll(playerName, "Mushroom");
            }
        }
        return text;
    }
}
