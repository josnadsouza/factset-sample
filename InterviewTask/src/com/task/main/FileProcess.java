package com.task.main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jdsouza
 *
 */
public class FileProcess {

	/**
	 * 
	 * This method returns the list of index of the result directly before a line and matching the specified given filter
	 * @param listOfLastNLines
	 * @param filterCondition
	 * @param searchString
	 * @return 
	 * @throws Exception
	 */
	public static List<Integer> getResultIdxList(String[] listOfLastNLines,String filterCondition,String searchString) throws Exception
	{

		List<Integer> matchedLineIdxList=new ArrayList<Integer>();

		int idx=0, prevMatchIdx = -1;

		// Here we check the index of the elements matching the filter with previous matched index and then add the index directly
		//before a matched line
		while(idx<listOfLastNLines.length)
		{
			//We have written a generic method to check the filter and do the implementation
			if(isFilterMatched(filterCondition,idx,listOfLastNLines,searchString))
			{
				if(idx != 0 && (prevMatchIdx != idx -1)){
					matchedLineIdxList.add(idx -1);
				}
				matchedLineIdxList.add(idx);
				prevMatchIdx = idx;
			}
			idx++;
		}
		return matchedLineIdxList;
	}
	
	/**
	 * 
	 * 
	 * @param thirdParameter
	 * @param idx
	 * @param lastNLines
	 * @param fourthParametr
	 * @return
	 * @throws Exception
	 */
	private static boolean isFilterMatched(String thirdParameter, int idx,
			String[] lastNLines, String fourthParametr) throws Exception {
		if(thirdParameter.equals("-startsWith"))
			return lastNLines[idx].startsWith(fourthParametr);
		else if(thirdParameter.equals("-endsWith"))
			return lastNLines[idx].endsWith(fourthParametr);
		else if(thirdParameter.equals("-contains"))
			return lastNLines[idx].contains(fourthParametr);
		else
			throw new Exception("Unsupported operation. Supported operations are: -startsWith, -endsWith, -contains");
	}
}
