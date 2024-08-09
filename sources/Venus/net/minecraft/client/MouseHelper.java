/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import mpp.venusfr.events.EventMouseButtonPress;
import mpp.venusfr.events.EventRotate;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.util.MouseSmoother;
import net.minecraft.client.util.NativeUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFWDropCallback;

public class MouseHelper {
    private final Minecraft minecraft;
    private boolean leftDown;
    private boolean middleDown;
    private boolean rightDown;
    private double mouseX;
    private double mouseY;
    private int simulatedRightClicks;
    private int activeButton = -1;
    private boolean ignoreFirstMove = true;
    private int touchScreenCounter;
    private double eventTime;
    private final MouseSmoother xSmoother = new MouseSmoother();
    private final MouseSmoother ySmoother = new MouseSmoother();
    private double xVelocity;
    private double yVelocity;
    private double accumulatedScrollDelta;
    private double lastLookTime = Double.MIN_VALUE;
    private boolean mouseGrabbed;
    EventMouseButtonPress eventMouseButtonPress = new EventMouseButtonPress(1);
    private final EventRotate eventRotate = new EventRotate(0.0, 0.0);

    public MouseHelper(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    private void mouseButtonCallback(long l, int n, int n2, int n3) {
        if (l == this.minecraft.getMainWindow().getHandle()) {
            boolean bl;
            boolean bl2 = bl = n2 == 1;
            if (Minecraft.IS_RUNNING_ON_MAC && n == 0) {
                if (bl) {
                    if ((n3 & 2) == 2) {
                        n = 1;
                        ++this.simulatedRightClicks;
                    }
                } else if (this.simulatedRightClicks > 0) {
                    n = 1;
                    --this.simulatedRightClicks;
                }
            }
            int n4 = n;
            if (bl) {
                if (this.minecraft.gameSettings.touchscreen && this.touchScreenCounter++ > 0) {
                    return;
                }
                this.activeButton = n4;
                this.eventTime = NativeUtil.getTime();
            } else if (this.activeButton != -1) {
                if (this.minecraft.gameSettings.touchscreen && --this.touchScreenCounter > 0) {
                    return;
                }
                this.activeButton = -1;
            }
            boolean[] blArray = new boolean[]{false};
            if (this.minecraft.loadingGui == null) {
                if (this.minecraft.currentScreen == null) {
                    if (!this.mouseGrabbed && bl) {
                        this.grabMouse();
                    }
                } else {
                    double d = this.mouseX * (double)this.minecraft.getMainWindow().getScaledWidth() / (double)this.minecraft.getMainWindow().getWidth();
                    double d2 = this.mouseY * (double)this.minecraft.getMainWindow().getScaledHeight() / (double)this.minecraft.getMainWindow().getHeight();
                    if (bl) {
                        Screen.wrapScreenError(() -> this.lambda$mouseButtonCallback$0(blArray, d, d2, n4), "mouseClicked event handler", this.minecraft.currentScreen.getClass().getCanonicalName());
                    } else {
                        Screen.wrapScreenError(() -> this.lambda$mouseButtonCallback$1(blArray, d, d2, n4), "mouseReleased event handler", this.minecraft.currentScreen.getClass().getCanonicalName());
                    }
                }
            }
            if (!blArray[0] && (this.minecraft.currentScreen == null || this.minecraft.currentScreen.passEvents) && this.minecraft.loadingGui == null) {
                if (n4 == 0) {
                    this.leftDown = bl;
                } else if (n4 == 2) {
                    this.middleDown = bl;
                } else if (n4 == 1) {
                    this.rightDown = bl;
                }
                KeyBinding.setKeyBindState(InputMappings.Type.MOUSE.getOrMakeInput(n4), bl);
                if (bl) {
                    venusfr.getInstance().onKeyPressed(-100 + n4);
                    this.eventMouseButtonPress.setButton(n4);
                    venusfr.getInstance().getEventBus().post(this.eventMouseButtonPress);
                    if (this.minecraft.player.isSpectator() && n4 == 2) {
                        this.minecraft.ingameGUI.getSpectatorGui().onMiddleClick();
                    } else {
                        KeyBinding.onTick(InputMappings.Type.MOUSE.getOrMakeInput(n4));
                    }
                }
            }
        }
    }

    private void scrollCallback(long l, double d, double d2) {
        if (l == Minecraft.getInstance().getMainWindow().getHandle()) {
            double d3 = (this.minecraft.gameSettings.discreteMouseScroll ? Math.signum(d2) : d2) * this.minecraft.gameSettings.mouseWheelSensitivity;
            if (this.minecraft.loadingGui == null) {
                if (this.minecraft.currentScreen != null) {
                    double d4 = this.mouseX * (double)this.minecraft.getMainWindow().getScaledWidth() / (double)this.minecraft.getMainWindow().getWidth();
                    double d5 = this.mouseY * (double)this.minecraft.getMainWindow().getScaledHeight() / (double)this.minecraft.getMainWindow().getHeight();
                    this.minecraft.currentScreen.mouseScrolled(d4, d5, d3);
                } else if (this.minecraft.player != null) {
                    if (this.accumulatedScrollDelta != 0.0 && Math.signum(d3) != Math.signum(this.accumulatedScrollDelta)) {
                        this.accumulatedScrollDelta = 0.0;
                    }
                    this.accumulatedScrollDelta += d3;
                    float f = (int)this.accumulatedScrollDelta;
                    if (f == 0.0f) {
                        return;
                    }
                    this.accumulatedScrollDelta -= (double)f;
                    if (this.minecraft.player.isSpectator()) {
                        if (this.minecraft.ingameGUI.getSpectatorGui().isMenuActive()) {
                            this.minecraft.ingameGUI.getSpectatorGui().onMouseScroll(-f);
                        } else {
                            float f2 = MathHelper.clamp(this.minecraft.player.abilities.getFlySpeed() + f * 0.005f, 0.0f, 0.2f);
                            this.minecraft.player.abilities.setFlySpeed(f2);
                        }
                    } else {
                        this.minecraft.player.inventory.changeCurrentItem(f);
                    }
                }
            }
        }
    }

    private void addPacksToScreen(long l, List<Path> list) {
        if (this.minecraft.currentScreen != null) {
            this.minecraft.currentScreen.addPacks(list);
        }
    }

    public void registerCallbacks(long l) {
        InputMappings.setMouseCallbacks(l, this::lambda$registerCallbacks$3, this::lambda$registerCallbacks$5, this::lambda$registerCallbacks$7, this::lambda$registerCallbacks$9);
    }

    private void cursorPosCallback(long l, double d, double d2) {
        if (l == Minecraft.getInstance().getMainWindow().getHandle()) {
            Screen screen;
            if (this.ignoreFirstMove) {
                this.mouseX = d;
                this.mouseY = d2;
                this.ignoreFirstMove = false;
            }
            if ((screen = this.minecraft.currentScreen) != null && this.minecraft.loadingGui == null) {
                double d3 = d * (double)this.minecraft.getMainWindow().getScaledWidth() / (double)this.minecraft.getMainWindow().getWidth();
                double d4 = d2 * (double)this.minecraft.getMainWindow().getScaledHeight() / (double)this.minecraft.getMainWindow().getHeight();
                Screen.wrapScreenError(() -> MouseHelper.lambda$cursorPosCallback$10(screen, d3, d4), "mouseMoved event handler", screen.getClass().getCanonicalName());
                if (this.activeButton != -1 && this.eventTime > 0.0) {
                    double d5 = (d - this.mouseX) * (double)this.minecraft.getMainWindow().getScaledWidth() / (double)this.minecraft.getMainWindow().getWidth();
                    double d6 = (d2 - this.mouseY) * (double)this.minecraft.getMainWindow().getScaledHeight() / (double)this.minecraft.getMainWindow().getHeight();
                    Screen.wrapScreenError(() -> this.lambda$cursorPosCallback$11(screen, d3, d4, d5, d6), "mouseDragged event handler", screen.getClass().getCanonicalName());
                }
            }
            this.minecraft.getProfiler().startSection("mouse");
            if (this.isMouseGrabbed() && this.minecraft.isGameFocused()) {
                this.xVelocity += d - this.mouseX;
                this.yVelocity += d2 - this.mouseY;
            }
            this.updatePlayerLook();
            this.mouseX = d;
            this.mouseY = d2;
            this.minecraft.getProfiler().endSection();
        }
    }

    public void updatePlayerLook() {
        double d = NativeUtil.getTime();
        double d2 = d - this.lastLookTime;
        this.lastLookTime = d;
        if (this.isMouseGrabbed() && this.minecraft.isGameFocused()) {
            double d3;
            double d4;
            double d5 = this.minecraft.gameSettings.mouseSensitivity * (double)0.6f + (double)0.2f;
            double d6 = d5 * d5 * d5 * 8.0;
            if (this.minecraft.gameSettings.smoothCamera) {
                double d7 = this.xSmoother.smooth(this.xVelocity * d6, d2 * d6);
                double d8 = this.ySmoother.smooth(this.yVelocity * d6, d2 * d6);
                d4 = d7;
                d3 = d8;
            } else {
                this.xSmoother.reset();
                this.ySmoother.reset();
                d4 = this.xVelocity * d6;
                d3 = this.yVelocity * d6;
            }
            this.xVelocity = 0.0;
            this.yVelocity = 0.0;
            int n = 1;
            if (this.minecraft.gameSettings.invertMouse) {
                n = -1;
            }
            this.minecraft.getTutorial().onMouseMove(d4, d3);
            if (this.minecraft.player != null) {
                this.eventRotate.setYaw(d4);
                this.eventRotate.setPitch(d3 * (double)n);
                venusfr.getInstance().getEventBus().post(this.eventRotate);
                if (!this.eventRotate.isCancel()) {
                    this.minecraft.player.rotateTowards(this.eventRotate.getYaw(), this.eventRotate.getPitch());
                }
                if (this.eventRotate.isCancel()) {
                    this.eventRotate.open();
                }
            }
        } else {
            this.xVelocity = 0.0;
            this.yVelocity = 0.0;
        }
    }

    public boolean isLeftDown() {
        return this.leftDown;
    }

    public boolean isRightDown() {
        return this.rightDown;
    }

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }

