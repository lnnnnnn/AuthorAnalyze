package edu.nuist.author.handle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.nuist.author.util.AnsjUtil;
import edu.nuist.author.util.AnsjUtil;
import edu.nuist.author.util.FileUtil;
import edu.nuist.author.util.TiKaUtil;
public class PreRead {
    public void trans(String fileIn,String fileOut){
    	
    	String str=new TiKaUtil().parsePDF(fileIn);
        
    	//System.out.println(str);
    	//  String str="进而, 但, 且, 而且, 按, 及, 以及, 和, 同, 因此, 于, 与, 假如, 如果, 当, 并且, 或,根据, 然而, 跟, 但是, 或者, 因而, 在, 按照, 从而, 一样";
	       
	      String s= new AnsjUtil().seg(str);
	      System.out.println(s);
	       FileUtil.fileAppend(fileOut,s);
    }
    
    public ArrayList<ArrayList<String>> readTest(String fileIn){
		 ArrayList<ArrayList<String>> outList = new ArrayList<ArrayList<String>>();
		 try {
			File file = new File(fileIn);
			 FileReader reader = new FileReader(file);
			 BufferedReader in = new BufferedReader(reader);
			 String line = null;
			 while((line = in.readLine()) != null){
				 ArrayList<String> list = new ArrayList<String> ();
				 String[] mArray = line.split(",");
				 for(int i = 0;i < mArray.length;i++){
					 list.add(mArray[i]);
				 }
				 outList.add(list);
			 }
			 in.close();
			 reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("读取出错");
			e.printStackTrace();
		} 
		return outList; 
	 }
	/**
	 * 读取并预处理数据
	 * @param fileIn
	 * @param fileOut
	 */
	
    public static void main(String[] args) {
    	//String in="D:\\Documents\\虚词分析\\paper\\吴黎兵\\VANET-cellular环境下安全消息广播中继选择方法研究.pdf";
    	String out="D:\\Users\\dell\\workspace\\authoranalyze\\file\\ZhuGeJianWei\\train\\trainData.txt";
		String path="D:\\Documents\\虚词分析\\paper\\诸葛建伟\\";
    	File flist[] = new File(path).listFiles();
    	 
    	  for (File f : flist) {
    	     
//    	          System.out.println( f.getAbsolutePath());
    	          String in=f.getAbsolutePath();
    	          new PreRead().trans(in,out);
    	      }
    	  
	}
}
