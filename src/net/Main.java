package net;

public class Main {

    public static Global_data data = new Global_data();
    public static Operations operations = new Operations();
    public static void main(String[] args) {

        System.out.println(data.getH()+"\n"+data.getB()+"\n"+data.getNodes_h()+"\n"+data.getNodes_b());

        Grid grid = new Grid(data);

        grid.show_element(4);

        double[][][] jakobian = grid.Jakobian(5);

        for(int k=0;k<4;k++) {  //po jakobianach elementu
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 2; i++) {

                    System.out.print(jakobian[k][j][i] + ",");
                }
                System.out.println();
            }
            System.out.println();
        }

        double[][] invertedjackian = operations.invertMatrix(jakobian[0]);
        double[][] pack = new double[4][2];
        for(int i=0;i<4;i++) {      //Zapakowanie pochodnych w pary dN/de dN/dn     4 bo 4 funkcje psztaltu
            pack[i][0] = grid.getDerrivative_e()[0][i];     //Dla 0 punktu calkowania
            pack[i][1]= grid.getDerrivative_n()[0][i];
        }
        double[][] result= new double[4][];
        for(int i=0;i<4;i++) {
            result[i]=operations.multiplyVector(invertedjackian, pack[i]);    //mnozenie jakobianu odwrocnego przez pochodne
        }

        //tworze wektor zlozony z pochodnych po x z funkcji ksztaltu
        double[] Nx_wektor = new double[4];
        for(int i=0;i<Nx_wektor.length;i++)
        {
            Nx_wektor[i]=result[i][0];
        }

        //tworze wektor zlozony z pochodnych po y z funkcji ksztaltu
        double[] Ny_wektor = new double[4];
        for(int i=0;i<Ny_wektor.length;i++)
        {
            Ny_wektor[i]=result[i][1];
        }

        for(int i=0;i<Nx_wektor.length;i++)
        {
            System.out.println(Nx_wektor[i]);
        }
        for(int i=0;i<Nx_wektor.length;i++)
        {
            System.out.println(Ny_wektor[i]);
        }


        double[][] Hmini =operations.multiplyVectorVectorTrans(Nx_wektor,Nx_wektor);

        operations.printMatrix(Hmini,0);

    }


}
