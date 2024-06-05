package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import zamorozka.module.Module;

import java.util.ArrayList;
import java.util.List;

public class ClickTask implements Task {

    protected final TimerHelper timer = new TimerHelper();

    protected final List<int[]> clickData = new ArrayList<int[]>();

    protected final Module mod;

    protected boolean running = false;

    protected int delay = 120;

    public ClickTask(Module mod) {
        this.mod = mod;
    }

    @Override
    public void onTaskCancelled() {

    }

    @Override
    public void onPreUpdate() {
        if (hasClicks()) {
            if (timer.hasReached(delay)) {
                int[] click = clickData.get(0);
                if (click.length > 3)
                    clickSlot(click[0], click[1], click[2], click[3]);
                else
                    clickSlot(Minecraft.getMinecraft().player.inventoryContainer.windowId, click[0], click[1], click[2]);
                clickData.remove(click);
                timer.reset();
            }
        } else
            timer.reset();
    }

    /**
     * Performs the slot click.
     * */
    private void clickSlot(int windowId, int slot, int mouse, int shift) {
        Minecraft.getMinecraft().playerController.windowClick(windowId, slot, mouse, shift == 0 ? ClickType.PICKUP : ClickType.QUICK_MOVE, Minecraft.getMinecraft().player);
    }

    /**
     * @return True if the task contains window clicks.
     * */
    public boolean hasClicks() {
        return !clickData.isEmpty();
    }

    @Override
    public void onPostUpdate() {

    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public Module getMod() {
        return mod;
    }

    /**
     * Adds clicks into the queue of clicks.
     * */
    public void add(int slot, int mouse, int shift) {
        clickData.add(new int[] { slot, mouse, shift });
    }

    /**
     * Adds clicks into the queue of clicks with a custom window id.
     * */
    public void add(int windowId, int slot, int mouse, int shift) {
        clickData.add(new int[] { windowId, slot, mouse, shift });
    }

    /**
     * @return True if the task already contains that window click.
     * */
    public boolean containsClick(int slot, int mouse, int shift) {
        for (int[] click : clickData) {
            if (click.length == 3 && click[0] == slot && click[1] == mouse && click[2] == shift) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return True if the task already contains that window click.
     * */
    public boolean containsClick(int windowId, int slot, int mouse, int shift) {
        for (int[] click : clickData) {
            if (click.length == 4 && click[0] == windowId && click[1] == slot && click[2] == mouse && click[3] == shift) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the window clicks.
     * */
    public void clearClicks() {
        clickData.clear();
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}