package cmm.android.eatme.view.actors;

import cmm.android.eatme.view.screens.Game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseActor extends Menu {

	public PauseActor(Game game) {
		super(game);
		addContinueButton();
		addRestartButton();
		addMainMenuButton();
	}
	
	private void addContinueButton() {
		TextButton continueButton = new TextButton("Continuer", buttonStyle);
		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				PauseActor.this.game.disableMenus();
			}
		});
		add(continueButton);
		row();
	}
}
