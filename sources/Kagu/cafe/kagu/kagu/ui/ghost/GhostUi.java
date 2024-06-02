/**
 * 
 */
package cafe.kagu.kagu.ui.ghost;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.opengl.Display;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.eventBus.impl.EventRenderObs;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.ghost.ModObsProofUi;
import cafe.kagu.kagu.mods.impl.move.ModNoSlow;
import cafe.kagu.kagu.utils.OSUtil;
import net.minecraft.client.Minecraft;

/**
 * @author DistastefulBannock
 *
 */
public class GhostUi extends JFrame {
	
	private static final long serialVersionUID = -4192554565787800580L;
	private static Minecraft mc = Minecraft.getMinecraft();
	private static GhostUi ghostUi = null;
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		if (!OSUtil.isWindows())
			return;
		
		ghostUi = new GhostUi();
		DrawPane drawPane = new DrawPane();
		ghostUi.setContentPane(drawPane);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		// Do our event
		new Timer("Ghost UI Event", true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				GhostUi ghostUi = GhostUi.ghostUi;
				if ((ghostUi.getWidth() == 0 && ghostUi.getHeight() == 0) || !ghostUi.isVisible())
					return;
				// Paint changes
				drawPane.repaint(0);
				drawPane.paintImmediately(0, 0, ghostUi.getWidth(), ghostUi.getHeight());
				toolkit.sync();
			}
		}, 0, 1);
		
	}
	
	public GhostUi() {
		
		// Makes the window not appear in the task bar
		setType(Type.UTILITY);
		
		// Makes the window invisible
		setUndecorated(true);
		setBackground(new Color(255, 255, 255, 0));
		
		// Makes the window always render on top of everything
		setAlwaysOnTop(true);
		
		// Make it so you cannot focus into the window
		setFocusable(false);
		
		// Sets more stuff that will be overridden later
		setSize(0, 0);
		setLocation(0, 0);
		
		// Makes window visible
		setVisible(true);
		
		// Makes mouse events fall through the window (totally not skidded from https://github.com/aeris170/Crosshair-Overlay/blob/57b4316d4ebf4dd671f53febd88384a845dc03b8/src/xhair/Overlay.java#L157)
		HWND handleToWindow = new HWND(); // Create a new useless window handle
		handleToWindow.setPointer(Native.getComponentPointer(this)); // Get the pointer for our jframe, then put it into our now useful window handle
		int windowLong = User32.INSTANCE.GetWindowLong(handleToWindow, WinUser.GWL_EXSTYLE); // Normally returns a dword, but that's the size of an int so we use int idfk
		windowLong = windowLong | WinUser.WS_EX_LAYERED /* Makes it layered or smth idk too lazy to read up on it */ | WinUser.WS_EX_TRANSPARENT /* Makes mouse events fall through the window */;
		User32.INSTANCE.SetWindowLong(handleToWindow, WinUser.GWL_EXSTYLE, windowLong); // Set the window long with our changes
		
		// Register this class to the event bus
		Kagu.getEventBus().subscribe(this);
		
	}
	
	@EventHandler
	private Handler<EventCheatRenderTick> onCheatRenderTick = e -> {
		if (e.isPost() || ghostUi == null || mc == null || !Display.isCreated())
			return;
		GhostUi ghostUi = GhostUi.ghostUi;
		ModObsProofUi modObsProofUi = Kagu.getModuleManager().getModule(ModObsProofUi.class);
		
		// If the game isn't in focus or the obs proof ui is disabled then don't render overlay
		if (!Display.isActive() || modObsProofUi.isDisabled()) {
			if (ghostUi.isVisible())
				ghostUi.setVisible(false);
			return;
		}
		
		if (!ghostUi.isVisible())
			ghostUi.setVisible(true);
		
		// Set the overlay size and position
		ghostUi.setSize(mc.displayWidth - 1, mc.displayHeight);
		ghostUi.setLocation(Display.getX() + modObsProofUi.getOffsetX().getValue(), Display.getY() + modObsProofUi.getOffsetY().getValue());
		
	};
	
	private static class DrawPane extends JComponent{
		
		private static final long serialVersionUID = 4424436145565545290L;
		
		private Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		@Override
		public boolean isLightweight() {
			return true;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphics2d = (Graphics2D)g;
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			GhostUi ghostUi = GhostUi.ghostUi;
			// Kagu hook
			{
				EventRenderObs eventRenderObs = new EventRenderObs(EventPosition.PRE, graphics2d, ghostUi);
				eventRenderObs.post();
				if (eventRenderObs.isCanceled()) {
					toolkit.sync();
					return;
				}
			}
			
			// Kagu hook
			{
				EventRenderObs eventRenderObs = new EventRenderObs(EventPosition.POST, graphics2d, ghostUi);
				eventRenderObs.post();
			}
			toolkit.sync();
		}
		
	}
	
}
