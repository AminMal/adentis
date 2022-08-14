package repo.impl

import models._
import repo.algebra.OrderItemsRepo
import repo.table.OrderItemTable
import scala.concurrent.Future

class OrderItemsRepoImpl extends OrderItemsRepo {

  import repo.DatabaseProfile.profile.api._
  import bootstrap.ec

  private final val OrderItems = OrderItemTable.OrderItems
  private final val db = bootstrap.db


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

  def upsert(orderItems: Seq[OrderItem]): Future[Int] = db.run {
    DBIO.sequence(orderItems.map(OrderItems.insertOrUpdate)).transactionally
  }.map(_.sum)

}