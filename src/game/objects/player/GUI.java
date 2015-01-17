/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.objects.player;

import game.Main;
import game.Main;
import static game.Main.HEIGHT;
import static game.Main.WIDTH;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author rplu1618
 */
public class GUI {
    public GUI() {
        
    }
    public void render(int health) {
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0.0, Main.WIDTH, Main.HEIGHT, 0.0, -1.0, 10.0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glDisable(GL_CULL_FACE);

        //glClear(GL_DEPTH_BUFFER_BIT);
        glTranslatef(10.0f, Main.HEIGHT-40.0f, 0.0f);
        for(int i = 0; i <= health; i++) {
            glBegin(GL_QUADS);
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex2f(0.0f, 0.0f);
                glVertex2f(30.0f, 0.0f);
                glVertex2f(30.0f, 30.0f);
                glVertex2f(0.0f, 30.0f);
            glEnd();
            glTranslatef(40.0f, 0.0f, 0.0f);
        }
        /*glBegin(GL_QUADS);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex2f(0.0f, 0.0f);
            glVertex2f(30.0f, 0.0f);
            glVertex2f(30.0f, 30.0f);
            glVertex2f(0.0f, 30.0f);
        glEnd();
        glTranslatef(40.0f, 0.0f, 0.0f);
        glBegin(GL_QUADS);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex2f(0.0f, 0.0f);
            glVertex2f(30.0f, 0.0f);
            glVertex2f(30.0f, 30.0f);
            glVertex2f(0.0f, 30.0f);
        glEnd();*/

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
    }
}
