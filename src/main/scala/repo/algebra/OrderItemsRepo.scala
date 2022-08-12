package repo.algebra

import models._

import java.time.LocalDateTime
import scala.concurrent.Future

trait OrderItemsRepo {

  def getItemIds(orderId: Long): Future[Seq[Long]]

  def getOrdersItems(orderIds: Seq[Long]): Future[Seq[OrderWithItemIds]]

  def getOrdersItemsAndProductsWithinDateRange(start: LocalDateTime, end: LocalDateTime): Future[Seq[(Order, Item, DomainProduct)]]

}
