package none.clickGui.valuepanel;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import none.Client;
import none.event.events.EventChat;
import none.fontRenderer.xdolf.Fonts;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.noneClickGui.Panel.ValuePanel.NumberBox;
import none.noneClickGui.Panel.ValuePanel.ValueSlot;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;
import none.valuesystem.StringValue;
import none.valuesystem.Value;

public class ValuePanel {
	public TTFFontRenderer fontRenderer;
	public int x,y,w,h;
	
	public Module mod;
	public ArrayList<VSlot> p;
	public ArrayList<PageV> page;
	public int numberpage = 0;
	public ValuePanel() {
		p = new ArrayList<>();
		page = new ArrayList<>();
	}
	
	public void update() {
		if (mod != null) {
			for (Value v : mod.getValues()) {
				if (v != null) {
					if (v instanceof BooleanValue) {
						p.add(new BooleanSlot((BooleanValue)v));
					}
					
					if (v instanceof ModeValue) {
						p.add(new ModeSlot((ModeValue)v));
					}
					
					if (v instanceof StringValue) {
						p.add(new StringSlot((StringValue)v));
					}
					
					if (v instanceof NumberValue) {
						NumberSlot cb;
						
						NumberSlot.NumberType type = NumberSlot.NumberType.DECIMAL;
						
						if (v.getObject() instanceof Integer) {
                            type = NumberSlot.NumberType.INTEGER;
                        } else if (v.getObject() instanceof Long) {
                            type = NumberSlot.NumberType.TIME;
                        } else if (v.getObject() instanceof Float && ((NumberValue) v).getMin().intValue() == 0 && ((NumberValue) v).getMax().intValue() == 100) {
                            type = NumberSlot.NumberType.PERCENT;
                        }
						p.add(cb = new NumberSlot((NumberValue)v, ((Number)v.getObject()).floatValue(), ((NumberValue)v).getMin().floatValue(), ((NumberValue)v).getMax().floatValue(), type));
						cb.setListener(val -> {
                            if (v.getObject() instanceof Integer) {
                            	String displayval = cb.getNumberType().getFormatter().apply(val.intValue());
                                int in = Integer.parseInt(displayval);
                            	v.setObject(in);
                            }
                            if (v.getObject() instanceof Float) {
                            	String displayval = cb.getNumberType().getFormatter().apply(val.floatValue());
                                Float in = Float.parseFloat(displayval);
                                v.setObject(in);
                            }
                            if (v.getObject() instanceof Long) {
                            	String displayval = cb.getNumberType().getFormatter().apply(val.longValue());
                                long in = Long.parseLong(displayval);
                                v.setObject(in);
                            }
                            if (v.getObject() instanceof Double) {
                            	String displayval = cb.getNumberType().getFormatter().apply(val.doubleValue());
                                double in = Double.parseDouble(displayval);
                                v.setObject(in);
                            }

                            return true;
                        });
					}
				}else {
					p.clear();
					page.clear();
				}
			}
			int count = 0;
			int startY = y;
			if (!p.isEmpty()) {
				page.add(new PageV(0, x - 10, startY));
				startY += 10;
				if (p.size() > 5) {
					page.add(new PageV(1, x - 10, startY));
					startY += 10;
					if (p.size() >= 10) {
						page.add(new PageV(2, x - 10, startY));
						startY += 10;
						if (p.size() >= 15) {
							page.add(new PageV(3, x - 10, startY));
							startY += 10;
							if (p.size() >= 20) {
								page.add(new PageV(4, x - 10, startY));
								startY += 10;
								if (p.size() >= 25) {
									page.add(new PageV(5, x - 10, startY));
									startY += 10;
									if (p.size() >= 30) {
										page.add(new PageV(6, x - 10, startY));
										startY += 10;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");

		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		x = 30;
		y = 260;
		w = res.getScaledWidth() - x - 30;
		h = 80;
		int renderColor = ClientColor.getColor();
		if (ClientColor.rainbow.getObject()) {
			renderColor = ClientColor.rainbow(10000);
		}
		
		if (isHovered(mouseX, mouseY)) {
			x+= 4;
			renderColor = ClientColor.getColor();
		}
		
		Gui.drawOutineRect(x, y, x + w, y + h, 2, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 200), renderColor);
		
		if (mod != null) {
			int startX = x;
			int count = 0;
			if (p.isEmpty()) {
				fontRenderer.drawString("No-Value", x + 4, y + (h/2), !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
			}else {
				for (VSlot vs : p) {
					if (count > 5) {
						vs.page = 1;
						if (count >= 10) {
							vs.page = 2;
							if (count >= 15) {
								vs.page = 3;
								if (count >= 20) {
									vs.page = 4;
									if (count >= 25) {
										vs.page = 5;
										if (count >= 30) {
											vs.page = 6;
										}
									}
								}
							}
						}
					}
					if (vs.page == getPage()) {
						vs.x = startX;
						if (vs instanceof BooleanSlot) {
							vs.y = y;
						}else if (vs instanceof ModeSlot) {
							vs.y = y + 20;
						}else if (vs instanceof NumberSlot) {
							vs.y = y + 47;
						}else if (vs instanceof StringSlot) {
							vs.y = y + 47;
						}
						vs.drawScreen(mouseX, mouseY, partialTicks);
						startX += vs.width + 4;
					}
					count++;
				}
				for (PageV page1 : page) {
					page1.drawScreen(mouseX, mouseY, partialTicks);
				}
			}
		}
	}
	
	public void mouseReleased(int button, int x, int y) {
		if (mod != null) {
			if (!p.isEmpty()) {
				for (VSlot slot : p) {
					if (slot.page == getPage()) {
						slot.mouseReleased(button, x, y);
					}
				}
			}
		}
		if (!page.isEmpty()) {
			for (PageV page : page) {
				page.mouseReleased(button, x, y);
			}
		}
    }
	public void mousePressed(int button, int x, int y) {
		if (mod != null) {
			if (!p.isEmpty()) {
				for (VSlot slot : p) {
					if (slot.page == getPage()) {
						slot.mousePressed(button, x, y);
					}
				}
			}
		}
		if (!page.isEmpty()) {
			for (PageV page : page) {
				page.mousePressed(button, x, y);
			}
		}
	}
	
	public void mouseMoved(int x, int y) {
		if (mod != null) {
			if (!p.isEmpty()) {
				for (VSlot slot : p) {
					if (slot.page == getPage()) {
						slot.mouseMoved(x, y);
					}
				}
			}
		}
		if (!page.isEmpty()) {
			for (PageV page : page) {
				page.mouseMoved(x, y);
			}
		}
    }
	
	public void onClosed() {
		if (mod != null) {
			if (!p.isEmpty()) {
				for (VSlot slot : p) {
					slot.onClose();
				}
			}
		}
	}
	
	public void onKey(char typedChar, int keyCode) {
		if (mod != null) {
			if (!p.isEmpty()) {
				for (VSlot slot : p) {
					if (slot instanceof NumberSlot) {
						if (((NumberSlot)slot).onGui) {
							slot.onKey(typedChar, keyCode);
						}
					}else if (slot instanceof StringSlot) {
						if (((StringSlot)slot).onGui) {
							slot.onKey(typedChar, keyCode);
						}
					}
				}
			}
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
	
	public void setModule(Module mod) {
		this.mod = mod;
	}
	
	public int getPage() {
		return numberpage;
	}
	
	public void setCurrentPage(int pagenum) {
		this.numberpage = pagenum;
	}
	
	public void onUpdate () {
		if (mod != null) {
			if (!p.isEmpty()) {
				for (VSlot slot : p) {
					if (slot.getPage() == numberpage) {
						slot.onUpdate();
					}
				}
			}
		}
	}
}
