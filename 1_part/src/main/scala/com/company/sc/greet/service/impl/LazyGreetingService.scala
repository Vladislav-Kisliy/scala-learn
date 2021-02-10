package com.company.sc.greet.service.impl

import com.company.sc.greet.dao.GreetingDao
import com.company.sc.greet.model.{GreetRequest, GreetResponse}
import com.company.sc.greet.service.CommonGreetingService

class LazyGreetingService(dao: GreetingDao) extends CommonGreetingService {
  override def serviceResponse(request: GreetRequest): GreetResponse = {
    GreetResponse(s"${dao.greetingPrefix} ${request.name} ${dao.greetingPostfix}")
  }
}
