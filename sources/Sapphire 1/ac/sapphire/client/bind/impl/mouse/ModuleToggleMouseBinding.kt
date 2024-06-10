package ac.sapphire.client.bind.impl.mouse

import ac.sapphire.client.module.AbstractModule

class ModuleToggleMouseBinding(button: Int, module: AbstractModule) : AbstractMouseBinding(button) {
    override val onPress: () -> Unit = {
        module.toggle()
    }
}