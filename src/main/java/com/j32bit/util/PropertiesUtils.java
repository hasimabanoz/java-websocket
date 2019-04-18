package com.j32bit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

	private final static String PROPERTIES_FILE_NAME_KEY = "@@PROPERTIES_FILE_NAME@@";

	/**
	 * fileName ismi ile verilen properties dosyasini props nesnesine yukler
	 */
	public static boolean loadProperties(String fileName, Properties props) {
		File f = new File(fileName);
		System.out.println("Loading setings Filename: " + f.getAbsolutePath());
		if (!f.exists()) {
			f = new File(System.getProperty("user.dir") + File.separator + fileName);
		}

		if (f.exists() && !f.isDirectory()) {
			try {
				FileInputStream fis = new FileInputStream(f);
				props.load(fis);

				props.put(PROPERTIES_FILE_NAME_KEY, fileName);
				fis.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return false;
			}
		} else {
			System.out.println(fileName + " : File not exist");
			return false;
		}
		return true;
	}

}