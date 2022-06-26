package ru.otus.bn

object bn1 {
  sealed trait Option[+T]
  case object None                    extends Option[Nothing]
  final case class Some[+T](value: T) extends Option[T]

  def extGetOrDefault[T](ot: Option[T])(default: T): T =
    ot match {
      case None        => default
      case Some(value) => value
    }

  val v = extGetOrDefault(Some(1)) {
    println("abc")
    2
  }

}
