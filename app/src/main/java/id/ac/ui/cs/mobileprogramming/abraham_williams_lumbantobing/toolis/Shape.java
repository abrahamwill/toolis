package id.ac.ui.cs.mobileprogramming.abraham_williams_lumbantobing.toolis;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Shape {

    private final int mProgram;
    private int positionHandle;
    private int colorHandle;
    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 3;

    private final int vertexCount = shapeCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    // membuat koordinat untuk bentuk segitiga
    static float shapeCoords[] = {
            0.7f, 0.9f, 0.0f,
            -0.7f, 0.9f, 0.0f,
            -0.4f, 0.6f, 0.0f,
            0.7f, 0.9f, 0.0f,
            -0.4f, 0.6f, 0.0f,
            0.4f, 0.6f, 0.0f,
            -0.4f, 0.6f, 0.0f,
            0.4f, 0.6f, 0.0f,
            -0.7f, 0.3f, 0.0f,
            0.4f, 0.6f, 0.0f,
            -0.7f, 0.3f, 0.0f,
            0.7f, 0.3f, 0.0f,

            -0.7f, -0.2f, 0.0f,
            0.7f, -0.2f, 0.0f,
            0.0f, -0.9f, 0.0f,
    };

    public void setColor(float[] color) {
        this.color = color;

    }

    float color[] = { 1f, 0f, 0f, 1.0f };


    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the shape vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the shape coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the shape
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    public Shape() {

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);

        ByteBuffer bb = ByteBuffer.allocateDirect(
                shapeCoords.length * 4);

        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();

        vertexBuffer.put(shapeCoords);
        vertexBuffer.position(0);
    }
}
