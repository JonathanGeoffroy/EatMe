package cmm.android.eatme.view.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class TableActor extends Actor {
	protected Table table;

	public TableActor() {
		table = new Table();
	}


	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		table.draw(batch, parentAlpha);
	}


	/**
	 * load table for the first time on the current screen
	 */
	public abstract void loadTable();

	/**
	 * compute the table again when there is a change on the current screen
	 */
	public void reloadTable() {
		table.clear();
		loadTable();
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		table.setVisible(visible);
	}


	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		table.setBounds(x, y, width, height);
	}
}
