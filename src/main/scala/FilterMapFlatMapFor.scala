/**
 * Created by anneumann on 19/03/15.
 */
object FilterMapFlatMapFor {

  object MapExample {
    List(1, 2, 3, 4) map (_ + 2)
    //  res7: List[Int] = List(3, 4, 5, 6)

    List(1, 2, 3, 4) map (x => x * x)
    //  res8: List[Int] = List(1, 4, 9, 16)

    List(1, 2, 3, 4) map (_.toDouble)
    //   res10: List[Double] = List(1.0, 2.0, 3.0, 4.0)
  }

  object FilterExample {

    import NaivePrime._

    (1 to 100) filter isPrime
    (1 to 100).filter(i => isPrime(i))
    // res2: scala.collection.immutable.IndexedSeq[Int] = Vector(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)

    (1 to 10) filter (_ % 3 == 0) //res3: scala.collection.immutable.IndexedSeq[Int] = Vector(3, 6, 9)  

    List("Januar", "Februar", "März", "April", "Mai") filter (month => month.reverse startsWith "r") //res4: List[String] = List(Januar, Februar)

  }

  object FlatMap {
    val words = "Gallia est omnis divisa in partes tres" split " "
    //words: Array[String] = Array(Gallia, est, omnis, divisa, in, partes, tres)  

    words.sliding(2, 1).toList
    //res2: List[Array[String]] = List(Array(Gallia, est), Array(est, omnis), Array(omnis, divisa), Array(divisa, in), Array(in, partes), Array(partes, tres))

    words.sliding(2, 1).toList map (bigram => bigram map (_.size)) //res3: List[Array[Int]] = List(Array(6, 3), Array(3, 5), Array(5, 6), Array(6, 2), Array(2, 6), Array(6, 4) )

    words.sliding(2, 1).toList flatMap (bigram => bigram map (_.size))
    //res4: List[Int] = List(6, 3, 3, 5, 5, 6, 6, 2, 2, 6, 6, 4)
  }

  object ForExample {
    import FlatMap._

    val f: String => Int = s => s.size
    //f: String => Int = <function1>

    words map f
    //  res5: Array[Int] = Array(6, 3, 5, 6, 2, 6, 4)

    for (word <- words) yield f(word)
    //  res6: Array[Int] = Array(6, 3, 5, 6, 2, 6, 4)


    words filter (_.size > 3) map f
    // res8: Array[Int] = Array(6, 5, 6, 6, 4)

    for (word <- words; if word.size > 3) yield f(word)
    //  res7: Array[Int] = Array(6, 5, 6, 6, 4)

    words.sliding(2, 1).toList flatMap (bigram => bigram map (word => f(word)))
    // res11: List[Int] = List(6, 3, 3, 5, 5, 6, 6, 2, 2, 6, 6, 4)

    for {
      bigram <- words.sliding(2, 1).toList
      word <- bigram
      if word.size > 3
    } yield f(word)
    // res10: List[Int] = List(6, 5, 5, 6, 6, 6, 6, 4)


  }


}
