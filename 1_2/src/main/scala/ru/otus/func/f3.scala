//package ru.otus.func
//
//object f3 {
//  val l1: String => Int = _.length // java: String::length
//
//  val l2 = (_: String).length
//
//  val l3 = "abc".contains _ // java: "abc"::contains
//
//  val l4: (Int, Int) => Int = _ + _
//
//  (1 to 10).reduce(_ + _) // use (1 to 10).sum
//
//  def curriedMethod(p1: String, p2: Double)(p3: Int)(p4: Long) = ???
//  val f5 = curriedMethod _
//  f5 : (String, Double) => (Int => (Long => Nothing))
//  // a => b => c === a => (b => c)
//
//  val f6 = curriedMethod("str", 0.1) _
//  f6: Int => (Long => Nothing)
//
//  val functionWith4Params: (String, Double, Int, Long) => Float = ???
//  val curriedFunction = functionWith4Params.curried
//  curriedFunction: String => Double => Int => Long => Float
//
//  case class Person(firstName: String, lastName: String, age: Int)
//
//  val doeFamilyMember: (String, Int) => Person = ???
//}
