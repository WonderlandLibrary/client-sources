package arsenic.module.impl.player;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventDisplayGuiScreen;
import arsenic.event.impl.EventTick;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.rangeproperty.RangeProperty;
import arsenic.module.property.impl.rangeproperty.RangeValue;
import arsenic.utils.font.FontRendererExtension;
import arsenic.utils.render.DrawUtils;
import arsenic.utils.render.RenderUtils;
import arsenic.utils.timer.Timer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "ChestStealer", category = ModuleCategory.Player)
public class ChestStealer extends Module {

    public final RangeProperty startDelay = new RangeProperty("StartDelay", new RangeValue(0, 500, 75, 150, 1));
    public final RangeProperty delay = new RangeProperty("Delay", new RangeValue(0, 500, 75, 150, 1));
    public final BooleanProperty hideGui = new BooleanProperty("HideGui", false);
    public final BooleanProperty closeOnFinish = new BooleanProperty("Close on finish", true);
    @PropertyInfo(reliesOn = "Close on finish", value = "true")
    public final RangeProperty closeDelay = new RangeProperty("Close Delay", new RangeValue(0, 500, 75, 150, 1));
    private boolean inChest;
    private ArrayList<Slot> path = new ArrayList<>();
    private int totalSlots;
    private float percentStolen;
    private final Timer timer = new Timer();
    private ContainerChest chest;

    private Runnable nextAction;
    private final Runnable closeAction = () -> {
        mc.thePlayer.closeScreen();
        inChest = false;
    };

    private final Runnable stealAction = () -> {
        if (path.isEmpty()) {
            if(closeOnFinish.getValue()) {
                timer.setCooldown((int) closeDelay.getValue().getRandomInRange());
                nextAction = closeAction;
            } else {
                inChest = false;
            }
            return;
        }
        percentStolen = (totalSlots - path.size())/(float) (totalSlots);
        mc.theWorld.playSound(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "note.hat", 3f, percentStolen * 2f, false);
        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, path.remove(0).s, 0, 1, mc.thePlayer);
        timer.setCooldown((int) delay.getValue().getRandomInRange());
    };

    private final Runnable startAction = () -> {
        path = generatePath(chest);
        totalSlots = path.size();
        nextAction = stealAction;
    };


    @EventLink
    public final Listener<EventDisplayGuiScreen> eventDisplayScreen = event -> {
        inChest = (event.getGuiScreen()instanceof GuiChest &&mc.thePlayer.openContainer instanceof ContainerChest);;
        if(!inChest)
            return;
        chest = (ContainerChest) mc.thePlayer.openContainer;
        percentStolen = 0;
        path.clear();
        timer.setCooldown((int) startDelay.getValue().getRandomInRange());
        timer.start();
        nextAction = startAction;
    };


    @EventLink
    public final Listener<EventTick> tickListener = event -> {
        if(!inChest)
            return;

        if(timer.hasFinished()) {
            nextAction.run();
            timer.start();
        }
    };

    @Override
    protected void onDisable() {
        inChest = false;
    }

    //below is copied from raven b++ i will review this later
    public ArrayList<Slot> generatePath(ContainerChest chest) {
        ArrayList<Slot> slots = new ArrayList<Slot>();
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (chest.getInventory().get(i) != null)
                slots.add(new Slot(i));
        }
        Slot[] ss = sort(slots.toArray(new Slot[slots.size()]));
        ArrayList<Slot> newSlots = new ArrayList<>();
        Collections.addAll(newSlots, ss);
        return newSlots;
    }

    public static Slot[] sort(Slot[] in) {
        if (in == null || in.length == 0) {
            return in;
        }
        Slot[] out = new Slot[in.length];
        Slot current = in[ThreadLocalRandom.current().nextInt(0, in.length)];
        for (int i = 0; i < in.length; i++) {
            if (i == in.length - 1) {
                out[in.length - 1] = Arrays.stream(in).filter(p -> !p.visited).findAny().orElseGet(null);
                break;
            }
            Slot finalCurrent = current;
            out[i] = finalCurrent;
            finalCurrent.visit();
            Slot next = Arrays.stream(in).filter(p -> !p.visited)
                    .min(Comparator.comparingDouble(p -> p.getDistance(finalCurrent))).get();
            current = next;
        }
        return out;
    }

    private class Slot {
        final int x;
        final int y;
        final int s;
        boolean visited;

        public Slot(int s) {
            this.x = (s + 1) % 10;
            this.y = s / 9;
            this.s = s;
        }

        public double getDistance(Slot s) {
            return Math.abs(this.x - s.x) + Math.abs(this.y - s.y);
        }

        public void visit() {
            visited = true;
        }
    }

    public void draw(GuiContainer container) {
        if (!inChest)
            return;
        FontRendererExtension<?> fr = Nexus.getNexus().getClickGuiScreen().getFontRenderer();
        float textX = (container.width) / 2f;
        float textY = 2 * (container.height / 3f);
        int color = RenderUtils.interpolateColours(new Color(0xFFFF0000), new Color(0xFF00FF00), percentStolen);
        GlStateManager.color(1f, 1f, 1f, 1f);
        RenderUtils.resetColorText();
        String text = "Stealing (press escape to leave)";
        fr.drawStringWithShadow(text, textX, textY, color, fr.CENTREX, fr.CENTREY);
        float fontWidth = fr.getWidth(text);
        float fontHeight = fr.getHeight(text);
        float x1 = textX - fontWidth / 2f;
        float y1 = textY + fontHeight;
        float x2 = x1 + 1 + (fontWidth * percentStolen);
        float y2 = textY + (2 * fontHeight);
        float radius = (y2 - y1);
        x2 = Math.max(x1 + radius, x2);
        DrawUtils.drawRoundedRect(x1, y1, x2, y2, radius, color);
    }

    public boolean isInChest() {
        return inChest;
    }
}
