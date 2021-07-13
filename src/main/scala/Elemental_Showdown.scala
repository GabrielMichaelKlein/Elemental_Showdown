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

    while (true) {
      var playerChoice = getPlayerChoice(p1)
      var computerChoice = getComputerChoice()
      //determine who gets damaged / how much dmg
      //is anyone dead?
    }
    return ""
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