package edu.nuist.author.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nuist.author.util.DecimalCalculate;


public class Bayes {
	public String predictClass(ArrayList<ArrayList<String >> trainList,ArrayList<String> testList){
		Map<String, ArrayList<ArrayList<String>>> resultMap = dataSet(trainList);
		double mMax = 0.0;
		//将训练数据按yes,no分为两类
		 String finalResult  = "";
		for(int i = 0;i < resultMap.size();i++){
			double mCurrent = 0.0;
			String key = "";
			if(i == 0){
				key = "yes";
			}else{
				key = "no";
			}
			ArrayList<ArrayList<String>> temp = resultMap.get(key);
			mCurrent = culCofV(temp.size(),trainList.size());//yes or no 占整个训练数据的概率
			for(int j = 0;j < testList.size();j++){
				 
				double pv = culPofV(temp,testList.get(j),j);//计算每个特征在yes或no中的概率
				mCurrent = DecimalCalculate.mul(mCurrent, pv); 
			}
			if(mMax <= mCurrent){
				if(i == 0){
					finalResult = "yes";
				}else{
					finalResult = "no";
				}
				mMax = mCurrent;
			}
		}
		
		return finalResult;
	}
	/**
	 * 计算总概率P(y)
	 * @param ySize
	 * @param nSize
	 * @return
	 */
	public double culCofV(int ySize,int nSize){
		return DecimalCalculate.div(ySize, nSize);
	}
	/**
	 * 分类
	 * @param list
	 * @return
	 */
	public Map<String, ArrayList<ArrayList<String>>> dataSet(List<ArrayList<String>> list){
		Map<String, ArrayList<ArrayList<String>>> culMap = new HashMap<String, ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> mIsList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> mNoList = new ArrayList<ArrayList<String>>();
		for(int i = 0;i < list.size();i++){
			ArrayList<String> temp = new ArrayList<String>();
			temp = list.get(i);
		String mResult = temp.get(temp.size()-1);//获取最后一项
		if(mResult.equals("yes")){
			mIsList.add(temp);
		}else{
			mNoList.add(temp);
		}
		}
		culMap.put("yes", mIsList);
		culMap.put("no", mNoList);
		return culMap;
	}
	/**
	 * 计算总概率
	 */
	
	
	  
    
	public double culPofV(ArrayList<ArrayList<String>> mList,String mStr,int index){
		int count = 1;////拉普拉斯平滑，避免特征属性频率为0，造成属于某个类别的概率为0
		for(int i = 0;i < mList.size();i++){
			if(mStr.equals(mList.get(i).get(index))){
				count++;
			}
		}
		return DecimalCalculate.div(count, mList.size(), 3);
	}
}
