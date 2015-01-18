/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.objects;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

/**
 *
 * @author rplu1618
 */
public class cube {
    public static void draw() {
        
        glBegin(GL11.GL_QUADS);
            glColor3d(Math.random(), Math.random(), Math.random());
                glVertex3f(-0.5f, 0.5f, 0.5f);
                glVertex3f(0.5f, 0.5f, 0.5f);
                glVertex3f(0.5f, 0.5f, -0.5f);
                glVertex3f(-0.5f, 0.5f, -0.5f);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(-0.5f, -0.5f, 0.5f);
                glVertex3f(0.5f, -0.5f, 0.5f);
                glVertex3f(0.5f, 0.5f, 0.5f);
                glVertex3f(-0.5f, 0.5f, 0.5f);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(0.5f, -0.5f, 0.5f);
                glVertex3f(0.5f, -0.5f, -0.5f);
                glVertex3f(0.5f, 0.5f, -0.5f);
                glVertex3f(0.5f, 0.5f, 0.5f);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(-0.5f, -0.5f, 0.5f);
                glVertex3f(-0.5f, 0.5f, 0.5f);
                glVertex3f(-0.5f, 0.5f, -0.5f);
                glVertex3f(-0.5f, -0.5f, -0.5f);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(-0.5f, -0.5f, 0.5f);
                glVertex3f(0.5f, -0.5f, 0.5f);
                glVertex3f(0.5f, -0.5f, -0.5f);
                glVertex3f(-0.5f, -0.5f, -0.5f);
                glColor3f(0.0f, 0.0f, 0.0f);
                glVertex3f(0.5f, 0.5f, -0.5f);
                glVertex3f(0.5f, -0.5f, -0.5f);
                glVertex3f(-0.5f, -0.5f, -0.5f);
                glVertex3f(-0.5f, 0.5f, -0.5f);
            glEnd();
    }
}
