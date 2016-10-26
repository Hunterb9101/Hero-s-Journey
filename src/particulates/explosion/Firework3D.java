package particulates.explosion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import particulates.particleCommons.ColorRange;
import particulates.particleCommons.Particle;

public class Firework3D extends Explosion {
	public enum Shapes3D{SPHERE};
	public Shapes3D fireworkShape = Shapes3D.SPHERE;
	public Firework3D(int iParticles, int iDur, ColorRange iColorRange) {
		super(iParticles, iDur, iColorRange);
		Explosion.allExplosions.add(this);
		particleMinLife = (int) (.75 * lifeLength);
	}
	
	public void initParticles(Point origin){
		super.initParticles(origin);
		this.origin = origin;
		double dX = 0;
		double dY = 0;
		double dZ = 0;
		
		currLife = 0;
		
		for(int i = 0; i<particleNum; i++){
			switch(fireworkShape){
				case SPHERE:
					dX = rand.nextDouble()*(2*velocity) - (velocity);
					dY = rand.nextDouble()*(2*velocity) - (velocity);
					dZ = rand.nextDouble()*(2*velocity) - (velocity);

					// Ensures no particles will be created on edges of sphere
					while(Math.pow(dX, 2) + Math.pow(dY,2) > (int) Math.pow(velocity, 2)){
						dX = rand.nextDouble()*(2*velocity) - (velocity);
						dY = rand.nextDouble()*(2*velocity) - (velocity);
					}
					
					while(Math.pow(dX, 2) + Math.pow(dZ, 2) > (int) Math.pow(velocity, 2) || Math.pow(dY, 2) + Math.pow(dZ, 2) > (int) Math.pow(velocity, 2)){
						dZ = rand.nextDouble()*(2*velocity) - (velocity);
					}
					break;
			}
			
			Color color = particleColor.generateColor();
			color = new Color(color.getRed(),color.getGreen(),color.getBlue(),196);
			
			allParticles.add(new Particle(origin,particleSize,dX,dY,dZ,rand.nextInt(lifeLength-particleMinLife) + particleMinLife,color));
		}
	}
}
