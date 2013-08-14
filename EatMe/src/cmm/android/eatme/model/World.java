package cmm.android.eatme.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class World {
	private static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
	public static final int EASY = 0, MEDIUM = 1, HARD = 2;
	private Pool<Ghost> ghostPool;
	private ArrayList<Ghost> ghosts;
	private Vector2 treat;
	private int level;

	public World(int level) {
		this.level = level;

		ghosts = new ArrayList<Ghost>();
		ghostPool = new Pool<Ghost>() {
			/**
			 * Appelée à chaque fois que vous voulez créer un nouveau Ghost
			 * Place le Ghost au hasard sur un bord de l'écran
			 */
			@Override
			protected Ghost newObject() {
				Ghost ghost = new Ghost();
				return ghost;
			}
		};

		// Place la friandise au mileu de l'écran
		treat = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}

	/**
	 * Déplace chaque fantôme un peu plus près de la friandise
	 */
	public void moveGhosts() {
		Vector2 pos;
		for(Ghost ghost: ghosts) {
			pos = ghost.getPosition();

			if(pos.x > treat.x)
				pos.x --;
			else if (pos.x < treat.x)
				pos.x ++;

			if(pos.y > treat.y)
				pos.y --;
			else if (pos.y < treat.y)
				pos.y ++;
		}
	}

	/**
	 * "crée" un nouveau Fantôme grâce au Pool et l'ajoute automatiquement parmi les fantôme de ce Monde
	 * @return Le nouveau fantôme créé
	 */
	public Ghost obtainGhost(float screenX, float screenY, float width, float height) {
		Ghost obtainedGhost = ghostPool.obtain();

		// on calcule les coordonnées du nouveau fantôme
		float x = 0, y = 0;
		int side = (int) (Math.random() * 4);
		switch(side) {
		case UP:
			x = (float) (Math.random() * (width - screenX) + screenX);
			y = screenY;
			break;
		case DOWN:
			x = (float) (Math.random() * (width - screenX) + screenX);
			y = height;
			break;
		case LEFT:
			x = screenX;
			y = (float) (Math.random() * (height - screenY) + screenY);
			break;
		case RIGHT:
			x = width;
			y = (float) (Math.random() * (height - screenY) + screenY);
			break;
		default:
			assert(false) : "Ce côté n'existe pas!";
		}
		obtainedGhost.setPosition(x, y);
		obtainedGhost.setColor((float) Math.random(), (float) Math.random(), (float) Math.random());
		obtainedGhost.setAlive(true);

		ghosts.add(obtainedGhost);
		return obtainedGhost;
	}

	/**
	 * Supprime le fantôme n° ghostIndex, et le libère dans le Pool
	 * @param ghostIndex l'indice du fantôme à supprimer
	 */
	public void freeGhost(int ghostIndex) {
		Ghost rmGhost = ghosts.remove(ghostIndex);
		ghostPool.free(rmGhost);
	}

	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}

	public int getLevel() {
		return level;
	}

	public Vector2 getTreat() {
		return treat;
	}
}
