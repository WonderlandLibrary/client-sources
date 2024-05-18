// 
// Decompiled by Procyon v0.5.30
// 

package jaco.mp3.player;

import javax.swing.LayoutStyle;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

final class MP3PlayerThemeDefault implements MP3PlayerTheme
{
    @Override
    public void apply(final MP3Player player) {
        final JButton playButton = new JButton();
        playButton.setText(">");
        playButton.setToolTipText("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.play();
            }
        });
        final JButton pauseButton = new JButton();
        pauseButton.setText("||");
        pauseButton.setToolTipText("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.pause();
            }
        });
        final JButton stopButton = new JButton();
        stopButton.setText("#");
        stopButton.setToolTipText("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.stop();
            }
        });
        final JButton skipBackwardButton = new JButton();
        skipBackwardButton.setText("|<");
        skipBackwardButton.setToolTipText("Skip Backward");
        skipBackwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.skipBackward();
            }
        });
        final JButton skipForwardButton = new JButton();
        skipForwardButton.setText(">|");
        skipForwardButton.setToolTipText("Skip Forward");
        skipForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.skipForward();
            }
        });
        final JSlider volumeSlider = new JSlider();
        volumeSlider.setToolTipText("Volume");
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                player.setVolume(volumeSlider.getValue());
            }
        });
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(100);
        volumeSlider.setMajorTickSpacing(50);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintTrack(true);
        final JCheckBox repeatCheckBox = new JCheckBox();
        repeatCheckBox.setText("Repeat");
        repeatCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.setRepeat(repeatCheckBox.isSelected());
            }
        });
        final JCheckBox shuffleCheckBox = new JCheckBox();
        shuffleCheckBox.setText("Shuffle");
        shuffleCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                player.setShuffle(shuffleCheckBox.isSelected());
            }
        });
        final GroupLayout layout = new GroupLayout(player);
        player.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(playButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(pauseButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(stopButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(skipBackwardButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(skipForwardButton)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(shuffleCheckBox).addComponent(repeatCheckBox)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(volumeSlider, 0, 0, 32767))).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(playButton, GroupLayout.Alignment.LEADING, -1, -1, 32767).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(pauseButton).addComponent(stopButton).addComponent(skipBackwardButton).addComponent(skipForwardButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(shuffleCheckBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(repeatCheckBox)).addComponent(volumeSlider, -2, 42, -2)))).addContainerGap(-1, 32767)));
    }
}
