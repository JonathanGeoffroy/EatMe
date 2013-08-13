package cmm.android.eatme.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Ghost implements Poolable {
	private Vector2 position;
	private Color color;
	private boolean alive;

	public Ghost() {
		position = new Vector2();
		color = new Color();
		color.a = 1.0f;
	}

	@Override
	/**
	 * Appelée à chaque fois que vous appelez MemoryManager.free(this)
	 * Met le flag alive à faux pour être sûr que le fantôme n'est plus utilisé
	 */
	public void reset() {
		alive = false;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	/**
	 * Vérifie si le fantôme est dans le cadre délimité par les paramètres
	 * @param screenX l'abscisse du coin inférieur gauche du cadre 
	 * @param screenY l'ordonnée du coin inférieur gauche du cadre
	 * @param screenWidth la longueur du cadre
	 * @param screenHeight la largeur du cadre
	 * @return VRAI si est seulement si le fantôme est dans le cadre
	 */
	public boolean isInScreen(float screenX, float screenY, float screenWidth, float screenHeight) {
		return position.x >= screenX && position.x <= screenWidth && 
				position.y >= screenY && position.y <= screenHeight;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(float r, float g, float b) {
		color.r = r;
		color.g = g;
		color.b = b;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
