package com.polarware.event.impl.render;

import com.polarware.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class HurtRenderEvent extends CancellableEvent {

    private boolean oldDamage;

}
