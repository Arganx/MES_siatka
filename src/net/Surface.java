package net;

public class Surface {
    private Node node1;
    private Node node2;

    Surface(Node a,Node b)
    {
        node1=a;
        node2=b;
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
}
