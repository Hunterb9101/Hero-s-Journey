package region;

import java.io.File;
import java.util.ArrayList;

public class Utils {
	public static void generateMap(String mapName, int width, int height){
		boolean isMaxWidth = false;
		ArrayList<String> data = new ArrayList<String>();
		String row = width + "x";
		for(int i = 0; i<3; i++){ // For loop for adding Map, Height Map, and Overlay Map //
			for(int i1 = 0; i1<height; i1++){
				
				if(i1 != 0 || i!=0){
					row = "";
				}
				
				for(int i2 = 0; i2<(width+((isMaxWidth) ? 1:0)); i2++){
					row+="0";
				}
				
				data.add(row);
				isMaxWidth = !isMaxWidth;
			}
			if(i != 2){
				data.add("#");
			}
		}
		
		System.out.println("-- Beginning Utils --");
		for(int i = 0; i<data.size(); i++){
			System.out.println(data.get(i));
		}
		System.out.println("-- End Utils --");
		
		FileOps.appendFile("C:/Users/Hunter/Desktop/Programs/Java Workspace/Hero-s-Journey/res/maps/" + mapName,data.toArray(new String[]{}));
	}
}
