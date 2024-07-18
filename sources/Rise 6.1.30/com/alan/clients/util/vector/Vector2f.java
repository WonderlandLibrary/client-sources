package com.alan.clients.util.vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class Vector2f {
    public float x, y;

    public Vector2f(Vector2f vector) {
        this(vector.x, vector.y);
    }
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

}
