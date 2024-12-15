package com.alan.clients.event.impl.input;

import com.alan.clients.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;

@Getter
@AllArgsConstructor
public final class GuiKeyBoardEvent extends CancellableEvent {
    private final int keyCode;
    private final char character;
    private final GuiScreen guiScreen;
}