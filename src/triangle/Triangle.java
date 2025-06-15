package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;
    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();

        // Clear background to white
        gBuffer.setColor(Color.white);
        gBuffer.fillRect(0, 0, size.width, size.height);

        int maxDepth =1;
        double sideLength = size.width * 0.9;

        drawSierpinski(gBuffer, size.width / 2.0, size.height * 0.95, sideLength, maxDepth, maxDepth);

        return bufferedImage;
    }


    private void drawSierpinski(Graphics2D g, double x, double y, double sideLength, int depth, int maxDepth) {
        int half = (int) (sideLength / 2);
        int height = (int) (Math.sqrt(3) / 2 * sideLength);

        int[] xPoints = {(int)(x - half), (int)(x + half), (int)x};
        int[] yPoints = {(int)y, (int)y, (int)(y - height)};

        // Draw filled triangle 
        g.setColor(getColorForDepth(maxDepth - depth, maxDepth));
        g.fillPolygon(xPoints, yPoints, 3);

        if (depth > 0) {
            double halfSide = sideLength / 2;
            double subHeight = Math.sqrt(3) / 2 * halfSide;

            // Recursively draw smaller triangles
            drawSierpinski(g, x - halfSide / 2, y, halfSide, depth - 1, maxDepth);
            drawSierpinski(g, x + halfSide / 2, y, halfSide, depth - 1, maxDepth);
            drawSierpinski(g, x, y - subHeight, halfSide, depth - 1, maxDepth);
        }
    }


    private Color getColorForDepth(int currentDepth, int maxDepth) {
        float ratio = (float) currentDepth / maxDepth;
        int red = (int) (255 * (1 - ratio));
        int blue = (int) (255 * ratio);
        return new Color(red, 0, blue);
    }



    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
