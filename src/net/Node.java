package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Node {
    private double x;
    private double y;
    private double t;
    private boolean status;

    public Node(double x, double y, double t, boolean status) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.status = status;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getT() {
        return t;
    }

    public boolean isStatus() {
        return status;
    }

    public void show()
    {
        System.out.println("Wartosci wezla: wynosza:\nX: " + x +"\nY: " + y);
    }
}
