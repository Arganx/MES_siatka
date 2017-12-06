package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Element {
    private Node[] id;
    private double pow[];
    private double[][] derrivative_e;
    private double[][] derrivative_n;

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

        fderrivative_e(Main.data);    //tablica pochodnych po e z funkcji krztaltu
        fderrivative_n(Main.data);    //tablica pochodnych po n z funkcji krztaltu
        jakobian = new double[4][]; //4 jakobiany po jednym na punkt
        doJacobian();

    }

    public void showJacobian(int nodeNumber)
    {
        System.out.println("Jakobian:");
        for(int i=3;i>1;i--)
        {
            System.out.print(jakobian[nodeNumber][i]+",");
        }
        System.out.println();
        for(int i=0;i<2;i++)
        {
            System.out.print(jakobian[nodeNumber][i]+",");
        }

    }

    public void show()
    {
        System.out.println("Wartosci elementu wynosza: ");
        id[0].show();
        id[1].show();
        id[2].show();
        id[3].show();

    }

    private void fderrivative_e(Global_data data)  //wylicza wartosci pochodnych po funkci krztaltu dla kolejnych punktow po e
    {
        derrivative_e=new double[4][];
        for(int i=0;i<4;i++)
        {
            derrivative_e[i]=new double[4];
        }

        for(int i=0;i<4;i++)        //po punktach
        {
            for(int j=0;j<4;j++)    //po funkcjach krztaltu
            {
                derrivative_e[i][j]=data.derrivative_e(j,id[i].getLocal_n());
            }
        }
    }

    private void fderrivative_n(Global_data data)  //wylicza wartosci pochodnych po funkci krztaltu dla kolejnych punktow po n
    {
        derrivative_n=new double[4][];
        for(int i=0;i<4;i++)
        {
            derrivative_n[i]=new double[4];
        }

        for(int i=0;i<4;i++)        //po punktach
        {
            for(int j=0;j<4;j++)    //po funkcjach krztaltu
            {
                derrivative_n[i][j]=data.derrivative_n(j,id[i].getLocal_e());
            }
        }
    }

    private void doJacobian()
    {
        for(int i=0;i<4;i++)    //po punktach
        {
            jakobian[i]=new double[4];
            jakobian[i][0]=0;
            for(int j=0;j<4;j++) {  //petla sumujaca dx/dn
                jakobian[i][0] += derrivative_n[i][j]*id[j].getX();
            }

            jakobian[i][1]=0;
            for(int j=0;j<4;j++) {  //petla sumujaca   dy/dn
                jakobian[i][1] += derrivative_n[i][j]*id[j].getY();
            }

            jakobian[i][2]=0;
            for(int j=0;j<4;j++) {  //petla sumujaca    dy/de
                jakobian[i][2] += derrivative_e[i][j]*id[j].getY();
            }

            jakobian[i][3]=0;
            for(int j=0;j<4;j++) {  //petla sumujaca
                jakobian[i][3] += derrivative_e[i][j]*id[j].getX();
            }
        }
    }
}
