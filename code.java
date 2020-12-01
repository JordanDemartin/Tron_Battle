import java.util.*;
import java.io.*;
import java.math.*;
import java.util.Random;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Random r = new Random();

        String[][] map = new String[30][30];
        for(int i = 0 ; i < 30 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                map[j][i] = "-";
            }
        }
        
        String dont_direction = "LEFT";

        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            System.err.println("tous "+N+" moi "+P);
            int Xmoi = -1;
            int Ymoi = -1;
            String direction = "LEFT";
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                map[X1][Y1] = ""+i;
                if(i == P){
                    System.err.println("c'est moi" + i);
                    Xmoi = X0;
                    Ymoi = Y0;
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


            while(direction == dont_direction){
                
                int rand = r.nextInt(3);

                if(rand == 0){
                    direction = "RIGHT";
                }else if(rand == 1){
                    direction = "DOWN";
                }else if(rand == 2){
                    direction = "LEFT";
                }else if(rand == 3){
                    direction = "UP";
                }
            }
            
            System.err.println("direction : "+direction);
            if(direction == "LEFT"){
                dont_direction = "RIGHT";
            }else if(direction == "RIGHT"){
                dont_direction = "LEFT";
            }else if(direction == "DOWN"){
                dont_direction = "UP";
            }else if(direction == "UP"){
                dont_direction = "DOWN";
            }

            for(int i = 0 ; i < 30 ; i++){
                for(int j = 0 ; j < 30 ; j++){
                    System.err.print(map[j][i] + " ");
                }
                System.err.println("");
            }
            
            System.out.println(direction); // A single line with UP, DOWN, LEFT or RIGHT
        }
    }
}
