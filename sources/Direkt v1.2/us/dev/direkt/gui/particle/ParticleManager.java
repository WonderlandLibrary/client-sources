package us.dev.direkt.gui.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;

import us.dev.direkt.Wrapper;
import us.dev.direkt.gui.minecraft.override.GuiDirektMainMenu;

public class ParticleManager {

	private List<Particle> activeParticles = new CopyOnWriteArrayList<>();
	private GuiDirektMainMenu menu;
	private boolean shouldSpawn = true;

	public ParticleManager(GuiDirektMainMenu menu) {
		this.menu = menu;
	}

	public void init(int width, int height) {}

	public void destroy() {
		this.activeParticles.clear();
	}

	public void render() {
		if(menu == null)
			throw new IllegalStateException("ANAS ANAS ANAS");

		for(Particle p : activeParticles) {
			p.updateAndRender(menu);
		}
		if(Mouse.isButtonDown(2)) {
			destroy();
		}
		if(Mouse.isButtonDown(0) && activeParticles.size() < 2000) {
			for(int i = 0; i < 2; i++) {
				this.addParticle(Mouse.getX()/2, menu.height-Mouse.getY()/2, 2, 2, false);
			}
		}
		for(int i = 0; i < Wrapper.getMinecraft().displayWidth / 40; i++) {
			int rando = (int) Math.floor(Math.random() * 50);
			if(rando >= 70) {
				rando = -rando;
			}
		this.addParticle(i * 40 - 40, 0, rando, 3, true);
	}
		this.addParticle(5, 4, (int) Math.floor(Math.random() * 50), 3, true);
		menu.drawRect(0, 0, 0, 0, 0xFFFFFFFF);
	}

	/**
	 * Distance formula from two points on a plane
	 */
	public static double getDistance(double p1, double p2, double p3, double p4) {
		double delta1 = p3 - p1;
		double delta2 = p4 - p2;
		
		return Math.sqrt(delta1 * delta1 + delta2 * delta2);
	}
	
	/**
	 * Add a particle with the inputted coordinates
	 * @param x - x coord
	 * @param z - z coord OR Y WHATEVER
	 * @param floorRemove 
	 */
	public void addParticle(int x, int z, int motionX, int motionY, boolean floorRemove) {
		activeParticles.add(new Particle(this, x, z, motionX, motionY, floorRemove));
	}

	public List<Particle> getParticles() {
		return this.activeParticles;
	}
}
