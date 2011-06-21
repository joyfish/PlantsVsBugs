package org.anddev.andengine.pvb.card;

import java.util.LinkedList;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.extra.Enviroment;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.pvb.plant.Plant;
import org.anddev.andengine.pvb.singleton.GameData;
import org.anddev.andengine.util.modifier.IModifier;

import android.util.Log;

public abstract class Card extends Sprite {

	private Rectangle mBlack;

	protected float mRecharge = 10f;
	protected boolean mReady = false;
	protected int mPrice = 2;

	public Card(final float pX, float pY, final TextureRegion pTextureRegion) {
		super(pX, pY, GameData.getInstance().mCard);
		Sprite image = new Sprite(4, 4, pTextureRegion);
		attachChild(image);
	}

	public void onAttached() {
		ChangeableText value = new ChangeableText(0, 0, GameData.getInstance().mFont2, Integer.toString(this.mPrice), 3);
		value.setPosition(31 - value.getWidthScaled() / 2 , 66 - value.getHeightScaled() / 2);
		attachChild(value);
		
		this.mBlack = new Rectangle(1, 1, this.mBaseWidth - 2, this.mBaseHeight - 2);
		this.mBlack.setColor(0f, 0f, 0f);
		this.mBlack.setAlpha(0.5f);
		this.mBlack.setScaleCenter(0, 0);
		attachChild(this.mBlack);
		
		Enviroment.getInstance().getScene().registerTouchArea(this);
		
		startRecharge();
	}
	
	public void startRecharge() {
		this.mReady = false;
		this.mBlack.setScaleY(1f);

		this.mBlack.registerEntityModifier(new ScaleModifier(this.mRecharge, 1f, 1f, 1f, 0f, new IEntityModifierListener() {
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				Card.this.mReady = true;
			}

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				
			}
		}));
	}

	public boolean isReady() {
		return this.mReady;
	}

	public int getPrice() {
		return this.mPrice;
	}

	public Card makeSelect() {
		Card sel = null;
		LinkedList<Card> cards = GameData.getInstance().getCards();
		for (int i = 0; i < cards.size(); i++) {
			Card c = cards.get(i);
			if (c == this) {
				if (getChildCount() == 3) {
					Log.i("Game", Integer.toString(this.getChildCount()));
					Sprite s = new Sprite(0, 0, GameData.getInstance().mCardSelected);
					attachChild(s);
					sel = c;
				} else
					c.setUnselect();
			} else
				c.setUnselect();
		}
		return sel;
	}

	public void setUnselect() {
		if (getChildCount() > 3)
			Enviroment.getInstance().safeDetachEntity(getLastChild());
			//detachChild(getLastChild());
	}

	public Plant getPlant() {
		return null;
	}
	
}
