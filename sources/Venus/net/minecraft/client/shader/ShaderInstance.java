/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.shader.IShaderManager;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JSONBlendingMode;
import net.minecraft.client.util.JSONException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderInstance
implements IShaderManager,
AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ShaderDefault field_216546_b = new ShaderDefault();
    private static ShaderInstance field_216547_c;
    private static int field_216548_d;
    private final Map<String, IntSupplier> field_216549_e = Maps.newHashMap();
    private final List<String> field_216550_f = Lists.newArrayList();
    private final List<Integer> field_216551_g = Lists.newArrayList();
    private final List<ShaderUniform> field_216552_h = Lists.newArrayList();
    private final List<Integer> field_216553_i = Lists.newArrayList();
    private final Map<String, ShaderUniform> field_216554_j = Maps.newHashMap();
    private final int field_216555_k;
    private final String field_216556_l;
    private boolean field_216558_n;
    private final JSONBlendingMode field_216559_o;
    private final List<Integer> field_216560_p;
    private final List<String> field_216561_q;
    private final ShaderLoader field_216562_r;
    private final ShaderLoader field_216563_s;

    public ShaderInstance(IResourceManager iResourceManager, String string) throws IOException {
        ResourceLocation resourceLocation = new ResourceLocation("shaders/program/" + string + ".json");
        this.field_216556_l = string;
        IResource iResource = null;
        try {
            JsonArray jsonArray;
            JsonArray jsonArray2;
            iResource = iResourceManager.getResource(resourceLocation);
            JsonObject jsonObject = JSONUtils.fromJson(new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8));
            String string2 = JSONUtils.getString(jsonObject, "vertex");
            String string3 = JSONUtils.getString(jsonObject, "fragment");
            JsonArray jsonArray3 = JSONUtils.getJsonArray(jsonObject, "samplers", null);
            if (jsonArray3 != null) {
                int n = 0;
                for (Object object : jsonArray3) {
                    try {
                        this.func_216541_a((JsonElement)object);
                    } catch (Exception exception) {
                        JSONException jSONException = JSONException.forException(exception);
                        jSONException.prependJsonKey("samplers[" + n + "]");
                        throw jSONException;
                    }
                    ++n;
                }
            }
            if ((jsonArray2 = JSONUtils.getJsonArray(jsonObject, "attributes", null)) != null) {
                int n = 0;
                this.field_216560_p = Lists.newArrayListWithCapacity(jsonArray2.size());
                this.field_216561_q = Lists.newArrayListWithCapacity(jsonArray2.size());
                for (JsonElement object : jsonArray2) {
                    try {
                        this.field_216561_q.add(JSONUtils.getString(object, "attribute"));
                    } catch (Exception exception) {
                        JSONException jSONException = JSONException.forException(exception);
                        jSONException.prependJsonKey("attributes[" + n + "]");
                        throw jSONException;
                    }
                    ++n;
                }
            } else {
                this.field_216560_p = null;
                this.field_216561_q = null;
            }
            if ((jsonArray = JSONUtils.getJsonArray(jsonObject, "uniforms", null)) != null) {
                int n = 0;
                for (JsonElement jsonElement : jsonArray) {
                    try {
                        this.func_216540_b(jsonElement);
                    } catch (Exception exception) {
                        JSONException jSONException = JSONException.forException(exception);
                        jSONException.prependJsonKey("uniforms[" + n + "]");
                        throw jSONException;
                    }
                    ++n;
                }
            }
            this.field_216559_o = ShaderInstance.func_216543_a(JSONUtils.getJsonObject(jsonObject, "blend", null));
            this.field_216562_r = ShaderInstance.func_216542_a(iResourceManager, ShaderLoader.ShaderType.VERTEX, string2);
            this.field_216563_s = ShaderInstance.func_216542_a(iResourceManager, ShaderLoader.ShaderType.FRAGMENT, string3);
            this.field_216555_k = ShaderLinkHelper.createProgram();
            ShaderLinkHelper.linkProgram(this);
            this.func_216536_h();
            if (this.field_216561_q != null) {
                for (String string4 : this.field_216561_q) {
                    int n = ShaderUniform.func_227807_b_(this.field_216555_k, string4);
                    this.field_216560_p.add(n);
                }
            }
        } catch (Exception exception) {
            Object object = iResource != null ? " (" + iResource.getPackName() + ")" : "";
            JSONException jSONException = JSONException.forException(exception);
            jSONException.setFilenameAndFlush(resourceLocation.getPath() + (String)object);
            throw jSONException;
        } finally {
            IOUtils.closeQuietly((Closeable)iResource);
        }
        this.markDirty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ShaderLoader func_216542_a(IResourceManager iResourceManager, ShaderLoader.ShaderType shaderType, String string) throws IOException {
        ShaderLoader shaderLoader = shaderType.getLoadedShaders().get(string);
        if (shaderLoader == null) {
            ResourceLocation resourceLocation = new ResourceLocation("shaders/program/" + string + shaderType.getShaderExtension());
            IResource iResource = iResourceManager.getResource(resourceLocation);
            try {
                shaderLoader = ShaderLoader.func_216534_a(shaderType, string, iResource.getInputStream(), iResource.getPackName());
            } finally {
                IOUtils.closeQuietly((Closeable)iResource);
            }
        }
        return shaderLoader;
    }

    public static JSONBlendingMode func_216543_a(JsonObject jsonObject) {
        if (jsonObject == null) {
            return new JSONBlendingMode();
        }
        int n = 32774;
        int n2 = 1;
        int n3 = 0;
        int n4 = 1;
        int n5 = 0;
        boolean bl = true;
        boolean bl2 = false;
        if (JSONUtils.isString(jsonObject, "func") && (n = JSONBlendingMode.stringToBlendFunction(jsonObject.get("func").getAsString())) != 32774) {
            bl = false;
        }
        if (JSONUtils.isString(jsonObject, "srcrgb") && (n2 = JSONBlendingMode.stringToBlendFactor(jsonObject.get("srcrgb").getAsString())) != 1) {
            bl = false;
        }
        if (JSONUtils.isString(jsonObject, "dstrgb") && (n3 = JSONBlendingMode.stringToBlendFactor(jsonObject.get("dstrgb").getAsString())) != 0) {
            bl = false;
        }
        if (JSONUtils.isString(jsonObject, "srcalpha")) {
            n4 = JSONBlendingMode.stringToBlendFactor(jsonObject.get("srcalpha").getAsString());
            if (n4 != 1) {
                bl = false;
            }
            bl2 = true;
        }
        if (JSONUtils.isString(jsonObject, "dstalpha")) {
            n5 = JSONBlendingMode.stringToBlendFactor(jsonObject.get("dstalpha").getAsString());
            if (n5 != 0) {
                bl = false;
            }
            bl2 = true;
        }
        if (bl) {
            return new JSONBlendingMode();
        }
        return bl2 ? new JSONBlendingMode(n2, n3, n4, n5, n) : new JSONBlendingMode(n2, n3, n);
    }

    @Override
    public void close() {
        for (ShaderUniform shaderUniform : this.field_216552_h) {
            shaderUniform.close();
        }
        ShaderLinkHelper.deleteShader(this);
    }

    public void func_216544_e() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        ShaderLinkHelper.func_227804_a_(0);
        field_216548_d = -1;
        field_216547_c = null;
        for (int i = 0; i < this.field_216551_g.size(); ++i) {
            if (this.field_216549_e.get(this.field_216550_f.get(i)) == null) continue;
            GlStateManager.activeTexture(33984 + i);
            GlStateManager.disableTexture();
            GlStateManager.bindTexture(0);
        }
    }

    public void func_216535_f() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        this.field_216558_n = false;
        field_216547_c = this;
        this.field_216559_o.apply();
        if (this.field_216555_k != field_216548_d) {
            ShaderLinkHelper.func_227804_a_(this.field_216555_k);
            field_216548_d = this.field_216555_k;
        }
        for (int i = 0; i < this.field_216551_g.size(); ++i) {
            String object = this.field_216550_f.get(i);
            IntSupplier intSupplier = this.field_216549_e.get(object);
            if (intSupplier == null) continue;
            RenderSystem.activeTexture(33984 + i);
            RenderSystem.enableTexture();
            int n = intSupplier.getAsInt();
            if (n == -1) continue;
            RenderSystem.bindTexture(n);
            ShaderUniform.func_227805_a_(this.field_216551_g.get(i), i);
        }
        for (ShaderUniform shaderUniform : this.field_216552_h) {
            shaderUniform.upload();
        }
    }

    @Override
    public void markDirty() {
        this.field_216558_n = true;
    }

    @Nullable
    public ShaderUniform func_216539_a(String string) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return this.field_216554_j.get(string);
    }

    public ShaderDefault getShaderUniform(String string) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        ShaderUniform shaderUniform = this.func_216539_a(string);
        return shaderUniform == null ? field_216546_b : shaderUniform;
    }

    private void func_216536_h() {
        int n;
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        IntArrayList intArrayList = new IntArrayList();
        for (n = 0; n < this.field_216550_f.size(); ++n) {
            String object = this.field_216550_f.get(n);
            int n2 = ShaderUniform.func_227806_a_(this.field_216555_k, object);
            if (n2 == -1) {
                LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", (Object)this.field_216556_l, (Object)object);
                this.field_216549_e.remove(object);
                intArrayList.add(n);
                continue;
            }
            this.field_216551_g.add(n2);
        }
        for (n = intArrayList.size() - 1; n >= 0; --n) {
            this.field_216550_f.remove(intArrayList.getInt(n));
        }
        for (ShaderUniform shaderUniform : this.field_216552_h) {
            String string = shaderUniform.getShaderName();
            int n3 = ShaderUniform.func_227806_a_(this.field_216555_k, string);
            if (n3 == -1) {
                LOGGER.warn("Could not find uniform named {} in the specified shader program.", (Object)string);
                continue;
            }
            this.field_216553_i.add(n3);
            shaderUniform.setUniformLocation(n3);
            this.field_216554_j.put(string, shaderUniform);
        }
    }

    private void func_216541_a(JsonElement jsonElement) {
        JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "sampler");
        String string = JSONUtils.getString(jsonObject, "name");
        if (!JSONUtils.isString(jsonObject, "file")) {
            this.field_216549_e.put(string, null);
            this.field_216550_f.add(string);
        } else {
            this.field_216550_f.add(string);
        }
    }

    public void func_216537_a(String string, IntSupplier intSupplier) {
        if (this.field_216549_e.containsKey(string)) {
            this.field_216549_e.remove(string);
        }
        this.field_216549_e.put(string, intSupplier);
        this.markDirty();
    }

    private void func_216540_b(JsonElement jsonElement) throws JSONException {
        Object object2;
        JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "uniform");
        String string = JSONUtils.getString(jsonObject, "name");
        int n = ShaderUniform.parseType(JSONUtils.getString(jsonObject, "type"));
        int n2 = JSONUtils.getInt(jsonObject, "count");
        float[] fArray = new float[Math.max(n2, 16)];
        JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "values");
        if (jsonArray.size() != n2 && jsonArray.size() > 1) {
            throw new JSONException("Invalid amount of values specified (expected " + n2 + ", found " + jsonArray.size() + ")");
        }
        int n3 = 0;
        for (Object object2 : jsonArray) {
            try {
                fArray[n3] = JSONUtils.getFloat((JsonElement)object2, "value");
            } catch (Exception exception) {
                JSONException jSONException = JSONException.forException(exception);
                jSONException.prependJsonKey("values[" + n3 + "]");
                throw jSONException;
            }
            ++n3;
        }
        if (n2 > 1 && jsonArray.size() == 1) {
            while (n3 < n2) {
                fArray[n3] = fArray[0];
                ++n3;
            }
        }
        int n4 = n2 > 1 && n2 <= 4 && n < 8 ? n2 - 1 : 0;
        object2 = new ShaderUniform(string, n + n4, n2, this);
        if (n <= 3) {
            ((ShaderUniform)object2).set((int)fArray[0], (int)fArray[1], (int)fArray[2], (int)fArray[3]);
        } else if (n <= 7) {
            ((ShaderUniform)object2).setSafe(fArray[0], fArray[1], fArray[2], fArray[3]);
        } else {
            ((ShaderUniform)object2).set(fArray);
        }
        this.field_216552_h.add((ShaderUniform)object2);
    }

    @Override
    public ShaderLoader getVertexShaderLoader() {
        return this.field_216562_r;
    }

    @Override
    public ShaderLoader getFragmentShaderLoader() {
        return this.field_216563_s;
    }

    @Override
    public int getProgram() {
        return this.field_216555_k;
    }

    static {
        field_216548_d = -1;
    }
}

