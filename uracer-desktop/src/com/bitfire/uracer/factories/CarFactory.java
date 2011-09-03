package com.bitfire.uracer.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.bitfire.uracer.Art;
import com.bitfire.uracer.entities.EntityType;
import com.bitfire.uracer.entities.vehicles.Car;
import com.bitfire.uracer.entities.vehicles.CarGraphics;
import com.bitfire.uracer.simulations.car.CarDescriptor;
import com.bitfire.uracer.utils.Convert;
import com.bitfire.uracer.utils.FixtureAtlas;

public class CarFactory
{
	public static final int OldSkool = 1;
	public static final int ModernBlack = 2;

	public static Car create( int carType, CarDescriptor descriptor, Vector2 position, float orientation, boolean isPlayer )
	{
		TextureRegion region = null;
		int b2deditorSourceW = 0;
//		int b2deditorSourceH = 0;
		String shapeName = null;
		String shapeRef = null;

		switch( carType )
		{
		case OldSkool:
			region = Art.cars.findRegion( "electron" );
			b2deditorSourceW = region.getRegionWidth();
//			b2deditorSourceH = region.getRegionHeight();
			shapeName = "data/base/electron.shape";
			shapeRef = "../../data-src/base/cars/electron.png";
			break;

		case ModernBlack:
			region = new TextureRegion( Art.hqCars, 0, 0, 210, 424 );
			b2deditorSourceW = Art.hqCars.getRegionWidth();
//			b2deditorSourceH = Art.hqCars.getRegionHeight();
			shapeName = "data/base/hqcars.shape";
			shapeRef = "../../data-src/base/black-car.png";
			break;
		}

		CarGraphics graphics = new CarGraphics( descriptor, region );
		Car car = Car.createForFactory( graphics, descriptor, position, orientation, isPlayer );

		// set physical properties and apply shape
		FixtureDef fd = new FixtureDef();
		fd.density = descriptor.carModel.density;
		fd.friction = descriptor.carModel.friction;
		fd.restitution = descriptor.carModel.restitution;

		// apply scaling factors
		Vector2 offset = new Vector2( -descriptor.carModel.width / 2f, -descriptor.carModel.length / 2f );

		Vector2 ratio = new Vector2(
				descriptor.carModel.width / Convert.px2mt( region.getRegionWidth() ),
				descriptor.carModel.length / Convert.px2mt( region.getRegionHeight() ) );

		// box2d editor "normalization" contemplates just a width-bound ratio.. WTF?
		Vector2 factor = new Vector2(
				Convert.px2mt( b2deditorSourceW * ratio.x ),
				Convert.px2mt( b2deditorSourceW * ratio.y ) );

		FixtureAtlas atlas = new FixtureAtlas( Gdx.files.internal( shapeName ) );
		atlas.createFixtures( car.getBody(), shapeRef, factor.x, factor.y, fd, offset, EntityType.Car );

		return car;
	}
}