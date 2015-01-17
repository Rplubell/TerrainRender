/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.player;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = null;
    private float yaw = 0.0f;
    private float pitch = 0.0f;
    
    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }
    
    public void yaw(float amt) {
        yaw += amt;
    }
    public void pitch(float amt) {
        if(pitch-amt < 100 && pitch-amt > -100)
            pitch -= amt;
    }
    
    public void forwards(float distance) {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw));
    }
    public void backwards(float distance) {
        position.x += distance * (float)Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float)Math.cos(Math.toRadians(yaw));
    }
    public void left(float distance) {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
    }
    public void right(float distance) {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
    }
    public void up(float distance) {
        position.y -= distance;
    }
    public void down(float distance) {
        position.y += distance;
    }
    public void lookThrough() {
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(position.x, position.y, position.z);
        
    }
}
