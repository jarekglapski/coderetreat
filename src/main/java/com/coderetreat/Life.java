package com.coderetreat;

import java.io.IOException;
import java.util.Scanner;

public class Life {

    public static void show(boolean[][] grid) {
        cls();
        String s = "";
        for (boolean[] row : grid) {
            for (boolean val : row)
                if (val)
                    s += "*";
                else
                    s += ".";
            s += "\n";
        }
        System.out.println(s);
    }

    public static boolean[][] gen() {
        boolean[][] grid = new boolean[10][10];
        for (int r = 0; r < 10; r++)
            for (int c = 0; c < 10; c++)
                if (Math.random() > 0.7)
                    grid[r][c] = true;
        return grid;
    }

    public static void main(String[] args) {
        boolean[][] world = gen();
        int[] stayAlive = new int[2];
        stayAlive[0] = 2;
        stayAlive[1] = 3;

        int[] birthNew = new int[1];
        birthNew[0] = 3;

        show(world);
        System.out.println();
        world = nextGen(world, stayAlive, birthNew);
        show(world);
        Scanner s = new Scanner(System.in);
        while (s.nextLine().length() == 0) {
            System.out.println();
            world = nextGen(world, stayAlive, birthNew);
            show(world);
        }
    }

    public static boolean[][] nextGen(boolean[][] world, int[] stayAlive, int[] birthNew) {
        boolean[][] newWorld
                = new boolean[world.length][world[0].length];
        int num;
        for (int r = 0; r < world.length; r++) {
            for (int c = 0; c < world[0].length; c++) {
                newWorld[r][c] = nextCellState(world, r, c, stayAlive, birthNew);
            }
        }
        return newWorld;
    }

    public static boolean occupiedNext(int numNeighbors, boolean occupied) {
        if (occupied && (numNeighbors == 2 || numNeighbors == 3))
            return true;
        else if (!occupied && numNeighbors == 3)
            return true;
        else
            return false;
    }

    private static int numNeighbors(boolean[][] world, int row, int col) {
        int num = world[row][col] ? -1 : 0;
        for (int r = row - 1; r <= row + 1; r++)
            for (int c = col - 1; c <= col + 1; c++)
                if (inbounds(world, r, c) && world[r][c])
                    num++;

        return num;
    }

    private static boolean nextCellState(boolean[][] world, int row, int col, int[] stayAlive, int[] birthNew) {
        int count = numNeighbors(world, row, col);
        int rules[] = world[row][col] ? stayAlive : birthNew;
        for (int rule : rules) {
            if (rule == count) {
                return true;
            }
        }
        return false;
    }

    private static boolean inbounds(boolean[][] world, int r, int c) {
        return r >= 0 && r < world.length && c >= 0 &&
                c < world[0].length;
    }

    private static void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
