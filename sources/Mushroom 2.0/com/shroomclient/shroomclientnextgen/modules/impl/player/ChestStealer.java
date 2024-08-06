package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.mixin.GenericContainerScreenHandlerAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.InventoryMove;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.InvManagerUtil;
import java.util.ArrayList;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Stealer",
    uniqueId = "stealer",
    description = "Auto Steals Items From Chests",
    category = ModuleCategory.Player
)
public class ChestStealer extends Module {

    @ConfigOption(
        name = "Name Check",
        description = "Checks To See If The Name Of The Chest is normal, And Not A Wierd GUI",
        order = 6
    )
    public static Boolean nameCheck = true;

    @ConfigOption(
        name = "Delay Before Steal",
        description = "Delay Before Start Stealing",
        max = 100,
        order = 1
    )
    public Integer DelayBeforeSteal = 0;

    @ConfigOption(
        name = "Delay Between Items",
        description = "Delay Between Throwing Out Each Item",
        max = 500,
        order = 2
    )
    public Integer DelayBetweenItems = 0;

    @ConfigParentId("close")
    @ConfigOption(
        name = "Auto close",
        description = "Automatically Closes The Chest After Finished",
        order = 3
    )
    public Boolean autoclose = true;

    @ConfigChild("close")
    @ConfigOption(
        name = "Delay Before Close",
        description = "Delay Before Closing Gui, In Ticks",
        min = 0,
        max = 500,
        order = 4
    )
    public Integer DelayBeforeClose = 0;

    @ConfigOption(
        name = "Don't Steal Junk",
        description = "Doesn't Take Useless Items",
        order = 5
    )
    public Boolean stealJunk = true;

    int i = 0;
    long close = 0;
    long openTime = 0;
    boolean open = false;
    Vec3d motion;
    long time;

    @Override
    protected void onEnable() {
        i = 0;
        close = 0;
    }

    @Override
    protected void onDisable() {}

    // motion event so i can set yaw and pitch, got an idea for later
    @SubscribeEvent
    public void onClientTick(MotionEvent.Pre e) {
        if (motion != null) {
            C.p().setVelocity(motion);
            motion = null;
        }

        if (
            C.mc.currentScreen instanceof GenericContainerScreen s &&
            ((s.getTitle().withoutStyle().get(0).getLiteralString() != null &&
                    s
                        .getTitle()
                        .withoutStyle()
                        .get(0)
                        .getLiteralString()
                        .toLowerCase()
                        .contains("chest")) ||
                !nameCheck)
        ) {
            if (!open) {
                openTime = System.currentTimeMillis();
                open = true;
            }

            if (
                close == 0 &&
                (System.currentTimeMillis() - openTime >= DelayBeforeSteal &&
                    openTime != 0)
            ) {
                Inventory inv =
                    ((GenericContainerScreenHandlerAccessor) s.getScreenHandler()).getInventory();

                while (delayPassed() && i < inv.size()) {
                    while (i <= inv.size() && (isStackEmpty(i, inv))) i++;

                    ArrayList<ItemStack> stacks = new ArrayList<>();
                    for (int ii = 0; ii < C.p().getInventory().size(); ii++) {
                        ItemStack theS = C.p().getInventory().getStack(ii);
                        if (theS != null) stacks.add(theS);
                    }
                    for (int ii = 0; ii < inv.size(); ii++) {
                        ItemStack theS = inv.getStack(ii);
                        if (theS != null) stacks.add(theS);
                    }
                    if (
                        (InvManagerUtil.isJunk(inv.getStack(i)) ||
                            !InvManagerUtil.isBetter(
                                null,
                                inv.getStack(i),
                                stacks
                            )) &&
                        !stealJunk
                    ) {
                        i++;
                        continue;
                    }

                    if (i < inv.size()) {
                        if (
                            ModuleManager.isEnabled(InventoryMove.class) &&
                            InventoryMove.pauseMotion
                        ) {
                            C.p().setVelocity(0, C.p().getVelocity().y, 0);
                        }
                        shiftClick(i);
                    }

                    i++;
                }

                if (autoclose && i >= inv.size()) {
                    close = System.currentTimeMillis();
                }
            } else {
                if (
                    System.currentTimeMillis() - close >= DelayBeforeClose &&
                    close != 0
                ) {
                    C.mc.currentScreen.close();
                    C.mc.mouse.lockCursor();
                }
            }
        } else {
            close = 0;
            i = 0;
            open = false;
            openTime = 0;
        }
    }

    private boolean delayPassed() {
        return System.currentTimeMillis() - time >= DelayBetweenItems;
    }

    public void shiftClick(int slot) {
        time = System.currentTimeMillis();
        C.mc.interactionManager.clickSlot(
            C.p().currentScreenHandler.syncId,
            slot,
            0,
            SlotActionType.QUICK_MOVE,
            C.p()
        );
    }

    public boolean isStackEmpty(int i, Inventory inv) {
        return inv.getStack(i).toString().contains("air");
    }
}
