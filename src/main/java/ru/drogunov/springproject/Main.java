package ru.drogunov.springproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    private static int dano = 7;
    /*
     * На любом языке программирования написать алгоритм, который для любых
     * вещественных a, b и с
     * решает уравнение: а*x^2+b*x+c = 7
     * */
    
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        main.result(a, b, c, dano);
        scanner.close();
    }
    
    public void result(double a, double b, double c, double number) {
        double disc = Math.pow(b, 2) - (4 * a * (c - number));
        if (disc > 0) {
            double x1 = a == 0 ? (-b + Math.sqrt(disc)) : (-b + Math.sqrt(disc)) / (2 * a);
            double x2 = a == 0 ? (-b - Math.sqrt(disc)) : (-b - Math.sqrt(disc)) / (2 * a);
            System.out.printf("Уравнение имеет два корня x1=%.2f x2=%.2f", x1, x2);
        } else if (disc == 0) {
            double x = a == 0 ? -b : -b / (2 * a);
            System.out.printf("корень уравнения равен x=%.2f", x);
        } else {
            System.out.println("Нужно вспомнить математику, а уравнение не имеет коренней");
        }
    }
}
