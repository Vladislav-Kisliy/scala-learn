package ru.otus.func

import scala.annotation.tailrec

object f2 {
  type My1Function[-T1, +R] = Function1[T1, R]
  type My2Function[-T1, +R] = T1 => R
  def mapList[T, R](l: List[T])(f: T => R): List[R] = ???

  def l1: String => Int          = ??? // Syntax for type
  def l2: Function1[String, Int] = ??? // same as above
//  l2: String => Int

  val l3 = (s: String) => s.length
//  l3: String => Int

  val l5: String => Int = (s) => s.length //  omit type
  val l6: String => Int = s => s.length   // omit () for a single parameter without type

  trait Java8StyleFunctionalInterface {
    def method(s: String): Int
  }

  val l7: Java8StyleFunctionalInterface = s => s.length

}
