package com.leafclient.leaf.event.game.entity

import fr.shyrogan.publisher4k.Cancellable

abstract class PlayerMotionEvent: Cancellable() {

    /**
     * Event invoked before the movement packet is sent, allowing you to modify your
     * current rotation.
     */
    class Pre(rotationYaw: Float, rotationPitch: Float, var posX: Double, var posY: Double, var posZ: Double, var isSprinting: Boolean, var isSneaking: Boolean, var isOnGround: Boolean): PlayerMotionEvent() {
        var isRotationModified = false

        var rotationYaw = rotationYaw
            set(value) {
                if(field != value) {
                    field = value
                    isRotationModified = true
                }
            }

        var rotationPitch = rotationPitch
            set(value) {
                if(field != value) {
                    field = value
                    isRotationModified = true
                }
            }

        /**
         * A boolean to lock the view of the player
         */
        var isViewLocked = false

        /**
         * A boolean used to know if the displayed rotation (in F5) should
         * be rendered
         */
        var isHeadRotationInjected = true
    }

    /**
     * Event invoked after the movement packet is sent.
     */
    class Post(val rotationYaw: Float, val rotationPitch: Float): PlayerMotionEvent()

}