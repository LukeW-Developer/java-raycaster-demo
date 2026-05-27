package com.crimsyn.viver;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.crimsyn.viver.World;
import com.crimsyn.viver.Player;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Window
	public static final String NAME = "Viver";
	public static final int HEIGHT = 480;
	public static final int WIDTH = HEIGHT * 16 / 9;
	
	public final int tileSize = 16;
	public final int tileScaler = 3;
	public int ntileSize = tileSize * tileScaler;
	
	public int screenCol = 12;
	public int screenRow = 16;
	
	public double mainDelta;
	
	public boolean raycaster2DMap = true;

	private boolean running;
	private Thread thread;
	
	Input cInput = new Input();
	World cWorld = new World(this);
	Player cPlayer = new Player(this);
	
	// Classes
	
	// Constructor
	public Main() {
		this.addKeyListener(cInput);
		
	}
	
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
	    final double TICKS_PER_SECOND = 60.0;
	    final double NS_PER_TICK = 1000000000.0 / TICKS_PER_SECOND; // nanoseconds per tick

	    long lastTime = System.nanoTime();
	    long timer = System.currentTimeMillis();
	    double delta = 0;

	    int ticks = 0;  // tick counter
	    int frames = 0; // frame counter

	    while (running) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / NS_PER_TICK;
	        lastTime = now;

	        // update game logic at fixed tick rate
	        while (delta >= 1) {
	            tick();
	            ticks++;
	            delta--;
	        }

	        // render as fast as possible
	        render();
	        frames++;

	        // every second, print ticks and FPS, then reset counters
	        if (System.currentTimeMillis() - timer >= 1000) {
	            System.out.println("Ticks: " + ticks + " | FPS: " + frames);
	            ticks = 0;
	            frames = 0;
	            timer += 1000;
	        }
	        
	        mainDelta = delta;
	    }
	}
	
	// Actions / Inputs
	public void tick() {
		
		cPlayer.update(cInput);
	}
	
	// Visuals
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		cPlayer.render(g);
		
		//g.drawImage(image, 0, 0, nTileSize, nTileSize, null);
		g.dispose();
		bs.show();
		
	}
	
	// Main
	public static void main(String[] args) {
		Main game = new Main();
		game.setMinimumSize(new Dimension(WIDTH*2, HEIGHT*2));
		game.setMaximumSize(new Dimension(WIDTH*2, HEIGHT*2));
		game.setPreferredSize(new Dimension(WIDTH*2, HEIGHT*2));
		
		JFrame frame = new JFrame(Main.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
}
