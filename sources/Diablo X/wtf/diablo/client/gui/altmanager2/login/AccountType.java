package wtf.diablo.client.gui.altmanager2.login;

import net.minecraft.util.ResourceLocation;

public enum AccountType {
    // MOJANG(new ResourceLocation("diablo/altmanager/mojang.png")),
    CRACKED(new ResourceLocation("diablo/altmanager/cracked.png")),
    MICROSOFT(new ResourceLocation("diablo/altmanager/microsoft.png"));

    private final ResourceLocation iconLocation;

    AccountType(ResourceLocation iconLocation) {
        this.iconLocation = iconLocation;
    }

    public ResourceLocation getIconLocation() {
        return iconLocation;
    }

}
