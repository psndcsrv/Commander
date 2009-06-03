package com.ungerdesign.commander;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DaemonAppView extends JPanel {
	private static final long serialVersionUID = 1L;
	private DaemonApp app;
	
	public DaemonAppView(DaemonApp application) {
		super();
		
		this.app = application;
		
		this.setLayout(new GridLayout(1,0));
		
		this.add(new JLabel(app.getAppName()));
		
		// radio button group for start/stop
		JRadioButton startButton = new JRadioButton("Start");
		JRadioButton stopButton = new JRadioButton("Stop");
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(startButton);
		bg.add(stopButton);
		
		if (app.isRunning()) {
			startButton.setSelected(true);
		} else {
			stopButton.setSelected(true);
		}
		
		this.add(startButton);
		this.add(stopButton);
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.start();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				app.stop();
			}
		});
		
		this.revalidate();
	}

}