    public void setIgnoreFirstMove() {
        this.ignoreFirstMove = true;
    }

    public boolean isMouseGrabbed() {
        return this.mouseGrabbed;
    }

    public void grabMouse() {
        if (this.minecraft.isGameFocused() && !this.mouseGrabbed) {
            if (!Minecraft.IS_RUNNING_ON_MAC) {
                KeyBinding.updateKeyBindState();
            }
            this.mouseGrabbed = true;
            this.mouseX = this.minecraft.getMainWindow().getWidth() / 2;
            this.mouseY = this.minecraft.getMainWindow().getHeight() / 2;
            InputMappings.setCursorPosAndMode(this.minecraft.getMainWindow().getHandle(), 212995, this.mouseX, this.mouseY);
            this.minecraft.displayGuiScreen(null);
            this.minecraft.leftClickCounter = 10000;
            this.ignoreFirstMove = true;
        }
    }

    public void ungrabMouse() {
        if (this.mouseGrabbed) {
            this.mouseGrabbed = false;
            this.mouseX = this.minecraft.getMainWindow().getWidth() / 2;
            this.mouseY = this.minecraft.getMainWindow().getHeight() / 2;
            InputMappings.setCursorPosAndMode(this.minecraft.getMainWindow().getHandle(), 212993, this.mouseX, this.mouseY);
        }
    }

