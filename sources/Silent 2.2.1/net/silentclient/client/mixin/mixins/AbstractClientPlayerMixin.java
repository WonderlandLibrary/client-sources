package net.silentclient.client.mixin.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.silentclient.client.Client;
import net.silentclient.client.admin.AdminRender;
import net.silentclient.client.cosmetics.*;
import net.silentclient.client.cosmetics.dynamiccurved.DynamicCape;
import net.silentclient.client.event.impl.EventFovUpdate;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.mods.settings.CosmeticsMod;
import net.silentclient.client.utils.CustomSkin;
import net.silentclient.client.utils.Players;
import net.silentclient.client.utils.SCTextureManager;
import net.silentclient.client.utils.types.PlayerResponse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin implements AbstractClientPlayerExt {
    @Shadow private NetworkPlayerInfo playerInfo;
    @Unique private AnimatedResourceLocation silent$cape;
    @Unique private AnimatedResourceLocation silent$bandana;
    @Unique private HatData silent$hat;
    @Unique private HatData silent$mask;
    @Unique private HatData silent$neck;
    @Unique private AnimatedResourceLocation silent$wings;
    @Unique private StaticResourceLocation silent$playerIcon;
    @Unique private StaticResourceLocation silent$shoulders;
    @Unique private String silent$nameClear;
    @Unique private DynamicCape silent$dynamicCape;
    @Unique private StaticCape silent$staticCape;
    @Unique private StaticCape silent$curvedCape;
    @Unique private ShieldData silent$shield;
    @Unique private PlayerResponse.Account silent$account;
    @Unique private String silent$capeType = "dynamic_curved";
    @Unique private boolean silent$shouldersBool = true;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initCustom(World worldIn, GameProfile playerProfile, CallbackInfo ci) {
        this.silent$nameClear = playerProfile.getName();

        if (this.silent$nameClear != null && !this.silent$nameClear.isEmpty())
        {
            this.silent$nameClear = StringUtils.stripControlCodes(this.silent$nameClear);
        }

        this.silent$dynamicCape = new DynamicCape();
        this.silent$staticCape = new StaticCape(1.0F, 0.0F, 0.0F);
        this.silent$curvedCape = new StaticCape(6.0F, 5.0F, 0.0F);

        Players.getPlayerStatus(false, silent$nameClear, playerProfile.getId(), ((AbstractClientPlayer) (Object) this));
    }

    @Inject(method = "getFovModifier", at = @At("RETURN"), cancellable = true)
    public void getFovModifier(CallbackInfoReturnable<Float> cir) {
        EventFovUpdate event = new EventFovUpdate((AbstractClientPlayer) (Object)this, cir.getReturnValue());
        event.call();

        cir.setReturnValue(event.getFov());
    }

    @Inject(method = "getSkinType", at = @At("HEAD"), cancellable = true)
    public void mixinSkinType(CallbackInfoReturnable<String> cir) {
        if(Minecraft.getMinecraft().currentScreen instanceof AdminRender) {
            cir.setReturnValue("default");
            cir.cancel();
        }
        if(Client.getInstance().getModInstances().getModByClass(AnimationsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, "1.7 Skins").getValBoolean()) {
            cir.setReturnValue("default");
            cir.cancel();
            return;
        }

        if(Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Custom Skins").getValBoolean() && this.silent$getAccount() != null && this.silent$getAccount().getCustomSkin()) {
            cir.setReturnValue(this.silent$getAccount().getSkinType());
            cir.cancel();
        }
    }

    @Inject(method = "hasSkin", at = @At("HEAD"), cancellable = true)
    public void mixinHasSkin(CallbackInfoReturnable<Boolean> cir) {
        if(Minecraft.getMinecraft().currentScreen instanceof AdminRender) {
            cir.setReturnValue(true);
            cir.cancel();
        }

        if(Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Custom Skins").getValBoolean() && this.silent$getAccount() != null && this.silent$getAccount().getCustomSkin()) {
            if(!CustomSkin.skins.containsKey(silent$nameClear.toLowerCase())) {
                Client.logger.info(String.format("Custom Skin not found in cache! Putting Custom Skin to cache. (%s)", silent$nameClear));
                CustomSkin.skins.put(silent$nameClear.toLowerCase(), new CustomSkin());
            }
            CustomSkin customSkin = CustomSkin.skins.get(silent$nameClear.toLowerCase());
            if(customSkin.getLocation() != null) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Inject(method = "getLocationSkin()Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void mixinSkinLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        if(Minecraft.getMinecraft().currentScreen instanceof AdminRender) {
            cir.setReturnValue(new ResourceLocation("textures/entity/steve.png"));
            cir.cancel();
            return;
        }

        if(Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Custom Skins").getValBoolean() && this.silent$getAccount() != null && this.silent$getAccount().getCustomSkin()) {
            if(!CustomSkin.skins.containsKey(silent$nameClear.toLowerCase())) {
                Client.logger.info(String.format("Custom Skin not found in cache! Putting Custom Skin to cache. (%s)", silent$nameClear));
                CustomSkin.skins.put(silent$nameClear.toLowerCase(), new CustomSkin());
            }
            CustomSkin customSkin = CustomSkin.skins.get(silent$nameClear.toLowerCase());
            if(!customSkin.isLoaded() && !CustomSkin.loading) {
                Client.logger.info(String.format("Starting Download Skin (%s)", silent$nameClear));
                CustomSkin.loading = true;
                (new Thread("CustomSkinThread") {
                    public void run() {
                        Client.logger.info(String.format("Downloading Custom Skin (%s)", silent$nameClear));
                        customSkin.setImage(SCTextureManager.getImage("https://cdn-test.silentclient.net/file/silentclient/" + silent$account.getCustomSkinPath()));
                        CustomSkin.loading = false;
                        customSkin.setLoaded(true);
                        Client.logger.info(String.format("Custom Skin downloaded! (%s)", silent$nameClear));
                    }
                }).start();
            }
            if(customSkin.getImage() != null && !customSkin.isInitialized()) {
                Client.logger.info(String.format("Initializing Custom Skin (%s)", silent$nameClear));
                customSkin.setLocation(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("custom_skin_" + new Random().nextLong(), new DynamicTexture(customSkin.getImage())));
                customSkin.setInitialized(true);
                Client.logger.info(String.format("Custom Skin Initialized (%s)", silent$nameClear));
            }
            if(customSkin.getLocation() != null) {
                cir.setReturnValue(customSkin.getLocation());
                cir.cancel();
            }
        }
    }

    @Override
    public String silent$getCapeType() {
        return silent$capeType;
    }

    @Override
    public void silent$setCapeType(String a) {
        this.silent$capeType = a;
    }

    @Override
    public boolean silent$getShoulders() {
        return silent$shouldersBool;
    }

    @Override
    public void silent$setShoulders(boolean a) {
        this.silent$shouldersBool = a;
    }

    @Override
    public PlayerResponse.Account silent$getAccount() {
        return silent$account;
    }

    @Override
    public void silent$setAccount(PlayerResponse.Account a) {
        this.silent$account = a;
    }

    @Override
    public AnimatedResourceLocation silent$getBandana() {
        return silent$bandana;
    }

    @Override
    public void silent$setBandana(AnimatedResourceLocation a) {
        this.silent$bandana = a;
    }

    @Override
    public AnimatedResourceLocation silent$getCape() {
        if(!Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Capes").getValBoolean()) {
            return null;
        }
        return silent$cape;
    }

    @Override
    public void silent$setCape(AnimatedResourceLocation a) {
        this.silent$cape = a;
    }

    @Override
    public DynamicCape silent$getDynamicCape() {
        return silent$dynamicCape;
    }

    @Override
    public StaticCape silent$getCurvedCape() {
        return silent$curvedCape;
    }

    @Override
    public StaticCape silent$getStaticCape() {
        return silent$staticCape;
    }

    @Override
    public HatData silent$getHat() {
        return silent$hat;
    }

    @Override
    public void silent$setHat(HatData a) {
        this.silent$hat = a;
    }

    @Override
    public HatData silent$getMask() {
        return silent$mask;
    }

    @Override
    public void silent$setMask(HatData a) {
        this.silent$mask = a;
    }

    @Override
    public HatData silent$getNeck() {
        return silent$neck;
    }

    @Override
    public void silent$setNeck(HatData a) {
        this.silent$neck = a;
    }

    @Override
    public ShieldData silent$getShield() {
        return silent$shield;
    }

    @Override
    public void silent$setShield(ShieldData a) {
        this.silent$shield = a;
    }

    @Override
    public AnimatedResourceLocation silent$getWings() {
        return silent$wings;
    }

    @Override
    public void silent$setWings(AnimatedResourceLocation a) {
        this.silent$wings = a;
    }

    @Override
    public String silent$getNameClear() {
        return silent$nameClear;
    }

    @Override
    public void silent$setNameClear(String a) {
        this.silent$nameClear = a;
    }

    @Override
    public StaticResourceLocation silent$getCapeShoulders() {
        return silent$shoulders;
    }

    @Override
    public void silent$setCapeShoulders(StaticResourceLocation a) {
        this.silent$shoulders = a;
    }

    @Override
    public StaticResourceLocation silent$getPlayerIcon() {
        return silent$playerIcon;
    }

    @Override
    public void silent$setPlayerIcon(StaticResourceLocation a) {
        this.silent$playerIcon = a;
    }

    @Override
    public Object silent$getPlayerInfo() {
        return this.playerInfo;
    }
}
