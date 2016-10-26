package particulates.particleCommons;

import java.awt.Color;
import java.awt.Point;

public class Particle {
	public boolean fades = true;
	
	public double currX;
	public double currY;
	public double currZ;
	
	public double deltX;
	public double deltY; 
	public double deltZ;
	
	public double deltAlpha;
	
	public int lifespan;
	
	public Color color;
	
	public double velocityDecayMultiplier = .975;
	public double alpha;
	private static int alphaLowRange = 64;
	private static int alphaHighRange = 255;
	
	public double size = 8;

	public Particle(Point p, double size, double dX, double dY, double dZ, int life, Color iColor){
		currX = p.getX();
		currY = p.getY();
		currZ = 0;
		deltX = dX;
		deltY = dY;
		deltZ = dZ;
		lifespan = life;
		color = iColor;
		
		deltAlpha = 2*((dZ > 0)? 1:-1); //Overall Change
		alpha = iColor.getAlpha();
		this.size = size;
	}
	public void applyVelocityDecay(){
		deltX*=velocityDecayMultiplier;
		deltY*=velocityDecayMultiplier;
		deltZ*=velocityDecayMultiplier;
	}
	public void applyDistanceFadeEffect(){
		alpha+=deltAlpha;
		if(alpha > alphaHighRange){
			alpha = alphaHighRange;
		} else if(alpha < alphaLowRange){
			alpha = alphaLowRange;
		}
		color = new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)(alpha));
	}
	
	public void applyDistanceEffect(){
		size += deltZ*.03;
	}
}
