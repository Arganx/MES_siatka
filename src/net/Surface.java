package net;

public class Surface {
    private Node node1;
    private Node node2;
    private int wall;//0-dol,1-prawo,2-gora,3-lewo

    Surface(Node a,Node b,int wall)
    {
        node1=a;
        node2=b;
        this.wall=wall;
    }

    public boolean isEdge()     //Sprawdza czy wystepuje warunek brzegowy na powierzchni
    {
        if(node1.isStatus() && node2.isStatus())
        {
            return true;
        }
        return false;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }

    public int getWall() {
        return wall;
    }
}
