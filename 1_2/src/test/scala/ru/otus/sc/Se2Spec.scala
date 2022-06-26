package ru.otus.sc

import org.scalatest.freespec.AnyFreeSpec
import se2._
import org.scalatest.matchers.should.Matchers._

class Se2Spec extends AnyFreeSpec {
  "se2" - {
    "extGetOrDefault" - {
      "should return default value on None" in {
        extGetOrDefault(None, 666) should be(666)
      }

      "should return value on Some" in {
        extGetOrDefault(Some(13), 666) should be(13)
      }
    }
  }
}
