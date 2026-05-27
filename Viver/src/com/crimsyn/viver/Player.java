package com.crimsyn.viver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;

public class Player {
	
	Main cMain;
	
	public int x;
	public int y;
	public int angle;
	public int playerAngle;
	
	public int speed;
	
	public float rayX;
	public float rayY;
	
	public boolean movement = false;
	
public Player(Main scMain) {
		
		cMain = scMain;
		
		x = 100;
		y = 100;
		speed = 5;
		
		angle = 90;
		
	}

	public void ray(Graphics g) {
		
		int fov = 60;
		int rayNum = 60;
		int rayDistance = 150;
		
		for (int i = 0; i < rayNum; i++) {
			float rayAngle = angle - fov / 2 + ((float) i / rayNum) * fov;
			
			double radians = Math.toRadians(rayAngle);
			
			rayX = x + 24;
			rayY = y + 24;
			
			for (int j = 0; j < rayDistance; j++) {
				
				rayX += Math.sin(radians);
				rayY += Math.cos(radians);
				
				g.setColor(Color.BLUE);
				g.fillRect((int) rayX, (int) rayY, 2, 2);
				
			}
		}
		
		
	}
	
	
	
	
	
	public void update(Input cInput) {
		
		movement = true;
		
		if (cInput.rightDirection == true) {
			x += speed;
			angle -= speed;
		}
		
		if (cInput.leftDirection == true) {
			x -= speed;
			angle += speed;
		}

		if (cInput.downDirection == true) {
			y += speed;
		}

		if (cInput.upDirection == true) {
			y -= speed;
		}
		
		movement = false;
		
	}

	public void render(Graphics g) {
		ray(g);
		
		g.setColor(Color.GREEN);
		g.fillRect(x, y, cMain.ntileSize, cMain.ntileSize);
				
		
		
	}
}
