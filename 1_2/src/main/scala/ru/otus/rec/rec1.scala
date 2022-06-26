package ru.otus.rec

import scala.annotation.tailrec

object rec1 {
  // 10!

  def factorial(i: Int): Long = ???

  def factorial1(i: Int): Long = {
    if (i <= 1) 1
    else i * factorial1(i - 1)
  }

  @tailrec
  def factorialT(i: Int, acc: Long): Long = {
    if (i <= 1) acc
    else factorialT(i - 1, acc * i)
  }
}
