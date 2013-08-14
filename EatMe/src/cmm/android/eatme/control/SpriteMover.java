package cmm.android.eatme.control;

import java.util.ArrayList;

import cmm.android.eatme.view.actors.WorldActor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class SpriteMover extends ActorGestureListener {
	private WorldActor worldActor;
	private float x, y;

	public SpriteMover(WorldActor worldActor) {
		this.worldActor = worldActor;
	}

	@Override
	public void fling(InputEvent event, float velocityX, float velocityY,
			int button) {
		System.out.println("fliyng : " + velocityX + " " + velocityY);
		super.fling(event, velocityX, velocityY, button);
		Sprite treatSprite = worldActor.getTreatSprite();
		Vector2 pos;
		if(treatSprite.getBoundingRectangle().contains(x, y)) {
			pos = worldActor.getWorld().getTreat();
			pos.x += (velocityX / 10);
			pos.y += (velocityY / 10);
		}
		else {
			ArrayList<Sprite> ghostSprite = worldActor.getGhostsSprite();
			Sprite sprite;
			for(int i = 0, size = ghostSprite.size(); i < size; i++) {
				sprite = ghostSprite.get(i);
				if(sprite.getBoundingRectangle().contains(x, y)) {
					pos = worldActor.getWorld().getGhosts().get(i).getPosition();
					pos.x += (velocityX / 10);
					pos.y += (velocityY / 10);
				}
			}
		}

		System.out.println("flying!");
	}

	@Override
	public void touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		super.touchDown(event, x, y, pointer, button);
		SpriteMover.this.x = x;
		SpriteMover.this.y = y;
		System.out.println("touchedDown / " + x + " " + y);
	}
}
