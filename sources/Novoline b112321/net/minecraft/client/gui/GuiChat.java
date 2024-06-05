package net.minecraft.client.gui;

import cc.novoline.modules.visual.KeyStrokes;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import net.J3;
import net.a6_;
import net.aLM;
import net.arc;
import net.asx;
import net.ava;
import net.dI;
import net.gZ;
import net.sT;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen {
   private String historyBuffer = "";
   private boolean F;
   private boolean w;
   private boolean U;
   private boolean M;
   private boolean y;
   private boolean R;
   private int D;
   private int N;
   private int C;
   private int x;
   private int J;
   private int z;
   private int I;
   private int G;
   private int E;
   private int L;
   private int A;
   private int H;
   private int sentHistoryCursor = -1;
   private boolean playerNamesFound;
   private boolean waitingOnAutocomplete;
   private int autocompleteIndex;
   private final List foundPlayerNames = Lists.newArrayList();
   protected GuiTextField inputField;
   private String defaultInputFieldText = "";

   public GuiChat() {
   }

   public GuiChat(String var1) {
      this.defaultInputFieldText = var1;
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
      this.inputField = new GuiTextField(0, this.mc.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
      this.inputField.setMaxStringLength(200);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setFocused(true);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.setCanLoseFocus(false);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.mc.ingameGUI.getChatGUI().resetScroll();
   }

   public void updateScreen() {
      this.inputField.updateCursorCounter();
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      this.waitingOnAutocomplete = false;
      if(var2 == 15) {
         this.autocompletePlayerNames();
      } else {
         this.playerNamesFound = false;
      }

      if(var2 == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
      } else if(var2 != 28 && var2 != 156) {
         switch(var2) {
         case 200:
            this.getSentHistory(-1);
            break;
         case 201:
            this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            break;
         case 202:
         case 203:
         case 204:
         case 205:
         case 206:
         case 207:
         default:
            this.inputField.textboxKeyTyped(var1, var2);
            break;
         case 208:
            this.getSentHistory(1);
            break;
         case 209:
            this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
         }
      } else {
         String var3 = this.inputField.getText().trim();
         if(!var3.isEmpty()) {
            this.sendChatMessage(var3);
         }

         this.mc.displayGuiScreen((GuiScreen)null);
      }

   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if(var1 > 1) {
         var1 = 1;
      } else if(var1 < -1) {
         var1 = -1;
      }

      if(!isShiftKeyDown()) {
         var1 *= 7;
      }

      this.mc.ingameGUI.getChatGUI().scroll(var1);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      int var4 = dI.a(this.mc, var1, var2)[0];
      int var5 = dI.a(this.mc, var1, var2)[1];
      ava var6 = (ava)gZ.g().d().b(ava.class);
      arc var7 = (arc)gZ.g().d().b(arc.class);
      KeyStrokes var8 = (KeyStrokes)gZ.g().d().b(KeyStrokes.class);
      if(this.a(var4, var5)) {
         asx var9 = (asx)gZ.g().d().b(asx.class);
         this.D = ((Integer)var9.o().a()).intValue() - var4;
         this.N = ((Integer)var9.l().a()).intValue() - var5;
         this.F = true;
      } else if(this.b(var4, var5, var6) && ((List)var6.F().a()).contains("Inventory")) {
         this.C = ((Integer)var6.b().a()).intValue() - var4;
         this.x = ((Integer)var6.E().a()).intValue() - var5;
         this.w = true;
      } else if(this.a(var4, var5, var7) && var7.y()) {
         this.J = ((Integer)var7.c().a()).intValue() - var4;
         this.z = ((Integer)var7.d().a()).intValue() - var5;
         this.U = true;
      } else if(this.c(var4, var5, var6) && ((List)var6.F().a()).contains("TargetsList")) {
         this.I = ((Integer)var6.t().a()).intValue() - var4;
         this.G = ((Integer)var6.m().a()).intValue() - var5;
         this.M = true;
      } else if(this.a(var4, var5, var8)) {
         this.E = ((Integer)var8.g().a()).intValue() - var4;
         this.L = ((Integer)var8.c().a()).intValue() - var5;
         this.y = true;
      } else if(this.a(var4, var5, var6) && ((List)var6.F().a()).contains("Stats")) {
         this.A = ((Integer)var6.y().a()).intValue() - var4;
         this.H = ((Integer)var6.v().a()).intValue() - var5;
         this.R = true;
      }

      IChatComponent var10 = this.mc.ingameGUI.getChatGUI().a(Mouse.getX(), Mouse.getY() - 15);
      if(!this.a(var10)) {
         this.inputField.mouseClicked(var1, var2, var3);
         super.mouseClicked(var1, var2, var3);
      }
   }

   protected void setText(String var1, boolean var2) {
      this.inputField.setText(var1);
   }

   public void autocompletePlayerNames() {
      if(this.playerNamesFound) {
         this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
         if(this.autocompleteIndex >= this.foundPlayerNames.size()) {
            this.autocompleteIndex = 0;
         }
      } else {
         int var1 = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
         this.foundPlayerNames.clear();
         this.autocompleteIndex = 0;
         String var2 = this.inputField.getText().substring(var1).toLowerCase();
         String var3 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
         this.sendAutocompleteRequest(var3, var2);
         if(this.foundPlayerNames.isEmpty()) {
            return;
         }

         this.playerNamesFound = true;
         this.inputField.deleteFromCursor(var1 - this.inputField.getCursorPosition());
      }

      if(this.foundPlayerNames.size() > 1) {
         StringBuilder var4 = new StringBuilder();

         for(String var6 : this.foundPlayerNames) {
            if(var4.length() > 0) {
               var4.append(", ");
            }

            var4.append(var6);
         }

         this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(var4.toString()), 1);
      }

      this.inputField.writeText((String)this.foundPlayerNames.get(this.autocompleteIndex++));
   }

   private void sendAutocompleteRequest(String var1, String var2) {
      if(var1.length() >= 1) {
         BlockPos var3 = null;
         if(this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
            var3 = this.mc.objectMouseOver.getBlockPos();
         }

         this.mc.thePlayer.sendQueue.b(new C14PacketTabComplete(var1, var3));
         this.waitingOnAutocomplete = true;
      }

   }

   public void getSentHistory(int var1) {
      int var2 = this.sentHistoryCursor + var1;
      int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
      var2 = MathHelper.a(var2, 0, var3);
      if(var2 != this.sentHistoryCursor) {
         if(var2 == var3) {
            this.sentHistoryCursor = var3;
            this.inputField.setText(this.historyBuffer);
         } else {
            if(this.sentHistoryCursor == var3) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
            this.sentHistoryCursor = var2;
         }
      }

   }

   public float a(EntityPlayer var1) {
      return (float)(35 + this.mc.a.d(var1.getName()) + 34);
   }

   public void drawScreen(int var1, int var2, float var3) {
      drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
      this.inputField.m();
      IChatComponent var4 = this.mc.ingameGUI.getChatGUI().a(Mouse.getX(), Mouse.getY() - 15);
      if(var4.getChatStyle().getChatHoverEvent() != null) {
         this.handleComponentHover(var4, var1, var2 - 5);
      }

      asx var5 = (asx)gZ.g().d().b(asx.class);
      ScaledResolution var6 = new ScaledResolution(this.mc);
      if(var5.n() == null || var5.n() instanceof EntityPlayer) {
         int var7 = dI.a(this.mc, var1, var2)[0];
         int var8 = dI.a(this.mc, var1, var2)[1];
         if(this.F) {
            var5.o().b((Number)Integer.valueOf(MathHelper.a(this.D + var7, 1, (int)((float)((Integer)var5.o().a()).intValue() - this.a((EntityPlayer)(!var5.P() && !var5.E()?this.mc.thePlayer:(EntityPlayer)var5.n()))))));
            var5.l().b((Number)Integer.valueOf(MathHelper.a(this.N + var8, 1, ((Integer)var5.l().a()).intValue() - 42)));
         }

         if(((Boolean)var5.C().a()).booleanValue() && (var5.P() || var5.E() || this.mc.thePlayer != null)) {
            a6_.a((asx)var5, (EntityLivingBase)(!var5.P() && !var5.E()?this.mc.thePlayer:(EntityPlayer)var5.n()));
            if(this.a(var7, var8)) {
               if(((Integer)var5.o().a()).intValue() > var6.a(this.mc) - 50) {
                  var5.o().b((Number)Integer.valueOf(var6.a(this.mc) - 50));
               }

               if(((Integer)var5.l().a()).intValue() > var6.b(this.mc) - 50) {
                  var5.l().b((Number)Integer.valueOf(var6.b(this.mc) - 50));
               }

               Object var9 = var5.n() != null?(EntityLivingBase)var5.n():this.mc.thePlayer;
               dI.a(this.mc);
               if(var5.B().a("Prettiest")) {
                  J3.a((double)((Integer)var5.o().a()).intValue(), (double)((float)((Integer)var5.l().a()).intValue() - 1.5F), (double)((float)((Integer)var5.o().a()).intValue() + this.a((EntityPlayer)(!var5.P() && !var5.E()?this.mc.thePlayer:(EntityPlayer)var5.n()))), (double)((Integer)var5.l().a()).intValue() + 37.5D, 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
               } else if(var5.B().a("Prettier")) {
                  int var10 = 0;

                  for(int var11 = 0; var11 < ((EntityPlayer)var9).bJ.armorInventory.length; ++var11) {
                     ItemStack var12 = ((EntityPlayer)var9).bJ.armorInventory[var11];
                     ++var10;
                  }

                  if(((EntityPlayer)var9).getCurrentEquippedItem() != null) {
                     ++var10;
                  }

                  float var33 = (float)Math.max(this.mc.fontRendererObj.d(((EntityLivingBase)var9).getName()) + 45, 16 * var10 + 45);
                  J3.a((double)((Integer)var5.o().a()).intValue(), (double)((float)((Integer)var5.l().a()).intValue() - 1.5F), (double)((float)((Integer)var5.o().a()).intValue() + var33), (double)((Integer)var5.l().a()).intValue() + 42.5D, 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
               } else if(var5.B().a("Pretty")) {
                  double var25 = (double)Math.max(sT.a.a(((EntityLivingBase)var9).getName()), aLM.a.a("Health: " + String.format("%.1f", new Object[]{Float.valueOf(((EntityLivingBase)var9).getHealth())}).replace(",", ".")));
                  J3.a((double)((Integer)var5.o().a()).intValue(), (double)((float)((Integer)var5.l().a()).intValue() - 1.5F), (double)((Integer)var5.o().a()).intValue() + var25 + 68.0D, (double)((Integer)var5.l().a()).intValue() + 52.5D, 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
               } else {
                  if(var5.B().a("Less Pretty")) {
                     ObjectArrayList var32 = new ObjectArrayList();
                     int var38 = 3;

                     while(true) {
                        ItemStack var41 = ((EntityPlayer)var9).bJ.armorInventory[var38];
                        var32.add(var41);
                        --var38;
                     }
                  }

                  if(var5.B().a("Trash")) {
                     double var26 = (double)(this.mc.fontRendererObj.d(((EntityLivingBase)var9).getName()) + 100);
                     J3.a((double)((Integer)var5.o().a()).intValue(), (double)((float)((Integer)var5.l().a()).intValue() - 1.5F), (double)((Integer)var5.o().a()).intValue() + var26, (double)((Integer)var5.l().a()).intValue() + 60.5D, 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
                  }
               }
            }
         }
      }

      ava var15 = (ava)gZ.g().d().b(ava.class);
      if(var15.y()) {
         if(((List)var15.F().a()).contains("Stats")) {
            dI.a(this.mc);
            int var16 = ((Integer)var15.y().a()).intValue();
            int var20 = ((Integer)var15.v().a()).intValue();
            int var27 = dI.a(this.mc, var1, var2)[0];
            int var34 = dI.a(this.mc, var1, var2)[1];
            if(this.R) {
               var15.y().b((Number)Integer.valueOf(MathHelper.a(this.A + var27, 1, ((Integer)var15.y().a()).intValue() - 167)));
               var15.v().b((Number)Integer.valueOf(MathHelper.a(this.H + var34, 1, ((Integer)var15.v().a()).intValue() - 73)));
            }

            if(this.a(var27, var34, var15)) {
               if(((Integer)var15.y().a()).intValue() > var6.a(this.mc) - 50) {
                  var15.y().b((Number)Integer.valueOf(var6.a(this.mc) - 50));
               }

               if(((Integer)var15.v().a()).intValue() > var6.b(this.mc) - 50) {
                  var15.v().b((Number)Integer.valueOf(var6.b(this.mc) - 50));
               }

               dI.a(this.mc);
               a6_.a((float)((Integer)var15.y().a()).intValue(), (float)((Integer)var15.v().a()).intValue(), (float)(((Integer)var15.y().a()).intValue() + 200), (float)(((Integer)var15.v().a()).intValue() + 92), 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
            }
         }

         if(((List)var15.F().a()).contains("Inventory")) {
            dI.a(this.mc);
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableDepth();
            int var17 = ((Integer)var15.b().a()).intValue();
            int var21 = ((Integer)var15.E().a()).intValue();
            Gui.drawRect(var17, var21, var17 + 167, var21 + 73, (new Color(29, 29, 29, 255)).getRGB());
            Gui.drawRect(var17 + 1, var21 + 13, var17 + 166, var21 + 72, (new Color(40, 40, 40, 255)).getRGB());
            this.mc.a.drawString("Your Inventory", (float)(var17 + 3), (float)(var21 + 3), -1, true);
            boolean var28 = false;

            for(int var35 = 9; var35 < this.mc.thePlayer.bo.inventorySlots.size() - 9; ++var35) {
               Slot var39 = (Slot)this.mc.thePlayer.bo.inventorySlots.get(var35);
               if(var39.getHasStack()) {
                  var28 = true;
               }

               int var13 = var39.xDisplayPosition;
               int var14 = var39.yDisplayPosition;
               this.mc.a().b(var39.getStack(), (float)(var17 + var13 - 4), (float)(var21 + var14 - 68));
               this.mc.a().a(this.mc.a, var39.getStack(), var17 + var13 - 4, var21 + var14 - 68, (String)null);
            }

            this.mc.a.drawString("Empty...", (float)(var17 + 83 - this.mc.a.d("Empty...") / 2), (float)(var21 + 36), -1, true);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableDepth();
            int var36 = dI.a(this.mc, var1, var2)[0];
            int var40 = dI.a(this.mc, var1, var2)[1];
            if(this.w) {
               var15.b().b((Number)Integer.valueOf(MathHelper.a(this.C + var36, 1, ((Integer)var15.b().a()).intValue() - 167)));
               var15.E().b((Number)Integer.valueOf(MathHelper.a(this.x + var40, 1, ((Integer)var15.E().a()).intValue() - 73)));
            }

            if(this.b(var36, var40, var15)) {
               if(((Integer)var15.b().a()).intValue() > var6.a(this.mc) - 50) {
                  var15.b().b((Number)Integer.valueOf(var6.a(this.mc) - 50));
               }

               if(((Integer)var15.E().a()).intValue() > var6.b(this.mc) - 50) {
                  var15.E().b((Number)Integer.valueOf(var6.b(this.mc) - 50));
               }

               dI.a(this.mc);
               a6_.a((float)((Integer)var15.b().a()).intValue(), (float)((Integer)var15.E().a()).intValue(), (float)(((Integer)var15.b().a()).intValue() + 167), (float)(((Integer)var15.E().a()).intValue() + 73), 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
            }
         }

         if(((List)var15.F().a()).contains("TargetsList")) {
            dI.a(this.mc);
            var15.i();
            int var18 = dI.a(this.mc, var1, var2)[0];
            int var22 = dI.a(this.mc, var1, var2)[1];
            if(this.M) {
               var15.t().b((Number)Integer.valueOf(MathHelper.a(this.I + var18, 1, (int)((double)((Integer)var15.t().a()).intValue() - var15.k()[0]))));
               var15.m().b((Number)Integer.valueOf(MathHelper.a(this.G + var22, 1, (int)((double)((Integer)var15.m().a()).intValue() - var15.k()[1]))));
            }

            if(this.c(var18, var22, var15)) {
               if(((Integer)var15.t().a()).intValue() > var6.a(this.mc) - 50) {
                  var15.t().b((Number)Integer.valueOf(var6.a(this.mc) - 50));
               }

               if(((Integer)var15.m().a()).intValue() > var6.b(this.mc) - 50) {
                  var15.m().b((Number)Integer.valueOf(var6.b(this.mc) - 50));
               }

               dI.a(this.mc);
               J3.a((double)((Integer)var15.t().a()).intValue(), (double)(((Integer)var15.m().a()).intValue() - 13), (double)((Integer)var15.t().a()).intValue() + var15.k()[0], (double)((Integer)var15.m().a()).intValue() + var15.k()[1], 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
            }
         }
      }

      arc var19 = (arc)gZ.g().d().b(arc.class);
      if(var19.y()) {
         var19.b();
         int var23 = dI.a(this.mc, var1, var2)[0];
         int var30 = dI.a(this.mc, var1, var2)[1];
         if(this.U) {
            var19.c().b((Number)Integer.valueOf(MathHelper.a(this.J + var23, 1, ((Integer)var19.c().a()).intValue() - ((Integer)var19.a().a()).intValue())));
            var19.d().b((Number)Integer.valueOf(MathHelper.a(this.z + var30, 1, ((Integer)var19.d().a()).intValue() - ((Integer)var19.a().a()).intValue())));
         }

         if(this.a(var23, var30, var19)) {
            if(((Integer)var19.c().a()).intValue() > var6.a(this.mc) - 50) {
               var19.c().b((Number)Integer.valueOf(var6.a(this.mc) - 50));
            }

            if(((Integer)var19.d().a()).intValue() > var6.b(this.mc) - 50) {
               var19.d().b((Number)Integer.valueOf(var6.b(this.mc) - 50));
            }

            dI.a(this.mc);
            a6_.a((float)((Integer)var19.c().a()).intValue(), (float)((Integer)var19.d().a()).intValue(), (float)(((Integer)var19.c().a()).intValue() + ((Integer)var19.a().a()).intValue()), (float)(((Integer)var19.d().a()).intValue() + ((Integer)var19.a().a()).intValue()), 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
         }
      }

      KeyStrokes var24 = (KeyStrokes)gZ.g().d().b(KeyStrokes.class);
      if(var24.y()) {
         int var31 = dI.a(this.mc, var1, var2)[0];
         int var37 = dI.a(this.mc, var1, var2)[1];
         if(this.y) {
            var24.g().b((Number)Integer.valueOf(MathHelper.a(this.E + var31, 1, ((Integer)var24.g().a()).intValue() - 40)));
            var24.c().b((Number)Integer.valueOf(MathHelper.a(this.L + var37, 1, ((Integer)var24.c().a()).intValue() - 40)));
         }

         if(this.a(var31, var37, var24)) {
            if(((Integer)var24.g().a()).intValue() > var6.a(this.mc) - 50) {
               var24.g().b((Number)Integer.valueOf(var6.a(this.mc) - 50));
            }

            if(((Integer)var24.c().a()).intValue() > var6.b(this.mc) - 50) {
               var24.c().b((Number)Integer.valueOf(var6.b(this.mc) - 50));
            }

            dI.a(this.mc);
            a6_.a((float)((Integer)var24.g().a()).intValue(), (float)((Integer)var24.c().a()).intValue(), (float)(((Integer)var24.g().a()).intValue() + 81), (float)(((Integer)var24.c().a()).intValue() + 81), 2.0F, -1, (new Color(0, 0, 0, 150)).getRGB());
         }
      }

      super.drawScreen(var1, var2, var3);
   }

   private boolean a(int var1, int var2) {
      asx var3 = (asx)gZ.g().d().b(asx.class);
      Object var4 = var3.n() != null?(EntityLivingBase)var3.n():this.mc.thePlayer;
      if(var3.B().a("Prettiest")) {
         return var1 >= ((Integer)var3.o().a()).intValue() && (float)var1 <= (float)((Integer)var3.o().a()).intValue() + this.a(this.mc.thePlayer) && (double)var2 >= (double)((Integer)var3.l().a()).intValue() - 1.5D && (double)var2 <= (double)((Integer)var3.l().a()).intValue() + 37.5D;
      } else if(var3.B().a("Prettier")) {
         int var10 = 0;

         for(int var11 = 0; var11 < ((EntityPlayer)var4).bJ.armorInventory.length; ++var11) {
            ItemStack var12 = ((EntityPlayer)var4).bJ.armorInventory[var11];
            ++var10;
         }

         if(((EntityPlayer)var4).getCurrentEquippedItem() != null) {
            ++var10;
         }

         return var1 >= ((Integer)var3.o().a()).intValue() && var1 <= ((Integer)var3.o().a()).intValue() + Math.max(this.mc.fontRendererObj.d(var3.n() != null?var3.n().getName():this.mc.thePlayer.getName()) + 45, 16 * var10 + 45) && (double)var2 >= (double)((Integer)var3.l().a()).intValue() - 1.5D && (double)var2 <= (double)((Integer)var3.l().a()).intValue() + 42.5D;
      } else if(var3.B().a("Pretty")) {
         double var9 = (double)Math.max(sT.a.a(((EntityLivingBase)var4).getName()), aLM.a.a("Health: " + String.format("%.1f", new Object[]{Float.valueOf(((EntityLivingBase)var4).getHealth())}).replace(",", ".")));
         return var1 >= ((Integer)var3.o().a()).intValue() && (double)var1 <= (double)((Integer)var3.o().a()).intValue() + var9 + 68.0D && (double)var2 >= (double)((Integer)var3.l().a()).intValue() - 1.5D && (double)var2 <= (double)((Integer)var3.l().a()).intValue() + 52.5D;
      } else {
         if(var3.B().a("Less Pretty")) {
            ObjectArrayList var8 = new ObjectArrayList();
            int var6 = 3;

            while(true) {
               ItemStack var7 = ((EntityPlayer)var4).bJ.armorInventory[var6];
               var8.add(var7);
               --var6;
            }
         }

         double var5 = (double)(this.mc.fontRendererObj.d(((EntityLivingBase)var4).getName()) + 100);
         return var1 >= ((Integer)var3.o().a()).intValue() && (double)var1 <= (double)((Integer)var3.o().a()).intValue() + var5 && (double)var2 >= (double)((Integer)var3.l().a()).intValue() - 1.5D && (double)var2 <= (double)((Integer)var3.l().a()).intValue() + 60.5D;
      }
   }

   private boolean a(int var1, int var2, ava var3) {
      return var1 >= ((Integer)var3.y().a()).intValue() && var1 <= ((Integer)var3.y().a()).intValue() + 200 && var2 >= ((Integer)var3.v().a()).intValue() && var2 <= ((Integer)var3.v().a()).intValue() + 92;
   }

   private boolean b(int var1, int var2, ava var3) {
      return var1 >= ((Integer)var3.b().a()).intValue() && var1 <= ((Integer)var3.b().a()).intValue() + 167 && var2 >= ((Integer)var3.E().a()).intValue() && var2 <= ((Integer)var3.E().a()).intValue() + 73;
   }

   private boolean a(int var1, int var2, arc var3) {
      return var1 >= ((Integer)var3.c().a()).intValue() && var1 <= ((Integer)var3.c().a()).intValue() + ((Integer)var3.a().a()).intValue() && var2 >= ((Integer)var3.d().a()).intValue() && var2 <= ((Integer)var3.d().a()).intValue() + ((Integer)var3.a().a()).intValue();
   }

   private boolean c(int var1, int var2, ava var3) {
      return var1 >= ((Integer)var3.t().a()).intValue() && (double)var1 <= (double)((Integer)var3.t().a()).intValue() + var3.k()[0] && var2 >= ((Integer)var3.m().a()).intValue() - 13 && (double)var2 <= (double)((Integer)var3.m().a()).intValue() + var3.k()[1];
   }

   private boolean a(int var1, int var2, KeyStrokes var3) {
      return var1 >= ((Integer)var3.g().a()).intValue() && var1 <= ((Integer)var3.g().a()).intValue() + 81 && var2 >= ((Integer)var3.c().a()).intValue() && var2 <= ((Integer)var3.c().a()).intValue() + 81;
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      this.F = false;
      this.w = false;
      this.U = false;
      this.M = false;
      this.y = false;
      this.R = false;
      super.mouseReleased(var1, var2, var3);
   }

   public void onAutocompleteResponse(String[] var1) {
      if(this.waitingOnAutocomplete) {
         this.playerNamesFound = false;
         this.foundPlayerNames.clear();

         for(String var5 : var1) {
            if(!var5.isEmpty()) {
               this.foundPlayerNames.add(var5);
            }
         }

         String var6 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
         String var7 = StringUtils.getCommonPrefix(var1);
         if(!var7.isEmpty() && !var6.equalsIgnoreCase(var7)) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            this.inputField.writeText(var7);
         } else if(!this.foundPlayerNames.isEmpty()) {
            this.playerNamesFound = true;
            this.autocompletePlayerNames();
         }
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private static IOException b(IOException var0) {
      return var0;
   }
}
