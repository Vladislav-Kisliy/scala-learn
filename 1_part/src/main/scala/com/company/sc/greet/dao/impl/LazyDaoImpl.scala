package com.company.sc.greet.dao.impl

import com.company.sc.greet.dao.GreetingDao

class LazyDaoImpl extends GreetingDao {
  lazy val greetingPrefix: String = {
    println("I need to think")
    Thread.sleep(500)
    "Oh, hi!"
  }

  lazy val greetingPostfix: String = {
    println("I need to think again")
    Thread.sleep(500)
    "!!!"
  }
}
