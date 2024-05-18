package org.newdawn.slick;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import org.newdawn.slick.opengl.GLUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.BufferedImageUtil;

import com.kilo.render.Format;
import com.kilo.util.ChatUtil;

/**
 * A TrueType font implementation for Slick
 * 
 * @author James Chambers (Jimmy)
 * @author Jeremy Adams (elias4444)
 * @author Kevin Glass (kevglass)
 * @author Peter Korzuszek (genail)
 */
public class TrueTypeFont implements org.newdawn.slick.Font {
	/** The renderer to use for all GL operations */
	private static final SGL GL = Renderer.get();

	/** Array that holds necessary information about the font characters */
	private IntObject[] charArray = new IntObject[256];
	
	/** Map of user defined font characters (Character <-> IntObject) */
	private Map customChars = new HashMap();

	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias;

	/** Font's size */
	private int fontSize = 0;

	/** Font's height */
	private int fontHeight = 0;

	/** Texture used to cache the font 0-255 characters */
	private Texture fontTexture;
	
	/** Default font texture width */
	private int textureWidth = 512;

	/** Default font texture height */
	private int textureHeight = 512;

	/** A reference to Java's AWT Font that we create our font texture from */
	private java.awt.Font font;

	/** The font metrics for our Java AWT font */
	private FontMetrics fontMetrics;

	/**
	 * This is a special internal class that holds our necessary information for
	 * the font characters. This includes width, height, and where the character
	 * is stored on the font texture.
	 */
	private class IntObject {
		/** Character's width */
		public int width;

		/** Character's height */
		public int height;

		/** Character's stored x position */
		public int storedX;

		/** Character's stored y position */
		public int storedY;
	}

	/**
	 * Constructor for the TrueTypeFont class Pass in the preloaded standard
	 * Java TrueType font, and whether you want it to be cached with
	 * AntiAliasing applied.
	 * 
	 * @param font
	 *            Standard Java AWT font
	 * @param antiAlias
	 *            Whether or not to apply AntiAliasing to the cached font
	 * @param additionalChars
	 *            Characters of font that will be used in addition of first 256 (by unicode).
	 */
	public TrueTypeFont(java.awt.Font font, boolean antiAlias, char[] additionalChars) {
		GLUtils.checkGLContext();
		
		this.font = font;
		this.fontSize = font.getSize();
		this.antiAlias = antiAlias;

		createSet( additionalChars );
	}
	
	/**
	 * Constructor for the TrueTypeFont class Pass in the preloaded standard
	 * Java TrueType font, and whether you want it to be cached with
	 * AntiAliasing applied.
	 * 
	 * @param font
	 *            Standard Java AWT font
	 * @param antiAlias
	 *            Whether or not to apply AntiAliasing to the cached font
	 */
	public TrueTypeFont(java.awt.Font font, boolean antiAlias) {
		this( font, antiAlias, null );
	}

	/**
	 * Create a standard Java2D BufferedImage of the given character
	 * 
	 * @param ch
	 *            The character to create a BufferedImage for
	 * 
	 * @return A BufferedImage containing the character
	 */
	private BufferedImage getFontImage(char ch) {
		// Create a temporary image to extract the character's size
		BufferedImage tempfontImage = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		if (antiAlias == true) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch);

		if (charwidth <= 0) {
			charwidth = 1;
		}
		int charheight = fontMetrics.getHeight();
		if (charheight <= 0) {
			charheight = fontSize;
		}

		// Create another image holding the character we are creating
		BufferedImage fontImage;
		fontImage = new BufferedImage(charwidth, charheight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		if (antiAlias == true) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		gt.setFont(font);

		gt.setColor(Color.WHITE);
		int charx = 0;
		int chary = 0;
		gt.drawString(String.valueOf(ch), (charx), (chary)
				+ fontMetrics.getAscent());

		return fontImage;

	}

