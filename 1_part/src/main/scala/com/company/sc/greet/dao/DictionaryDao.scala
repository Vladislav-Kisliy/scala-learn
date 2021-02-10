package com.company.sc.greet.dao

trait DictionaryDao {

  def greetingPrefix(key: Int): String
  def greetingPostfix(key: Int): String
  def maxIndex(): Int
}
