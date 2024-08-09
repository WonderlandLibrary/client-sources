/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.logic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.util.LinkedList;
import java.util.List;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.ui.ab.logic.AuctionLogic;
import mpp.venusfr.ui.ab.model.ItemStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.item.ItemStack;

public class ActivationLogic {
    private final Minecraft mc;
    public State currentState;
    protected final ItemStorage itemStorage;
    public final List<ItemStack> itemList = new LinkedList<ItemStack>();
    private final AuctionLogic auctionLogic;

    public ActivationLogic(ItemStorage itemStorage, EventBus eventBus) {
        this.itemStorage = itemStorage;
        this.currentState = State.INACTIVE;
        this.auctionLogic = new AuctionLogic(this);
        this.mc = Minecraft.getInstance();
        eventBus.register(this);
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        switch (1.$SwitchMap$mpp$venusfr$ui$ab$logic$ActivationLogic$State[this.currentState.ordinal()]) {
            case 1: {
                this.processActive();
                break;
            }
        }
    }

    private void processActive() {
        Screen screen = this.mc.currentScreen;
        if (screen instanceof ChestScreen) {
            ChestScreen chestScreen = (ChestScreen)screen;
            this.auctionLogic.processBuy(chestScreen);
        }
    }

    public void toggleState() {
        this.currentState = this.currentState == State.ACTIVE ? State.INACTIVE : State.ACTIVE;
    }

    public boolean isActive() {
        return this.currentState == State.ACTIVE;
    }

    public State getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(State state) {
        this.currentState = state;
    }

    public static enum State {
        ACTIVE,
        INACTIVE;

    }
}

