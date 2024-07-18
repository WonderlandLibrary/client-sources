package com.alan.clients.layer;

import com.alan.clients.util.shader.impl.BloomShader;
import com.alan.clients.util.shader.impl.BlurShader;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {
    REGULAR(null),
    BLOOM(BloomShader.class),
    BLUR(BlurShader.class);

    private final Class<?> shader;
}