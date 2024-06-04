package com.polarware.event.impl.motion;

import com.polarware.event.CancellableEvent;
import com.polarware.util.interfaces.InstanceAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class PostStrafeEvent extends CancellableEvent implements InstanceAccess {

}
