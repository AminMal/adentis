import java.time.LocalDateTime
import scala.util.{Failure, Try}
import models.{Interval, Arguments}

object Parser {

  def parseRawDateTime(rawDateTime: String): Try[LocalDateTime] = {
    val normalizedStrDateTime = {
      if (rawDateTime.startsWith("\"")) rawDateTime.drop(1).dropRight(1) else rawDateTime
    }.replace(' ', 'T')
    Try(LocalDateTime.parse(normalizedStrDateTime))
  }

  def parseArguments(rawArgs: Array[String])(defaultIntervals: => Seq[Interval]): Try[Arguments] = {
    rawArgs.toList match {
      case List(rawStart, rawEnd) =>
        Parser.parseRawDateTime(rawStart).flatMap { start =>
          Parser.parseRawDateTime(rawEnd).map { end =>
            models.Arguments(start, end, defaultIntervals)
          }
        }
      case rawStart :: rawEnd :: rawIntervals =>
        Parser.parseRawDateTime(rawStart).flatMap { start =>
          Parser.parseRawDateTime(rawEnd).flatMap { end =>
            Try(rawIntervals.zipWithIndex.map { case (expr, order) => Interval.apply(expr, order) })
              .map { intervals =>
                Arguments(start, end, intervals)
              }
          }
        }
      case other =>
        Failure(new IllegalArgumentException(s"could not parse ${other.mkString(" ")} as valid application arguments"))
          .asInstanceOf[Try[Arguments]]
    }
  }

}
