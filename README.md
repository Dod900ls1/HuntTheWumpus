# Hunt the Wumpus

## Introduction

This project is a text-based implementation of the "Hunt the Wumpus" game. The game is set in a cave with interconnected rooms, where the player must navigate, avoid hazards like pits and bats, and ultimately find and defeat the Wumpus.

## Getting Started

To run the game, execute the `Main` class in your Java environment. The game will prompt you with instructions and guide you through the gameplay.

## Classes

### Main

The `Main` class serves as the entry point for the game. It initializes necessary objects like the player, Wumpus, bats, and the game itself. The main loop allows the player to make decisions such as moving or shooting an arrow. The game continues until the player either defeats the Wumpus, falls into a pit, runs out of arrows, or get defeated by Wumpus.

### Player

The `Player` class handles player-related actions, such as displaying the player's current location, checking proximity to hazards, and obtaining user input for movement or shooting arrows.

<details>
<summary> Player methods details </summary>


#### `getPlace`

- **Description:** Retrieves the current player's location.

- **Return:** The player's current location.

#### `setPlace`

- **Description:** Sets the current player's location.

- **Parameters:**
  - `playerLocation`: The new location for the player to move.

#### `locationOutput`

- **Description:** Displays the current player's location and available caves.

- **Parameters:**
  - `game`: The `Game` object to access `Game` class methods.

#### `isRightStep`

- **Description:** Checks if the step made by the player is valid.

- **Parameters:**
  - `input`: The value chosen by the player to move to.
  - `game`: The `Game` object needed to access `Game` class methods.

- **Return:** Whether the move made is valid or not.

#### `nextToWumpus`

- **Description:** Informs the player about the proximity of the Wumpus.

- **Parameters:**
  - `game`: The `Game` object needed to access `Game` class methods.
  - `wumpus`: The `Wumpus` object needed to access `Wumpus` class methods.

- **Return:** Whether the player is next to wumpus.

#### `nextToBats`

- **Description:** Informs the player about the proximity of bats.

- **Parameters:**
  - `game`: The `Game` object needed to access `Game` class methods.

- **Return:** Whether the player is next to bats.

#### `nextToPits`

- **Description:** Informs the player about the proximity of pits.

- **Parameters:**
  - `game`: The `Game` object needed to access `Game` class methods.

- **Return:** Whether the player is next to a pit.

#### `shootArrow`

- **Description:** Manages the player's arrow shooting mechanism. Handles shooting in adjacent caves, killing bats, checking for Wumpus, counting arrows, and scaring the Wumpus.

- **Parameters:**
  - `game`: The `Game` object needed to access `Game` class methods.
  - `wumpus`: The `Wumpus` object needed to access `Wumpus` class methods.

- **Return:** Boolean indicating whether the game continues.

</details>

### GameStarter

The `GameStarter` class provides initial instructions to the player based on their input. It helps set up the game environment.

<details>
<summary> GameStarter methods details </summary>

#### `starterInstructions`

- **Description:** Displays introductory instructions and a map for the game.

- **Parameters:**
  - `showOrNot`: A string indicating whether to show the instructions and map. (Accepts "Y", "y", "Yes" to show, and any other input to skip.)


</details>

### Wumpus

The `Wumpus` class represents the Wumpus character in the game. It includes methods to check if the player is adjacent to the Wumpus and handles the consequences of encountering the Wumpus.

<details>
<summary> Wumpus methods details </summary>

#### `getWumpusLoc`

- **Description:** Returns the location of the Wumpus.

- **Return:** The Wumpus location.

#### `setWumpusLoc`

- **Description:** Sets the location of the Wumpus.

- **Parameters:**
  - `wumpusLocation`: The generated location of the Wumpus.

#### `WumpusTrap`

- **Description:** Checks if the player entered the Wumpus cave. Terminate the program if true.

- **Parameters:**
  - `currentPlace`: The current location of the player.

- **Return:** Boolean indicating whether the game continues.

#### `scareWumpus`

- **Description:** If the player shoots an arrow next to a Wumpus cave but didn't kill it, the Wumpus relocates to another cave.

- **Parameters:**
  - `shot`: The location where the arrow was shot.
  - `currentPlace`: The current location of the player.
  - `game`: The `Game` object required to access `Game` class methods.

- **Return:** Boolean indicating whether the game continues.

</details>

### Bat

The `Bat` class manages interactions between the player and bats in the game. It includes a method:

<details>
<summary>Bat methods details</summary>

#### `batTrap`

- **Description:** Checks if the player encounters bats. If the bats lead the player to a pit or the Wumpus, the method terminates the game.

- **Parameters:**
  - `currentPlace`: The current location of the player.
  - `game`: The `Game` object required to access `Game` class methods.
  - `wumpus`: The `Wumpus` object required to access `Wumpus` class methods.
  - `player`: The `Player` object required to access `Player` class methods.

- **Return:** Boolean indicating whether the game continues.

#### `checkBatLocation`

- **Description:** Checks if the player is in the same location as bats.

