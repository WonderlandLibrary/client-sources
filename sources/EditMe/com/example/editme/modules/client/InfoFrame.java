package com.example.editme.modules.client;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.InfoUtil;
import com.example.editme.util.module.ModuleManager;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@Module.Info(
   name = "InfoFrame",
   description = "Shows external info frame",
   category = Module.Category.CLIENT
)
public class InfoFrame extends Module {
   private ArrayList labelList = new ArrayList();
   private Setting height = this.register(SettingsManager.i("Height", 300));
   JPanel panel;
   private boolean initialized;
   private JFrame frame;
   private Setting width = this.register(SettingsManager.i("Width", 300));

   public void onUpdate() {
      try {
         int var1 = 0;

         Iterator var2;
         for(var2 = InfoUtil.infoFrameContents().iterator(); var2.hasNext(); ++var1) {
            String var3 = (String)var2.next();
            if (var1 < this.labelList.size()) {
               ((JLabel)this.labelList.get(var1)).setText(var3);
            } else {
               JLabel var4 = new JLabel(var3);
               var4.setAlignmentX(0.5F);
               var4.setForeground(Color.WHITE);
               this.labelList.add(var4);
               this.panel.add(var4);
            }
         }

         if (var1 < this.labelList.size()) {
            ((JLabel)this.labelList.get(var1)).setText("-");
         } else {
            this.labelList.add(new JLabel("-"));
         }

         ++var1;
         var2 = ModuleManager.getModules().iterator();

         while(true) {
            Module var8;
            do {
               if (!var2.hasNext()) {
                  while(var1 < this.labelList.size()) {
                     ((JLabel)this.labelList.get(var1)).setText("");
                     ++var1;
                  }

                  var2 = this.labelList.iterator();

                  while(var2.hasNext()) {
                     JLabel var9 = (JLabel)var2.next();
                     var9.setAlignmentX(0.5F);
                     var9.setForeground(Color.WHITE);
                  }

                  this.width.setValue(this.frame.getSize().width);
                  this.height.setValue(this.frame.getSize().height);
                  return;
               }

               var8 = (Module)var2.next();
            } while(!var8.isEnabled());

            Iterator var10 = var8.settingList.iterator();

            while(var10.hasNext()) {
               Setting var5 = (Setting)var10.next();
               if (var5.getName().equals("Visible") && (Boolean)var5.getValue()) {
                  if (var1 < this.labelList.size()) {
                     ((JLabel)this.labelList.get(var1)).setText(var8.getName());
                  } else {
                     JLabel var6 = new JLabel(var8.getName());
                     var6.setAlignmentX(0.5F);
                     var6.setForeground(Color.WHITE);
                     this.labelList.add(var6);
                     this.panel.add(var6);
                  }

                  ++var1;
               }
            }
         }
      } catch (Exception var7) {
      }
   }

   public void onEnable() {
      if (!this.initialized) {
         try {
            this.frame = new JFrame("Editme Info");
            this.panel = new JPanel();
            this.panel.setLayout(new BoxLayout(this.panel, 1));
            this.panel.setBackground(Color.BLACK);
            this.labelList.add(new JLabel(" - "));
            Iterator var1 = InfoUtil.infoFrameContents().iterator();

            while(var1.hasNext()) {
               String var2 = (String)var1.next();
               this.labelList.add(new JLabel(var2));
            }

            this.labelList.add(new JLabel(" - "));
            var1 = ModuleManager.getModules().iterator();

            while(true) {
               Module var6;
               do {
                  if (!var1.hasNext()) {
                     var1 = this.labelList.iterator();

                     while(var1.hasNext()) {
                        JLabel var7 = (JLabel)var1.next();
                        var7.setAlignmentX(0.5F);
                        var7.setForeground(Color.WHITE);
                        this.panel.add(var7);
                     }

                     this.panel.setForeground(Color.WHITE);
                     this.frame.add(this.panel);
                     this.frame.setSize((Integer)this.width.getValue(), (Integer)this.height.getValue());
                     this.frame.setLocationRelativeTo((Component)null);
                     this.frame.setDefaultCloseOperation(3);
                     this.frame.setVisible(true);
                     this.initialized = true;
                     return;
                  }

                  var6 = (Module)var1.next();
               } while(!var6.isEnabled());

               Iterator var3 = var6.settingList.iterator();

               while(var3.hasNext()) {
                  Setting var4 = (Setting)var3.next();
                  if (var4.getName() == "Visible" && (Boolean)var4.getValue()) {
                     this.labelList.add(new JLabel(var6.getName()));
                  }
               }
            }
         } catch (Exception var5) {
         }
      }

   }

   public void onDisable() {
      this.frame.setVisible(false);
   }
}
