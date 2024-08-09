/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JSONException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.apache.commons.io.IOUtils;

public class ShaderGroup
implements AutoCloseable {
    private final Framebuffer mainFramebuffer;
    private final IResourceManager resourceManager;
    private final String shaderGroupName;
    private final List<Shader> listShaders = Lists.newArrayList();
    private final Map<String, Framebuffer> mapFramebuffers = Maps.newHashMap();
    private final List<Framebuffer> listFramebuffers = Lists.newArrayList();
    private Matrix4f projectionMatrix;
    private int mainFramebufferWidth;
    private int mainFramebufferHeight;
    private float time;
    private float lastStamp;

    public ShaderGroup(TextureManager textureManager, IResourceManager iResourceManager, Framebuffer framebuffer, ResourceLocation resourceLocation) throws IOException, JsonSyntaxException {
        this.resourceManager = iResourceManager;
        this.mainFramebuffer = framebuffer;
        this.time = 0.0f;
        this.lastStamp = 0.0f;
        this.mainFramebufferWidth = framebuffer.framebufferWidth;
        this.mainFramebufferHeight = framebuffer.framebufferHeight;
        this.shaderGroupName = resourceLocation.toString();
        this.resetProjectionMatrix();
        this.parseGroup(textureManager, resourceLocation);
    }

    private void parseGroup(TextureManager textureManager, ResourceLocation resourceLocation) throws IOException, JsonSyntaxException {
        IResource iResource;
        block11: {
            iResource = null;
            try {
                int n;
                JsonArray jsonArray;
                iResource = this.resourceManager.getResource(resourceLocation);
                JsonObject jsonObject = JSONUtils.fromJson(new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8));
                if (JSONUtils.isJsonArray(jsonObject, "targets")) {
                    jsonArray = jsonObject.getAsJsonArray("targets");
                    n = 0;
                    for (JsonElement jsonElement : jsonArray) {
                        try {
                            this.initTarget(jsonElement);
                        } catch (Exception exception) {
                            JSONException jSONException = JSONException.forException(exception);
                            jSONException.prependJsonKey("targets[" + n + "]");
                            throw jSONException;
                        }
                        ++n;
                    }
                }
                if (!JSONUtils.isJsonArray(jsonObject, "passes")) break block11;
                jsonArray = jsonObject.getAsJsonArray("passes");
                n = 0;
                for (JsonElement jsonElement : jsonArray) {
                    try {
                        this.parsePass(textureManager, jsonElement);
                    } catch (Exception exception) {
                        JSONException jSONException = JSONException.forException(exception);
                        jSONException.prependJsonKey("passes[" + n + "]");
                        throw jSONException;
                    }
                    ++n;
                }
            } catch (Exception exception) {
                try {
                    Object object = iResource != null ? " (" + iResource.getPackName() + ")" : "";
                    JSONException jSONException = JSONException.forException(exception);
                    jSONException.setFilenameAndFlush(resourceLocation.getPath() + (String)object);
                    throw jSONException;
                } catch (Throwable throwable) {
                    IOUtils.closeQuietly(iResource);
                    throw throwable;
                }
            }
        }
        IOUtils.closeQuietly((Closeable)iResource);
    }

    private void initTarget(JsonElement jsonElement) throws JSONException {
        if (JSONUtils.isString(jsonElement)) {
            this.addFramebuffer(jsonElement.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
        } else {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "target");
            String string = JSONUtils.getString(jsonObject, "name");
            int n = JSONUtils.getInt(jsonObject, "width", this.mainFramebufferWidth);
            int n2 = JSONUtils.getInt(jsonObject, "height", this.mainFramebufferHeight);
            if (this.mapFramebuffers.containsKey(string)) {
                throw new JSONException(string + " is already defined");
            }
            this.addFramebuffer(string, n, n2);
        }
    }

    private void parsePass(TextureManager textureManager, JsonElement jsonElement) throws IOException {
        JsonArray jsonArray;
        Object object;
        JsonObject jsonObject;
        block21: {
            jsonObject = JSONUtils.getJsonObject(jsonElement, "pass");
            String string = JSONUtils.getString(jsonObject, "name");
            String string2 = JSONUtils.getString(jsonObject, "intarget");
            String string3 = JSONUtils.getString(jsonObject, "outtarget");
            Framebuffer framebuffer = this.getFramebuffer(string2);
            Framebuffer framebuffer2 = this.getFramebuffer(string3);
            if (framebuffer == null) {
                throw new JSONException("Input target '" + string2 + "' does not exist");
            }
            if (framebuffer2 == null) {
                throw new JSONException("Output target '" + string3 + "' does not exist");
            }
            Shader shader = this.addShader(string, framebuffer, framebuffer2);
            JsonArray jsonArray2 = JSONUtils.getJsonArray(jsonObject, "auxtargets", null);
            if (jsonArray2 == null) break block21;
            int n = 0;
            for (Object object2 : jsonArray2) {
                block20: {
                    Object object3;
                    try {
                        Framebuffer framebuffer3;
                        boolean bl;
                        block22: {
                            Object object4;
                            JsonElement jsonElement2 = JSONUtils.getJsonObject((JsonElement)object2, "auxtarget");
                            object3 = JSONUtils.getString(jsonElement2, "name");
                            object = JSONUtils.getString(jsonElement2, "id");
                            if (((String)object).endsWith(":depth")) {
                                bl = true;
                                object4 = ((String)object).substring(0, ((String)object).lastIndexOf(58));
                            } else {
                                bl = false;
                                object4 = object;
                            }
                            framebuffer3 = this.getFramebuffer((String)object4);
                            if (framebuffer3 != null) break block22;
                            if (bl) {
                                throw new JSONException("Render target '" + (String)object4 + "' can't be used as depth buffer");
                            }
                            ResourceLocation resourceLocation = new ResourceLocation("textures/effect/" + (String)object4 + ".png");
                            IResource iResource = null;
                            try {
                                iResource = this.resourceManager.getResource(resourceLocation);
                            } catch (FileNotFoundException fileNotFoundException) {
                                try {
                                    throw new JSONException("Render target or texture '" + (String)object4 + "' does not exist");
                                } catch (Throwable throwable) {
                                    IOUtils.closeQuietly(iResource);
                                    throw throwable;
                                }
                            }
                            IOUtils.closeQuietly((Closeable)iResource);
                            textureManager.bindTexture(resourceLocation);
                            Texture texture = textureManager.getTexture(resourceLocation);
                            int n2 = JSONUtils.getInt(jsonElement2, "width");
                            int n3 = JSONUtils.getInt(jsonElement2, "height");
                            boolean bl2 = JSONUtils.getBoolean(jsonElement2, "bilinear");
                            if (bl2) {
                                RenderSystem.texParameter(3553, 10241, 9729);
                                RenderSystem.texParameter(3553, 10240, 9729);
                            } else {
                                RenderSystem.texParameter(3553, 10241, 9728);
                                RenderSystem.texParameter(3553, 10240, 9728);
                            }
                            shader.addAuxFramebuffer((String)object3, texture::getGlTextureId, n2, n3);
                            break block20;
                        }
                        if (bl) {
                            shader.addAuxFramebuffer((String)object3, framebuffer3::func_242997_g, framebuffer3.framebufferTextureWidth, framebuffer3.framebufferTextureHeight);
                        } else {
                            shader.addAuxFramebuffer((String)object3, framebuffer3::func_242996_f, framebuffer3.framebufferTextureWidth, framebuffer3.framebufferTextureHeight);
                        }
                    } catch (Exception exception) {
                        object3 = JSONException.forException(exception);
                        ((JSONException)object3).prependJsonKey("auxtargets[" + n + "]");
                        throw object3;
                    }
                }
                ++n;
            }
        }
        if ((jsonArray = JSONUtils.getJsonArray(jsonObject, "uniforms", null)) != null) {
            int n = 0;
            for (JsonElement jsonElement2 : jsonArray) {
                try {
                    this.initUniform(jsonElement2);
                } catch (Exception exception) {
                    object = JSONException.forException(exception);
                    ((JSONException)object).prependJsonKey("uniforms[" + n + "]");
                    throw object;
                }
                ++n;
            }
        }
    }

    private void initUniform(JsonElement jsonElement) throws JSONException {
        JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "uniform");
        String string = JSONUtils.getString(jsonObject, "name");
        ShaderUniform shaderUniform = this.listShaders.get(this.listShaders.size() - 1).getShaderManager().func_216539_a(string);
        if (shaderUniform == null) {
            throw new JSONException("Uniform '" + string + "' does not exist");
        }
        float[] fArray = new float[4];
        int n = 0;
        for (JsonElement jsonElement2 : JSONUtils.getJsonArray(jsonObject, "values")) {
            try {
                fArray[n] = JSONUtils.getFloat(jsonElement2, "value");
            } catch (Exception exception) {
                JSONException jSONException = JSONException.forException(exception);
                jSONException.prependJsonKey("values[" + n + "]");
                throw jSONException;
            }
            ++n;
        }
        switch (n) {
            default: {
                break;
            }
            case 1: {
                shaderUniform.set(fArray[0]);
                break;
            }
            case 2: {
                shaderUniform.set(fArray[0], fArray[1]);
                break;
            }
            case 3: {
                shaderUniform.set(fArray[0], fArray[1], fArray[2]);
                break;
            }
            case 4: {
                shaderUniform.set(fArray[0], fArray[1], fArray[2], fArray[3]);
            }
        }
    }

    public Framebuffer getFramebufferRaw(String string) {
        return this.mapFramebuffers.get(string);
    }

    public void addFramebuffer(String string, int n, int n2) {
        Framebuffer framebuffer = new Framebuffer(n, n2, true, Minecraft.IS_RUNNING_ON_MAC);
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mapFramebuffers.put(string, framebuffer);
        if (n == this.mainFramebufferWidth && n2 == this.mainFramebufferHeight) {
            this.listFramebuffers.add(framebuffer);
        }
    }

    @Override
    public void close() {
        for (Framebuffer object : this.mapFramebuffers.values()) {
            object.deleteFramebuffer();
        }
        for (Shader shader : this.listShaders) {
            shader.close();
        }
        this.listShaders.clear();
    }

    public Shader addShader(String string, Framebuffer framebuffer, Framebuffer framebuffer2) throws IOException {
        Shader shader = new Shader(this.resourceManager, string, framebuffer, framebuffer2);
        this.listShaders.add(this.listShaders.size(), shader);
        return shader;
    }

    private void resetProjectionMatrix() {
        this.projectionMatrix = Matrix4f.orthographic(this.mainFramebuffer.framebufferTextureWidth, this.mainFramebuffer.framebufferTextureHeight, 0.1f, 1000.0f);
    }

    public void createBindFramebuffers(int n, int n2) {
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();
        for (Shader object : this.listShaders) {
            object.setProjectionMatrix(this.projectionMatrix);
        }
        for (Framebuffer framebuffer : this.listFramebuffers) {
            framebuffer.resize(n, n2, Minecraft.IS_RUNNING_ON_MAC);
        }
    }

    public void render(float f) {
        if (f < this.lastStamp) {
            this.time += 1.0f - this.lastStamp;
            this.time += f;
        } else {
            this.time += f - this.lastStamp;
        }
        this.lastStamp = f;
        while (this.time > 20.0f) {
            this.time -= 20.0f;
        }
        for (Shader shader : this.listShaders) {
            shader.render(this.time / 20.0f);
        }
    }

    public final String getShaderGroupName() {
        return this.shaderGroupName;
    }

    private Framebuffer getFramebuffer(String string) {
        if (string == null) {
            return null;
        }
        return string.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(string);
    }
}

