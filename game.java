

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class game {
    private final Integer[][] defaultPDat ={
            {},
            {0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0},
            {},
    };
    public game() {
    }

   public void startNewGame() {
       GameBoard gameBoard1 = new GameBoard(defaultPDat, 1);
   }

   public int loadGame(File file) throws FileNotFoundException {
       Scanner scanner = new Scanner(file);

       if ((scanner.nextLine()).equals("69420")){
           int currentplayer = Integer.parseInt(scanner.nextLine());
           Integer[][] pDat = defaultPDat;

           for (int y = 0; y < pDat.length; y++) {
               String temp = scanner.nextLine();
               String[] arrOfStr = temp.split(",");
               for (int x = 0; x < pDat[y].length; x++) {
                   pDat[y][x] = Integer.parseInt(arrOfStr[x]);
               }
           }

           GameBoard gameBoard = new GameBoard(pDat, currentplayer);
           scanner.close();
           return 0;
       }else {
           scanner.close();
           return 1;
       }
   }
}
