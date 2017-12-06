package net;

public class Main {

    public static Global_data data = new Global_data();
    public static void main(String[] args) {



        System.out.println(data.getH()+"\n"+data.getB()+"\n"+data.getNodes_h()+"\n"+data.getNodes_b());

        Grid grid = new Grid(data);
/*        for(int i=0;i<data.getNodes_h()*data.getNodes_b();i++)
        {
            grid.show_node(i);
        }*/

        grid.show_element(4);

        grid.getElement(3).showJacobian(0);
    }
}
