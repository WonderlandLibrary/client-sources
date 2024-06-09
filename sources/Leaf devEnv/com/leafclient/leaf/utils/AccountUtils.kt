package com.leafclient.leaf.utils

import com.mojang.authlib.Agent
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
import net.minecraft.util.Session
import java.net.Proxy
import java.util.*

object AccountUtils {

    /**
     * Tries to login with specified informations to Minecraft's login servers and
     * returns the [Session] object.
     */
    fun login(username: String, password: String): Session? {
        val service = YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString())
        val userAuth = service.createUserAuthentication(Agent.MINECRAFT)
        userAuth.setUsername(username)
        userAuth.setPassword(password)
        userAuth.logIn()

        if(!userAuth.canPlayOnline())
            return null

        return Session(userAuth.selectedProfile.name, userAuth.selectedProfile.id.toString(), userAuth.authenticatedToken, userAuth.userType.getName())
    }

}