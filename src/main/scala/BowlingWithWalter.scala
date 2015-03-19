import scala.util.Random

/**
 * Date: 06.12.13
 * Time: 12:14
 */
object BowlingWithWalter {


  /**
   * The Dude: Walter, ya know, it's Smokey, so his toe slipped over the line a little, big deal. It's just a game, man.
   Walter Sobchak: Dude, this is a league game, this determines who enters the next round robin. Am I wrong? Am I wrong?
   Smokey: Yeah, but I wasn't over. Gimme the marker Dude, I'm marking it 8.
   Walter Sobchak: [pulls out a gun] Smokey, my friend, you are entering a world of pain.
   The Dude: Walter...
   Walter Sobchak: You mark that frame an 8, and you're entering a world of pain.
   Smokey: I'm not...
   Walter Sobchak: A world of pain.
   Smokey: Dude, he's your partner...
   Walter Sobchak: [shouting] Has the whole world gone crazy? Am I the only one around here who gives a shit about the rules? Mark it zero!
   The Dude: They're calling the cops, put the piece away.
   Walter Sobchak: Mark it zero!
   [points gun in Smokey's face]
   The Dude: Walter...
   Walter Sobchak: [shouting] You think I'm fucking around here? Mark it zero!
   Smokey: All right, it's fucking zero. Are you happy, you crazy fuck?
   Walter Sobchak: ...It's a league game, Smokey.
   */

  case class WorldOfPain(s: String)
  case class MarkItZero(s: String)

  def markItAnEight : Boolean = Random.nextBoolean()

  def bowlAndTouchLine : Either[WorldOfPain, MarkItZero] =
    if ( markItAnEight )
      Left(WorldOfPain("you are entering a world of pain."))
    else
      Right(MarkItZero("All right, it's fucking zero. Are you happy, you crazy fuck?"))

  def example = {
    println(
      """The Dude: Walter, ya know, it's Smokey, so his toe slipped over the line a little, big deal. It's just a game, man.
        |   Walter Sobchak: Dude, this is a league game, this determines who enters the next round robin. Am I wrong? Am I wrong?
        |   Smokey: Yeah, but I wasn't over. Gimme the marker Dude, I'm marking it 8.
        |   Walter Sobchak: [pulls out a gun] Smokey, my friend ...""".stripMargin)

    bowlAndTouchLine match {
      case Left(x) => println(x)
      case Right(MarkItZero(text)) => {
        println("Smokey: " + text)
        println( println("Walter Sobchak: ...It's a league game, Smokey.")
        )
      }
    }
  }



}
