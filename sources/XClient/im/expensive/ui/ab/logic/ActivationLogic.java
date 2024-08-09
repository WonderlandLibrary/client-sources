package im.expensive.ui.ab.logic;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import im.expensive.ui.ab.model.ItemStorage;
import im.expensive.events.EventUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivationLogic {
    final Minecraft mc;
    @Getter
    @Setter
    public State currentState;
    protected final ItemStorage itemStorage;
    public final List<ItemStack> itemList = new LinkedList<>();
    private final AuctionLogic auctionLogic;

    public ActivationLogic(ItemStorage itemStorage, EventBus eventBus) {
        this.itemStorage = itemStorage;
        this.currentState = State.INACTIVE;
        this.auctionLogic = new AuctionLogic(this);
        this.mc = Minecraft.getInstance();
        eventBus.register(this);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        switch (currentState) {
            case ACTIVE:
                processActive();
                break;
            case INACTIVE:
                break;
        }
    }

    private void processActive() {
        if (mc.currentScreen instanceof ChestScreen chestScreen) {
            auctionLogic.processBuy(chestScreen);
        }
    }

    public void toggleState() {
        currentState = (currentState == State.ACTIVE) ? State.INACTIVE : State.ACTIVE;
    }

    public boolean isActive() {
        return currentState == State.ACTIVE;
    }

    public enum State {
        ACTIVE,
        INACTIVE
    }
}
