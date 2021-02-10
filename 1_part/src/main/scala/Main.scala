import com.company.sc.App
import com.company.sc.greet.model.{COUNTER, DICTIONARY, ECHO, GREETER, GreetRequest, LAZY}

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    val app = App()

    val greetRequest = GreetRequest("Vasya", GREETER)
    println(s"Request #1 ${app.greet(greetRequest)}")
    println(s"Request #2 ${app.greet(GreetRequest("Joe", GREETER))}")
    println(s"Request #3 ${app.greet(GreetRequest("Joe", GREETER, false))}")
    println("====")

    val counterRequest = GreetRequest("Petya", COUNTER)
    println(s"Request #1 ${app.greet(counterRequest)}")
    println(s"Request #2 ${app.greet(counterRequest)}")
    println(s"Request #3 ${app.greet(GreetRequest("Vasya", COUNTER))}")
    println(s"Request #4 ${app.greet(GreetRequest("Vasya", COUNTER, false))}")
    println("====")
    val echoRequest = GreetRequest("Petya", ECHO)
    println(s"Request #1 ${app.greet(echoRequest)}")

    val dictionaryRequest = GreetRequest("Petya", DICTIONARY)
    println(s"Request #1 ${app.greet(dictionaryRequest)}")
    println(s"Request #2 ${app.greet(dictionaryRequest)}")
    println(s"Request #3 ${app.greet(dictionaryRequest)}")
    println(s"Request #4 ${app.greet(dictionaryRequest)}")
    println(s"Request #5 ${app.greet(GreetRequest("Petya", DICTIONARY, false))}")

    val lazyRequest = GreetRequest("Petya", LAZY)
    println(s"Request #1 ${app.greet(lazyRequest)}")
    println(s"Request #2 ${app.greet(lazyRequest)}")
    println(s"Request #3 ${app.greet(GreetRequest("Petya", LAZY, false))}")
  }
}
