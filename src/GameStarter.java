/**
 * GameStarter
 */
public class GameStarter {

    public void starterInstructions(String showOrNot) {
        String textStarter = """
                YOU ARE A FAMOUS HUNTER DESCENDING DOWN INTO THE CAVES OF DARKNESS,
                LAIR OF THE INFAMOUS MAN-EATING WUMPUS.  YOU ARE EQUIPPED WITH CROOKED
                ARROWS, AND ALL YOUR SENSES.  THERE ARE TWENTY CAVES CONNECTED
                BY TUNNELS, AND THERE ARE TWO OTHER KINDS OF HAZARDS:

                A) PITS, WHICH ARE BOTTOMLESS, AND USUALLY FATAL TO FALL
                        INTO.  THERE IS ONE SUCH PIT IN THE NETWORK.

                B) SUPER-BATS, WHICH IF YOU STUMBLE INTO THEIR ROOM WILL
                        PICK YOU UP AND DROP YOU IN SOME RANDOM ROOM IN THE NETWORK.
                        YOU MAY SHOOT SUPER-BATS, THERE IS ONE IN EACH OF THREE OR
                        FOUR ROOMS WITHIN THE NETWORK.  THE SUPER-BATS GENERALLY STAY
                        IN THEIR OWN ROOMS, EXCEPT WHEN DISPOSING OF INTRUDERS.

                IF YOU BLUNDER INTO THE SAME ROOM AS THE WUMPUS, YOU LOSE....
                THE NORMALLY SLEEPING WUMPUS DOES NOT MOVE (HAVING GORGED HIMSELF UPON
                A PREVIOUS HUNTER).  HOWEVER ONE THING CAN WAKE HIM UP, WALKING INTO HIS ROOM!
                THE WUMPUS IS TOO BIG TO BE PICKED UP BY SUPER-BATS AND HAS SUCKER FEET, SO HE DOESN'T
                FALL INTO THE PITS. YOU CAN SMELL THE WUMPUS FROM ONE ROOM AWAY. YOU CAN HEAR SUPER-BATS FROM
                ONE ROOM AWAY, AND FEEL BREEZES (FROM BOTTOMLESS PITS) FROM ONE ROOM AWAY.
                TO SHOOT AN ARROW TYPE ‘SHOOT' INSTEAD OF A MOVE, AND THEN
                SPECIFY WHICH ROOM THE ARROW SHOULD PASS THROUGH. YOU CAN ONLY SHOOT INTO THE NEXT ROOM.
                SPECIFY AN IMPOSSIBLE PATH AND THE ARROW WILL NOT SHOOT.
                EACH ROOM IS CONNECTED TO FOUR OTHER ROOMS BY THREE TUNNELS.
                YOU MUST ALWAYS MOVE BETWEEN ROOMS BY SPECIFYING WHICH
                TUNNEL YOU WISH TO EXPLORE.  YOU CAN ALWAYS RETRACE YOUR FOOT STEPS
                BY MOVING BACK USING THE SAME TUNNEL DESIGNATOR.

                GOOD LUCK HUNTING!!
                    """;
        String map = """
                                   __                                       
                           ...... /  \\                                      
                       .....     | 1  | ....                                
                .......           \\__/     ......                           
            ....                    .            ....                       
          ...                       .               ....                    
     __ ...                         .                  ...                  
    /  \\                           __                    ...                
   | 5  |                  __     /  \\                     ...              
  . \\__/ '.               /  \\ ..| 8  |......                 ...           
 ..        '.       __ ..| 7  |   \\__/       ... __             ...         
 .           '.___ /  \\.  \\__/                  /  \\              ...       
 .                | 6  |    ..                 | 9  |               ..      
 .                 \\__/      .                 .\\__..                ..     
.                  .         .               .'      ..               ..    
.                 .          '.__        __.'          .               ..   
.                ..           /  \\      /  \\           . __             ._  
.                .           | 17 |....| 18 |           /  \\           /  \\ 
.               ..            \\__/      \\__/.          | 10 |.........| 2  |
 .             ..             .             ..          \\__/           \\__/ 
 .             .__         __.'              .__          .             .   
 .             /  \\       /  \\               /  \\         .             .   
 .            | 15 |.....| 16 |             | 19 |        .             .   
 .             \\__/       \\__/       __     .\\__/.        .             .   
 ..              ..          .....  /  \\   ..     '.      .             .   
  .               ..             ..| 20 |...        '. __..            ..   
  .                .                \\__/              /  \\             .    
  ..               .                 .               | 11 |            .    
   .               ..                .                \\__/            .     
    .               .. __            .                .               .     
    .                ./  \\           .          __   ..              .      
     .               | 14 |          .         /  \\ ..              ..      
     .               .\\__/...        __       | 12 |               ..       
      .             .       ..      /  \\  .....\\__/.              ..        
       ..         .'         ......| 13 |..         .            ..         
         .       .                  \\__/             .          ..          
         .. __  .                                     .        ..           
          ./  \\.                                       . __  ...            
          | 4  |                                        /  \\..              
           \\__/........................................| 3  |               
                                                        \\__/                

                                """;
        
        if (showOrNot.equals("Y") || showOrNot.equals("y") || showOrNot.equals("Yes")) {
            System.out.println(textStarter);
            System.out.println(map);
        }

    }
}