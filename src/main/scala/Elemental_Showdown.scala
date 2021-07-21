/**************************************
 * Gabe Klein
 * 7/21/21
 * p0 project
 * Elemental Showdown
 * 
 * This is a command line interactive fighting game
 * based on the Wuxing elements. It is much like
 * rock-paper-scissors where each of the elemenmts
 * will "beat" specific other elements every single
 * time. The user plays against a computer who makes
 * completely random moves.
 * ************************************/

import scala.collection.mutable.ArrayBuffer
import java.util.Scanner


object Elemental_Showdown {
  /*
  This is the object that holds the main method as well as many print methods
  for the game
  */

  val elements = Map(1->"Wood", 2->"Fire", 3->"Earth", 4->"Metal", 5->"Water") // For use with printing choices

  def main(args: Array[String]): Unit = {
    /*
    Main method
    */

    val PLAYER_HP = 20 // Can be changed if you want a shorter or longer game
    val scanner = new Scanner(System.in) 

    print("\u001b[2J") // This print statement clears the terminal
    print_showdown()
    println()
    
    print("Enter your name: ")
    var name = scanner.nextLine()
    print("\u001b[2J")

    val p1 = new Player(name, PLAYER_HP)
    val comp = new Player("Computer", PLAYER_HP)
    
    var winner = playGame(p1, comp) 
    
    println()
    if (winner == "-1") println("Game quit early.")
    else println(winner+ " is the WINNER!!!")
    println("(enter any key to exit)")
    scanner.nextLine()
  }

  def playGame(p1:Player, p2:Player):String = {
    /*
    This method controls the turns of the game.
    */
    var isSomeoneDead = false // Once a player dies, it's game over
    var winner = ""
    var playerChoice = 0
    var computerChoice = 0
    var dmg = 0

    while (!isSomeoneDead) {
      playerChoice = getPlayerChoice(p1)

      if (playerChoice == 6) { // If user wants to look at info for the elements
        info_page() 
      } 
      else if (playerChoice == 0) { // If user wishes to quit
        isSomeoneDead = true
        winner = "-1"
      }
      else {
        computerChoice = getComputerChoice()
        dmg = determineDmg(playerChoice, computerChoice)

        if (dmg < 0) {
          p2.damage(0-dmg)
          damagePrint(computerChoice, playerChoice, p2, p1, dmg.abs)
        }
        else if (dmg > 0) {
          p1.damage(dmg)
          damagePrint(playerChoice, computerChoice, p1, p2, dmg.abs)
        }
        else {
          noDamagePrint(playerChoice)
        }

        if (p1.getHP() == 0) { // Check if player is dead
          isSomeoneDead = true
          winner = p2.getName()
        }
        else if (p2.getHP() == 0) { // Check if computer is dead
          isSomeoneDead = true
          winner = p1.getName()
        }
      }
    }
    return winner // Returns name of winner. If user quits early, this returns "-1"
  }

  def info_page() {
    /*
    This method prints info about Wuxing and lets the user choose to get more info on each element.
    */
    val scanner = new Scanner(System.in)
    var choice = ""

    while (choice != "6") { // "6" goes back to game
      choice = ""
      while (choice != "1" && choice != "2" && choice != "3" && choice != "4" && choice != "5" && choice != "6") 
      {
        print("\u001b[2J")
        println("Wuxing, usually translated as Five Phases, is a fivefold conceptual scheme that many traditional Chinese fields used to explain a wide array of phenomena,")
        println("from cosmic cycles to the interaction between internal organs, and from the succession of political regimes to the properties of medicinal drugs. The \"Five Phases\" are Fire (huo), Water (shui),")
        println("Wood (mù), Metal or Gold (jin), and Earth or Soil (tu). This order of presentation is known as the \"Days of the Week\" sequence. In the order of \"mutual generation\" (xiangsheng), they are Wood,") 
        println("Fire, Earth, Metal, and Water. In the order of \"mutual overcoming\" (xiangkè), they are Wood, Earth, Water, Fire, and Metal.")
        println("The system of five phases was used for describing interactions and relationships between phenomena. After it came to maturity in the second or first century BCE during the Han dynasty, this device was employed in")
        println("many fields of early Chinese thought, including seemingly disparate fields such as Yi jing divination, alchemy, feng shui, astrology, traditional Chinese medicine, music, military strategy, and martial arts.")
        println("\nhttps://en.wikipedia.org/wiki/Wuxing_(Chinese_philosophy)  \n")
        println("Select an element to learn more:")
        println("    1) Wood\n    2) Fire\n    3) Earth\n    4) Metal\n    5) Water\n    6) GO BACK TO GAME")
        print("CHOICE: ")
        choice = scanner.nextLine()
      }

      if (choice != "6") {
        choice match {
          case "1" => info_wood()
          case "2" => info_fire()
          case "3" => info_earth()
          case "4" => info_metal()
          case "5" => info_water()
          case default => println()
        }
      }
    }
    
  }

