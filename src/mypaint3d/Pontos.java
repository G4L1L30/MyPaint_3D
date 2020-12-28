package mypaint3d;

/**
 *
 * @author g4l1l3u
 */
public class Pontos {
    
    private double x, y, z;

    public Pontos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Pontos()
    {
        x = y = z = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public Pontos menos(Pontos p)
    {
        return new Pontos(x - p.getX(), y - p.getY(), z - p.getZ());
    }
    
    public Pontos mais(Pontos p)
    {
        return new Pontos(x + p.getX(), y + p.getY(), z + p.getZ());
    }
    
    public Pontos produto_Vetorial(Pontos p)
    {
        Pontos novo = new Pontos();
        novo.setX(y*p.getZ() - (z*p.getY()));
        novo.setY(z*p.getX() - (x*p.getZ()));
        novo.setZ(x*p.getY() - (y*p.getX()));
        return novo;
    }
    
    public double produto_Escalar(Pontos p)
    {
        return x * p.getX() + y * p.getY() + z * p.getZ();
    }
    
    public Pontos normalizar()
    {
        double norma = Math.sqrt(x*x + y*y + z*z);
        if(norma != 0)
            return new Pontos(x / norma, y / norma, z / norma);
        return new Pontos(1, 1, 1);
    }
    
    
    
    
}
