package com.moonjew.rps;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RockPaperScissors extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	Skin skin;
	Stage stage;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);

//		Begin layout
		final Label resultLabel = new Label("", skin);
		resultLabel.getStyle().font = font;
		resultLabel.setStyle(resultLabel.getStyle());

		final Label cpuLabel = new Label("", skin);
		cpuLabel.setStyle(resultLabel.getStyle());

		root.defaults().space(0);

		Label label = new Label("Rock, paper, or scissors?", skin);
		label.getStyle().font = font;
		label.setFontScale(0.75f);
		label.setStyle(label.getStyle());
		root.add(label).expandY().top().padTop(20);

		root.row();

		Table buttons = new Table();
		buttons.defaults().space(10);
		TextButton test = new TextButton("Rock", skin);
		test.setName("Rock");
		test.getStyle().font = font;
		test.setStyle(test.getStyle());
		test.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				checkWon(actor, resultLabel, cpuLabel);
			}
		});
		buttons.add(test);

		test = new TextButton("Paper", skin);
		test.setName("Paper");
		test.getStyle().font = font;
		test.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				checkWon(actor, resultLabel, cpuLabel);
			}
		});
		buttons.add(test);

		test = new TextButton("Scissors", skin);
		test.setName("Scissors");
		test.getStyle().font = font;
		test.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				checkWon(actor, resultLabel, cpuLabel);
			}
		});
		buttons.add(test);
		root.add(buttons).expandY().top();

		root.row();

		root.add(cpuLabel);

		root.row();

		root.add(resultLabel);
	}

	public void checkWon(Actor actor, Label resultLabel, Label cpuLabel){
		int cpuChoice = (int) Math.round(Math.random()*3);
		switch (cpuChoice){
			case 0:
				cpuLabel.setText("Rock");
				break;
			case 1:
				cpuLabel.setText("Paper");
				break;
			default:
				cpuLabel.setText("Scissors");
		}

		switch (actor.getName()) {
			case "Rock":
				if (cpuChoice == 0) {
					win(resultLabel);
				} else if (cpuChoice == 1) {
					lose(resultLabel);
				} else tie(resultLabel);
				break;
			case "Paper":
				if (cpuChoice == 0) win(resultLabel);
				else if (cpuChoice == 1) tie(resultLabel);
				else lose(resultLabel);
				break;
			case "Scissors":
				if (cpuChoice == 0) lose(resultLabel);
				else if (cpuChoice == 1) win(resultLabel);
				else tie(resultLabel);
				break;
		}
	}

	public void win(Label label){
		label.setText("You win!");
	}
	public void lose(Label label){
		label.setText("You lose! :(");
	}
	public void tie(Label label){
		label.setText("Tie!");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();

		stage.act();
		stage.draw();

	}

	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
