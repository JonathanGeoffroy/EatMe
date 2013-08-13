package cmm.android.eatme.view.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class WallpaperActor extends Actor {
	private Texture wallpaper;

	public WallpaperActor(Texture wallpaper) {
		setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.wallpaper = wallpaper; 
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(wallpaper, getX(), getY(), getWidth(), getHeight());
	}
}
