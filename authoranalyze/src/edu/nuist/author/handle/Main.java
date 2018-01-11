package edu.nuist.author.handle;

import java.util.ArrayList;
import java.util.Scanner;

import edu.nuist.author.util.DecimalCalculate;


public class Main {
	public static void main(String[] args){
		ArrayList<ArrayList<String>> trainList ;
		ArrayList<ArrayList<String>> testList ;
		
		
		String processedTrain = "file/ZhuGeJianWei/train/trainData.txt";
		
		String processedTest = "file/ZhuGeJianWei/test/testData";
		
		 String finalStr = "";
		 int wrong_number = 0; //��¼��ȷ������
		 double finalData = 0.0; //������
		 
		// boolean type = false; //�Ƿ������
		 boolean flag = false; //��־���Ե�����
		 
		
		PreRead convert = new PreRead();
		//convert.readFile(originalTrain, processedTrain,type); //ѵ������
		//if(!flag){
			//convert.readFile(originalTest, processedTest,type); //1000��������
			testList = convert.readTest(processedTest);
		//}else{
		//	convert.readFile(originalTest100, processedTest100,type); //100����������
		//	testList = convert.readTest(processedTest100);
		//}
		
		trainList =convert.readTest(processedTrain);	
		Bayes bayes = new Bayes();
		for(int i = 0;i < testList.size();i++){
			ArrayList<String> tmp = new ArrayList<String>();
			tmp = testList.get(i);
			String label = tmp.get(tmp.size()-1);
			tmp.remove(tmp.size() - 1);
			finalStr = bayes.predictClass(trainList, tmp);
			
			if(!label.equals(finalStr)){
				wrong_number ++;
			}
		}
		//finalData = DecimalCalculate.div(count, testList.size(),3); 
		System.out.println("Ԥ�Դ��������"+ wrong_number+", �����ܸ���"+testList.size());
		System.out.println("��ȷ��Ϊ��" + (DecimalCalculate.sub(1.00000000, DecimalCalculate.div(wrong_number, testList.size())))*100 + "%");
	}
}
