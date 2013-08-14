package cmm.android.eatme.view.actors;

import java.util.ArrayList;

import cmm.android.eatme.EatMe;
import cmm.android.eatme.control.SpriteMover;
import cmm.android.eatme.model.Ghost;
import cmm.android.eatme.model.World;
import cmm.android.eatme.view.screens.Game;
import cmm.android.eatme.view.utils.App;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class WorldActor extends Actor {
	private Game game;
	private World world;
	private TextureRegion ghostRegion;
	private SpriteMover listener;
	private Pool<Sprite> spritesPool;
	private ArrayList<Ghost> ghosts;
	private ArrayList<Sprite> ghostsSprite;
	private Sprite treatSprite;
	private float appearsTimer, moveTimer;
	private final float timeBeforeGhostAppears, timerBeforeGhostMove;
	private boolean playing;

	public WorldActor(Game game) {
		this.game = game;
		this.world = EatMe.getWorld();

		Vector2 treat = world.getTreat();
		Texture treatText = (Texture) App.getAsset(Game.TREAT);
		treatSprite = new Sprite(treatText);
		treatSprite.setPosition(treat.x, treat.y);

		Texture ghostText = (Texture) App.getAsset(Game.GHOST); 
		ghostRegion = new TextureRegion(ghostText, 0, 0, 154, 163);
		spritesPool = Pools.get(Sprite.class);
		ghosts = world.getGhosts();
		ghostsSprite = new ArrayList<Sprite>();

		timeBeforeGhostAppears = 1.0f - 0.3f * world.getLevel();
		timerBeforeGhostMove = 0.05f - 0.02f * world.getLevel();

		listener = new SpriteMover(this);
	}

	private boolean ghostEatTreat() {
		Rectangle treatRect = treatSprite.getBoundingRectangle();
		for(Sprite ghost: ghostsSprite) {
			if(ghost.getBoundingRectangle().overlaps(treatRect)) {
				return true;
			}
		}
		return false;
	}

	private boolean treatIsInScreen() {
		return getX() < treatSprite.getX() && getX() + getWidth() > treatSprite.getX() + treatSprite.getWidth()
				&& getY() < treatSprite.getY() && getY() + getHeight() > treatSprite.getY() + treatSprite.getHeight();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if(playing && !world.isLoose()) {
			// On utilise le timer pour faire apparaître de nouveaux fantôme 
			appearsTimer += delta;
			if (appearsTimer >= timeBeforeGhostAppears) {
				obtainGhostSprite();
				appearsTimer = 0;
				assert(ghostsSprite.size() == ghosts.size());
			}

			moveTimer += delta;
			if(moveTimer >= timerBeforeGhostMove) {
				world.moveGhosts();
				moveTimer = 0;
			}

			float x = getX(), y = getY();
			float width = getWidth(), height = getHeight();

			Vector2 treat = world.getTreat();
			treatSprite.setPosition(treat.x, treat.y);

			Ghost ghost;
			Sprite ghostSprite;
			Vector2 ghostPos;

			for(int i = ghosts.size() - 1; i >= 0; i--) {
				ghost = ghosts.get(i);
				if(ghost.isInScreen(x, y, width, height)) {
					// on affiche les fantômes qui restent dans l'écran au bon endroit
					ghostPos = ghost.getPosition();
					ghostSprite = ghostsSprite.get(i);
					ghostSprite.setPosition(ghostPos.x, ghostPos.y);
				}
				else {
					// On libère les fantômes qui sont déjà sorti de l'écran
					freeGhostSprite(i);
				}
			}

			boolean loose = ghostEatTreat() || !treatIsInScreen();
			if(loose) {
				game.loose();
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		treatSprite.draw(batch);
		for(Sprite ghostSprite : ghostsSprite) {
			ghostSprite.draw(batch);
		}
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		float widthByTen = width / 10, heightByTen = height / 10;

		Vector2 treat = world.getTreat();
		treat.x = getX() + (width - widthByTen) / 2;
		treat.y = getY() + (height - heightByTen) / 2; 
		treatSprite.setBounds(treat.x, treat.y, widthByTen, heightByTen);

		for(Sprite sprite: ghostsSprite) {
			sprite.setSize(width, height);
		}
	}

	/**
	 * Crée un nouveau Fantôme et ajoute un Sprite pour afficher ce dernier
	 * @return le Sprite créé
	 */
	public Sprite obtainGhostSprite() {
		float ghostWidth = getWidth() / 10, ghostHeight = getHeight() / 10;

		Ghost obtainedGhost = world.obtainGhost(getX(), getY(), getWidth() - ghostWidth, getHeight() - ghostHeight);
		Vector2 obtainedPos = obtainedGhost.getPosition();

		Sprite obtainedSprite = spritesPool.obtain();
		obtainedSprite.setBounds(obtainedPos.x, obtainedPos.y, ghostWidth, ghostHeight);
		obtainedSprite.setRegion(ghostRegion);
		obtainedSprite.setColor(obtainedGhost.getColor());
		ghostsSprite.add(obtainedSprite);
		return obtainedSprite;
	}

	/**
	 * Libère le Sprite pointé par l'indice, et libère également le Fantôme associé
	 * @param index l'indice du fantôme à libérer
	 */
	public void freeGhostSprite(int index) {
		world.freeGhost(index);
		Sprite ghostSprite = ghostsSprite.remove(index);
		spritesPool.free(ghostSprite);
	}


	/**
	 * Supprime les fantômes et remet le bonbon au milieu
	 */
	public void reset() {
		ghostsSprite.clear();

		float width = getWidth(), height = getHeight();
		float widthByTen = width / 10, heightByTen = height / 10;
		Vector2 treat = world.getTreat();
		treat.x = getX() + (width - widthByTen) / 2;
		treat.y = getY() + (height - heightByTen) / 2; 
		treatSprite.setBounds(treat.x, treat.y, widthByTen, heightByTen);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public ArrayList<Sprite> getGhostsSprite() {
		return ghostsSprite;
	}

	public void setGhostsSprite(ArrayList<Sprite> ghostsSprite) {
		this.ghostsSprite = ghostsSprite;
	}

	public Sprite getTreatSprite() {
		return treatSprite;
	}

	public void setTreatSprite(Sprite treatSprite) {
		this.treatSprite = treatSprite;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
		if(playing) {
			addListener(listener);
		}
		else {
			removeListener(listener);
		}
	}
}
