package club.bluezenith.util.font;

import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;

import static java.lang.Math.min;

@SuppressWarnings({"StringConcatenationInLoop", "SpellCheckingInspection"})
public final class TFontRenderer extends FontRenderer {
	public float size;
	public long lastUsedTime = System.currentTimeMillis();
	public boolean isIconFont;
	private final boolean antiAlias;
	private final Font font;
	private final boolean fractionalMetrics;
	private final CharacterData[] regularData;
	private final CharacterData[] boldData;
	private final CharacterData[] italicsData;
	private final int[] colorCodes = new int[32];
	private static final int RANDOM_OFFSET = 1;

	public boolean isCustom;

	public TFontRenderer(final Font font) {
		this(font, 256);
	}

	public TFontRenderer(final Font font, String name, boolean isCustom) {
		this(font, 256);
		this.name = name;
		this.isCustom = isCustom;
	}

	public TFontRenderer setIcon(boolean icon) {
		isIconFont = icon;
		return this;
	}

	public TFontRenderer(final Font font, final int characterCount) {
		this(font, characterCount, true);
	}

	public TFontRenderer(final Font font, final int characterCount, final boolean antiAlias) {
		super(Minecraft.getMinecraft().gameSettings,
				new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
		this.name = font.getName().split(" ")[0] + "-" + (font.getSize() * 2);
		this.font = font;
		fractionalMetrics = true;
		this.antiAlias = antiAlias;
		regularData = setup(new CharacterData[characterCount], 0);
		boldData = setup(new CharacterData[characterCount], 1);
		italicsData = setup(new CharacterData[characterCount], 2);
		FONT_HEIGHT = (int) getHeight(" ");
	}

	protected void bindTexture(ResourceLocation p_bindTexture_1_) {
	}

	protected void readGlyphSizes() {
	}

	private CharacterData[] setup(final CharacterData[] characterData, final int type) {
		generateColors();
		final Font font = this.font.deriveFont(type);
		final BufferedImage utilityImage = new BufferedImage(1, 1, 2);
		final Graphics2D utilityGraphics = (Graphics2D) utilityImage.getGraphics();
		utilityGraphics.setFont(font);
		final FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
		for (int index = 0; index < characterData.length; ++index) {
			final char character = (char) index;
			final Rectangle2D characterBounds = fontMetrics.getStringBounds(String.valueOf(character), utilityGraphics);
			final float width = (float) characterBounds.getWidth() + 8.0f;
			final float height = (float) characterBounds.getHeight();
			final BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int(width),
					MathHelper.ceiling_double_int(height), 2);
			final Graphics2D graphics = (Graphics2D) characterImage.getGraphics();
			graphics.setFont(font);
			graphics.setColor(new Color(255, 255, 255, 0));
			graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
			graphics.setColor(Color.WHITE);
			if (antiAlias) {
				graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON
								: RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			}
			graphics.drawString(String.valueOf(character), 4, fontMetrics.getAscent());
			final int textureId = GL11.glGenTextures();
			createTexture(textureId, characterImage);
			characterData[index] = new CharacterData(character, characterImage.getWidth(), characterImage.getHeight(),
					textureId);
		}
		return characterData;
	}

	private void createTexture(final int textureId, final BufferedImage image) {
		final int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		for (int y2 = 0; y2 < image.getHeight(); ++y2) {
			for (int x = 0; x < image.getWidth(); ++x) {
				final int pixel = pixels[y2 * image.getWidth() + x];
				buffer.put((byte) (pixel >> 16 & 255));
				buffer.put((byte) (pixel >> 8 & 255));
				buffer.put((byte) (pixel & 255));
				buffer.put((byte) (pixel >> 24 & 255));
			}
		}
		buffer.flip();
		GlStateManager.bindTexture(textureId);
		GL11.glTexParameteri(3553, 10241, 9728);
		GL11.glTexParameteri(3553, 10240, 9728);
		GL11.glTexImage2D(3553, 0, 6408, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
	}

	public int drawString(final String text, final int x, final int y, final int color) {
		return renderString(text, x, y, color, false);
	}

	public int drawString(final String text, final float x, final float y, final int color) {
		return renderString(text, x, y, color, false);
	}

	public void drawCenteredString(final String text, final float x, final float y, final int color) {
		final float width = getStringWidthF(text) / 2.0f;
		renderString(text, x - width, y, color, false);
	}

	public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
		return drawString(text, x, y, color, true);
	}

