/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CClientStatusPacket;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class StatsScreen
extends Screen
implements IProgressMeter {
    private static final ITextComponent field_243320_c = new TranslationTextComponent("multiplayer.downloadingStats");
    protected final Screen parentScreen;
    private CustomStatsList generalStats;
    private StatsList itemStats;
    private MobStatsList mobStats;
    private final StatisticsManager stats;
    @Nullable
    private ExtendedList<?> displaySlot;
    private boolean doesGuiPauseGame = true;

    public StatsScreen(Screen screen, StatisticsManager statisticsManager) {
        super(new TranslationTextComponent("gui.stats"));
        this.parentScreen = screen;
        this.stats = statisticsManager;
    }

    @Override
    protected void init() {
        this.doesGuiPauseGame = true;
        this.minecraft.getConnection().sendPacket(new CClientStatusPacket(CClientStatusPacket.State.REQUEST_STATS));
    }

    public void initLists() {
        this.generalStats = new CustomStatsList(this, this.minecraft);
        this.itemStats = new StatsList(this, this.minecraft);
        this.mobStats = new MobStatsList(this, this.minecraft);
    }

    public void initButtons() {
        this.addButton(new Button(this.width / 2 - 120, this.height - 52, 80, 20, new TranslationTextComponent("stat.generalButton"), this::lambda$initButtons$0));
        Button button = this.addButton(new Button(this.width / 2 - 40, this.height - 52, 80, 20, new TranslationTextComponent("stat.itemsButton"), this::lambda$initButtons$1));
        Button button2 = this.addButton(new Button(this.width / 2 + 40, this.height - 52, 80, 20, new TranslationTextComponent("stat.mobsButton"), this::lambda$initButtons$2));
        this.addButton(new Button(this.width / 2 - 100, this.height - 28, 200, 20, DialogTexts.GUI_DONE, this::lambda$initButtons$3));
        if (this.itemStats.getEventListeners().isEmpty()) {
            button.active = false;
        }
        if (this.mobStats.getEventListeners().isEmpty()) {
            button2.active = false;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.doesGuiPauseGame) {
            this.renderBackground(matrixStack);
            StatsScreen.drawCenteredString(matrixStack, this.font, field_243320_c, this.width / 2, this.height / 2, 0xFFFFFF);
            StatsScreen.drawCenteredString(matrixStack, this.font, LOADING_STRINGS[(int)(Util.milliTime() / 150L % (long)LOADING_STRINGS.length)], this.width / 2, this.height / 2 + 18, 0xFFFFFF);
        } else {
            this.func_213116_d().render(matrixStack, n, n2, f);
            StatsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
            super.render(matrixStack, n, n2, f);
        }
    }

    @Override
    public void onStatsUpdated() {
        if (this.doesGuiPauseGame) {
            this.initLists();
            this.initButtons();
            this.func_213110_a(this.generalStats);
            this.doesGuiPauseGame = false;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return !this.doesGuiPauseGame;
    }

    @Nullable
    public ExtendedList<?> func_213116_d() {
        return this.displaySlot;
    }

    public void func_213110_a(@Nullable ExtendedList<?> extendedList) {
        this.children.remove(this.generalStats);
        this.children.remove(this.itemStats);
        this.children.remove(this.mobStats);
        if (extendedList != null) {
            this.children.add(0, extendedList);
            this.displaySlot = extendedList;
        }
    }

    private static String func_238672_b_(Stat<ResourceLocation> stat) {
        return "stat." + stat.getValue().toString().replace(':', '.');
    }

    private int func_195224_b(int n) {
        return 115 + 40 * n;
    }

    private void func_238667_a_(MatrixStack matrixStack, int n, int n2, Item item) {
        this.func_238674_c_(matrixStack, n + 1, n2 + 1, 0, 0);
        RenderSystem.enableRescaleNormal();
        this.itemRenderer.renderItemIntoGUI(item.getDefaultInstance(), n + 2, n2 + 2);
        RenderSystem.disableRescaleNormal();
    }

    private void func_238674_c_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(STATS_ICON_LOCATION);
        StatsScreen.blit(matrixStack, n, n2, this.getBlitOffset(), n3, n4, 18, 18, 128, 128);
    }

    private void lambda$initButtons$3(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$initButtons$2(Button button) {
        this.func_213110_a(this.mobStats);
    }

    private void lambda$initButtons$1(Button button) {
        this.func_213110_a(this.itemStats);
    }

    private void lambda$initButtons$0(Button button) {
        this.func_213110_a(this.generalStats);
    }

    class CustomStatsList
    extends ExtendedList<Entry> {
        final StatsScreen this$0;

        public CustomStatsList(StatsScreen statsScreen, Minecraft minecraft) {
            this.this$0 = statsScreen;
            super(minecraft, statsScreen.width, statsScreen.height, 32, statsScreen.height - 64, 10);
            ObjectArrayList<Stat<ResourceLocation>> objectArrayList = new ObjectArrayList<Stat<ResourceLocation>>(Stats.CUSTOM.iterator());
            objectArrayList.sort(Comparator.comparing(CustomStatsList::lambda$new$0));
            for (Stat stat : objectArrayList) {
                this.addEntry(new Entry(this, stat));
            }
        }

        @Override
        protected void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        private static String lambda$new$0(Stat stat) {
            return I18n.format(StatsScreen.func_238672_b_(stat), new Object[0]);
        }

        class Entry
        extends AbstractList.AbstractListEntry<Entry> {
            private final Stat<ResourceLocation> field_214405_b;
            private final ITextComponent field_243321_c;
            final CustomStatsList this$1;

            private Entry(CustomStatsList customStatsList, Stat<ResourceLocation> stat) {
                this.this$1 = customStatsList;
                this.field_214405_b = stat;
                this.field_243321_c = new TranslationTextComponent(StatsScreen.func_238672_b_(stat));
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, this.field_243321_c, n3 + 2, n2 + 1, n % 2 == 0 ? 0xFFFFFF : 0x909090);
                String string = this.field_214405_b.format(this.this$1.this$0.stats.getValue(this.field_214405_b));
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, string, n3 + 2 + 213 - this.this$1.this$0.font.getStringWidth(string), n2 + 1, n % 2 == 0 ? 0xFFFFFF : 0x909090);
            }
        }
    }

    class StatsList
    extends ExtendedList<Entry> {
        protected final List<StatType<Block>> field_195113_v;
        protected final List<StatType<Item>> field_195114_w;
        private final int[] field_195112_D;
        protected int field_195115_x;
        protected final List<Item> field_195116_y;
        protected final java.util.Comparator<Item> field_195117_z;
        @Nullable
        protected StatType<?> field_195110_A;
        protected int field_195111_B;
        final StatsScreen this$0;

        public StatsList(StatsScreen statsScreen, Minecraft minecraft) {
            boolean bl;
            this.this$0 = statsScreen;
            super(minecraft, statsScreen.width, statsScreen.height, 32, statsScreen.height - 64, 20);
            this.field_195112_D = new int[]{3, 4, 1, 2, 5, 6};
            this.field_195115_x = -1;
            this.field_195117_z = new Comparator(this);
            this.field_195113_v = Lists.newArrayList();
            this.field_195113_v.add(Stats.BLOCK_MINED);
            this.field_195114_w = Lists.newArrayList(Stats.ITEM_BROKEN, Stats.ITEM_CRAFTED, Stats.ITEM_USED, Stats.ITEM_PICKED_UP, Stats.ITEM_DROPPED);
            this.setRenderHeader(true, 1);
            Set<Item> set = Sets.newIdentityHashSet();
            for (Item iItemProvider : Registry.ITEM) {
                bl = false;
                for (StatType<Item> statType : this.field_195114_w) {
                    if (!statType.contains(iItemProvider) || statsScreen.stats.getValue(statType.get(iItemProvider)) <= 0) continue;
                    bl = true;
                }
                if (!bl) continue;
                set.add(iItemProvider);
            }
            for (Block block : Registry.BLOCK) {
                bl = false;
                for (StatType<IItemProvider> statType : this.field_195113_v) {
                    if (!statType.contains(block) || statsScreen.stats.getValue(statType.get(block)) <= 0) continue;
                    bl = true;
                }
                if (!bl) continue;
                set.add(block.asItem());
            }
            set.remove(Items.AIR);
            this.field_195116_y = Lists.newArrayList(set);
            for (int i = 0; i < this.field_195116_y.size(); ++i) {
                this.addEntry(new Entry(this));
            }
        }

        @Override
        protected void renderHeader(MatrixStack matrixStack, int n, int n2, Tessellator tessellator) {
            int n3;
            int n4;
            if (!this.minecraft.mouseHelper.isLeftDown()) {
                this.field_195115_x = -1;
            }
            for (n4 = 0; n4 < this.field_195112_D.length; ++n4) {
                this.this$0.func_238674_c_(matrixStack, n + this.this$0.func_195224_b(n4) - 18, n2 + 1, 0, this.field_195115_x == n4 ? 0 : 18);
            }
            if (this.field_195110_A != null) {
                n4 = this.this$0.func_195224_b(this.func_195105_b(this.field_195110_A)) - 36;
                n3 = this.field_195111_B == 1 ? 2 : 1;
                this.this$0.func_238674_c_(matrixStack, n + n4, n2 + 1, 18 * n3, 0);
            }
            for (n4 = 0; n4 < this.field_195112_D.length; ++n4) {
                n3 = this.field_195115_x == n4 ? 1 : 0;
                this.this$0.func_238674_c_(matrixStack, n + this.this$0.func_195224_b(n4) - 18 + n3, n2 + 1 + n3, 18 * this.field_195112_D[n4], 18);
            }
        }

        @Override
        public int getRowWidth() {
            return 0;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width / 2 + 140;
        }

        @Override
        protected void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        protected void clickedHeader(int n, int n2) {
            this.field_195115_x = -1;
            for (int i = 0; i < this.field_195112_D.length; ++i) {
                int n3 = n - this.this$0.func_195224_b(i);
                if (n3 < -36 || n3 > 0) continue;
                this.field_195115_x = i;
                break;
            }
            if (this.field_195115_x >= 0) {
                this.func_195107_a(this.func_195108_d(this.field_195115_x));
                this.minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }

        private StatType<?> func_195108_d(int n) {
            return n < this.field_195113_v.size() ? this.field_195113_v.get(n) : this.field_195114_w.get(n - this.field_195113_v.size());
        }

        private int func_195105_b(StatType<?> statType) {
            int n = this.field_195113_v.indexOf(statType);
            if (n >= 0) {
                return n;
            }
            int n2 = this.field_195114_w.indexOf(statType);
            return n2 >= 0 ? n2 + this.field_195113_v.size() : -1;
        }

        @Override
        protected void renderDecorations(MatrixStack matrixStack, int n, int n2) {
            if (n2 >= this.y0 && n2 <= this.y1) {
                Entry entry = (Entry)this.getEntryAtPosition(n, n2);
                int n3 = (this.width - this.getRowWidth()) / 2;
                if (entry != null) {
                    if (n < n3 + 40 || n > n3 + 40 + 20) {
                        return;
                    }
                    Item item = this.field_195116_y.get(this.getEventListeners().indexOf(entry));
                    this.func_238680_a_(matrixStack, this.func_200208_a(item), n, n2);
                } else {
                    ITextComponent iTextComponent = null;
                    int n4 = n - n3;
                    for (int i = 0; i < this.field_195112_D.length; ++i) {
                        int n5 = this.this$0.func_195224_b(i);
                        if (n4 < n5 - 18 || n4 > n5) continue;
                        iTextComponent = this.func_195108_d(i).func_242170_d();
                        break;
                    }
                    this.func_238680_a_(matrixStack, iTextComponent, n, n2);
                }
            }
        }

        protected void func_238680_a_(MatrixStack matrixStack, @Nullable ITextComponent iTextComponent, int n, int n2) {
            if (iTextComponent != null) {
                int n3 = n + 12;
                int n4 = n2 - 12;
                int n5 = this.this$0.font.getStringPropertyWidth(iTextComponent);
                this.fillGradient(matrixStack, n3 - 3, n4 - 3, n3 + n5 + 3, n4 + 8 + 3, -1073741824, -1073741824);
                RenderSystem.pushMatrix();
                RenderSystem.translatef(0.0f, 0.0f, 400.0f);
                this.this$0.font.func_243246_a(matrixStack, iTextComponent, n3, n4, -1);
                RenderSystem.popMatrix();
            }
        }

        protected ITextComponent func_200208_a(Item item) {
            return item.getName();
        }

        protected void func_195107_a(StatType<?> statType) {
            if (statType != this.field_195110_A) {
                this.field_195110_A = statType;
                this.field_195111_B = -1;
            } else if (this.field_195111_B == -1) {
                this.field_195111_B = 1;
            } else {
                this.field_195110_A = null;
                this.field_195111_B = 0;
            }
            this.field_195116_y.sort(this.field_195117_z);
        }

        class Comparator
        implements java.util.Comparator<Item> {
            final StatsList this$1;

            private Comparator(StatsList statsList) {
                this.this$1 = statsList;
            }

            @Override
            public int compare(Item item, Item item2) {
                int n;
                int n2;
                if (this.this$1.field_195110_A == null) {
                    n2 = 0;
                    n = 0;
                } else if (this.this$1.field_195113_v.contains(this.this$1.field_195110_A)) {
                    StatType<?> statType = this.this$1.field_195110_A;
                    n2 = item instanceof BlockItem ? this.this$1.this$0.stats.getValue(statType, ((BlockItem)item).getBlock()) : -1;
                    n = item2 instanceof BlockItem ? this.this$1.this$0.stats.getValue(statType, ((BlockItem)item2).getBlock()) : -1;
                } else {
                    StatType<?> statType = this.this$1.field_195110_A;
                    n2 = this.this$1.this$0.stats.getValue(statType, item);
                    n = this.this$1.this$0.stats.getValue(statType, item2);
                }
                return n2 == n ? this.this$1.field_195111_B * Integer.compare(Item.getIdFromItem(item), Item.getIdFromItem(item2)) : this.this$1.field_195111_B * Integer.compare(n2, n);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Item)object, (Item)object2);
            }
        }

        class Entry
        extends AbstractList.AbstractListEntry<Entry> {
            final StatsList this$1;

            private Entry(StatsList statsList) {
                this.this$1 = statsList;
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                int n8;
                Item item = this.this$1.this$0.itemStats.field_195116_y.get(n);
                this.this$1.this$0.func_238667_a_(matrixStack, n3 + 40, n2, item);
                for (n8 = 0; n8 < this.this$1.this$0.itemStats.field_195113_v.size(); ++n8) {
                    Stat<Block> stat = item instanceof BlockItem ? this.this$1.this$0.itemStats.field_195113_v.get(n8).get(((BlockItem)item).getBlock()) : null;
                    this.func_238681_a_(matrixStack, stat, n3 + this.this$1.this$0.func_195224_b(n8), n2, n % 2 == 0);
                }
                for (n8 = 0; n8 < this.this$1.this$0.itemStats.field_195114_w.size(); ++n8) {
                    this.func_238681_a_(matrixStack, this.this$1.this$0.itemStats.field_195114_w.get(n8).get(item), n3 + this.this$1.this$0.func_195224_b(n8 + this.this$1.this$0.itemStats.field_195113_v.size()), n2, n % 2 == 0);
                }
            }

            protected void func_238681_a_(MatrixStack matrixStack, @Nullable Stat<?> stat, int n, int n2, boolean bl) {
                String string = stat == null ? "-" : stat.format(this.this$1.this$0.stats.getValue(stat));
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, string, n - this.this$1.this$0.font.getStringWidth(string), n2 + 5, bl ? 0xFFFFFF : 0x909090);
            }
        }
    }

    class MobStatsList
    extends ExtendedList<Entry> {
        final StatsScreen this$0;

        public MobStatsList(StatsScreen statsScreen, Minecraft minecraft) {
            this.this$0 = statsScreen;
            super(minecraft, statsScreen.width, statsScreen.height, 32, statsScreen.height - 64, 36);
            for (EntityType entityType : Registry.ENTITY_TYPE) {
                if (statsScreen.stats.getValue(Stats.ENTITY_KILLED.get(entityType)) <= 0 && statsScreen.stats.getValue(Stats.ENTITY_KILLED_BY.get(entityType)) <= 0) continue;
                this.addEntry(new Entry(this, entityType));
            }
        }

        @Override
        protected void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        class Entry
        extends AbstractList.AbstractListEntry<Entry> {
            private final EntityType<?> field_214411_b;
            private final ITextComponent field_243322_c;
            private final ITextComponent field_243323_d;
            private final boolean field_243324_e;
            private final ITextComponent field_243325_f;
            private final boolean field_243326_g;
            final MobStatsList this$1;

            public Entry(MobStatsList mobStatsList, EntityType<?> entityType) {
                this.this$1 = mobStatsList;
                this.field_214411_b = entityType;
                this.field_243322_c = entityType.getName();
                int n = mobStatsList.this$0.stats.getValue(Stats.ENTITY_KILLED.get(entityType));
                if (n == 0) {
                    this.field_243323_d = new TranslationTextComponent("stat_type.minecraft.killed.none", this.field_243322_c);
                    this.field_243324_e = false;
                } else {
                    this.field_243323_d = new TranslationTextComponent("stat_type.minecraft.killed", n, this.field_243322_c);
                    this.field_243324_e = true;
                }
                int n2 = mobStatsList.this$0.stats.getValue(Stats.ENTITY_KILLED_BY.get(entityType));
                if (n2 == 0) {
                    this.field_243325_f = new TranslationTextComponent("stat_type.minecraft.killed_by.none", this.field_243322_c);
                    this.field_243326_g = false;
                } else {
                    this.field_243325_f = new TranslationTextComponent("stat_type.minecraft.killed_by", this.field_243322_c, n2);
                    this.field_243326_g = true;
                }
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, this.field_243322_c, n3 + 2, n2 + 1, 0xFFFFFF);
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, this.field_243323_d, n3 + 2 + 10, n2 + 1 + 9, this.field_243324_e ? 0x909090 : 0x606060);
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, this.field_243325_f, n3 + 2 + 10, n2 + 1 + 18, this.field_243326_g ? 0x909090 : 0x606060);
            }
        }
    }
}

