package com.leafclient.leaf.event.game.world

import fr.shyrogan.publisher4k.Cancellable
import net.minecraft.util.SoundEvent

class WorldSoundEvent(val soundEvent: SoundEvent, var volume: Float, var pitch: Float): Cancellable()