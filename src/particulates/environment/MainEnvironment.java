package particulates.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import particulates.effects.Snowfall;
import particulates.explosion.*;
import particulates.particleCommons.ColorRange;

public class MainEnvironment extends ConstructorClass {
	private static final long serialVersionUID = 1L;
	int explosionState = 0;
	Firework3D e;
	Snowfall s = new Snowfall(180,new ColorRange(Color.white));
	
	Random rand = new Random();
	
	public void doInitialization(int width, int height){
		this.setSize(600,600);
		Snowfall.width = 600;
		Snowfall.height = 600;
		s.velocity = 2;
		s.initializeEffect();
	}
	
	synchronized public void drawFrame(Graphics g, int width, int height) {
		this.setSize(600,600);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		Explosion.drawAll(g);
		System.out.println(Explosion.allExplosions.size());
		
		e = new Firework3D(120,40,ColorRange.rgb,"");
		e.particleSize = 5;
		e.velocity = 8;
		e.particleShape = Explosion.shapes.FILLEDCIRCLE;
	}

	public void mousePressed(MouseEvent evt){
		super.mousePressed(evt);
		e.initParticles(evt.getPoint());
	}
	
	public void keyPressed(KeyEvent evt) {}
}


