package net;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Global_data {
    private double h;   //dlugosc siatki
    private double b;      //wysokosc siatki
    private int nodes_b;
    private int nodes_h;
    private double[] points; //punkty calkowania

    Global_data() {
        try {
            Scanner scanner = new Scanner(new File("Dane.txt"));

            h = scanner.nextDouble();
            scanner.nextLine();
            b = scanner.nextDouble();
            scanner.nextLine();
            nodes_h = scanner.nextInt();
            scanner.nextLine();
            nodes_b = scanner.nextInt();
            points = new double[2];
            points[0]=0.57735;
            points[1]=-0.57735;
        } catch (FileNotFoundException e) {
            System.out.println("Can not open file");
            e.printStackTrace();
        }
    }

    //wszystkie funkcje krztaltu sa zdefiniowane w lokalnym ukladzie wspolrzednych
    public double f1(double e, double n)    //1 funkcja krztaltu
    {
        return 0.25 * (1. - e) * (1. - n);
    }

    public double f2(double e, double n)    //2 funkcja krztaltu
    {
        return 0.25 * (1. + e) * (1. - n);
    }

    public double f3(double e, double n)    //3 funkcja krztaltu
    {
        return 0.25 * (1. + e) * (1. + n);
    }

    public double f4(double e, double n)    //4 funkcja krztaltu
    {
        return 0.25 * (1. - e) * (1. + n);
    }

    public double f1de(double n)    //Pochodna po e z pierwszej funkcji krztltu
    {
        return -0.25 *(1-n);
    }

    public double f2de(double n)    //Pochodna po e z drugiej funkcji krztltu
    {
        return 0.25 *(1-n);
    }

    public double f3de(double n)    //Pochodna po e z trzeciej funkcji krztltu
    {
        return 0.25 *(1+n);
    }

    public double f4de(double n)    //Pochodna po e z czwartej funkcji krztltu
    {
        return -0.25 *(1+n);
    }

    public double f1dn(double e)    //Pochodna po n z pierwszej funkcji krztltu
    {
        return -0.25 *(1-e);
    }

    public double f2dn(double e)    //Pochodna po n z drugiej funkcji krztltu
    {
        return -0.25 *(1+e);
    }

    public double f3dn(double e)    //Pochodna po n z trzeciej funkcji krztltu
    {
        return 0.25 *(1+e);
    }

    public double f4dn(double e)    //Pochodna po n z czwartej funkcji krztltu
    {
        return 0.25 *(1-e);
    }

    public double derrivative_e(int number,double n)
    {
        switch (number) {
            case 0:
                return f1de(n);
            case 1:
                return f2de(n);
            case 2:
                return f3de(n);
            case 3:
                return f4de(n);
            default:
                return 9999;
        }
    }

    public double derrivative_n(int number,double e)
    {
        switch (number) {
            case 0:
                return f1dn(e);
            case 1:
                return f2dn(e);
            case 2:
                return f3dn(e);
            case 3:
                return f4dn(e);
            default:
                return 8888;
        }
    }


    public double getH() {
        return h;
    }

    public double getB() {
        return b;
    }

    public int getNodes_b() {
        return nodes_b;
    }

    public int getNodes_h() {
        return nodes_h;
    }

    public double[] getPoints() {
        return points;
    }
}
