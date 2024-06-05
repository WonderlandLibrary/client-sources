package zamorozka.modules.ZAMOROZKA;

import java.net.URI;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class PashalkaXDDD extends Module {

	public PashalkaXDDD() {
		super("лив в кт", 0, Category.Zamorozka);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		 try {
             java.awt.Desktop.getDesktop().browse(URI.create("https://youtu.be/dQw4w9WgXcQ"));
         } catch (Exception e) {}
	}
}