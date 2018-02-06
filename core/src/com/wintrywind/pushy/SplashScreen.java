package com.wintrywind.pushy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SplashScreen extends ScreenAdapter implements InputProcessor {
	
	private ShapeRenderer sr;
	private Logo logo;
	private SpriteBatch batch;
	private ScreenViewport view;
	
	private final int SPLASH_TIME = 2;
	

	private boolean splashed = false;
	private boolean canDrag = false;
	
	public SplashScreen(SpriteBatch b, ScreenViewport v) {
		batch = b;
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(b.getProjectionMatrix());

		logo = new Logo();
		
		//initialize an 8 second timer for splash screen
		Timer tmr = new Timer();
		tmr.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				splashed = true;
				tmr.clear();
				System.out.println("long have we waited"); //SYSTEM.OUT
			}
		}, SPLASH_TIME);
		
		view = v;

	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		logo.render(batch, sr, delta);
		
	}
	
	@Override
	public void resize(int x, int y) {
		view.update(x, y);
	}

	@Override
	public void dispose () {
		sr.dispose();
		logo.dispose();
	}
	
	public boolean splashed() {
		return splashed;
	}
	
	//splash input processing
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT && !canDrag) {
			System.out.println(screenX + ", " + screenY);
			Vector3 v3 = new Vector3(screenX, screenY, 0);
			view.unproject(v3);
			System.out.println(v3.toString());
			if (logo.contains(v3)) {
				System.out.println("commence the drag");
				canDrag = true;
				logo.dragStart(v3);
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (canDrag) {
			Vector3 v3 = view.unproject(new Vector3(screenX, screenY,0));
			canDrag = false;
			logo.dragTo(v3, screenX, screenY);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (canDrag) {
			Vector3 v = new Vector3(screenX, screenY, 0);
			view.unproject(v);
			logo.dragTo(v, screenX, screenY);
			return true;
		}
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
	
	private class Logo {
		private Rectangle logoBox;
		private Vector2 textCenter;
		private Vector2 lastLoc;
		private final CharSequence text = "Wintry Wind proudly resents:";
		private BitmapFont font;
		
		protected Logo() {
			font = new BitmapFont();
			font.getData().setScale(2);
			textCenter = new Vector2(-200,-100); 
			lastLoc = textCenter;
			logoBox = new Rectangle(-200, -100, 400, 200);
			
		}
		
		@SuppressWarnings("unused")
		private boolean within(Vector2 vec) {
			Vector2 v = view.project(vec);
			return ((v.x >= 0) && (v.y >= 0) && (v.y <= view.getScreenHeight() - logoBox.height) && (v.x <= view.getScreenWidth() - logoBox.width));
		}

		public void dragTo(Vector3 vec, int origX, int origY) {
			// wait copies of all these points before f'ing with them
			Vector2 v = new Vector2(vec.x, vec.y);
			
			Vector2 potLoc = textCenter.sub(lastLoc.sub(v));
			float xedge = view.getWorldWidth() / 2;
			if (potLoc.x < -xedge) {
				potLoc.x = - xedge;
			} else if (potLoc.x > (xedge - logoBox.width)) {
				potLoc.x = (xedge - logoBox.width);
			}
			float yedge = view.getWorldHeight() / 2;
			if (potLoc.y < -yedge) {
				potLoc.y = -yedge;
			} else if (potLoc.y > yedge - logoBox.height) {
				potLoc.y = yedge - logoBox.height;
			}
			
			//final calculation
			textCenter = potLoc;
			logoBox.setPosition(textCenter);
			lastLoc = v;
			
			/*Vector2 ll = lastLoc.cpy();
			Vector2 tC = textCenter.cpy();
			Vector2 disp = ll.sub(v);
			
			// to prevent rect from going off sides we edit displacement vector before sending it through to final calc
			Vector2 potLoc = tC.sub(disp); 
			 * */
		}

		public void dragStart(Vector3 vec) {
			lastLoc = new Vector2(vec.x, vec.y);
		}

		@SuppressWarnings("unused") //DEPRECATED - will be using Vector3 and Vector2 wherever possible
		public boolean contains(int x, int y) {
			Vector3 v = new Vector3(x,y,0);
			view.unproject(v);
			return logoBox.contains(x, y);
		}
		
		public boolean contains(Vector3 vec) {
			return logoBox.contains(vec.x, vec.y);
		}
		
		// THe following are methods for Screen workings

		public void render(SpriteBatch b, ShapeRenderer sr, float delta) {
			sr.begin(ShapeType.Filled);
			sr.setColor(0,1,0,0);
			sr.rect(logoBox.x, logoBox.y, logoBox.width, logoBox.height);
			sr.end();
			b.begin();
			font.draw(batch, text, logoBox.x, logoBox.y + (logoBox.height / 2), logoBox.width, Align.center, true);
			b.end();

		}
		
		public void dispose() {
			font.dispose();
		}
	}

}
