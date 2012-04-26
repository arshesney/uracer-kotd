package com.bitfire.uracer.game.logic.hud.debug;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bitfire.uracer.Art;
import com.bitfire.uracer.Config;
import com.bitfire.uracer.game.Director;
import com.bitfire.uracer.game.events.GameEvents;
import com.bitfire.uracer.game.logic.hud.HudElement;
import com.bitfire.uracer.game.logic.trackeffects.effects.PlayerSkidMarks;
import com.bitfire.uracer.game.player.PlayerCar;
import com.bitfire.uracer.game.player.PlayerDriftState;
import com.bitfire.uracer.game.rendering.GameRendererEvent;

/** Encapsulates a special hud element that won't render as usual, but it will schedule
 * its drawing operations by registering to the GameRenderer's BatchDebug event.
 *
 * @author bmanuel */
public class HudDebug extends HudElement {

	private PlayerCar player;
	private PlayerDriftState driftState;
	private PlayerSkidMarks skidMarks;
	private HudDebugMeter meterLatForce, meterSkidMarks;
	private Array<HudDebugMeter> meters = new Array<HudDebugMeter>();
	private Vector2 pos = new Vector2();

	private GameRendererEvent.Listener gameRendererEvent = new GameRendererEvent.Listener() {
		@Override
		public void gameRendererEvent( GameRendererEvent.Type type ) {
			if( Config.Graphics.RenderHudDebugInfo ) {
				onDebug( GameEvents.gameRenderer.batch );
			}
		}
	};

	public HudDebug( PlayerCar player, PlayerDriftState driftState, PlayerSkidMarks skidMarks ) {
		GameEvents.gameRenderer.addListener( gameRendererEvent, GameRendererEvent.Type.BatchDebug, GameRendererEvent.Order.DEFAULT );

		this.player = player;
		this.driftState = driftState;
		this.skidMarks = skidMarks;

		// meter lateral forces
		meterLatForce = new HudDebugMeter( 100, 5 );
		meterLatForce.setLimits( 0, 1 );
		meterLatForce.setName( "ds" );
		meters.add( meterLatForce );

		// meter skid marks count
		meterSkidMarks = new HudDebugMeter( 100, 5 );
		meterSkidMarks.setLimits( 0, PlayerSkidMarks.MaxSkidMarks );
		meterSkidMarks.setName( "sm" );
		meters.add( meterSkidMarks );
	}

	@Override
	public void dispose() {
	}

	@Override
	public void onTick() {
		// update values

		meterLatForce.setValue( driftState.driftStrength );
		if( driftState.isDrifting ) {
			meterLatForce.color.set( .3f, 1f, .3f, 1f );
		} else {
			meterLatForce.color.set( 1f, 1f, 1f, 1f );
		}

		meterSkidMarks.setValue( skidMarks.getParticleCount() );
	}

	public void onDebug( SpriteBatch batch ) {

		float prevHeight = 0;
		int index = 0;
		for( HudDebugMeter m : meters ) {

			pos.set( Director.screenPosForPx( player.state().position ) );
			pos.x -= m.getWidth() * 0.5f;
			pos.y += 50;// * displacement.x;

			// offset by index
			pos.y += index * (prevHeight + Art.fontHeight);

			m.setPosition( pos );
			m.render( batch );

			index++;
			prevHeight = m.getHeight();
		}
	}

	@Override
	public void onRender( SpriteBatch batch ) {
	}

	@Override
	public void onReset() {
	}
}
