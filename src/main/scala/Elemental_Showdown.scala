import scala.collection.mutable.ArrayBuffer

object Elemental_Showdown {
  def main(args: Array[String]): Unit = {
    

    


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