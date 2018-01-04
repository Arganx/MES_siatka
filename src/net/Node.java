package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Node {
    private double x;
    private double y;
    private double t;
    private double local_e;
    private double local_n;
    private boolean status;
    private int node_number;

    public Node(double x, double y, double t, boolean status,int node_number) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.status = status;
        this.node_number=node_number;
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

    public double getLocal_e() {
        return local_e;
    }

    public void setLocal_e(double local_e) {
        this.local_e = local_e;
    }

    public double getLocal_n() {
        return local_n;
    }

    public void setLocal_n(double local_n) {
        this.local_n = local_n;
    }

    public boolean isStatus() {
        return status;
    }

    public void show()
    {
        System.out.println("Wartosci wezla: wynosza:\nX: " + x +"\nY: " + y);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNode_number() {
        return node_number;
    }
}
