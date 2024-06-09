package com.cout970.fira

import com.cout970.fira.coremod.Keep
import com.cout970.fira.gui.GuiModuleConfig
import com.cout970.fira.gui.Keybinds
import com.cout970.fira.gui.config.ModuleConfig
import net.minecraft.client.Minecraft
import net.minecraftforge.common.config.Config
import org.lwjgl.input.Keyboard

class Config {

    @Config(modid = MOD_ID, category = "hud_colors", name = "$MOD_ID:hud_color")
    @Config.LangKey("config.hud_colors.name")
    object HudColors {
        @JvmField
        @Config.LangKey("config.enable.name")
        @EnableField
        var enable: Boolean = true

        @JvmField
        @Config.LangKey("config.hud.brightness.name")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        @Config.SlidingOption
        var hudColorBright: Float = 100f

        @JvmField
        @Config.LangKey("config.hud.saturation.name")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        @Config.SlidingOption
        var hudColorSaturation: Float = 90f

        @JvmField
        @Config.LangKey("config.hud.color_displacement.name")
        @Config.RangeDouble(min = 0.0, max = 360.0)
        @Config.SlidingOption
        var hudColorOffset: Float = 20f

        @JvmField
        @Config.LangKey("config.hud.color_displacement_amount.name")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        @Config.SlidingOption
        var hudColorScale: Float = 10f

        @JvmField
        @Config.LangKey("config.hud.horizontal_separation.name")
        var offsetX: Float = 3f

        @JvmField
        @Config.LangKey("config.hud.vertical_separation.name")
        var offsetY: Float = 30f

        @JvmField
        @Config.LangKey("config.hud.show_x.name")
        var showX: Boolean = true

        @JvmField
        @Config.LangKey("config.hud.show_y.name")
        var showY: Boolean = true

        @JvmField
        @Config.LangKey("config.hud.show_z.name")
        var showZ: Boolean = true

        @JvmField
        @Config.LangKey("config.hud.show_speed.name")
        var showSpeed: Boolean = true

        @JvmField
        @Config.LangKey("config.hud.show_logo.name")
        var showLogo: Boolean = true

        @JvmField
        @Config.LangKey("config.hud.restore.name")
        @Config.Ignore
        var restoreHud: () -> Unit = {
            Debug.guiConfig = ""
            ModuleConfig.createConfig()
        }

        @JvmField
        @Config.LangKey("config.hud.open_click_gui.name")
        @Config.Ignore
        var toggleModuleConfig = Keybinds.ToggleKeybind("config", Keyboard.KEY_M) {
            Minecraft.getMinecraft().displayGuiScreen(GuiModuleConfig())
        }
    }

    @Config(modid = MOD_ID, category = "screen_inventory", name = "$MOD_ID:screen_inventory")
    @Config.LangKey("config.screen_inventory.name")
    object ScreenInventory {
        @JvmField
        @Config.LangKey("config.screen_inventory.enable.name")
        @EnableField
        var enable: Boolean = true

        @JvmField
        @Config.LangKey("config.screen_inventory.offset_x.name")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        @Config.SlidingOption
        var offsetX: Float = 50f

        @JvmField
        @Config.LangKey("config.screen_inventory.offset_y.name")
        @Config.RangeDouble(min = 0.0, max = 100.0)
        @Config.SlidingOption
        var offsetY: Float = 85f

        @JvmField
        @Config.LangKey("config.screen_inventory.background.name")
        var background: Boolean = false
    }

    @Config(modid = MOD_ID, category = "chat", name = "$MOD_ID:chat")
    @Config.LangKey("config.chat.name")
    object Chat {
        @JvmField
        @Config.LangKey("config.enable.name")
        @EnableField
        var enableChatFilter: Boolean = false

        @JvmField
        @Config.LangKey("config.chat.direct_chat.name")
        var directChat: Boolean = false

        @JvmField
        @Config.LangKey("config.chat.notification_sound.name")
        var enablePing: Boolean = true

