package ac.sapphire.client.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <reified T> requireMixin(any: Any) {
    contract {
        returns() implies (any is T)
    }

    if (any !is T) {
        error("Any is not T")
    }
}