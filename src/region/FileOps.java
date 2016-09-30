package region;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileOps{	
	public static String[] readFile(String path){ //This is still in it's primitive form, and ReadFile should be used instead
		String fLine = "";
		List<String> fDataRaw = new ArrayList<String>(); //All Data in a file
		
		if (new File(path).exists()){
			Scanner scan = new Scanner(path);
			while (scan.hasNext()) {
				fLine = scan.nextLine(); // This records every line
				fDataRaw.add(fLine); // this adds the string into the entire database
				fLine = "";//resets the line variable so the string doesn't keep concatenating the lines before it.
			}
			scan.close();
		}
		else{
			System.out.println("ERROR: FileOps can't find the file: " + path + "!");
		}
		
		String[] fData = fDataRaw.toArray(new String[]{});
		return fData;
		
	}
	
	public static void writeFile(String path, String[] data){ //Allows the program to write to a file
		try{
			PrintWriter writer = new PrintWriter(new File(path), "UTF-8");
			
			for(int i = 1; i<data.length; i++) {
				System.out.println(data[i]);
				writer.println(data[i]);
			}
		
			writer.close();
		}
		
		catch(FileNotFoundException ingore){}
		catch(UnsupportedEncodingException ignore){}
	}
	
	public static void appendFile(String path, String[] data){ // Append (Add to end) information to the end of a file!
		ArrayList<String> fileDatRaw = new ArrayList<>(Arrays.asList(readFile(path)));
		
		for(int i = 0; i<data.length; i++){
			fileDatRaw.add(data[i]);
			System.out.println(data[i]);
		}
		
		String[] write = fileDatRaw.toArray(new String[]{});
		for(int i = 1; i<write.length;i++){
			System.out.println(write[i]);
		}
		writeFile(path,write);
	}
}
