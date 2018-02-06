package com.wintrywind.pushy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PushyGame extends Game {
	//view
	ScreenViewport view;
	SpriteBatch batch;

	GL20 gl;
	
	//state
	PushyState state;
	
	//screens 
	SplashScreen introScreen;
	MenuScreen menu;
	PushyScreen gameScreen;

	//input
	InputMultiplexer minput;
	
	
	@Override
	public void create () {
		state = PushyState.Init;
		
		gl = Gdx.graphics.getGL20();
		view = new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		//view.setUnitsPerPixel(2);
		
		minput = new InputMultiplexer();
		minput.addProcessor(new PushyUIProcessor());
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(view.getCamera().combined);

		introScreen = new SplashScreen(batch, view);
		minput.addProcessor(introScreen);
		Gdx.input.setInputProcessor(minput);
		
		setScreen(introScreen);
		state = PushyState.Splash;
		
	}
	
	private void newGame() {
		if (gameScreen != null) {
			gameScreen.restart(batch, view);
		} else {
			gameScreen = new PushyScreen(batch, view);
		}
		minput.addProcessor(gameScreen.getInputProcessor());
		setScreen(gameScreen);
	}
	
	@Override
	public void resize (int width, int height) {
		if (screen != null) screen.resize(width, height);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		menu.dispose();
	}
	
	private class PushyUIProcessor implements InputProcessor {
		
		private boolean pdLately = false;

		@Override
		public boolean keyDown(int keycode) {
			switch (state) {
			case Splash: {
				if (introScreen.splashed() && keycode == Keys.SPACE) {
					state = state.step();
					menu = new MenuScreen(batch, view);
					setScreen(menu);
					minput.removeProcessor(introScreen);
					minput.addProcessor(menu.getInputProcessor());
					introScreen.dispose();
					return true;
				}
			}
			case Pushing: {
				if (!pdLately && keycode == Keys.P) {
					pdLately = true;
					return true;
				}
			}
			case Paused: {
				if (pdLately && keycode == Keys.P) {
					pdLately = true;
					return true;
				}
			}
			
			default: return false;
			}

		}

		@Override
		public boolean keyUp(int keycode) {
			switch (state) {
			case Menu: {
				if (menu.pushedToPushy() && keycode == Keys.SPACE) {
					minput.removeProcessor(menu.getInputProcessor());
					state = state.step();
					menu.disable();
					newGame();
				}
			}
			case Pushing: {
				if (pdLately && keycode == Keys.P) {
					state = state.pause();
					pdLately = false;
					return true;
				}
			}
			case Paused: {
				if (!pdLately && keycode == Keys.P) {
					state = state.resume();
					pdLately = false;
					return true;
				}
			}
			
			default: return false;
			}
		}

		@Override
		public boolean keyTyped(char character) {
			switch (state) {
			case Menu: {}
			case Pushing: {}
			case Paused: {}
			
			default: return false;
			}
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}
		
		
		
	}
}
