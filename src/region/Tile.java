package region;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Tile {	
	static int tileSize = 48;
    public static final int pxHeight = 12;
	
	public Color textColor = Color.black;
	
	public Color borderColor = Color.black;
	public boolean hasBorder = true;
	
	public String sideImage;
	public String topImage;
	
	public Tile(String imgPath, String imgPath2){
		this.topImage = imgPath;
		this.sideImage = imgPath2;
	}
	
	public static int[] selectTile(int mouseX, int mouseY){
		int[] coords = null;
		
		mouseX -= RegionLoader.xOffset - RegionLoader.origXOffset;
		mouseY -= RegionLoader.yOffset - RegionLoader.origYOffset;
		
		for(int i = TileReference.allTiles.size()-1 ; i>= 0; i--){
			if(contains(new Point(mouseX,mouseY), TileReference.allTiles.get(i).xPoints, TileReference.allTiles.get(i).yPoints)){
				coords = TileReference.allTiles.get(i).coords;
				break;
			}
		}	
		
		return coords;
	}
	public void drawTile(Graphics g, int height, int[] coords, Tile tile, Overlay overlay, String dispText){
		int[] xPoints = coords2Points(coords[0],coords[1],"x");
		int[] yPoints = coords2Points(coords[0],coords[1],"y");
		
		Image imgtop = RegionLoader.images.get(tile.topImage);
		Image imgside = RegionLoader.images.get(tile.sideImage);
		
		for(int h = 0; h<height; h++){			
			yPoints = RegionWindow.addToArray(yPoints,-pxHeight);
			g.drawImage(imgside,xPoints[0],yPoints[0],null);
			g.drawImage(RegionLoader.images.get("shadow.png"), xPoints[0], yPoints[0], null);
		}		
		
		g.drawImage(imgtop,xPoints[0], yPoints[1], null);
		int[] newCoords = new int[]{coords[0],coords[1]};
		new TileReference(tile, overlay, height, newCoords);
		
		if(textColor.equals("")){
			g.setColor(textColor);
			g.drawString(dispText, xPoints[1] - 16, yPoints[1]+tileSize/2 + 8);
		}
		
		if(hasBorder){
			g.setColor(borderColor);
			g.drawPolygon(xPoints, yPoints, 4);
		}
		
		try{
			g.drawImage(RegionLoader.images.get(overlay.topImage),xPoints[0],yPoints[1]-RegionLoader.images.get(overlay.topImage).getHeight(null)+tileSize,null);
		}catch(NullPointerException e){}
		
		yPoints = RegionWindow.addToArray(yPoints,pxHeight*height);	
	}
	
	
///////////////////////////////////////////
//			  Tile Utilities             //
///////////////////////////////////////////
	public static void setTile(Tile newTile, int[] coords){
		int idx = getIdxNum(coords[0],coords[1],RegionLoader.rowLen);
		System.out.println(RegionLoader.rowLen);
		TileReference.allTiles.get(idx).parent = newTile;
	}
	
	public static int getIdxNum (int x, int y, int rowNum){
		return x+y*(rowNum) + (y/2);
	}
	
	public static int[] coords2Points(int x, int y, String returnVar){
		int refPointX =((y%2==0) ? tileSize/2:0) + RegionLoader.xOffset + tileSize*x ;
		int refPointY = tileSize/2*y + RegionLoader.yOffset;
		
		if(returnVar.equalsIgnoreCase("x")){
			return new int[]{refPointX,refPointX+tileSize/2,refPointX+tileSize,refPointX+tileSize/2};
		}
		else if(returnVar.equalsIgnoreCase("y")){
			return new int[]{refPointY,refPointY - tileSize/2, refPointY, refPointY + tileSize/2};
		}
		else{
			throw new IllegalArgumentException();
		}
	}
	
	public static int[] points2Coords(int[] xPoints, int[] yPoints){
		int y = yPoints[0]/tileSize;
		int x = (xPoints[0] - ((y%2==0)?tileSize/2:0) - RegionLoader.xOffset)/tileSize;
		return new int[]{x,y};
	}
	
	public static boolean contains(Point test, int[] xPoints, int[] yPoints) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = xPoints.length - 1; i < xPoints.length; j = i++) {
	        if (((yPoints[i] > test.y) != yPoints[j] > test.y) &&
	            (test.x < ((xPoints[j] - xPoints[i]) * (test.y - yPoints[i]) / (yPoints[j]-yPoints[i]) + xPoints[i]))) {
	          result = !result;
	         }
	      }
	      return result;
	 }
}