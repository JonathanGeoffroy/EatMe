package cmm.android.eatme.view.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;

public abstract class HelpScreen implements Screen {
	protected static App app;
	private boolean hasInit;
	protected static float width, height;

	@Override
	public void show() {
		App.loadAssets(getAssetDescriptors());
	}

	@Override
	public void resume() {
		App.loadAssets(getAssetDescriptors());		
	}

	@Override
	public void dispose() {
		hasInit = false;
		App.clearAssets();
	}

	public abstract ArrayList<AssetDescriptor<Object>> getAssetDescriptors();
	protected abstract void onEndLoaded();

	public static void initialize(App app) {
		HelpScreen.app = app;
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
	}

	@Override
	public void render(float delta) {
		if(App.hasLoaded()) {
			if(!hasInit) {
				hasInit = true;
				onEndLoaded();
			}
			draw(delta);
		}
		else {
			drawLoader();
		}
	}

	@Override
	public void hide() {
		hasInit = false;
		App.clearAssets();
	}

	@Override
	public void pause() {
		hasInit = false;
		App.clearAssets();
	}
	
	private void drawLoader() {
		// TODO Auto-generated method stub
		
	}

	public abstract void draw(float delta);
	
	public static App getApp() {
		return app;
	}

	public static void setApp(App app) {
		HelpScreen.app = app;
	}

}
