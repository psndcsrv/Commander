package com.ungerdesign.commander;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class DaemonApp {
	private static final Logger logger = Logger.getLogger(DaemonApp.class.getCanonicalName());
	private String startCmd;
	private String stopCmd;
	private String appName;
	private String pidFile;
	private int pid = -1;
	
	public DaemonApp(String appName, String pidFile, String startCommand, String stopCommand) {
		this.pidFile = pidFile;
		this.appName = appName;
		this.startCmd = startCommand;
		this.stopCmd = stopCommand;
	}
	
	public void start() {
		logger.info("starting " + appName);
		runCommand(startCmd);
	}
	
	public void stop() {
		// TODO
		logger.info("stopping " + appName);
		runCommand(stopCmd);
	}
	
	private Process runCommand(String command) {
		try {
			return Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isRunning() {
		calcRealPid();
		if (pid > 0) {
			Process p = runCommand("ps -p " + pid);
			if (p != null) {
				try {
					int exitVal = p.waitFor();
					if (exitVal == 0) {
						return true;
					}
				} catch(InterruptedException e) {
					
				}
			}
		}

		return false;
	}
	
	public void calcRealPid() {
		// get the pid from pidFile
		try {
			BufferedReader in = new BufferedReader(new FileReader(pidFile));
			String line = in.readLine();
			pid = Integer.parseInt(line);
		} catch (Exception e) {
			pid = -1;
		}
	}
	
	/**
	 * @param startCmd the startCmd to set
	 */
	public void setStartCmd(String startCmd) {
		this.startCmd = startCmd;
	}
	/**
	 * @return the startCmd
	 */
	public String getStartCmd() {
		return startCmd;
	}
	/**
	 * @param stopCmd the stopCmd to set
	 */
	public void setStopCmd(String stopCmd) {
		this.stopCmd = stopCmd;
	}
	/**
	 * @return the stopCmd
	 */
	public String getStopCmd() {
		return stopCmd;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param pidFile the pidFile to set
	 */
	public void setPidFile(String pidFile) {
		this.pidFile = pidFile;
	}

	/**
	 * @return the pidFile
	 */
	public String getPidFile() {
		return pidFile;
	}
}
