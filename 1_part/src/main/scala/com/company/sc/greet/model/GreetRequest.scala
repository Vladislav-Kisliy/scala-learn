package com.company.sc.greet.model

case class GreetRequest(name: String, requestType: GreetRequestType, isHuman: Boolean = true)
