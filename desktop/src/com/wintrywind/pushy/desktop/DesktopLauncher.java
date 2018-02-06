package com.wintrywind.pushy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wintrywind.pushy.PushyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "def not pushy penguins";
		config.useGL30 = false;
		config.useHDPI = true;
		config.height = 640;
		config.width = 960;
		new LwjglApplication(new PushyGame(), config);
	}
}