	public int drawString(final String text, final float x, final float y, final int color, final boolean shadow) {
		if (shadow) {
			renderString(text, x + 0.5f, y + 0.5f, color, true);
		}
		return renderString(text, x, y, color, false);
	}

	protected int renderString(String text, float x, float y, final int color, final boolean shadow) {
		x += 0.01F; //bypass value lmao (makes the font sometimes not look glitchy idk why)
		y += 0.01F;

		text = getEventString(text);

		if (text.length() == 0) {
			return (int) x;
		}
		GL11.glPushMatrix();
		GlStateManager.scale(0.5, 0.5, 1.0);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
		x -= 2.0f;
		y -= 2.0f;
		x += 0.5f;
		y += 0.5f;
		x *= 2.0f;
		y *= 2.0f;
		CharacterData[] characterData = regularData;
		boolean underlined = false;
		boolean strikethrough = false;
		boolean obfuscated = false;
		int colorProgress = 0;
		final int length = text.length();
		final double multiplier = 255.0 * (shadow ? 4 : 1);
		final Color c2 = new Color(color);
		String customColour = "";
		final double normalAlpha = (color >> 24 & 255) / 255.0,
					 forcedAlpha = RenderUtil.limitAlpha ? RenderUtil.alphaLimit255 / 255.0 : normalAlpha;

		GL11.glColor4d(c2.getRed() / multiplier, c2.getGreen() / multiplier, c2.getBlue() / multiplier, min(normalAlpha, forcedAlpha));
		x = renderChars(text, x, y, color, shadow, characterData, underlined, strikethrough, obfuscated, colorProgress, length, customColour);
		GL11.glPopMatrix();
		GlStateManager.disableBlend();
		GlStateManager.bindTexture(0);
		GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
		return (int) x;
	}

	private float renderChars(String text, float x, float y, int color, boolean shadow, CharacterData[] characterData, boolean underlined, boolean strikethrough, boolean obfuscated, int colorProgress, int length, String customColour) {
		sex sex1 = null;
		if(shadow) {
			sex1 = sex;
			sex = null;
		}
		for (int i = 0; i < length; ++i) {
			int previous = i > 0 ? (int) text.charAt(i - 1) : 46;
			char next = text.charAt(i + 1 < text.length() ? i + 1 : i);
			char character = text.charAt(i);
			if (previous == 167) {
				continue;
			}
			if (character == '\u00a7') {
				int index = "0123456789abcdefklmnorабвгдеёжзийклмнопрстуфхцчщшъьэюя".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
				if (index < 16) {
					if(sex == null) {
						obfuscated = false;
						strikethrough = false;
						underlined = false;
						characterData = regularData;
						if (index < 0) {
							index = 15;
						}
						if (shadow) {
							index += 16;
						}
						final int textColor = colorCodes[index];
						GL11.glColor4d((textColor >> 16) / 255.0, (textColor >> 8 & 255) / 255.0, (textColor & 255) / 255.0,
								(color >> 24 & 255) / 255.0);
					}
					continue;
				}
				if (index == 16) {
					obfuscated = true;
					continue;
				}
				if (index == 17) {
					characterData = boldData;
					continue;
				}
				if (index == 18) {
					strikethrough = true;
					continue;
				}
				if (index == 19) {
					underlined = true;
					continue;
				}
				if (index == 20) {
					characterData = italicsData;
					continue;
				}
				obfuscated = false;
				strikethrough = false;
				underlined = false;
				characterData = regularData;
				GL11.glColor4d((shadow ? 0.25 : 1.0), (shadow ? 0.25 : 1.0), (shadow ? 0.25 : 1.0),
						(color >> 24 & 255) / 255.0);
				continue;
			}
			if (character > '\u00ff') {
				continue;
			}
			if (obfuscated) {
				character = (char) (character + (char) RANDOM_OFFSET);
			}
			//TODO: Improve this coloring system
			if(sex == null){
				if (character == '$' && next == '{') {
					colorProgress = 1;
					continue;
				} else if (character == '{' && colorProgress == 1) {
					colorProgress = 2;
					continue;
				} else if (colorProgress == 2) {
					if (character == '}') {
						colorProgress = 3;
					} else {
						customColour += character;
					}
					continue;
				} else if (colorProgress == 3 && !customColour.isEmpty()) {
					int secs = Integer.parseInt(customColour);
					GL11.glColor4d((secs >> 16 & 0x000000FF) / 255.0, (secs >> 8 & 0x000000FF) / 255.0, (secs & 0x000000FF) / 255.0, (secs >> 24 & 0x000000FF) / 255.0);
					colorProgress = 0;
					customColour = "";
				}
			}else{
				Color c = sex.colour(i);
				GlStateManager.color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
			}
			drawChar(character, characterData, x, y, i);
			final CharacterData charData = characterData[character];
			drawFormatting(underlined, strikethrough, charData);
			x += charData.width - 8.0f;
		}
		if(sex != null && !shadow){
			sex = null;
		}
		if(shadow && sex1 != null){
			sex = sex1;
		}
		return x;
	}

