package com.kilo.mod.toolbar.dropdown;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;


public class TextBox extends Interactable {
	
	public TrueTypeFont font;
	public int fontColor;
	public Align fontAlignH, fontAlignV;
	
	public String placeholder, text;
	public float scroll, scrollTo;
	public final float scrollSpeed = 2f;
	public int cursorPos, startSelect, endSelect;
	public boolean dragging;
	
	public TextBoxRel rel;
	
	protected Timer timer = new Timer();
	
	public float anim;
	public final float animSpeed = 2f;
	
	public TextBox(Window parent, TYPE type, TextBoxRel rel, String t, int id, String help, float x, float y, float w, float h) {
		super(parent, type, id, help, parent.x, parent.y, w, h, x, y);
		placeholder = t;
		text = "";
		font = Fonts.ttfRoundedBold14;
		fontColor = Colors.WHITE.c;
		fontAlignH = Align.L;
		fontAlignV = Align.C;
		this.rel = rel;
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);

		hover = mouseOver(mx, my) && enabled;
		
		anim+= ((active?1f:0f)-anim)/animSpeed;

		if (!active) {
			cursorPos = 0;
			startSelect = cursorPos;
			scrollTo = 0;
		} else {
			if (dragging) {
				cursorPos = ChatUtil.getMousePos(text, font, x, mx, scroll);
			}
		}
		
