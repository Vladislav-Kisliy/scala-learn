package com.company.sc.greet.service.impl

import com.company.sc.greet.model.{GreetRequest, GreetResponse}
import com.company.sc.greet.service.CommonGreetingService

import java.util.concurrent.atomic.AtomicInteger

/**
  * This class generates responses with incrementing counter.
  */
class CountingGreetingService extends CommonGreetingService {
  private[this] var counter: AtomicInteger = new AtomicInteger(0)

  override def serviceResponse(request: GreetRequest): GreetResponse = {
    GreetResponse(s"${request.name}, your are number ${counter.incrementAndGet()}")
  }
}
