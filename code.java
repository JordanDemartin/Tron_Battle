import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        String[][] map = new String[30][20];
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                map[j][i] = "-";
            }
        }
        
        String direction = "LEFT";

        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            System.err.println("tous "+N+" moi "+P);
            int Xmoi = -1;
            int Ymoi = -1;
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                map[X1][Y1] = ""+i;
                if(i == P){
                    System.err.println("c'est moi" + i);
                    Xmoi = X1;
                    Ymoi = Y1;
                    System.err.println("0:"+X0+" "+Y0);
                    System.err.println("1:"+X1+" "+Y1);
                }
                else{
                    System.err.println("c'est pas moi" + i);
                    System.err.println("0:"+X0+" "+Y0);
                    System.err.println("1:"+X1+" "+Y1);
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            while(!direction_possilbe(map, direction, Xmoi, Ymoi)){

                if(direction == "LEFT"){
                    direction = "RIGHT";
                }else if(direction == "RIGHT"){
                    direction = "DOWN";
                }else if(direction == "DOWN"){
                    direction = "UP";
                }else if(direction == "UP"){
                    direction = "LEFT";
                }
                
            }
            
            //System.err.println("direction : "+direction);

            //affiche_map(map);
            
            System.out.println(direction); // A single line with UP, DOWN, LEFT or RIGHT
        }
    }

    private static void affiche_map(String[][] map){
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                System.err.print(map[j][i] + " ");
            }
            System.err.println("");
        }
    }

    private static boolean direction_possilbe(String[][] map, String direction, int Xmoi, int Ymoi){

        affiche_map(map);
        System.err.println("direction : "+direction);
        System.err.println("Xmoi : "+Xmoi);
        System.err.println("Ymoi : "+Ymoi);

        if(direction == "LEFT"){
            if(Xmoi != 0){
                if(map[Xmoi-1][Ymoi] == "-"){
                    System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        if(direction == "RIGHT"){
            if(Xmoi != 29){
                if(map[Xmoi+1][Ymoi] == "-"){
                    System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        if(direction == "UP"){
            if(Ymoi != 0){
                if(map[Xmoi][Ymoi-1] == "-"){
                    System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        if(direction == "DOWN"){
            if(Ymoi != 19){
                if(map[Xmoi][Ymoi+1] == "-"){
                    System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        return false;
    }
}

class Choix_Direction {

}
