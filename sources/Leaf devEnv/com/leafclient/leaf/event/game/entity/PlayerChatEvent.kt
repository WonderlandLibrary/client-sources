package com.leafclient.leaf.event.game.entity

import fr.shyrogan.publisher4k.Cancellable

/**
 * Event invoked when the client player sends a message in chat.
 */
class PlayerChatEvent constructor(var message: String = ""): Cancellable()