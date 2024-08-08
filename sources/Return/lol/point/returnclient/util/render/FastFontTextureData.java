package lol.point.returnclient.util.render;

import java.nio.ByteBuffer;

public record FastFontTextureData(int textureId, int width, int height, ByteBuffer buffer) {
}