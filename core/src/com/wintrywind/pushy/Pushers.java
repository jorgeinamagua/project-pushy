package com.wintrywind.pushy;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.math.MathUtils;

public class Pushers extends Group {
	private PusherPool pusherPool;
	private final int PUSHER_POOL_MIN = 32;

	public Pushers() {
		super();
		pusherPool = new PusherPool(PUSHER_POOL_MIN);
	}
	
	
	
	public void spawnPushers() {
		//TODO: spawn method
		//randomize num of pushers from 0 to 8
		// if 4 or less make a random amount of em big pushers
		// give them random acceleration (extended from action)
		// add them all to group, make them renderable
		Pusher p = newPusher(); //IGNORE -> TBDELETED
		p.clear(); //IGNORE -> TBDELETED
		// above two lines are to suppress compiler warnings
	}
	
	// ask group to obtain new pusher with initialized action etc in its group to make
	private Pusher newPusher() {
		Pusher p = pusherPool.obtain();
		return p;
	}
	
	//honestly we're wrapping this class under Pushers, will it be worth it?
	class Pusher extends Actor {
		
		
	}
	
	class PusherPool extends Pool<Pusher> {
		
		PusherPool(int size) {
			super(size);
		}

		@Override
		protected Pusher newObject() {
			return new Pusher();
		}
		
	}
	
	class Motion extends TemporalAction {

		@Override
		protected void update(float percent) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