	/**
	 * Create and store the font
	 * 
	 * @param customCharsArray Characters that should be also added to the cache.
	 */
	private void createSet( char[] customCharsArray ) {
		// If there are custom chars then I expand the font texture twice		
		if	(customCharsArray != null && customCharsArray.length > 0) {
			textureWidth *= 2;
		}
		
		// In any case this should be done in other way. Texture with size 512x512
		// can maintain only 256 characters with resolution of 32x32. The texture
		// size should be calculated dynamicaly by looking at character sizes. 
		
		try {
			
			BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();

			g.setColor(new Color(255,255,255,1));
			g.fillRect(0,0,textureWidth,textureHeight);
			
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			
			int customCharsLength = ( customCharsArray != null ) ? customCharsArray.length : 0; 

			for (int i = 0; i < 256 + customCharsLength; i++) {
				
				// get 0-255 characters and then custom characters
				char ch = ( i < 256 ) ? (char) i : customCharsArray[i-256];
				
				BufferedImage fontImage = getFontImage(ch);

				IntObject newIntObject = new IntObject();

				newIntObject.width = fontImage.getWidth();
				newIntObject.height = fontImage.getHeight();

				if (positionX + newIntObject.width >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}

				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;

				if (newIntObject.height > fontHeight) {
					fontHeight = newIntObject.height;
				}

				if (newIntObject.height > rowHeight) {
					rowHeight = newIntObject.height;
				}

				// Draw it here
				g.drawImage(fontImage, positionX, positionY, null);

				positionX += newIntObject.width;

				if( i < 256 ) { // standard characters
					charArray[i] = newIntObject;
				} else { // custom characters
					customChars.put( new Character( ch ), newIntObject );
				}

				fontImage = null;
			}

			fontTexture = BufferedImageUtil
					.getTexture(font.toString(), imgTemp);

		} catch (IOException e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Draw a textured quad
	 * 
	 * @param drawX
	 *            The left x position to draw to
	 * @param drawY
	 *            The top y position to draw to
	 * @param drawX2
	 *            The right x position to draw to
	 * @param drawY2
	 *            The bottom y position to draw to
	 * @param srcX
	 *            The left source x position to draw from
	 * @param srcY
	 *            The top source y position to draw from
	 * @param srcX2
	 *            The right source x position to draw from
	 * @param srcY2
	 *            The bottom source y position to draw from
	 */
	private void drawQuad(float drawX, float drawY, float drawX2, float drawY2,
			float srcX, float srcY, float srcX2, float srcY2, boolean italic) {
		float DrawWidth = drawX2 - drawX;
		float DrawHeight = drawY2 - drawY;
		float TextureSrcX = srcX / textureWidth;
		float TextureSrcY = srcY / textureHeight;
		float SrcWidth = srcX2 - srcX;
		float SrcHeight = srcY2 - srcY;
		float RenderWidth = (SrcWidth / textureWidth);
		float RenderHeight = (SrcHeight / textureHeight);

		float i = italic?2:0;

		GL.glBegin(SGL.GL_QUADS);
		GL.glTexCoord2f(TextureSrcX, TextureSrcY);
		GL.glVertex2f(drawX + i, drawY);
		GL.glTexCoord2f(TextureSrcX, TextureSrcY + RenderHeight);
		GL.glVertex2f(drawX - i, drawY + DrawHeight);
		GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
		GL.glVertex2f(drawX + DrawWidth - i, drawY + DrawHeight);
		GL.glTexCoord2f(TextureSrcX + RenderWidth, TextureSrcY);
		GL.glVertex2f(drawX + DrawWidth + i, drawY);
		GL.glEnd();
	}
	private void drawQuad(float drawX, float drawY, float drawX2, float drawY2) {
		float DrawWidth = drawX2 - drawX;
		float DrawHeight = drawY2 - drawY;

		
		GL.glBegin(GL.GL_QUADS);
		GL.glVertex2f(drawX, drawY);
		GL.glVertex2f(drawX, drawY + DrawHeight);
		GL.glVertex2f(drawX + DrawWidth, drawY + DrawHeight);
		GL.glVertex2f(drawX + DrawWidth, drawY);
		GL.glEnd();
	}

	/**
	 * Get the width of a given String
	 * 
	 * @param whatchars
	 *            The characters to get the width of
	 * 
	 * @return The width of the characters
	 */
	public int getWidth(String whatchars) {
		int totalwidth = 0;
		int maxwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;
		boolean bold = false;
		
		for (int i = 0; i < whatchars.length(); i++) {
			currentChar = whatchars.charAt(i);
			if (currentChar < 256) {
				intObject = charArray[currentChar];
			} else {
				intObject = (IntObject)customChars.get( new Character( (char) currentChar ) );
			}
			
			if (currentChar == '\u00a7') {
				char ac = whatchars.charAt(Math.min(i+1, whatchars.length()-1));
				if (ac == 'l') {
					bold = true;
				} else if (ac == '0' || ac == '1' || ac == '2' || ac == '3'
						|| ac == '4' || ac == '5' || ac == '6' || ac == '7'
						|| ac == '8' || ac == '9' || ac == 'a' || ac == 'b'
						|| ac == 'c' || ac == 'd' || ac == 'e' || ac == 'f'
						|| ac == 'r') {
					bold = false;
				}
				i++;
				continue;
			}
			
			if (currentChar == '\n') {
				totalwidth = 0;
				continue;
			}
			
			if( intObject != null )
				totalwidth += intObject.width+(bold?1:0);
			
			if (totalwidth > maxwidth) {
				maxwidth = totalwidth;
			}
		}
		return maxwidth;
	}

	/**
	 * Get the font's height
	 * 
	 * @return The height of the font
	 */
	public int getHeight() {
		return fontHeight;
	}

	/**
	 * Get the height of a String
	 * 
	 * @return The height of a given string
	 */
	public int getHeight(String HeightString) {
		return fontHeight;
	}

	/**
	 * Get the font's line height
	 * 
	 * @return The line height of the font
	 */
	public int getLineHeight() {
		return fontHeight;
	}

	/**
	 * Draw a string
	 * 
	 * @param x
	 *            The x position to draw the string
	 * @param y
	 *            The y position to draw the string
	 * @param whatchars
	 *            The string to draw
	 * @param color
	 *            The color to draw the text
	 */
	public void drawString(float x, float y, String whatchars,
			org.newdawn.slick.Color color, boolean shadow) {
		drawString(x,y,whatchars,color,0,whatchars.length()-1, shadow);
	}
	
	/**
	 * @see Font#drawString(float, float, String, org.newdawn.slick.Color, int, int)
	 */
	public void drawString(float x, float y, String whatchars,
			org.newdawn.slick.Color color, int startIndex, int endIndex, boolean shadow) {
		color.bind();
		fontTexture.bind();

		IntObject intObject = null;
		int charCurrent;

		int totalwidth = 0;
		
		boolean bold = false;
		boolean italic = false;
		boolean strike = false;
		boolean under = false;
		boolean magic = false;
		float boldOffsetX = 1f;
		
		for (int i = 0; i < whatchars.length(); i++) {
			charCurrent = whatchars.charAt(i);

			if (charCurrent == '\u00a7') {
				if (i != whatchars.length()-1) {
					char charColor = whatchars.charAt(i+1);
					
					Format f = ChatUtil.getChatFormatter(charColor);
					org.newdawn.slick.Color c = null;
					
					switch(f) {
					case COLOR:
						if (!shadow) {
							c = ChatUtil.getColorFromChar(charColor);
						} else {
							c = ChatUtil.getColorFromChar(charColor).darker(0.9f);
						}
						bold = false;
						italic = false;
						strike = false;
						under = false;
						magic = false;
						break;
					case FORMAT:
						if (charColor == 'k') {
							magic = true;
						} else if (charColor == 'l') {
							bold = true;
						} else if (charColor == 'm') {
							strike = true;
						} else if (charColor == 'n') {
							under = true;
						} else if (charColor == 'o') {
							italic = true;
						} else if (charColor == 'r') {
							c = color;
							bold = false;
							italic = false;
							strike = false;
							under = false;
							magic = false;
						}
						break;
					case NONE:
						break;
					}
	
					if (c != null) {
						c.a = color.a;
						c.bind();
					}
					i++;
				}
				continue;
			}
			
			if (charCurrent == '\n') {
				totalwidth = 0;
				y+= getHeight();
				continue;
			}
			
			int charOld = charCurrent;
			if (magic) {
				Random rand = new Random();
				int temp = 33+rand.nextInt(122-33);
				if (this.getWidth(String.valueOf(new Character((char)temp))) <= this.getWidth(String.valueOf(new Character((char)charCurrent)))) {
					charCurrent = temp;
				}
			}
			
			if (charCurrent < 256) {
				intObject = charArray[charCurrent];
			} else {
				intObject = (IntObject)customChars.get( new Character( (char) charCurrent ) );
			} 
			
			if( intObject != null ) {
				if ((i >= startIndex) || (i <= endIndex)) {
					if (bold) {
						drawQuad((x + totalwidth)+boldOffsetX, y,
								(x + totalwidth + intObject.width)+boldOffsetX,
								(y + intObject.height), intObject.storedX,
								intObject.storedY, intObject.storedX + intObject.width,
								intObject.storedY + intObject.height, italic);
					}
					drawQuad((x + totalwidth), y,
							(x + totalwidth + intObject.width),
							(y + intObject.height), intObject.storedX,
							intObject.storedY, intObject.storedX + intObject.width,
							intObject.storedY + intObject.height, italic);
				}
				
				Tessellator var9;
                WorldRenderer var10;

                if (strike)
                {
                    var9 = Tessellator.getInstance();
                    var10 = var9.getWorldRenderer();
                    RenderHelper.enableGUIStandardItemLighting();
                    int var8 = intObject.width;
                    float xx = x+totalwidth;
                    float yy = y;
                    int yOffset = 1;
                    var10.startDrawingQuads();
                    var10.addVertex((double)xx, (double)(yy + (float)(getHeight() / 2))+yOffset, 0.0D);
                    var10.addVertex((double)(xx + var8), (double)(yy + (float)(getHeight() / 2))+yOffset, 0.0D);
                    var10.addVertex((double)(xx + var8), (double)(yy + (float)(getHeight() / 2) - 1.0F)+yOffset, 0.0D);
                    var10.addVertex((double)xx, (double)(yy + (float)(getHeight() / 2) - 1.0F)+yOffset, 0.0D);
                    var9.draw();
                    RenderHelper.disableStandardItemLighting();
                }

                if (under)
                {
                    var9 = Tessellator.getInstance();
                    var10 = var9.getWorldRenderer();
                    RenderHelper.enableGUIStandardItemLighting();
                    int var8 = intObject.width;
                    float xx = x+totalwidth;
                    float yy = y-(getHeight()/7f)+1;
                    var10.startDrawingQuads();
                    int var11 = under ? -1 : 0;
                    var10.addVertex((double)(xx + (float)var11), (double)(yy + (float)getHeight()), 0.0D);
                    var10.addVertex((double)(xx + var8), (double)(yy + (float)getHeight()), 0.0D);
                    var10.addVertex((double)(xx + var8), (double)(yy + (float)getHeight() - 1.0F), 0.0D);
                    var10.addVertex((double)(xx + (float)var11), (double)(yy + (float)getHeight() - 1.0F), 0.0D);
                    var9.draw();
                    RenderHelper.disableStandardItemLighting();
                }
				
                if (magic) {
                	charCurrent = charOld;
                }
				if (charCurrent < 256) {
					intObject = charArray[charCurrent];
				} else {
					intObject = (IntObject)customChars.get( new Character( (char) charCurrent ) );
				} 
				totalwidth += intObject.width+(bold?boldOffsetX:0);
			}
		}
	}

	/**
	 * Draw a string
	 * 
	 * @param x
	 *            The x position to draw the string
	 * @param y
	 *            The y position to draw the string
	 * @param whatchars
	 *            The string to draw
	 */
	public void drawString(float x, float y, String whatchars, boolean shadow) {
		drawString(x, y, whatchars, org.newdawn.slick.Color.white, shadow);
	}

	@Override
	public void drawString(float x, float y, String text) {
		drawString(x, y, text, false);
	}

	@Override
	public void drawString(float x, float y, String text,
			org.newdawn.slick.Color col) {
		drawString(x, y, text, col, false);
	}

	@Override
	public void drawString(float x, float y, String text,
			org.newdawn.slick.Color col, int startIndex, int endIndex) {
		drawString(x, y, text, col, startIndex, endIndex, false);
	}
}