		cursorPos = Math.min(Math.max(0, cursorPos), text.length());
		endSelect = cursorPos;
	}
	
	@Override
	public void mouseClicked(int mx, int my, int b) {
		if (mouseOver(mx, my)) {
			//parent.action(this, mx, my, b);
			active = true;
		} else {
			active = false;
		}
		
		if (active) {
			dragging = true;
			cursorPos = ChatUtil.getMousePos(text, font, x, mx, scroll);
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
				startSelect = cursorPos;
			}
		}
	}
	
	@Override
	public void mouseReleased(int mx, int my, int b) {
		dragging = false;
	}
	
	public void keyboardPress(int key) {
		if (active) {
			switch(key) {
			case Keyboard.KEY_ESCAPE:
				active = false;
				break;
			case Keyboard.KEY_BACK:
				if (text.length() > 0) {
					if (startSelect != endSelect) {
						text = ChatUtil.replaceAt(text, "", startSelect, endSelect);
						if (cursorPos > startSelect) {
							cursorPos-= Math.max(startSelect, endSelect)-Math.min(startSelect, endSelect);
						}
					} else {
						if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
							int next = -1;
							for(int i = Math.max(cursorPos-1, 0); i >= 0; i--) {
								if ((String.valueOf(text.charAt(i)).equalsIgnoreCase(" ") || i == 0) && Math.abs(cursorPos-i) > 1) {
									next = i+(i == 0?0:1);
									break;
								}
							}
							if (next != -1) {
								text = ChatUtil.replaceAt(text, "", next, cursorPos);
								cursorPos = next;
							}
						} else {
							text = ChatUtil.replaceAt(text, "", cursorPos-1, cursorPos);
							cursorPos--;
						}
					}
				}
				startSelect = cursorPos;
				break;
			case Keyboard.KEY_DELETE:
				if (text.length() > 0) {
					if (startSelect != endSelect) {
						text = ChatUtil.replaceAt(text, "", startSelect, endSelect);
						if (cursorPos > startSelect) {
							cursorPos-= Math.max(startSelect, endSelect)-Math.min(startSelect, endSelect);
						}
					} else {
						if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
							int next = -1;
							for(int i = cursorPos; i < text.length(); i++) {
								if ((String.valueOf(text.charAt(i)).equalsIgnoreCase(" ") || i == text.length()-1) && (Math.abs(cursorPos-i) > 1 || i == text.length()-1)) {
									next = i+(i == text.length()-1?1:0);
									break;
								}
							}
							if (next != -1) {
								text = ChatUtil.replaceAt(text, "", cursorPos, next);
							}
						} else {
							text = ChatUtil.replaceAt(text, "", cursorPos, cursorPos+1);
						}
					}
				}
				startSelect = cursorPos;
				break;
			case Keyboard.KEY_HOME:
				cursorPos = 0;
				if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					startSelect = cursorPos;
				}
				break;
			case Keyboard.KEY_END:
				cursorPos = text.length();
				if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					startSelect = cursorPos;
				}
				break;
			case Keyboard.KEY_LEFT:
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					int prev = -1;
					for(int i = Math.max(cursorPos-1, 0); i >= 0; i--) {
						try {
							if ((String.valueOf(text.charAt(i)).equalsIgnoreCase(" ") || i == 0) && Math.abs(cursorPos-i) > 1) {
								prev = i;
								break;
							}
						} catch (Exception e) {
							break;
						}
					}
					if (prev != -1) {
						cursorPos = prev;
					}
				} else {
					cursorPos--;
				}
				if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					startSelect = cursorPos;
				}
				break;
			case Keyboard.KEY_RIGHT:
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					int next = -1;
					for(int i = cursorPos; i < text.length(); i++) {
						try {
							if ((String.valueOf(text.charAt(i)).equalsIgnoreCase(" ") || i == text.length()-1) && (Math.abs(cursorPos-i) > 1 || i == text.length()-1)) {
								next = i+1;
								break;
							}
						} catch (Exception e) {
							break;
						}
					}
					if (next != -1) {
						cursorPos = next;
					}
				} else {
					cursorPos++;
				}
				if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					startSelect = cursorPos;
				}
				break;
			case Keyboard.KEY_A:
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					cursorPos = text.length();
					startSelect = 0;
					endSelect = cursorPos;
				}
				break;
			case Keyboard.KEY_X:
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					if (startSelect != endSelect) {
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text.substring(Math.min(startSelect, endSelect), Math.max(startSelect, endSelect))), (ClipboardOwner)null);
						
						text = ChatUtil.replaceAt(text, "", startSelect, endSelect);
						if (cursorPos > startSelect) {
							cursorPos-= Math.max(startSelect, endSelect)-Math.min(startSelect, endSelect);
						}
						startSelect = cursorPos;
						endSelect = cursorPos;
					}
				}
				break;
			case Keyboard.KEY_C:
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					if (startSelect != endSelect) {
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text.substring(Math.min(startSelect, endSelect), Math.max(startSelect, endSelect))), (ClipboardOwner)null);
					}
				}
				break;
			case Keyboard.KEY_V:
				if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
					String cb = "";
					try {
						cb = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
					} catch (Exception e) {}
					if (cb != "") {
						if (startSelect != endSelect) {
							text = ChatUtil.replaceAt(text, cb, startSelect, endSelect);
							if (cursorPos > startSelect) {
								cursorPos-= Math.max(startSelect, endSelect)-Math.min(startSelect, endSelect);
							}
							cursorPos+= cb.length();
							startSelect = cursorPos;
						} else {
							text = ChatUtil.insertAt(text, cb, cursorPos);
							cursorPos+= cb.length();
							startSelect = cursorPos;
						}
					} 
				}
				break;
			case Keyboard.KEY_RETURN:
				parent.action(this, 0, 0, 0);
				break;
			}
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		if (active) {
			if (ChatUtil.isAllowedCharacter(keyChar)) {
				if (startSelect != endSelect) {
					text = ChatUtil.replaceAt(text, Character.toString(keyChar), startSelect, endSelect);
				} else {
					text = ChatUtil.insertAt(text, Character.toString(keyChar), cursorPos);
				}
				cursorPos++;
				startSelect = cursorPos;
			}
		}
	}
	
	public void render(float opacity) {
		if (opacity > 0.01f) {
			if (active) {
				Draw.rect(x-4, y, x+width+4, y+height, Util.reAlpha(Colors.BLACK.c, parent.boxFade*opacity));
			}
			float cursorBlink = 1f;
			boolean cursor = (active?(timer.isTime(cursorBlink/2f)?true:false):false);
	
			if (timer.isTime(cursorBlink)) {
				timer.reset();
			}
			
			Draw.startClip(x, y, x+width, y+height);
	
			if (cursor) {
				float xx = x+scroll+font.getWidth(text.substring(0, cursorPos));
				Draw.rect(xx-1, y+(height/2)-(font.getHeight(text)/2), xx+1, y+(height/2)+(font.getHeight(text)/2), Util.reAlpha(Colors.WHITE.c, 0.8f*opacity));
			}
	
			if (active) {
				float cp = (x+font.getWidth(text.substring(0, cursorPos))+scrollTo);
				if (cp < x) {
					scrollTo+= (x-cp);
					scrollTo-= Math.min(width, scrollTo);
				}
				if (cp > x+width) {
					scrollTo+= (x+width)-cp;
				}
			}
			
			scroll+= (scrollTo-scroll)/scrollSpeed;
	
			startSelect = Math.min(Math.max(0, startSelect), text.length());
			endSelect = Math.min(Math.max(0, endSelect), text.length());
			
			float startX = x+scroll+font.getWidth(text.substring(0, startSelect));
			float endX = x+scroll+font.getWidth(text.substring(0, endSelect));
			Draw.rect(startX, y+(height/2)-(font.getHeight(text)/2), endX, y+(height/2)+(font.getHeight(text)/2), Util.reAlpha(Colors.DARKBLUE.c, 0.5f*opacity));
			
			Draw.string(font, x+scroll, y+(height/2), text.length() != 0 || active?text:placeholder, Util.reAlpha(fontColor, ((anim/2)+0.5f)*opacity), fontAlignH, fontAlignV);
			
			Draw.endClip();
		}
	}
}
