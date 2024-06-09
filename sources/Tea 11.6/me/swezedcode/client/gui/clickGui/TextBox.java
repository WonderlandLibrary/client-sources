package me.swezedcode.client.gui.clickGui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import me.swezedcode.client.utils.values.NumberValue;
import me.swezedcode.client.utils.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;

public class TextBox extends Component {

	private Value value;

	public boolean typing;

	private Predicate field_175209_y = Predicates.alwaysTrue();
	private GuiPageButtonList.GuiResponder field_175210_x;
	private int field_175208_g;

	String text = "0";

	public static FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

	public TextBox(Value value, Component parent) {
		this.parent = parent;
		this.value = value;
		this.width = parent.width - 4;
		this.height = 12;
		this.renderWidth = width;
		this.renderHeight = height;
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks, int parX, int parY, GuiScreen screen) {
		Value val = (NumberValue) value;
		GL11.glPushMatrix();
		GL11.glTranslatef(this.parent.x, this.parent.y, 0);
		this.absx = parX + this.x;
		this.absy = parY + this.y;
		screen.drawRect(x + 118, y - 1, x + width / 8 + 102, y + height, 0xFF457A8C);
		screen.drawRect(x - 2, y - 1, x + width / 8 - 14, y + height, 0xFF31484D);
		screen.drawRect(x, y, x + width, y + height, 0xFF31484D);
		String test = text;
		if (!typing) {
			test = val.getName() + ": " + val.getValue();
		}
		fr.drawStringWithShadow(test, x + 2, y + 2, 0xFFFFFFFF);
		GL11.glPopMatrix();
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!this.isVisible) {
			return;
		}
		if (mouseX >= this.absx && mouseX <= this.absx + this.width) {
			if (mouseY >= this.absy && mouseY <= this.absy + this.height) {
				if (mouseButton == 0) {
					this.typing = true;
				}
			}
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		final NumberValue val;
		val = (NumberValue) value;
		if (this.typing) {
			this.typing = true;
			if (keyCode == Keyboard.KEY_DELETE) {
				val.setValue(val.getValue());
				typing = false;
			} else if (keyCode == Keyboard.KEY_RETURN) {
				val.setValue(Integer.parseInt(text));
				typing = false;
			} else if (keyCode == Keyboard.KEY_BACK) {
				deleteWords(keyCode);
			} else {
				this.writeText(Character.toString(typedChar));
			}
		}
	}

	@Override
	public void keyTypedNum(int typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub

	}

	public void deleteWords(int p_146177_1_) {
		if (this.text.length() != 0) {
			this.writeText("");
		}
	}

	public void writeText(String p_146191_1_) {
		String var2 = "";
		String var3 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
		
		if (this.field_175209_y.apply(var2)) {
			this.text = var2;
		}
	}

}