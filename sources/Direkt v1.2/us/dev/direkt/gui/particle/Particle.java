package us.dev.direkt.gui.particle;

import java.awt.Window;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import us.dev.direkt.Wrapper;
import us.dev.direkt.gui.minecraft.override.GuiDirektMainMenu;

public class Particle {

	private ParticleManager particleManager;

	private int x, y;
	private double motionX, motionY;

	private boolean floorRemove;

	public Particle(ParticleManager particle, int x, int y, int motionX, int motionY, boolean floorRemove) {
		this.x = x;
		this.y = y;
		this.motionX = motionX;
		this.motionY = motionY;
		this.particleManager = particle;
		this.floorRemove = floorRemove;
	}

	private int lastDisplayX, lastDisplayY;

	/**
	 * Update particle logic (gravity and collision)
	 * @param menu
	 */
	private void update(GuiDirektMainMenu menu) {
		int height = menu.height - 1;
		int width = menu.width;
		if(this.floorRemove) {
		if(y >= Wrapper.getMinecraft().displayHeight / 2 || checkCollision(Integer.MAX_VALUE, height, (int) (x + motionX), (int) (y + motionY))) {
			particleManager.getParticles().remove(this);
		}
	}
		motionY += 1;
	
		if(this.lastDisplayX != 0 && this.lastDisplayY != 0) {
			motionX += (Display.getX() - this.lastDisplayX) / 4;
			motionY += (Display.getY() - this.lastDisplayY) / 4;
		}

		this.lastDisplayX = Display.getX();
		this.lastDisplayY = Display.getY();

		if(Mouse.isButtonDown(1)) {
			int mouseX = Mouse.getX()/2;
			int mouseY = height-Mouse.getY()/2;

			motionX -= (x - mouseX) / 3;
			motionY -= (y - mouseY) / 10;
		}

		if(!checkCollision(width, height, (int) (x + motionX), (int) (y + motionY))) {
			x += (motionX);
			y += (motionY);
		} else {
			motionY = -motionY*0.1;
			motionX = -motionX*0.5;
		}
	}

	/**
	 * Check if the particle will collide with something with the provided coordinates
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkCollision(int width, int height, int x, int y) {
		for(Particle p : this.particleManager.getParticles()) {
			if(p != this) {
				if(p.getX() == x && p.getY() == y) {
					return true;
				}
			}
		}

		if(x > width / 2 - 100 && x < width / 2 + 100 && y > height / 4 + 47 && y < height / 4 + 119) {
			return true;
		}

		if(x > width / 2 - 124 && x < width / 2 + 2 + 98 && y > height / 4 + 48 + 72 + 12 && y < height / 4 + 48 + 72 + 12 + 20) {
			return true;
		}

		return x < 0 || y < 0 || x > width || y > height;
	}

	/**
	 * Check if the particle is on top of something
	 * @param height
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean checkOn(int height, int x, int y) {
		for(Particle p : this.particleManager.getParticles()) {
			if(p != this) {
				if(p.getX() == x && p.getY() + 1 == y) {
					return true;
				}
			}
		}

		return y+1 == height;
	}

	public double getMotionX() {
		return this.motionX;
	}

	public double getMotionY() {
		return this.motionY;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Render the particle on the screen
	 * @param menu - the menu it is rendered on
	 */
	private void render(GuiDirektMainMenu menu) {
		menu.drawRect(x, y, x+1, y+1, 0xFF63C22F);		
	}

	/**
	 * Updates and then renders the particles
	 * anas method
	 * @param menu - the menu it will be rendered on
	 */
	public void updateAndRender(GuiDirektMainMenu menu) {
		this.update(menu);
		this.render(menu);
	}
}
