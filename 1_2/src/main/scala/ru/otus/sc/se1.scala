package ru.otus.sc

object se1 {
  trait TraitA
  trait TraitB
  class ClassA
  class ClassB

  class MyClass extends TraitA with TraitB {}
  //  class MyClass extends ClassA with ClassB
  object MyObject extends ClassA {}

  // Option
  sealed trait Option[+T]
  final case class Some[+T](value: T) extends Option[T]
  case object None                    extends Option[Nothing]

//  val a: Option[String]
// printIfAny
  def printIfAny[T](op: Option[T]): Unit =
    op match {
      case Some(value) => println(value)
      case None        =>
    }

}
