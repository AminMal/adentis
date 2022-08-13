package process

import models._
import repo.algebra.OrderItemsRepo

import java.time.{LocalDate, LocalDateTime}
import java.time.temporal.ChronoUnit
import scala.concurrent.{ExecutionContext, Future}

class OrderProcess(val orderItemsRepo: OrderItemsRepo) {

  def getInterval(date: LocalDate, start: LocalDate, intervals: Seq[Interval]): Option[Interval] = {
    val monthsBetween = ChronoUnit.MONTHS.between(date, start).abs
    intervals.find(_.accepts(monthsBetween))
  }

  def getOrdersCountGroupedByProductAgeInInterval(
                                                   start: LocalDateTime,
                                                   end: LocalDateTime,
                                                   intervals: Seq[Interval]
                                                 )(implicit ec: ExecutionContext): Future[Seq[(String, Int)]] = {
    orderItemsRepo.getOrdersItemsAndProductsWithinDateRange(start, end).map { orderItemAndProduct =>
      orderItemAndProduct.groupMapReduce {
        case (order, item, product) =>
          getInterval(product.creationDate, start.toLocalDate, intervals)
      }(_ => 1)(_ + _)
        .toSeq.sortBy {
        case (maybeInterval, _) =>
          maybeInterval.map(_.order).getOrElse(Int.MaxValue)
      }.map {
        case (maybeInterval, count) =>
          maybeInterval.map(_.title).getOrElse("other") -> count
      }
    }
  }

}
