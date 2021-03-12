package com.task.main;

import java.util.List;

/**
 * This program will reads a file and prints out the last N lines of the file, supporting the filter criteria 
 * provided by the user.
 * 
 * 
 * SYSTEM REQUIREMENTS =>
 * Operating System: Windows,Unix,MAC
 * JDK:1.8 and above
 * RAM:512MB and above
 * 
 * @author jdsouza
 *
 */
public class FactsetProblem {
	public static void main(String[] args) throws Exception {
		
		validateArguments(args);
		
		String filename=args[0];
		int N = Integer.parseInt(args[1]);;
		String filterCondition=args[2];
		String searchString=args[3];

		String[] lastNLines=FileReaderTask.getLastNLines(filename, N).toArray(new String[0]);
		List<Integer> resultIdxList=FileProcess.getResultIdxList(lastNLines, filterCondition, searchString);

		//Print the result
		resultIdxList.stream().forEach(idx->System.out.println(lastNLines[idx]));
	}

	private static void validateArguments(String[] args) throws Exception {
		if(args.length != 4){
			throw new Exception("Please enter required values: <filename> <N> <filter> <searchString>");
		}
		
		int N = 0;
		try{
			N=Integer.parseInt(args[1]);
		}catch(NumberFormatException ex){
			throw new Exception("Please enter a valid digit for last N lines");
		}
		
		if(N<0)
			throw new Exception("Negative values are not supported");
		else if(N==0)
			return;	

		String filterCondition=args[2];
		if(!filterCondition.equals("-startsWith") && !filterCondition.equals("-endsWith") && !filterCondition.equals("-contains"))
			throw new Exception("Unsupported operation. Supported operations are: -startsWith, -endsWith, -contains");		
	}
}
