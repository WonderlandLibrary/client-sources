package com.alan.clients.layer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Layers {
    BLOOM(Type.BLOOM),
    BLUR(Type.BLUR),
    REGULAR(Type.REGULAR);

    final Type type;
}
