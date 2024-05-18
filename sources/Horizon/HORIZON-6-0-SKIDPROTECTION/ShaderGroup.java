package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.io.FileNotFoundException;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.InputStream;
import com.google.gson.JsonElement;
import org.apache.commons.io.IOUtils;
import com.google.common.base.Charsets;
import com.google.gson.JsonParser;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import javax.vecmath.Matrix4f;
import java.util.Map;
import java.util.List;

public class ShaderGroup
{
    private Framebuffer HorizonCode_Horizon_È;
    private IResourceManager Â;
    private String Ý;
    private final List Ø­áŒŠá;
    private final Map Âµá€;
    private final List Ó;
    private Matrix4f à;
    private int Ø;
    private int áŒŠÆ;
    private float áˆºÑ¢Õ;
    private float ÂµÈ;
    private static final String á = "CL_00001041";
    
    public ShaderGroup(final TextureManager p_i1050_1_, final IResourceManager p_i1050_2_, final Framebuffer p_i1050_3_, final ResourceLocation_1975012498 p_i1050_4_) throws JsonException {
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Âµá€ = Maps.newHashMap();
        this.Ó = Lists.newArrayList();
        this.Â = p_i1050_2_;
        this.HorizonCode_Horizon_È = p_i1050_3_;
        this.áˆºÑ¢Õ = 0.0f;
        this.ÂµÈ = 0.0f;
        this.Ø = p_i1050_3_.Ý;
        this.áŒŠÆ = p_i1050_3_.Ø­áŒŠá;
        this.Ý = p_i1050_4_.toString();
        this.Ý();
        this.HorizonCode_Horizon_È(p_i1050_1_, p_i1050_4_);
    }
    
    public void HorizonCode_Horizon_È(final TextureManager p_152765_1_, final ResourceLocation_1975012498 p_152765_2_) throws JsonException {
        final JsonParser var3 = new JsonParser();
        InputStream var4 = null;
        try {
            final IResource var5 = this.Â.HorizonCode_Horizon_È(p_152765_2_);
            var4 = var5.Â();
            final JsonObject var6 = var3.parse(IOUtils.toString(var4, Charsets.UTF_8)).getAsJsonObject();
            if (JsonUtils.Ý(var6, "targets")) {
                final JsonArray var7 = var6.getAsJsonArray("targets");
                int var8 = 0;
                for (final JsonElement var10 : var7) {
                    try {
                        this.HorizonCode_Horizon_È(var10);
                    }
                    catch (Exception var12) {
                        final JsonException var11 = JsonException.HorizonCode_Horizon_È(var12);
                        var11.HorizonCode_Horizon_È("targets[" + var8 + "]");
                        throw var11;
                    }
                    ++var8;
                }
            }
            if (JsonUtils.Ý(var6, "passes")) {
                final JsonArray var7 = var6.getAsJsonArray("passes");
                int var8 = 0;
                for (final JsonElement var10 : var7) {
                    try {
                        this.HorizonCode_Horizon_È(p_152765_1_, var10);
                    }
                    catch (Exception var13) {
                        final JsonException var11 = JsonException.HorizonCode_Horizon_È(var13);
                        var11.HorizonCode_Horizon_È("passes[" + var8 + "]");
                        throw var11;
                    }
                    ++var8;
                }
            }
        }
        catch (Exception var15) {
            final JsonException var14 = JsonException.HorizonCode_Horizon_È(var15);
            var14.Â(p_152765_2_.Â());
            throw var14;
        }
        finally {
            IOUtils.closeQuietly(var4);
        }
        IOUtils.closeQuietly(var4);
    }
    
    private void HorizonCode_Horizon_È(final JsonElement p_148027_1_) throws JsonException {
        if (JsonUtils.HorizonCode_Horizon_È(p_148027_1_)) {
            this.HorizonCode_Horizon_È(p_148027_1_.getAsString(), this.Ø, this.áŒŠÆ);
        }
        else {
            final JsonObject var2 = JsonUtils.Âµá€(p_148027_1_, "target");
            final String var3 = JsonUtils.Ó(var2, "name");
            final int var4 = JsonUtils.HorizonCode_Horizon_È(var2, "width", this.Ø);
            final int var5 = JsonUtils.HorizonCode_Horizon_È(var2, "height", this.áŒŠÆ);
            if (this.Âµá€.containsKey(var3)) {
                throw new JsonException(String.valueOf(var3) + " is already defined");
            }
            this.HorizonCode_Horizon_È(var3, var4, var5);
        }
    }
    
