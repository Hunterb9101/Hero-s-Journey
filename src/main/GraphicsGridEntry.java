package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import main.Main;
import main.Registry;

public class GraphicsGridEntry extends GraphicsObject {
	public Image src;
	public String text;
	private GraphicsGrid parent;

	public GraphicsGridEntry(Image iSrc, int iX, int iY, String iText, GraphicsGrid parent) {
		super(iX, iY, parent.itemWidth, parent.itemHeight, parent.parentMenu);
		this.parent = parent;
		src = iSrc;
		text = iText;
	}

	public void drawObject(Graphics g) { // SHOULD NEVER BE CALLED EXCEPT BY GRAPHICS GRID
		g.drawImage(src, (int) (x * xScalar), (int) (y * yScalar), (int) (width * xScalar),(int) (height * yScalar), null);
		g.setColor(Color.black);
		g.drawRect((int) (x * xScalar), (int) (y * yScalar), (int) (width * xScalar),
				(int) (height * yScalar));// adds outline to the image
	}

	public void onClick() {	
	}

	public void onHover() {}
}