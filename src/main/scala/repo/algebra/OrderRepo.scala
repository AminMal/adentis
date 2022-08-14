package repo.algebra

import models.{DomainProduct, Item, Order}

import java.time.LocalDateTime
import scala.concurrent.Future

trait OrderRepo {

  def getById(id: Long): Future[Option[Order]]

  def getOrdersInInterval(start: LocalDateTime, end: LocalDateTime): Future[Seq[Order]]

  def getOrdersItemsAndProductsWithinDateRange(start: LocalDateTime, end: LocalDateTime): Future[Seq[(Order, Item, DomainProduct)]]

}
