package us.dev.direkt.module.internal.core.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPostReceivePacket;
import us.dev.direkt.event.internal.events.game.render.EventRender2D;
import us.dev.direkt.event.internal.events.system.module.EventUpdateModuleLabel;
import us.dev.direkt.gui.font.CustomFont;
import us.dev.direkt.gui.font.FontManager;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@ModData(label = "InGameUI", category = ModCategory.CORE)
public class InGameUI extends Module {
    public static List<Module> wasEnabled;
    private final Timer potionTimer = new Timer();
    private final Timer slideTimer = new Timer();
    private final Timer pingTimer = new Timer();
    public static List<ToggleableModule> sorted;
    private int initialRenderPos = 3;
    private int pingYPos = -9;
    public static InGameUI INSTANCE;
    private boolean once = true;
    private Map<ToggleableModule, Integer> elementPositions = new HashMap<>();
    private int posY;

    @Listener
    protected Link<EventPostReceivePacket> onPostReceivePacket = new Link<>(event -> {
        /*
		 * Code for getting server TPS and lag (may not be used.).
		 */
        if (!(event.getPacket() instanceof SPacketChat)) {
            this.pingTimer.reset();
        }
    });
    @Listener
    protected Link<EventRender2D> onRender2D = new Link<>(event -> {
        Gui.drawRect(50, 100, 150, 200, 0xBFCE76);
		/*
		 * Fixes a bug with blending
		 */

		/*
		 * Renders "Direkt v1.0" Or whatever in the top right corner
		 */
        if (!Wrapper.getGameSettings().showDebugInfo) {
        	
        	
        	if (this.sorted == null) {
                this.sorted = Direkt.getInstance().getModuleManager().getModules().stream()
                        .filter(ToggleableModule.class::isInstance)
                        .map(ToggleableModule.class::cast)
                        .sorted((one, two) -> FontManager.usingCustomFont ? (CustomFont.INSANCE.getStringWidth(two.getDisplayName()) - CustomFont.INSANCE.getStringWidth(one.getDisplayName())) : Wrapper.getFontRenderer().getStringWidth(two.getDisplayName()) - Wrapper.getFontRenderer().getStringWidth(one.getDisplayName()))
                        .collect(Collectors.toList());
            }
            Wrapper.getFontRenderer().drawStringWithShadow("\247a" + Direkt.getInstance().getClientName() + " \247f" + Direkt.getInstance().getClientVersion(), 2.0f, 2.0f, 16777215);
			/*
			 * This is here because in 1.9 you send significantly less packets
			 * when standing still.
			 */

			/*
			 * Renders and animates the ping in the top left corner (May not be used)
			 */
            if (this.pingTimer.getTime() > (MovementUtils.isMoving(Wrapper.getPlayer()) ? 500 : 1000) && this.pingYPos < 2)
                this.pingYPos++;
            if (this.pingTimer.getTime() < (MovementUtils.isMoving(Wrapper.getPlayer()) ? 500 : 1000) && pingYPos > -Wrapper.getFontRenderer().FONT_HEIGHT)
                this.pingYPos--;
            if (this.pingYPos > -10)
                Wrapper.getFontRenderer().drawStringWithShadow("\247a" + new DecimalFormat("0.0").format((float) this.pingTimer.getTime() / 1000) + "s", Wrapper.getFontRenderer().getStringWidth(Direkt.getInstance().getClientName() + " \247f" + Direkt.getInstance().getClientVersion()) + 5, this.pingYPos, 16777215);

			/*
			 * Render this arraylist.
			 */
            int positionX = Wrapper.getResolution().getScaledWidth();
            int positionY = initialRenderPos;

            for (ToggleableModule module : this.sorted) {
                elementPositions.putIfAbsent(module, -8);
                int renderPos = positionX - elementPositions.get(module) - 3;
                if (module.isRunning()) {
                    Gui.drawRect(renderPos - 3, positionY - 2, Wrapper.getResolution().getScaledWidth(), positionY + Wrapper.getFontRenderer().FONT_HEIGHT + 1, 0x99000000);
                    Wrapper.getFontRenderer().drawStringWithShadow(module.getDisplayName(), renderPos, positionY, module.getColor());
                    if (elementPositions.get(module) < Wrapper.getFontRenderer().getStringWidth(module.getDisplayName())) {
                        if (slideTimer.hasReach(10)) {
                            elementPositions.put(module, elementPositions.get(module) + (Wrapper.getFontRenderer().getStringWidth(module.getDisplayName()) / 12 + 1));
                            slideTimer.reset();
                        }
                    }
                    if (elementPositions.get(module) > Wrapper.getFontRenderer().getStringWidth(module.getDisplayName())) {
                        elementPositions.put(module, elementPositions.get(module) - 1);
                    }
                    positionY += 12;
                } else if (elementPositions.get(module) > -8) {
                    this.posY++;
                    if (slideTimer.hasReach(10)) {
                        elementPositions.put(module, elementPositions.get(module) - (Wrapper.getFontRenderer().getStringWidth(module.getDisplayName()) / 12 + 1));
                        slideTimer.reset();
                    }
                    Gui.drawRect(renderPos - 3, positionY - 2, Wrapper.getResolution().getScaledWidth(), positionY + Wrapper.getFontRenderer().FONT_HEIGHT + 1, 0x99000000);
                    Wrapper.getFontRenderer().drawStringWithShadow(module.getDisplayName(), renderPos, positionY, module.getColor());
                    positionY += 12F;
                }
	        /*
	         * TODO: Reanimate this
	         */

	        /*
	         * Animation for making the arraylist slide down when you get a potion
	         */
                if (this.potionTimer.hasReach(10)) {
                    for (int i = 0; i < (Minecraft.getDebugFPS() > 45 ? 4 : 8); i++) {
                        final Collection<PotionEffect> collection = Wrapper.getPlayer().getActivePotionEffects();
                        Potion potion = null;
                        PotionEffect anaspotion = null;
                        for (final PotionEffect potioneffect : collection) {
                            anaspotion = potioneffect;
                            if(potioneffect.getPotion().hasStatusIcon())
                            	potion = potioneffect.getPotion();
                        }
                        if (collection.size() > 0 && potion.hasStatusIcon()) {
                            if(this.initialRenderPos < 54 && (!potion.isBeneficial() || potion.isBadEffect())) {
                                this.initialRenderPos++;
                            }
                            if(this.initialRenderPos < 28 && potion.isBeneficial()) {
                                this.initialRenderPos++;
                            }
                            if(this.initialRenderPos > 28 && potion.isBeneficial()) {
                                this.initialRenderPos--;
                            }
                        } else if (this.initialRenderPos > 3) {
                            this.initialRenderPos--;
                            this.potionTimer.reset();
                        }
                    }
                }
            }
        }
    }, Link.VERY_LOW_PRIORITY);

    @Listener
    protected Link<EventUpdateModuleLabel> onUpdateModuleLabel = new Link<>(event -> {
        if (sorted != null)
            Collections.sort(sorted, (one, two) -> FontManager.usingCustomFont ? (CustomFont.INSANCE.getStringWidth(two.getDisplayName()) - CustomFont.INSANCE.getStringWidth(one.getDisplayName())) : Wrapper.getFontRenderer().getStringWidth(two.getDisplayName()) - Wrapper.getFontRenderer().getStringWidth(one.getDisplayName()));
    });

}
