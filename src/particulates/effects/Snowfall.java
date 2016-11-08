package particulates.effects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import particulates.particleCommons.ColorRange;
import particulates.particleCommons.Particle;

public class Snowfall {
	public static int width = 1;
	public static int height = 1;
	public Random rand = new Random(); //for random number generation

	public enum shapes{SQUARE,CIRCLE,FILLEDSQUARE,FILLEDCIRCLE,ASTERIK};

	public double velocity = 2;
	
	public shapes particleShape = shapes.ASTERIK;
	public int particleSize = 8;
	public int particleVariability = 4;
	
	public ColorRange particleColor;
	int particleNum; // Number of particles

	public boolean isLive = false; //Explosion is running

	int currLife = 0; //Current frame of Snowflakes

	ArrayList<Particle> allParticles;

	public Snowfall(int iParticles, ColorRange iColorRange){
		particleNum = iParticles;
		allParticles = new ArrayList<>();
		particleColor = iColorRange;
	}
	
	public Snowfall(int iParticles, Color iColor){
		particleNum = iParticles;
		allParticles = new ArrayList<>();
		particleColor = new ColorRange(iColor);
	}


	public void initializeEffect(){
		double dX;
		double dY;
		Particle newParticle;
		
		isLive = true;
		currLife = 0;
		
		for(int i = 0; i<particleNum; i++){
			dX = 0;
			dY = velocity + rand.nextDouble()*velocity;
			newParticle = new Particle(new Point(rand.nextInt(width),rand.nextInt(height)),particleSize,dX,dY,0,0,particleColor.generateColor(),null);
			newParticle.size = rand.nextInt(particleVariability) + particleSize - particleVariability/2;
			allParticles.add(newParticle);	
		}
	}
	
	public void drawSnowfall(Graphics g){
		Particle newParticle;
		double dX;
		double dY;
		
		isLive = true;
		for(int i = 0; i<allParticles.toArray().length;i++){
			Particle p = allParticles.get(i);
			if(p.currY>height){
				allParticles.remove(i);

				dX = 0;
				dY = velocity + rand.nextDouble()*velocity;
				newParticle = new Particle(new Point(rand.nextInt(width),1),particleSize,dX,dY,0,0,particleColor.generateColor(),null);
				newParticle.size = rand.nextInt(particleVariability) + particleSize - particleVariability/2;
				
				allParticles.add(newParticle);
			}
			else{
				this.drawSnowfall(g);
			}
		}
		currLife++;
	}
}
