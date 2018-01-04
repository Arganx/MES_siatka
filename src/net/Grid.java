package net;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Grid {
    private Node[] nodes;
    private Element[] elements;
    private Global_data global_data;
    private double[][]  global_H;   //pamietac o zerowaniu
    private double[][]  global_C;
    private double[] global_P;

    //tymczasowo gdyz dla 1 elementu nie beda sie zmieniac
    private double[][] derrivative_e;
    private double[][] derrivative_n;

    Grid(Global_data global_data)
    {
        global_H=new double[16][16];
        global_C=new double[16][16];
        global_P=new double[16];
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
            nodes[i]=new Node(x,y,0,false,i);
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

        warunekBrzegowy_na_bokach();
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

    public double[][][] Jakobian(int element_number)        //zwraca 4 macierze jakobiego dla 4 punktow calkowania w danym elemencie
    {
        Element element = elements[element_number];
        double jacobian[][][] = new double[4][][];
        double[][] derrivative_e=new double[4][];
        double[][] derrivative_n=new double[4][];
        for(int i=0;i<4;i++) {  //petla po punktach calkowania
            derrivative_e[i] = fderrivative_e(global_data,i);
            derrivative_n[i] = fderrivative_n(global_data,i);
            jacobian[i] = doJacobian(derrivative_e[i], derrivative_n[i], element);
        }
        this.derrivative_e=derrivative_e;
        this.derrivative_n=derrivative_n;
        return jacobian;
    }

    private double[][] doJacobian(double[] derrivative_e,double[] derrivative_n,Element element)
    {
        double[][] jakobian = new double[2][];
        for(int i=0;i<2;i++)
        {
            jakobian[i]=new double[2];
        }

        //  dx/de
        jakobian[0][0]=0;
        for(int i=0;i<4;i++) {
            jakobian[0][0] += derrivative_e[i]*element.getId()[i].getX();
            //System.out.println(derrivative_e[i]+"*"+element.getId()[i].getX());
            //System.out.println("Nowa wartosc jakobianu: "+ jakobian[0] );
        }

        // dy/de
        jakobian[0][1]=0;
        for(int i=0;i<4;i++) {
            jakobian[0][1] += derrivative_e[i]*element.getId()[i].getY();
        }

        // dx/dn
        jakobian[1][0]=0;
        for(int i=0;i<4;i++) {
            jakobian[1][0] += derrivative_n[i]*element.getId()[i].getX();
        }

        // dy/dn
        jakobian[1][1]=0;
        for(int i=0;i<4;i++) {
            jakobian[1][1] += derrivative_n[i]*element.getId()[i].getY();
        }
        return jakobian;
    }

    public double[] fderrivative_e(Global_data data, int point_number)  //wylicza wartosci pochodnych po funkci krztaltu dla kolejnych punktow po e
    {
        //point number 0 - x=-0.57735   y=-0.57735
        //point number 1 - x=0.57735    y=-0.57735
        //point number 2 - x=-0.57735   y=0.57735
        //point number 3 - x=0.57735    y=0.57735
        //n -> x
        //e -> y
        double derrivative_e[]=new double[4];

        if(point_number==1 || point_number==3) {
            for (int j = 0; j < 4; j++)    //po funkcjach krztaltu
            {
                derrivative_e[j] = data.derrivative_e(j, data.getPoints()[0]);
            }
            return  derrivative_e;
        }
        else if(point_number==0 || point_number==2)
        {
            for (int j = 0; j < 4; j++)    //po funkcjach krztaltu
            {
                derrivative_e[j] = data.derrivative_e(j, data.getPoints()[1]);
            }
            return  derrivative_e;
        }
        return  null;
    }

    public double[] fderrivative_n(Global_data data, int point_number)  //wylicza wartosci pochodnych po funkci krztaltu dla kolejnych punktow po n
    {
        double[] derrivative_n=new double[4];
        //point number 0 - x=-0.57735   y=-0.57735
        //point number 1 - x=0.57735    y=-0.57735
        //point number 2 - x=-0.57735   y=0.57735
        //point number 3 - x=0.57735    y=0.57735
        //n -> x
        //e -> y
        if(point_number<2) {
            for (int j = 0; j < 4; j++)    //po funkcjach krztaltu
            {
                derrivative_n[j] = data.derrivative_n(j, data.getPoints()[1]);
            }
            return  derrivative_n;
        }
        else if(point_number<4)
        {
            for (int j = 0; j < 4; j++)    //po funkcjach krztaltu
            {
                derrivative_n[j] = data.derrivative_n(j, data.getPoints()[0]);
            }
            return  derrivative_n;
        }
        return  null;
    }

    public double[][] getDerrivative_e() {
        return derrivative_e;
    }

    public double[][] getDerrivative_n() {
        return derrivative_n;
    }

    private void warunekBrzegowy_na_bokach()
    {
        int nodes_b = global_data.getNodes_b();
        int nodes_h = global_data.getNodes_h();
        for(int i=0;i<nodes_h*nodes_b;i++)
        {
            if(i<nodes_b)       //dol
            {
            nodes[i].setStatus(true);
            }
            else  if(i%nodes_h==0)      //lewo
            {
                nodes[i].setStatus(true);
            }
            else if(i%nodes_h==nodes_h-1)   //prawo
            {
                nodes[i].setStatus(true);
            }
            else if(i>(nodes_b*nodes_h)-nodes_b-2)
            {
                nodes[i].setStatus(true);
            }

        }
    }

    public Node[] getNodes() {
        return nodes;
    }


    //Zwraca Macierz H danego elementu
    public void doH(int elementNumber,Global_data data)
    {
        Element element = elements[elementNumber];
        Operations operations = new Operations();

        double[][][] jakobian = Jakobian(elementNumber);    //jakobiany dla wszystkich 4 punktow calkowania

        //wyswietlenie jakobianow
/*        for(int k=0;k<4;k++) {  //po jakobianach elementu
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 2; i++) {

                    System.out.print(jakobian[k][j][i] + ",");
                }
                System.out.println();
            }
            System.out.println();
        }*/
        //Inicjacja macierzy H
        double H_matrix_of_element[][]=new double[4][4];
        for(int i=0;i<H_matrix_of_element.length;i++)
        {
            for(int j=0;j<H_matrix_of_element.length;j++)
            {
                H_matrix_of_element[i][j]=0;
            }
        }
        double[][] pack = new double[4][2];
        double[][] result = new double[4][];
        double[] Nx_wektor = new double[4];
        double[] Ny_wektor = new double[4];
        //4 macierze H dla 4 punktow calkowania
        double[][][] H = new double[4][][];
        //Wyliczenie macierzy H dla kazdego punktu calkowania w elemencie
        for(int z=0;z<4;z++) {
            double[][] invertedjackian = operations.invertMatrix(jakobian[z]);//odwrocenie macierzy jakobiego

            for (int i = 0; i < 4; i++) {      //Zapakowanie pochodnych w pary dN/de dN/dn     4 bo 4 funkcje psztaltu
                pack[i][0] = getDerrivative_e()[z][i];     //Dla 0 punktu calkowania
                pack[i][1] = getDerrivative_n()[z][i];
            }


            for (int i = 0; i < 4; i++) {
                result[i] = operations.multiplyVector(invertedjackian, pack[i]);    //mnozenie jakobianu odwrocnego przez pochodne
            }

            //tworze wektor zlozony z pochodnych po x z funkcji ksztaltu
            for (int i = 0; i < Nx_wektor.length; i++) {
                Nx_wektor[i] = result[i][0];
            }

            //tworze wektor zlozony z pochodnych po y z funkcji ksztaltu
            for (int i = 0; i < Ny_wektor.length; i++) {
                Ny_wektor[i] = result[i][1];
            }

            //Wyswietlenie wkktorow
            /*for (int i = 0; i < Nx_wektor.length; i++) {
                System.out.println(Nx_wektor[i]);
            }
            for (int i = 0; i < Nx_wektor.length; i++) {
                System.out.println(Ny_wektor[i]);
            }*/


            double[][] Hminix = operations.multiplyVectorVectorTrans(Nx_wektor, Nx_wektor);
            double[][] Hminiy = operations.multiplyVectorVectorTrans(Ny_wektor, Ny_wektor);


            H[z] = operations.matrixAdd(Hminix, Hminiy);


            double det = operations.determinant(jakobian[0]);


            det = det * data.getConductivity();   //Pomnozenie przez k
            //Mnozenie przez wyznacznik jakobianu
            H[z] = operations.multiplyMatrixScalar(H[z], det);
            //operations.printMatrix(H[z], 0);

            //dodawanie pojedynczych macierzy H do macierzy H elementu
            for(int i=0;i<H_matrix_of_element.length;i++)
            {
                for(int j=0;j<H_matrix_of_element.length;j++)
                {
                    H_matrix_of_element[i][j]+=H[z][i][j];
                }
            }
        }

        double[] N=new double[4];
        //Jeśli jest warunek brzegowy w elemencie
        for(int j=0;j<2;j++)//po 2 punktach na scianie
        {
            for (int i = 0; i < 4; i++) {  //po krawedziach elementu
                if (element.getSurface()[i].isEdge()) {
                    double l = 0;
                    if (element.getSurface()[i].getNode1().getX() == element.getSurface()[i].getNode2().getX() && element.getSurface()[i].getNode1().getX() != 0) {  //oba na tej samej osi y to e
                        N[0] = data.f1(1, data.getPoints()[j]);
                        N[1] = data.f2(1, data.getPoints()[j]);
                        N[2] = data.f3(1, data.getPoints()[j]);
                        N[3] = data.f4(1, data.getPoints()[j]);
                        l = Math.abs(element.getSurface()[i].getNode1().getY() - element.getSurface()[i].getNode2().getY());
                    } else if (element.getSurface()[i].getNode1().getX() == element.getSurface()[i].getNode2().getX() && element.getSurface()[i].getNode1().getX() == 0) //lewa krawedz
                    {
                        N[0] = data.f1(-1, data.getPoints()[j]);
                        N[1] = data.f2(-1, data.getPoints()[j]);
                        N[2] = data.f3(-1, data.getPoints()[j]);
                        N[3] = data.f4(-1, data.getPoints()[j]);
                        l = Math.abs(element.getSurface()[i].getNode1().getY() - element.getSurface()[i].getNode2().getY());
                    } else if (element.getSurface()[i].getNode1().getY() == element.getSurface()[i].getNode2().getY() && element.getSurface()[i].getNode1().getY() != 0) //gora
                    {
                        N[0] = data.f1(data.getPoints()[j], 1);
                        N[1] = data.f2(data.getPoints()[j], 1);
                        N[2] = data.f3(data.getPoints()[j], 1);
                        N[3] = data.f4(data.getPoints()[j], 1);
                        l = Math.abs(element.getSurface()[i].getNode1().getX() - element.getSurface()[i].getNode2().getX());
                    } else if (element.getSurface()[i].getNode1().getY() == element.getSurface()[i].getNode2().getY() && element.getSurface()[i].getNode1().getY() == 0) //dol
                    {
                        N[0] = data.f1(data.getPoints()[j], -1);
                        N[1] = data.f2(data.getPoints()[j], -1);
                        N[2] = data.f3(data.getPoints()[j], -1);
                        N[3] = data.f4(data.getPoints()[j], -1);
                        l = Math.abs(element.getSurface()[i].getNode1().getX() - element.getSurface()[i].getNode2().getX());
                    } else {
                        System.out.println("Nie udalo sie policzyć drugiej czesci macierzy H");
                    }

                    double[][] H2 = operations.multiplyVectorVectorTrans(N, N);
                    H2 = operations.multiplyMatrixScalar(H2, data.getAlfa());
                    H2 = operations.multiplyMatrixScalar(H2, l / 2);
                    //System.out.println(l);
                    H_matrix_of_element=operations.matrixAdd(H_matrix_of_element,H2);
                }
            }
        }

        mergeWithGlobalH(H_matrix_of_element,element);
        //operations.printMatrix(H_matrix_of_element,0);
    }

    public void mergeWithGlobalH(double[][] H,Element element){
        for(int i = 0 ; i < H.length; i++){
            for(int w = 0; w< H[i].length; w++){
                global_H[element.getId()[i].getNode_number()][element.getId()[w].getNode_number()] += H[i][w];
            }
        }
    }

    public void mergeWithGlobalC(double[][] C,Element element){
        for(int i = 0 ; i < C.length; i++){
            for(int w = 0; w< C[i].length; w++){
                global_C[element.getId()[i].getNode_number()][element.getId()[w].getNode_number()] += C[i][w];
            }
        }
    }

    public void mergeWithGlobalP(double[] P, Element element){
        for(int i = 0 ; i < P.length; i++){
            global_P[element.getId()[i].getNode_number()] += P[i];
        }
    }

    public Element[] getElements() {
        return elements;
    }

    public double[][] getGlobal_H() {
        return global_H;
    }

    //Zwraca macierz C elementu
    public double[][] doC(int elementNumber,Global_data data)
    {
        double[][][] jakobian = Jakobian(elementNumber);
        double[][][] minC = new double[4][][];  //dla 4 punktow calkowania
        double[][] C = new double[4][4];
        //inicjacja macierzy C dla elementu
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                C[i][j]=0;
            }
        }
        Operations operations = new Operations();
        double[] N=new double[4];
        int iterator =0;
        //deklaracja
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++) {
                N[0] = data.f1(data.getPoints()[i], data.getPoints()[j]);
                N[1] = data.f2(data.getPoints()[i], data.getPoints()[j]);
                N[2] = data.f3(data.getPoints()[i], data.getPoints()[j]);
                N[3] = data.f4(data.getPoints()[i], data.getPoints()[j]);
                minC[iterator]=operations.multiplyVectorVectorTrans(N,N);
                minC[iterator]=operations.multiplyMatrixScalar(minC[iterator],data.getDensity()* data.getSpecific_heat()*operations.determinant(jakobian[iterator]));
                //sumowanie macierzy C dla elementu
                for(int z=0;z<C.length;z++)
                {
                    for(int g=0;g<C[0].length;g++)
                    {
                        C[z][g]+=minC[iterator][z][g];
                    }
                }
                iterator++;
            }
        }
        mergeWithGlobalC(C,elements[elementNumber]);
        return C;

    }

    public double[] doP(int elementNumber, Global_data data)
    {
            Element element = elements[elementNumber];
            double l =0;
            if(element.getId()[0].getX()==element.getId()[1].getX()) {
                l = Math.abs(element.getId()[0].getY()-element.getId()[1].getY());
            }
            else
            {
                l = Math.abs(element.getId()[0].getX()-element.getId()[1].getX());
            }
            double[] P=new double[4];
            for(int i=0;i<4;i++)
            {
                P[i]=0;
            }
            Operations operations = new Operations();
            double[] N = new double[4];
            for(int i=0;i<2;i++)    //Po 4 punktach calkowania
            {
                for(int j=0;j<2;j++)
                {
                    N[0]=data.f1(data.getPoints()[i],data.getPoints()[j]);
                    N[1]=data.f2(data.getPoints()[i],data.getPoints()[j]);
                    N[2]=data.f3(data.getPoints()[i],data.getPoints()[j]);
                    N[3]=data.f4(data.getPoints()[i],data.getPoints()[j]);
                    N=operations.multiplyVectorScalar(N,data.getAlfa()*data.getAmbient());
                    N=operations.multiplyVectorScalar(N,l/2);
                    for(int z=0;z<4;z++)
                    {
                        P[z]+=N[z];
                    }
                }

            }
            mergeWithGlobalP(P,element);
            return P;
    }

    public double[][] getGlobal_C() {
        return global_C;
    }

    public double[] getGlobal_P() {
        return global_P;
    }
}