  def info_wood() {
    /*
    Prints info on Wood
    */
    print("\u001b[2J")
    print_wood()
    println("WOOD associations:\n\nColor: Green\nSeason: Spring\nPlanet: Jupiter\nSymbol: Dragon\nClimate: Windy\n") // Add info about element
    println("Elemental interactions:\n\nFire BURNS Wood (-1 health)\nWood DEPLETES Earth (2 dmg)\nMetal CHOPS Wood (-2 health)\nWood DEPLETES water (1 dmg)\n")

    print("Type 'c' to continue: ")
    val scanner = new Scanner(System.in)
    var choice = ""

    while (choice != "c") choice = scanner.nextLine()

  }

  def info_fire() {
    /*
    Prints info on Fire
    */
    print("\u001b[2J")
    print_fire()
    println("FIRE associations:\n\nColor: Red\nSeason: Summer\nPlanet: Mars\nSymbol: Phoenix\nClimate: Hot\n") // Add info about element
    println("Elemental interactions:\n\nEarth SMOTHERS Fire (-1 health)\nFire MELTS Metal (2 dmg)\nWater EXTINGUISHES Fire (-2 health)\nFire BURNS Wood (1 dmg)\n")

    print("Type 'c' to continue: ")
    val scanner = new Scanner(System.in)
    var choice = ""

    while (choice != "c") choice = scanner.nextLine()
  }

  def info_earth() {
    /*
    Prints info on Earth
    */
    print("\u001b[2J")
    print_earth()
    println("EARTH associations:\n\nColor: Brown\nSeason: (Between Summer and Autumn)\nPlanet: Saturn\nSymbol: Cauldron\nClimate: Rainy\n") // Add info about element
    println("Elemental interactions:\n\nMetal MINES Earth (-1 health)\nEarth OBSTRUCTS Water (2 dmg)\nWood DEPLETES Earth (-2 health)\nEarth SMOTHERS Fire (1 dmg)\n")

    print("Type 'c' to continue: ")
    val scanner = new Scanner(System.in)
    var choice = ""

    while (choice != "c") choice = scanner.nextLine()
  }

  def info_metal() {
    /*
    Prints info on Metal
    */
    print("\u001b[2J")
    print_metal()
    println("METAL associations:\n\nColor: White\nSeason: Autumn\nPlanet: Venus\nSymbol: Tiger\nClimate: Dry\n") // Add info about element
    println("Elemental interactions:\n\nWater RUSTS Metal (-1 health)\nMetal CHOPS Wood (2 dmg)\nFire MELTS Metal (-2 health)\nMetal MINES Earth (1 dmg)\n")

    print("Type 'c' to continue: ")
    val scanner = new Scanner(System.in)
    var choice = ""

    while (choice != "c") choice = scanner.nextLine()
  }

