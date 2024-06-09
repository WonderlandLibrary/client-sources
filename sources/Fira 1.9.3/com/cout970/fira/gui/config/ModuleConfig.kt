package com.cout970.fira.gui.config

import com.cout970.fira.MOD_ID
import com.cout970.fira.MOD_VERSION
import com.cout970.fira.gui.Keybinds
import com.cout970.fira.util.FieldProxy
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.nbt.JsonToNBT
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.config.Config
import net.minecraftforge.common.config.ConfigManager

object ModuleConfig {
    var mouseX: Int = 0
    var mouseY: Int = 0
    var delta: Float = 0f

    lateinit var config: List<ModuleConfigColumn>
        private set

    fun createConfig() {
        config = listOf(
                ModuleConfigColumn("Misc", listOf(
                        moduleOf("ChatFilter", com.cout970.fira.Config.Chat),
                        moduleOf("CrystalPvP", com.cout970.fira.Config.CrystalPvP),
                        moduleOf("InventoryUtilities", com.cout970.fira.Config.InventoryUtilities)
                ) + (
                        if (MOD_VERSION == "0.0.0")
                            listOf(moduleOf("Debug", com.cout970.fira.Config.Debug))
                        else
                            emptyList()
                        )
                ),
                ModuleConfigColumn("HUD", listOf(
                        moduleOf("HUDText", com.cout970.fira.Config.HudColors),
                        moduleOf("ScreenInventory", com.cout970.fira.Config.ScreenInventory)
                )),
                ModuleConfigColumn("Elytra", listOf(
                        moduleOf("ElytraFly", com.cout970.fira.Config.ElytraFly),
                        moduleOf("ElytraTweaks", com.cout970.fira.Config.ElytraTweaks)
                )),
                ModuleConfigColumn("FlyUtils", listOf(
                        moduleOf("PacketFly", com.cout970.fira.Config.PacketFly),
                        moduleOf("AutoPilot", com.cout970.fira.Config.AutoPilot),
                        moduleOf("AutoFly", com.cout970.fira.Config.AutoFly),
                        moduleOf("YawLock", com.cout970.fira.Config.YawLock)
                ))
        )

        config.forEachIndexed { i, col ->
            col.posX = 4f + (COLUMN_WIDTH + 4) * i
            col.posY = 4f
        }

        loadConfig()
    }

    fun saveConfig() {
        Minecraft.getMinecraft().addScheduledTask {
            try {
                val nbt = NBTTagCompound()
                config.forEach { it.save(nbt) }
                com.cout970.fira.Config.Debug.guiConfig = nbt.toString()
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    fun loadConfig() {
        try {
            val json = com.cout970.fira.Config.Debug.guiConfig
            val nbt = JsonToNBT.getTagFromJson(json)
            config.forEach { it.load(nbt) }
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun moduleOf(name: String, config: Any): ModuleConfigModule {
        val (enable, options) = optionsFromConfig(config)
        return ModuleConfigModule(name, options, enable)
    }

    fun optionsFromConfig(config: Any): Pair<ConfigOptionBoolean?, List<ConfigOption>> {
        val fields = config.javaClass.declaredFields
        var enable: ConfigOptionBoolean? = null
        val options = mutableListOf<ConfigOption>()

        for (field in fields) {
            if (!field.isAnnotationPresent(Config.LangKey::class.java)) continue

            val nameKey = field.getAnnotation(Config.LangKey::class.java)?.value
                    ?: field.getAnnotation(Config.Name::class.java)?.value
                    ?: field.name

            val descriptionAnn = field.getAnnotation(Config.Comment::class.java)?.value?.joinToString("\n") ?: ""
            val name = I18n.format(nameKey)

            val descKey = nameKey.removeSuffix(".name") + ".tooltip"
            val description = I18n.format(descKey).takeIf { it != descKey } ?: descriptionAnn

            when {
                Boolean::class.java.isAssignableFrom(field.type) -> {
                    val option = ConfigOptionBoolean(name, description, FieldProxy(field, config))

                    if (field.isAnnotationPresent(com.cout970.fira.Config.EnableField::class.java)) {
                        enable = option
                    } else {
                        options += option
                    }
                }
                Float::class.java.isAssignableFrom(field.type) -> {
                    val range: Config.RangeDouble? = field.getAnnotation(Config.RangeDouble::class.java)

                    if (range != null) {
                        options += ConfigOptionSlider(name, description, range.min.toFloat(), range.max.toFloat(), FieldProxy(field, config))
                    } else {
                        options += ConfigOptionFloat(name, description, FieldProxy(field, config))
                    }
                }
                Int::class.java.isAssignableFrom(field.type) -> {
                    options += ConfigOptionInt(name, description, FieldProxy(field, config))
                }
                Keybinds.PressKeybind::class.java.isAssignableFrom(field.type) || Keybinds.ToggleKeybind::class.java.isAssignableFrom(field.type) -> {
                    options += ConfigOptionKeybind(name, description, FieldProxy(field, config))
                }
                Function::class.java.isAssignableFrom(field.type) -> {
                    @Suppress("UNCHECKED_CAST")
                    options += ConfigOptionButton(name, description, field.get(null) as () -> Unit)
                }
            }
        }

        return enable to options
    }

    fun getColumnUnderCursor(mouseX: Int, mouseY: Int): Triple<Int, Int, ModuleConfigColumn>? {
        config.forEach { col ->
            if (
                    mouseX >= col.posX && mouseX <= col.posX + col.width &&
                    mouseY >= col.posY && mouseY <= col.posY + col.height
            ) {
                return Triple((mouseX - col.posX).toInt(), (mouseY - col.posY).toInt(), col)
            }
        }

        return null
    }
}