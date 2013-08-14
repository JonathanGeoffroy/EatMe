package cmm.android.eatme;

import com.badlogic.gdx.Gdx;

import cmm.android.eatme.model.World;
import cmm.android.eatme.view.screens.Game;
import cmm.android.eatme.view.screens.MainMenu;
import cmm.android.eatme.view.utils.App;

public class EatMe extends App {
	public static final String IMAGES = "data/img/";
	public static final String SOUNDS = "data/sounds/";
	public static final String FONTS = "data/fonts/";
	public static final int MAIN_MENU = 0, GAME = 1;
	private static World world;

	@Override
	public void create() {
		super.create();
		screens.add(new MainMenu());
		screens.add(new Game());
		setScreen(MAIN_MENU);
		Gdx.input.setCatchBackKey(true);
	}
	
	public static World getWorld() {
		return world;
	}

	public static void setWorld(World world) {
		EatMe.world = world;
	}
}