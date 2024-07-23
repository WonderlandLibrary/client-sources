package io.github.liticane.monoxide.module.impl.render;

import net.minecraft.util.ResourceLocation;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "ClientCape", description = "Equips Atani's custom cape", category = ModuleCategory.RENDER)
public class ClientCapeModule extends Module {

    public final ModeValue cape = new ModeValue("Cape", this, new String[]{"Technoblade", "Normal", "Small Text", "Small Text Up", "Atan+I Logo", "Minecraft Res", "Laugh At This User", "Verus Airlines", "Menace \uD83D\uDE14", "Stolas White", "Stolas Black", "Blåhaj"});

    ResourceLocation normal = new ResourceLocation("atani/capes/AtaniCape.png");
    ResourceLocation small = new ResourceLocation("atani/capes/AtaniCapeSmall.png");
    ResourceLocation smallUp = new ResourceLocation("atani/capes/AtaniCapeSmallUp.png");
    ResourceLocation ataniFunctionLogo = new ResourceLocation("atani/capes/AtaniFunctionLogoCape.png");
    ResourceLocation pixelated = new ResourceLocation("atani/capes/AtaniCapePixelated.png");
    ResourceLocation technoblade = new ResourceLocation("atani/capes/Technoblade.png");
    ResourceLocation laughAtThisUser = new ResourceLocation("atani/capes/Laugh.png");
    ResourceLocation verusAirlines = new ResourceLocation("atani/capes/Verus.png");
    ResourceLocation menace = new ResourceLocation("atani/capes/Menace.png");
    ResourceLocation stolasWhite = new ResourceLocation("atani/capes/StolasWhite.png");
    ResourceLocation stolasBlack = new ResourceLocation("atani/capes/StolasBlack.png");
    ResourceLocation blahaj = new ResourceLocation("atani/capes/Blahaj.png");

    @Listen
    public void onUpdate(UpdateEvent event) {
        ResourceLocation resourceLocation = null;
        switch (cape.getValue()) {
            case "Normal":
                resourceLocation = normal;
                break;
            case "Small Text":
                resourceLocation = small;
                break;
            case "Small Text Up":
                resourceLocation = smallUp;
                break;
            case "Atan+I Logo":
                resourceLocation = ataniFunctionLogo;
                break;
            case "Minecraft Res":
                resourceLocation = pixelated;
                break;
            case "Technoblade":
                resourceLocation = technoblade;
                break;
            case "Laugh At This User":
                resourceLocation = laughAtThisUser;
                break;
            case "Verus Airlines":
                resourceLocation = verusAirlines;
                break;
            case "Menace \uD83D\uDE14":
                resourceLocation = menace;
                break;
            case "Stolas White":
                resourceLocation = stolasWhite;
                break;
            case "Stolas Black":
                resourceLocation = stolasBlack;
                break;
            case "Blåhaj":
                resourceLocation = blahaj;
                break;
        }
        getPlayer().setLocationOfCape(resourceLocation);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        if(Methods.mc.thePlayer == null) {
            return;
        }
        Methods.mc.thePlayer.setLocationOfCape(null);
    }

}