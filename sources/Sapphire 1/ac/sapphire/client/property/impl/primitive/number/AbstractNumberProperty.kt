package ac.sapphire.client.property.impl.primitive.number

import ac.sapphire.client.property.AbstractProperty

abstract class AbstractNumberProperty<T : Number> @JvmOverloads constructor(
    override val displayName: String,
    override var value: T,
    override val isInternal: Boolean = false,
    val min: T,
    val max: T
) : AbstractProperty<T>(displayName, isInternal)