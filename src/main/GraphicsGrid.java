package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class GraphicsGrid extends GraphicsObject {
	public GraphicsGridEntry[] items = new GraphicsGridEntry[25];
	public int rows;
	public int columns;
	public static int xPadding = 3;
	public static int yPadding = 3;

	int x;
	int y;

	int itemWidth = 90;
	int itemHeight = 90;
	public Main.menuItem parentMenu;

	public GraphicsGrid(int iX, int iY, int rows, int columns, int itemSize, Main.menuItem parentMenu) {
		// x start, y start, number of rows in grid, number of columns in grid,
		// what menu it is in for hover purposes
		super(iX, iY, (rows * itemSize) + ((rows - 1) * xPadding), (columns * itemSize) + ((columns - 1) * yPadding),
				parentMenu);
		itemWidth = itemSize;
		itemHeight = itemSize;
		this.parentMenu = parentMenu;
		x = iX;
		y = iY;
		this.rows = rows;
		this.columns = columns;
	}

	@Override
	public void drawObject(Graphics g) {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				try {
					items[c * rows + r].drawObject(g);// draws the items
				} 
				catch (NullPointerException e) {
					g.setColor(Color.BLACK);
					g.fillRect(
							(int) ((x + r * itemWidth + xPadding * r) * xScalar),
							(int) ((y + c * itemHeight + yPadding * c) * yScalar), 
							(int) (itemWidth * xScalar),
							(int) (itemHeight * yScalar)
					); // Creates boxes where there weren't any items
				}
			}
		}
	}

	public void addEntry(Image src, String text) {
		boolean needsEntry = true;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (items[c * rows + r] == null) {
					if (needsEntry) {
						GraphicsGridEntry entry = new GraphicsGridEntry(src, x + r * itemWidth + xPadding * r, y + c * itemHeight + yPadding * c, text, this);
						// makes a grid entry at the right spot
						items[c * rows + r] = entry;
						needsEntry = false;
					}
				}
				// Basic Checking Mechanism (Displays Numbers and Rectangles)
			}
		}
	}

	@Override
	public void onClick() {
	}

	@Override
	public void onHover() {
		// do nothing because it is the grid
	}

}