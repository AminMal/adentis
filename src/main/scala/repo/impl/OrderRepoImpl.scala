package repo.impl

import models.{DomainProduct, Item, Order}
import repo.algebra.OrderRepo
import repo.table.OrderTable

import java.time.LocalDateTime
import scala.concurrent.Future

class OrderRepoImpl extends OrderRepo {
  import repo.DatabaseProfile.profile.api._

  private final val Orders = OrderTable.Orders
  private final val db = bootstrap.db
  private final val OrderItems = repo.table.OrderItemTable.OrderItems
  private final val Items = repo.table.ItemTable.Items
  private final val Products = repo.table.ProductTable.Products

  override def getById(id: Long): Future[Option[Order]] = db.run {
    Orders.filter(_.id === id)
      .result
      .headOption
  }

  override def getOrdersInInterval(start: LocalDateTime, end: LocalDateTime): Future[Seq[Order]] = db.run {
    Orders.filter { order =>
      order.registeredDateTime > start && order.registeredDateTime < end
    }.result
  }

  override def getOrdersItemsAndProductsWithinDateRange(
                                                         start: LocalDateTime,
                                                         end: LocalDateTime
                                                       ): Future[Seq[(Order, Item, DomainProduct)]] = db.run {
    val relevantOrders = Orders filter (o => o.registeredDateTime > start && o.registeredDateTime < end)
    (OrderItems join relevantOrders on (_.orderId === _.id) join Items on (_._1.itemId === _.id) join Products on (_._2.productId === _.id))
      .map {
        case (((_, order), item), product) =>
          (order, item, product)
      }.result
  }

}