        @JvmField
        @Config.LangKey("config.chat.show_spam.name")
        var showChatSpam: Boolean = false

        @JvmField
        @Config.LangKey("config.chat.show_deaths.name")
        var showChatDeaths: Boolean = false

        @JvmField
        @Config.LangKey("config.chat.user_blacklist.name")
        var chatBlacklist: Array<String> = arrayOf()

        @JvmField
        @Config.LangKey("config.chat.user_whitelist.name")
        var spamWhitelist: Array<String> = arrayOf("RusherBOT", "usMushi", "mushout")

        @JvmField
        @Config.LangKey("config.chat.censored_words.name")
        var chatCensored: Array<String> = arrayOf()

        @JvmField
        @Config.LangKey("config.chat.regular_format.name")
        var chatMsgFormat: String = "{AQUA}@name {DARK_GREEN}@text"

        @JvmField
        @Config.LangKey("config.chat.private_format.name")
        var chatPrivateMsgFormat: String = "{LIGHT_PURPLE}@name {YELLOW}@text"

        @JvmField
        @Config.LangKey("config.chat.private_only.name")
        var chatShowOnlyPrivate: Boolean = false

        @JvmField
        @Config.LangKey("config.chat.target.name")
        var chatPrivateMsgTarget: String = ""
    }

    @Config(modid = MOD_ID, category = "elytra_tweaks", name = "$MOD_ID:elytra_teaks")
    @Config.LangKey("config.elytra_tweaks.name")
    object ElytraTweaks {
        @JvmField
        @Config.LangKey("config.elytra_tweaks.open_elytras.name")
        @EnableField
        var autoEnableElytras: Boolean = true

        @JvmField
        @Config.LangKey("config.elytra_tweaks.cooldown.name")
        var autoEnableCooldown: Int = 10

        @JvmField
        @Config.LangKey("config.elytra_tweaks.easy_takeoff.name")
        var enableEasyTakeOff: Boolean = true

        @JvmField
        @Config.LangKey("config.elytra_tweaks.disable_vanilla_behaviour.name")
        var disableVanillaBehaviour: Boolean = false

        @JvmField
        @Config.LangKey("config.elytra_tweaks.swap_elytras.name")
        var swapElytras: Boolean = false

        @JvmField
        @Config.LangKey("config.elytra_tweaks.swap_elytra_by_time.name")
        var swapElytraByTime: Int = 200

        @JvmField
        @Config.LangKey("config.elytra_tweaks.min_durability.name")
        var elytraMinDurability: Int = 10

        @JvmField
        @Config.LangKey("config.elytra_tweaks.enable_key.name")
        @Config.Ignore
        var toggleElytraTweaks = Keybinds.ToggleKeybind("elytra_tweaks", Keyboard.KEY_H) {
            autoEnableElytras = !autoEnableElytras
        }
    }

    @Config(modid = MOD_ID, category = "elytra_fly", name = "$MOD_ID:elytra_fly")
    @Config.LangKey("config.elytra_fly.name")
    object ElytraFly {
        @JvmField
        @Config.LangKey("config.enable.name")
        @EnableField
        var enable: Boolean = true

        @JvmField
        @Config.LangKey("config.elytra_fly.fall_speed.name")
        var elytraFlyFall: Float = 0.0002f

        @JvmField
        @Config.LangKey("config.elytra_fly.speed.name")
        var movementSpeed: Float = 36f

        @JvmField
        @Config.LangKey("config.elytra_fly.acceleration.name")
        var accelerationSpeed: Float = 20f / 16f

        @JvmField
        @Config.LangKey("config.elytra_fly.keep_inertia.name")
        var keepInertia: Boolean = true

        @JvmField
        @Config.LangKey("config.elytra_fly.air_drag.name")
        var airDrag: Float = 0.005f

        @JvmField
        @Config.LangKey("config.elytra_fly.move_facing.name")
        var moveFacing: Boolean = true

