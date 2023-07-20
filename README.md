# Project 5 - Graphical Adventure Game

## Overview
According to Wikipedia in their description of an adventure game, "the player takes on the role of the protagonist in an interactive story driven by exploration and/or puzzle-solving". In a text-based (Links to an external site.) game, the way the game is played is through printable text data, usually through a console.

A design and implementation of a model for an adventure game that will allow a player to enter a preconstructed dungeon with caves and tunnels for finding the goal, given they arrive at the dungeon at some random starting position. The world for our game consists of a dungeon, a network of tunnels, and caves that are interconnected so that players can explore the entire world by traveling from cave to cave through the tunnels that connect them. The winner of this game is if the players reach the goal position, collecting treasures along the way.

## List of features
- A dungeon is represented by an m x n two-dimensional grid

- Each location in the grid represents a location in the dungeon where a player can explore and can be connected to at most four (4) other locations:

    - one to the north
    - one to the east
    - one to the south
    - and one to the west

- A location can further be classified as:

    - tunnel (which has exactly 2 entrances)
    - cave (which has 1, 3, or 4 entrances)

- the dungeons are generated at random following some set of constraints resulting in a different network each time the game begins. Some of the constraints include:

    - the dungeon is represented on a 2-D grid
    - there is a path from every cave in the dungeon to every other cave in the dungeon
    - each dungeon is constructed with a degree of interconnectivity
    - increasing the degree of interconnectivity increases the number of paths between caves
    - not all dungeons "wrap" from one side to the other
    - one cave is randomly selected as the start and one cave is randomly selected to be the end
    - the path between the start and the end locations is at least of length 5

Otyughs are extremely smelly creatures that lead solitary lives in the deep, dark places of the world like the dungeon.
- There is always at least one Otyugh in the dungeon located at the specially designated end cave. The actual number is specified on the command line. There is never an Otyugh at the start.
- Otyugh only occupy caves and are never found in tunnels. Their caves can also contain treasure or other items.
- They can be detected by their smell. In general, the player can detect two levels of smell:
  - a less pungent smell can be detected when there is a single Otyugh 2 positions from the player's current location
  - detecting a more pungent smell either means that there is a single Otyugh 1 position from the player's current location or that there are multiple Otyughs within 2 positions from the player's current location
- They are adapted to eat whatever organic material that they can find but love it when fresh meat happens into the cave in which they dwell. This means that a player entering a cave with an Otyugh that has not been slain will be killed and eaten.

To give our players the ability to slay the Otyugh, they will automatically be equipped with a bow that uses crooked arrows.
- The player starts with 3 crooked arrows but can find additional arrows in the dungeon with the same frequency as treasure. Arrows and treasure can be, but are not always, found together. Furthermore, arrows can be found in both caves and tunnels.
- A player that has arrows, can attempt to slay an Otyugh by specifying a direction and distance in which to shoot their crooked arrow. Distance is defined as the number of caves (but not tunnels) that an arrow travels. Arrows travel freely down tunnels (even crooked ones) but only travel in a straight line through a cave. For example,
  - a tunnel that has exits to the west and south can have an arrow enter the tunnel from the west and exit the tunnel to the south, or vice-versa (this is the reason the arrow is called a crooked arrow)
  - a cave that has exits to the east, south, and west will allow an arrow to enter from the east and exit to the west, or vice-versa; but an arrow that enters from the south would be stopped since there is no exit to the north
- Distances must be exact. For example, if an arrow is shot at a distance of 3 to the east and the Otyugh is at a distance of 2, the arrow misses the Otyugh.
- It takes 2 hits to kill an Otyugh. Players have a 50% chance of escaping the Otyugh if they enter a cave of an injured Otyugh that has been hit by a single crooked arrow.

We also have the option to play the game using an interactive graphical user interface (GUI). This GUI:

- exposes all game settings including the size of the dungeon, the interconnectivity, whether it is wrapping or not, the percentage of caves that have treasure, and the number of Otyughs through one or more items on a menu
- provides an option for quitting the game, restarting the game as a new game with a new dungeon, or reusing the same dungeon through one or more items on a menu
- displays the dungeon to the screen using a graphical representation. The view begins with a mostly blank screen and displays only the pieces of the maze that have been revealed by the user's exploration of the caves and tunnels. Dungeons that are bigger than the area allocated to the screen should provide the ability to scroll the view.
- allows the player to move through the dungeon using a mouse click on the screen in addition to the keyboard arrow keys. A click on an invalid space in the game would not advance the player
- displays the details of a dungeon's location on the screen. For instance, does it have treasure, does it have an arrow, does it smell
- provides an option to get the player's description
- provides an option for the player to pick up a treasure or an arrow by pressing a key on the keyboard.
- provides an option for the player to shoot an arrow by pressing a key on the keyboard followed by an arrow key to indicate the direction
- provides a clear indication of the results of each action a player takes