  def info_water() {
    /*
    Prints info on Water
    */
    print("\u001b[2J")
    print_water()
    println("WATER associations:\n\nColor: Black\nSeason: Winter\nPlanet: Mercury\nSymbol: Turtle\nClimate: Cold\n") // Add info about element
    println("Elemental interactions:\n\nWood DEPLETES Water (-1 health)\nWater EXTINGUISHES Fire (2 dmg)\nEarth OBSTRUCTS Water (-2 health)\nWater RUSTS Metal (1 dmg)\n")

    print("Type 'c' to continue: ")
    val scanner = new Scanner(System.in)
    var choice = ""

    while (choice != "c") choice = scanner.nextLine()
  }

  def print_battle(winner:Int, loser:Int) {
    /*
    Prints the result of the battle, given player element and computer element.
    */
    if(winner == loser) {
      print_tie()
      return
    }
    winner match {
      case 1 => print_wood()
      case 2 => print_fire()
      case 3 => print_earth()
      case 4 => print_metal()
      case 5 => print_water()
      case default => println()
    }
    print_defeat()
    loser match {
      case 1 => print_wood()
      case 2 => print_fire()
      case 3 => print_earth()
      case 4 => print_metal()
      case 5 => print_water()
      case default => println()
    }
  }

  /************************************
   * The following methods print ASCII art that is used throughout the program
   * 
   * *********************************/
  def print_showdown() {
    print("      _                           _        _     _                      _                     \n     | |                         | |      | |   | |                    | |                    \n  ___| | ___ _ __ ___   ___ _ __ | |_ __ _| |___| |__   _____      ____| | _____      ___ __  \n")
    print(" / _ \\ |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __/ _` | / __| '_ \\ / _ \\ \\ /\\ / / _` |/ _ \\ \\ /\\ / / '_ \\ \n|  __/ |  __/ | | | | |  __/ | | | || (_| | \\__ \\ | | | (_) \\ V  V / (_| | (_) \\ V  V /| | | |\n")
    print(" \\___|_|\\___|_| |_| |_|\\___|_| |_|\\__\\__,_|_|___/_| |_|\\___/ \\_/\\_/ \\__,_|\\___/ \\_/\\_/ |_| |_|\n")
  }

  def print_wood() {
    println("   ________________________________ \n  |                                |")
    println("  |                  /--/          |\n  |                 /  /           |\n  |                / °/            |\n  |               /  /             |\n  |              /__/              |")
    
    println("  |                           _    |\n  |                          | |   |\n  |  __      _____   ___   __| |   |\n  |  \\ \\ /\\ / / _ \\ / _ \\ / _` |   |\n  |   \\ V  V / (_) | (_) | (_| |   |\n  |    \\_/\\_/ \\___/ \\___/ \\__,_|   |")
    println("  |                                |\n  |________________________________|")
  }

  def print_fire() {
    println("   ________________________________ \n  |                                |")
    println("  |              )\\                |\n  |            )( ( \\              |\n  |           /  )(  (             |\n  |           \\_(__)_/             |")
    println("  |         __ _                   |\n  |        / _(_)                  |\n  |       | |_ _ _ __ ___          |\n  |       |  _| | '__/ _ \\         |\n  |       | | | | | |  __/         |\n  |       |_| |_|_|  \\___|         |")
    println("  |                                |\n  |________________________________|")
  }

  def print_water() {
    println("   ________________________________ \n  |                                |")
    println("  |            ( ( (               |\n  |             ) ) )              |\n  |            ( ( (               |\n  |             ) ) )              |")
    println("  |                 _              |\n  |                | |             |\n  |  __      ____ _| |_ ___ _ __   |\n  |  \\ \\ /\\ / / _` | __/ _ \\ '__|  |\n  |   \\ V  V / (_| | ||  __/ |     |\n  |    \\_/\\_/ \\__,_|\\__\\___|_|     |")
    println("  |                                |\n  |________________________________|")
  }

  def print_metal() {
    println("   ________________________________ \n  |                                |")
    println("  |                /\\              |\n  |               / /              |\n  |              / /               |\n  |           __/ /__              |\n  |            /_/                 |")
    println("  |                  _        _    |\n  |                 | |      | |   |\n  |   _ __ ___   ___| |_ __ _| |   |\n  |  | '_ ` _ \\ / _ \\ __/ _` | |   |\n  |  | | | | | |  __/ || (_| | |   |\n  |  |_| |_| |_|\\___|\\__\\__,_|_|   |")
    println("  |                                |\n  |________________________________|")
  }