        @JvmField
        @Config.LangKey("config.elytra_fly.lock_controls.name")
        var lockControls: Boolean = false

        @JvmField
        @Config.LangKey("config.elytra_fly.enable_key.name")
        @Config.Ignore
        var toggleElytraFly = Keybinds.ToggleKeybind("elytra_fly", Keyboard.KEY_G) {
            enable = !enable
        }

        @JvmField
        @Config.LangKey("config.elytra_fly.free_fly_key.name")
        @Config.Ignore
        var elytraFlyControl = Keybinds.PressKeybind("elytrafly_control", Keyboard.KEY_SPACE)

        @JvmField
        @Config.LangKey("config.elytra_fly.stop_key.name")
        @Config.Ignore
        var elytraFlyStop = Keybinds.PressKeybind("elytrafly_stop", Keyboard.KEY_R)

    }

    @Config(modid = MOD_ID, category = "auto_fly", name = "$MOD_ID:auto_fly")
    @Config.LangKey("config.auto_fly.name")
    object AutoFly {

        @JvmField
        @Config.LangKey("config.auto_fly.enable.name")
        @EnableField
        var enable: Boolean = false

        @JvmField
        @Config.LangKey("config.auto_fly.enable_key.name")
        @Config.Ignore
        var toggleAutoFly = Keybinds.ToggleKeybind("auto_fly_enable", Keyboard.KEY_ESCAPE) {
            enable = !enable
        }
    }

    @Config(modid = MOD_ID, category = "yawlock", name = "$MOD_ID:yawlock")
    @Config.LangKey("config.yawlock.name")
    object YawLock {

        @JvmField
        @Config.LangKey("config.yawlock.enable.name")
        @EnableField
        var enable: Boolean = false

        @JvmField
        @Config.LangKey("config.yawlock.only_flying.name")
        var onlyFlying: Boolean = false

        @JvmField
        @Config.LangKey("config.yawlock.ignore_key.name")
        @Config.Ignore
        var ignoreYawLock = Keybinds.PressKeybind("yawlock_ignore", Keyboard.KEY_LCONTROL)

        @JvmField
        @Config.LangKey("config.yawlock.enable_key.name")
        @Config.Ignore
        var toggleYawLock = Keybinds.ToggleKeybind("yawlock_enable", Keyboard.KEY_ESCAPE) {
            enable = !enable
        }
    }

    @Config(modid = MOD_ID, category = "auto_pilot", name = "$MOD_ID:auto_pilot")
    @Config.LangKey("config.auto_pilot.name")
    object AutoPilot {
        @JvmField
        @Config.LangKey("config.enable.name")
        @EnableField
        var enable: Boolean = false

        @JvmField
        @Config.LangKey("config.auto_pilot.height.name")
        var autoPilotHeight: Float = 66f

        @JvmField
        @Config.LangKey("config.auto_pilot.collision_detection.name")
        var autoPilotCollisionDetection: Float = 3.0f

        @JvmField
        @Config.LangKey("config.auto_pilot.use_rockets.name")
        var autoUseRockets: Boolean = true

        @JvmField
        @Config.LangKey("config.auto_pilot.rocket_cooldown.name")
        var autoUseRocketsDelay: Int = 20

        @JvmField
        @Config.LangKey("config.auto_pilot.min_speed.name")
        var autoPilotMinSpeed: Float = 30f

        @JvmField
        @Config.LangKey("config.auto_pilot.enable_key.name")
        @Config.Ignore
        var toggleAutoPilot = Keybinds.ToggleKeybind("auto_pilot", Keyboard.KEY_P) {
            enable = !enable
        }
    }

    @Config(modid = MOD_ID, category = "packet_fly", name = "$MOD_ID:packet_fly")
    @Config.LangKey("config.packet_fly.name")
    object PacketFly {
        @JvmField
        @Config.LangKey("config.packet_fly.enable.name")
        @EnableField
        var enable: Boolean = false

