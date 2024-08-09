/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.WorldOptimizer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptimizeWorldScreen
extends Screen {
    private static final Logger field_239024_a_ = LogManager.getLogger();
    private static final Object2IntMap<RegistryKey<World>> PROGRESS_BAR_COLORS = Util.make(new Object2IntOpenCustomHashMap(Util.identityHashStrategy()), OptimizeWorldScreen::lambda$static$0);
    private final BooleanConsumer field_214332_b;
    private final WorldOptimizer optimizer;

    @Nullable
    public static OptimizeWorldScreen func_239025_a_(Minecraft minecraft, BooleanConsumer booleanConsumer, DataFixer dataFixer, SaveFormat.LevelSave levelSave, boolean bl) {
        DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
        Minecraft.PackManager packManager = minecraft.reloadDatapacks(impl, Minecraft::loadDataPackCodec, Minecraft::loadWorld, false, levelSave);
        try {
            IServerConfiguration iServerConfiguration = packManager.getServerConfiguration();
            levelSave.saveLevel(impl, iServerConfiguration);
            ImmutableSet<RegistryKey<World>> immutableSet = iServerConfiguration.getDimensionGeneratorSettings().func_236226_g_();
            OptimizeWorldScreen optimizeWorldScreen = new OptimizeWorldScreen(booleanConsumer, dataFixer, levelSave, iServerConfiguration.getWorldSettings(), bl, immutableSet);
            if (packManager != null) {
                packManager.close();
            }
            return optimizeWorldScreen;
        } catch (Throwable throwable) {
            try {
                if (packManager != null) {
                    try {
                        packManager.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            } catch (Exception exception) {
                field_239024_a_.warn("Failed to load datapacks, can't optimize world", (Throwable)exception);
                return null;
            }
        }
    }

    private OptimizeWorldScreen(BooleanConsumer booleanConsumer, DataFixer dataFixer, SaveFormat.LevelSave levelSave, WorldSettings worldSettings, boolean bl, ImmutableSet<RegistryKey<World>> immutableSet) {
        super(new TranslationTextComponent("optimizeWorld.title", worldSettings.getWorldName()));
        this.field_214332_b = booleanConsumer;
        this.optimizer = new WorldOptimizer(levelSave, dataFixer, immutableSet, bl);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 150, 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
    }

    @Override
    public void tick() {
        if (this.optimizer.isFinished()) {
            this.field_214332_b.accept(true);
        }
    }

    @Override
    public void closeScreen() {
        this.field_214332_b.accept(false);
    }

    @Override
    public void onClose() {
        this.optimizer.cancel();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        OptimizeWorldScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        int n3 = this.width / 2 - 150;
        int n4 = this.width / 2 + 150;
        int n5 = this.height / 4 + 100;
        int n6 = n5 + 10;
        OptimizeWorldScreen.drawCenteredString(matrixStack, this.font, this.optimizer.getStatusText(), this.width / 2, n5 - 9 - 2, 0xA0A0A0);
        if (this.optimizer.getTotalChunks() > 0) {
            OptimizeWorldScreen.fill(matrixStack, n3 - 1, n5 - 1, n4 + 1, n6 + 1, -16777216);
            OptimizeWorldScreen.drawString(matrixStack, this.font, new TranslationTextComponent("optimizeWorld.info.converted", this.optimizer.getConverted()), n3, 40, 0xA0A0A0);
            OptimizeWorldScreen.drawString(matrixStack, this.font, new TranslationTextComponent("optimizeWorld.info.skipped", this.optimizer.getSkipped()), n3, 52, 0xA0A0A0);
            OptimizeWorldScreen.drawString(matrixStack, this.font, new TranslationTextComponent("optimizeWorld.info.total", this.optimizer.getTotalChunks()), n3, 64, 0xA0A0A0);
            int n7 = 0;
            for (RegistryKey registryKey : this.optimizer.func_233533_c_()) {
                int n8 = MathHelper.floor(this.optimizer.func_233531_a_(registryKey) * (float)(n4 - n3));
                OptimizeWorldScreen.fill(matrixStack, n3 + n7, n5, n3 + n7 + n8, n6, PROGRESS_BAR_COLORS.getInt(registryKey));
                n7 += n8;
            }
            int n9 = this.optimizer.getConverted() + this.optimizer.getSkipped();
            OptimizeWorldScreen.drawCenteredString(matrixStack, this.font, n9 + " / " + this.optimizer.getTotalChunks(), this.width / 2, n5 + 18 + 2, 0xA0A0A0);
            OptimizeWorldScreen.drawCenteredString(matrixStack, this.font, MathHelper.floor(this.optimizer.getTotalProgress() * 100.0f) + "%", this.width / 2, n5 + (n6 - n5) / 2 - 4, 0xA0A0A0);
        }
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.optimizer.cancel();
        this.field_214332_b.accept(false);
    }

    private static void lambda$static$0(Object2IntOpenCustomHashMap object2IntOpenCustomHashMap) {
        object2IntOpenCustomHashMap.put(World.OVERWORLD, -13408734);
        object2IntOpenCustomHashMap.put(World.THE_NETHER, -10075085);
        object2IntOpenCustomHashMap.put(World.THE_END, -8943531);
        object2IntOpenCustomHashMap.defaultReturnValue(-2236963);
    }
}

