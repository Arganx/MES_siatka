package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Grid {
    private Node[] nodes;
    private Element[] elements;
    private Global_data global_data;

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
        this.global_data = global_data;

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
            if(i%(nodes_h)!=nodes_h-1||i==0) {
                //elements[k] = new Element(nodes[i], nodes[i + 1], nodes[i + nodes_b], nodes[i + nodes_b + 1]);        //stary sposob
                elements[k] = new Element(nodes[i], nodes[i + nodes_b], nodes[i + nodes_b + 1], nodes[i + 1]);
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

    public Element getElement(int elementNumber)
    {
        if(elementNumber>=elements.length)
        {
            System.out.println("Brak takiego elementu");
            return null;
        }
        return elements[elementNumber];
    }

    public double[] Jakobian(int element_number)
    {
        Element element = elements[element_number];
        double jacobian[] = new double[4];
        double[] derrivative_e;
        double[] derrivative_n;
        derrivative_e=fderrivative_e(global_data);
        derrivative_n=fderrivative_n(global_data);
        jacobian = doJacobian(derrivative_e,derrivative_n,element);
        return jacobian;
    }

    private double[] doJacobian(double[] derrivative_e,double[] derrivative_n,Element element)
    {
        double[] jakobian = new double[4];

        //  dx/de
        jakobian[0]=0;
        for(int i=0;i<4;i++) {
            jakobian[0] += derrivative_e[i]*element.getId()[i].getX();
        }

        // dy/de
        jakobian[1]=0;
        for(int i=0;i<4;i++) {
            jakobian[1] += derrivative_e[i]*element.getId()[i].getY();
        }

        // dx/dn
        jakobian[2]=0;
        for(int i=0;i<4;i++) {
            jakobian[2] += derrivative_n[i]*element.getId()[i].getX();
        }

        // dy/dn
        jakobian[3]=0;
        for(int i=0;i<4;i++) {
            jakobian[3] += derrivative_n[i]*element.getId()[i].getY();
        }
        return jakobian;
    }

    public double[] fderrivative_e(Global_data data)  //wylicza wartosci pochodnych po funkci krztaltu dla kolejnych punktow po e
    {
        double derrivative_e[]=new double[4];

        for(int j=0;j<4;j++)    //po funkcjach krztaltu
        {
            derrivative_e[j]=data.derrivative_e(j,data.getPoints()[0]);
        }
        return  derrivative_e;
    }

    public double[] fderrivative_n(Global_data data)  //wylicza wartosci pochodnych po funkci krztaltu dla kolejnych punktow po n
    {
        double[] derrivative_n=new double[4];


        for(int j=0;j<4;j++)    //po funkcjach krztaltu
        {
            derrivative_n[j]=data.derrivative_n(j,data.getPoints()[1]);
        }
        return  derrivative_n;
    }
}
