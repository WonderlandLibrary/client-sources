/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingDebugRenderer;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.RandomObjectDescriptor;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PointOfInterestDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft client;
    private final Map<BlockPos, POIInfo> field_217713_c = Maps.newHashMap();
    private final Map<UUID, BrainInfo> field_239313_d_ = Maps.newHashMap();
    @Nullable
    private UUID field_217716_f;

    public PointOfInterestDebugRenderer(Minecraft minecraft) {
        this.client = minecraft;
    }

    @Override
    public void clear() {
        this.field_217713_c.clear();
        this.field_239313_d_.clear();
        this.field_217716_f = null;
    }

    public void func_217691_a(POIInfo pOIInfo) {
        this.field_217713_c.put(pOIInfo.field_217755_a, pOIInfo);
    }

    public void func_217698_a(BlockPos blockPos) {
        this.field_217713_c.remove(blockPos);
    }

    public void func_217706_a(BlockPos blockPos, int n) {
        POIInfo pOIInfo = this.field_217713_c.get(blockPos);
        if (pOIInfo == null) {
            LOGGER.warn("Strange, setFreeTicketCount was called for an unknown POI: " + blockPos);
        } else {
            pOIInfo.field_217757_c = n;
        }
    }

    public void func_217692_a(BrainInfo brainInfo) {
        this.field_239313_d_.put(brainInfo.field_217747_a, brainInfo);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        this.func_239331_b_();
        this.func_229035_a_(d, d2, d3);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        if (!this.client.player.isSpectator()) {
            this.func_217710_d();
        }
    }

    private void func_239331_b_() {
        this.field_239313_d_.entrySet().removeIf(this::lambda$func_239331_b_$0);
    }

    private void func_229035_a_(double d, double d2, double d3) {
        BlockPos blockPos = new BlockPos(d, d2, d3);
        this.field_239313_d_.values().forEach(arg_0 -> this.lambda$func_229035_a_$1(d, d2, d3, arg_0));
        for (BlockPos blockPos2 : this.field_217713_c.keySet()) {
            if (!blockPos.withinDistance(blockPos2, 30.0)) continue;
            PointOfInterestDebugRenderer.func_217699_b(blockPos2);
        }
        this.field_217713_c.values().forEach(arg_0 -> this.lambda$func_229035_a_$2(blockPos, arg_0));
        this.func_222915_d().forEach((arg_0, arg_1) -> this.lambda$func_229035_a_$3(blockPos, arg_0, arg_1));
    }

    private static void func_217699_b(BlockPos blockPos) {
        float f = 0.05f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        DebugRenderer.renderBox(blockPos, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
    }

    private void func_222921_a(BlockPos blockPos, List<String> list) {
        float f = 0.05f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        DebugRenderer.renderBox(blockPos, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
        PointOfInterestDebugRenderer.func_222923_a("" + list, blockPos, 0, -256);
        PointOfInterestDebugRenderer.func_222923_a("Ghost POI", blockPos, 1, -65536);
    }

    private void func_217705_b(POIInfo pOIInfo) {
        int n = 0;
        Set<String> set = this.func_217696_c(pOIInfo);
        if (set.size() < 4) {
            PointOfInterestDebugRenderer.func_217695_a("Owners: " + set, pOIInfo, n, -256);
        } else {
            PointOfInterestDebugRenderer.func_217695_a(set.size() + " ticket holders", pOIInfo, n, -256);
        }
        ++n;
        Set<String> set2 = this.func_239342_d_(pOIInfo);
        if (set2.size() < 4) {
            PointOfInterestDebugRenderer.func_217695_a("Candidates: " + set2, pOIInfo, n, -23296);
        } else {
            PointOfInterestDebugRenderer.func_217695_a(set2.size() + " potential owners", pOIInfo, n, -23296);
        }
        PointOfInterestDebugRenderer.func_217695_a("Free tickets: " + pOIInfo.field_217757_c, pOIInfo, ++n, -256);
        PointOfInterestDebugRenderer.func_217695_a(pOIInfo.field_217756_b, pOIInfo, ++n, -1);
    }

    private void func_229037_a_(BrainInfo brainInfo, double d, double d2, double d3) {
        if (brainInfo.field_222930_g != null) {
            PathfindingDebugRenderer.func_229032_a_(brainInfo.field_222930_g, 0.5f, false, false, d, d2, d3);
        }
    }

    private void func_229038_b_(BrainInfo brainInfo, double d, double d2, double d3) {
        boolean bl = this.func_217703_c(brainInfo);
        int n = 0;
        PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, brainInfo.field_217749_c, -1, 0.03f);
        ++n;
        if (bl) {
            PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, brainInfo.field_222928_d + " " + brainInfo.field_222929_e + " xp", -1, 0.02f);
            ++n;
        }
        if (bl) {
            int n2 = brainInfo.field_239349_f_ < brainInfo.field_239350_g_ ? -23296 : -1;
            PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, "health: " + String.format("%.1f", Float.valueOf(brainInfo.field_239349_f_)) + " / " + String.format("%.1f", Float.valueOf(brainInfo.field_239350_g_)), n2, 0.02f);
            ++n;
        }
        if (bl && !brainInfo.field_223455_g.equals("")) {
            PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, brainInfo.field_223455_g, -98404, 0.02f);
            ++n;
        }
        if (bl) {
            for (String string : brainInfo.field_217752_f) {
                PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, string, -16711681, 0.02f);
                ++n;
            }
        }
        if (bl) {
            for (String string : brainInfo.field_217751_e) {
                PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, string, -16711936, 0.02f);
                ++n;
            }
        }
        if (brainInfo.field_223456_i) {
            PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, "Wants Golem", -23296, 0.02f);
            ++n;
        }
        if (bl) {
            for (String string : brainInfo.field_223457_m) {
                if (string.startsWith(brainInfo.field_217749_c)) {
                    PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, string, -1, 0.02f);
                } else {
                    PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, string, -23296, 0.02f);
                }
                ++n;
            }
        }
        if (bl) {
            for (String string : Lists.reverse(brainInfo.field_217753_g)) {
                PointOfInterestDebugRenderer.func_217693_a(brainInfo.field_217750_d, n, string, -3355444, 0.02f);
                ++n;
            }
        }
        if (bl) {
            this.func_229037_a_(brainInfo, d, d2, d3);
        }
    }

    private static void func_217695_a(String string, POIInfo pOIInfo, int n, int n2) {
        BlockPos blockPos = pOIInfo.field_217755_a;
        PointOfInterestDebugRenderer.func_222923_a(string, blockPos, n, n2);
    }

    private static void func_222923_a(String string, BlockPos blockPos, int n, int n2) {
        double d = 1.3;
        double d2 = 0.2;
        double d3 = (double)blockPos.getX() + 0.5;
        double d4 = (double)blockPos.getY() + 1.3 + (double)n * 0.2;
        double d5 = (double)blockPos.getZ() + 0.5;
        DebugRenderer.renderText(string, d3, d4, d5, n2, 0.02f, true, 0.0f, true);
    }

    private static void func_217693_a(IPosition iPosition, int n, String string, int n2, float f) {
        double d = 2.4;
        double d2 = 0.25;
        BlockPos blockPos = new BlockPos(iPosition);
        double d3 = (double)blockPos.getX() + 0.5;
        double d4 = iPosition.getY() + 2.4 + (double)n * 0.25;
        double d5 = (double)blockPos.getZ() + 0.5;
        float f2 = 0.5f;
        DebugRenderer.renderText(string, d3, d4, d5, n2, f, false, 0.5f, true);
    }

    private Set<String> func_217696_c(POIInfo pOIInfo) {
        return this.func_239340_c_(pOIInfo.field_217755_a).stream().map(RandomObjectDescriptor::getRandomObjectDescriptor).collect(Collectors.toSet());
    }

    private Set<String> func_239342_d_(POIInfo pOIInfo) {
        return this.func_239343_d_(pOIInfo.field_217755_a).stream().map(RandomObjectDescriptor::getRandomObjectDescriptor).collect(Collectors.toSet());
    }

    private boolean func_217703_c(BrainInfo brainInfo) {
        return Objects.equals(this.field_217716_f, brainInfo.field_217747_a);
    }

    private boolean func_217694_d(BrainInfo brainInfo) {
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        BlockPos blockPos = new BlockPos(clientPlayerEntity.getPosX(), brainInfo.field_217750_d.getY(), clientPlayerEntity.getPosZ());
        BlockPos blockPos2 = new BlockPos(brainInfo.field_217750_d);
        return blockPos.withinDistance(blockPos2, 30.0);
    }

    private Collection<UUID> func_239340_c_(BlockPos blockPos) {
        return this.field_239313_d_.values().stream().filter(arg_0 -> PointOfInterestDebugRenderer.lambda$func_239340_c_$4(blockPos, arg_0)).map(BrainInfo::func_217746_a).collect(Collectors.toSet());
    }

    private Collection<UUID> func_239343_d_(BlockPos blockPos) {
        return this.field_239313_d_.values().stream().filter(arg_0 -> PointOfInterestDebugRenderer.lambda$func_239343_d_$5(blockPos, arg_0)).map(BrainInfo::func_217746_a).collect(Collectors.toSet());
    }

    private Map<BlockPos, List<String>> func_222915_d() {
        HashMap<BlockPos, List<String>> hashMap = Maps.newHashMap();
        for (BrainInfo brainInfo : this.field_239313_d_.values()) {
            for (BlockPos blockPos : Iterables.concat(brainInfo.field_217754_h, brainInfo.field_239360_q_)) {
                if (this.field_217713_c.containsKey(blockPos)) continue;
                hashMap.computeIfAbsent(blockPos, PointOfInterestDebugRenderer::lambda$func_222915_d$6).add(brainInfo.field_217749_c);
            }
        }
        return hashMap;
    }

    private void func_217710_d() {
        DebugRenderer.getTargetEntity(this.client.getRenderViewEntity(), 8).ifPresent(this::lambda$func_217710_d$7);
    }

    private void lambda$func_217710_d$7(Entity entity2) {
        this.field_217716_f = entity2.getUniqueID();
    }

    private static List lambda$func_222915_d$6(BlockPos blockPos) {
        return Lists.newArrayList();
    }

    private static boolean lambda$func_239343_d_$5(BlockPos blockPos, BrainInfo brainInfo) {
        return brainInfo.func_239365_b_(blockPos);
    }

    private static boolean lambda$func_239340_c_$4(BlockPos blockPos, BrainInfo brainInfo) {
        return brainInfo.func_217744_a(blockPos);
    }

    private void lambda$func_229035_a_$3(BlockPos blockPos, BlockPos blockPos2, List list) {
        if (blockPos.withinDistance(blockPos2, 30.0)) {
            this.func_222921_a(blockPos2, list);
        }
    }

    private void lambda$func_229035_a_$2(BlockPos blockPos, POIInfo pOIInfo) {
        if (blockPos.withinDistance(pOIInfo.field_217755_a, 30.0)) {
            this.func_217705_b(pOIInfo);
        }
    }

    private void lambda$func_229035_a_$1(double d, double d2, double d3, BrainInfo brainInfo) {
        if (this.func_217694_d(brainInfo)) {
            this.func_229038_b_(brainInfo, d, d2, d3);
        }
    }

    private boolean lambda$func_239331_b_$0(Map.Entry entry) {
        Entity entity2 = this.client.world.getEntityByID(((BrainInfo)entry.getValue()).field_217748_b);
        return entity2 == null || entity2.removed;
    }

    public static class POIInfo {
        public final BlockPos field_217755_a;
        public String field_217756_b;
        public int field_217757_c;

        public POIInfo(BlockPos blockPos, String string, int n) {
            this.field_217755_a = blockPos;
            this.field_217756_b = string;
            this.field_217757_c = n;
        }
    }

    public static class BrainInfo {
        public final UUID field_217747_a;
        public final int field_217748_b;
        public final String field_217749_c;
        public final String field_222928_d;
        public final int field_222929_e;
        public final float field_239349_f_;
        public final float field_239350_g_;
        public final IPosition field_217750_d;
        public final String field_223455_g;
        public final Path field_222930_g;
        public final boolean field_223456_i;
        public final List<String> field_217751_e = Lists.newArrayList();
        public final List<String> field_217752_f = Lists.newArrayList();
        public final List<String> field_217753_g = Lists.newArrayList();
        public final List<String> field_223457_m = Lists.newArrayList();
        public final Set<BlockPos> field_217754_h = Sets.newHashSet();
        public final Set<BlockPos> field_239360_q_ = Sets.newHashSet();

        public BrainInfo(UUID uUID, int n, String string, String string2, int n2, float f, float f2, IPosition iPosition, String string3, @Nullable Path path, boolean bl) {
            this.field_217747_a = uUID;
            this.field_217748_b = n;
            this.field_217749_c = string;
            this.field_222928_d = string2;
            this.field_222929_e = n2;
            this.field_239349_f_ = f;
            this.field_239350_g_ = f2;
            this.field_217750_d = iPosition;
            this.field_223455_g = string3;
            this.field_222930_g = path;
            this.field_223456_i = bl;
        }

        private boolean func_217744_a(BlockPos blockPos) {
            return this.field_217754_h.stream().anyMatch(blockPos::equals);
        }

        private boolean func_239365_b_(BlockPos blockPos) {
            return this.field_239360_q_.contains(blockPos);
        }

        public UUID func_217746_a() {
            return this.field_217747_a;
        }
    }
}

