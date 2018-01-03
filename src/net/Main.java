package net;

public class Main {

    public static Global_data data = new Global_data();
    public static void main(String[] args) {



        System.out.println(data.getH()+"\n"+data.getB()+"\n"+data.getNodes_h()+"\n"+data.getNodes_b());

        Grid grid = new Grid(data);

        grid.show_element(4);

        double[] jakobian = grid.Jakobian(0);

        for(int i=0;i<4;i++)
        {

            System.out.print(jakobian[i]+",");
            if(i==1)
            {
                System.out.println();
            }

        }
    }
}
