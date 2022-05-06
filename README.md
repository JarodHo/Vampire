# Vampire

![titlescreen](https://user-images.githubusercontent.com/70664893/167162532-74260670-b54a-47e2-a41a-bf5897715e18.png)

![instructions](https://user-images.githubusercontent.com/70664893/167162627-0164c3c2-74bf-4380-8fc3-df2340b426d6.png)

![powerups](https://user-images.githubusercontent.com/70664893/167162848-7e3e914a-8ff3-455d-b412-15161f80e05f.png)

![gaemplay](https://github.com/JarodHo/Vampire/blob/master/gameplay%20(2).gif)

## Overview
The overall goal of the Space Invader project was to recreate a version of the traditional arcade game **Space Invaders** in the programming language **Java**. 
Although this version doesn't contain all the parts of the original game, additional features were added to keep the game from being too difficult or easy.

## Key Classes
The main classes used in this program are:
* Alien.java
  * Creates the Alien object, plays a default animation when alive, stays on screen to play death animation when killed.
* Bullet.java
  * Creates the Bullet object, moves up the screen vertically from the initialized location.
* Frame.java
  * Overall Runner class, handles user input and calls the paint functions and the collision checks for each object along with handling the current game state.
* HealPack.java
  * Creates the Healpack object that allows the player to increase their health.
* Health.java
  * Creates the Health object that keeps track of the player's health and updates the UI accordingly.
* Music.java
  * Creates the Music object that plays sound effects and the background music.
* Nuke.java
  * Creates the Nuke object that allows the player to wipe out a large amount of aliens at once.
* Player.java
  * Creates the Player object that the player controls throughout the game.
* Wall.java
  * Creates the Wall object that blocks bullets coming from either direction and changes the sprite image depending on how much health it has remaining. 
  
## Expanding on the Project
While this project is open publically to be built upon, please create a new branch in order to change the contents of the original project. This can be done by clicking the button labeled "master" at the top of the main page and typing in a unique name for the branch. 
