package com.leafclient.leaf.event.game.render

import net.minecraft.client.renderer.culling.ICamera

class RenderEntitiesEvent(val partialTicks: Float, val camera: ICamera)