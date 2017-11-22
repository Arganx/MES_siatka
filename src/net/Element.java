package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Element {
    private Node[] id;
    private double pow[];

    Element(Node a, Node b, Node c, Node d)
    {
        id = new Node[4];
        id[0]=a;
        id[1]=b;
        id[2]=c;
        id[3]=d;

        pow=new double[4];
    }

    public void show()
    {
        System.out.println("Wartosci elementu wynosza: ");
        id[0].show();
        id[1].show();
        id[2].show();
        id[3].show();

    }
}
