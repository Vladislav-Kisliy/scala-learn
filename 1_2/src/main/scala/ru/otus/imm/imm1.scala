package ru.otus.imm

object imm1 {
  case class Person(firstName: String, lastName: String, age: Int)

  val person = Person("John", "Doe", 33)

  // person.age += 1
  val olderPerson = person.copy(age = person.age + 1)
}
