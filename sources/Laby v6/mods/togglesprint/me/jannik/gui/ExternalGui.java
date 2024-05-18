package mods.togglesprint.me.jannik.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
//LabyClient src by Exeptiq
public class ExternalGui
  extends JFrame
{
  public JPanel contentPane;
  private JPanel combat;
  private JPanel movement;
  private JPanel render;
  private JPanel player;
  private JPanel bedwars;
  
  public ExternalGui()
  {
    setVisible(false);
    setDefaultCloseOperation(1);
    setBounds(100, 100, 450, 300);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(this.contentPane);
    
    JTabbedPane jtabbedpane = new JTabbedPane(1);
    this.contentPane.add(jtabbedpane);
    
    this.combat = new JPanel();
    jtabbedpane.addTab("Combat", this.combat);
    
    this.movement = new JPanel();
    jtabbedpane.addTab("Movement", this.movement);
    
    this.render = new JPanel();
    jtabbedpane.addTab("Render", this.render);
    
    this.player = new JPanel();
    jtabbedpane.addTab("Player", this.player);
    
    this.bedwars = new JPanel();
    jtabbedpane.addTab("Bedwars", this.bedwars);
    for (final Module m : Jannik.getModuleManager().getModules())
    {
      JRadioButton modules = new JRadioButton(m.getName());
      modules.setSelected(m.isEnabled());
      
      modules.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          m.toggleModule();
        }
      });
      if (m.getCategory() == Category.COMBAT) {
        this.combat.add(modules);
      }
      if (m.getCategory() == Category.MOVEMENT) {
        this.movement.add(modules);
      }
      if (m.getCategory() == Category.RENDER) {
        this.render.add(modules);
      }
      if (m.getCategory() == Category.PLAYER) {
        this.player.add(modules);
      }
      if (m.getCategory() == Category.BEDWARS) {
        this.bedwars.add(modules);
      }
    }
  }
}
