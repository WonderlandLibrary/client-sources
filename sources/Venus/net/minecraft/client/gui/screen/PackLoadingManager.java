/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PackLoadingManager {
    private final ResourcePackList field_241617_a_;
    private final List<ResourcePackInfo> field_238860_a_;
    private final List<ResourcePackInfo> field_238861_b_;
    private final Function<ResourcePackInfo, ResourceLocation> field_243388_d;
    private final Runnable field_238863_d_;
    private final Consumer<ResourcePackList> field_238864_e_;

    public PackLoadingManager(Runnable runnable, Function<ResourcePackInfo, ResourceLocation> function, ResourcePackList resourcePackList, Consumer<ResourcePackList> consumer) {
        this.field_238863_d_ = runnable;
        this.field_243388_d = function;
        this.field_241617_a_ = resourcePackList;
        this.field_238860_a_ = Lists.newArrayList(resourcePackList.getEnabledPacks());
        Collections.reverse(this.field_238860_a_);
        this.field_238861_b_ = Lists.newArrayList(resourcePackList.getAllPacks());
        this.field_238861_b_.removeAll(this.field_238860_a_);
        this.field_238864_e_ = consumer;
    }

    public Stream<IPack> func_238865_a_() {
        return this.field_238861_b_.stream().map(this::lambda$func_238865_a_$0);
    }

    public Stream<IPack> func_238869_b_() {
        return this.field_238860_a_.stream().map(this::lambda$func_238869_b_$1);
    }

    public void func_241618_c_() {
        this.field_241617_a_.setEnabledPacks(Lists.reverse(this.field_238860_a_).stream().map(ResourcePackInfo::getName).collect(ImmutableList.toImmutableList()));
        this.field_238864_e_.accept(this.field_241617_a_);
    }

    public void func_241619_d_() {
        this.field_241617_a_.reloadPacksFromFinders();
        this.field_238860_a_.retainAll(this.field_241617_a_.getAllPacks());
        this.field_238861_b_.clear();
        this.field_238861_b_.addAll(this.field_241617_a_.getAllPacks());
        this.field_238861_b_.removeAll(this.field_238860_a_);
    }

    private IPack lambda$func_238869_b_$1(ResourcePackInfo resourcePackInfo) {
        return new EnabledPack(this, resourcePackInfo);
    }

    private IPack lambda$func_238865_a_$0(ResourcePackInfo resourcePackInfo) {
        return new DisabledPack(this, resourcePackInfo);
    }

    class EnabledPack
    extends AbstractPack {
        final PackLoadingManager this$0;

        public EnabledPack(PackLoadingManager packLoadingManager, ResourcePackInfo resourcePackInfo) {
            this.this$0 = packLoadingManager;
            super(packLoadingManager, resourcePackInfo);
        }

        @Override
        protected List<ResourcePackInfo> func_230474_q_() {
            return this.this$0.field_238860_a_;
        }

        @Override
        protected List<ResourcePackInfo> func_230475_r_() {
            return this.this$0.field_238861_b_;
        }

        @Override
        public boolean func_230473_l_() {
            return false;
        }

        @Override
        public void func_230471_h_() {
        }

        @Override
        public void func_230472_i_() {
            this.func_238880_s_();
        }
    }

    class DisabledPack
    extends AbstractPack {
        final PackLoadingManager this$0;

        public DisabledPack(PackLoadingManager packLoadingManager, ResourcePackInfo resourcePackInfo) {
            this.this$0 = packLoadingManager;
            super(packLoadingManager, resourcePackInfo);
        }

        @Override
        protected List<ResourcePackInfo> func_230474_q_() {
            return this.this$0.field_238861_b_;
        }

        @Override
        protected List<ResourcePackInfo> func_230475_r_() {
            return this.this$0.field_238860_a_;
        }

        @Override
        public boolean func_230473_l_() {
            return true;
        }

        @Override
        public void func_230471_h_() {
            this.func_238880_s_();
        }

        @Override
        public void func_230472_i_() {
        }
    }

    public static interface IPack {
        public ResourceLocation func_241868_a();

        public PackCompatibility func_230460_a_();

        public ITextComponent func_230462_b_();

        public ITextComponent func_230463_c_();

        public IPackNameDecorator func_230464_d_();

        default public ITextComponent func_243390_f() {
            return this.func_230464_d_().decorate(this.func_230463_c_());
        }

        public boolean func_230465_f_();

        public boolean func_230466_g_();

        public void func_230471_h_();

        public void func_230472_i_();

        public void func_230467_j_();

        public void func_230468_k_();

        public boolean func_230473_l_();

        default public boolean func_238875_m_() {
            return !this.func_230473_l_();
        }

        default public boolean func_238876_n_() {
            return this.func_230473_l_() && !this.func_230466_g_();
        }

        public boolean func_230469_o_();

        public boolean func_230470_p_();
    }

    abstract class AbstractPack
    implements IPack {
        private final ResourcePackInfo field_238878_b_;
        final PackLoadingManager this$0;

        public AbstractPack(PackLoadingManager packLoadingManager, ResourcePackInfo resourcePackInfo) {
            this.this$0 = packLoadingManager;
            this.field_238878_b_ = resourcePackInfo;
        }

        protected abstract List<ResourcePackInfo> func_230474_q_();

        protected abstract List<ResourcePackInfo> func_230475_r_();

        @Override
        public ResourceLocation func_241868_a() {
            return this.this$0.field_243388_d.apply(this.field_238878_b_);
        }

        @Override
        public PackCompatibility func_230460_a_() {
            return this.field_238878_b_.getCompatibility();
        }

        @Override
        public ITextComponent func_230462_b_() {
            return this.field_238878_b_.getTitle();
        }

        @Override
        public ITextComponent func_230463_c_() {
            return this.field_238878_b_.getDescription();
        }

        @Override
        public IPackNameDecorator func_230464_d_() {
            return this.field_238878_b_.getDecorator();
        }

        @Override
        public boolean func_230465_f_() {
            return this.field_238878_b_.isOrderLocked();
        }

        @Override
        public boolean func_230466_g_() {
            return this.field_238878_b_.isAlwaysEnabled();
        }

        protected void func_238880_s_() {
            this.func_230474_q_().remove(this.field_238878_b_);
            this.field_238878_b_.getPriority().insert(this.func_230475_r_(), this.field_238878_b_, Function.identity(), false);
            this.this$0.field_238863_d_.run();
        }

        protected void func_238879_a_(int n) {
            List<ResourcePackInfo> list = this.func_230474_q_();
            int n2 = list.indexOf(this.field_238878_b_);
            list.remove(n2);
            list.add(n2 + n, this.field_238878_b_);
            this.this$0.field_238863_d_.run();
        }

        @Override
        public boolean func_230469_o_() {
            List<ResourcePackInfo> list = this.func_230474_q_();
            int n = list.indexOf(this.field_238878_b_);
            return n > 0 && !list.get(n - 1).isOrderLocked();
        }

        @Override
        public void func_230467_j_() {
            this.func_238879_a_(-1);
        }

        @Override
        public boolean func_230470_p_() {
            List<ResourcePackInfo> list = this.func_230474_q_();
            int n = list.indexOf(this.field_238878_b_);
            return n >= 0 && n < list.size() - 1 && !list.get(n + 1).isOrderLocked();
        }

        @Override
        public void func_230468_k_() {
            this.func_238879_a_(1);
        }
    }
}

