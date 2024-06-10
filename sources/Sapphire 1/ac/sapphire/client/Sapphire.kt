package ac.sapphire.client

import ac.sapphire.client.alt.AltLoginHelper
import ac.sapphire.client.alt.AltManager
import ac.sapphire.client.alt.impl.MojangAlt
import ac.sapphire.client.bind.BindingManager
import ac.sapphire.client.event.EventBus
import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.game.GameQuitEvent
import ac.sapphire.client.event.events.game.GameStartEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.file.FileManager
import ac.sapphire.client.module.ModuleManager
import ac.sapphire.client.property.PropertyManager
import java.io.File
import java.util.*
import kotlin.contracts.ExperimentalContracts

object Sapphire {
    val dataDir = File(mc.mcDataDir, "Sapphire")

    val eventBus = EventBus()
    val altManager = AltManager()
    val bindingManager = BindingManager(eventBus)
    val propertyManager = PropertyManager()
    val moduleManager = ModuleManager(propertyManager)
    val fileManager = FileManager(altManager)

    init {
        eventBus.subscribe(this)
    }

    @Subscribe
    @ExperimentalContracts
    private fun onGameStart(event: GameStartEvent) {
        fileManager.loadAll()

        moduleManager.registerModules()

        if (System.getenv().containsKey("MC_CREDS")) {
            val creds = System.getenv("MC_CREDS").split(":")
            val email = creds[0]
            val pass = creds[1]

            AltLoginHelper.tryLogin(MojangAlt(email, pass))
        }
    }

    @Subscribe
    private fun onGameQuit(event: GameQuitEvent) {
        fileManager.saveAll()
    }

    var VERSION: String = "1"
        private set

    init {
        try {
            val stream = Sapphire::class.java.classLoader.getResourceAsStream("git.properties")
            if (stream != null) {
                val properties = Properties()
                properties.load(stream)
                stream.close()

                val tag = properties.getProperty("git.commit.id.abbrev")
                val dirty = properties.getProperty("git.dirty").toBoolean()

                VERSION = tag + if (dirty) "-dirty" else ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}