    private void HorizonCode_Horizon_È(final TextureManager p_152764_1_, final JsonElement p_152764_2_) throws JsonException {
        final JsonObject var3 = JsonUtils.Âµá€(p_152764_2_, "pass");
        final String var4 = JsonUtils.Ó(var3, "name");
        final String var5 = JsonUtils.Ó(var3, "intarget");
        final String var6 = JsonUtils.Ó(var3, "outtarget");
        final Framebuffer var7 = this.Â(var5);
        final Framebuffer var8 = this.Â(var6);
        if (var7 == null) {
            throw new JsonException("Input target '" + var5 + "' does not exist");
        }
        if (var8 == null) {
            throw new JsonException("Output target '" + var6 + "' does not exist");
        }
        final Shader var9 = this.HorizonCode_Horizon_È(var4, var7, var8);
        final JsonArray var10 = JsonUtils.HorizonCode_Horizon_È(var3, "auxtargets", (JsonArray)null);
        if (var10 != null) {
            int var11 = 0;
            for (final JsonElement var13 : var10) {
                try {
                    final JsonObject var14 = JsonUtils.Âµá€(var13, "auxtarget");
                    final String var15 = JsonUtils.Ó(var14, "name");
                    final String var16 = JsonUtils.Ó(var14, "id");
                    final Framebuffer var17 = this.Â(var16);
                    if (var17 == null) {
                        final ResourceLocation_1975012498 var18 = new ResourceLocation_1975012498("textures/effect/" + var16 + ".png");
                        try {
                            this.Â.HorizonCode_Horizon_È(var18);
                        }
                        catch (FileNotFoundException var31) {
                            throw new JsonException("Render target or texture '" + var16 + "' does not exist");
                        }
                        p_152764_1_.HorizonCode_Horizon_È(var18);
                        final ITextureObject var19 = p_152764_1_.Â(var18);
                        final int var20 = JsonUtils.áŒŠÆ(var14, "width");
                        final int var21 = JsonUtils.áŒŠÆ(var14, "height");
                        final boolean var22 = JsonUtils.à(var14, "bilinear");
                        if (var22) {
                            GL11.glTexParameteri(3553, 10241, 9729);
                            GL11.glTexParameteri(3553, 10240, 9729);
                        }
                        else {
                            GL11.glTexParameteri(3553, 10241, 9728);
                            GL11.glTexParameteri(3553, 10240, 9728);
                        }
                        var9.HorizonCode_Horizon_È(var15, var19.HorizonCode_Horizon_È(), var20, var21);
                    }
                    else {
                        var9.HorizonCode_Horizon_È(var15, var17, var17.HorizonCode_Horizon_È, var17.Â);
                    }
                }
                catch (Exception var24) {
                    final JsonException var23 = JsonException.HorizonCode_Horizon_È(var24);
                    var23.HorizonCode_Horizon_È("auxtargets[" + var11 + "]");
                    throw var23;
                }
                ++var11;
            }
        }
        final JsonArray var25 = JsonUtils.HorizonCode_Horizon_È(var3, "uniforms", (JsonArray)null);
        if (var25 != null) {
            int var26 = 0;
            for (final JsonElement var28 : var25) {
                try {
                    this.Â(var28);
                }
                catch (Exception var30) {
                    final JsonException var29 = JsonException.HorizonCode_Horizon_È(var30);
                    var29.HorizonCode_Horizon_È("uniforms[" + var26 + "]");
                    throw var29;
                }
                ++var26;
            }
        }
    }
    
