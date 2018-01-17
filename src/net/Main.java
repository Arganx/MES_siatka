package net;

public class Main {

    public static Global_data data = new Global_data();
    public static Operations operations = new Operations();
    public static void main(String[] args) {

        System.out.println(data.getH()+"\n"+data.getB()+"\n"+data.getNodes_h()+"\n"+data.getNodes_b());

        Grid grid = new Grid(data);

        grid.show_element(1);


        //wyswietlenie czy jest warunek brzegowy
        Node[] nodes = grid.getNodes();
        for(int i=0;i<nodes.length;i++)
        {
            System.out.print(nodes[i].isStatus()+",");
            if((i+1)%data.getNodes_h()==0)
            {
                System.out.println();
            }
        }


       grid.simulate(grid,data);


    }


}
