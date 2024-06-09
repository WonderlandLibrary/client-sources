package com.leafclient.leaf.management.forge

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

@SortingIndex(29384)
class ForgeLoadingPlugin: IFMLLoadingPlugin {

    init {
        MixinBootstrap.init()
        Mixins.addConfiguration("mixins.leaf.json")
    }

    override fun getASMTransformerClass()
            = emptyArray<String>()

    override fun getModContainerClass()
            = null

    override fun getSetupClass()
            = null

    override fun getAccessTransformerClass()
            = null

    override fun injectData(data: MutableMap<String, Any>?) {}

}