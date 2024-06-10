package ac.sapphire.client.module

import ac.sapphire.client.model.IToggleable

abstract class AbstractModuleMode<M : AbstractModule>(val parent: M, val name: String, val displayName: String) :
    IToggleable