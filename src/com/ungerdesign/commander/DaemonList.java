package com.ungerdesign.commander;

import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DaemonList extends JPanel {
	private static final long serialVersionUID = 1L;
	private ArrayList<DaemonApp> daemonApps = new ArrayList<DaemonApp>();
	private ArrayList<DaemonAppView> daemonAppViews = new ArrayList<DaemonAppView>();
	
	public DaemonList() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(0,5,0,5));
	}
	
	public void addApp(String name, String pid, String start, String stop) {
		DaemonApp app = new DaemonApp(name, pid, start, stop);
		DaemonAppView appView = new DaemonAppView(app);
		
		daemonApps.add(app);
		daemonAppViews.add(appView);
		
		this.add(appView);
		this.revalidate();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DaemonList list = new DaemonList();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document configDoc = builder.parse(new File("/Users/aunger/.commander.xml"));
			
			NodeList appList = configDoc.getElementsByTagName("application");
			for (int i = 0; i < appList.getLength(); i++) {
				Node app = appList.item(i);
				NamedNodeMap attrs = app.getAttributes();
				list.addApp(attrs.getNamedItem("name").getNodeValue(), attrs.getNamedItem("pidFile").getNodeValue(), attrs.getNamedItem("startCmnd").getNodeValue(), attrs.getNamedItem("stopCmnd").getNodeValue());
			}
			
//			list.addApp("MySQL", "/opt/local/var/db/mysql5/dhcp124.concord.org.pid", "sudo launchctl load /Library/LaunchDaemons/org.macports.mysql5.plist", "sudo launchctl unload /Library/LaunchDaemons/org.macports.mysql5.plist");
//			list.addApp("Jetty", "/tmp/jetty.pid", "/var/jetty/bin/jetty.sh start", "/var/jetty/bin/jetty.sh stop");
//			list.addApp("Nexus", "/tmp/jetty.pid", "/var/jetty/bin/jetty.sh start", "/var/jetty/bin/jetty.sh stop");
			
			JFrame frame = new JFrame();
			frame.getContentPane().add(list);
			// frame.setPreferredSize(new Dimension(300,100));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
