package edu.nuist.author.util;


import java.awt.image.BufferedImage;
import java.io.*;



//import java.net.URLDecoder;
import java.nio.charset.Charset;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;



//import java.text.MessageFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import javax.imageio.ImageIO;

public class FileUtil {
	public static final int FIL = 1;
	public static final int IMG = 2;
	public static final int TXT = 0;
	//private static Log logger = Logger.getLogger();
	
	public static void convertFileToUTF(File src, String outputFile, String encodeing){  
        char[] buf = new char[1024];  
        StringBuilder strb = new StringBuilder();  
          
        InputStreamReader isr = null;  
          
        try {  
            isr = new InputStreamReader(new FileInputStream(src), Charset.forName(encodeing));  
            int readCount = 0;  
            while ( -1 != (readCount =isr.read(buf))) {  
                strb.append(buf, 0, readCount-1);  
            }               
        }catch(FileNotFoundException e){
        	String msg = "convertFileToUTF - the file: "+ src.getName() +" doesn't exist error!";
        	//logger.error(msg);
        }catch(IOException e){
        	String msg = "convertFileToUTF - the file: "+ src.getName() +" open error!";    
        	//logger.error(msg);
        } 
        finally {  
            if (isr!=null) {  
                try {  
                    isr.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(outputFile);  
            fos.write(strb.toString().getBytes("utf-8"));  
            fos.flush();  
        }catch(IOException e){
        	//logger.error("convertFileToUTF - the converted file create error!");          
        }finally {  
            if (fos!=null) {  
                try {  
                    fos.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
    } 
	   
	
	
	//if subDirectory is null and directory isn't exist, just create directory, else create subDirectory under the directory
	 public static void createDirectory(String directory, String subDirectory) throws Exception {
	  String dir[];
	  File fl = new File(directory);
	  
	  if ("".equals(subDirectory) && fl.exists() != true)
 		fl.mkdir();
	  else if ( !"".equals(subDirectory) ) {
		dir = subDirectory.replace('\\', '/').split("/");
		 for (int i = 0; i < dir.length; i++) {
			 File subFile = new File(directory + File.separator + dir[i]);
			 if (subFile.exists() == false)
			   subFile.mkdir();
			   directory += File.separator + dir[i];
			 }
	  }
	  	
	}
	
	

	
	public static void copyFile(String src, String dst){
		
		try { 
			   int byteread = 0; 
	           File oldfile = new File(src); 
	           if (oldfile.exists()) {
	               InputStream inStream = new FileInputStream(src);
	               FileOutputStream fs = new FileOutputStream(dst); 
	               byte[] buffer = new byte[1444]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                  fs.write(buffer, 0, byteread); 
	               } 
	               fs.close();
	               inStream.close(); 
	           } 
	       } 
	       catch (Exception e) {
	    	   //logger.error(" FileCopyException copyFile - the file: " + src + " to dest file: "+dst +" IO Exception!");	          
	       } 		
	}
	//waiting test
	public static boolean deleteFile(String fileName) {
	   File file = new File(fileName);
	   int deleteTryCount = 5;
	   if (file.isFile() && file.exists())  file.getAbsoluteFile().delete();	    
	   while(!file.renameTo(file) && deleteTryCount >= 0)  
	    {
	    	try{
	    		file = null;
	    		System.gc();
	    		file = new File(fileName);
	    		Thread.sleep(1000);	    	
	    	}catch(Exception e){
	    		;
	    	}    		      
	    	deleteTryCount --;
	    } 	   
	    if(deleteTryCount == 0) {
	    	//logger.error(" FileDeleteException deleteFile - the file: "+ fileName + " deleted Exception");
	    	return false;
	    }
	    return  true;	    
	}
	
	public static String getFileNameFromFullPath(String path){
		return path.substring(path.lastIndexOf("/")+1);
	}
	
	public static String getFileStorePath(String fullPath){
		return fullPath.substring(0, fullPath.lastIndexOf("/"));
	}

	
	
	protected static String getExceptionString(StringWriter sw, Exception e){
		e.printStackTrace(new PrintWriter(sw,true));  
    	return sw.toString();   
	}
	
	

    public static boolean existZH(String str) {  
        String regEx = "[\\u4e00-\\u9fa5]";  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(str);  
        while (m.find()) {  
            return true;  
        }  
        return false;  
    }  
    
	/*
	public static JSONObject scanDirectory(String dir){
		
		int fileCount = 0;
		File dirFile = new File(dir);
		try{
			if(!dirFile.isDirectory()){
				fileCount = 1;
				int dataType = FileUtil.judgeFileType(dir);
				String mimeType = FileUtil.detectFileType(dir);
				String jsonTmp = MessageFormat.format(Configuration.DIRECTORY_SCAN_JSON_TEMP, 0, dataType, mimeType, dir, fileCount);
				JSONObject json = new JSONObject("{" + jsonTmp + "}");		
				return json;
			}else{
				String jsonTmp = MessageFormat.format(Configuration.DIRECTORY_SCAN_JSON_TEMP, 1, "fileDir", "fileDir",dir, 0);
				JSONObject json = new JSONObject("{" + jsonTmp + "}");
				for(String f : dirFile.list()){
					f = dir + "/" + f;
					JSONObject jo = scanDirectory(f);
					fileCount += jo.getInt("fileNums");
					json.put("fileNums", fileCount);
					JSONArray dirFiles = (JSONArray)json.get("dirFiles");
					dirFiles.put(jo);
				}
				return json;
			}		
		}catch(JSONException e){
			logger.error(" JSONParseException scanDirectory - the file directory: "+ dir + " scan json parse Exception");	
		}catch(Exception e){
			logger.error(" FileIOException scanDirectory - the file directory: "+ dir + " scan Exception");			
		}
		
	}*/
	
	

	public static void copyDir(String srcDir, String dstDir){
		File dst = new File(dstDir);
		File src = new File(srcDir);
		if(!src.exists()){
			//logger.error(" FileDirCopyException copyDir - the src file directory: "+ srcDir + "  doesn't exist!");
		}
		if(dst.exists()){
			//logger.error(" FileDirCopyException copyDir - the dst file directory: "+ dstDir + "  already exist!");
		}		
		if(src.isDirectory()){
			 String[] subFiles = src.list();
			 dst.mkdirs();
			 for(int i=0; i<subFiles.length; i++){
				 String newFileName = dstDir.endsWith("/") ? dstDir + subFiles[i] : dstDir + "/" +subFiles[i];
				 subFiles[i] = srcDir.endsWith("/") ? srcDir + subFiles[i] : srcDir + "/" +subFiles[i];
				 if(new File(subFiles[i]).isDirectory()){
					 copyDir(subFiles[i], newFileName);
				 }else{
					 try{
						 copyFile(subFiles[i], newFileName);
					 }catch(Exception e){
						 //logger.error(" FileCopyException copyDir:copyFile - the file: " + subFiles[i] + " to dest file: "+ newFileName +" IO Exception!");
					 }					 
				 }
			}
		}else{
			try{
				 copyFile(srcDir, dstDir);
			 }catch(Exception e){
				 //logger.error(" FileCopyException copyDir:copyFile - the file: " + srcDir + " to dest file: "+ dstDir +" IO Exception!");
			 }	
		}
	}
	
	

	public static void writeStringToFile(String str, String fileName){
		File f = new File(fileName);
		try {
		    if(!f.exists()){
		    	f.createNewFile();
		    }
		    FileWriter fw = new FileWriter(f);
		    BufferedWriter out = new BufferedWriter(fw);
		    out.write(str, 0, str.length());
		    out.close();
		}catch (IOException e) {
			//logger.error(" FileIOException writeStringToFile - the file: " + fileName + " IO Exception!");
		}
	}
	
	public static String getFileContent(String targetFile){	
		BufferedReader br = null;
	    try{	    	
		    String data = "";
		    String jsonString = "";
		    InputStreamReader isr = new InputStreamReader(new FileInputStream(targetFile), "UTF-8");
	    	br = new BufferedReader(isr);  
		    data = br.readLine();
		    jsonString += data;
		    while( data != null){ 
		        data = br.readLine();
		        if(data != null) jsonString += data;
		    }  		
		    if("".equals(jsonString))
		    	return null;
		    else
		    	return jsonString.trim();
	   }catch(IOException e){
			//logger.error(" FileIOException getJSONFromFile - get JSON from the file : "+ targetFile + " IOException");
		}finally{
			if(br != null)
				try{
					br.close();
				}catch(IOException e){;}
		}	    
	    return null;
	} 

	public static boolean deleteDirectory(String sPath) {  
	    //濡傛灉sPath涓嶄互鏂囦欢鍒嗛殧绗︾粨灏撅紝鑷姩娣诲姞鏂囦欢鍒嗛殧绗�  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //濡傛灉dir瀵瑰簲鐨勬枃浠朵笉瀛樺湪锛屾垨鑰呬笉鏄竴涓洰褰曪紝鍒欓��鍑�  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //鍒犻櫎鏂囦欢澶逛笅鐨勬墍鏈夋枃浠�(鍖呮嫭瀛愮洰褰�)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //鍒犻櫎瀛愭枃浠�  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //鍒犻櫎瀛愮洰褰�  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //鍒犻櫎褰撳墠鐩綍  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	
	public static void fileAppend(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
            writer = new FileWriter(fileName, true);     
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }     
	
	public static byte[] toByteArray(String filename) throws IOException{  
        
        File f = new File(filename);  
        if(!f.exists()){  
            throw new FileNotFoundException(filename);  
        }  
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());  
        BufferedInputStream in = null;  
        try{  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while(-1 != (len = in.read(buffer,0,buf_size))){  
                bos.write(buffer,0,len);  
            }  
            return bos.toByteArray();  
        }catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        }finally{  
            try{  
                in.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            bos.close();  
        }  
    }  
	
	public static void byteArraytoFile(String filename, byte[] content) throws IOException{  
		FileOutputStream fos = new FileOutputStream(filename);  
        fos.write(content);  
        fos.close();
    }  
	
	public static long getFileSize(String fileName){
		File f= new File( fileName );
		 if (f.exists() && f.isFile()){
			 return f.length();
		 }else{
			 return -1;
		 }
	}
	
	public static void renameFile(String oldFileName, String newFileName){
		File f = new File( oldFileName );
        File f1 = new File( newFileName );
        
        f.renameTo(f1);
	}
	public static void main(String[] args){
		renameFile(
			"E:/opensource_data/tmp/philstar.business.column2016.06.27[12.00].446",
			"E:/opensource_data/tmp/philstar.business.column2016.06.27[12.00].446-uploaded"
		);
	}
}
