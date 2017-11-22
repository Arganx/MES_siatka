package net;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by qwerty on 08-Nov-17.
 */
public class Global_data {
    private double h;
    private double b;
    private int nodes_b;
    private int nodes_h;

    Global_data()
    {
        try {
            Scanner scanner=new Scanner(new File("Dane.txt"));

            h=scanner.nextDouble();
            scanner.nextLine();
            b=scanner.nextDouble();
            scanner.nextLine();
            nodes_h=scanner.nextInt();
            scanner.nextLine();
            nodes_b=scanner.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println("Can not open file");
            e.printStackTrace();
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
}
