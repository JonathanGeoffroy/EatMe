package cmm.android.eatme.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import cmm.android.eatme.EatMe;
import cmm.android.eatme.model.World;
import cmm.android.eatme.view.actors.WorldActor;
import cmm.android.eatme.view.utils.App;
import cmm.android.eatme.view.utils.StageScreen;

public class Game extends StageScreen {
	private final static String FONT = EatMe.FONTS + "mainMenu_font.fnt";
	private final static String MUSIC = EatMe.SOUNDS + "game_music.mp3";
	private final static String LOOSE = EatMe.SOUNDS + "loose.mp3", APPEARS = EatMe.SOUNDS + "game_appears.mp3";
	public final static String GHOST = EatMe.IMAGES + "ghost.png", TREAT = EatMe.IMAGES + "treat.png";

	private World world;
	private WorldActor worldActor;
	private Music music;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(FONT, BitmapFont.class));
		result.add(new AssetDescriptor(MUSIC, Music.class));
		result.add(new AssetDescriptor(LOOSE, Sound.class));
		result.add(new AssetDescriptor(APPEARS, Sound.class));
		result.add(new AssetDescriptor(TREAT, Texture.class));
		result.add(new AssetDescriptor(GHOST, Texture.class));
		return result;
	}

	@Override
	protected void onEndLoaded() {
		world = EatMe.getWorld();
		worldActor = new WorldActor(world);
		worldActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(worldActor);

		music = (Music) App.getAsset(MUSIC);
		music.play();
		music.setLooping(true);
	}
}
