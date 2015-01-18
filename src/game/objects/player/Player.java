/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author bplubell
 */
public class Player {

    private Camera camera;
    private float moveSpeed;
    public GUI gui;
    public int health = 3;
    private final float fallspeed = 0.1f;

    public Player(float x, float y, float z, float speed) {
        camera = new Camera(x, y, z);
        moveSpeed = speed;
        gui = new GUI();
    }

    public void update() {
        keyboard();
        camera.lookThrough();
        switch(isGrounded()) {
            case -1:
                adjustY();
                break;
            case 0:
                //adjustY();
                break;
            case 1:
                camera.position.y -= fallspeed;
                break;
        }
        
        // BOTH SIDES THE USE DIFFERENCE FOR ANOTHER LINE//////////////////
        //gui.render(health); GUI NOT RENDERING RIGHT NOW FOR SOME REASON......
    }

    public int isGrounded() {
        

        try {
            if (camera.position.y > game.Main.terrainmap.map[(int) camera.position.x][(int) camera.position.z]) {
                return 1;
            } else if (camera.position.y < game.Main.terrainmap.map[(int) camera.position.x][(int) camera.position.z]) {
                return -1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        //System.out.println(camera.position.y);
        
        return 0;
    }
    
    private void adjustY() {
        float x1 = (int) camera.position.x;
        float z1 = game.Main.terrainmap.map[(int) camera.position.x][(int) camera.position.z];
        float x2 = (int) camera.position.x + 1;
        float z2 = game.Main.terrainmap.map[(int) camera.position.x+1][(int) camera.position.z];
        float slope = (z2 - z1) / (x2 - x1);
        float p1 = slope * camera.position.x;
        
        x1 = (int) camera.position.x;
        z1 = game.Main.terrainmap.map[(int) camera.position.x][(int) camera.position.z+1];
        x2 = (int) camera.position.x + 1;
        z2 = game.Main.terrainmap.map[(int) camera.position.x+1][(int) camera.position.z+1];
        slope = (z2 - z1) / (x2 - x1);
        float p2 = slope * camera.position.x;
        
        x1 = p1;
        z1 = (int) camera.position.z;
        x2 = p2;
        z2 = (int) camera.position.z + 1;
        slope = (z2 - z1) / (x2 - x1);
        float newY = slope * camera.position.z;
        camera.position.y = game.Main.terrainmap.map[(int) camera.position.x][(int) camera.position.z] + newY;
    }

    private void keyboard() {
        camera.yaw(Mouse.getDX() * game.Main.mouseSens);
        camera.pitch(Mouse.getDY() * game.Main.mouseSens);
        if (Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
            //Forward
            camera.forwards(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S) && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
            //Back
            camera.backwards(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
            //Left
            camera.left(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) {
            //Right
            camera.right(moveSpeed);
        }
        //Up down
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            camera.up(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (isGrounded() == 1) {
                camera.down(moveSpeed);
            }
        }
    }
}
