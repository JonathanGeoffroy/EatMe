package cmm.android.eatme.view.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;

public class App extends Game {
	private static AssetManager manager;
	protected ArrayList<HelpScreen> screens;

	@Override
	public void create() {
		HelpScreen.initialize(this);
		manager = new AssetManager();
		screens = new ArrayList<HelpScreen>();
	}

	public void setScreen(int screenType) {
		assert(screenType >= 0 && screenType < screens.size());
		setScreen(screens.get(screenType));
	}

	public static void loadAssets(ArrayList<AssetDescriptor<Object>> assetDesc) {
		for(AssetDescriptor<Object> ad : assetDesc) {
			manager.load(ad);
		}
	}

	public static void clearAssets() {
		manager.clear();
		System.out.println("cleared: " + manager.getLoadedAssets());
	}

	public static boolean hasLoaded() {
		return manager.update();
	}

	public static Object getAsset(String name) {
		return manager.get(name);
	}
}
