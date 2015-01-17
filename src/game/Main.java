package game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import game.map.TerrainMap;
import game.player.Player;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
/*
 *
 * @author rplu1618
 */
public class Main {
    
    public static final int WIDTH = 1900;
    public static final int HEIGHT = 1070;
    public static final float mouseSens = 0.5f;
    public Player player;
    public TerrainMap map;
    
    public Main() {
        player = new Player(0.0f, 0.0f, 0.0f, 0.5f);
        map = new TerrainMap(65, 100,100,100,100);
    }
    
    void start() {
        initGraphics();
        try {
            Display.setFullscreen(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        while(!Display.isCloseRequested()) {
            player.update();
            render();
            Display.setVSyncEnabled(true);
        }
        
        Display.destroy();
    }
    
    void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        player.update();
        map.renderVBO();
        
        Display.sync(60);
        Display.update();
    }
    
    void initGraphics() {
        Mouse.setGrabbed(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glEnable(GL_CULL_FACE);
        GL11.glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(70, (float)WIDTH/HEIGHT, 0.5f, 1000.0f);
        GL11.glMatrixMode(GL_MODELVIEW);
        map.initVBO();
        map.print();
    }
    
    public static void main(String args[]) {
        Main g = new Main();
        
        g.start();
    }
    
    
}
