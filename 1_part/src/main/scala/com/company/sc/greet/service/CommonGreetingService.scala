package com.company.sc.greet.service

import com.company.sc.greet.model.{GreetRequest, GreetResponse}

trait CommonGreetingService extends GreetingService {
  override def greet(request: GreetRequest): GreetResponse = {
    if (request.isHuman) {
      serviceResponse(request)
    } else defaultResponse
  }

  def serviceResponse(request: GreetRequest): GreetResponse

  def defaultResponse: GreetResponse = {
    GreetResponse("Robot go away")
  }

}