    public void ignoreFirstMove() {
        this.ignoreFirstMove = true;
    }

    private void lambda$cursorPosCallback$11(IGuiEventListener iGuiEventListener, double d, double d2, double d3, double d4) {
        iGuiEventListener.mouseDragged(d, d2, this.activeButton, d3, d4);
    }

    private static void lambda$cursorPosCallback$10(IGuiEventListener iGuiEventListener, double d, double d2) {
        iGuiEventListener.mouseMoved(d, d2);
    }

    private void lambda$registerCallbacks$9(long l, int n, long l2) {
        Path[] pathArray = new Path[n];
        for (int i = 0; i < n; ++i) {
            pathArray[i] = Paths.get(GLFWDropCallback.getName(l2, i), new String[0]);
        }
        this.minecraft.execute(() -> this.lambda$registerCallbacks$8(l, pathArray));
    }

    private void lambda$registerCallbacks$8(long l, Path[] pathArray) {
        this.addPacksToScreen(l, Arrays.asList(pathArray));
    }

    private void lambda$registerCallbacks$7(long l, double d, double d2) {
        this.minecraft.execute(() -> this.lambda$registerCallbacks$6(l, d, d2));
    }

    private void lambda$registerCallbacks$6(long l, double d, double d2) {
        this.scrollCallback(l, d, d2);
    }

    private void lambda$registerCallbacks$5(long l, int n, int n2, int n3) {
        this.minecraft.execute(() -> this.lambda$registerCallbacks$4(l, n, n2, n3));
    }

    private void lambda$registerCallbacks$4(long l, int n, int n2, int n3) {
        this.mouseButtonCallback(l, n, n2, n3);
    }

    private void lambda$registerCallbacks$3(long l, double d, double d2) {
        this.minecraft.execute(() -> this.lambda$registerCallbacks$2(l, d, d2));
    }

    private void lambda$registerCallbacks$2(long l, double d, double d2) {
        this.cursorPosCallback(l, d, d2);
    }

    private void lambda$mouseButtonCallback$1(boolean[] blArray, double d, double d2, int n) {
        blArray[0] = this.minecraft.currentScreen.mouseReleased(d, d2, n);
    }

    private void lambda$mouseButtonCallback$0(boolean[] blArray, double d, double d2, int n) {
        blArray[0] = this.minecraft.currentScreen.mouseClicked(d, d2, n);
    }
}

