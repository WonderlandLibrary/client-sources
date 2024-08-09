/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SystemToast
implements IToast {
    private final Type type;
    private ITextComponent title;
    private List<IReorderingProcessor> field_238531_e_;
    private long firstDrawTime;
    private boolean newDisplay;
    private final int field_238532_h_;

    public SystemToast(Type type, ITextComponent iTextComponent, @Nullable ITextComponent iTextComponent2) {
        this(type, iTextComponent, SystemToast.func_238537_a_(iTextComponent2), 160);
    }

    public static SystemToast func_238534_a_(Minecraft minecraft, Type type, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        FontRenderer fontRenderer = minecraft.fontRenderer;
        List<IReorderingProcessor> list = fontRenderer.trimStringToWidth(iTextComponent2, 200);
        int n = Math.max(200, list.stream().mapToInt(fontRenderer::func_243245_a).max().orElse(200));
        return new SystemToast(type, iTextComponent, list, n + 30);
    }

    private SystemToast(Type type, ITextComponent iTextComponent, List<IReorderingProcessor> list, int n) {
        this.type = type;
        this.title = iTextComponent;
        this.field_238531_e_ = list;
        this.field_238532_h_ = n;
    }

    private static ImmutableList<IReorderingProcessor> func_238537_a_(@Nullable ITextComponent iTextComponent) {
        return iTextComponent == null ? ImmutableList.of() : ImmutableList.of(iTextComponent.func_241878_f());
    }

    @Override
    public int func_230445_a_() {
        return this.field_238532_h_;
    }

    @Override
    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long l) {
        int n;
        if (this.newDisplay) {
            this.firstDrawTime = l;
            this.newDisplay = false;
        }
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        int n2 = this.func_230445_a_();
        int n3 = 12;
        if (n2 == 160 && this.field_238531_e_.size() <= 1) {
            toastGui.blit(matrixStack, 0, 0, 0, 64, n2, this.func_238540_d_());
        } else {
            n = this.func_238540_d_() + Math.max(0, this.field_238531_e_.size() - 1) * 12;
            int n4 = 28;
            int n5 = Math.min(4, n - 28);
            this.func_238533_a_(matrixStack, toastGui, n2, 0, 0, 28);
            for (int i = 28; i < n - n5; i += 10) {
                this.func_238533_a_(matrixStack, toastGui, n2, 16, i, Math.min(16, n - i - n5));
            }
            this.func_238533_a_(matrixStack, toastGui, n2, 32 - n5, n - n5, n5);
        }
        if (this.field_238531_e_ == null) {
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.title, 18.0f, 12.0f, -256);
        } else {
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.title, 18.0f, 7.0f, -256);
            for (n = 0; n < this.field_238531_e_.size(); ++n) {
                toastGui.getMinecraft().fontRenderer.func_238422_b_(matrixStack, this.field_238531_e_.get(n), 18.0f, 18 + n * 12, -1);
            }
        }
        return l - this.firstDrawTime < 5000L ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
    }

    private void func_238533_a_(MatrixStack matrixStack, ToastGui toastGui, int n, int n2, int n3, int n4) {
        int n5 = n2 == 0 ? 20 : 5;
        int n6 = Math.min(60, n - n5);
        toastGui.blit(matrixStack, 0, n3, 0, 64 + n2, n5, n4);
        for (int i = n5; i < n - n6; i += 64) {
            toastGui.blit(matrixStack, i, n3, 32, 64 + n2, Math.min(64, n - i - n6), n4);
        }
        toastGui.blit(matrixStack, n - n6, n3, 160 - n6, 64 + n2, n6, n4);
    }

    public void setDisplayedText(ITextComponent iTextComponent, @Nullable ITextComponent iTextComponent2) {
        this.title = iTextComponent;
        this.field_238531_e_ = SystemToast.func_238537_a_(iTextComponent2);
        this.newDisplay = true;
    }

    public Type getType() {
        return this.type;
    }

    public static void func_238536_a_(ToastGui toastGui, Type type, ITextComponent iTextComponent, @Nullable ITextComponent iTextComponent2) {
        toastGui.add(new SystemToast(type, iTextComponent, iTextComponent2));
    }

    public static void addOrUpdate(ToastGui toastGui, Type type, ITextComponent iTextComponent, @Nullable ITextComponent iTextComponent2) {
        SystemToast systemToast = toastGui.getToast(SystemToast.class, (Object)type);
        if (systemToast == null) {
            SystemToast.func_238536_a_(toastGui, type, iTextComponent, iTextComponent2);
        } else {
            systemToast.setDisplayedText(iTextComponent, iTextComponent2);
        }
    }

    public static void func_238535_a_(Minecraft minecraft, String string) {
        SystemToast.func_238536_a_(minecraft.getToastGui(), Type.WORLD_ACCESS_FAILURE, new TranslationTextComponent("selectWorld.access_failure"), new StringTextComponent(string));
    }

    public static void func_238538_b_(Minecraft minecraft, String string) {
        SystemToast.func_238536_a_(minecraft.getToastGui(), Type.WORLD_ACCESS_FAILURE, new TranslationTextComponent("selectWorld.delete_failure"), new StringTextComponent(string));
    }

    public static void func_238539_c_(Minecraft minecraft, String string) {
        SystemToast.func_238536_a_(minecraft.getToastGui(), Type.PACK_COPY_FAILURE, new TranslationTextComponent("pack.copyFailure"), new StringTextComponent(string));
    }

    @Override
    public Object getType() {
        return this.getType();
    }

    public static enum Type {
        TUTORIAL_HINT,
        NARRATOR_TOGGLE,
        WORLD_BACKUP,
        WORLD_GEN_SETTINGS_TRANSFER,
        PACK_LOAD_FAILURE,
        WORLD_ACCESS_FAILURE,
        PACK_COPY_FAILURE;

    }
}