**Introducing new update**:
- a pit that the player could fall into with an indicator that a pit is nearby
- another monster that roams around the dungeon but requires the player to battle in hand-to-hand combat
- thief that steals treasure from the player whenever they are encountered

## How To Run
You can run the precompiled JAR file by entering the following command line:
```
java -jar <path-to-jar>/<jar-file-name>.jar
```
Suppose, in the current directory with the JAR file as "project_4_text_based_adventure_game.jar", you can enter:

**For GUI mode:**
```
java -jar project_5_graphical_adventure_game.jar
```
**For command-line mode:**
```
java -jar project_5_graphical_adventure_game.jar --args
```
This application requires Java 11 and JUnit4.

## How to Use the Program
**In GUI mode**

- **Up arrow**: Move up
- **Down arrow**: Move down
- **Left arrow**: Move left
- **Right arrow**: Move right
- **R**: Reset player
- **P**: Initiate pickup
  - **T**: Pickup treasure
  - **W**: Pickup weapon
- **S**: Initiate shoot
  - **Up arrow**: Shoot up
  - **Down arrow**: Shoot down
  - **Left arrow**: Shoot left
  - **Right arrow**: Shoot right
    - Set power in slider and click **Ok**
- **Q**: Quit game

**In command-line mode**

Upon starting, the application will prompt for a few inputs:
- **Enter the row of the dungeon:** Enter the height/number of rows in the dungeon
- **Enter the col of the dungeon:** Enter the width/number of columns in the dungeon
- **Enter the interconnectivity of the dungeon:** Enter the degree of interconnectivity in the dungeon
- **Enter whether the dungeon is wrapping (Y/N):** Enter whether the dungeon will be wrapping (Y for wrapping, N otherwise). Invalid values will cause the program to assume non-wrapping behavior.

A bare minimum dungeon will be created at this stage. Furthermore:
- **Enter the percentage of caves treasure will be added to:** Enter the percentage of caves to have treasure in them
- **Enter the percentage of caves monsters will be added to:** Enter the percentage of caves to have monsters in them
- **Enter the percentage of caves weapons will be added to:** Enter the percentage of caves to have weapons in them

At this stage, the program will resume showing relevant information regarding the dungeon and start:
- **To receive input** from the user to
  - **navigate** the player through the dungeon.
  - **pick up** treasure and/or arrows if they are found in the same location as the player
  - **shoot** an arrow in a given direction
- Output clues about the nearby caves and other relevant aspects of the current game state to the screen
The program will terminate when the player reaches the goal location or gets killed, otherwise will show meaningful error messages accordingly.

## Description of Examples
The screenshot provided shows the example run where the player is exploring the cave. It has an Otyugh, thus the red blood splatter. We also see two thieves and one moving monster, Beholder on the screen. The bottom panel provides insight into the game setting that this run is on. The right panel is divided into three sections:
- Top part shows the player's health, player treasure content, and player weapon content
- Middle part shows the treasures available at the current location
- Bottom part shows the weapons available at the current location

## Design Changes
Not many major changes were required to be made to the original design, except for:
- Introduction of:
  - GUI modules
  - Pits as enums
  - Thieves as enums
  - EnhancedController interface and GuiConsole classes
  - New monster and weapon-related methods such as getters, setters, add, remove, etc.

## Assumptions
- The scope of the project will not change throughout the life cycle of this application

## Limitations
Since the project uses random values to initialize its attributes, it uses secondary, more traditional method signatures to test the functionality; albeit trivial, it performs effectively on all the tests, but limiting the tests to only predefined values and no way to test for random generations.
Also, the project uses Swing, which has its own limitations in consistency as well.

## Citations
- PNG Repo - Free Quality PNG Icons and Graphics. (n.d.). Www.pngrepo.com. https://www.pngrepo.com/
- Kaleido. (n.d.). Remove Background from Image. Remove.bg. Retrieved December 10, 2021, from https://remove.bg
- Online PNG Tools - Simple, free, and easy-to-use PNG utilities. (n.d.). Onlinepngtools.com. Retrieved December 10, 2021, from https://onlinepngtools.com/
- MyBib Contributors. (2019, May 26). APA Citation Generator – FREE & Fast – (6th Edition, 2019). MyBib. https://www.mybib.com/tools/apa-citation-generator
