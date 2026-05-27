package com.crimsyn.viver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	public boolean upDirection, downDirection, leftDirection, rightDirection;
	
	public Input() {
		upDirection = false;
		downDirection = false;
		leftDirection = false;
		rightDirection = false;
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) {
			upDirection = true;
		}
		
		if (key == KeyEvent.VK_S) {
			downDirection = true;
		}
		
		if (key == KeyEvent.VK_A) {
			leftDirection = true;
		}
		
		if (key == KeyEvent.VK_D) {
			rightDirection = true;
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

			upDirection = false;

			downDirection = false;

			leftDirection = false;

			rightDirection = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
