/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.gui.fonts.providers.IGlyphProviderFactory;
import net.minecraft.client.gui.fonts.providers.TrueTypeGlyphProvider;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Struct;

public class TrueTypeGlyphProviderFactory
implements IGlyphProviderFactory {
    private static final Logger RANDOM = LogManager.getLogger();
    private final ResourceLocation file;
    private final float size;
    private final float oversample;
    private final float shiftX;
    private final float shiftY;
    private final String chars;

    public TrueTypeGlyphProviderFactory(ResourceLocation resourceLocation, float f, float f2, float f3, float f4, String string) {
        this.file = resourceLocation;
        this.size = f;
        this.oversample = f2;
        this.shiftX = f3;
        this.shiftY = f4;
        this.chars = string;
    }

    public static IGlyphProviderFactory deserialize(JsonObject jsonObject) {
        Object object;
        float f = 0.0f;
        float f2 = 0.0f;
        if (jsonObject.has("shift")) {
            object = jsonObject.getAsJsonArray("shift");
            if (((JsonArray)object).size() != 2) {
                throw new JsonParseException("Expected 2 elements in 'shift', found " + ((JsonArray)object).size());
            }
            f = JSONUtils.getFloat(((JsonArray)object).get(0), "shift[0]");
            f2 = JSONUtils.getFloat(((JsonArray)object).get(1), "shift[1]");
        }
        object = new StringBuilder();
        if (jsonObject.has("skip")) {
            JsonElement jsonElement = jsonObject.get("skip");
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = JSONUtils.getJsonArray(jsonElement, "skip");
                for (int i = 0; i < jsonArray.size(); ++i) {
                    ((StringBuilder)object).append(JSONUtils.getString(jsonArray.get(i), "skip[" + i + "]"));
                }
            } else {
                ((StringBuilder)object).append(JSONUtils.getString(jsonElement, "skip"));
            }
        }
        return new TrueTypeGlyphProviderFactory(new ResourceLocation(JSONUtils.getString(jsonObject, "file")), JSONUtils.getFloat(jsonObject, "size", 11.0f), JSONUtils.getFloat(jsonObject, "oversample", 1.0f), f, f2, ((StringBuilder)object).toString());
    }

    @Override
    @Nullable
    public IGlyphProvider create(IResourceManager iResourceManager) {
        TrueTypeGlyphProvider trueTypeGlyphProvider;
        block10: {
            Struct struct = null;
            ByteBuffer byteBuffer = null;
            IResource iResource = iResourceManager.getResource(new ResourceLocation(this.file.getNamespace(), "font/" + this.file.getPath()));
            try {
                RANDOM.debug("Loading font {}", (Object)this.file);
                struct = STBTTFontinfo.malloc();
                byteBuffer = TextureUtil.readToBuffer(iResource.getInputStream());
                byteBuffer.flip();
                RANDOM.debug("Reading font {}", (Object)this.file);
                if (!STBTruetype.stbtt_InitFont((STBTTFontinfo)struct, byteBuffer)) {
                    throw new IOException("Invalid ttf");
                }
                trueTypeGlyphProvider = new TrueTypeGlyphProvider(byteBuffer, (STBTTFontinfo)struct, this.size, this.oversample, this.shiftX, this.shiftY, this.chars);
                if (iResource == null) break block10;
            } catch (Throwable throwable) {
                try {
                    if (iResource != null) {
                        try {
                            iResource.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (Exception exception) {
                    RANDOM.error("Couldn't load truetype font {}", (Object)this.file, (Object)exception);
                    if (struct != null) {
                        struct.free();
                    }
                    MemoryUtil.memFree(byteBuffer);
                    return null;
                }
            }
            iResource.close();
        }
        return trueTypeGlyphProvider;
    }
}

