/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.PlatformDescriptors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GPUWarning
extends ReloadListener<GPUInfo> {
    private static final Logger field_241686_a_ = LogManager.getLogger();
    private static final ResourceLocation field_241687_b_ = new ResourceLocation("gpu_warnlist.json");
    private ImmutableMap<String, String> field_241688_c_ = ImmutableMap.of();
    private boolean field_241689_d_;
    private boolean field_241690_e_;
    private boolean field_241691_f_;

    public boolean func_241692_a_() {
        return !this.field_241688_c_.isEmpty();
    }

    public boolean func_241695_b_() {
        return this.func_241692_a_() && !this.field_241690_e_;
    }

    public void func_241697_d_() {
        this.field_241689_d_ = true;
    }

    public void func_241698_e_() {
        this.field_241690_e_ = true;
    }

    public void func_241699_f_() {
        this.field_241690_e_ = true;
        this.field_241691_f_ = true;
    }

    public boolean func_241700_g_() {
        return this.field_241689_d_ && !this.field_241690_e_;
    }

    public boolean func_241701_h_() {
        return this.field_241691_f_;
    }

    public void func_241702_i_() {
        this.field_241689_d_ = false;
        this.field_241690_e_ = false;
        this.field_241691_f_ = false;
    }

    @Nullable
    public String func_241703_j_() {
        return this.field_241688_c_.get("renderer");
    }

    @Nullable
    public String func_241704_k_() {
        return this.field_241688_c_.get("version");
    }

    @Nullable
    public String func_241705_l_() {
        return this.field_241688_c_.get("vendor");
    }

    @Nullable
    public String func_243499_m() {
        StringBuilder stringBuilder = new StringBuilder();
        this.field_241688_c_.forEach((arg_0, arg_1) -> GPUWarning.lambda$func_243499_m$0(stringBuilder, arg_0, arg_1));
        return stringBuilder.length() == 0 ? null : stringBuilder.toString();
    }

    @Override
    protected GPUInfo prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        ArrayList<Pattern> arrayList = Lists.newArrayList();
        ArrayList<Pattern> arrayList2 = Lists.newArrayList();
        ArrayList<Pattern> arrayList3 = Lists.newArrayList();
        iProfiler.startTick();
        JsonObject jsonObject = GPUWarning.func_241696_c_(iResourceManager, iProfiler);
        if (jsonObject != null) {
            iProfiler.startSection("compile_regex");
            GPUWarning.func_241693_a_(jsonObject.getAsJsonArray("renderer"), arrayList);
            GPUWarning.func_241693_a_(jsonObject.getAsJsonArray("version"), arrayList2);
            GPUWarning.func_241693_a_(jsonObject.getAsJsonArray("vendor"), arrayList3);
            iProfiler.endSection();
        }
        iProfiler.endTick();
        return new GPUInfo(arrayList, arrayList2, arrayList3);
    }

    @Override
    protected void apply(GPUInfo gPUInfo, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.field_241688_c_ = gPUInfo.func_241709_a_();
    }

    private static void func_241693_a_(JsonArray jsonArray, List<Pattern> list) {
        jsonArray.forEach(arg_0 -> GPUWarning.lambda$func_241693_a_$1(list, arg_0));
    }

    @Nullable
    private static JsonObject func_241696_c_(IResourceManager iResourceManager, IProfiler iProfiler) {
        iProfiler.startSection("parse_json");
        JsonObject jsonObject = null;
        try (IResource iResource = iResourceManager.getResource(field_241687_b_);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8));){
            jsonObject = new JsonParser().parse(bufferedReader).getAsJsonObject();
        } catch (JsonSyntaxException | IOException exception) {
            field_241686_a_.warn("Failed to load GPU warnlist");
        }
        iProfiler.endSection();
        return jsonObject;
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((GPUInfo)object, iResourceManager, iProfiler);
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }

    private static void lambda$func_241693_a_$1(List list, JsonElement jsonElement) {
        list.add(Pattern.compile(jsonElement.getAsString(), 2));
    }

    private static void lambda$func_243499_m$0(StringBuilder stringBuilder, String string, String string2) {
        stringBuilder.append(string).append(": ").append(string2);
    }

    public static final class GPUInfo {
        private final List<Pattern> field_241706_a_;
        private final List<Pattern> field_241707_b_;
        private final List<Pattern> field_241708_c_;

        private GPUInfo(List<Pattern> list, List<Pattern> list2, List<Pattern> list3) {
            this.field_241706_a_ = list;
            this.field_241707_b_ = list2;
            this.field_241708_c_ = list3;
        }

        private static String func_241711_a_(List<Pattern> list, String string) {
            ArrayList<String> arrayList = Lists.newArrayList();
            for (Pattern pattern : list) {
                Matcher matcher = pattern.matcher(string);
                while (matcher.find()) {
                    arrayList.add(matcher.group());
                }
            }
            return String.join((CharSequence)", ", arrayList);
        }

        private ImmutableMap<String, String> func_241709_a_() {
            String string;
            String string2;
            ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<String, String>();
            String string3 = GPUInfo.func_241711_a_(this.field_241706_a_, PlatformDescriptors.getGlRenderer());
            if (!string3.isEmpty()) {
                builder.put("renderer", string3);
            }
            if (!(string2 = GPUInfo.func_241711_a_(this.field_241707_b_, PlatformDescriptors.getGlVersion())).isEmpty()) {
                builder.put("version", string2);
            }
            if (!(string = GPUInfo.func_241711_a_(this.field_241708_c_, PlatformDescriptors.getGlVendor())).isEmpty()) {
                builder.put("vendor", string);
            }
            return builder.build();
        }
    }
}

