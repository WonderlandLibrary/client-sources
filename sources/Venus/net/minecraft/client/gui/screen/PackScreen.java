/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.PackLoadingManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ResourcePackList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PackScreen
extends Screen {
    private static final Logger field_238883_a_ = LogManager.getLogger();
    private static final ITextComponent field_238884_b_ = new TranslationTextComponent("pack.dropInfo").mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent field_238885_c_ = new TranslationTextComponent("pack.folderInfo");
    private static final ResourceLocation field_243391_p = new ResourceLocation("textures/misc/unknown_pack.png");
    private final PackLoadingManager field_238887_q_;
    private final Screen field_238888_r_;
    @Nullable
    private PackDirectoryWatcher field_243392_s;
    private long field_243393_t;
    private ResourcePackList field_238891_u_;
    private ResourcePackList field_238892_v_;
    private final File field_241817_w_;
    private Button field_238894_x_;
    private final Map<String, ResourceLocation> field_243394_y = Maps.newHashMap();

    public PackScreen(Screen screen, net.minecraft.resources.ResourcePackList resourcePackList, Consumer<net.minecraft.resources.ResourcePackList> consumer, File file, ITextComponent iTextComponent) {
        super(iTextComponent);
        this.field_238888_r_ = screen;
        this.field_238887_q_ = new PackLoadingManager(this::func_238904_g_, this::func_243395_a, resourcePackList, consumer);
        this.field_241817_w_ = file;
        this.field_243392_s = PackDirectoryWatcher.func_243403_a(file);
    }

    @Override
    public void closeScreen() {
        this.field_238887_q_.func_241618_c_();
        this.minecraft.displayGuiScreen(this.field_238888_r_);
        this.func_243399_k();
    }

    private void func_243399_k() {
        if (this.field_243392_s != null) {
            try {
                this.field_243392_s.close();
                this.field_243392_s = null;
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    protected void init() {
        this.field_238894_x_ = this.addButton(new Button(this.width / 2 + 4, this.height - 48, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 154, this.height - 48, 150, 20, new TranslationTextComponent("pack.openFolder"), this::lambda$init$1, this::lambda$init$2));
        this.field_238891_u_ = new ResourcePackList(this.minecraft, 200, this.height, new TranslationTextComponent("pack.available.title"));
        this.field_238891_u_.setLeftPos(this.width / 2 - 4 - 200);
        this.children.add(this.field_238891_u_);
        this.field_238892_v_ = new ResourcePackList(this.minecraft, 200, this.height, new TranslationTextComponent("pack.selected.title"));
        this.field_238892_v_.setLeftPos(this.width / 2 + 4);
        this.children.add(this.field_238892_v_);
        this.func_238906_l_();
    }

    @Override
    public void tick() {
        if (this.field_243392_s != null) {
            try {
                if (this.field_243392_s.func_243402_a()) {
                    this.field_243393_t = 20L;
                }
            } catch (IOException iOException) {
                field_238883_a_.warn("Failed to poll for directory {} changes, stopping", (Object)this.field_241817_w_);
                this.func_243399_k();
            }
        }
        if (this.field_243393_t > 0L && --this.field_243393_t == 0L) {
            this.func_238906_l_();
        }
    }

    private void func_238904_g_() {
        this.func_238899_a_(this.field_238892_v_, this.field_238887_q_.func_238869_b_());
        this.func_238899_a_(this.field_238891_u_, this.field_238887_q_.func_238865_a_());
        this.field_238894_x_.active = !this.field_238892_v_.getEventListeners().isEmpty();
    }

    private void func_238899_a_(ResourcePackList resourcePackList, Stream<PackLoadingManager.IPack> stream) {
        resourcePackList.getEventListeners().clear();
        stream.forEach(arg_0 -> this.lambda$func_238899_a_$3(resourcePackList, arg_0));
    }

    private void func_238906_l_() {
        this.field_238887_q_.func_241619_d_();
        this.func_238904_g_();
        this.field_243393_t = 0L;
        this.field_243394_y.clear();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderDirtBackground(0);
        this.field_238891_u_.render(matrixStack, n, n2, f);
        this.field_238892_v_.render(matrixStack, n, n2, f);
        PackScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        PackScreen.drawCenteredString(matrixStack, this.font, field_238884_b_, this.width / 2, 20, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    protected static void func_238895_a_(Minecraft minecraft, List<Path> list, Path path) {
        MutableBoolean mutableBoolean = new MutableBoolean();
        list.forEach(arg_0 -> PackScreen.lambda$func_238895_a_$5(path, mutableBoolean, arg_0));
        if (mutableBoolean.isTrue()) {
            SystemToast.func_238539_c_(minecraft, path.toString());
        }
    }

    @Override
    public void addPacks(List<Path> list) {
        String string = list.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
        this.minecraft.displayGuiScreen(new ConfirmScreen(arg_0 -> this.lambda$addPacks$6(list, arg_0), new TranslationTextComponent("pack.dropConfirm"), new StringTextComponent(string)));
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private ResourceLocation func_243397_a(TextureManager textureManager, ResourcePackInfo resourcePackInfo) {
        try (IResourcePack iResourcePack = resourcePackInfo.getResourcePack();){
            ResourceLocation resourceLocation;
            block15: {
                InputStream inputStream = iResourcePack.getRootResourceStream("pack.png");
                try {
                    String string = resourcePackInfo.getName();
                    ResourceLocation resourceLocation2 = new ResourceLocation("minecraft", "pack/" + Util.func_244361_a(string, ResourceLocation::validatePathChar) + "/" + Hashing.sha1().hashUnencodedChars(string) + "/icon");
                    NativeImage nativeImage = NativeImage.read(inputStream);
                    textureManager.loadTexture(resourceLocation2, new DynamicTexture(nativeImage));
                    resourceLocation = resourceLocation2;
                    if (inputStream == null) break block15;
                } catch (Throwable throwable) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                inputStream.close();
            }
            return resourceLocation;
        } catch (FileNotFoundException fileNotFoundException) {
        } catch (Exception exception) {
            field_238883_a_.warn("Failed to load icon from pack {}", (Object)resourcePackInfo.getName(), (Object)exception);
        }
        return field_243391_p;
    }

    private ResourceLocation func_243395_a(ResourcePackInfo resourcePackInfo) {
        return this.field_243394_y.computeIfAbsent(resourcePackInfo.getName(), arg_0 -> this.lambda$func_243395_a$7(resourcePackInfo, arg_0));
    }

    private ResourceLocation lambda$func_243395_a$7(ResourcePackInfo resourcePackInfo, String string) {
        return this.func_243397_a(this.minecraft.getTextureManager(), resourcePackInfo);
    }

    private void lambda$addPacks$6(List list, boolean bl) {
        if (bl) {
            PackScreen.func_238895_a_(this.minecraft, list, this.field_241817_w_.toPath());
            this.func_238906_l_();
        }
        this.minecraft.displayGuiScreen(this);
    }

    private static void lambda$func_238895_a_$5(Path path, MutableBoolean mutableBoolean, Path path2) {
        try (Stream<Path> stream = Files.walk(path2, new FileVisitOption[0]);){
            stream.forEach(arg_0 -> PackScreen.lambda$func_238895_a_$4(path2, path, mutableBoolean, arg_0));
        } catch (IOException iOException) {
            field_238883_a_.warn("Failed to copy datapack file from {} to {}", (Object)path2, (Object)path);
            mutableBoolean.setTrue();
        }
    }

    private static void lambda$func_238895_a_$4(Path path, Path path2, MutableBoolean mutableBoolean, Path path3) {
        try {
            Util.func_240984_a_(path.getParent(), path2, path3);
        } catch (IOException iOException) {
            field_238883_a_.warn("Failed to copy datapack file  from {} to {}", (Object)path3, (Object)path2, (Object)iOException);
            mutableBoolean.setTrue();
        }
    }

    private void lambda$func_238899_a_$3(ResourcePackList resourcePackList, PackLoadingManager.IPack iPack) {
        resourcePackList.getEventListeners().add(new ResourcePackList.ResourcePackEntry(this.minecraft, resourcePackList, this, iPack));
    }

    private void lambda$init$2(Button button, MatrixStack matrixStack, int n, int n2) {
        this.renderTooltip(matrixStack, field_238885_c_, n, n2);
    }

    private void lambda$init$1(Button button) {
        Util.getOSType().openFile(this.field_241817_w_);
    }

    private void lambda$init$0(Button button) {
        this.closeScreen();
    }

    static class PackDirectoryWatcher
    implements AutoCloseable {
        private final WatchService field_243400_a;
        private final Path field_243401_b;

        public PackDirectoryWatcher(File file) throws IOException {
            this.field_243401_b = file.toPath();
            this.field_243400_a = this.field_243401_b.getFileSystem().newWatchService();
            try {
                this.func_243404_a(this.field_243401_b);
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(this.field_243401_b);){
                    for (Path path : directoryStream) {
                        if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) continue;
                        this.func_243404_a(path);
                    }
                }
            } catch (Exception exception) {
                this.field_243400_a.close();
                throw exception;
            }
        }

        @Nullable
        public static PackDirectoryWatcher func_243403_a(File file) {
            try {
                return new PackDirectoryWatcher(file);
            } catch (IOException iOException) {
                field_238883_a_.warn("Failed to initialize pack directory {} monitoring", (Object)file, (Object)iOException);
                return null;
            }
        }

        private void func_243404_a(Path path) throws IOException {
            path.register(this.field_243400_a, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        }

        public boolean func_243402_a() throws IOException {
            WatchKey watchKey;
            boolean bl = false;
            while ((watchKey = this.field_243400_a.poll()) != null) {
                for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
                    Path path;
                    bl = true;
                    if (watchKey.watchable() != this.field_243401_b || watchEvent.kind() != StandardWatchEventKinds.ENTRY_CREATE || !Files.isDirectory(path = this.field_243401_b.resolve((Path)watchEvent.context()), LinkOption.NOFOLLOW_LINKS)) continue;
                    this.func_243404_a(path);
                }
                watchKey.reset();
            }
            return bl;
        }

        @Override
        public void close() throws IOException {
            this.field_243400_a.close();
        }
    }
}

