package particulates.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import particulates.effects.Snowfall;
import particulates.explosion.Explosion;
import particulates.explosion.Firework3D;
import particulates.particleCommons.ColorRange;
import particulates.particleCommons.Effect;
import particulates.environment.*;
public class MainEnvironment extends ConstructorClass {
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
		
		ArrayList<Effect> myEffects = new ArrayList<Effect>();
		myEffects.add(new Effect("velocityDecay",0,-1));
		myEffects.add(new Effect("distanceSize",0,-1));
		/*
		myEffects.add(new Effect("similarFade",0,40,ColorRange.christmas));
		myEffects.add(new Effect("sparkle",80,-1,ColorRange.christmas));
		myEffects.add(new Effect("explode",100,-1,Color.WHITE));
		*/
		
		
		myEffects.add(new Effect("similarFade",0,40,ColorRange.rgb));
		myEffects.add(new Effect("sparkle",35,20,ColorRange.rgb));
		
		myEffects.add(new Effect("fade",40,25,Color.RED));
		myEffects.add(new Effect("sparkle",55,20,new ColorRange(Color.gray,Color.red)));
		
		myEffects.add(new Effect("fade",65,25,Color.GREEN));
		myEffects.add(new Effect("sparkle",80,20, new ColorRange(Color.gray,Color.green)));
		
		myEffects.add(new Effect("fade",90,25,Color.BLUE));
		myEffects.add(new Effect("sparkle",105,20,ColorRange.blueScale));
		
		myEffects.add(new Effect("explode",115,-1,Color.WHITE));
		
		e = new Firework3D(240,120,ColorRange.rainbow,myEffects);
		e.particleSize = 5;
		e.velocity = 6;
		e.particleShape = Explosion.shapes.CIRCLE;
	}

	public void mousePressed(MouseEvent evt){
		super.mousePressed(evt);
		e.initParticles(evt.getPoint());
	}
	
	public void keyPressed(KeyEvent evt) {}
}


