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
    	//  String str="����, ��, ��, ����, ��, ��, �Լ�, ��, ͬ, ���, ��, ��, ����, ���, ��, ����, ��,����, Ȼ��, ��, ����, ����, ���, ��, ����, �Ӷ�, һ��";
	       
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
			System.out.println("��ȡ����");
			e.printStackTrace();
		} 
		return outList; 
	 }
	/**
	 * ��ȡ��Ԥ��������
	 * @param fileIn
	 * @param fileOut
	 */
	
    public static void main(String[] args) {
    	//String in="D:\\Documents\\��ʷ���\\paper\\�����\\VANET-cellular�����°�ȫ��Ϣ�㲥�м�ѡ�񷽷��о�.pdf";
    	String out="D:\\Users\\dell\\workspace\\authoranalyze\\file\\ZhuGeJianWei\\train\\trainData.txt";
		String path="D:\\Documents\\��ʷ���\\paper\\���ΰ\\";
    	File flist[] = new File(path).listFiles();
    	 
    	  for (File f : flist) {
    	     
//    	          System.out.println( f.getAbsolutePath());
    	          String in=f.getAbsolutePath();
    	          new PreRead().trans(in,out);
    	      }
    	  
	}
}
