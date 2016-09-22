package main;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import region.Overlay;
import region.RegionLoader;
import region.Tile;

public class Registry {
	public static final Color brown = new Color(96,54,0);
	public static final Color darkGreen = new Color(0,70,0);
	public static final Color medGreen = new Color(0,128,0);
	
	public static String regionGraphicsPath = "regionResources/";
	
	static String imgLoadError = "Error loading image: ";
	
	public static List<String> ReadFile(File filePath){ //This is still in it's primitive form, and ReadFile should be used instead
		String fLine = "";
		List<String> fDataRaw = new ArrayList<String>(); //All Data in a file
		
		if (filePath.exists()){
			try {
				Scanner scan = new Scanner(filePath);
				while (scan.hasNext()) {
					fLine = scan.nextLine(); // This records every line
					fDataRaw.add(fLine); // this adds the string into the entire database
					fLine = "";//resets the line variable so the string doesn't keep concatenating the lines before it.
				}
				scan.close();
			}
			catch (FileNotFoundException ignored) {}
		}
		
		else{
			System.out.println("ERROR: FileOps can't find the file!");
		}
		return fDataRaw;	
	}
	
	public static Image loadImage(String path){
		Image img = null;
		try {
		    img = ImageIO.read(new File(path));
		    if(img == null){
		    	System.out.println(imgLoadError + path);
		    }
		} 
		catch (IOException e){ System.out.println(imgLoadError + path);} 
		catch(NullPointerException e){System.out.println(imgLoadError + path);}
		
		return img;
	}
	
	public static void registerMaps(){
		Tile grass = new Tile("grassTile.png","dirtSide.png");
		Tile farmland = new Tile("farmTile.png","dirtSide.png");
		Tile dirt = new Tile("dirtTile.png","dirtSide.png");
		
		Overlay house = new Overlay("houseOverlay.png");
		Overlay fence = new Overlay("fenceOverlay.png");
		
		/* Key for the map */
		RegionLoader.mapKey.put("0", grass);
			grass.borderColor = darkGreen;
		RegionLoader.mapKey.put("1", dirt);
			dirt.borderColor = brown;
		RegionLoader.mapKey.put("2", farmland);
			farmland.borderColor = brown;
			
		/* Overlay Key */
		RegionLoader.overlayKey.put("0", null);
		RegionLoader.overlayKey.put("1", house);
		RegionLoader.overlayKey.put("2", fence);
		
		/* Images for Region */
		File folder = new File(regionGraphicsPath);
		File[] allResources = folder.listFiles();
		for(int i = 0; i<allResources.length; i++){
			RegionLoader.images.put(allResources[i].getName(), loadImage(regionGraphicsPath + allResources[i].getName()));
		}
	}
}