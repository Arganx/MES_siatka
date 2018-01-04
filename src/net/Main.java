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


        for(int i=0;i<grid.getElements().length;i++)
        {
            grid.doH(i,data);
            grid.doC(i,data);
            grid.doP(i,data);
        }

        operations.printMatrix(grid.getGlobal_H(),0);
        operations.printMatrix(grid.getGlobal_C(),0);

        double[][] H = operations.divideMatrixScalar(grid.getGlobal_C(),data.getStep_time());//Najpierw dzielenie przez skalar
        H = operations.matrixAdd(H,grid.getGlobal_H());
        operations.printMatrix(H,0);
        //double[] P=grid.doP(0,data);
        //perations.printVector(P);

        operations.printVector(grid.getGlobal_P());

        double[] t0=new double[16];
        for(int i=0;i<16;i++)
        {
            t0[i]=data.getInitial_temp();
        }
        double[] P=operations.addVectors(grid.getGlobal_P(),operations.multiplyVector(operations.divideMatrixScalar(grid.getGlobal_C(),data.getStep_time()),t0));
        System.out.println();
        operations.printVector(P);  //brak razy 2 w niektorych miejscach wektora

    }


}
