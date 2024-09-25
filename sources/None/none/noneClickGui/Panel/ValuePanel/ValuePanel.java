package none.noneClickGui.Panel.ValuePanel;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import none.Client;
import none.fontRenderer.xdolf.Fonts;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.noneClickGui.Panel.ModulePanel;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;
import none.valuesystem.Value;

public class ValuePanel {
	
	public int x, y, w, h;
	public Module mod;
	public Module lastmod;
	
	public boolean extended;
	
    private TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
    public ArrayList<ValueSlot> p;
    
	public ValuePanel() {
		p = new ArrayList<>();
	}
	
	public void update() {
		if (mod != null) {
			for (Value v : mod.getValues()) {
				if (v != null) {
					if (v instanceof BooleanValue) {
						p.add(new BooleanBox((BooleanValue)v));
					}else if (v instanceof ModeValue) {
						p.add(new ModeBox((ModeValue)v));
					}else if (v instanceof NumberValue) {
						
						NumberBox cb;
						
						NumberBox.NumberType type = NumberBox.NumberType.DECIMAL;
						
						if (v.getObject() instanceof Integer) {
                            type = NumberBox.NumberType.INTEGER;
                        } else if (v.getObject() instanceof Long) {
                            type = NumberBox.NumberType.TIME;
                        } else if (v.getObject() instanceof Float && ((NumberValue) v).getMin().intValue() == 0 && ((NumberValue) v).getMax().intValue() == 100) {
                            type = NumberBox.NumberType.PERCENT;
                        }
						p.add(cb = new NumberBox(v.getName(), ((Number)v.getObject()).floatValue(), ((NumberValue)v).getMin().floatValue(), ((NumberValue)v).getMax().floatValue(), type, (NumberValue)v));
						cb.setListener(val -> {
                            if (v.getObject() instanceof Integer) {
                                v.setObject(val.intValue());
                            }
                            if (v.getObject() instanceof Float) {
                                v.setObject(val.floatValue());
                            }
                            if (v.getObject() instanceof Long) {
                                v.setObject(val.longValue());
                            }
                            if (v.getObject() instanceof Double) {
                                v.setObject(val.doubleValue());
                            }

                            return true;
                        });
					}
				}else {
					p.clear();
				}
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int vstartX = res.getScaledWidth() / 2 + 45;
		int vrealX = vstartX + (Fonts.roboto22.getStringWidth("Value") / 2);
		x = vrealX;
		y = 65;
		w = 100;
		h = res.getScaledHeight() - 26;
		if (mod != null) {
			int startY2 = y + (int)jigsawFont.getHeight(mod.getName()) + 1;
			int startY = y + (int)jigsawFont.getHeight(mod.getName()) + 1;
			int startX = x;
			int count = 0;
			if (p.isEmpty()) {
				Fonts.roboto22.drawString("No-Value", x, 60 + Fonts.roboto22.FONT_HEIGHT + 2, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100), false);
			}else {
				for (ValueSlot vs : p) {
					if (count > 14) {
						vs.x = startX = x + 105;
						vs.y = startY2;
						vs.width = w;
						startY2 += vs.height;
					}else {
						vs.x = startX;
						vs.y = startY;
						vs.width = w;
						startY += vs.height;
					}
					vs.drawScreen(mouseX, mouseY, partialTicks);
					count++;
				}
			}
		}
	}
	
	public void Clear() {
		p.clear();
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		for (ValueSlot p : p) {
			p.isHovered(mouseX, mouseY);
		}
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
	
	public boolean isExtended() {
		return extended;
	}
	
	public void setExtended(boolean extended) {
		this.extended = extended;
	}
	
	public void setModule(Module mod) {
		this.mod = mod;
	}
	public void mouseReleased(int button, int x, int y) {
		for (ValueSlot p : p) {
			p.mouseReleased(button, x, y);
		}
    }
	public void mousePressed(int button, int x, int y) {
		for (ValueSlot p : p) {
			p.mousePressed(button, x, y);
		}
	}
	
	public void mouseMoved(int x, int y) {
		for (ValueSlot p : p) {
			p.mouseMoved(x, y);
		}
    }
}