package ru.otus.func

import scala.annotation.tailrec

object f5 {

  sealed trait List[+T] {
    def ::[H >: T](h: H): List[H] = Cons(h, this)
  }
  case object Nil                                 extends List[Nothing]
  final case class ::[+T](head: T, tail: List[T]) extends List[T]
  val Cons = ::

  @tailrec
  def reverse[T](l: List[T], acc: List[T] = Nil): List[T] =
    l match {
      case Nil          => acc
      case head :: tail => reverse(tail, head :: acc)
    }

  def mapList[T, R](l: List[T])(f: T => R): List[R] = {
    @tailrec
    def inner(l: List[T], acc: List[R] = Nil): List[R] =
      l match {
        case Nil          => acc
        case head :: tail => inner(tail, f(head) :: acc)
      }

    reverse(inner(l))
  }

  sealed trait Option[+T]
  case object None                    extends Option[Nothing]
  final case class Some[+T](value: T) extends Option[T]

  def incOption(o: Option[Int]): Option[Int] =
    o match {
      case None        => None
      case Some(value) => Some(value + 1)
    }

  // TODO: def mapOption
  def mapOption
}
