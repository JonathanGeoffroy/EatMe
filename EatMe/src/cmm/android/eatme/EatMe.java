package cmm.android.eatme;

import cmm.android.eatme.view.screens.MainMenu;
import cmm.android.eatme.view.utils.App;

public class EatMe extends App {
	public static final String IMAGES = "data/img/";
	public static final String SOUNDS = "data/sounds/";
	public static final String FONTS = "data/fonts/";
	public static final int MAIN_MENU = 0, GAME = 1;
	
	@Override
	public void create() {
		super.create();
		screens.add(new MainMenu());
		setScreen(MAIN_MENU);
	}
}