package region;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.Registry;
import particulates.effects.Snowfall;
import particulates.particleCommons.ColorRange;

public class RegionLoader{	
	protected static int rowLen; //Value assigned in parseMap
	
	// The original offsets of board
	public static final int origYOffset = 64;
	public static final int origXOffset = 0;
	
	public static int yOffset = origYOffset;
	public static int xOffset = origXOffset;
		
	public static int[] selected = new int[]{0,0};
	public static int scrollSpeed = 4; //Speed at which keys move the screen around
	
	static String[] map;
	static String hMap;
	static String oMap; 
	
	static Color skyColor = Registry.nightBlack;
	static Color mistColor = Registry.snowyNightMist;
	
	public static enum particulates{NONE,SNOW};
	static particulates effect = particulates.SNOW;
	
	public static Snowfall snowEffect = new Snowfall(360, new ColorRange(Color.white));
	
	public static HashMap<String,Tile> mapKey = new HashMap<String,Tile>();	
	public static HashMap<String,Overlay> overlayKey = new HashMap<String,Overlay>();
	public static HashMap<String,Image> images = new HashMap<String,Image>();
	
	
	public static void initEffects(){
		Snowfall.width = 640;
		Snowfall.height = 640;
		snowEffect.velocity = .2;
		snowEffect.initializeEffect();
	}
	
	public static void setMap(String filePath){	
		initEffects();
		TileReference.allTiles.clear();
		
		File f = new File(filePath);
		List<String> rawData = Registry.ReadFile(f);	
		
		// Normal Map //
		String raw = "";

		for(int i = 0; i<rawData.size(); i++){
			if(!rawData.get(i).equalsIgnoreCase("#")){ raw+=rawData.get(i);}
			else{ break;}
		}
		
		ArrayList<String> data = new ArrayList<String>();
		int tileShift = 0;
		rowLen = Integer.parseInt(raw.split("x")[0]);
		raw = raw.split("x")[1];
		while(raw.length() > 0){
			data.add(raw.substring(0,rowLen+tileShift));
			raw = raw.substring(rowLen + tileShift);
			tileShift = (tileShift==1) ? 0:1;
		}
		
		map = (String[]) data.toArray(new String[0]);
		
		// Height Map //
		hMap = "";
		int startIdx = rawData.indexOf("#");
		for(int i = startIdx+1; i<rawData.size(); i++){
				hMap+=rawData.get(i);
		}
		
		// Overlay Map //
		oMap = "";
		for(int i = 0; i<rawData.size(); i++){
				oMap+=rawData.get(i);
		}
		oMap = oMap.split("#")[2];
	}
	
	
	//////////////////////////////////////////////////
	//				      DRAW MAPS		     		//
	//////////////////////////////////////////////////
	public static void generateMap(Graphics g, int width, int height, int mapWidth, int mapHeight){
		g.setColor(skyColor);;
		g.fillRect(0, 0, width, height);
		
		String[][] heightMap = smootherNoise(smootherNoise(pinkNoise(5,mapWidth,mapHeight)));
		int[] xPoints = {-Tile.tileSize/2,0,Tile.tileSize/2,};
		int[] yPoints = {0,-Tile.tileSize/2,0,Tile.tileSize/2};
		
		int tileShift = 0; //With a diamond shaped pattern, 1 extra is needed per row, this keeps track.
		int cntr = 0; //Not for a 'for' loop for once!!!
		
		for(int y = 0; y<mapHeight-1; y++){
			for(int x=0; x<mapWidth-1 + tileShift; x++){
				/* Shift Every Other Tile Row */
				for(int i = 0; i<xPoints.length;i++){
					if((x==0)&& tileShift==1){ xPoints[i] += Tile.tileSize/2;}
					else{ xPoints[i] += Tile.tileSize;}
				}
				
				Tile thisTile = mapKey.get("0");
				thisTile.drawTile(g, Integer.parseInt(heightMap[x][y]), new int[]{x,y}, thisTile, null, "");
				cntr++;
			}
			
			int[] xDefaultPoints = {-Tile.tileSize/2,0,Tile.tileSize/2,0};
			xPoints = xDefaultPoints; //Resets X values to normal
			tileShift = (tileShift==1) ? 0:1; //Invert Tile shift
			
			yPoints = RegionWindow.addToArray(yPoints,Tile.tileSize/2);
		}		
	}
	
	public static String[][] pinkNoise(int amplitude, int width, int height){
		Random rand = new Random();
		
		String[][] randMap = new String[width][height];
		for(int i = 0; i<width; i++){
			for(int j=0; j<height; j++){
				randMap[i][j] = String.valueOf(rand.nextInt(amplitude));
			}
		}
		return randMap;
	}
	
	public static String[][] smootherNoise(String[][] noise){
		String[][] newNoise = noise;
		for(int i = 0; i<noise.length - 1; i++){
			for(int j = 0; j<noise[i].length - 1; j++){
				newNoise[i][j] = String.valueOf(Math.round(.25 * (Integer.parseInt(noise[i][j])+Integer.parseInt(noise[i][j+1])+Integer.parseInt(noise[i+1][j])+Integer.parseInt(noise[i+1][j+1])))); 
			}
		}
		return newNoise;
	}
	public static void parseMap(Graphics g, int width, int height){
		g.setColor(skyColor);
		g.fillRect(0, 0, width, height);
		
		int[] xPoints = {-Tile.tileSize/2,0,Tile.tileSize/2,};
		int[] yPoints = {0,-Tile.tileSize/2,0,Tile.tileSize/2};
		
		int tileShift = 0; //With a diamond shaped pattern, 1 extra is needed per row, this keeps track.
		int cntr = 0; //Not for a 'for' loop for once!!!
				
		for(int y = 0; y<map.length; y++){
			for(int x=0; x<map[0].length() + tileShift; x++){
				/* Shift Every Other Tile Row */
				for(int i = 0; i<xPoints.length;i++){
					if((x==0)&& tileShift==1){ xPoints[i] += Tile.tileSize/2;}
					else{ xPoints[i] += Tile.tileSize;}
				}
				
				Tile thisTile = ((Tile)mapKey.get(map[y].substring(x,x+1)));
				thisTile.drawTile(g, Integer.parseInt(hMap.substring(cntr,cntr+1)), new int[]{x,y}, thisTile, overlayKey.get(oMap.substring(cntr,cntr+1)), "");
				cntr++;
			}
			
			int[] xDefaultPoints = {-Tile.tileSize/2,0,Tile.tileSize/2,0};
			xPoints = xDefaultPoints; //Resets X values to normal
			tileShift = (tileShift==1) ? 0:1; //Invert Tile shift
			
			yPoints = RegionWindow.addToArray(yPoints,Tile.tileSize/2);
		}
	}
	
	public static void fastDrawMap(Graphics g, int width, int height){
		g.setColor(skyColor);
		g.fillRect(0, 0, width, height);
		
		for(int i = 0; i<TileReference.allTiles.size(); i++){
			TileReference thisTile = TileReference.allTiles.get(i);
			if(thisTile.selected){
				thisTile.parent.drawTile(g, thisTile.height, TileReference.allTiles.get(i).coords, TileReference.parentSelected, thisTile.overlay, "");
			}
			else{
				thisTile.parent.drawTile(g, thisTile.height, TileReference.allTiles.get(i).coords, thisTile.parent, thisTile.overlay, "");
			}
		}
		
		if(effect == particulates.SNOW){
			snowEffect.drawSnowfall(g);
			Snowfall.width = width;
			Snowfall.height = height;
		}
		
		g.setColor(mistColor);
		g.fillRect(0, 0, width, height);
	}
}
