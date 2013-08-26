package cmm.android.eatme.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import cmm.android.eatme.EatMe;
import cmm.android.eatme.model.World;
import cmm.android.eatme.view.actors.LooseActor;
import cmm.android.eatme.view.actors.PauseActor;
import cmm.android.eatme.view.actors.WorldActor;
import cmm.android.eatme.view.utils.App;
import cmm.android.eatme.view.utils.StageScreen;

public class Game extends StageScreen {
	public final static String FONT = EatMe.FONTS + "mainMenu_font.fnt";
	private final static String MUSIC = EatMe.SOUNDS + "game_music.mp3";
	public final static String LOOSE = EatMe.SOUNDS + "loose.mp3", APPEARS = EatMe.SOUNDS + "game_appears.mp3";
	public final static String GHOST = EatMe.IMAGES + "ghost.png", TREAT = EatMe.IMAGES + "treat.png";

	private World world;
	private WorldActor worldActor;
	private PauseActor pauseActor;
	private LooseActor looseActor;
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
		result.add(new AssetDescriptor(MainMenu.BACKGROUND, Texture.class));
		return result;
	}

	@Override
	protected void onEndLoaded() {
		stage.clear();
		world = EatMe.getWorld();
		worldActor = new WorldActor(this);
		worldActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		pauseActor = new PauseActor(this);
		pauseActor.setVisible(false);
		pauseActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		looseActor = new LooseActor(this);
		looseActor.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		looseActor.setVisible(false);

		stage.addActor(worldActor);
		stage.addActor(pauseActor);
		stage.addActor(looseActor);

		music = (Music) App.getAsset(MUSIC);
		music.play();
		music.setLooping(true);

		worldActor.setPlaying(true);
	}

	/**
	 * Affiche le menu pause et met pause au jeu
	 */
	public void enablePauseActor() {
		worldActor.setPlaying(false);
		pauseActor.setVisible(true);
	}

	/**
	 * Cache le menu pause et relance le jeu
	 */
	public void disableMenus() {
		pauseActor.setVisible(false);
		looseActor.setVisible(false);
		worldActor.setPlaying(true);

	}

	/**
	 * affiche le menu s'il est caché, le cache sinon
	 */
	public void switchPauseActor() {
		if(pauseActor.isVisible()) {
			disableMenus();
		}
		else {
			enablePauseActor();
		}
	}

	public void reset() {
		worldActor.reset();
		world.reset();
		disableMenus();
	}

	@Override
	public void draw(float delta) {
		if(!looseActor.isVisible() && Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			enablePauseActor();
		}
		super.draw(delta);
	}

	public void loose() {
		world.setLoose(true);
		Sound looseSound = (Sound) App.getAsset(Game.LOOSE);
		looseSound.play();
		looseActor.setVisible(true);
	}
}
