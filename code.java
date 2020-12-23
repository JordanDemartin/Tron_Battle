import java.util.*;
import java.io.*;
import java.math.*;


class Player {

    

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int x_joueur[] = new int[4];
        int y_joueur[] = new int[4];

        char[][] map = initialisation_map(30,20);
        
        

        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            String direction = "Oooooskour!";

            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                map[X0][Y0] = Character.forDigit(i,10);
                map[X1][Y1] = Character.forDigit(i,10);
                x_joueur[i] = X1;
                y_joueur[i] = Y1;
            }
            for(int i = N; i < 4; i++){
                x_joueur[i] = -1;
                y_joueur[i] = -1;
            }

            //affiche la position de chaque joueur
            for(int i = 0; i < 4; i++){
                System.err.println("tête du joueur "+ i +" : x="+x_joueur[i]+" y="+y_joueur[i]);
            }

            int score_max = -1;
            
            int temp = score(map, x_joueur[P]-1, y_joueur[P]); //score gauche
            System.err.println("score gauche : " + temp);
            if( temp > score_max ){
                score_max = temp;
                direction = "LEFT";
            }
            temp = score(map, x_joueur[P]+1, y_joueur[P]); //score droite
            System.err.println("score droite : " + temp);
            if( temp > score_max ){
                score_max = temp;
                direction = "RIGHT";
            }
            temp = score(map, x_joueur[P], y_joueur[P]+1); //score bas
            System.err.println("score bas : " + temp);
            if( temp > score_max ){
                score_max = temp;
                direction = "DOWN";
            }
            temp = score(map, x_joueur[P], y_joueur[P]-1); //score haut
            System.err.println("score haut : " + temp);
            if( temp > score_max ){
                score_max = temp;
                direction = "UP";
            }
            
            affiche_map(map);

            System.out.println(direction);
        }
    }

    private static char[][] initialisation_map(int L, int H){
        char[][] map = new char[L][H];

        for(int i = 0 ; i < H ; i++){
            for(int j = 0 ; j < L ; j++){
                map[j][i] = '-';
            }
        }

        return map;
    }

    private static void affiche_map(char[][] map){
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                System.err.print(map[j][i] + " ");
            }
            System.err.println("");
        }
    }


    private static int score(char[][] map, int X, int Y){
        if(X < 0 || Y < 0 || X > 29 || Y > 19){
            return -1;
        }
        if(map[X][Y] != '-'){
            return -1;
        }
        int score = 0;
        if( X-1 >= 0 && map[X-1][Y] == '-' ){
            score++;
        }if( Y-1 >= 0 && map[X][Y-1] == '-' ){
            score++;
        }if( X+1 <= 29 && map[X+1][Y] == '-' ){
            score++;
        }if( Y+1 <= 19 && map[X][Y+1] == '-' ){
            score++;
        }
        return score;
    }

}

class Noeud{
    public Noeud gauche;
    public Noeud droite;
    public Noeud bas;
    public Noeud haut;
    
    public char[][] map;
    public int[] x_joueur;
    public int[] y_joueur;
    public int depth;
    public int joueur_concerner;
    public int score = -1;

    public Noeud(char[][] map, int[] x_joueur, int[] y_joueur, int depth, int joueur_concerner, int x_move, int y_move){
        this.map = map;
        this.x_joueur = x_joueur;
        this.y_joueur = y_joueur;
        this.depth = depth;
        this.joueur_concerner = joueur_concerner;

        this.map[x_move][y_move] = Character.forDigit(joueur_concerner,10);

        this.create_childs();
    }

    public void create_childs(){
        int nouveau_joueur_concerner = (this.joueur_concerner+1)%4;
        if( this.x_joueur[nouveau_joueur_concerner] != 0 ){
            if( map[this.x_joueur[nouveau_joueur_concerner]-1][this.y_joueur[nouveau_joueur_concerner]] == '-'){
                this.gauche = new Noeud( this.map.clone(), this.x_joueur, this.y_joueur, this.depth, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner]-1, this.y_joueur[nouveau_joueur_concerner]);
            }
        }
        if( this.y_joueur[nouveau_joueur_concerner] != 0 ){
            if( map[this.x_joueur[nouveau_joueur_concerner]][this.y_joueur[nouveau_joueur_concerner]-1] == '-'){
                this.haut = new Noeud( this.map.clone(), this.x_joueur, this.y_joueur, this.depth, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner], this.y_joueur[nouveau_joueur_concerner]-1);
            }
        }
        if( this.x_joueur[nouveau_joueur_concerner] != 29 ){
            if( map[this.x_joueur[nouveau_joueur_concerner]+1][this.y_joueur[nouveau_joueur_concerner]] == '-'){
                this.droite = new Noeud( this.map.clone(), this.x_joueur, this.y_joueur, this.depth, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner], this.y_joueur[nouveau_joueur_concerner]);
            }
        }
        if( this.y_joueur[nouveau_joueur_concerner] != 19 ){
            if( map[this.x_joueur[nouveau_joueur_concerner]+1][this.y_joueur[nouveau_joueur_concerner]] == '-'){
                this.bas = new Noeud( this.map.clone(), this.x_joueur, this.y_joueur, this.depth, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner], this.y_joueur[nouveau_joueur_concerner]-1);
            }
        }
    }

}
