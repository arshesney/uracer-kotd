package com.bitfire.uracer.audio;

import com.bitfire.uracer.carsimulation.CarInputMode;
import com.bitfire.uracer.game.logic.PlayerState;

public class CarSoundManager {
	private static PlayerState player = null;

	// sound effects
	private static CarDriftSoundEffect carDrift;
	private static CarEngineSoundEffect carEngine;
	private static CarImpactSoundEffect carImpact;

	public static void load() {
		carEngine = new CarEngineSoundEffect();
		// carEngine.start();

		carDrift = new CarDriftSoundEffect();
		carDrift.start();	// wtf? why the manager should start it in load()?

		carImpact = new CarImpactSoundEffect();
	}

	public static void dispose() {
		carEngine.dispose();
		carDrift.dispose();
		carImpact.dispose();
	}

	public static void setPlayer( PlayerState player ) {
		CarSoundManager.player = player;
	}

	public static void tick() {
		if( player.car.getInputMode() == CarInputMode.InputFromPlayer ) {
			// TODO when update() will use GameData shared data internally, no params, thus a task-based component
			// system can be created
			carEngine.update( player.currSpeedFactor );
			carDrift.update( player.currSpeedFactor );
		}
	}

	// drift events
	public static void driftBegin() {
		carDrift.driftBegin();
	}

	public static void driftEnd() {
		carDrift.driftEnd();
	}

	// crashes
	public static void carImpacted( float impactForce ) {
		carImpact.impact( impactForce, player.currSpeedFactor );
	}
}
