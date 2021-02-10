package com.company.sc.greet.service.impl

import com.company.sc.greet.model.{GreetRequest, GreetResponse}
import com.company.sc.greet.service.CommonGreetingService

/**
  * Echo service. It repeats information from input response.
  */
class EchoGreetingService extends CommonGreetingService {
  override def serviceResponse(request: GreetRequest): GreetResponse = {
    GreetResponse(s"${request.name}")
  }
}
