package com.company.sc.greet.model

sealed abstract class GreetRequestType(val requestType: String)

case object GREETER    extends GreetRequestType("greeter")
case object COUNTER    extends GreetRequestType("counter")
case object ECHO       extends GreetRequestType("echo")
case object DICTIONARY extends GreetRequestType("dictionary")
case object LAZY       extends GreetRequestType("lazy")
case object UNKNOWN    extends GreetRequestType("UNKNOWN")
