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
    
    public Player(float x, float y, float z, float speed) {
        camera = new Camera(x,y,z);
        moveSpeed = speed;
        gui = new GUI();
    }
    
    public void update() {
        keyboard();
        camera.lookThrough();
        if(isGrounded() == false) {
            camera.down(0.01f);
        }
        //gui.render(health); GUI NOT RENDERING RIGHT NOW FOR SOME REASON......
    }
    
    public boolean isGrounded() {
        try {
            if(camera.position.y > game.Main.terrainmap.map[(int)camera.position.x][(int)camera.position.z]) return false;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            
        }
        //System.out.println(camera.position.y);
        return false;
    }
    
    private void keyboard() {
        camera.yaw(Mouse.getDX() * game.Main.mouseSens);
        camera.pitch(Mouse.getDY() * game.Main.mouseSens);
        if(Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
            //Forward
            camera.forwards(moveSpeed);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S) && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
            //Back
            camera.backwards(moveSpeed);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
            //Left
            camera.left(moveSpeed);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D) && !Keyboard.isKeyDown(Keyboard.KEY_A)) {
            //Right
            camera.right(moveSpeed);
        }
        //Up down
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            camera.up(moveSpeed);
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if(!isGrounded())
                camera.down(moveSpeed);
        }
    }
}
