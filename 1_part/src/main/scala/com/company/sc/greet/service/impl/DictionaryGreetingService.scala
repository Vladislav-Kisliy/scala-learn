package com.company.sc.greet.service.impl

import com.company.sc.greet.dao.DictionaryDao
import com.company.sc.greet.model.{GreetRequest, GreetResponse}
import com.company.sc.greet.service.CommonGreetingService

/**
  * Service uses inner storage for generating responses.
 *
  *  @param dao - storage with different responses
  */
class DictionaryGreetingService(dao: DictionaryDao) extends CommonGreetingService {

  private[this] val rnd = new scala.util.Random

  override def serviceResponse(request: GreetRequest): GreetResponse = {
    GreetResponse(
      s"${dao.greetingPrefix(getRandomIndex)} ${request.name}, ${dao.greetingPostfix(getRandomIndex)}"
    )
  }

  private[this] def getRandomIndex = 0 + rnd.nextInt((dao.maxIndex() - 0) + 1)
}
