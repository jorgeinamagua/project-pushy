package com.wintrywind.pushy;

/* Welcome to another enum implementation of gamestate
 * 
 *  features:
 *      cycling through from app conception to end
 *          init -> menu <-> (active <-> paused) => end
 */
public enum PushyState {
	Init, Splash, Menu, Pushing, Paused, End;

	public PushyState step() {
		switch (this) {
		case Splash: { return Menu;}
		case Menu: { return Pushing;}
		default: return this;
		}
		
	}

	public PushyState pause() {
		return Paused;
	}

	public PushyState resume() {
		return Pushing;
	}
}
