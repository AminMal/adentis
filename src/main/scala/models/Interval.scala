package models

case class Interval private(title: String, order: Int, atLeast: Int, atMostInclusiveOpt: Option[Int]) {

  def accepts(monthsDifference: Long): Boolean = atMostInclusiveOpt.map { atMostInclusive =>
    atMostInclusive >= monthsDifference && atLeast <= monthsDifference
  }.getOrElse(atLeast <= monthsDifference)

}

object Interval {
  private final val finiteInterval = "^([0-9]{1,2})-([1-9][0-9]?)$".r
  private final val infiniteInterval = "^>([1-9][0-9]?)".r

  def apply(expr: String, order: Int): Interval = {
    expr match {
      case finiteInterval(atLeast, atMostExclusive) if atLeast < atMostExclusive =>
        apply(
          title = expr concat " months",
          order = order,
          atLeast = atLeast.toInt,
          atMostInclusiveOpt = Some(atMostExclusive.toInt)
        )
      case infiniteInterval(atLeast) =>
        apply(
          title = expr concat " months",
          order = order,
          atLeast = atLeast.toInt,
          atMostInclusiveOpt = None
        )
      case other =>
        throw new IllegalArgumentException(s"could not resolve $other as a valid interval")
    }
  }
}
