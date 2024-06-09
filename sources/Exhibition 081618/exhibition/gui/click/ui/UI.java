package exhibition.gui.click.ui;

import exhibition.gui.click.ClickGui;
import exhibition.gui.click.components.Button;
import exhibition.gui.click.components.CategoryButton;
import exhibition.gui.click.components.CategoryPanel;
import exhibition.gui.click.components.Checkbox;
import exhibition.gui.click.components.ColorPreview;
import exhibition.gui.click.components.ConfigButton;
import exhibition.gui.click.components.ConfigList;
import exhibition.gui.click.components.DropdownBox;
import exhibition.gui.click.components.DropdownButton;
import exhibition.gui.click.components.GroupBox;
import exhibition.gui.click.components.HSVColorPicker;
import exhibition.gui.click.components.MainPanel;
import exhibition.gui.click.components.MultiDropdownBox;
import exhibition.gui.click.components.MultiDropdownButton;
import exhibition.gui.click.components.SLButton;
import exhibition.gui.click.components.Slider;
import exhibition.gui.click.components.TextBox;

public abstract class UI {
   public abstract void mainConstructor(ClickGui var1);

   public abstract void mainPanelDraw(MainPanel var1, int var2, int var3);

   public abstract void mainPanelKeyPress(MainPanel var1, int var2);

   public abstract void panelConstructor(MainPanel var1, float var2, float var3);

   public abstract void panelMouseClicked(MainPanel var1, int var2, int var3, int var4);

   public abstract void panelMouseMovedOrUp(MainPanel var1, int var2, int var3, int var4);

   public abstract void categoryButtonConstructor(CategoryButton var1, MainPanel var2);

   public abstract void categoryButtonMouseClicked(CategoryButton var1, MainPanel var2, int var3, int var4, int var5);

   public abstract void categoryButtonDraw(CategoryButton var1, float var2, float var3);

   public abstract void categoryPanelConstructor(CategoryPanel var1, CategoryButton var2, float var3, float var4);

   public abstract void categoryPanelMouseClicked(CategoryPanel var1, int var2, int var3, int var4);

   public abstract void categoryPanelDraw(CategoryPanel var1, float var2, float var3);

   public abstract void categoryPanelMouseMovedOrUp(CategoryPanel var1, int var2, int var3, int var4);

   public abstract void groupBoxConstructor(GroupBox var1, float var2, float var3);

   public abstract void groupBoxMouseClicked(GroupBox var1, int var2, int var3, int var4);

   public abstract void groupBoxDraw(GroupBox var1, float var2, float var3);

   public abstract void groupBoxMouseMovedOrUp(GroupBox var1, int var2, int var3, int var4);

   public abstract void buttonContructor(Button var1, CategoryPanel var2);

   public abstract void buttonMouseClicked(Button var1, int var2, int var3, int var4, CategoryPanel var5);

   public abstract void buttonDraw(Button var1, float var2, float var3, CategoryPanel var4);

   public abstract void buttonKeyPressed(Button var1, int var2);

   public abstract void checkBoxMouseClicked(Checkbox var1, int var2, int var3, int var4, CategoryPanel var5);

   public abstract void checkBoxDraw(Checkbox var1, float var2, float var3, CategoryPanel var4);

   public abstract void dropDownContructor(DropdownBox var1, float var2, float var3, CategoryPanel var4);

   public abstract void dropDownMouseClicked(DropdownBox var1, int var2, int var3, int var4, CategoryPanel var5);

   public abstract void dropDownDraw(DropdownBox var1, float var2, float var3, CategoryPanel var4);

   public abstract void dropDownButtonMouseClicked(DropdownButton var1, DropdownBox var2, int var3, int var4, int var5);

   public abstract void dropDownButtonDraw(DropdownButton var1, DropdownBox var2, float var3, float var4);

   public abstract void multiDropDownContructor(MultiDropdownBox var1, float var2, float var3, CategoryPanel var4);

   public abstract void multiDropDownMouseClicked(MultiDropdownBox var1, int var2, int var3, int var4, CategoryPanel var5);

   public abstract void multiDropDownDraw(MultiDropdownBox var1, float var2, float var3, CategoryPanel var4);

   public abstract void multiDropDownButtonMouseClicked(MultiDropdownButton var1, MultiDropdownBox var2, int var3, int var4, int var5);

   public abstract void multiDropDownButtonDraw(MultiDropdownButton var1, MultiDropdownBox var2, float var3, float var4);

   public abstract void SliderContructor(Slider var1, CategoryPanel var2);

   public abstract void SliderMouseClicked(Slider var1, int var2, int var3, int var4, CategoryPanel var5);

   public abstract void SliderMouseMovedOrUp(Slider var1, int var2, int var3, int var4, CategoryPanel var5);

   public abstract void SliderDraw(Slider var1, float var2, float var3, CategoryPanel var4);

   public abstract void categoryButtonMouseReleased(CategoryButton var1, int var2, int var3, int var4);

   public abstract void slButtonDraw(SLButton var1, float var2, float var3, MainPanel var4);

   public abstract void slButtonMouseClicked(SLButton var1, float var2, float var3, int var4, MainPanel var5);

   public abstract void colorConstructor(ColorPreview var1, float var2, float var3);

   public abstract void colorPrewviewDraw(ColorPreview var1, float var2, float var3);

   public abstract void colorPickerConstructor(HSVColorPicker var1, float var2, float var3);

   public abstract void colorPickerDraw(HSVColorPicker var1, float var2, float var3);

   public abstract void colorPickerClick(HSVColorPicker var1, float var2, float var3, int var4);

   public abstract void colorPickerMovedOrUp(HSVColorPicker var1, float var2, float var3, int var4);

   public abstract void configButtonDraw(ConfigButton var1, float var2, float var3);

   public abstract void configButtonMouseClicked(ConfigButton var1, float var2, float var3, int var4);

   public abstract void configListDraw(ConfigList var1, float var2, float var3);

   public abstract void configListMouseClicked(ConfigList var1, float var2, float var3, int var4);

   public abstract void textBoxDraw(TextBox var1, float var2, float var3);

   public abstract void textBoxMouseClicked(TextBox var1, int var2, int var3, int var4);

   public abstract void textBoxKeyPressed(TextBox var1, int var2);
}
