package ac.sapphire.client.property.impl

import ac.sapphire.client.property.IProperty

class ButtonProperty(override val displayName: String, val onClick: () -> Unit) : IProperty {
    override val isInternal = false
    override var shouldDisplay: () -> Boolean = { true }
}