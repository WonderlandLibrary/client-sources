package com.example.editme.gui;

import com.example.editme.EditmeMod;
import com.example.editme.commands.Command;
import com.example.editme.util.command.syntax.SyntaxChunk;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class EditmeGuiChat extends GuiChat {
   private String startString;
   private int cursor;
   private String currentFillinLine;

   public EditmeGuiChat(String var1, String var2, int var3) {
      super(var1);
      this.startString = var1;
      if (!var1.equals(Command.getCommandPrefix())) {
         this.calculateCommand(var1.substring(Command.getCommandPrefix().length()));
      }

      this.field_146410_g = var2;
      this.cursor = var3;
   }

   public void func_73863_a(int var1, int var2, float var3) {
      func_73734_a(2, this.field_146295_m - 14, this.field_146294_l - 2, this.field_146295_m - 2, Integer.MIN_VALUE);
      int var4 = this.field_146415_a.field_146211_a.func_78256_a(String.valueOf((new StringBuilder()).append(this.field_146415_a.func_146179_b()).append(""))) + 4;
      int var5 = this.field_146415_a.func_146181_i() ? this.field_146415_a.field_146210_g + (this.field_146415_a.field_146219_i - 8) / 2 : this.field_146415_a.field_146210_g;
      this.field_146415_a.field_146211_a.func_175063_a(this.currentFillinLine, (float)var4, (float)var5, 6710886);
      this.field_146415_a.func_146194_f();
      ITextComponent var6 = this.field_146297_k.field_71456_v.func_146158_b().func_146236_a(Mouse.getX(), Mouse.getY());
      if (var6 != null && var6.func_150256_b().func_150210_i() != null) {
         this.func_175272_a(var6, var1, var2);
      }

      boolean var7 = GL11.glIsEnabled(3042);
      boolean var8 = GL11.glIsEnabled(3553);
      GL11.glDisable(3042);
      GL11.glDisable(3553);
      GL11.glColor3f(0.6F, 0.0F, 0.6F);
      GL11.glBegin(1);
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f - 2), (float)(this.field_146415_a.field_146210_g - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f + this.field_146415_a.func_146200_o() - 2), (float)(this.field_146415_a.field_146210_g - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f + this.field_146415_a.func_146200_o() - 2), (float)(this.field_146415_a.field_146210_g - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f + this.field_146415_a.func_146200_o() - 2), (float)(this.field_146415_a.field_146210_g + this.field_146415_a.field_146219_i - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f + this.field_146415_a.func_146200_o() - 2), (float)(this.field_146415_a.field_146210_g + this.field_146415_a.field_146219_i - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f - 2), (float)(this.field_146415_a.field_146210_g + this.field_146415_a.field_146219_i - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f - 2), (float)(this.field_146415_a.field_146210_g + this.field_146415_a.field_146219_i - 2));
      GL11.glVertex2f((float)(this.field_146415_a.field_146209_f - 2), (float)(this.field_146415_a.field_146210_g - 2));
      GL11.glEnd();
      if (var7) {
         GL11.glEnable(3042);
      }

      if (var8) {
         GL11.glEnable(3553);
      }

   }

   protected void func_73869_a(char var1, int var2) throws IOException {
      this.field_146416_h = this.cursor;
      super.func_73869_a(var1, var2);
      this.cursor = this.field_146416_h;
      String var3 = this.field_146415_a.func_146179_b();
      if (!var3.startsWith(Command.getCommandPrefix())) {
         GuiChat var4 = new GuiChat(this, var3) {
            int cursor;
            final EditmeGuiChat this$0;

            {
               this.this$0 = var1;
               this.cursor = this.this$0.cursor;
            }

            protected void func_73869_a(char var1, int var2) throws IOException {
               this.field_146416_h = this.cursor;
               super.func_73869_a(var1, var2);
               this.cursor = this.field_146416_h;
            }
         };
         var4.field_146410_g = this.field_146410_g;
         this.field_146297_k.func_147108_a(var4);
      } else if (var3.equals(Command.getCommandPrefix())) {
         this.currentFillinLine = "";
      } else {
         this.calculateCommand(var3.substring(Command.getCommandPrefix().length()));
      }
   }

   protected void calculateCommand(String var1) {
      String[] var2 = var1.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      HashMap var3 = new HashMap();
      if (var2.length != 0) {
         Iterator var4 = EditmeMod.getInstance().getCommandManager().getCommands().iterator();

         while(true) {
            Command var5;
            do {
               if (!var4.hasNext()) {
                  if (var3.isEmpty()) {
                     this.currentFillinLine = "";
                     return;
                  }

                  TreeMap var11 = new TreeMap(var3);
                  var5 = (Command)var11.firstEntry().getValue();
                  this.currentFillinLine = var5.getLabel().substring(var2[0].length());
                  if (var5.getSyntaxChunks() != null && var5.getSyntaxChunks().length != 0) {
                     if (!var1.endsWith(" ")) {
                        this.currentFillinLine = String.valueOf((new StringBuilder()).append(this.currentFillinLine).append(" "));
                     }

                     SyntaxChunk[] var6 = var5.getSyntaxChunks();
                     boolean var7 = false;

                     for(int var8 = 0; var8 < var6.length; ++var8) {
                        if (var8 + 1 >= var2.length - 1) {
                           SyntaxChunk var9 = var6[var8];
                           String var10 = var9.getChunk(var6, var9, var2, var8 + 1 == var2.length - 1 ? var2[var8 + 1] : null);
                           if (var10 != "" && (!var10.startsWith("<") || !var10.endsWith(">")) && (!var10.startsWith("[") || !var10.endsWith("]"))) {
                              var7 = true;
                           }

                           this.currentFillinLine = String.valueOf((new StringBuilder()).append(this.currentFillinLine).append(var10).append(var10 == "" ? "" : " ").append(""));
                        }
                     }

                     if (var7) {
                        this.currentFillinLine = this.currentFillinLine.substring(1);
                     }

                     return;
                  }

                  return;
               }

               var5 = (Command)var4.next();
            } while((!var5.getLabel().startsWith(var2[0]) || var1.endsWith(" ")) && !var5.getLabel().equals(var2[0]));

            var3.put(var5.getLabel(), var5);
         }
      }
   }

   static int access$000(EditmeGuiChat var0) {
      return var0.cursor;
   }
}
