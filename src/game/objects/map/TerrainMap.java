/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects.map;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 *
 * @author bplubell
 */
public class TerrainMap {
    private float scale = 1.0f;
    public static float[][] map;
    private int SIZE;
    private float amtSmooth = 0.5f;
    public int vbo_vertex_handle;
    public int vbo_color_handle;
    public int vertex_size = 3; // X, Y, Z,
    public int vertices;
    public int color_size = 3; // R, G, B,
    public FloatBuffer vertex_data;
    public FloatBuffer color_data;
    
    public TerrainMap(int size, float seed1, float seed2, float seed3, float seed4) {
        this.SIZE = size;
        map = new float[SIZE][SIZE];
        map[0][0] = seed1;
        map[SIZE-1][0] = seed2;
        map[0][SIZE-1] = seed3;
        map[SIZE-1][SIZE-1] = seed4;
        process();
        //smooth(amtSmooth);
        //detail();
    }
    
    public void print() {
        for(int xx = 0; xx < SIZE; xx++) {
            for(int zz = 0; zz < SIZE; zz++) {
                System.out.print(" " + map[xx][zz]);
            }
            System.out.println();
        }
    }
    
    public void initVBO() {
        vertices = vertex_size*SIZE*SIZE*16;
        vertex_data = BufferUtils.createFloatBuffer(vertices * vertex_size);
        color_data = BufferUtils.createFloatBuffer(vertices * color_size);
        for(int x = 1; x+1 < SIZE; x++) {
            for(int z = 1; z+1 < SIZE; z++) {
                vertex_data.put(new float[] {x, map[x][z], z});
                vertex_data.put(new float[] {(x+1), map[x+1][z], z});
                vertex_data.put(new float[] {x, map[x][z+1], (z+1)});
                vertex_data.put(new float[] {(x+1), map[x+1][z+1], (z+1)});
                
                vertex_data.put(new float[] {x, map[x][z], z});
                vertex_data.put(new float[] {x, map[x][z+1], (z+1)});
                vertex_data.put(new float[] {(x+1), map[x+1][z], z});
                vertex_data.put(new float[] {(x+1), map[x+1][z+1], (z+1)});
                
                vertex_data.put(new float[] {x, map[x][z], z});
                vertex_data.put(new float[] {(x-1), map[x-1][z], z});
                vertex_data.put(new float[] {x, map[x][z-1], z-1});
                vertex_data.put(new float[] {(x-1), map[x-1][z-1], (z-1)});
                
                vertex_data.put(new float[] {x, map[x][z], z});
                vertex_data.put(new float[] {(x-1), map[x-1][z], z});
                vertex_data.put(new float[] {x, map[x][z-1], (z-1)});
                vertex_data.put(new float[] {(x-1), map[x-1][z-1], (z-1)});
            }
        }
        vertex_data.flip();
        
        for(int x = 1; x+1 < SIZE; x++) {
            for(int z = 1; z+1 < SIZE * 16; z++) {
                color_data.put(new float[] {(float)Math.random(), (float)Math.random(), (float)Math.random()});
                //Sweg colours
            }
        }
        color_data.flip();
        
        vbo_vertex_handle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
        glBufferData(GL_ARRAY_BUFFER, vertex_data, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vbo_color_handle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
        glBufferData(GL_ARRAY_BUFFER, color_data, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    public void detail() {
        float[][] New = new float[((SIZE-1)*2)+1][((SIZE-1)*2)+1];
        for(int x = 0; x+1 < SIZE; x++) {
            for(int z = 0; z+1 < SIZE; z++) {
                New[x*2][z*2] = map[x][z];
            }
        }
        for(int x = 0; x < SIZE*2-2; x++) {
            for(int z = 0; z < SIZE*2-2; z++) {
                System.out.print("   " + New[x][z]);
            }
            System.out.println();
        }
        SIZE = ((SIZE-1)*2)+1;
        map = new float[SIZE][SIZE];
        map = New;
    }
    
    public void renderAsCubes() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                for(int x = 0; x < Math.round(map[i][j]); x++) {
                    glPushMatrix();
                    glTranslatef(i, x, j);
                    game.objects.cube.draw();
                    glPopMatrix();
                }
            }
        }
    }
    
