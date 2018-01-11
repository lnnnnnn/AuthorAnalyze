package edu.nuist.author.util;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.*;
public class AnsjUtil {
	 public  String seg(String content) {
	      
	        //副词(d)、介词(p)、连词(c)、助词(u)
	        Set<String> funcNature = new HashSet<String>() {{
	            add("p");add("c");add("u");add("d");
	        }};
	        
	       // String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!" ;
	       // String str="进而, 但, 且, 而且, 按, 及, 以及, 和, 同, 因此, 于, 与, 假如, 如果, 当, 并且, 或,根据, 然而, 跟, 但是, 或者, 因而, 在, 按照, 从而, 一样";
	        
	        Result result = ToAnalysis.parse(content); //分词结果的一个封装，主要是一个List<Term>的terms
	      //  System.out.println(result.getTerms());

	        //删除了“在”
	       String funcWords[]={"进而", "但", "且", "而且", "按", "及", "以及", "和", "同", "因此", "于", "与", "假如", "如果", "当", "并且", "或","根据", "然而", "跟", "但是", "或者", "因而",  "按照", "从而", "一样"};
	       int resNum[]=new int[26]; 
	       
	       
	     
	       int tot=0;
	       String resStr="";
	        List<Term> terms = result.getTerms(); //拿到terms
	       // System.out.println(terms.size());
	        for(int i=0; i<terms.size(); i++) {
	            String word = terms.get(i).getName(); //拿到词
	            String natureStr = terms.get(i).getNatureStr(); //拿到词性
	            if(funcNature.contains(natureStr)) {
                       tot++;
	            }
	            for(int j=0;j<26;j++){
	            	if(word.equals(funcWords[j])){
	            		resNum[j]++;
	            	}
	            }
	          
	        }
	        
	        
	        for(int j=0;j<26;j++){
	        	//System.out.println((double)resNum[j]/tot);
            	    int interval=dataConvert((double)resNum[j]/tot);
            		resStr+=(interval+",");;
            }
	        resStr+=dataConvert((double)resNum[25]/tot);
	        resStr+=",yes\n";
	        return resStr;
	       // System.out.println(tot);
	       // System.out.println(resStr);
	    }

	/* 0	0
 	1	(0,0.00025]
 	2	(0.00025,0.0005]
 	3	(0.0005,0.00075]
 	4	(0.00075,0.001]
 	5	(0.001,0.0025]
 	6	(0.0025,0.005]
 	7	(0.005,0.0075]
 	8	(0.0075, 0.01]
 	9	>0.01*/
	 
	 
	 //将词频转为区间标号
	    public int dataConvert(double p){
	    		if(p==0) return 0;
	    		if(p>0&&p<=0.00025) return 1;
	    		if(p>0.00025&&p<=0.0005) return 2;
	    		if(p>0.0005&&p<=0.00075) return 3;
	    		if(p>0.00075&&p<=0.001) return 4;
	    		if(p>0.001&&p<=0.0025) return 5;
	    		if(p>0.0025&&p<=0.005) return 6;
	    		if(p>0.005&&p<=0.0075) return 7;
	    		if(p>0.0075&&p<=0.01) return 8;
	    		if(p>0.01) return 9;
	    		
	    		return -1;
	    }
	 
	 //double可直接比较
	 /*public static void doubleTest(){
		 double a=0,b=0.00025;
		 if(b==0.00025) System.out.println(true);
		 if(0.00026>b) System.out.println(true);
	 }*/
	 
	    public static void main(String[] args) {
	    	  String str="进而, 但, 且, 而且, 按, 及, 以及, 和, 同, 因此, 于, 与, 假如, 如果, 当, 并且, 或,根据, 然而, 跟, 但是, 或者, 因而, 在, 按照, 从而, 一样";
		       
	       new AnsjUtil(). seg(str);
	        //doubleTest();
	    }
}
