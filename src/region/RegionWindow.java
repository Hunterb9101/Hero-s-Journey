package region;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import main.Registry;

public class RegionWindow extends ConstructorClass{
	public static boolean firstFrame = true;
	
	public static int[] addToArray(int[] baseArr, int add){
		for(int i = 0; i<baseArr.length;i++){
			baseArr[i] += add;
		}
		return baseArr;
	}
	
	public void init(int width, int height){
		Registry.registerMaps();
		RegionLoader.setMap("maps/SnowyMap");
		this.setSize(640,640);
	}
	
	public void draw(Graphics g, int width, int height){
		if(firstFrame){
			RegionLoader.parseMap(g, width,height);
			firstFrame = false;
		}
		
		RegionLoader.fastDrawMap(g,width,height);
	}
	
	public void keyPressed(KeyEvent evt){
		switch(evt.getKeyChar()){
		case 'w': RegionLoader.yOffset += RegionLoader.scrollSpeed; break;
		case 'a': RegionLoader.xOffset += RegionLoader.scrollSpeed; break;
		case 's': RegionLoader.yOffset -= RegionLoader.scrollSpeed; break;
		case 'd': RegionLoader.xOffset -= RegionLoader.scrollSpeed; break;
	}
	}
	public void mousePressed(MouseEvent evt) {
		super.mousePressed(evt);
		
		// Deselect Tile //
		try{
			TileReference.allTiles.get(Tile.getIdxNum(RegionLoader.selected[0], RegionLoader.selected[1], RegionLoader.rowLen)).selected = false;
		}catch(NullPointerException | IndexOutOfBoundsException e){}
		
		// Select Clicked Tile //
		try{
			RegionLoader.selected = Tile.selectTile(evt.getX(), evt.getY());
			TileReference.allTiles.get(Tile.getIdxNum(RegionLoader.selected[0], RegionLoader.selected[1], RegionLoader.rowLen)).selected = true;
		}catch(NullPointerException e){}
	}
	
	public void keyReleased(KeyEvent evt){
	}
}
