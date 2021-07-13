import scala.collection.mutable.ArrayBuffer
import java.util.Scanner
object Elemental_Showdown {
  def main(args: Array[String]): Unit = {
    val PLAYER_HP = 20
    val scanner = new Scanner(System.in)

    print("Enter your name: ")
    var name = scanner.nextLine()
    print("\u001b[2J")
    scanner.close()

    val p1 = new Player(name, PLAYER_HP)
    val comp = new Player("Computer", PLAYER_HP)
    
    var winner = playGame(p1, comp)
  }

  def playGame(p1:Player, p2:Player):String = {
    var isSomeoneDead = false

    //TODO: CHECK TO SEE IF ANYONE IS DEAD
    while (!isSomeoneDead) {
      var playerChoice = getPlayerChoice(p1)
      var computerChoice = getComputerChoice()
      var dmg = determineDmg(playerChoice, computerChoice)

      if (dmg < 0) {
        p2.damage(0-dmg)
        damagePrint(computerChoice, playerChoice, p2, p1)
      }
      else if (dmg > 0) {
        p2.damage(dmg)
        damagePrint(playerChoice, computerChoice, p1, p2)
      }
      else {
        noDamagePrint(playerChoice)
      }
      //is anyone dead?
    }
    return "" // FIXME
  }

  // TODO: PRINT DAMAGE OUTPUT
  def damagePrint(losingElementInt:Int, winningElement:Int, loser:Player, winner:Player) {

  }

  // TODO: PRINT NODAMAGE OUTPUT
  def noDamagePrint(element:Int) {

  }

  def determineDmg(choice1:Int, choice2:Int):Int ={
  /*
    This function determines amount of damage / who gets damaged. If
    return value is negative, magnitude of damage goes against player 2.
  */
    var dmgDet = choice1 - choice2

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