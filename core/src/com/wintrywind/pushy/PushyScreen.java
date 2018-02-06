package com.wintrywind.pushy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PushyScreen extends ScreenAdapter {
	private PushyStage stage;
	private BitmapFont font;

	public PushyScreen(SpriteBatch b, Viewport v) {
		stage = new PushyStage(v, b);
	}
	
	public InputProcessor getInputProcessor() {
		return stage;
	}
	
	private PushyStage getStage() {
		return stage;
	}
	
	public void restart(SpriteBatch b, Viewport v) {
		newGame(b, v);
	}
	
	private void newGame(SpriteBatch b, Viewport v) {
		
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, (float) .7, 1, (float) .6);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getStage().draw();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
	
	
	class PushyStage extends Stage {
		
		private final Rectangle iceBlock = new Rectangle(-500, -200, 1000, 400);
		private final Rectangle playerIce = new Rectangle(iceBlock).setWidth(iceBlock.width - 100).setX(iceBlock.x + 100);
		
		private ShapeRenderer sr;
		
		public PushyStage(Viewport v, SpriteBatch b) {
			super(v,b);
			Pushers pushers = new Pushers();
			pushers.setCullingArea(iceBlock);
			RepeatAction spawnPushers = new RepeatAction();
			spawnPushers.setAction(new MakePushers());
			pushers.addAction(spawnPushers);
			//pushers.addActor(spawnPushers.getActor());
			
			sr = new ShapeRenderer();
			sr.setProjectionMatrix(b.getProjectionMatrix());
			
		}
		
		private void drawBackground() {
			sr.begin(ShapeType.Filled);
			sr.setColor(0, (float) .7, 1, (float) .3);
			sr.rect(iceBlock.x, iceBlock.y, iceBlock.width, iceBlock.height);
			sr.end();
		}
		

		public void draw() {
			drawBackground();
			super.draw();
		}
		
	}
	
	class MakePushers extends Action {
		
		@Override
		public boolean act(float delta) {
			//TODO: not return false
			return false;
		}
		
	}
}
