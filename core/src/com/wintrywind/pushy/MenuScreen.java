package com.wintrywind.pushy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends ScreenAdapter {
	
	private final int PLAY_DELAY = 1;

	private Viewport view;
	
	private SpriteBatch batch;
	private BitmapFont font;
	private boolean pushedToPushy = false;
	private boolean timerElapsed = false;
	private InputAdapter inputProcessor;
	
	private final CharSequence title = "PUSHY" ;
	private final CharSequence playText = "Press Space To Begin!";
	
	public MenuScreen(SpriteBatch b, Viewport v) {
		view = v;
		batch = b;
		
		font = new BitmapFont();
		font.getData().setScale(2);
		font.getCache().addText(title, -50, Gdx.graphics.getHeight() / 3, 50, Align.center, false);
		
		inputProcessor = new InputAdapter() {
			public boolean keyDown(int keycode) {
				if (timerElapsed && keycode == Keys.SPACE) {
					pushedToPushy = true;
					System.out.println("space down");
				}
				return false;
			}
		};
		
		Timer tmr = new Timer();
		tmr.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				timerElapsed = true;
				tmr.clear();
				font.getCache().addText(playText, -100, 0, 200, Align.center, false);
			}
		}, PLAY_DELAY);
	}
	
	public boolean hasTimerElapsed() {
		return timerElapsed;
	}
	
	public boolean pushedToPushy() {
		return pushedToPushy;
	}
	
	public InputProcessor getInputProcessor(){
		return inputProcessor;
	}
	
	
	//screen methods
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor((float) .4, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.getCache().draw(batch);
		batch.end();
	}
	
	@Override
	public void resize(int x, int y) {
		view.update(x, y);
	}

	@Override
	public void dispose () {
		font.dispose();
	}

	public void disable() {
		//nullify content
		view = null;
		batch = null;
	}
	
	public void renable(SpriteBatch b, Viewport v) {
		view = v;
		batch = b;
		pushedToPushy = false;
	}
	
}