        @JvmField
        @Config.Comment("Cooldown entre paquetes")
        @Config.Name("Cooldown")
        @Config.LangKey("config.packet_fly.cooldown.name")
        var cooldown: Float = 0f

        @JvmField
        @Config.LangKey("config.packet_fly.fall_speed.name")
        var fallSpeed: Float = 0.005f

        @JvmField
        @Config.LangKey("config.packet_fly.upwards_speed.name")
        var ascendingSpeed: Float = 0.05f

        @JvmField
        @Config.LangKey("config.packet_fly.ascend_key.name")
        @Config.Ignore
        var packetFlyAscending = Keybinds.PressKeybind("packetfly_ascending", Keyboard.KEY_LCONTROL)

        @JvmField
        @Config.LangKey("config.packet_fly.enable_key.name")
        @Config.Ignore
        var toggleAutoPilot = Keybinds.ToggleKeybind("packet_fly", Keyboard.KEY_ESCAPE) {
            enable = !enable
        }
    }

    @Config(modid = MOD_ID, category = "crystal_pvp", name = "$MOD_ID:crystal_pvp")
    @Config.LangKey("config.crystal_pvp.name")
    object CrystalPvP {
        @JvmField
        @Config.LangKey("config.crystal_pvp.enable_color.name")
        var colorDamage: Boolean = false

        @JvmField
        @Config.LangKey("config.crystal_pvp.surround.name")
        var surround: Boolean = false

        @JvmField
        @Config.LangKey("config.crystal_pvp.suppress_explosions.name")
        var suppressExplosions: Boolean = false

        @JvmField
        @Config.LangKey("config.crystal_pvp.enable_surround.name")
        @Config.Ignore
        var enableSurround = Keybinds.ToggleKeybind("enable_surround", Keyboard.KEY_ESCAPE) {
            surround = !surround
        }

        @JvmField
        @Config.LangKey("config.crystal_pvp.place_key.name")
        @Config.Ignore
        var placeObsidian = Keybinds.ToggleKeybind("place_obsidian", Keyboard.KEY_ESCAPE) {
            com.cout970.fira.modules.CrystalPvP.placeObsidian(true)
        }
    }

    @Config(modid = MOD_ID, category = "inventory_utilities", name = "$MOD_ID:inventory_utilities")
    @Config.LangKey("config.inventory_utilities.name")
    object InventoryUtilities {

        @JvmField
        @Config.LangKey("config.inventory_utilities.only_shulkers.name")
        var onlyShulkers: Boolean = true

        @JvmField
        @Config.LangKey("config.inventory_utilities.take_items.name")
        @Config.Ignore
        var takeItems = Keybinds.MenuToggleKeybind("take_items", Keyboard.KEY_ESCAPE) {
            com.cout970.fira.modules.InventoryUtilities.takeItems()
        }
        @JvmField
        @Config.LangKey("config.inventory_utilities.drop_items.name")
        @Config.Ignore
        var dropItems = Keybinds.MenuToggleKeybind("drop_items", Keyboard.KEY_ESCAPE) {
            com.cout970.fira.modules.InventoryUtilities.dropItems()
        }
    }

    @Config(modid = MOD_ID, category = "debug", name = "$MOD_ID:debug")
    @Config.LangKey("config.debug.name")
    object Debug {
        @JvmField
        @Config.LangKey("Debug Mode")
        @EnableField
        var debugMode: Boolean = false

        @JvmField
        @Config.LangKey("Log network packets")
        var logNetworkPackets: Boolean = false

        @JvmField
        @Config.LangKey("Filter motion packets")
        var filterMotionPackets: Boolean = false

        @JvmField
        @Config.LangKey("Flood server with packets")
        var packetFlood: Boolean = false

        @JvmField
        @Config.LangKey("Cooldown")
        var packetFloodCooldown: Int = 17

        @JvmField
        @Config.LangKey("Serialized GUI config (do not change)")
        var guiConfig: String = ""
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FIELD)
    @Keep
    annotation class EnableField
}