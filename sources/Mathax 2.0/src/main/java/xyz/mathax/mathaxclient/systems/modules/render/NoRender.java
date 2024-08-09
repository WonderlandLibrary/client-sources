package xyz.mathax.mathaxclient.systems.modules.render;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.ChunkOcclusionEvent;
import xyz.mathax.mathaxclient.events.world.ParticleEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import xyz.mathax.mathaxclient.settings.*;

import java.util.List;

public class NoRender extends Module {
    private final SettingGroup overlaySettings = settings.createGroup("Overlay");
    private final SettingGroup hudSettings = settings.createGroup("HUD");
    private final SettingGroup worldSettings = settings.createGroup("World");
    private final SettingGroup entitySettings = settings.createGroup("Entity");

    // Overlay

    private final Setting<Boolean> noHurtCamSetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Hurt cam")
            .description("Disable rendering of the hurt camera effect.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noPortalOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Portal overlay")
            .description("Disable rendering of the nether portal overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noSpyglassOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Spyglass overlay")
            .description("Disable rendering of the spyglass overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noNauseaSetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Nausea")
            .description("Disable rendering of the nausea overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noPumpkinOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Pumpkin overlay")
            .description("Disable rendering of the pumpkin head overlay")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noPowderedSnowOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Powdered snow overlay")
            .description("Disable rendering of the powdered snow overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noFireOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Fire overlay")
            .description("Disable rendering of the fire overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noLiquidOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Liquid overlay")
            .description("Disables rendering of the liquid overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noInWallOverlaySetting = overlaySettings.add(new BoolSetting.Builder()
            .name("In wall overlay")
            .description("Disable rendering of the overlay when inside blocks.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noVignetteSetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Vignette")
            .description("Disable rendering of the vignette overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noGuiBackgroundSetting = overlaySettings.add(new BoolSetting.Builder()
            .name("GUI background")
            .description("Disable rendering of the GUI background overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noTotemAnimationSetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Totem animation")
            .description("Disable rendering of the totem animation when you pop a totem.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noEatParticlesSetting = overlaySettings.add(new BoolSetting.Builder()
            .name("Eating particles")
            .description("Disable rendering of eating particles.")
            .defaultValue(false)
            .build()
    );

    // HUD

    private final Setting<Boolean> noBossBarSetting = hudSettings.add(new BoolSetting.Builder()
            .name("Boss bar")
            .description("Disable rendering of boss bars.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noScoreboardSetting = hudSettings.add(new BoolSetting.Builder()
            .name("Scoreboard")
            .description("Disable rendering of the scoreboard.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noCrosshairSetting = hudSettings.add(new BoolSetting.Builder()
            .name("Crosshair")
            .description("Disable rendering of the crosshair.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noHeldItemNameSetting = hudSettings.add(new BoolSetting.Builder()
            .name("Held item name")
            .description("Disable rendering of the held item name.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noPotionIconsSetting = hudSettings.add(new BoolSetting.Builder()
            .name("Potion icons")
            .description("Disable rendering of status effect icons.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noMessageSignatureIndicatorSetting = hudSettings.add(new BoolSetting.Builder()
            .name("Message signature indicator")
            .description("Disable chat message signature indicator on the left of the message.")
            .defaultValue(false)
            .build()
    );

    // World

    private final Setting<Boolean> noWeatherSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Weather")
            .description("Disable rendering of weather.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noFogSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Fog")
            .description("Disable rendering of fog.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noEnchantmentTableBookSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Enchantment table book")
            .description("Disable rendering of books above enchanting tables.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noSignTextSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Sign text")
            .description("Disable rendering of text on signs.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noBlockBreakParticlesSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Block break particles")
            .description("Disable rendering of block-break particles.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noBlockBreakOverlaySetting = worldSettings.add(new BoolSetting.Builder()
            .name("Block break overlay")
            .description("Disable rendering of block-break overlay.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noSkylightUpdatesSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Skylight updates")
            .description("Disable rendering of skylight updates.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noFallingBlocksSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Falling blocks")
            .description("Disable rendering of falling blocks.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noCaveCullingSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Cave culling")
            .description("Disable Minecraft's cave culling algorithm.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noMapMarkersSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Map markers")
            .description("Disable markers on maps.")
            .defaultValue(false)
            .build()
    );

    private final Setting<BannerRenderMode> bannerRenderSetting = worldSettings.add(new EnumSetting.Builder<BannerRenderMode>()
            .name("Banners")
            .description("Change rendering of banners.")
            .defaultValue(BannerRenderMode.Everything)
            .build()
    );

    private final Setting<Boolean> noFireworkExplosionsSetting = worldSettings.add(new BoolSetting.Builder()
            .name("Firework explosions")
            .description("Disable rendering of firework explosions.")
            .defaultValue(false)
            .build()
    );

    private final Setting<List<ParticleType<?>>> particlesSetting = worldSettings.add(new ParticleTypeListSetting.Builder()
            .name("Particles")
            .description("Particles to not render.")
            .build()
    );

    private final Setting<Boolean> noBarrierInvisibilitySetting = worldSettings.add(new BoolSetting.Builder()
            .name("Barrier invisibility")
            .description("Disable barriers being invisible when not holding one.")
            .defaultValue(false)
            .build()
    );

    // Entity

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = entitySettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Disable rendering of selected entities.")
            .build()
    );

    private final Setting<Boolean> dropSpawnPacketSetting = entitySettings.add(new BoolSetting.Builder()
            .name("Drop spawn packets")
            .description("WARNING! Drop all spawn packets of entities selected in the above list.")
            .defaultValue(false)
            .build()
    );


    private final Setting<Boolean> noArmorSetting = entitySettings.add(new BoolSetting.Builder()
            .name("Armor")
            .description("Disable rendering of armor on entities.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noInvisibilitySetting = entitySettings.add(new BoolSetting.Builder()
            .name("Invisibility")
            .description("Show invisible entities.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noGlowingSetting = entitySettings.add(new BoolSetting.Builder()
            .name("Glowing")
            .description(" rendering of the glowing effect")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noMobInSpawnerSetting = entitySettings.add(new BoolSetting.Builder()
            .name("Spawner entities")
            .description("Disable rendering of spinning mobs inside of mob spawners")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noDeadEntitiesSetting = entitySettings.add(new BoolSetting.Builder()
            .name("Dead entities")
            .description("Disable rendering of dead entities")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> noNametagsSetting = entitySettings.add(new BoolSetting.Builder()
            .name("Nametags")
            .description("Disable rendering of entity nametags")
            .defaultValue(false)
            .build()
    );

    public NoRender(Category category) {
        super(category, "No Render", "Disables certain animations or overlays from rendering.");
    }

    // Overlay

    public boolean noHurtCam() {
        return isEnabled() && noHurtCamSetting.get();
    }

    public boolean noPortalOverlay() {
        return isEnabled() && noPortalOverlaySetting.get();
    }

    public boolean noSpyglassOverlay() {
        return isEnabled() && noSpyglassOverlaySetting.get();
    }

    public boolean noNausea() {
        return isEnabled() && noNauseaSetting.get();
    }

    public boolean noPumpkinOverlay() {
        return isEnabled() && noPumpkinOverlaySetting.get();
    }

    public boolean noFireOverlay() {
        return isEnabled() && noFireOverlaySetting.get();
    }

    public boolean noLiquidOverlay() {
        return isEnabled() && noLiquidOverlaySetting.get();
    }

    public boolean noPowderedSnowOverlay() {
        return isEnabled() && noPowderedSnowOverlaySetting.get();
    }

    public boolean noInWallOverlay() {
        return isEnabled() && noInWallOverlaySetting.get();
    }

    public boolean noVignette() {
        return isEnabled() && noVignetteSetting.get();
    }

    public boolean noGuiBackground() {
        return isEnabled() && noGuiBackgroundSetting.get();
    }

    public boolean noTotemAnimation() {
        return isEnabled() && noTotemAnimationSetting.get();
    }

    public boolean noEatParticles() {
        return isEnabled() && noEatParticlesSetting.get();
    }

    // HUD

    public boolean noBossBar() {
        return isEnabled() && noBossBarSetting.get();
    }

    public boolean noScoreboard() {
        return isEnabled() && noScoreboardSetting.get();
    }

    public boolean noCrosshair() {
        return isEnabled() && noCrosshairSetting.get();
    }

    public boolean noHeldItemName() {
        return isEnabled() && noHeldItemNameSetting.get();
    }

    public boolean noPotionIcons() {
        return isEnabled() && noPotionIconsSetting.get();
    }

    public boolean noMessageSignatureIndicator() {
        return isEnabled() && noMessageSignatureIndicatorSetting.get();
    }
    
    // World

    public boolean noWeather() {
        return isEnabled() && noWeatherSetting.get();
    }

    public boolean noFog() {
        return isEnabled() && noFogSetting.get();
    }

    public boolean noEnchantingTableBook() {
        return isEnabled() && noEnchantmentTableBookSetting.get();
    }

    public boolean noSignText() {
        return isEnabled() && noSignTextSetting.get();
    }

    public boolean noBlockBreakParticles() {
        return isEnabled() && noBlockBreakParticlesSetting.get();
    }

    public boolean noBlockBreakOverlay() {
        return isEnabled() && noBlockBreakOverlaySetting.get();
    }

    public boolean noSkylightUpdates() {
        return isEnabled() && noSkylightUpdatesSetting.get();
    }

    public boolean noFallingBlocks() {
        return isEnabled() && noFallingBlocksSetting.get();
    }

    @EventHandler
    private void onChunkOcclusion(ChunkOcclusionEvent event) {
        if (noCaveCullingSetting.get()) {
            event.cancel();
        }
    }

    public boolean noMapMarkers() {
        return isEnabled() && noMapMarkersSetting.get();
    }

    public BannerRenderMode getBannerRenderMode() {
        if (!isEnabled()) {
            return BannerRenderMode.Everything;
        } else {
            return bannerRenderSetting.get();
        }
    }

    public boolean noFireworkExplosions() {
        return isEnabled() && noFireworkExplosionsSetting.get();
    }

    @EventHandler
    private void onAddParticle(ParticleEvent event) {
        if (noWeatherSetting.get() && event.particle.getType() == ParticleTypes.RAIN) {
            event.cancel();
        } else if (noFireworkExplosionsSetting.get() && event.particle.getType() == ParticleTypes.FIREWORK) {
            event.cancel();
        } else if (particlesSetting.get().contains(event.particle.getType())) {
            event.cancel();
        }
    }

    public boolean noBarrierInvisibility() {
        return isEnabled() && noBarrierInvisibilitySetting.get();
    }

    // Entity

    public boolean noEntity(Entity entity) {
        return isEnabled() && entitiesSetting.get().getBoolean(entity.getType());
    }

    public boolean noEntity(EntityType<?> entity) {
        return isEnabled() && entitiesSetting.get().getBoolean(entity);
    }

    public boolean getDropSpawnPacket() {
        return isEnabled() && dropSpawnPacketSetting.get();
    }

    public boolean noArmor() {
        return isEnabled() && noArmorSetting.get();
    }

    public boolean noInvisibility() {
        return isEnabled() && noInvisibilitySetting.get();
    }

    public boolean noGlowing() {
        return isEnabled() && noGlowingSetting.get();
    }

    public boolean noMobInSpawner() {
        return isEnabled() && noMobInSpawnerSetting.get();
    }

    public boolean noDeadEntities() {
        return isEnabled() && noDeadEntitiesSetting.get();
    }

    public boolean noNametags() {
        return isEnabled() && noNametagsSetting.get();
    }

    public enum BannerRenderMode {
        Everything,
        Pillar,
        None
    }
}