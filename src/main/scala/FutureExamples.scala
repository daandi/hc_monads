import scala.util.{Failure, Success, Random}
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

/**
 * Date: 10.12.13
 * Time: 11:25
 */
object FutureExamples {

  object creatingFuture {
    val h = Future("Hello World!")
    Future {
      "Hello World!"
    }
    // h: scala.concurrent.Future[String] = scala.concurrent.impl.Promise$DefaultPromise@2 caa89b0

    h.isCompleted
    //res8: Boolean = true

    h.value
    //res9: Option[scala.util.Try[String]] = Some(Success(Hello World !))

    h.value.get.get //danger lies here
    // String = Hello BOT
  }

  object promiseExample {
    val p = Promise[String]
    // p: scala.concurrent.Promise[String] = scala.concurrent.impl.Promise$DefaultPromise@37 cf00f1

    p.success("Welcome to the World of Tomorrow!")
    // res4: p.type = scala.concurrent.impl.Promise$DefaultPromise@37 cf00f1

    p.success("!!!")
    //java.lang.IllegalStateException: Promise already completed.
    //at scala.concurrent.Promise$class.complete(Promise.scala: 55)


    val p2 = Promise[String]
    // p2: scala.concurrent.Promise[String] = scala.concurrent.impl.Promise$DefaultPromise@612 c4f6d

    p2.failure(new Exception("Cryogenic Error"))
    // res7: p2.type = scala.concurrent.impl.Promise$DefaultPromise@612 c4f6d

    p.future
    // res11: scala.concurrent.Future[String] = scala.concurrent.impl.Promise$DefaultPromise@37 cf00f1

    p2.future
    // res12: scala.concurrent.Future[String] = scala.concurrent.impl.Promise$DefaultPromise@612 c4f6d

    p.future.value
    // res17: Option[scala.util.Try[String]] = Some(Success(Welcome to the World of Tomorrow !))

    p2.future.value
    // res15: Option[scala.util.Try[String]] = Some(Failure(java.lang.Exception: Cryogenic Error))

  }

  object easyExmples {
    val f = futureThatTakes(10)("Nach zehn Sekunden")
    // f: scala.concurrent.Future[String] = scala.concurrent.impl.Promise$DefaultPromise@4 a64534b

    // 5 Sekunden

    f.isCompleted
    // res0: Boolean = false
    f.value
    // res1: Option[scala.util.Try[String]] = None

    // 11 Sekunden

    f.isCompleted
    // res3: Boolean = true
    f.value
    //res4: Option[scala.util.Try[String]] = Some(Success(Nach zehn Sekunden))


    // Enough time

    Await.result(
      futureThatTakes(10)("Ich brauche zehn Sekunden."),
      10 seconds
    )
    //res6: String = Ich brauche zehn Sekunden.


    // Not enough Time

    Await.result(
      futureThatTakes(10)("Ich brauche zehn Sekunden."),
      5 seconds)
    //java.util.concurrent.TimeoutException: Futures timed out after[5 seconds]
    //at scala.concurrent.impl.Promise$DefaultPromise.ready(Promise.scala: 219)


  }

  val failedFuture: Future[Int] = Promise[Int].failure(  new Exception("I am ERROR") ).future

  def futureThatTakes[T](secondsToRun: Int)(resultAfter: T): Future[T] = Future {
    blocking( Thread.sleep( secondsToRun * 1000))
    resultAfter
  }

  def futureInt : Future[Int] = Future( Random.nextInt() )

  def getFutureIntInt = {
    val i = futureInt

    Await.result(i, 1 second)
  }

  def combineFuture = {
    val aF = futureInt
    val bF = futureInt

    for ( a <- aF ; b <- bF ) yield a.toDouble / b

  }


  def printFuture[T](f: Future[T]) = f onComplete {
  	case Success(t) => println(t)
  	case Failure(f) => throw f
  }


  def callbackExample = {
    val a1 = futureThatTakes(10)("Ich brauche zehn Sekunden.")
    val a2 = futureThatTakes(5)("Ich brauche fünf Sekunden.")
    val a3 = futureThatTakes(11)("Ich brauche elf Sekunden.")

    val fs : List[Future[String]] = List(a1,a2,a3)

    fs map printFuture
  }

  def awaitExample = {
    Await.result(futureThatTakes(10)("Ich brauche zehn Sekunden."), 5 seconds)
    Await.result(futureThatTakes(10)("Ich brauche zehn Sekunden."), 10 seconds)
    Await.result(futureSeq, 12 seconds)
  }

  def futureSeq = Future.sequence(
    List(
      futureThatTakes(10)("Ich brauche zehn Sekunden."),
      futureThatTakes(5)("Ich brauche fünf Sekunden."),
      futureThatTakes(12)("Ich brauche zwölf Sekunden.")
    )
  )

  /**
  * scala> Await.result(combined, 11 seconds)
  * warning: there were 1 feature warning(s); re-run with -feature for details
  * java.util.concurrent.TimeoutException: Futures timed out after [11 seconds]
  */
  def combineDoneWrong = {
  	val combined = for {
  		f1 <- futureThatTakes(10)("Ich brauche zehn Sekunden.")
      f2 <- futureThatTakes(5)("Ich brauche fünf Sekunden.")
  	} yield f1 + f2

  	Await.result(combined, 11 seconds)
  }



  // right

  def combineDoneRight = {
  	val fF1 = futureThatTakes(10)("Ich brauche zehn Sekunden.")
  	val fF2 = futureThatTakes(5)("Ich brauche fünf Sekunden.")

  	val combined = for {
  		f1 <- fF1
      f2 <- fF2
  	} yield f1 + f2

  	Await.result(combined, 11 seconds)
  }

  /**
  * scala> Await.result(combined, 11 seconds)
  * warning: there were 1 feature warning(s); re-run with -feature for details
  * res7: String = Ich brauche zehn Sekunden.Ich brauche fünf Sekunden.
  */





}




