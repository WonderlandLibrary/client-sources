package ru.FecuritySQ.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.clickgui.elements.Element;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.utils.MathUtil;
import ru.FecuritySQ.utils.RandomString;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alt extends Element {
    private String name;
    private String date;
    private boolean edit = false;
    public RandomString randomString = new RandomString(12);
    protected TextFieldWidget textfield;
    public float animation = 0;
    public float animation2 = 0;
    public boolean toRemove;
    private String waring = "Ник слишком короткий. Минимум 3 символа!";

    public Alt(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        this.name = randomString.nextString();
        this.date = formatter.format(date);
        this.width = 250;
        this.height = 30;
        textfield = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int) (this.width / 2), 50, 100, 20, new TranslationTextComponent("FLEX")) {};
        textfield.setMaxStringLength(14);
        textfield.setVisible(true);
        textfield.setEnabled(true);
        textfield.setText(name);
        textfield.setEnableBackgroundDrawing(false);
    }

    public Alt(String name){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        this.name = name;
        this.date = formatter.format(date);
        this.width = 250;
        this.height = 30;
        textfield = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int) (this.width / 2), 50, 100, 20, new TranslationTextComponent("FLEX")) {};
        textfield.setMaxStringLength(14);
        textfield.setVisible(true);
        textfield.setEnabled(true);
        textfield.setText(name);
        textfield.setEnableBackgroundDrawing(false);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GL11.glTranslated(this.x + width / 2, this.y + height / 2, 0);
        GL11.glScaled(animation, animation, 1);
        GL11.glTranslated((-this.x) -width / 2, (-this.y) -height / 2, 0);
        this.animation = (float) MathUtil.animation( 1, this.animation, 0.0001f);

        if(toRemove){
            this.animation2 = (float) MathUtil.animate(-200, this.animation2, 0.09f);
            GL11.glTranslated(0, -animation2, 0);
            if(animation2 < -100){
                AltManagerScreen.alts.remove(this);
                toRemove = false;
            }
        }

        RenderUtil.drawRound((float) this.x, (float) this.y, (float) this.width, (float) this.height, 5, new Color(30, 30, 30, 200).getRGB());
        int color = textfield.getText().length() > 2 ? -1 : new Color(255, 0, 0).getRGB();

        boolean isWarn = this.textfield.getText().equals(waring);


        if(isWarn) color = new Color(255, 0, 0).getRGB();

        Fonts.mntsb16.drawCenteredString(new MatrixStack(), this.textfield.getText(), this.x + this.width / 2, this.y + this.height / 2 - 1, color);

        if(!isWarn){
            RenderUtil.drawRound((float) this.x + 8, (float) this.y + 5, 80, 20, 5, new Color(5, 5, 5, 100).getRGB());
            RenderUtil.drawImage(new MatrixStack(), new ResourceLocation("FecuritySQ/icons/unknown.png"), (float) this.x + 5 + 8, (float) ((float) this.y + height / 2) - 8, 16, 16);
            Fonts.MCR8.drawString(new MatrixStack(), "Без скина", this.x + 32, this.y + 8, -1);
            Fonts.MCR8.drawString(new MatrixStack(), date, this.x + 32, this.y + 14, -1);
        }

        RenderUtil.drawImage(new MatrixStack(), new ResourceLocation("FecuritySQ/icons/delete.png"), (float) ((float) this.x + this.width - 10), (float) (float) this.y + 2, 8, 8);
        RenderUtil.drawImage(new MatrixStack(), new ResourceLocation("FecuritySQ/icons/edit.png"), (float) ((float) this.x + this.width - 20), (float) (float) this.y + 2, 8, 8);

        if(edit){
            RenderUtil.drawImage(new MatrixStack(), new ResourceLocation("FecuritySQ/icons/apply.png"), (float) ((float) this.x + this.width - 31), (float) (float) this.y + 3, 8, 8);
        }

        textfield.setFocused2(edit);

        //RenderUtil.drawRound(textfield.x, textfield.y, textfield.getWidth(), textfield.getHeightRealms(), 3, new Color(10, 10, 10).getRGB());
        textfield.x = (int) (this.x + this.width / 2) - textfield.getWidth() / 2;
        textfield.y = (int) ((int) this.y + this.height / 2) - textfield.getHeightRealms() / 2;

        if(mc.getSession().getUsername().toLowerCase().equals(textfield.getText().toLowerCase())){
            String active = "Активен";
            Fonts.MCR8.drawString(new MatrixStack(), active, this.x + this.width - Fonts.MCR8.getStringWidth(active) - 10, this.y + 12, new Color(115, 255, 0).getRGB());
        }

        GlStateManager.popMatrix();
    }

    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        if(textfield.getText().contains(waring)){
            textfield.setText("");
            textfield.setMaxStringLength(15);
        }
        if(keyCode == GLFW.GLFW_KEY_ENTER && edit){
            apply();
        }
        textfield.keyPressed(keyCode, scanCode, modifiers);
    }

    public void charTyped(char codePoint, int modifiers) {
        if(textfield.getText().contains(waring)){
            textfield.setText("");
            textfield.setMaxStringLength(15);
        }
        textfield.charTyped(codePoint, modifiers);
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if(MathUtil.collided((float) ((float) this.x + this.width - 20), (float) (float) this.y + 2,8,8, x, y) && button == 0){
            edit = true;
        }
        if(MathUtil.collided( (float) ((float) this.x + this.width - 10), (float) (float) this.y + 2, 8, 8, x, y) && button == 0){
            toRemove = true;
        }
        if(MathUtil.collided((float) ((float) this.x + this.width - 31), (float) (float) this.y + 3, 8, 8, x, y) && button == 0){
           apply();
        }
        if(button == 0 && collided(x, y) && !edit){

            mc.getSession().username = textfield.getText();
        }
    }

    private void apply(){
        if(textfield.getText().length() > 2 && !textfield.getText().equals(waring)) {
            edit = false;
            mc.getSession().username = textfield.getText();
        }else{
            textfield.setMaxStringLength(100);
            textfield.setText(waring);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
