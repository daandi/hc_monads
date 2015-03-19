// based on http://aperiodic.net/phil/scala/s-99/p31.scala

object NaivePrime {

  def isPrime(i: Int): Boolean =
      (i > 1) && ( primes takeWhile { _ <= Math.sqrt(i) } forall ( i % _ != 0 ) )

  val primes = Stream.cons(2, Stream.from(3, 2) filter isPrime )

}