    public void renderVBO() {
        glColor3f(0,0,0);
        for(int i = 0; i < 1; i++) {
            for(int j = 0; j < 1; j++) {
                //glPushMatrix();
                //glTranslatef(SIZE*j, 0.0f, SIZE*i);
                glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex_handle);
                glVertexPointer(vertex_size, GL_FLOAT, 0, 0l);

                glBindBuffer(GL_ARRAY_BUFFER, vbo_color_handle);
                glColorPointer(color_size, GL_FLOAT, 0, 0l);

                glEnableClientState(GL_VERTEX_ARRAY);
                glEnableClientState(GL_COLOR_ARRAY);

                glDrawArrays(GL_QUADS, 0, vertices);

                glDisableClientState(GL_COLOR_ARRAY);
                glDisableClientState(GL_VERTEX_ARRAY);
                //glPopMatrix();
            }
        }
    }
    
    public void crapRender() {
        glColor4f(0.1f, 0.5f, 0.0f, 0.5f);
        for(int x = 1; x+1 < SIZE; x++) {
            for(int z = 1; z+1 < SIZE; z++) {
                glBegin(GL_QUADS);
                glVertex3f(x, map[x][z], z);
                glVertex3f(x+1, map[x+1][z], z);
                glVertex3f(x, map[x][z+1], z+1);
                glVertex3f(x+1, map[x+1][z+1], z+1);
                
                glVertex3f(x, map[x][z], z);
                glVertex3f(x, map[x][z+1], z+1);
                glVertex3f(x+1, map[x+1][z], z);
                glVertex3f(x+1, map[x+1][z+1], z+1);
                
                glVertex3f(x, map[x][z], z);
                glVertex3f(x-1, map[x-1][z], z);
                glVertex3f(x, map[x][z-1], z-1);
                glVertex3f(x-1, map[x-1][z-1], z-1);
                
                glVertex3f(x, map[x][z], z);
                glVertex3f(x-1, map[x-1][z], z);
                glVertex3f(x, map[x][z-1], z-1);
                glVertex3f(x-1, map[x-1][z-1], z-1);
                glEnd();
            }
        }
    }
    
    public void smooth(float threshold) {
        for(int x = 1; x+1 < SIZE; x++) {
            for(int z = 1; z+1 < SIZE; z++) {
                float avg = 0;
                avg += map[x][z];
                avg += map[x+1][z];
                avg += map[x][z+1];
                avg += map[x+1][z+1];
                avg += map[x-1][z];
                avg += map[x][z-1];
                avg += map[x-1][z-1];
                avg += map[x+1][z-1];
                avg += map[x-1][z+1];
                avg /= 9;
                map[x][z] -= (map[x][z]-avg)*threshold;
            }
        }
    }
    
    void process(/*int passes*/) {//TODO Fix index per square value
        int passes = SIZE*SIZE;
        for(int i = 0, squares = 1; i < passes /*&& squares < SIZE*/; i++, squares*=4) {
            float indexPerSquare = Math.round((SIZE-1)/Math.sqrt(squares));
            System.out.println(indexPerSquare);
            if(indexPerSquare < 1) break;
            
            int x1 = 0, z1 = 0, x2 = 0, z2 = 0;
            for(int xx = 0; xx+indexPerSquare < SIZE; xx += indexPerSquare) {
                for(int zz = 0; zz+indexPerSquare < SIZE; zz += indexPerSquare) {
                    x1 = xx;
                    z1 = zz;
                    x2 = x1 + (int)indexPerSquare;
                    z2 = z1 + (int)indexPerSquare;
                    setSquare(x1, z1, x2, z2);
                    setDiamond(x1, z1, x2, z2);
                }
            }
            
            
        }
    }
    
    float rnd(float tornd) {
        return tornd;// * (float)Math.random() * 10;
    }
    
    void setDiamond(int x1, int z1, int x2, int z2) {
        float avg = 0;
        avg += map[x1][z1];
        avg += map[x1][z2];
        avg += map[x2][z1];
        avg += map[x2][z2];
        avg /= 4;
        avg = rnd(avg);
        map[x1+((x2-x1)/2)][z1+((z2-z1)/2)] = avg;
    }
    
    void setSquare(int x1, int z1, int x2, int z2) {
        float avg = 0;
        float mid = map[x1+(x2-x1)/2][z1+((x2-z1)/2)];
        
        if(x1-x2-x1 > 0) {
            avg += map[x1-x2-x1][z1+((z2-z1)/2)];
        }
        avg += map[x1][z1];
        avg += map[x1][z2];
        avg += mid;
        avg /= 4;
        avg = rnd(avg);
        map[x1][z1+(z2-z1)/2] = avg;
        avg = 0;
        
        if(x2+x2-x1 < SIZE) {
            avg += map[x2+x2-x1][z1+((z2-z1)/2)];
        }
        avg += map[x2][z2];
        avg += map[x2][z1];
        avg += mid;
        avg /= 4;
        avg = rnd(avg);
        map[x2][z1+(z2-z1)/2] = avg;
        avg = 0;
        
        if(z1-z2-z1 > 0) {
            avg += map[x1+((x2-x1)/2)][z1-z2-z1];
        }
        avg += map[x1][z1];
        avg += map[x2][z1];
        avg += mid;
        avg /= 4;
        avg = rnd(avg);
        map[x1+((x2-x1)/2)][z1] = avg;
        avg = 0;
        
        if(z2+z2-z1 < SIZE) {
            avg += map[x1+(x2-x1)/2][z2+z2-z1];
        }
        avg += map[x2][z2];
        avg += map[x1][z2];
        avg += mid;
        avg /= 4;
        avg = rnd(avg);
        map[x1+((x2-x1)/2)][z2] = avg;
        avg = 0;
    }
}
