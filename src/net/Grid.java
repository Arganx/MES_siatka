package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Grid {
    private Node[] nodes;
    private Element[] elements;

    Grid(Global_data global_data)
    {
        double b=global_data.getB();
        double h= global_data.getH();
        int nodes_b = global_data.getNodes_b();
        int nodes_h = global_data.getNodes_h();
        double x=0;
        double y=0;
        double dx = b/(nodes_b-1);
        double dy = h/(nodes_h-1);


        nodes=new Node[nodes_h*nodes_b];
        for(int i=0;i<nodes_h*nodes_b;i++)
        {
            nodes[i]=new Node(x,y,0,false);
            y+=dy;
            if(y>h)
            {
                y=0;
                x+=dx;
            }
        }

        elements = new Element[(nodes_h-1)*(nodes_b-1)];

        int k=0;
        for(int i=0;k<(nodes_h-1)*(nodes_b-1);i++)
        {
            System.out.println(i);
            if(i%(nodes_h)!=nodes_h-1||i==0) {
                elements[k] = new Element(nodes[i], nodes[i + 1], nodes[i + nodes_b], nodes[i + nodes_b + 1]);
                k++;
            }
        }
    }

    public void show_node(int node_number)
    {
        System.out.println("Wartosci wezla: " + node_number +" wynosza:\nX: " + nodes[node_number].getX() +"\nY: " + nodes[node_number].getY());
    }

    public void show_element(int element_number)
    {
        elements[element_number].show();
    }
}