    private void Â(final JsonElement p_148028_1_) throws JsonException {
        final JsonObject var2 = JsonUtils.Âµá€(p_148028_1_, "uniform");
        final String var3 = JsonUtils.Ó(var2, "name");
        final ShaderUniform var4 = this.Ø­áŒŠá.get(this.Ø­áŒŠá.size() - 1).Â().HorizonCode_Horizon_È(var3);
        if (var4 == null) {
            throw new JsonException("Uniform '" + var3 + "' does not exist");
        }
        final float[] var5 = new float[4];
        int var6 = 0;
        final JsonArray var7 = JsonUtils.ÂµÈ(var2, "values");
        for (final JsonElement var9 : var7) {
            try {
                var5[var6] = JsonUtils.Ý(var9, "value");
            }
            catch (Exception var11) {
                final JsonException var10 = JsonException.HorizonCode_Horizon_È(var11);
                var10.HorizonCode_Horizon_È("values[" + var6 + "]");
                throw var10;
            }
            ++var6;
        }
        switch (var6) {
            case 1: {
                var4.HorizonCode_Horizon_È(var5[0]);
                break;
            }
            case 2: {
                var4.HorizonCode_Horizon_È(var5[0], var5[1]);
                break;
            }
            case 3: {
                var4.HorizonCode_Horizon_È(var5[0], var5[1], var5[2]);
                break;
            }
            case 4: {
                var4.HorizonCode_Horizon_È(var5[0], var5[1], var5[2], var5[3]);
                break;
            }
        }
    }
    
    public Framebuffer HorizonCode_Horizon_È(final String p_177066_1_) {
        return this.Âµá€.get(p_177066_1_);
    }
    
    public void HorizonCode_Horizon_È(final String p_148020_1_, final int p_148020_2_, final int p_148020_3_) {
        final Framebuffer var4 = new Framebuffer(p_148020_2_, p_148020_3_, true);
        var4.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.0f);
        this.Âµá€.put(p_148020_1_, var4);
        if (p_148020_2_ == this.Ø && p_148020_3_ == this.áŒŠÆ) {
            this.Ó.add(var4);
        }
    }
    
    public void HorizonCode_Horizon_È() {
        for (final Framebuffer var2 : this.Âµá€.values()) {
            var2.HorizonCode_Horizon_È();
        }
        for (final Shader var3 : this.Ø­áŒŠá) {
            var3.HorizonCode_Horizon_È();
        }
        this.Ø­áŒŠá.clear();
    }
    
    public Shader HorizonCode_Horizon_È(final String p_148023_1_, final Framebuffer p_148023_2_, final Framebuffer p_148023_3_) throws JsonException {
        final Shader var4 = new Shader(this.Â, p_148023_1_, p_148023_2_, p_148023_3_);
        this.Ø­áŒŠá.add(this.Ø­áŒŠá.size(), var4);
        return var4;
    }
    
    private void Ý() {
        (this.à = new Matrix4f()).setIdentity();
        this.à.m00 = 2.0f / this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.à.m11 = 2.0f / -this.HorizonCode_Horizon_È.Â;
        this.à.m22 = -0.0020001999f;
        this.à.m33 = 1.0f;
        this.à.m03 = -1.0f;
        this.à.m13 = 1.0f;
        this.à.m23 = -1.0001999f;
    }
    
    public void HorizonCode_Horizon_È(final int p_148026_1_, final int p_148026_2_) {
        this.Ø = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.áŒŠÆ = this.HorizonCode_Horizon_È.Â;
        this.Ý();
        for (final Shader var4 : this.Ø­áŒŠá) {
            var4.HorizonCode_Horizon_È(this.à);
        }
        for (final Framebuffer var5 : this.Ó) {
            var5.HorizonCode_Horizon_È(p_148026_1_, p_148026_2_);
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_148018_1_) {
        if (p_148018_1_ < this.ÂµÈ) {
            this.áˆºÑ¢Õ += 1.0f - this.ÂµÈ;
            this.áˆºÑ¢Õ += p_148018_1_;
        }
        else {
            this.áˆºÑ¢Õ += p_148018_1_ - this.ÂµÈ;
        }
        this.ÂµÈ = p_148018_1_;
        while (this.áˆºÑ¢Õ > 20.0f) {
            this.áˆºÑ¢Õ -= 20.0f;
        }
        for (final Shader var3 : this.Ø­áŒŠá) {
            var3.HorizonCode_Horizon_È(this.áˆºÑ¢Õ / 20.0f);
        }
    }
    
    public final String Â() {
        return this.Ý;
    }
    
    private Framebuffer Â(final String p_148017_1_) {
        return (p_148017_1_ == null) ? null : (p_148017_1_.equals("minecraft:main") ? this.HorizonCode_Horizon_È : this.Âµá€.get(p_148017_1_));
    }
}
