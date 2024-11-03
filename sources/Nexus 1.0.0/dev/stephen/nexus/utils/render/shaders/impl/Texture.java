package dev.stephen.nexus.utils.render.shaders.impl;

import net.minecraft.util.Identifier;

public class Texture {

    final Identifier id;

    public Texture(String path) {
        id = Identifier.of("nexus", validatePath(path));
    }

    public Texture(Identifier i) {
        id = Identifier.of(i.getNamespace(), i.getPath());
    }

    String validatePath(String path) {
        if (Identifier.isPathValid(path)) {
            return path;
        }
        StringBuilder ret = new StringBuilder();
        for (char c : path.toLowerCase().toCharArray()) {
            if (Identifier.isPathCharacterValid(c)) {
                ret.append(c);
            }
        }
        return ret.toString();
    }

    public Identifier getId() {
        return id;
    }
}