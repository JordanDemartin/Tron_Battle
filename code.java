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

        int x_joueur[] = new int[4];
        int y_joueur[] = new int[4];

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
            
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                map[X0][Y0] = ""+i;
                map[X1][Y1] = ""+i;
                x_joueur[i] = X1;
                y_joueur[i] = Y1;
            }
            for(int i = N; i < 4; i++){
                x_joueur[i] = -1;
                y_joueur[i] = -1;
            }
            
            for(int i = 0; i < 4; i++){
                System.err.println("tête du joueur "+ i +" : x="+x_joueur[i]+" y="+y_joueur[i]);
            }


            joueur_plus_proche(x_joueur, y_joueur, P);
            while(!direction_possilbe(map, direction, x_joueur[P], y_joueur[P])){

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

            affiche_map(map);
            
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

    private static int joueur_plus_proche(int[] x_joueur, int[] y_joueur, int num_joueur){
        
        int min = 400;
        int num_joueur_min = 0;

        for(int i = 0; i < 4; i++){
            if(x_joueur[i] != -1 && y_joueur[i] != -1 && i != num_joueur){

                int distance = Math.abs(x_joueur[i] - x_joueur[num_joueur]);
                distance += Math.abs(y_joueur[i] - y_joueur[num_joueur]);

                if(distance < min){
                    min = distance;
                    num_joueur_min = i;
                }

            }
        }
        
        System.err.println("min distance: le joueur "+num_joueur_min+" à "+min+" cases.");
        return num_joueur_min;
    }

    private static boolean direction_possilbe(String[][] map, String direction, int Xmoi, int Ymoi){

        //affiche_map(map);
        //System.err.println("direction : "+direction);
        //System.err.println("Xmoi : "+Xmoi);
        //System.err.println("Ymoi : "+Ymoi);

        if(direction == "LEFT"){
            if(Xmoi != 0){
                if(map[Xmoi-1][Ymoi] == "-"){
                    //System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    //System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                //System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        if(direction == "RIGHT"){
            if(Xmoi != 29){
                if(map[Xmoi+1][Ymoi] == "-"){
                    //System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    //System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                //System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        if(direction == "UP"){
            if(Ymoi != 0){
                if(map[Xmoi][Ymoi-1] == "-"){
                    //System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    //System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                //System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        if(direction == "DOWN"){
            if(Ymoi != 19){
                if(map[Xmoi][Ymoi+1] == "-"){
                    //System.err.println("je peux aller à " + direction);
                    return true;
                }else{
                    //System.err.println("je peux pas aller à " + direction);
                    return false;
            }
            }else{
                //System.err.println("je peux pas aller à " + direction);
                return false;
            }
        }

        return false;
    }

    private static int score(String[][] map_copie, int[] x_joueur, int[] y_joueur, int num_joueur, int N){
        String terminer[] = new String[N];
        int nb_case_controler[] = new int[N];

        for(int i = 0 ; i < N ; i++){
            terminer[i] = "-";
        }
        
        for(int i = 0; i < N; i++){
            if(x_joueur[i] == -1 || y_joueur[i] == -1){
                terminer[i] = "fin";
            }

            if(x_joueur[i] != 0){
                if( map_copie[x_joueur[i]-1][y_joueur[i]] == "-"){
                    map_copie[x_joueur[i]-1][y_joueur[i]] = "i";
                }
            }

            if(y_joueur[i] != 0){
                if( map_copie[x_joueur[i]][y_joueur[i]-1] == "-"){
                    map_copie[x_joueur[i]][y_joueur[i]-1] = "i";
                }
            }

            if(x_joueur[i] != 29){
                if( map_copie[x_joueur[i]+1][y_joueur[i]] == "-"){
                    map_copie[x_joueur[i]+1][y_joueur[i]] = "i";
                }
            }

            if(y_joueur[i] != 19){
                if( map_copie[x_joueur[i]][y_joueur[i]+1] == "-"){
                    map_copie[x_joueur[i]][y_joueur[i]+1] = "i";
                }
            }

        }
        return 0;
    }

    private static String[][] remplis_map_copie(String[][] map_copie, int x, int y, int numero_joueur){
        
        if(x != 0){
            if( map_copie[x-1][y] == "-"){
                map_copie[x-1][y] = "p" + numero_joueur;
                map_copie = remplis_map_copie(map_copie, x-1, y, numero_joueur);
            }
        }

        if(y != 0){
            if( map_copie[x][y-1] == "-"){
                map_copie[x][y-1] = "p" + numero_joueur;
                map_copie = remplis_map_copie(map_copie, x, y-1, numero_joueur);
            }
        }

        if(x != 29){
            if( map_copie[x+1][y] == "-"){
                map_copie[x+1][y] = "p" + numero_joueur;
                map_copie = remplis_map_copie(map_copie, x+1, y, numero_joueur);
            }
        }

        if(y != 19){
            if( map_copie[x][y+1] == "-"){
                map_copie[x][y+1] = "p" + numero_joueur;
                map_copie = remplis_map_copie(map_copie, x, y+1, numero_joueur);
            }
        }
        return map_copie;
    }
}
