/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

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
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingDebugRenderer;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.RandomObjectDescriptor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BeeDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft field_228958_a_;
    private final Map<BlockPos, Hive> field_228959_b_ = Maps.newHashMap();
    private final Map<UUID, Bee> field_228960_c_ = Maps.newHashMap();
    private UUID field_228961_d_;

    public BeeDebugRenderer(Minecraft minecraft) {
        this.field_228958_a_ = minecraft;
    }

    @Override
    public void clear() {
        this.field_228959_b_.clear();
        this.field_228960_c_.clear();
        this.field_228961_d_ = null;
    }

    public void func_228966_a_(Hive hive) {
        this.field_228959_b_.put(hive.field_229011_a_, hive);
    }

    public void func_228964_a_(Bee bee) {
        this.field_228960_c_.put(bee.field_228998_a_, bee);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        this.func_228987_c_();
        this.func_228981_b_();
        this.func_228989_d_();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
        if (!this.field_228958_a_.player.isSpectator()) {
            this.func_228997_i_();
        }
    }

    private void func_228981_b_() {
        this.field_228960_c_.entrySet().removeIf(this::lambda$func_228981_b_$0);
    }

    private void func_228987_c_() {
        long l = this.field_228958_a_.world.getGameTime() - 20L;
        this.field_228959_b_.entrySet().removeIf(arg_0 -> BeeDebugRenderer.lambda$func_228987_c_$1(l, arg_0));
    }

    private void func_228989_d_() {
        BlockPos blockPos = this.func_228995_g_().getBlockPos();
        this.field_228960_c_.values().forEach(this::lambda$func_228989_d_$2);
        this.func_228993_f_();
        for (BlockPos blockPos2 : this.field_228959_b_.keySet()) {
            if (!blockPos.withinDistance(blockPos2, 30.0)) continue;
            BeeDebugRenderer.func_228968_a_(blockPos2);
        }
        Map<BlockPos, Set<UUID>> map = this.func_228991_e_();
        this.field_228959_b_.values().forEach(arg_0 -> this.lambda$func_228989_d_$3(blockPos, map, arg_0));
        this.func_228996_h_().forEach((arg_0, arg_1) -> this.lambda$func_228989_d_$4(blockPos, arg_0, arg_1));
    }

    private Map<BlockPos, Set<UUID>> func_228991_e_() {
        HashMap<BlockPos, Set<UUID>> hashMap = Maps.newHashMap();
        this.field_228960_c_.values().forEach(arg_0 -> BeeDebugRenderer.lambda$func_228991_e_$7(hashMap, arg_0));
        return hashMap;
    }

    private void func_228993_f_() {
        HashMap hashMap = Maps.newHashMap();
        this.field_228960_c_.values().stream().filter(Bee::func_229010_c_).forEach(arg_0 -> BeeDebugRenderer.lambda$func_228993_f_$9(hashMap, arg_0));
        hashMap.entrySet().forEach(BeeDebugRenderer::lambda$func_228993_f_$10);
    }

    private static String func_228977_a_(Collection<UUID> collection) {
        if (collection.isEmpty()) {
            return "-";
        }
        return collection.size() > 3 ? collection.size() + " bees" : collection.stream().map(RandomObjectDescriptor::getRandomObjectDescriptor).collect(Collectors.toSet()).toString();
    }

    private static void func_228968_a_(BlockPos blockPos) {
        float f = 0.05f;
        BeeDebugRenderer.func_228969_a_(blockPos, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
    }

    private void func_228972_a_(BlockPos blockPos, List<String> list) {
        float f = 0.05f;
        BeeDebugRenderer.func_228969_a_(blockPos, 0.05f, 0.2f, 0.2f, 1.0f, 0.3f);
        BeeDebugRenderer.func_228976_a_("" + list, blockPos, 0, -256);
        BeeDebugRenderer.func_228976_a_("Ghost Hive", blockPos, 1, -65536);
    }

    private static void func_228969_a_(BlockPos blockPos, float f, float f2, float f3, float f4, float f5) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        DebugRenderer.renderBox(blockPos, f, f2, f3, f4, f5);
    }

    private void func_228967_a_(Hive hive, Collection<UUID> collection) {
        int n = 0;
        if (!collection.isEmpty()) {
            BeeDebugRenderer.func_228975_a_("Blacklisted by " + BeeDebugRenderer.func_228977_a_(collection), hive, n++, -65536);
        }
        BeeDebugRenderer.func_228975_a_("Out: " + BeeDebugRenderer.func_228977_a_(this.func_228983_b_(hive.field_229011_a_)), hive, n++, -3355444);
        if (hive.field_229013_c_ == 0) {
            BeeDebugRenderer.func_228975_a_("In: -", hive, n++, -256);
        } else if (hive.field_229013_c_ == 1) {
            BeeDebugRenderer.func_228975_a_("In: 1 bee", hive, n++, -256);
        } else {
            BeeDebugRenderer.func_228975_a_("In: " + hive.field_229013_c_ + " bees", hive, n++, -256);
        }
        BeeDebugRenderer.func_228975_a_("Honey: " + hive.field_229014_d_, hive, n++, -23296);
        BeeDebugRenderer.func_228975_a_(hive.field_229012_b_ + (hive.field_229015_e_ ? " (sedated)" : ""), hive, n++, -1);
    }

    private void func_228982_b_(Bee bee) {
        if (bee.field_229001_d_ != null) {
            PathfindingDebugRenderer.func_229032_a_(bee.field_229001_d_, 0.5f, false, false, this.func_228995_g_().getProjectedView().getX(), this.func_228995_g_().getProjectedView().getY(), this.func_228995_g_().getProjectedView().getZ());
        }
    }

    private void func_228988_c_(Bee bee) {
        boolean bl = this.func_228990_d_(bee);
        int n = 0;
        BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, bee.toString(), -1, 0.03f);
        if (bee.field_229002_e_ == null) {
            BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, "No hive", -98404, 0.02f);
        } else {
            BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, "Hive: " + this.func_228965_a_(bee, bee.field_229002_e_), -256, 0.02f);
        }
        if (bee.field_229003_f_ == null) {
            BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, "No flower", -98404, 0.02f);
        } else {
            BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, "Flower: " + this.func_228965_a_(bee, bee.field_229003_f_), -256, 0.02f);
        }
        for (String string : bee.field_229005_h_) {
            BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, string, -16711936, 0.02f);
        }
        if (bl) {
            this.func_228982_b_(bee);
        }
        if (bee.field_229004_g_ > 0) {
            int n2 = bee.field_229004_g_ < 600 ? -3355444 : -23296;
            BeeDebugRenderer.func_228974_a_(bee.field_229000_c_, n++, "Travelling: " + bee.field_229004_g_ + " ticks", n2, 0.02f);
        }
    }

    private static void func_228975_a_(String string, Hive hive, int n, int n2) {
        BlockPos blockPos = hive.field_229011_a_;
        BeeDebugRenderer.func_228976_a_(string, blockPos, n, n2);
    }

    private static void func_228976_a_(String string, BlockPos blockPos, int n, int n2) {
        double d = 1.3;
        double d2 = 0.2;
        double d3 = (double)blockPos.getX() + 0.5;
        double d4 = (double)blockPos.getY() + 1.3 + (double)n * 0.2;
        double d5 = (double)blockPos.getZ() + 0.5;
        DebugRenderer.renderText(string, d3, d4, d5, n2, 0.02f, true, 0.0f, true);
    }

    private static void func_228974_a_(IPosition iPosition, int n, String string, int n2, float f) {
        double d = 2.4;
        double d2 = 0.25;
        BlockPos blockPos = new BlockPos(iPosition);
        double d3 = (double)blockPos.getX() + 0.5;
        double d4 = iPosition.getY() + 2.4 + (double)n * 0.25;
        double d5 = (double)blockPos.getZ() + 0.5;
        float f2 = 0.5f;
        DebugRenderer.renderText(string, d3, d4, d5, n2, f, false, 0.5f, true);
    }

    private ActiveRenderInfo func_228995_g_() {
        return this.field_228958_a_.gameRenderer.getActiveRenderInfo();
    }

    private String func_228965_a_(Bee bee, BlockPos blockPos) {
        float f = MathHelper.sqrt(blockPos.distanceSq(bee.field_229000_c_.getX(), bee.field_229000_c_.getY(), bee.field_229000_c_.getZ(), false));
        double d = (double)Math.round(f * 10.0f) / 10.0;
        return blockPos.getCoordinatesAsString() + " (dist " + d + ")";
    }

    private boolean func_228990_d_(Bee bee) {
        return Objects.equals(this.field_228961_d_, bee.field_228998_a_);
    }

    private boolean func_228992_e_(Bee bee) {
        ClientPlayerEntity clientPlayerEntity = this.field_228958_a_.player;
        BlockPos blockPos = new BlockPos(clientPlayerEntity.getPosX(), bee.field_229000_c_.getY(), clientPlayerEntity.getPosZ());
        BlockPos blockPos2 = new BlockPos(bee.field_229000_c_);
        return blockPos.withinDistance(blockPos2, 30.0);
    }

    private Collection<UUID> func_228983_b_(BlockPos blockPos) {
        return this.field_228960_c_.values().stream().filter(arg_0 -> BeeDebugRenderer.lambda$func_228983_b_$11(blockPos, arg_0)).map(Bee::func_229007_a_).collect(Collectors.toSet());
    }

    private Map<BlockPos, List<String>> func_228996_h_() {
        HashMap<BlockPos, List<String>> hashMap = Maps.newHashMap();
        for (Bee bee : this.field_228960_c_.values()) {
            if (bee.field_229002_e_ == null || this.field_228959_b_.containsKey(bee.field_229002_e_)) continue;
            hashMap.computeIfAbsent(bee.field_229002_e_, BeeDebugRenderer::lambda$func_228996_h_$12).add(bee.func_229009_b_());
        }
        return hashMap;
    }

    private void func_228997_i_() {
        DebugRenderer.getTargetEntity(this.field_228958_a_.getRenderViewEntity(), 8).ifPresent(this::lambda$func_228997_i_$13);
    }

    private void lambda$func_228997_i_$13(Entity entity2) {
        this.field_228961_d_ = entity2.getUniqueID();
    }

    private static List lambda$func_228996_h_$12(BlockPos blockPos) {
        return Lists.newArrayList();
    }

    private static boolean lambda$func_228983_b_$11(BlockPos blockPos, Bee bee) {
        return bee.func_229008_a_(blockPos);
    }

    private static void lambda$func_228993_f_$10(Map.Entry entry) {
        BlockPos blockPos = (BlockPos)entry.getKey();
        Set set = (Set)entry.getValue();
        Set set2 = set.stream().map(RandomObjectDescriptor::getRandomObjectDescriptor).collect(Collectors.toSet());
        int n = 1;
        BeeDebugRenderer.func_228976_a_(set2.toString(), blockPos, n++, -256);
        BeeDebugRenderer.func_228976_a_("Flower", blockPos, n++, -1);
        float f = 0.05f;
        BeeDebugRenderer.func_228969_a_(blockPos, 0.05f, 0.8f, 0.8f, 0.0f, 0.3f);
    }

    private static void lambda$func_228993_f_$9(Map map, Bee bee) {
        map.computeIfAbsent(bee.field_229003_f_, BeeDebugRenderer::lambda$func_228993_f_$8).add(bee.func_229007_a_());
    }

    private static Set lambda$func_228993_f_$8(BlockPos blockPos) {
        return Sets.newHashSet();
    }

    private static void lambda$func_228991_e_$7(Map map, Bee bee) {
        bee.field_229006_i_.forEach(arg_0 -> BeeDebugRenderer.lambda$func_228991_e_$6(map, bee, arg_0));
    }

    private static void lambda$func_228991_e_$6(Map map, Bee bee, BlockPos blockPos) {
        map.computeIfAbsent(blockPos, BeeDebugRenderer::lambda$func_228991_e_$5).add(bee.func_229007_a_());
    }

    private static Set lambda$func_228991_e_$5(BlockPos blockPos) {
        return Sets.newHashSet();
    }

    private void lambda$func_228989_d_$4(BlockPos blockPos, BlockPos blockPos2, List list) {
        if (blockPos.withinDistance(blockPos2, 30.0)) {
            this.func_228972_a_(blockPos2, list);
        }
    }

    private void lambda$func_228989_d_$3(BlockPos blockPos, Map map, Hive hive) {
        if (blockPos.withinDistance(hive.field_229011_a_, 30.0)) {
            Set set = (Set)map.get(hive.field_229011_a_);
            this.func_228967_a_(hive, set == null ? Sets.newHashSet() : set);
        }
    }

    private void lambda$func_228989_d_$2(Bee bee) {
        if (this.func_228992_e_(bee)) {
            this.func_228988_c_(bee);
        }
    }

    private static boolean lambda$func_228987_c_$1(long l, Map.Entry entry) {
        return ((Hive)entry.getValue()).field_229016_f_ < l;
    }

    private boolean lambda$func_228981_b_$0(Map.Entry entry) {
        return this.field_228958_a_.world.getEntityByID(((Bee)entry.getValue()).field_228999_b_) == null;
    }

    public static class Hive {
        public final BlockPos field_229011_a_;
        public final String field_229012_b_;
        public final int field_229013_c_;
        public final int field_229014_d_;
        public final boolean field_229015_e_;
        public final long field_229016_f_;

        public Hive(BlockPos blockPos, String string, int n, int n2, boolean bl, long l) {
            this.field_229011_a_ = blockPos;
            this.field_229012_b_ = string;
            this.field_229013_c_ = n;
            this.field_229014_d_ = n2;
            this.field_229015_e_ = bl;
            this.field_229016_f_ = l;
        }
    }

    public static class Bee {
        public final UUID field_228998_a_;
        public final int field_228999_b_;
        public final IPosition field_229000_c_;
        @Nullable
        public final Path field_229001_d_;
        @Nullable
        public final BlockPos field_229002_e_;
        @Nullable
        public final BlockPos field_229003_f_;
        public final int field_229004_g_;
        public final List<String> field_229005_h_ = Lists.newArrayList();
        public final Set<BlockPos> field_229006_i_ = Sets.newHashSet();

        public Bee(UUID uUID, int n, IPosition iPosition, Path path, BlockPos blockPos, BlockPos blockPos2, int n2) {
            this.field_228998_a_ = uUID;
            this.field_228999_b_ = n;
            this.field_229000_c_ = iPosition;
            this.field_229001_d_ = path;
            this.field_229002_e_ = blockPos;
            this.field_229003_f_ = blockPos2;
            this.field_229004_g_ = n2;
        }

        public boolean func_229008_a_(BlockPos blockPos) {
            return this.field_229002_e_ != null && this.field_229002_e_.equals(blockPos);
        }

        public UUID func_229007_a_() {
            return this.field_228998_a_;
        }

        public String func_229009_b_() {
            return RandomObjectDescriptor.getRandomObjectDescriptor(this.field_228998_a_);
        }

        public String toString() {
            return this.func_229009_b_();
        }

        public boolean func_229010_c_() {
            return this.field_229003_f_ != null;
        }
    }
}

