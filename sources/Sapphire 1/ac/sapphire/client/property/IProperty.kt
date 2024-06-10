package ac.sapphire.client.property

import ac.sapphire.client.model.INameable

interface IProperty : INameable {
    val isInternal: Boolean

    var shouldDisplay: () -> Boolean

    fun displayIf(filter: () -> Boolean): IProperty {
        shouldDisplay = filter
        return this
    }
}