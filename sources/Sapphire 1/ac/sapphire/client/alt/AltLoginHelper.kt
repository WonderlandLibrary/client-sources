package ac.sapphire.client.alt

import ac.sapphire.client.alt.impl.MojangAlt
import ac.sapphire.client.ext.mc
import com.mojang.authlib.Agent
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
import net.minecraft.util.Session
import java.util.*

object AltLoginHelper {
    private val authService = YggdrasilAuthenticationService(mc.proxy, "")

    fun tryLogin(alt: IAlt): Boolean {
        try {
            if (alt is MojangAlt) {
                return mojangLogin(alt)
            }

            TODO()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private fun mojangLogin(alt: MojangAlt): Boolean {
        if (alt.cracked) {
            mc.session = Session(
                alt.email,
                UUID.nameUUIDFromBytes(alt.email.toByteArray(Charsets.UTF_8)).toString(),
                "0",
                "LEGACY"
            )

            return true
        }

        val userAuth = authService.createUserAuthentication(Agent.MINECRAFT) as YggdrasilUserAuthentication
        userAuth.setUsername(alt.email)
        userAuth.setPassword(alt.password)
        userAuth.logIn()

        if (userAuth.isLoggedIn) {
            val profile = userAuth.selectedProfile
            mc.session = Session(
                profile.name,
                profile.id.toString(),
                userAuth.authenticatedToken,
                "MOJANG"
            )

            return true
        }

        return false
    }
}