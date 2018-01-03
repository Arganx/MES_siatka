package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Element {
    private Node[] id;
    private double pow[];

    private double[][] jakobian;

    Element(Node a, Node b, Node c, Node d)
    {
        id = new Node[4];
        id[0]=a;
        id[0].setLocal_e(0);
        id[0].setLocal_n(0);
        id[1]=b;
        id[1].setLocal_e(1);
        id[1].setLocal_n(0);
        id[2]=c;
        id[2].setLocal_e(1);
        id[2].setLocal_n(1);
        id[3]=d;
        id[3].setLocal_e(0);
        id[3].setLocal_n(1);
        pow=new double[4];

    }

    public Node[] getId() {
        return id;
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
