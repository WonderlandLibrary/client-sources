/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import java.util.Locale;
import javax.annotation.Nullable;
import mpp.venusfr.venusfr;
import net.minecraft.block.BlockState;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.ClipboardHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.INestedGuiEventHandler;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.screen.GamemodeSelectionScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WithNarratorSettingsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.util.NativeUtil;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.gui.GuiShaderOptions;

public class KeyboardListener {
    private final Minecraft mc;
    private boolean repeatEventsEnabled;
    public final ClipboardHelper clipboardHelper = new ClipboardHelper();
    private long debugCrashKeyPressTime = -1L;
    private long lastDebugCrashWarning = -1L;
    private long debugCrashWarningsSent = -1L;
    private boolean actionKeyF3;

    public KeyboardListener(Minecraft minecraft) {
        this.mc = minecraft;
    }

    private void printDebugMessage(String string, Object ... objectArray) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent("").append(new TranslationTextComponent("debug.prefix").mergeStyle(TextFormatting.YELLOW, TextFormatting.BOLD)).appendString(" ").append(new TranslationTextComponent(string, objectArray)));
    }

    private void printDebugWarning(String string, Object ... objectArray) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent("").append(new TranslationTextComponent("debug.prefix").mergeStyle(TextFormatting.RED, TextFormatting.BOLD)).appendString(" ").append(new TranslationTextComponent(string, objectArray)));
    }

    private boolean processKeyF3(int n) {
        if (this.debugCrashKeyPressTime > 0L && this.debugCrashKeyPressTime < Util.milliTime() - 100L) {
            return false;
        }
        switch (n) {
            case 65: {
                this.mc.worldRenderer.loadRenderers();
                this.printDebugMessage("debug.reload_chunks.message", new Object[0]);
                return false;
            }
            case 66: {
                boolean bl = !this.mc.getRenderManager().isDebugBoundingBox();
                this.mc.getRenderManager().setDebugBoundingBox(bl);
                this.printDebugMessage(bl ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off", new Object[0]);
                return false;
            }
            case 67: {
                if (this.mc.player.hasReducedDebug()) {
                    return true;
                }
                ClientPlayNetHandler clientPlayNetHandler = this.mc.player.connection;
                if (clientPlayNetHandler == null) {
                    return true;
                }
                this.printDebugMessage("debug.copy_location.message", new Object[0]);
                this.setClipboardString(String.format(Locale.ROOT, "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f", this.mc.player.world.getDimensionKey().getLocation(), this.mc.player.getPosX(), this.mc.player.getPosY(), this.mc.player.getPosZ(), Float.valueOf(this.mc.player.rotationYaw), Float.valueOf(this.mc.player.rotationPitch)));
                return false;
            }
            case 68: {
                if (this.mc.ingameGUI != null) {
                    this.mc.ingameGUI.getChatGUI().clearChatMessages(true);
                }
                return false;
            }
            case 70: {
                AbstractOption.RENDER_DISTANCE.set(this.mc.gameSettings, MathHelper.clamp((double)(this.mc.gameSettings.renderDistanceChunks + (Screen.hasShiftDown() ? -1 : 1)), AbstractOption.RENDER_DISTANCE.getMinValue(), AbstractOption.RENDER_DISTANCE.getMaxValue()));
                this.printDebugMessage("debug.cycle_renderdistance.message", this.mc.gameSettings.renderDistanceChunks);
                return false;
            }
            case 71: {
                boolean bl = this.mc.debugRenderer.toggleChunkBorders();
                this.printDebugMessage(bl ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off", new Object[0]);
                return false;
            }
            case 72: {
                this.mc.gameSettings.advancedItemTooltips = !this.mc.gameSettings.advancedItemTooltips;
                this.printDebugMessage(this.mc.gameSettings.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off", new Object[0]);
                this.mc.gameSettings.saveOptions();
                return false;
            }
            case 73: {
                if (!this.mc.player.hasReducedDebug()) {
                    this.copyHoveredObject(this.mc.player.hasPermissionLevel(1), !Screen.hasShiftDown());
                }
                return false;
            }
            case 76: {
                Minecraft minecraft = Config.getMinecraft();
                minecraft.worldRenderer.loadVisibleChunksCounter = 1;
                StringTextComponent stringTextComponent = new StringTextComponent(I18n.format("of.message.loadingVisibleChunks", new Object[0]));
                minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(stringTextComponent, 201435902);
                return false;
            }
            case 78: {
                if (!this.mc.player.hasPermissionLevel(1)) {
                    this.printDebugMessage("debug.creative_spectator.error", new Object[0]);
                } else if (!this.mc.player.isSpectator()) {
                    this.mc.player.sendChatMessage("/gamemode spectator");
                } else {
                    this.mc.player.sendChatMessage("/gamemode " + this.mc.playerController.func_241822_k().getName());
                }
                return false;
            }
            case 79: {
                if (Config.isShaders()) {
                    GuiShaderOptions guiShaderOptions = new GuiShaderOptions(null, Config.getGameSettings());
                    Config.getMinecraft().displayGuiScreen(guiShaderOptions);
                }
                return false;
            }
            case 80: {
                this.mc.gameSettings.pauseOnLostFocus = !this.mc.gameSettings.pauseOnLostFocus;
                this.mc.gameSettings.saveOptions();
                this.printDebugMessage(this.mc.gameSettings.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off", new Object[0]);
                return false;
            }
            case 81: {
                this.printDebugMessage("debug.help.message", new Object[0]);
                NewChatGui newChatGui = this.mc.ingameGUI.getChatGUI();
                newChatGui.printChatMessage(new TranslationTextComponent("debug.reload_chunks.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.show_hitboxes.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.copy_location.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.clear_chat.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.cycle_renderdistance.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.chunk_boundaries.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.advanced_tooltips.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.inspect.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.creative_spectator.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.pause_focus.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.help.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.reload_resourcepacks.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.pause.help"));
                newChatGui.printChatMessage(new TranslationTextComponent("debug.gamemodes.help"));
                return false;
            }
            case 82: {
                if (Config.isShaders()) {
                    Shaders.uninit();
                    Shaders.loadShaderPack();
                }
                return false;
            }
            case 84: {
                this.printDebugMessage("debug.reload_resourcepacks.message", new Object[0]);
                this.mc.reloadResources();
                return false;
            }
            case 293: {
                if (!this.mc.player.hasPermissionLevel(1)) {
                    this.printDebugMessage("debug.gamemodes.error", new Object[0]);
                } else {
                    this.mc.displayGuiScreen(new GamemodeSelectionScreen());
                }
                return false;
            }
        }
        return true;
    }

    private void copyHoveredObject(boolean bl, boolean bl2) {
        RayTraceResult rayTraceResult = this.mc.objectMouseOver;
        if (rayTraceResult != null) {
            switch (1.$SwitchMap$net$minecraft$util$math$RayTraceResult$Type[rayTraceResult.getType().ordinal()]) {
                case 1: {
                    BlockPos blockPos = ((BlockRayTraceResult)rayTraceResult).getPos();
                    BlockState blockState = this.mc.player.world.getBlockState(blockPos);
                    if (bl) {
                        if (bl2) {
                            this.mc.player.connection.getNBTQueryManager().queryTileEntity(blockPos, arg_0 -> this.lambda$copyHoveredObject$0(blockState, blockPos, arg_0));
                            break;
                        }
                        TileEntity tileEntity = this.mc.player.world.getTileEntity(blockPos);
                        CompoundNBT compoundNBT = tileEntity != null ? tileEntity.write(new CompoundNBT()) : null;
                        this.setBlockClipboardString(blockState, blockPos, compoundNBT);
                        this.printDebugMessage("debug.inspect.client.block", new Object[0]);
                        break;
                    }
                    this.setBlockClipboardString(blockState, blockPos, null);
                    this.printDebugMessage("debug.inspect.client.block", new Object[0]);
                    break;
                }
                case 2: {
                    Entity entity2 = ((EntityRayTraceResult)rayTraceResult).getEntity();
                    ResourceLocation resourceLocation = Registry.ENTITY_TYPE.getKey(entity2.getType());
                    if (bl) {
                        if (bl2) {
                            this.mc.player.connection.getNBTQueryManager().queryEntity(entity2.getEntityId(), arg_0 -> this.lambda$copyHoveredObject$1(resourceLocation, entity2, arg_0));
                            break;
                        }
                        CompoundNBT compoundNBT = entity2.writeWithoutTypeId(new CompoundNBT());
                        this.setEntityClipboardString(resourceLocation, entity2.getPositionVec(), compoundNBT);
                        this.printDebugMessage("debug.inspect.client.entity", new Object[0]);
                        break;
                    }
                    this.setEntityClipboardString(resourceLocation, entity2.getPositionVec(), null);
                    this.printDebugMessage("debug.inspect.client.entity", new Object[0]);
                }
            }
        }
    }

    private void setBlockClipboardString(BlockState blockState, BlockPos blockPos, @Nullable CompoundNBT compoundNBT) {
        if (compoundNBT != null) {
            compoundNBT.remove("x");
            compoundNBT.remove("y");
            compoundNBT.remove("z");
            compoundNBT.remove("id");
        }
        StringBuilder stringBuilder = new StringBuilder(BlockStateParser.toString(blockState));
        if (compoundNBT != null) {
            stringBuilder.append(compoundNBT);
        }
        String string = String.format(Locale.ROOT, "/setblock %d %d %d %s", blockPos.getX(), blockPos.getY(), blockPos.getZ(), stringBuilder);
        this.setClipboardString(string);
    }

    private void setEntityClipboardString(ResourceLocation resourceLocation, Vector3d vector3d, @Nullable CompoundNBT compoundNBT) {
        String string;
        if (compoundNBT != null) {
            compoundNBT.remove("UUID");
            compoundNBT.remove("Pos");
            compoundNBT.remove("Dimension");
            String string2 = compoundNBT.toFormattedComponent().getString();
            string = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", resourceLocation.toString(), vector3d.x, vector3d.y, vector3d.z, string2);
        } else {
            string = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", resourceLocation.toString(), vector3d.x, vector3d.y, vector3d.z);
        }
        this.setClipboardString(string);
    }

    public void onKeyEvent(long l, int n, int n2, int n3, int n4) {
        if (l == this.mc.getMainWindow().getHandle()) {
            Object object;
            boolean bl;
            if (this.debugCrashKeyPressTime > 0L) {
                if (!InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 67) || !InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 292)) {
                    this.debugCrashKeyPressTime = -1L;
                }
            } else if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 67) && InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 292)) {
                this.actionKeyF3 = true;
                this.debugCrashKeyPressTime = Util.milliTime();
                this.lastDebugCrashWarning = Util.milliTime();
                this.debugCrashWarningsSent = 0L;
            }
            Screen screen = this.mc.currentScreen;
            if (!(this.mc.currentScreen instanceof ControlsScreen) || ((ControlsScreen)screen).time <= Util.milliTime() - 20L) {
                if (n3 == 1) {
                    if (this.mc.currentScreen == null) {
                        venusfr.getInstance().onKeyPressed(n);
                    }
                    if (this.mc.gameSettings.keyBindFullscreen.matchesKey(n, n2)) {
                        this.mc.getMainWindow().toggleFullscreen();
                        this.mc.gameSettings.fullscreen = this.mc.getMainWindow().isFullscreen();
                        this.mc.gameSettings.saveOptions();
                        return;
                    }
                    if (this.mc.gameSettings.keyBindScreenshot.matchesKey(n, n2)) {
                        if (Screen.hasControlDown()) {
                            // empty if block
                        }
                        ScreenShotHelper.saveScreenshot(this.mc.gameDir, this.mc.getMainWindow().getFramebufferWidth(), this.mc.getMainWindow().getFramebufferHeight(), this.mc.getFramebuffer(), this::lambda$onKeyEvent$3);
                        return;
                    }
                } else if (n3 == 0 && this.mc.currentScreen instanceof ControlsScreen) {
                    ((ControlsScreen)this.mc.currentScreen).buttonId = null;
                }
            }
            boolean bl2 = bl = screen == null || !(screen.getListener() instanceof TextFieldWidget) || !((TextFieldWidget)screen.getListener()).canWrite();
            if (n3 != 0 && n == 66 && Screen.hasControlDown() && bl) {
                AbstractOption.NARRATOR.setValueIndex(this.mc.gameSettings, 1);
                if (screen instanceof WithNarratorSettingsScreen) {
                    ((WithNarratorSettingsScreen)screen).func_243317_i();
                }
            }
            if (screen != null) {
                object = new boolean[]{false};
                Screen.wrapScreenError(() -> this.lambda$onKeyEvent$4(n3, (boolean[])object, n, n2, n4, screen), "keyPressed event handler", screen.getClass().getCanonicalName());
                if (object[0]) {
                    return;
                }
            }
            if (this.mc.currentScreen == null || this.mc.currentScreen.passEvents) {
                object = InputMappings.getInputByCode(n, n2);
                if (n3 == 0) {
                    KeyBinding.setKeyBindState((InputMappings.Input)object, false);
                    if (n == 292) {
                        if (this.actionKeyF3) {
                            this.actionKeyF3 = false;
                        } else {
                            this.mc.gameSettings.showDebugInfo = !this.mc.gameSettings.showDebugInfo;
                            this.mc.gameSettings.showDebugProfilerChart = this.mc.gameSettings.showDebugInfo && Screen.hasShiftDown();
                            boolean bl3 = this.mc.gameSettings.showLagometer = this.mc.gameSettings.showDebugInfo && Screen.hasAltDown();
                            if (this.mc.gameSettings.showDebugInfo) {
                                if (this.mc.gameSettings.ofLagometer) {
                                    this.mc.gameSettings.showLagometer = true;
                                }
                                if (this.mc.gameSettings.ofProfiler) {
                                    this.mc.gameSettings.showDebugProfilerChart = true;
                                }
                            }
                        }
                    }
                } else {
                    if (n == 293 && this.mc.gameRenderer != null) {
                        this.mc.gameRenderer.switchUseShader();
                    }
                    boolean bl4 = false;
                    if (this.mc.currentScreen == null) {
                        if (n == 256) {
                            boolean bl5 = InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 292);
                            this.mc.displayInGameMenu(bl5);
                        }
                        bl4 = InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 292) && this.processKeyF3(n);
                        this.actionKeyF3 |= bl4;
                        if (n == 290) {
                            boolean bl6 = this.mc.gameSettings.hideGUI = !this.mc.gameSettings.hideGUI;
                        }
                    }
                    if (bl4) {
                        KeyBinding.setKeyBindState((InputMappings.Input)object, false);
                    } else {
                        KeyBinding.setKeyBindState((InputMappings.Input)object, true);
                        KeyBinding.onTick((InputMappings.Input)object);
                    }
                    if (this.mc.gameSettings.showDebugProfilerChart && n >= 48 && n <= 57) {
                        this.mc.updateDebugProfilerName(n - 48);
                    }
                }
            }
            Reflector.ForgeHooksClient_fireKeyInput.call(n, n2, n3, n4);
        }
    }

    private void onCharEvent(long l, int n, int n2) {
        Screen screen;
        if (l == this.mc.getMainWindow().getHandle() && (screen = this.mc.currentScreen) != null && this.mc.getLoadingGui() == null) {
            if (Character.charCount(n) == 1) {
                Screen.wrapScreenError(() -> this.lambda$onCharEvent$5(n, n2, screen), "charTyped event handler", screen.getClass().getCanonicalName());
            } else {
                for (char c : Character.toChars(n)) {
                    Screen.wrapScreenError(() -> this.lambda$onCharEvent$6(c, n2, screen), "charTyped event handler", screen.getClass().getCanonicalName());
                }
            }
        }
    }

    public void enableRepeatEvents(boolean bl) {
        this.repeatEventsEnabled = bl;
    }

    public void setupCallbacks(long l) {
        InputMappings.setKeyCallbacks(l, this::lambda$setupCallbacks$8, this::lambda$setupCallbacks$10);
    }

    public String getClipboardString() {
        return this.clipboardHelper.getClipboardString(this.mc.getMainWindow().getHandle(), this::lambda$getClipboardString$11);
    }

    public void setClipboardString(String string) {
        this.clipboardHelper.setClipboardString(this.mc.getMainWindow().getHandle(), string);
    }

    public void tick() {
        if (this.debugCrashKeyPressTime > 0L) {
            long l = Util.milliTime();
            long l2 = 10000L - (l - this.debugCrashKeyPressTime);
            long l3 = l - this.lastDebugCrashWarning;
            if (l2 < 0L) {
                if (Screen.hasControlDown()) {
                    NativeUtil.crash();
                }
                throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
            }
            if (l3 >= 1000L) {
                if (this.debugCrashWarningsSent == 0L) {
                    this.printDebugMessage("debug.crash.message", new Object[0]);
                } else {
                    this.printDebugWarning("debug.crash.warning", MathHelper.ceil((float)l2 / 1000.0f));
                }
                this.lastDebugCrashWarning = l;
                ++this.debugCrashWarningsSent;
            }
        }
    }

    private void lambda$getClipboardString$11(int n, long l) {
        if (n != 65545) {
            this.mc.getMainWindow().logGlError(n, l);
        }
    }

    private void lambda$setupCallbacks$10(long l, int n, int n2) {
        this.mc.execute(() -> this.lambda$setupCallbacks$9(l, n, n2));
    }

    private void lambda$setupCallbacks$9(long l, int n, int n2) {
        this.onCharEvent(l, n, n2);
    }

    private void lambda$setupCallbacks$8(long l, int n, int n2, int n3, int n4) {
        this.mc.execute(() -> this.lambda$setupCallbacks$7(l, n, n2, n3, n4));
    }

    private void lambda$setupCallbacks$7(long l, int n, int n2, int n3, int n4) {
        this.onKeyEvent(l, n, n2, n3, n4);
    }

    private void lambda$onCharEvent$6(char c, int n, IGuiEventListener iGuiEventListener) {
        if (!Reflector.ForgeHooksClient_onGuiCharTypedPre.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiCharTypedPre, this.mc.currentScreen, Character.valueOf(c), n)) {
            boolean bl = iGuiEventListener.charTyped(c, n);
            if (Reflector.ForgeHooksClient_onGuiCharTypedPost.exists() && !bl) {
                Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiCharTypedPost, this.mc.currentScreen, Character.valueOf(c), n);
            }
        }
    }

    private void lambda$onCharEvent$5(int n, int n2, IGuiEventListener iGuiEventListener) {
        if (!Reflector.ForgeHooksClient_onGuiCharTypedPre.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiCharTypedPre, this.mc.currentScreen, Character.valueOf((char)n), n2)) {
            boolean bl = iGuiEventListener.charTyped((char)n, n2);
            if (Reflector.ForgeHooksClient_onGuiCharTypedPost.exists() && !bl) {
                Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiCharTypedPost, this.mc.currentScreen, Character.valueOf((char)n), n2);
            }
        }
    }

    private void lambda$onKeyEvent$4(int n, boolean[] blArray, int n2, int n3, int n4, INestedGuiEventHandler iNestedGuiEventHandler) {
        if (!(n == 1 || n == 2 && this.repeatEventsEnabled)) {
            if (n == 0) {
                if (Reflector.ForgeHooksClient_onGuiKeyReleasedPre.exists()) {
                    blArray[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiKeyReleasedPre, this.mc.currentScreen, n2, n3, n4);
                    if (blArray[0]) {
                        return;
                    }
                }
                blArray[0] = iNestedGuiEventHandler.keyReleased(n2, n3, n4);
                if (Reflector.ForgeHooksClient_onGuiKeyReleasedPost.exists() && !blArray[0]) {
                    blArray[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiKeyReleasedPost, this.mc.currentScreen, n2, n3, n4);
                }
            }
        } else {
            if (Reflector.ForgeHooksClient_onGuiKeyPressedPre.exists()) {
                blArray[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiKeyPressedPre, this.mc.currentScreen, n2, n3, n4);
                if (blArray[0]) {
                    return;
                }
            }
            blArray[0] = iNestedGuiEventHandler.keyPressed(n2, n3, n4);
            if (Reflector.ForgeHooksClient_onGuiKeyPressedPost.exists() && !blArray[0]) {
                blArray[0] = Reflector.callBoolean(Reflector.ForgeHooksClient_onGuiKeyPressedPost, this.mc.currentScreen, n2, n3, n4);
            }
        }
    }

    private void lambda$onKeyEvent$3(ITextComponent iTextComponent) {
        this.mc.execute(() -> this.lambda$onKeyEvent$2(iTextComponent));
    }

    private void lambda$onKeyEvent$2(ITextComponent iTextComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
    }

    private void lambda$copyHoveredObject$1(ResourceLocation resourceLocation, Entity entity2, CompoundNBT compoundNBT) {
        this.setEntityClipboardString(resourceLocation, entity2.getPositionVec(), compoundNBT);
        this.printDebugMessage("debug.inspect.server.entity", new Object[0]);
    }

    private void lambda$copyHoveredObject$0(BlockState blockState, BlockPos blockPos, CompoundNBT compoundNBT) {
        this.setBlockClipboardString(blockState, blockPos, compoundNBT);
        this.printDebugMessage("debug.inspect.server.block", new Object[0]);
    }
}

