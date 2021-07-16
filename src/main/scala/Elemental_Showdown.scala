// TODO: Add flair (ASCII art), different damage values, potential GUI (Swing?)

import scala.collection.mutable.ArrayBuffer
import java.util.Scanner
object Elemental_Showdown {
  val elements = Map(1->"Wood", 2->"Fire", 3->"Earth", 4->"Metal", 5->"Water")
  def main(args: Array[String]): Unit = {
    val PLAYER_HP = 20
    val scanner = new Scanner(System.in)

    print("Enter your name: ")

    // FOR TESTING PRINTS OF ASCII
    //println()
    //print_defeat()


    var name = scanner.nextLine()
    print("\u001b[2J")
    //scanner.close()

    val p1 = new Player(name, PLAYER_HP)
    val comp = new Player("Computer", PLAYER_HP)
    
    var winner = playGame(p1, comp)
    println()
    println(winner+ " is the WINNER!!!")
  }

  def playGame(p1:Player, p2:Player):String = {
    var isSomeoneDead = false
    var winner = ""
    var playerChoice = 0
    var computerChoice = 0
    var dmg = 0
    //TODO: CHECK TO SEE IF ANYONE IS DEAD
    while (!isSomeoneDead) {
      playerChoice = getPlayerChoice(p1)
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
        //println(playerChoice + " " + computerChoice)
        noDamagePrint(playerChoice)
      }
      //is anyone dead?
      if (p1.getHP() == 0) {
        isSomeoneDead = true
        winner = p2.getName()
      }
      else if (p2.getHP() == 0) {
        isSomeoneDead = true
        winner = p1.getName()
      }

    }
    return winner // FIXME
  }

  def print_battle(winner:Int, loser:Int) {
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

  def print_wood() {
    println("                  /--/\n                 /  /\n                / °/\n               /  /\n              /__/")
    println("                              _\n                             | |\n     __      _____   ___   __| |\n     \\ \\ /\\ / / _ \\ / _ \\ / _` |\n      \\ V  V / (_) | (_) | (_| |\n       \\_/\\_/ \\___/ \\___/ \\__,_|")

  }

  def print_fire() {
    println("            )\\ \n          )( ( \\\n         /  )(  (\n         \\_(__)_/\n")
    println("       __ _          \n      / _(_)         \n     | |_ _ _ __ ___ \n     |  _| | '__/ _ \\\n     | | | | | |  __/\n     |_| |_|_|  \\___|\n")
  }

  def print_water() {
    println("               ( ( (\n                ) ) )\n               ( ( (\n                ) ) ) \n")
    println("                    _            \n                   | |           \n     __      ____ _| |_ ___ _ __ \n     \\ \\ /\\ / / _` | __/ _ \\ '__|\n      \\ V  V / (_| | ||  __/ |   \n       \\_/\\_/ \\__,_|\\__\\___|_|   \n")
  }

  def print_metal() {
    println("                   /\\\n                  / /\n                 / / \n              __/ /__\n               /_/   \n")
    println("                     _        _ \n                    | |      | |\n      _ __ ___   ___| |_ __ _| |\n     | '_ ` _ \\ / _ \\ __/ _` | |\n     | | | | | |  __/ || (_| | |\n     |_| |_| |_|\\___|\\__\\__,_|_|\n")
  }

  def print_earth() {
    println("               /\\\n              /  \\/\\ \n             /\\   \\ \\\n            /__\\___\\_\\\n")
    println("                      _   _     \n                     | | | |    \n       ___  __ _ _ __| |_| |__  \n      / _ \\/ _` | '__| __| '_ \\ \n     |  __/ (_| | |  | |_| | | |\n      \\___|\\__,_|_|   \\__|_| |_|\n")
  }

  def print_defeat() {
    println("           _       __           _       \n          | |     / _|         | |      \n        __| | ___| |_ ___  __ _| |_ ___ \n  ___  / _` |/ _ \\  _/ _ \\/ _` | __/ __|  ___\n /__/ | (_| |  __/ ||  __/ (_| | |_\\__ \\ /__/\n       \\__,_|\\___|_| \\___|\\__,_|\\__|___/\n")
  }

  def print_tie() {
    println("      _   _      \n     | | (_)     \n     | |_ _  ___ \n     | __| |/ _ \\\n     | |_| |  __/\n      \\__|_|\\___|\n")
  }

  // TODO: UPDATE PRINT STATEMENT TO ADD FLAIR
  def damagePrint(losingElementInt:Int, winningElement:Int, loser:Player, winner:Player, dmg:Int) {
    print_battle(winningElement, losingElementInt)
    println(winner.getName + " has damaged " + loser.getName + " by using " + elements(winningElement) + " against " + elements(losingElementInt))
    println(loser.getName()+" has suffered " + dmg + " damage! They now have " + loser.getHP + " health.")
  }

  // TODO: UPDATE FOR FLAIR
  def noDamagePrint(element:Int) {
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

  def getComputerChoice():Int = {
    return scala.util.Random.nextInt(5) + 1
  }

  def getPlayerChoice(p1:Player):Int = {
    val scanner = new Scanner(System.in)
    var choice = ""
    while (choice != "1" && choice != "2" && choice != "3" && choice != "4" && choice != "5") 
    {
      println(p1.getName()+", select an element to attack with:")
      println("    1) Wood\n    2) Fire\n    3) Earth\n    4) Metal\n    5) Water")
      print("CHOICE: ")
      choice = scanner.nextLine()
    }
  
  print("\u001b[2J")
  return choice.toInt
  }

}

class Player(name:String, health:Int) {
  private var NAME = name
  private var HP = health
  //private val movesMade = ArrayBuffer[String]()

  def getName():String = NAME

  def getHP():Int = HP

  def damage(dmg:Int):Int = {
    HP -= dmg
    if (HP < 0) HP = 0
    return HP
  }
}