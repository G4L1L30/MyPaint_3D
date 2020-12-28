package mypaint3d;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author g4l1l3u
 */
public class Reta {
    
    private final List<Point> coordenadas = new ArrayList<>();

    public List<Point> getCoordenadas() {
        return coordenadas;
    }
    
    public Color getColor()
    {
        return Color.RED;
    }
    
    private void writePixel(int x, int y)
    {
        coordenadas.add(new Point(x, y));
    }
    
    public void bresenham(Point p1, Point p2)
    {
        int x1 = p1.x, y1 = p1.y;
        int x2 = p2.x, y2 = p2.y;
        
        int d, x, y, incE, incNE, declive;
        int dx = x2 - x1;
        int dy = y2 - y1;
        
        if (Math.abs(dx) >= Math.abs(dy)) 
        {
            if (x2 < x1) 
            {
                bresenham(p2, p1);
            } 
            else 
            {
                if (dy <= 0) 
                {
                    declive = -1;
                    dy = -dy;
                } 
                else 
                {
                    declive = 1;
                }
                // Constante de Bresenham
                incE = 2 * dy;
                incNE = 2 * dy - 2 * dx;
                d = 2 * dy - dx;
                y = y1;
                for (x = x1; x <= x2; x++) 
                {
                    writePixel(x, y);
                    if (d <= 0) 
                    {
                        d += incE;
                    } 
                    else 
                    {
                        d += incNE;
                        y += declive;
                    }
                }
            }
        }
        else
        {
            if (y2 < y1) 
            {
                bresenham(p2, p1);
            } 
            else 
            {
                if (dx < 0) 
                {
                    declive = -1;
                    dx = -dx;
                } 
                else 
                {
                    declive = 1;
                }
                // Constante de Bresenham
                incE = 2 * dx;
                incNE = 2 * dx - 2 * dy;
                d = 2 * dx - dy;
                x = x1;
                for (y = y1; y <= y2; y++) 
                {
                    writePixel(x, y);
                    if (d <= 0) 
                    {
                        d += incE;
                    } 
                    else 
                    {
                        d += incNE;
                        x += declive;
                    }
                }
            }
        }
        
    }

    
}
