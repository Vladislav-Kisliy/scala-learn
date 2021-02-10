package com.company.sc.greet.dao.impl

import com.company.sc.greet.dao.DictionaryDao

class DictionaryDaoImpl extends DictionaryDao {

  private[this] val prefixDict =
    Map(
      0 -> "Hi!",
      1 -> "Good day!",
      2 -> "Hello!",
      3 -> "Highfive!",
      4 -> "Hey!",
      5 -> "Long time no see!",
      6 -> "Nice to see you!"
    )
  private[this] val postfixDict =
    Map(
      0 -> "how are you?",
      1 -> "what's new?",
      2 -> "glad to see you",
      3 -> "what's up?",
      4 -> "how are you doing?",
      5 -> "how's your day going?",
      6 -> "how're things with you?"
    )

  override def greetingPrefix(key: Int): String = {
    prefixDict.get(key).getOrElse("-")
  }

  override def greetingPostfix(key: Int): String = {
    postfixDict.get(key).getOrElse("-")
  }

  override def maxIndex(): Int = prefixDict.size
}
