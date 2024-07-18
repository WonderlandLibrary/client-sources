package com.alan.clients.script.api;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GameSettingsAPI extends API {

    public GameSettingsAPI() {
    }

    public boolean isKeyBindForwardDown() {
        return MC.gameSettings.keyBindForward.isKeyDown();
    }

    public boolean isKeyBindBackDown() {
        return MC.gameSettings.keyBindBack.isKeyDown();
    }

    public boolean isKeyBindLeftDown() {
        return MC.gameSettings.keyBindLeft.isKeyDown();
    }

    public boolean isKeyBindRightDown() {
        return MC.gameSettings.keyBindRight.isKeyDown();
    }

    public boolean isKeyBindJumpDown() {
        return MC.gameSettings.keyBindJump.isKeyDown();
    }

    public boolean isKeyBindSneakDown() {
        return MC.gameSettings.keyBindSneak.isKeyDown();
    }

    public boolean isKeyBindAttackDown() {
        return MC.gameSettings.keyBindAttack.isKeyDown();
    }

    public boolean isKeyBindUseItemDown() {
        return MC.gameSettings.keyBindUseItem.isKeyDown();
    }

    public boolean isKeyBindDropDown() {
        return MC.gameSettings.keyBindDrop.isKeyDown();
    }

    public boolean isKeyBindInventoryDown() {
        return MC.gameSettings.keyBindInventory.isKeyDown();
    }

    public boolean isKeyBindChatDown() {
        return MC.gameSettings.keyBindChat.isKeyDown();
    }

    public boolean isKeyBindPlayerListDown() {
        return MC.gameSettings.keyBindPlayerList.isKeyDown();
    }

    public boolean isKeyBindCommandDown() {
        return MC.gameSettings.keyBindCommand.isKeyDown();
    }

    public boolean isKeyBindScreenshotDown() {
        return MC.gameSettings.keyBindScreenshot.isKeyDown();
    }

    public boolean isKeyBindTogglePerspectiveDown() {
        return MC.gameSettings.keyBindTogglePerspective.isKeyDown();
    }

    public boolean isKeyBindSmoothCameraDown() {
        return MC.gameSettings.keyBindSmoothCamera.isKeyDown();
    }

    public boolean isKeyBindFullscreenDown() {
        return MC.gameSettings.keyBindFullscreen.isKeyDown();
    }

    public boolean isKeyBindSpectatorOutlinesDown() {
        return MC.gameSettings.keyBindSpectatorOutlines.isKeyDown();
    }

    public void setKeyBindForward(boolean pressed) {
        MC.gameSettings.keyBindForward.setPressed(pressed);
    }

    public void setKeyBindBack(boolean pressed) {
        MC.gameSettings.keyBindBack.setPressed(pressed);
    }

    public void setKeyBindLeft(boolean pressed) {
        MC.gameSettings.keyBindLeft.setPressed(pressed);
    }

    public void setKeyBindRight(boolean pressed) {
        MC.gameSettings.keyBindRight.setPressed(pressed);
    }

    public void setKeyBindJump(boolean pressed) {
        MC.gameSettings.keyBindJump.setPressed(pressed);
    }

    public void setKeyBindSneak(boolean pressed) {
        MC.gameSettings.keyBindSneak.setPressed(pressed);
    }

    public void setKeyBindAttack(boolean pressed) {
        MC.gameSettings.keyBindAttack.setPressed(pressed);
    }

    public void setKeyBindUseItem(boolean pressed) {
        MC.gameSettings.keyBindUseItem.setPressed(pressed);
    }

    public void setKeyBindDrop(boolean pressed) {
        MC.gameSettings.keyBindDrop.setPressed(pressed);
    }

    public void setKeyBindInventory(boolean pressed) {
        MC.gameSettings.keyBindInventory.setPressed(pressed);
    }

    public void setKeyBindChat(boolean pressed) {
        MC.gameSettings.keyBindChat.setPressed(pressed);
    }

    public void setKeyBindPlayerList(boolean pressed) {
        MC.gameSettings.keyBindPlayerList.setPressed(pressed);
    }

    public void setKeyBindCommand(boolean pressed) {
        MC.gameSettings.keyBindCommand.setPressed(pressed);
    }

    public void setKeyBindScreenshot(boolean pressed) {
        MC.gameSettings.keyBindScreenshot.setPressed(pressed);
    }

    public void setKeyBindTogglePerspective(boolean pressed) {
        MC.gameSettings.keyBindTogglePerspective.setPressed(pressed);
    }

    public void setKeyBindSmoothCamera(boolean pressed) {
        MC.gameSettings.keyBindSmoothCamera.setPressed(pressed);
    }

    public void setKeyBindFullscreen(boolean pressed) {
        MC.gameSettings.keyBindFullscreen.setPressed(pressed);
    }

    public void setKeyBindSpectatorOutlines(boolean pressed) {
        MC.gameSettings.keyBindSpectatorOutlines.setPressed(pressed);
    }
    public boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    public boolean isMouseDown(int button) {
        return Mouse.isButtonDown(button);
    }
}