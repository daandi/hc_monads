import scala.util.Random
import scala.io.Source

/**
 * AN-iT
 * Andreas Neumann
 * andreas.neumann@an-it.com
 * http://www.an-it.com
 * Date: 04.12.13
 * Time: 13:46
 */
object OptionExamples {

  val wikiByLanguage = Map(
    "de" -> "http://de.wikipedia.org/wiki",
    "en" -> "http://en.wikipedia.org/wiki"
  )

  val translation = Map(
    "Monade" -> Map("de" -> "Monade", "en" -> "monad"),
    "Erdferkel" -> Map("en" -> "Aardvark")
  )

  def getWikiTexts(lang: String, searchTerm: String) : Option[String] = for {
    url <- wikiByLanguage get lang
    translations <- translation get searchTerm
    translatedTerm <- translations get lang
    answer <- flakyConnection(s"$url/$translatedTerm")
  } yield answer

  def flakyConnection(url: String) : Option[String] =
    if( Random.nextBoolean() )
     Some( Source.fromURL(url,"UTF-8").getLines().mkString("").replaceAll("<[^>]+>","") )
    else
      None

  def makeHumanReadable(s : String) : String = s.replaceAll("<[^>]+>", "").replaceAll("\\s{2,}","")

  def example = for {
    search <- List("Erdferkel","Monade","Erdbeeren")
    language <- List("en","de")
    answer <- getWikiTexts(language,search)
  } yield answer take 50


}
