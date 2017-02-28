package ve.com.edgaralexanderfr.fz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import ve.com.edgaralexanderfr.game.GameObject;
import ve.com.edgaralexanderfr.game.Input;
import ve.com.edgaralexanderfr.game.Resources;
import ve.com.edgaralexanderfr.util.PropertiesReader;

public class Survivor extends GameObject {
	private Resources resources = null;
	private PropertiesReader c  = null;

	@Override
	public void onTextFormatting (Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(new Color(142, 224, 52, 175));
	}

	@Override
	public void start () {
		this.resources     = this.game.getResources();
		this.c             = this.resources.get("config", PropertiesReader.class);
		this.text          = "Edgar";
		this.spriteTexture = this.resources.get("survivor", Image.class);
		this.setTextOffsetY(-30.0f);
		this.x             = this.c.i("windowWidth")  / 2;
		this.y             = this.c.i("windowHeight") / 2;
		this.zIndex        = 1;
	}

	@Override
	public void update () {
		Input input     = this.game.getInput();
		float deltaTime = this.game.getRenderer().deltaTime();

		if (input.getKey(87)) {
			this.y -= 400.0f * deltaTime;
		}

		if (input.getKey(83)) {
			this.y += 400.0f * deltaTime;
		}

		if (input.getKey(65)) {
			this.x -= 400.0f * deltaTime;
		}

		if (input.getKey(68)) {
			this.x += 400.0f * deltaTime;
		}

		if (input.getMouse(1)) {
			this.x = input.getMouseX();
			this.y = input.getMouseY();
		}

		if (this.x >= this.c.i("windowWidth") - 64 - 16) {
			this.x = this.c.i("windowWidth")  - 64 - 16;
		} else 
		if (this.x <= 64.0f + 16.0f) {
			this.x = 64.0f + 16.0f;
		}

		if (this.y >= this.c.i("windowHeight") - 64 - 16) {
			this.y = this.c.i("windowHeight")  - 64 - 16;
		} else 
		if (this.y <= 64.0f + 16.0f) {
			this.y = 64.0f + 16.0f;
		}
	}
}
