package com.company.sc.greet.service.impl

import com.company.sc.greet.dao.GreetingDao
import com.company.sc.greet.model.{GreetRequest, GreetResponse}
import com.company.sc.greet.service.GreetingService

class GreetingServiceImpl(dao: GreetingDao) extends GreetingService {
  def greet(request: GreetRequest): GreetResponse =
    if (request.isHuman)
      GreetResponse(s"${dao.greetingPrefix} ${request.name} ${dao.greetingPostfix}")
    else GreetResponse("AAAAAAAAAA!!!!!!")
}
