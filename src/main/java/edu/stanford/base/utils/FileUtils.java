package edu.stanford.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;



public class FileUtils {
	public static void copyFile(File in, File out) throws Exception {
	    FileInputStream fis  = new FileInputStream(in);
	    FileOutputStream fos = new FileOutputStream(out);
	    try {
	        byte[] buf = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buf)) != -1) {
	            fos.write(buf, 0, i);
	        }
	    } 
	    catch (Exception e) {
	        throw e;
	    }
	    finally {
	        if (fis != null) fis.close();
	        if (fos != null) fos.close();
	    }
	}
	
	public static String formatValidDirectory(String dir){
		if(!dir.endsWith("/")){
			return dir + "/";
		}
		
		return dir;
	}
	

	public static boolean ensuresDirExists(String filePath){
		File folder = new File(filePath);
		boolean exists = folder.exists();
		if (exists) {
		    return true;
		} else {
		    return folder.mkdir();
		}
	}
	

	
}
