package ru.otus.func

import scala.annotation.tailrec

object f1 {
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

  val l = 1 :: 2 :: 3 :: Nil

  // def incList [1, 2, 3] => [2, 3, 4]
  // def shoutList [1, 2, 3] => ["1!", "2!", "3!"]
}