- **Parameters:**
  - `currentPlace`: The current location of the player.
  - `game`: The `Game` object required to access `Game` class methods.

- **Return:** Boolean indicating whether the player encounters bats.

</details>

### Game

The `Game` class manages the game state, including the player's location, cave layout, and the Wumpus. It handles player movement, shooting arrows, and checks for various game conditions, such as falling into pits or encountering the Wumpus.

<details>
<summary> Game methods summary </summary>

#### `Constructor`

- **Description:** Initializes the game state, randomly generating cave locations for pits, bats, and the Wumpus.

#### `generateCaveConnections`

- **Description:** Defines cave connections forming a dodecahedron.

- **Return:** `HashMap<Integer, Integer[]>` representing nodes that can be accessed.

#### `generateRandomNumber`

- **Description:** Generates a random number between 1 and 20 (inclusive).

- **Return:** The random number.

#### `generateUniqueLocation`

- **Description:** Generates a unique location for game objects.

- **Return:** A unique location.

#### `generateUniqueLocations`

- **Description:** Generates a set of unique locations that have not been used in the game.

- **Parameters:**
  - `count`: The number of unique locations to generate.

- **Return:** An array of unique locations.

#### `getUserInput`

- **Description:** Gets user input for the next move.

- **Return:** The user input as a character ('S' for Shoot, 'W' for Walk).

#### `moveCave`

- **Description:** Moves to a new cave based on user input.

- **Parameters:**
  - `game`: The `Game` object needed to access `Game` class methods.
  - `player`: The `Player` object needed to access `Player` class methods.

#### `pitTrap`

- **Description:** Checks if the player falls into a pit trap.

- **Parameters:**
  - `currentPlace`: The current location of the player.

- **Return:** Boolean indicating whether the game is still running.

#### `getPits`

- **Description:** Returns the individual location of a pit.

- **Parameters:**
  - `index`: Used to indicate the location to find the exact needed coordinate.

- **Return:** The individual pit location.

#### `getPitsArr`

- **Description:** Returns an array of locations of pits.

- **Return:** The array of pit locations.

#### `getBats`

- **Description:** Returns the individual location of a bat.

- **Parameters:**
  - `index`: Used to indicate the location to find the exact needed coordinate.

- **Return:** The individual bat location.

#### `getBatsArr`

- **Description:** Returns an array of locations of bats.

- **Return:** The array of bat locations.

#### `setBats`

- **Description:** Sets the location of an individual bat.

- **Parameters:**
  - `index`: Used to indicate the location to find the exact needed coordinate.
  - `newVal`: The new location value for bats.

</details>

### Unit Tests

The `Tests` class contains several unit tests to ensure the correct functionality of the classes in the "Hunt the Wumpus" game. Here is a brief description of each test:

<details>
<summary> Tests description </summary>

#### `testKillWumpus`

- **Description:** Checks if the player successfully kills the Wumpus. The test sets up the Wumpus location and invokes the `killWumpus` method to validate the game outcome.

#### `testDontKillWumpus`

- **Description:** Checks if the player fails to kill the Wumpus. The test sets up the Wumpus location and invokes the `killWumpus` method with a different location to validate the game outcome.

#### `testKillBat`

- **Description:** Checks if the player successfully kills a bat. The test sets up the bat locations and invokes the `killBat` method to verify that at least one bat is killed.

#### `playerFinishItsArrows`

- **Description:** Checks if the game recognizes when the player finishes all arrows. The test sets up the player's remaining arrows and invokes the `arrowCounter` method to validate the game outcome.

#### `testScareWumpus`

- **Description:** Checks if the Wumpus relocates after the player shoots an arrow next to it. The test sets up the Wumpus location and invokes the `scareWumpus` method to validate the Wumpus relocation.

#### `testWalking_ValidUserInput`

- **Description:** Checks if the player can successfully move to an accessible cave. The test sets up the player's current location and invokes the `isRightStep` method to validate a valid move.

#### `testWalking_InvalidUserInput`

- **Description:** Checks if the player cannot move to an inaccessible cave. The test sets up the player's current location and invokes the `isRightStep` method to validate an invalid move.

#### `testPitTrap_PlayerFallsInPit`

- **Description:** Checks if the player loses the game when falling into a pit. The test sets up the pit locations and invokes the `pitTrap` method to validate the game outcome.

#### `testPitTrap_PlayerDoesNotFallInPit`

- **Description:** Checks if the player does not lose the game when not falling into a pit. The test sets up the pit locations and invokes the `pitTrap` method to validate the game outcome.

#### `testWumpusTrap_PlayerEntersWumpus`

- **Description:** Checks if the player loses the game when entering the Wumpus cave. The test sets up the Wumpus location and invokes the `WumpusTrap` method to validate the game outcome.

</details>


## Instructions

Upon starting the game, you will be prompted to see instructions. Enter 'Y' for yes or 'N' for no. Follow the on-screen prompts to navigate the cave, avoid hazards, and defeat the Wumpus.

Have fun playing "Hunt the Wumpus"!
