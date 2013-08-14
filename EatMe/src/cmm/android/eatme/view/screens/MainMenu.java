package cmm.android.eatme.view.screens;

import java.util.ArrayList;

import cmm.android.eatme.model.World;
import cmm.android.eatme.view.utils.WallpaperActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import cmm.android.eatme.EatMe;
import cmm.android.eatme.view.utils.App;
import cmm.android.eatme.view.utils.StageScreen;

public class MainMenu extends StageScreen {
	public final static String WALLPAPER = EatMe.IMAGES + "mainMenu_wallpaper.jpg", BACKGROUND = EatMe.IMAGES + "mainMenu_background.png";
	private final static String FONT = EatMe.FONTS + "mainMenu_font.fnt";
	private final static String MUSIC = EatMe.SOUNDS + "effect.mp3";
	private final static String CHOOSEN_SOUND = EatMe.SOUNDS + "loose.mp3";
	private final static int NB_MAIN_MENUS = 3, NB_LEVEL_MENUS = 4;

	private Music music;
	private Table menu;
	private TextButtonStyle style;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayList<AssetDescriptor<Object>> getAssetDescriptors() {
		ArrayList<AssetDescriptor<Object>> result = new ArrayList<AssetDescriptor<Object>>();
		result.add(new AssetDescriptor(WALLPAPER, Texture.class));
		result.add(new AssetDescriptor(BACKGROUND, Texture.class));
		result.add(new AssetDescriptor(FONT, BitmapFont.class));
		result.add(new AssetDescriptor(MUSIC, Music.class));
		result.add(new AssetDescriptor(CHOOSEN_SOUND, Sound.class));
		return result;
	}

	@Override
	public void resume() {
		super.resume();
		menu.clear();
		createMainMenu();
	}
	
	@Override
	protected void onEndLoaded() {
		stage.clear();
		music = (Music) App.getAsset(MUSIC);
		music.play();
		music.setLooping(true);

		menu = new Table();
		menu.setSize(Gdx.graphics.getWidth() * 2 / 3, Gdx.graphics.getHeight() * 2 / 3);

		// On récupère les Textures dont on a besoin
		Texture background = (Texture) App.getAsset(BACKGROUND);
		TextureRegion backgroundRegion = new TextureRegion(background, 256, 42);
		BitmapFont font = (BitmapFont) App.getAsset(FONT);
		Texture wallpaper = (Texture) App.getAsset(WALLPAPER);

		// On crée un nouveau style de bouton
		style = new TextButtonStyle();
		style.up = new TextureRegionDrawable(backgroundRegion);
		style.font = font;

		createMainMenu();

		stage.addActor(new WallpaperActor(new TextureRegion(wallpaper, 1024, 640)));
		stage.addActor(menu);
	}

	/**
	 * Crée le menu principal avec tous ses boutons
	 */
	private void createMainMenu() {
		float widthMenu = Gdx.graphics.getWidth() * 2 / 3;
		float heightMenu = (Gdx.graphics.getHeight() / NB_MAIN_MENUS) * 2 / 3;

		// On crée les boutons 1 par 1, en ajoutant un Listener pour chaque
		TextButton level = new TextButton("Jouer", style);
		level.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				menu.clear();
				createLevelMenu();
			}
		});
		menu.add(level).width(widthMenu).height(heightMenu);
		menu.row();

		TextButton options = new TextButton("OPTIONS", style);
		options.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("options");
				haveChoosen();
			}
		});
		menu.add(options).width(widthMenu).height(heightMenu);;
		menu.row();

		TextButton exit = new TextButton("QUITTER", style);
		exit.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				haveChoosen();
				Gdx.app.exit();
			}
		});
		menu.add(exit).width(widthMenu).height(heightMenu);;
		menu.row();

		menu.pack();
		menu.setPosition(Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight() / 2);
		addTableEffects();
	}

	/**
	 * Crée le menu premettant de choisir le niveau de difficulté
	 */
	private void createLevelMenu() {
		float widthMenu = Gdx.graphics.getWidth() * 2 / 3;
		float heightMenu = (Gdx.graphics.getHeight() / NB_LEVEL_MENUS) * 2 / 3;

		// On crée les boutons 1 par 1, en ajoutant un Listener pour chaque
		TextButton easy = new TextButton("Facile", style);
		easy.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				EatMe.setWorld(new World(World.EASY));
				haveChoosen();
				app.setScreen(EatMe.GAME);
			}
		});
		menu.add(easy).width(widthMenu).height(heightMenu);
		menu.row();

		TextButton medium = new TextButton("Moyen", style);
		medium.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				EatMe.setWorld(new World(World.MEDIUM));
				haveChoosen();
				app.setScreen(EatMe.GAME);
			}
		});
		menu.add(medium).width(widthMenu).height(heightMenu);;
		menu.row();

		TextButton hard = new TextButton("Difficile", style);
		hard.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				EatMe.setWorld(new World(World.HARD));
				haveChoosen();
				app.setScreen(EatMe.GAME);
			}
		});
		menu.add(hard).width(widthMenu).height(heightMenu);;
		menu.row();

		TextButton returnButton = new TextButton("Retour", style);
		returnButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				menu.clear();
				createMainMenu();
			}
		});
		menu.add(returnButton).width(widthMenu).height(heightMenu);;
		menu.row();

		menu.pack();
		menu.setPosition(Gdx.graphics.getWidth() / 2 - menu.getWidth() / 2, Gdx.graphics.getHeight() / 2 - menu.getHeight() / 2);
		addTableEffects();
	}

	/**
	 * AJoute un effet sur les boutons de la table:
	 * Chaque bouton vient par de la gauche ou de la droite
	 */
	private void addTableEffects() {
		int switcher = 0;
		float x, y;
		for(Actor actor : menu.getChildren()) {
			actor.addAction(Actions.moveTo(actor.getX(), actor.getY(), 1.0f, Interpolation.swing));

			if(switcher % 2 == 0) {
				x = - Gdx.graphics.getWidth() / 2;
			}
			else {
				x = Gdx.graphics.getWidth() / 2;
			}
			y = actor.getY();
			actor.setPosition(x, y);

			switcher++;
		}
	}

	/**
	 * Supprime la musique et joue un petit son.
	 */
	private void haveChoosen() {
		music.stop();
		Sound sound = (Sound) App.getAsset(CHOOSEN_SOUND);
		sound.play();
	}
}
