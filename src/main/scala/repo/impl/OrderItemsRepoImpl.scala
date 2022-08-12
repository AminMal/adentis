package repo.impl

import models._
import repo.algebra.OrderItemsRepo
import repo.table.{ItemTable, OrderItemTable, OrderTable, ProductTable}

import java.time.LocalDateTime
import scala.concurrent.Future

@Singleton
class OrderItemsRepoImpl extends OrderItemsRepo {

  import repo.DatabaseProfile.profile.api._
  private final val OrderItems = OrderItemTable.OrderItems
  private final val db = bootstrap.Bootstrap.db
  import bootstrap.Bootstrap.ec
  private final val Items = ItemTable.Items
  private final val Products = ProductTable.Products
  private final val Orders = OrderTable.Orders

  override def getItemIds(orderId: Long): Future[Seq[Long]] = db.run {
    OrderItems.filter(_.orderId === orderId)
      .map(_.itemId)
      .result
  }

  override def getOrdersItems(orderIds: Seq[Long]): Future[List[OrderWithItemIds]] = db.run {
    OrderItems.filter(_.orderId inSet orderIds)
      .result
  }.map { ordersItems =>
    ordersItems.groupMap(_.orderId)(_.itemId).foldLeft(List.empty[OrderWithItemIds]) {
      case (aggregator, newMapEntry) =>
        OrderWithItemIds(newMapEntry._1, newMapEntry._2) :: aggregator
    }
  }

  def getOrdersItemsAndProductsWithinDateRange(
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
