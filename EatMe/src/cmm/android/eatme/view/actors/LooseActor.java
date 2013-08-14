package cmm.android.eatme.view.actors;

import cmm.android.eatme.view.screens.Game;
import cmm.android.eatme.view.utils.App;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class LooseActor extends Menu {
	private LabelStyle labelStyle;
	
	public LooseActor(Game game) {
		super(game);
		labelStyle = new LabelStyle((BitmapFont) App.getAsset(Game.FONT), Color.WHITE);
		add(new Label("Vous avez Perdu !", labelStyle));
		row();
		addRestartButton();
		row();
		addMainMenuButton();
		row();
	}
}
