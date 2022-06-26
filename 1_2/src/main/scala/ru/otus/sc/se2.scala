package ru.otus.sc

import ru.otus.sc.se1.None

object se2 {

  sealed trait Option[+T]
  case object None                    extends Option[Nothing]
  final case class Some[+T](value: T) extends Option[T]

  def printIfAny[T](ot: Option[T]): Unit =
    ot match {
      case None        =>
      case Some(value) => println(value)
    }

  // TODO
  def extGetOrDefault[T](ot: Option[T], default: T): T =
    ot match {
      case Some(value) => value
      case None        => default
    }
}
