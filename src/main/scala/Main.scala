import models.{Arguments, Interval}
import process.OrderProcess
import repo.impl.OrderRepoImpl

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.Await
import scala.util.{Failure, Success}

object Main {

  lazy val defaultIntervals = Seq(
    Interval("0-3", 1),
    Interval("4-7", 2),
    Interval("8-11", 3),
    Interval(">12", 4),
  )

  def main(args: Array[String]): Unit = {
    import bootstrap.ec
    val taskTimeoutValue = bootstrap.config.getInt("taskTimeoutValue")
    val taskTimeoutUnit = TimeUnit.valueOf(bootstrap.config.getString("taskTimeoutUnit"))

    val orderProcess = new OrderProcess(new OrderRepoImpl())

    Parser.parseArguments(args)(defaultIntervals) match {
      case Failure(exception) =>
        exception.printStackTrace()
        sys.exit(1)
      case Success(Arguments(start, end, intervals)) =>
        val result = Await.result(
          orderProcess.getOrdersCountGroupedByProductAgeInInterval(start, end, intervals),
          FiniteDuration(taskTimeoutValue, taskTimeoutUnit)
        )
        result.foreach {
          case (intervalTitle, count) =>
            println(s"$intervalTitle: $count orders")
        }
        sys.exit(0)
    }
  }

}
