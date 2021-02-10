package com.company.sc.greet.service

import com.company.sc.greet.model.{GreetRequest, GreetResponse}

trait GreetingService {
  def greet(request: GreetRequest): GreetResponse
}
