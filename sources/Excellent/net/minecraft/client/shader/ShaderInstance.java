package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.JSONBlendingMode;
import net.minecraft.client.util.JSONException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;

public class ShaderInstance implements IShaderManager, AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ShaderDefault DUMMY_UNIFORM = new ShaderDefault();
    private static ShaderInstance field_216547_c;
    private static int field_216548_d = -1;
    private final Map<String, IntSupplier> field_216549_e = Maps.newHashMap();
    private final List<String> field_216550_f = Lists.newArrayList();
    private final List<Integer> field_216551_g = Lists.newArrayList();
    private final List<ShaderUniform> field_216552_h = Lists.newArrayList();
    private final List<Integer> field_216553_i = Lists.newArrayList();
    private final Map<String, ShaderUniform> field_216554_j = Maps.newHashMap();
    private final int programId;
    private final String name;
    private boolean field_216558_n;
    private final JSONBlendingMode blend;
    private final List<Integer> attributes;
    private final List<String> attributeNames;
    private final ShaderLoader vertexProgram;
    private final ShaderLoader fragmentProgram;

    public ShaderInstance(IResourceManager p_i50988_1_, String p_i50988_2_) throws IOException {
        ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + p_i50988_2_ + ".json");
        this.name = p_i50988_2_;
        IResource iresource = null;

        try {
            iresource = p_i50988_1_.getResource(resourcelocation);
            JsonObject jsonobject = JSONUtils.fromJson(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
            String s = JSONUtils.getString(jsonobject, "vertex");
            String s2 = JSONUtils.getString(jsonobject, "fragment");
            JsonArray jsonarray = JSONUtils.getJsonArray(jsonobject, "samplers", null);

            if (jsonarray != null) {
                int i = 0;

                for (JsonElement jsonelement : jsonarray) {
                    try {
                        this.parseSamplerNode(jsonelement);
                    } catch(Exception exception2) {
                        JSONException jsonexception1 = JSONException.forException(exception2);
                        jsonexception1.prependJsonKey("samplers[" + i + "]");
                        throw jsonexception1;
                    }

                    ++i;
                }
            }

            JsonArray jsonarray1 = JSONUtils.getJsonArray(jsonobject, "attributes", null);

            if (jsonarray1 != null) {
                int j = 0;
                this.attributes = Lists.newArrayListWithCapacity(jsonarray1.size());
                this.attributeNames = Lists.newArrayListWithCapacity(jsonarray1.size());

                for (JsonElement jsonelement1 : jsonarray1) {
                    try {
                        this.attributeNames.add(JSONUtils.getString(jsonelement1, "attribute"));
                    } catch(Exception exception1) {
                        JSONException jsonexception2 = JSONException.forException(exception1);
                        jsonexception2.prependJsonKey("attributes[" + j + "]");
                        throw jsonexception2;
                    }

                    ++j;
                }
            } else {
                this.attributes = null;
                this.attributeNames = null;
            }

            JsonArray jsonarray2 = JSONUtils.getJsonArray(jsonobject, "uniforms", null);

            if (jsonarray2 != null) {
                int k = 0;

                for (JsonElement jsonelement2 : jsonarray2) {
                    try {
                        this.parseUniformNode(jsonelement2);
                    } catch(Exception exception) {
                        JSONException jsonexception3 = JSONException.forException(exception);
                        jsonexception3.prependJsonKey("uniforms[" + k + "]");
                        throw jsonexception3;
                    }

                    ++k;
                }
            }

            this.blend = parseBlendNode(JSONUtils.getJsonObject(jsonobject, "blend", null));
            this.vertexProgram = getOrCreate(p_i50988_1_, ShaderLoader.ShaderType.VERTEX, s);
            this.fragmentProgram = getOrCreate(p_i50988_1_, ShaderLoader.ShaderType.FRAGMENT, s2);
            this.programId = ShaderLinkHelper.createProgram();
            ShaderLinkHelper.linkProgram(this);
            this.updateLocations();

            if (this.attributeNames != null) {
                for (String s3 : this.attributeNames) {
                    int l = ShaderUniform.glGetAttribLocation(this.programId, s3);
                    this.attributes.add(l);
                }
            }
        } catch(Exception exception3) {
            String s1;

            if (iresource != null) {
                s1 = " (" + iresource.getPackName() + ")";
            } else {
                s1 = "";
            }

            JSONException jsonexception = JSONException.forException(exception3);
            jsonexception.setFilenameAndFlush(resourcelocation.getPath() + s1);
            throw jsonexception;
        } finally {
            IOUtils.closeQuietly(iresource);
        }

        this.markDirty();
    }

    public static ShaderLoader getOrCreate(IResourceManager p_216542_0_, ShaderLoader.ShaderType p_216542_1_, String p_216542_2_) throws IOException {
        ShaderLoader shaderloader = p_216542_1_.getLoadedShaders().get(p_216542_2_);

        if (shaderloader == null) {
            ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + p_216542_2_ + p_216542_1_.getShaderExtension());
            IResource iresource = p_216542_0_.getResource(resourcelocation);

            try {
                shaderloader = ShaderLoader.compileShader(p_216542_1_, p_216542_2_, iresource.getInputStream(), iresource.getPackName());
            } finally {
                IOUtils.closeQuietly(iresource);
            }
        }

        return shaderloader;
    }

    // EXCELLENT START
    public ShaderInstance(String name, InputStream json, InputStream fragment, InputStream vertex) throws IOException {
        this.name = name;

        try {
            JsonObject jsonobject = JSONUtils.fromJson(new InputStreamReader(json, StandardCharsets.UTF_8));
            String s = JSONUtils.getString(jsonobject, "vertex");
            String s2 = JSONUtils.getString(jsonobject, "fragment");
            JsonArray jsonarray = JSONUtils.getJsonArray(jsonobject, "samplers", null);

            if (jsonarray != null) {
                int i = 0;

                for (JsonElement jsonelement : jsonarray) {
                    try {
                        this.parseSamplerNode(jsonelement);
                    } catch(Exception exception2) {
                        JSONException jsonexception1 = JSONException.forException(exception2);
                        jsonexception1.prependJsonKey("samplers[" + i + "]");
                        throw jsonexception1;
                    }

                    ++i;
                }
            }

            JsonArray jsonarray1 = JSONUtils.getJsonArray(jsonobject, "attributes", null);

            if (jsonarray1 != null) {
                int j = 0;
                this.attributes = Lists.newArrayListWithCapacity(jsonarray1.size());
                this.attributeNames = Lists.newArrayListWithCapacity(jsonarray1.size());

                for (JsonElement jsonelement1 : jsonarray1) {
                    try {
                        this.attributeNames.add(JSONUtils.getString(jsonelement1, "attribute"));
                    } catch(Exception exception1) {
                        JSONException jsonexception2 = JSONException.forException(exception1);
                        jsonexception2.prependJsonKey("attributes[" + j + "]");
                        throw jsonexception2;
                    }

                    ++j;
                }
            } else {
                this.attributes = null;
                this.attributeNames = null;
            }

            JsonArray jsonarray2 = JSONUtils.getJsonArray(jsonobject, "uniforms", null);

            if (jsonarray2 != null) {
                int k = 0;

                for (JsonElement jsonelement2 : jsonarray2) {
                    try {
                        this.parseUniformNode(jsonelement2);
                    } catch(Exception exception) {
                        JSONException jsonexception3 = JSONException.forException(exception);
                        jsonexception3.prependJsonKey("uniforms[" + k + "]");
                        throw jsonexception3;
                    }

                    ++k;
                }
            }

            this.blend = parseBlendNode(JSONUtils.getJsonObject(jsonobject, "blend", null));
            this.vertexProgram = getOrCreate(ShaderLoader.ShaderType.VERTEX, s, vertex);
            this.fragmentProgram = getOrCreate(ShaderLoader.ShaderType.FRAGMENT, s2, fragment);
            this.programId = ShaderLinkHelper.createProgram();
            ShaderLinkHelper.linkProgram(this);
            this.updateLocations();

            if (this.attributeNames != null) {
                for (String s3 : this.attributeNames) {
                    int l = ShaderUniform.glGetAttribLocation(this.programId, s3);
                    this.attributes.add(l);
                }
            }
        } catch(Exception exception3) {
            throw JSONException.forException(exception3);
        }

        this.markDirty();
    }

    public static ShaderLoader getOrCreate(ShaderLoader.ShaderType shaderType, String name, InputStream stream) {
        ShaderLoader shaderloader = shaderType.getLoadedShaders().get(name);
        if (shaderloader == null) {
            try {
                shaderloader = ShaderLoader.compileShader(shaderType, name, stream, name);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return shaderloader;
    }
    // EXCELLENT END

    public static JSONBlendingMode parseBlendNode(JsonObject p_216543_0_) {
        if (p_216543_0_ == null) {
            return new JSONBlendingMode();
        } else {
            int i = 32774;
            int j = 1;
            int k = 0;
            int l = 1;
            int i1 = 0;
            boolean flag = true;
            boolean flag1 = false;

            if (JSONUtils.isString(p_216543_0_, "func")) {
                i = JSONBlendingMode.stringToBlendFunction(p_216543_0_.get("func").getAsString());

                if (i != 32774) {
                    flag = false;
                }
            }

            if (JSONUtils.isString(p_216543_0_, "srcrgb")) {
                j = JSONBlendingMode.stringToBlendFactor(p_216543_0_.get("srcrgb").getAsString());

                if (j != 1) {
                    flag = false;
                }
            }

            if (JSONUtils.isString(p_216543_0_, "dstrgb")) {
                k = JSONBlendingMode.stringToBlendFactor(p_216543_0_.get("dstrgb").getAsString());

                if (k != 0) {
                    flag = false;
                }
            }

            if (JSONUtils.isString(p_216543_0_, "srcalpha")) {
                l = JSONBlendingMode.stringToBlendFactor(p_216543_0_.get("srcalpha").getAsString());

                if (l != 1) {
                    flag = false;
                }

                flag1 = true;
            }

            if (JSONUtils.isString(p_216543_0_, "dstalpha")) {
                i1 = JSONBlendingMode.stringToBlendFactor(p_216543_0_.get("dstalpha").getAsString());

                if (i1 != 0) {
                    flag = false;
                }

                flag1 = true;
            }

            if (flag) {
                return new JSONBlendingMode();
            } else {
                return flag1 ? new JSONBlendingMode(j, k, l, i1, i) : new JSONBlendingMode(j, k, i);
            }
        }
    }

    public void close() {
        for (ShaderUniform shaderuniform : this.field_216552_h) {
            shaderuniform.close();
        }

        ShaderLinkHelper.deleteShader(this);
    }

    public void clear() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        ShaderLinkHelper.func_227804_a_(0);
        field_216548_d = -1;
        field_216547_c = null;

        for (int i = 0; i < this.field_216551_g.size(); ++i) {
            if (this.field_216549_e.get(this.field_216550_f.get(i)) != null) {
                GlStateManager.activeTexture(33984 + i);
                GlStateManager.disableTexture();
                GlStateManager.bindTexture(0);
            }
        }
    }

    public void apply() {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        this.field_216558_n = false;
        field_216547_c = this;
        this.blend.apply();

        if (this.programId != field_216548_d) {
            ShaderLinkHelper.func_227804_a_(this.programId);
            field_216548_d = this.programId;
        }

        for (int i = 0; i < this.field_216551_g.size(); ++i) {
            String s = this.field_216550_f.get(i);
            IntSupplier intsupplier = this.field_216549_e.get(s);

            if (intsupplier != null) {
                RenderSystem.activeTexture(33984 + i);
                RenderSystem.enableTexture();
                int j = intsupplier.getAsInt();

                if (j != -1) {
                    RenderSystem.bindTexture(j);
                    ShaderUniform.func_227805_a_(this.field_216551_g.get(i), i);
                }
            }
        }

        for (ShaderUniform shaderuniform : this.field_216552_h) {
            shaderuniform.upload();
        }
    }

    public void markDirty() {
        this.field_216558_n = true;
    }

    @Nullable
    public ShaderUniform getUniform(String p_216539_1_) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return this.field_216554_j.get(p_216539_1_);
    }

    public ShaderDefault safeGetUniform(String p_216538_1_) {
        RenderSystem.assertThread(RenderSystem::isOnGameThread);
        ShaderUniform shaderuniform = this.getUniform(p_216538_1_);
        return shaderuniform == null ? DUMMY_UNIFORM : shaderuniform;
    }

    private void updateLocations() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        IntList intlist = new IntArrayList();

        for (int i = 0; i < this.field_216550_f.size(); ++i) {
            String s = this.field_216550_f.get(i);
            int j = ShaderUniform.func_227806_a_(this.programId, s);

            if (j == -1) {
                LOGGER.warn("Shader {} could not find sampler named {} in the specified shader program.", this.name, s);
                this.field_216549_e.remove(s);
                intlist.add(i);
            } else {
                this.field_216551_g.add(j);
            }
        }

        for (int l = intlist.size() - 1; l >= 0; --l) {
            this.field_216550_f.remove(intlist.getInt(l));
        }

        for (ShaderUniform shaderuniform : this.field_216552_h) {
            String s1 = shaderuniform.getShaderName();
            int k = ShaderUniform.func_227806_a_(this.programId, s1);

            if (k == -1) {
                LOGGER.warn("Could not find uniform named {} in the specified shader program.", s1);
            } else {
                this.field_216553_i.add(k);
                shaderuniform.setUniformLocation(k);
                this.field_216554_j.put(s1, shaderuniform);
            }
        }
    }

    private void parseSamplerNode(JsonElement p_216541_1_) {
        JsonObject jsonobject = JSONUtils.getJsonObject(p_216541_1_, "sampler");
        String s = JSONUtils.getString(jsonobject, "name");

        if (!JSONUtils.isString(jsonobject, "file")) {
            this.field_216549_e.put(s, null);
            this.field_216550_f.add(s);
        } else {
            this.field_216550_f.add(s);
        }
    }

    public void setSampler(String p_216537_1_, IntSupplier p_216537_2_) {
        this.field_216549_e.remove(p_216537_1_);

        this.field_216549_e.put(p_216537_1_, p_216537_2_);
        this.markDirty();
    }

    private void parseUniformNode(JsonElement p_216540_1_) throws JSONException {
        JsonObject jsonobject = JSONUtils.getJsonObject(p_216540_1_, "uniform");
        String s = JSONUtils.getString(jsonobject, "name");
        int i = ShaderUniform.parseType(JSONUtils.getString(jsonobject, "type"));
        int j = JSONUtils.getInt(jsonobject, "count");
        float[] afloat = new float[Math.max(j, 16)];
        JsonArray jsonarray = JSONUtils.getJsonArray(jsonobject, "values");

        if (jsonarray.size() != j && jsonarray.size() > 1) {
            throw new JSONException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
        } else {
            int k = 0;

            for (JsonElement jsonelement : jsonarray) {
                try {
                    afloat[k] = JSONUtils.getFloat(jsonelement, "value");
                } catch(Exception exception) {
                    JSONException jsonexception = JSONException.forException(exception);
                    jsonexception.prependJsonKey("values[" + k + "]");
                    throw jsonexception;
                }

                ++k;
            }

            if (j > 1 && jsonarray.size() == 1) {
                while (k < j) {
                    afloat[k] = afloat[0];
                    ++k;
                }
            }

            int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
            ShaderUniform shaderuniform = new ShaderUniform(s, i + l, j, this);

            if (i <= 3) {
                shaderuniform.set((int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]);
            } else if (i <= 7) {
                shaderuniform.setSafe(afloat[0], afloat[1], afloat[2], afloat[3]);
            } else {
                shaderuniform.set(afloat);
            }

            this.field_216552_h.add(shaderuniform);
        }
    }

    public ShaderLoader getVertexShaderLoader() {
        return this.vertexProgram;
    }

    public ShaderLoader getFragmentShaderLoader() {
        return this.fragmentProgram;
    }

    public int getProgram() {
        return this.programId;
    }
}
