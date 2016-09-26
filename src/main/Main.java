package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import region.ConstructorClass;
import java.awt.Image;
import java.awt.event.MouseEvent;

public class Main extends ConstructorClass{
	public static enum menuItem{NONE,MAIN};
	public static menuItem currentMenu = menuItem.NONE;
	public static GraphicsImage[] arrows;
	GraphicsGrid smallGrid = new GraphicsGrid(15,570,9,1,65, menuItem.MAIN);
	public void init(int width, int height){
		this.setSize(640,640);
		
		GraphicsObject.defaultWidth = 640;
		GraphicsObject.defaultHeight = 640;
		
		arrows = new GraphicsImage[]{
				new GraphicsImage(Registry.loadImage("symbols/UpArrow.png"),210,518,20,20,menuItem.MAIN),
				new GraphicsImage(Registry.loadImage("symbols/DownArrow.png"),210,537,20,20,menuItem.MAIN),
				new GraphicsImage(Registry.loadImage("symbols/LeftArrow.png"),576, 528,20,20,menuItem.MAIN),
				new GraphicsImage(Registry.loadImage("symbols/RightArrow.png"),598, 528,20,20,menuItem.MAIN)
		};
	}
	
	public void draw(Graphics g, int width, int height){
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.black);
		
		frame.setFont(new Font("SanSerif", Font.BOLD, 30)); // Default font
		g.drawString("Overlay Menu", 5, (int)(height*.85));
		
		arrows[0].drawObject(g);
		arrows[1].drawObject(g);
		arrows[2].drawObject(g);
		arrows[3].drawObject(g);
		
		smallGrid.drawObject(g);
		GraphicsObject.setDimens(width, height);
	}
	public void mousePressed(MouseEvent evt) {
		super.mousePressed(evt);
	}
}
