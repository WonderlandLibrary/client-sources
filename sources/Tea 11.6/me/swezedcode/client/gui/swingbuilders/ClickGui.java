package me.swezedcode.client.gui.swingbuilders;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;

import me.swezedcode.client.module.modules.Fight.AimAssist;
import me.swezedcode.client.utils.ModuleUtils;

import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickGui extends JFrame {

	private JPanel contentPane;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	public static JSlider slider = new JSlider();
	public static JSlider slider_1 = new JSlider();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClickGui frame = new ClickGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClickGui() {
		setTitle("Tea");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 521, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane.setBounds(0, 0, 505, 317);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Aim Assist", null, panel, null);
		panel.setLayout(null);
		
		JCheckBox chckbxEnabled = new JCheckBox("Enabled");
		chckbxEnabled.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModuleUtils.getMod(AimAssist.class).setToggled(true);
			}
		});
		chckbxEnabled.setBounds(6, 7, 97, 23);
		panel.add(chckbxEnabled);
		
		slider.setValue(30);
		slider.setBounds(6, 54, 200, 12);
		panel.add(slider);
		
		slider_1.setValue(4);
		slider_1.setMaximum(10);
		slider_1.setBounds(6, 95, 200, 12);
		panel.add(slider_1);
		
		JLabel lblMinCps = new JLabel("Speed");
		lblMinCps.setBounds(10, 37, 46, 14);
		panel.add(lblMinCps);
		
		JLabel lblMaxCps = new JLabel("Range");
		lblMaxCps.setBounds(10, 77, 46, 14);
		panel.add(lblMaxCps);
	}
}
