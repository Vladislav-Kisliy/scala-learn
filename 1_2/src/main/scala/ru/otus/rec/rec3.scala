package ru.otus.rec

import scala.annotation.tailrec

object rec3 {

  sealed trait List[+T] {
    def ::[H >: T](h: H): List[H] = Cons(h, this)
  }
  case object Nil                                 extends List[Nothing]
  final case class ::[+T](head: T, tail: List[T]) extends List[T]
  val Cons = ::

  @tailrec
  def reverse[T](l: List[T], acc: List[T] = Nil): List[T] =
    l match {
      case Nil            => acc
      case ::(head, tail) => reverse(tail, head :: acc)
    }
}