	private void drawFormatting(boolean underlined, boolean strikethrough, CharacterData charData) {
		if (strikethrough) {
			drawLine(new Vector2f(0.0f, charData.height / 2.0f),
					new Vector2f(charData.width, charData.height / 2.0f));
		}
		if (underlined) {
			drawLine(new Vector2f(0.0f, charData.height - 15.0f),
					new Vector2f(charData.width, charData.height - 15.0f));
		}
	}

	public int getStringWidth(final String text) {
		return (int) getStringWidthF(text);
	}

	public float getStringWidthF(String text) {
		text = getEventString(text);
		float width = 0.0f;
		CharacterData[] characterData = regularData;
		final int length = text.length();
		int colorProgress = 0;
		for (int i = 0; i < length; ++i) {
			int previous;
			final char character = text.charAt(i);
			previous = i > 0 ? (int) text.charAt(i - 1) : 46;
			char next = i < text.length() - 1 ? text.charAt(i + 1) : 46;
			if (previous == 167) {
				continue;
			}
			if (character == '\u00a7') {
				final int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
				if (index == 17) {
					characterData = boldData;
					continue;
				}
				if (index == 20) {
					characterData = italicsData;
					continue;
				}
				if (index != 21) {
					continue;
				}
				characterData = regularData;
				continue;
			}
			if (character > '\u00ff') {
				continue;
			}
			if (character == '$' && next == '{') {
				colorProgress = 1;
				continue;
			} else if (character == '{' && previous == '$') {
				colorProgress = 2;
				continue;
			} else if (colorProgress == 2) {
				if (character == '}')
					colorProgress = 0;
				continue;
			}
			final CharacterData charData = characterData[character];
			width += (charData.width - 8.0f) / 2.0f;
		}
		return width;
	}

	public float getHeight(final String text) {
		float height = 0.0f;
		CharacterData[] characterData = regularData;
		final int length = text.length();
		for (int i = 0; i < length; ++i) {
			int previous;
			final char character = text.charAt(i);
			previous = i > 0 ? (int) text.charAt(i - 1) : 46;
			if (previous == 167) {
				continue;
			}
			if (character == '\u00a7') {
				final int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
				if (index == 17) {
					characterData = boldData;
					continue;
				}
				if (index == 20) {
					characterData = italicsData;
					continue;
				}
				if (index != 21) {
					continue;
				}
				characterData = regularData;
				continue;
			}
			if (character > '\u00ff') {
				continue;
			}
			final CharacterData charData = characterData[character];
			height = Math.max(height, charData.height);
		}
		return height / 2.0f - 2.0f;
	}

	private void drawChar(final char character, final CharacterData[] characterData, final float x, final float y, int i) {
		final CharacterData charData = characterData[character];
		charData.bind();
		GL11.glBegin(7);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2d(x, y);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2d(x, y + charData.height);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2d(x + charData.width, y + charData.height);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2d(x + charData.width, y);
		GL11.glEnd();
	}

	private void drawLine(final Vector2f start, final Vector2f end) {
		GL11.glDisable(3553);
		GL11.glLineWidth((float) 3.0);
		GL11.glBegin(1);
		GL11.glVertex2f(start.x, start.y);
		GL11.glVertex2f(end.x, end.y);
		GL11.glEnd();
		GL11.glEnable(3553);
	}

	private void generateColors() {
		for (int i = 0; i < 32; ++i) {
			final int thingy = (i >> 3 & 1) * 85;
			int red = (i >> 2 & 1) * 170 + thingy;
			int green = (i >> 1 & 1) * 170 + thingy;
			int blue = (i & 1) * 170 + thingy;
			if (i == 6) {
				red += 85;
			}
			if (i >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}
			colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
		}
	}

	static class CharacterData {
		public char character;
		public float width;
		public float height;
		private final int textureId;

		public CharacterData(final char character, final float width, final float height, final int textureId) {
			this.character = character;
			this.width = width;
			this.height = height;
			this.textureId = textureId;
		}

		public void bind() {
			GL11.glBindTexture(3553, textureId);
		}
	}

}