package ac.sapphire.client.module

import ac.sapphire.client.model.INameable

enum class ModuleCategory : INameable {
    COMBAT {
        override val displayName = "Combat"
    },
    MOVEMENT {
        override val displayName = "Movement"
    },
    PLAYER {
        override val displayName = "Player"
    },
    VISUALS {
        override val displayName = "Visuals"
    },
    EXPLOITS {
        override val displayName = "Exploits"
    },
    MISC {
        override val displayName = "Misc"
    }
}