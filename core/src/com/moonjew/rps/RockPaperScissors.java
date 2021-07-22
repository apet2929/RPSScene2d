package com.moonjew.rps;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class RockPaperScissors extends ApplicationAdapter {
	BitmapFont font;
	Skin skin;
	Stage playStage;
	Stage menuStage;
	boolean play;
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		play = false;
		menuStage = new Stage(new StretchViewport(640, 400));
		playStage = new Stage(new StretchViewport(640, 400));

		Gdx.input.setInputProcessor(menuStage);
		Table root = new Table();
		root.setFillParent(true);
		playStage.addActor(root);



//		Begin layout
		final Label resultLabel = new Label("", skin);


		resultLabel.getStyle().font = font;
		resultLabel.setStyle(resultLabel.getStyle());

		final Label cpuLabel = new Label("", skin);
		cpuLabel.setStyle(resultLabel.getStyle());

		final Label choiceLabel = new Label("", skin);
		choiceLabel.setStyle(resultLabel.getStyle());

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
				choiceLabel.setText("Rock vs ");
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
				choiceLabel.setText("Paper vs ");
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
				choiceLabel.setText("Scissors vs ");
				checkWon(actor, resultLabel, cpuLabel);
			}
		});
		buttons.add(test);
		root.add(buttons).expandY().top();

		root.row();
		Table table = new Table();
		table.add(choiceLabel);
		table.add(cpuLabel);
		root.add(table).colspan(3);

		root.row();

		root.add(resultLabel).expandX();

		root = new Table();
		root.setFillParent(true);
		menuStage.addActor(root);

		label = new Label("Play", skin);
		label.getStyle().font = font;
		label.setStyle(label.getStyle());

		root.add(label);

		root.row();

		buttons = new Table();
		test = new TextButton("Play", skin);
		test.getStyle().font = font;
		test.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				play = true;
				Gdx.input.setInputProcessor(playStage);
			}
		});
		buttons.add(test).uniform();

		test = new TextButton("Quit", skin);
		test.getStyle().font = font;
		test.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		buttons.add(test).uniform();

		root.add(buttons).growX();


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
	public void resize(int width, int height) {
		super.resize(width, height);
		playStage.getViewport().update(width, height, true);
		menuStage.getViewport().update(width, height, true);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playStage.getViewport().apply();
		menuStage.getViewport().apply();

		if(play) {
			playStage.act();
			playStage.draw();
		}
		else {
			menuStage.act();
			menuStage.draw();
		}

	}

	@Override
	public void dispose () {
		playStage.dispose();
		font.dispose();
		skin.dispose();
	}
}