  def print_earth() {
    println("   ________________________________ \n  |                                |")
    println("  |               /\\               |\n  |              /  \\/\\            |\n  |             /\\   \\ \\           |\n  |            /__\\___\\_\\          |")
    println("  |                    _   _       |\n  |                   | | | |      |\n  |     ___  __ _ _ __| |_| |__    |\n  |    / _ \\/ _` | '__| __| '_ \\   |\n  |   |  __/ (_| | |  | |_| | | |  |\n  |    \\___|\\__,_|_|   \\__|_| |_|  |")
    println("  |                                |\n  |________________________________|")
  }

  def print_defeat() {
    println("           _       __           _       \n          | |     / _|         | |      \n        __| | ___| |_ ___  __ _| |_ ___ \n  ___  / _` |/ _ \\  _/ _ \\/ _` | __/ __|  ___\n /__/ | (_| |  __/ ||  __/ (_| | |_\\__ \\ /__/\n       \\__,_|\\___|_| \\___|\\__,_|\\__|___/\n")
  }

  def print_tie() {
    println("      _   _      \n     | | (_)     \n     | |_ _  ___ \n     | __| |/ _ \\\n     | |_| |  __/\n      \\__|_|\\___|\n")
  }

  def damagePrint(losingElementInt:Int, winningElement:Int, loser:Player, winner:Player, dmg:Int) {
    /*
    Prints when damage has been suffered
    */
    print_battle(winningElement, losingElementInt)
    println(winner.getName + " has damaged " + loser.getName + " by using " + elements(winningElement) + " against " + elements(losingElementInt))
    println(loser.getName()+" has suffered " + dmg + " damage! They now have " + loser.getHP + " health.")
  }

  def noDamagePrint(element:Int) {
    /*
    Prints when both players played the same element
    */
    print_tie()
    println("Both players used " + element + ". No damage given!")
  }

  def determineDmg(choice1:Int, choice2:Int):Int ={
  /*
    This function determines amount of damage / who gets damaged. If
    return value is negative, magnitude of damage goes against player 2.
    MAY WANT TO CHANGE FOR MORE VARIABILITY
  */
    var dmgDet = Math.floorMod(choice1 - choice2, 5)

    if (dmgDet == 0) return 0
    else if (dmgDet == 1) return -1
    else if (dmgDet == 4) return 1
    else if (dmgDet == 3) return -2
    else if (dmgDet == 2) return 2
    else return 0
  }

  // Returns a random element for the computer (1-5)
  def getComputerChoice():Int = return scala.util.Random.nextInt(5) + 1

  def getPlayerChoice(p1:Player):Int = {
    /*
    Asks for user input. Returns 0 if player wants to quit
    */
    val scanner = new Scanner(System.in)
    var choice = ""
    while (choice != "1" && choice != "2" && choice != "3" && choice != "4" && choice != "5" && choice != "6") 
    {
      println(p1.getName()+", select an element to attack with:")
      println("    1) Wood\n    2) Fire\n    3) Earth\n    4) Metal\n    5) Water\n    6) INFO ON THE ELEMENTS\n q) QUIT")
      print("CHOICE: ")
      choice = scanner.nextLine()
      if (choice == "q") return 0
    }
  
  print("\u001b[2J")
  return choice.toInt
  }

}

class Player(name:String, health:Int) {
  /*
  This class holds the blueplrint for player objects. Each object 
  stores a name and health. It is fully encapsulated
  */
  private var NAME = name
  private var HP = health

  // Getter method for name
  def getName():String = NAME

  // Getter method for health
  def getHP():Int = HP

  // Damages player and returns health
  def damage(dmg:Int):Int = {
    HP -= dmg
    if (HP < 0) HP = 0
    return HP
  }
}