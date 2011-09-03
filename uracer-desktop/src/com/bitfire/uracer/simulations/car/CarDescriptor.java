package com.bitfire.uracer.simulations.car;

import com.badlogic.gdx.math.Vector2;

/**
 * Describes the global state of the car entity providing access to both the base
 * physical model information and the processed per-timestep data resulting
 * after each integration.
 *
 * @author manuel
 *
 */

public class CarDescriptor
{
	public CarDescriptor()
	{
		angularvelocity = steerangle = throttle = brake = angularOrientation = 0;
		position_wc.set( 0, 0 );
		velocity_wc.set( 0, 0 );
	}

	public CarDescriptor(CarDescriptor other)
	{
		set(other);
	}

	public static CarDescriptor create()
	{
		return new CarDescriptor();
	}

	public void set(CarDescriptor desc)
	{
		// a shallow copy should be enough
		this.carModel = desc.carModel;

		this.angularvelocity = desc.angularvelocity;
		this.steerangle = desc.steerangle;
		this.throttle = desc.throttle;
		this.brake = desc.brake;
		this.angularOrientation = desc.angularOrientation;
		this.position_wc.set( desc.position_wc );
		this.velocity_wc.set( desc.velocity_wc );
	}

	// physical model data
	public CarModel carModel = new CarModel();

	// results

	// vector-related
	public Vector2 position_wc = new Vector2(); // position of car center in world coordinates
	public Vector2 velocity_wc = new Vector2(); // velocity vector of car in world coordinates

	// angle-related
	public float angularvelocity;
	public float steerangle; // angle of steering (input)

	// impulses
	public float throttle; // amount of throttle (input)
	public float brake; // amount of braking (input)

	// internally computed
	public float angularOrientation;
}