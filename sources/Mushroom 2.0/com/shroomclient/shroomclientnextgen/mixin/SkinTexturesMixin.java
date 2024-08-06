package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.CustomCape;
import com.shroomclient.shroomclientnextgen.util.C;
import java.util.Objects;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SkinTextures.class)
public class SkinTexturesMixin {

    @Shadow
    private Identifier capeTexture;

    @Shadow
    @Nullable
    private String textureUrl;

    @Shadow
    @Nullable
    public String textureUrl() {
        return this.textureUrl;
    }

    /**
     * @author scoliosis
     * @reason custom capes!!
     */
    @Nullable
    @Overwrite
    public Identifier capeTexture() {
        String[] capes = {
            "girl",
            "kiss",
            "mushroomcape",
            "minecon2011",
            "minecon2012",
            "minecon2013",
            "minecon2015",
            "minecon2016",
            "mushroomclientcape",
            "mushroomswig",
            "trapscape",
            "astolfocape",
            "grimantihakecape",
            "mooncape",
            "ravenanime",
            "ravencape",
            "gatocape",
            "risecape",
            "nsfw",
        };

        if (
            ModuleManager.isEnabled(CustomCape.class) &&
            (CustomCape.everyoneCapes ||
                Objects.equals(
                    this.textureUrl(),
                    C.p().getSkinTextures().textureUrl()
                ))
        ) {
            return new Identifier(
                "shroomclientnextgen",
                capes[CustomCape.mode.ordinal()] + ".png"
            );
        }
        return this.capeTexture;
    }
}
