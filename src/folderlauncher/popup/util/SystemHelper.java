package folderlauncher.popup.util;

import org.eclipse.jface.preference.IPreferenceStore;

import folderlauncher.Activator;


public class SystemHelper {
	public static final String WINDOWS = "win32";
	public static final String LINUX = "linux";
	public static final String MACOSX = "macosx";
	
	private String osName;
	public static SystemHelper sInstance = null;
	public SystemHelper() {
		osName = System.getProperty("osgi.os");
	}
	
	public static SystemHelper getInstance() {
		if(sInstance == null) {
			sInstance = new SystemHelper();
		}
		return sInstance;
	}
	
	public String getBrowser() {
		String browser = null;
		
		if(isWindows()) {
			browser = "explorer";
		} else if(isLinux()) {
			IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			browser = store.getString("linuxFileManager");
			if(browser.equals("other")) {
				browser = store.getString("linuxFileManagerPath");
			}
		} else if(isMacOSX()) {
			browser = "open";
		}
		
		return browser;
	}

	public boolean isMacOSX() {
		return osName.equalsIgnoreCase(MACOSX);
	}

	public boolean isLinux() {
		return osName.equalsIgnoreCase(LINUX);
	}

	public boolean isWindows() {
		return osName.equalsIgnoreCase(WINDOWS);
	}
}
