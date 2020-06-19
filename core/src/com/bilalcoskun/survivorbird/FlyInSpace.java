package com.bilalcoskun.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class FlyInSpace extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture spaceShip;
	Texture foe1;
	Texture foe2;
	Texture foe3;
	float spaceShipX = 0;
	float spaceShipY = 0;
	int gameState = 0;
	float velocity = 0;
	float trouble = 0.2f;
	float enemyVelocity = 15;
	Random random;
	int score=0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle spaceShipCircle;

	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffSet = new float[numberOfEnemies];
	float[]	enemyOffSet2 = new float[numberOfEnemies];
	float[] enemyOffSet3 = new float[numberOfEnemies];

	float distance = 0;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("background.jpg");
		spaceShip = new Texture("spaceShip.png");
		foe1 = new Texture("foe.png");
		foe2 = new Texture("foe2.png");
		foe3 = new Texture("foe3.png");

		distance = Gdx.graphics.getWidth()/2.0f;
		random = new Random();

		spaceShipX = Gdx.graphics.getWidth() / 2.0f-spaceShip.getHeight() / 2.0f;
		spaceShipY = Gdx.graphics.getHeight() / 3.0f;

		shapeRenderer = new ShapeRenderer();

		spaceShipCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2=new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(4);

		for (int i = 0;i<numberOfEnemies;i++){


			enemyOffSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

			enemyX[i]=Gdx.graphics.getWidth()-foe1.getWidth()/2.0f + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState==1){

			if (spaceShipY >Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 15.0f){

				spaceShipY=Gdx.graphics.getHeight()-Gdx.graphics.getHeight() / 15.0f;

			}

			if (enemyX[scoredEnemy]<Gdx.graphics.getWidth() / 2.0f-spaceShip.getHeight() / 2.0f){
				score++;
				if (scoredEnemy<numberOfEnemies-1){
					scoredEnemy++;
				}else {
					scoredEnemy=0;
				}
			}

			if (Gdx.input.justTouched()){
				velocity = -7;
			}

			for (int i = 0 ; i<numberOfEnemies;i++){
				if (enemyX[i]< Gdx.graphics.getWidth() / 15.0f){
					enemyX[i] = enemyX[i]+numberOfEnemies*distance;

					enemyOffSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

				}else{
					enemyX[i] = enemyX[i]-enemyVelocity;
				}

				batch.draw(foe1,enemyX[i],Gdx.graphics.getHeight()/2.0f+enemyOffSet[i],Gdx.graphics.getWidth() / 15.0f,Gdx.graphics.getHeight() / 10.0f);
				batch.draw(foe2,enemyX[i],Gdx.graphics.getHeight()/2.0f+enemyOffSet2[i],Gdx.graphics.getWidth() / 15.0f,Gdx.graphics.getHeight() / 10.0f);
				batch.draw(foe3,enemyX[i],Gdx.graphics.getHeight()/2.0f+enemyOffSet3[i],Gdx.graphics.getWidth() / 15.0f,Gdx.graphics.getHeight() / 10.0f);

			    enemyCircles[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth() / 30.0f,Gdx.graphics.getHeight()/2.0f+enemyOffSet[i]+Gdx.graphics.getHeight() / 20.0f,Gdx.graphics.getWidth() / 30.0f);
                enemyCircles2[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth() / 30.0f,Gdx.graphics.getHeight()/2.0f+enemyOffSet2[i]+Gdx.graphics.getHeight() / 20.0f,Gdx.graphics.getWidth() / 30.0f);
                enemyCircles3[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth() / 30.0f,Gdx.graphics.getHeight()/2.0f+enemyOffSet3[i]+Gdx.graphics.getHeight() / 20.0f,Gdx.graphics.getWidth() / 30.0f);

            }




			if (spaceShipY>0){
				velocity= velocity+trouble;
				spaceShipY = spaceShipY-velocity;
			}else{
				gameState=2;
			}


		}else if (gameState==0){
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if (gameState==2){

			font2.draw(batch,"Game Over! Tap to play again.",Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()){
				gameState = 1;
				spaceShipY = Gdx.graphics.getHeight() / 3.0f;
				for (int i = 0;i<numberOfEnemies;i++){


					enemyOffSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

					enemyX[i]=Gdx.graphics.getWidth()-foe1.getWidth()/2.0f + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}
				velocity = 0;
				scoredEnemy=0;
				score=0;
			}
		}


		batch.draw(spaceShip,spaceShipX,spaceShipY, Gdx.graphics.getWidth() / 20.0f,Gdx.graphics.getHeight() / 15.0f);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();

		spaceShipCircle.set(spaceShipX+Gdx.graphics.getWidth() / 40.0f,spaceShipY+Gdx.graphics.getHeight() / 30.0f,Gdx.graphics.getWidth() / 40.0f);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);


		for (int i = 0 ; i<numberOfEnemies;i++){
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30.0f,Gdx.graphics.getHeight()/2.0f+enemyOffSet[i]+Gdx.graphics.getHeight() / 20.0f,Gdx.graphics.getWidth() / 30.0f);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30.0f,Gdx.graphics.getHeight()/2.0f+enemyOffSet2[i]+Gdx.graphics.getHeight() / 20.0f,Gdx.graphics.getWidth() / 30.0f);
            //shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth() / 30.0f,Gdx.graphics.getHeight()/2.0f+enemyOffSet3[i]+Gdx.graphics.getHeight() / 20.0f,Gdx.graphics.getWidth() / 30.0f);

            if (Intersector.overlaps(spaceShipCircle,enemyCircles[i]) || Intersector.overlaps(spaceShipCircle,enemyCircles2[i]) || Intersector.overlaps(spaceShipCircle,enemyCircles3[i])){
				gameState=2;
			}
		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
