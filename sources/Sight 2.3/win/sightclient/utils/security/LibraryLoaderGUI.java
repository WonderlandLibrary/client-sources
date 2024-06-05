package win.sightclient.utils.security;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Window.Type;
import javax.swing.JSlider;
import javax.swing.JProgressBar;
import java.awt.Color;

public class LibraryLoaderGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	private float correctValue;
	private float lastValue = 0;
	
	public void setStatus(float percentage) {
		correctValue = (int)((float)this.progressBar.getMaximum() * percentage);
	}
	
	private void progressAnimation() {
		JFrame frame = this;
		Thread anim = new Thread(new Runnable() {

			@Override
			public void run() {
				while (frame.isVisible()) {
					
					lastValue += (correctValue - lastValue) / 128;
					progressBar.setValue((int) lastValue);
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		anim.start();
	}
	
	JProgressBar progressBar;
	public LibraryLoaderGUI() {
		setAlwaysOnTop(true);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Loading Sight (This may take a minute)");
		setResizable(false);
		setBounds(100, 100, 357, 70);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.ORANGE);
		progressBar.setMaximum(1000);
		contentPane.add(progressBar, BorderLayout.CENTER);
		
		this.setVisible(true);
		progressAnimation();
	}

}
