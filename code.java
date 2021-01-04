import java.util.*;
import java.io.*;
import java.math.*;


class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int x_joueur[] = new int[4];
        int y_joueur[] = new int[4];
        boolean dead[] = new boolean[4];
        for(int i = 0; i < 4; i++){
            dead[i] = false;
        }

        char[][] map = Map_Fonctions.initialisation_map(30,20);

        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            String direction = "Oooooskour!";
            Noeud current = null;

            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                if(X0 != -1 && Y0 != -1 && X1 != -1 && Y1 != -1){ 
                    map[X0][Y0] = Character.forDigit(i,10);
                    map[X1][Y1] = Character.forDigit(i,10);
                }else{
                    if(!dead[i]){
                        Map_Fonctions.delete_player(map, Character.forDigit(i,10));
                        dead[i] = true;
                    }
                }
                x_joueur[i] = X1;
                y_joueur[i] = Y1;
            }
            for(int i = N; i < 4; i++){
                x_joueur[i] = -1;
                y_joueur[i] = -1;
                dead[i] = true;
            }

            //affiche la position de chaque joueur
            for(int i = 0; i < 4; i++){
                System.err.println("tête du joueur "+ i +" : x="+x_joueur[i]+" y="+y_joueur[i] + ", mort?"+dead[i]);
            }

            current = new Noeud( map, x_joueur, y_joueur, 10, P, x_joueur[P], y_joueur[P], 0);
               


            int score_max = -100000;
            
            int temp;

            if(current.gauche != null){
                temp = current.gauche.calcule_score();//score gauche

                if( temp > score_max ){
                    score_max = temp;
                    direction = "LEFT";
                }
            }

            if(current.droite != null){
                temp = current.droite.calcule_score();//score droite

                if( temp > score_max ){
                    score_max = temp;
                    direction = "RIGHT";
                }
            }

            if(current.haut != null){
                temp = current.haut.calcule_score();//score haut

                if( temp > score_max ){
                    score_max = temp;
                    direction = "UP";
                }
            }

            if(current.bas != null){
                temp = current.bas.calcule_score();//score bas

                if( temp > score_max ){
                    score_max = temp;
                    direction = "DOWN";
                }
            }
            
            //affiche la map
            Map_Fonctions.affiche_map(map);

            System.out.println(direction);
        }
    }

    

}

class Map_Fonctions{

    public static char[][] initialisation_map(int L, int H){
        char[][] map = new char[L][H];

        for(int i = 0 ; i < H ; i++){
            for(int j = 0 ; j < L ; j++){
                map[j][i] = '-';
            }
        }

        return map;
    }

    public static void affiche_map(char[][] map){
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                System.err.print(map[j][i] + " ");
            }
            System.err.println("");
        }
    }

    public static char[][] copy_map(char[][]map){
        char[][] copy = new char[30][20];

        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                copy[j][i] = map[j][i];
            }
        }

        return copy;
    }

    public static void delete_player(char[][]map, char player){
        for(int i = 0 ; i < 20 ; i++){
            for(int j = 0 ; j < 30 ; j++){
                if(map[j][i] == player){
                    map[j][i] = '-';
                }
            }
        }
    }

}

class Noeud{
    public Noeud gauche = null;
    public Noeud droite = null;
    public Noeud bas = null;
    public Noeud haut = null;
    
    private char[][] map = null;
    private int[] x_joueur = null;
    private int[] y_joueur = null;
    private int depth;
    private int joueur_concerner;
    private int tour;

    public Noeud(char[][] map, int[] x_joueur, int[] y_joueur, int depth, int joueur_concerner, int x_move, int y_move, int tour){
        if(tour == 0){
            this.map = Map_Fonctions.copy_map(map);
        }else{
            this.map = map;
        }

        this.x_joueur = x_joueur.clone();
        this.y_joueur = y_joueur.clone();
        this.depth = depth;
        this.joueur_concerner = joueur_concerner;
        this.tour = tour;
        
        this.map[x_move][y_move] = Character.forDigit(joueur_concerner,10);
        this.x_joueur[this.joueur_concerner] = x_move;
        this.y_joueur[this.joueur_concerner] = y_move;

        if(this.depth > 0){
            this.create_childs();
        }
    }

    public void create_childs(){
        int nouveau_joueur_concerner = this.joueur_concerner;

        if( this.x_joueur[nouveau_joueur_concerner] != 0 ){
            if( this.map[this.x_joueur[nouveau_joueur_concerner]-1][this.y_joueur[nouveau_joueur_concerner]] == '-'){
                this.gauche = new Noeud( this.map, this.x_joueur, this.y_joueur, this.depth-1, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner]-1, this.y_joueur[nouveau_joueur_concerner], this.tour+1);
            }
        }
        if( this.x_joueur[nouveau_joueur_concerner] != 29 ){
            if( this.map[this.x_joueur[nouveau_joueur_concerner]+1][this.y_joueur[nouveau_joueur_concerner]] == '-'){
                this.droite = new Noeud( this.map, this.x_joueur, this.y_joueur, this.depth-1, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner]+1, this.y_joueur[nouveau_joueur_concerner], this.tour+1);
            }
        }
        if( this.y_joueur[nouveau_joueur_concerner] != 19 ){
            if( this.map[this.x_joueur[nouveau_joueur_concerner]][this.y_joueur[nouveau_joueur_concerner]+1] == '-'){
                this.bas = new Noeud( this.map, this.x_joueur, this.y_joueur, this.depth-1, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner], this.y_joueur[nouveau_joueur_concerner]+1, this.tour+1);
            }
        }
        if( this.y_joueur[nouveau_joueur_concerner] != 0 ){
            if( this.map[this.x_joueur[nouveau_joueur_concerner]][this.y_joueur[nouveau_joueur_concerner]-1] == '-'){
                this.haut = new Noeud( this.map, this.x_joueur, this.y_joueur, this.depth-1, nouveau_joueur_concerner, this.x_joueur[nouveau_joueur_concerner], this.y_joueur[nouveau_joueur_concerner]-1, this.tour+1);
            }
        }
    }

    public int calcule_score(){
        int x_move = this.x_joueur[this.joueur_concerner];
        int y_move = this.y_joueur[this.joueur_concerner];

        if(x_move < 0 || y_move < 0 || x_move > 29 || y_move > 19){
            return -10000;
        }

        int score = 1;

        if(this.gauche!=null){
            score += gauche.calcule_score();
        }if(this.droite!=null){
            score += droite.calcule_score();
        }if(this.bas!=null){
            score += bas.calcule_score();
        }if(this.haut!=null){
            score += haut.calcule_score();
        }
        
        return score;
    }
}
