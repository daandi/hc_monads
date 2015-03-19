import scala.util.{Failure, Success, Random, Try}

/**
 * Date: 09.12.13
 * Time: 12:59
 */
object TryExample {

  case class Connection(toWhat: String) {
    def sendData(data: String): Try[String] = Try( send(data) )

    private def send(paylod: String) =
      if(Random.nextBoolean)
        s"Sent payload with ${paylod.size}"
      else
        throw new Exception("Couldn't send payload over Connection")
  }

  object Connection {
    def getConnection : Try[Connection] = Try ( connect )

    def connect =
      if(Random.nextBoolean)
        Connection("to the Internetz")
      else
        throw new Exception("Couldn't establish connection")
  }

  def sendHelloWorld : Try[String]  = for {
        connection <- Connection.getConnection
        r <- connection.sendData("Hallo Welt")
      } yield  r

  def example1 = (1 to 10) map (idx => s"$idx  $sendHelloWorld")

  def example2 = (1 to 10).map(_ => sendHelloWorld).zipWithIndex

}
