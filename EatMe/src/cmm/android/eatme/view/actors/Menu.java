package cmm.android.eatme.view.actors;

import cmm.android.eatme.EatMe;
import cmm.android.eatme.view.screens.Game;
import cmm.android.eatme.view.screens.MainMenu;
import cmm.android.eatme.view.utils.App;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Menu extends Table {
	protected Game game;
	protected TextButtonStyle buttonStyle;

	public Menu(Game game) {
		super();
		this.game = game;

		Texture background = (Texture) App.getAsset(MainMenu.BACKGROUND);
		TextureRegion backgroundRegion = new TextureRegion(background, 256, 42);
		BitmapFont font = (BitmapFont) App.getAsset(Game.FONT);

		// On crée un nouveau style de bouton
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(backgroundRegion);
		buttonStyle.font = font;
	}

	protected void addRestartButton() {
		TextButton restartButton = new TextButton("Recommencer", buttonStyle);
		restartButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Menu.this.game.reset();
			}
		});
		add(restartButton);
		row();
	}

	protected void addMainMenuButton() {
		TextButton mainMenuButton = new TextButton("Menu Principal", buttonStyle);
		mainMenuButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				Game.getApp().setScreen(EatMe.MAIN_MENU);
			}
		});
		add(mainMenuButton);
		row();
	}
}
