package com.task.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class FileReaderTask {
	
	private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_WINDOWS_OS = (OS.indexOf("win") >= 0);
    private static final boolean IS_MAC_OS = (OS.indexOf("mac") >= 0);
    private static final boolean IS_UNIX_OS = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	    
	/**
	 * This method returns the last N lines within the given filename
	 * 
	 * @param fileName
	 * @param N
	 * @return the last N lines
	 * @throws IOException 
	 * @throws Exception
	 */
	public static List<String> getLastNLines(String fileName,int noOfLines) throws IOException
	{
		RandomAccessFile fileHandler =null;
		File file = null;
		try{
			file = new File(fileName);
			LinkedList<String> lastNLines = new LinkedList<String>();
			fileHandler=new RandomAccessFile(file, "r");
			long fileLength=fileHandler.length()-1;
			StringBuilder builder=new StringBuilder();
			int line=0;
			// This flag determines whether the character sequence for new line in Windows(\r\n) is begun
			boolean isWindowsLineBreakStart = false;
			for(long filePointer=fileLength;filePointer!=1;filePointer--)
			{
				fileHandler.seek(filePointer);
				int readByte = fileHandler.readByte();
				// Unix treats \n as newline, Mac treats \r, Windows treats \r\n
				if(readByte == 0xA)
				{
					if(filePointer < fileLength){
						if(IS_UNIX_OS){
							line++;
							addLineAndCleanup(lastNLines, builder);
						}
						else{
							if(IS_WINDOWS_OS){
								isWindowsLineBreakStart = true;
							}
						}
					}
					continue;
				}else if(readByte == 0xD)
				{
					if(filePointer < fileLength-1){
						if(IS_MAC_OS){
							line++;
							addLineAndCleanup(lastNLines, builder);
						}
						else if(IS_WINDOWS_OS && isWindowsLineBreakStart){
							line++;
							addLineAndCleanup(lastNLines, builder);
							isWindowsLineBreakStart = false;
						}
					}
					continue;
				}
				if(line >= noOfLines)
				{
					break;
				}
				
				builder.append( ( char ) readByte );
			}
			return lastNLines;
		}catch(java.io.FileNotFoundException e){
			e.printStackTrace();
			throw e;
		}catch(java.io.IOException e){
			e.printStackTrace();
			throw e;
		}finally
		{
			if(fileHandler != null){
				try{
					fileHandler.close();
				}catch (Exception e)
				{
					throw e;
				}
			}
		}
	}
	
	/**
	 * Add line to list and cleanup the builder object (instead of creating new object)
	 * 
	 * @param lastNLines
	 * @param builder
	 */
	private static void addLineAndCleanup(LinkedList<String> lastNLines, StringBuilder builder){
		lastNLines.addFirst(builder.reverse().toString());
		builder.setLength(0);
	}	
	
}
