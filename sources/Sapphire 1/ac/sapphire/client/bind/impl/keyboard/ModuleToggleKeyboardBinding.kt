package ac.sapphire.client.bind.impl.keyboard

import ac.sapphire.client.module.AbstractModule

class ModuleToggleKeyboardBinding(keyCode: Int, module: AbstractModule) : AbstractKeyboardBinding(keyCode) {
    override val onPress: () -> Unit = {
        module.toggle()
    }
}