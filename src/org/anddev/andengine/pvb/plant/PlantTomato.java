package org.anddev.andengine.pvb.plant;

import org.anddev.andengine.pvb.singleton.GameData;

public class PlantTomato extends Plant {
	
	public PlantTomato() {
		super(GameData.getInstance().mPlantTomato);
		
		this.mShotDelay = 1f;
	}
	
}
