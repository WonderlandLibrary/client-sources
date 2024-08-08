package librarys.org.newdawn.slick.tests;

import librarys.org.newdawn.slick.*;
import librarys.org.newdawn.slick.AppGameContainer;
import librarys.org.newdawn.slick.BasicGame;
import librarys.org.newdawn.slick.Color;
import librarys.org.newdawn.slick.GameContainer;
import librarys.org.newdawn.slick.Graphics;
import librarys.org.newdawn.slick.Image;
import librarys.org.newdawn.slick.SlickException;
import librarys.org.newdawn.slick.geom.Polygon;
import librarys.org.newdawn.slick.geom.Rectangle;
import librarys.org.newdawn.slick.geom.ShapeRenderer;
import librarys.org.newdawn.slick.geom.TexCoordGenerator;
import librarys.org.newdawn.slick.geom.Vector2f;

/**
 * Test to emulate texture paint
 * 
 * @author kevin
 */
public class TexturePaintTest extends BasicGame {
	/** The poly being drawn */
	private Polygon poly = new Polygon();
	/** The image being textured */
	private Image image;
	
	/** The texture paint rectangle */
	private Rectangle texRect = new Rectangle(50,50,100,100);
	/** The texture paint */
	private TexCoordGenerator texPaint;
	
	/**
	 * Create the test
	 */
	public TexturePaintTest() {
		super("Texture Paint Test");
	}
	
	/**
	 * @see BasicGame#init(GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		poly.addPoint(120, 120);
		poly.addPoint(420, 100);
		poly.addPoint(620, 420);
		poly.addPoint(300, 320);
	
		image = new Image("testdata/rocks.png");
		
		texPaint = new TexCoordGenerator() {
			public Vector2f getCoordFor(float x, float y) {
				float tx = (texRect.getX() - x) / texRect.getWidth();
				float ty = (texRect.getY() - y) / texRect.getHeight();
				
				return new Vector2f(tx,ty);
			}
		};
	}

	/**
	 * @see BasicGame#update(GameContainer, int)
	 */
	public void update(GameContainer container, int delta) throws SlickException {
	}

	/**
	 * @see Game#render(GameContainer, Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.texture(poly, image);
		
		ShapeRenderer.texture(poly, image, texPaint);
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv The arguments to pass into the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new TexturePaintTest());
			container.setDisplayMode(800,600,false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
