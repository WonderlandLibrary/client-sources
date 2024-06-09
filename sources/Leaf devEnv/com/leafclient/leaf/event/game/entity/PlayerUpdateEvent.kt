package com.leafclient.leaf.event.game.entity

abstract class PlayerUpdateEvent {

    /**
     * Event called when the client player is updated (head of the method)
     */
    class Pre: PlayerUpdateEvent()

    /**
     * Event called when the client player is updated (tail of the method)
     */
    class Post: PlayerUpdateEvent()

}