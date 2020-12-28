package mypaint3d;


/**
 *
 * @author g4l1l3u
 */
public class NoAET implements Comparable<NoAET>{
    
    private double ymax, xmin, incx, zmin, inczy, rxmin, gymin, bzmin, incrx, incgy, incbz;

    public NoAET(double ymax, double xmin, double incx, double zmin, double inczy, double rxmin, double gymin, double bzmin, double incrx, double incgy, double incbz) {
        this.ymax = ymax;
        this.xmin = xmin;
        this.incx = incx;
        this.zmin = zmin;
        this.inczy = inczy;
        this.rxmin = rxmin;
        this.gymin = gymin;
        this.bzmin = bzmin;
        this.incrx = incrx;
        this.incgy = incgy;
        this.incbz = incbz;
    }

    public double getYmax() {
        return ymax;
    }

    public void setYmax(double ymax) {
        this.ymax = ymax;
    }

    public double getXmin() {
        return xmin;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getIncx() {
        return incx;
    }

    public void setIncx(double incx) {
        this.incx = incx;
    }

    public double getZmin() {
        return zmin;
    }

    public void setZmin(double zmin) {
        this.zmin = zmin;
    }

    public double getInczy() {
        return inczy;
    }

    public void setInczy(double inczy) {
        this.inczy = inczy;
    }

    public double getRxmin() {
        return rxmin;
    }

    public void setRxmin(double rxmin) {
        this.rxmin = rxmin;
    }

    public double getGymin() {
        return gymin;
    }

    public void setGymin(double gymin) {
        this.gymin = gymin;
    }

    public double getBzmin() {
        return bzmin;
    }

    public void setBzmin(double bzmin) {
        this.bzmin = bzmin;
    }

    public double getIncrx() {
        return incrx;
    }

    public void setIncrx(double incrx) {
        this.incrx = incrx;
    }

    public double getIncgy() {
        return incgy;
    }

    public void setIncgy(double incgy) {
        this.incgy = incgy;
    }

    public double getIncbz() {
        return incbz;
    }

    public void setIncbz(double incbz) {
        this.incbz = incbz;
    }

    

    
    
    @Override 
    public int compareTo(NoAET no)
    {
        if(no.getXmin() < xmin)
            return 1;
        else
            return -1;
    }
    
}
