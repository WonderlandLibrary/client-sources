package ac.sapphire.client.bind.impl.mouse

import ac.sapphire.client.bind.IBinding

abstract class AbstractMouseBinding(var button: Int) : IBinding {
    abstract override val onPress: () -> Unit
}