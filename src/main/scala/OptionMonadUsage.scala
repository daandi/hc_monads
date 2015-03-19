/**
 * Created by anneumann on 19/03/15.
 */
object OptionMonadUsage {

  // Like a list
  Some(1) map (i => i + 0.5)
  // res12: Option[Double] = Some(1.5)

  List(1) map (i => i + 0.5)
  // res13: List[Double] = List(1.5)


  // Like an empty List
  val x: Option[Int] = None
  // x: Option[Int] = None

  x map (i => i + 0.5)
  // res16: Option[Double] = None

  val y: List[Int] = List()
  // y: List[Int] = List()

  y map (i => i + 0.5)
  // res17: List[Double] = List()


}
