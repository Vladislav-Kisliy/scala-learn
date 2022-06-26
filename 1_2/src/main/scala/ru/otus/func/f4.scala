package ru.otus.func

import java.io.FileOutputStream

import scala.util.Using

object f4 {
  val l1 = (s: String) => {
    println(s)
    s.length
  }
  val l2 = { s: String => // omit () for a single parameter
    println(s)
    s.length
  }

  def using[T <: AutoCloseable, R](res: T)(f: T => R): R =
    try f(res)
    finally if (res != null) res.close()

  using(new FileOutputStream("name"))(_.write(1))
  using(new FileOutputStream("name")) { os =>
    os.write(1)
    os.write(2)
  }

  Using.resource(new FileOutputStream("name")) { os =>
    os.write(1)
    os.write(2)
  }

}
