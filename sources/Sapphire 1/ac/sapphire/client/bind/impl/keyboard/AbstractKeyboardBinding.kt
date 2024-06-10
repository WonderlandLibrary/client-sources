package ac.sapphire.client.bind.impl.keyboard

import ac.sapphire.client.bind.IBinding

abstract class AbstractKeyboardBinding(var keyCode: Int) : IBinding {
    abstract override val onPress: () -> Unit
}