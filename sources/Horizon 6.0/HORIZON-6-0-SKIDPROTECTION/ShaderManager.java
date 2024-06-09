package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.InputStream;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import org.apache.commons.io.IOUtils;
import com.google.common.base.Charsets;
import com.google.gson.JsonParser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class ShaderManager
{
    private static final Logger HorizonCode_Horizon_È;
    private static final ShaderDefault Â;
    private static ShaderManager Ý;
    private static int Ø­áŒŠá;
    private static boolean Âµá€;
    private final Map Ó;
    private final List à;
    private final List Ø;
    private final List áŒŠÆ;
    private final List áˆºÑ¢Õ;
    private final Map ÂµÈ;
    private final int á;
    private final String ˆÏ­;
    private final boolean £á;
    private boolean Å;
    private final JsonBlendingMode £à;
    private final List µà;
    private final List ˆà;
    private final ShaderLoader ¥Æ;
    private final ShaderLoader Ø­à;
    private static final String µÕ = "CL_00001040";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new ShaderDefault();
        ShaderManager.Ý = null;
        ShaderManager.Ø­áŒŠá = -1;
        ShaderManager.Âµá€ = true;
    }
    
    public ShaderManager(final IResourceManager resourceManager, final String programName) throws JsonException {
        this.Ó = Maps.newHashMap();
        this.à = Lists.newArrayList();
        this.Ø = Lists.newArrayList();
        this.áŒŠÆ = Lists.newArrayList();
        this.áˆºÑ¢Õ = Lists.newArrayList();
        this.ÂµÈ = Maps.newHashMap();
        final JsonParser var3 = new JsonParser();
        final ResourceLocation_1975012498 var4 = new ResourceLocation_1975012498("shaders/program/" + programName + ".json");
        this.ˆÏ­ = programName;
        InputStream var5 = null;
        try {
            var5 = resourceManager.HorizonCode_Horizon_È(var4).Â();
            final JsonObject var6 = var3.parse(IOUtils.toString(var5, Charsets.UTF_8)).getAsJsonObject();
            final String var7 = JsonUtils.Ó(var6, "vertex");
            final String var8 = JsonUtils.Ó(var6, "fragment");
            final JsonArray var9 = JsonUtils.HorizonCode_Horizon_È(var6, "samplers", (JsonArray)null);
            if (var9 != null) {
                int var10 = 0;
                for (final JsonElement var12 : var9) {
                    try {
                        this.HorizonCode_Horizon_È(var12);
                    }
                    catch (Exception var14) {
                        final JsonException var13 = JsonException.HorizonCode_Horizon_È(var14);
                        var13.HorizonCode_Horizon_È("samplers[" + var10 + "]");
                        throw var13;
                    }
                    ++var10;
                }
            }
            final JsonArray var15 = JsonUtils.HorizonCode_Horizon_È(var6, "attributes", (JsonArray)null);
            if (var15 != null) {
                int var16 = 0;
                this.µà = Lists.newArrayListWithCapacity(var15.size());
                this.ˆà = Lists.newArrayListWithCapacity(var15.size());
                for (final JsonElement var18 : var15) {
                    try {
                        this.ˆà.add(JsonUtils.HorizonCode_Horizon_È(var18, "attribute"));
                    }
                    catch (Exception var20) {
                        final JsonException var19 = JsonException.HorizonCode_Horizon_È(var20);
                        var19.HorizonCode_Horizon_È("attributes[" + var16 + "]");
                        throw var19;
                    }
                    ++var16;
                }
            }
            else {
                this.µà = null;
                this.ˆà = null;
            }
            final JsonArray var21 = JsonUtils.HorizonCode_Horizon_È(var6, "uniforms", (JsonArray)null);
            if (var21 != null) {
                int var22 = 0;
                for (final JsonElement var24 : var21) {
                    try {
                        this.Â(var24);
                    }
                    catch (Exception var26) {
                        final JsonException var25 = JsonException.HorizonCode_Horizon_È(var26);
                        var25.HorizonCode_Horizon_È("uniforms[" + var22 + "]");
                        throw var25;
                    }
                    ++var22;
                }
            }
            this.£à = JsonBlendingMode.HorizonCode_Horizon_È(JsonUtils.HorizonCode_Horizon_È(var6, "blend", (JsonObject)null));
            this.£á = JsonUtils.HorizonCode_Horizon_È(var6, "cull", true);
            this.¥Æ = ShaderLoader.HorizonCode_Horizon_È(resourceManager, ShaderLoader.HorizonCode_Horizon_È.HorizonCode_Horizon_È, var7);
            this.Ø­à = ShaderLoader.HorizonCode_Horizon_È(resourceManager, ShaderLoader.HorizonCode_Horizon_È.Â, var8);
            this.á = ShaderLinkHelper.Â().Ý();
            ShaderLinkHelper.Â().Â(this);
            this.Ø();
            if (this.ˆà != null) {
                for (final String var27 : this.ˆà) {
                    final int var28 = OpenGlHelper.Â(this.á, var27);
                    this.µà.add(var28);
                }
            }
        }
        catch (Exception var30) {
            final JsonException var29 = JsonException.HorizonCode_Horizon_È(var30);
            var29.Â(var4.Â());
            throw var29;
        }
        finally {
            IOUtils.closeQuietly(var5);
        }
        IOUtils.closeQuietly(var5);
        this.Ø­áŒŠá();
    }
    
    public void HorizonCode_Horizon_È() {
        ShaderLinkHelper.Â().HorizonCode_Horizon_È(this);
    }
    
    public void Â() {
        OpenGlHelper.Ø­áŒŠá(0);
        ShaderManager.Ø­áŒŠá = -1;
        ShaderManager.Ý = null;
        ShaderManager.Âµá€ = true;
        for (int var1 = 0; var1 < this.Ø.size(); ++var1) {
            if (this.Ó.get(this.à.get(var1)) != null) {
                GlStateManager.à(OpenGlHelper.£à + var1);
                GlStateManager.áŒŠÆ(0);
            }
        }
    }
    
    public void Ý() {
        this.Å = false;
        ShaderManager.Ý = this;
        this.£à.HorizonCode_Horizon_È();
        if (this.á != ShaderManager.Ø­áŒŠá) {
            OpenGlHelper.Ø­áŒŠá(this.á);
            ShaderManager.Ø­áŒŠá = this.á;
        }
        if (this.£á) {
            GlStateManager.Å();
        }
        else {
            GlStateManager.£à();
        }
        for (int var1 = 0; var1 < this.Ø.size(); ++var1) {
            if (this.Ó.get(this.à.get(var1)) != null) {
                GlStateManager.à(OpenGlHelper.£à + var1);
                GlStateManager.µÕ();
                final Object var2 = this.Ó.get(this.à.get(var1));
                int var3 = -1;
                if (var2 instanceof Framebuffer) {
                    var3 = ((Framebuffer)var2).à;
                }
                else if (var2 instanceof ITextureObject) {
                    var3 = ((ITextureObject)var2).HorizonCode_Horizon_È();
                }
                else if (var2 instanceof Integer) {
                    var3 = (int)var2;
                }
                if (var3 != -1) {
                    GlStateManager.áŒŠÆ(var3);
                    OpenGlHelper.Ó(OpenGlHelper.HorizonCode_Horizon_È(this.á, this.à.get(var1)), var1);
                }
            }
        }
        for (final ShaderUniform var5 : this.áŒŠÆ) {
            var5.Â();
        }
    }
    
    public void Ø­áŒŠá() {
        this.Å = true;
    }
    
    public ShaderUniform HorizonCode_Horizon_È(final String p_147991_1_) {
        return this.ÂµÈ.containsKey(p_147991_1_) ? this.ÂµÈ.get(p_147991_1_) : null;
    }
    
    public ShaderUniform Â(final String p_147984_1_) {
        return this.ÂµÈ.containsKey(p_147984_1_) ? this.ÂµÈ.get(p_147984_1_) : ShaderManager.Â;
    }
    
    private void Ø() {
        for (int var1 = 0, var2 = 0; var1 < this.à.size(); ++var1, ++var2) {
            final String var3 = this.à.get(var1);
            final int var4 = OpenGlHelper.HorizonCode_Horizon_È(this.á, var3);
            if (var4 == -1) {
                ShaderManager.HorizonCode_Horizon_È.warn("Shader " + this.ˆÏ­ + "could not find sampler named " + var3 + " in the specified shader program.");
                this.Ó.remove(var3);
                this.à.remove(var2);
                --var2;
            }
            else {
                this.Ø.add(var4);
            }
        }
        for (final ShaderUniform var6 : this.áŒŠÆ) {
            final String var3 = var6.HorizonCode_Horizon_È();
            final int var4 = OpenGlHelper.HorizonCode_Horizon_È(this.á, var3);
            if (var4 == -1) {
                ShaderManager.HorizonCode_Horizon_È.warn("Could not find uniform named " + var3 + " in the specified" + " shader program.");
            }
            else {
                this.áˆºÑ¢Õ.add(var4);
                var6.HorizonCode_Horizon_È(var4);
                this.ÂµÈ.put(var3, var6);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final JsonElement p_147996_1_) {
        final JsonObject var2 = JsonUtils.Âµá€(p_147996_1_, "sampler");
        final String var3 = JsonUtils.Ó(var2, "name");
        if (!JsonUtils.HorizonCode_Horizon_È(var2, "file")) {
            this.Ó.put(var3, null);
            this.à.add(var3);
        }
        else {
            this.à.add(var3);
        }
    }
    
    public void HorizonCode_Horizon_È(final String p_147992_1_, final Object p_147992_2_) {
        if (this.Ó.containsKey(p_147992_1_)) {
            this.Ó.remove(p_147992_1_);
        }
        this.Ó.put(p_147992_1_, p_147992_2_);
        this.Ø­áŒŠá();
    }
    
    private void Â(final JsonElement p_147987_1_) throws JsonException {
        final JsonObject var2 = JsonUtils.Âµá€(p_147987_1_, "uniform");
        final String var3 = JsonUtils.Ó(var2, "name");
        final int var4 = ShaderUniform.HorizonCode_Horizon_È(JsonUtils.Ó(var2, "type"));
        final int var5 = JsonUtils.áŒŠÆ(var2, "count");
        final float[] var6 = new float[Math.max(var5, 16)];
        final JsonArray var7 = JsonUtils.ÂµÈ(var2, "values");
        if (var7.size() != var5 && var7.size() > 1) {
            throw new JsonException("Invalid amount of values specified (expected " + var5 + ", found " + var7.size() + ")");
        }
        int var8 = 0;
        for (final JsonElement var10 : var7) {
            try {
                var6[var8] = JsonUtils.Ý(var10, "value");
            }
            catch (Exception var12) {
                final JsonException var11 = JsonException.HorizonCode_Horizon_È(var12);
                var11.HorizonCode_Horizon_È("values[" + var8 + "]");
                throw var11;
            }
            ++var8;
        }
        if (var5 > 1 && var7.size() == 1) {
            while (var8 < var5) {
                var6[var8] = var6[0];
                ++var8;
            }
        }
        final int var13 = (var5 > 1 && var5 <= 4 && var4 < 8) ? (var5 - 1) : 0;
        final ShaderUniform var14 = new ShaderUniform(var3, var4 + var13, var5, this);
        if (var4 <= 3) {
            var14.HorizonCode_Horizon_È((int)var6[0], (int)var6[1], (int)var6[2], (int)var6[3]);
        }
        else if (var4 <= 7) {
            var14.Â(var6[0], var6[1], var6[2], var6[3]);
        }
        else {
            var14.HorizonCode_Horizon_È(var6);
        }
        this.áŒŠÆ.add(var14);
    }
    
    public ShaderLoader Âµá€() {
        return this.¥Æ;
    }
    
    public ShaderLoader Ó() {
        return this.Ø­à;
    }
    
    public int à() {
        return this.á;
    }
}
