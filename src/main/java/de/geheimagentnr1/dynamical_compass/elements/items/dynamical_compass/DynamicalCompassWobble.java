package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


//package-private
@OnlyIn( Dist.CLIENT )
class DynamicalCompassWobble {
	
	
	private double rotation;
	
	private double deltaRotation;
	
	private long lastUpdateTick;
	
	//package-private
	boolean shouldUpdate( long gameTime ) {
		
		return lastUpdateTick != gameTime;
	}
	
	//package-private
	void update( long gameTime, double oldRotation ) {
		
		lastUpdateTick = gameTime;
		double rotationDelta = oldRotation - rotation;
		rotationDelta = Mth.positiveModulo( rotationDelta + 0.5D, 1.0D ) - 0.5D;
		deltaRotation += rotationDelta * 0.1D;
		deltaRotation *= 0.8D;
		rotation = Mth.positiveModulo( rotation + deltaRotation, 1.0D );
	}
	
	//package-private
	double getRotation() {
		
		return rotation;
	}
}
