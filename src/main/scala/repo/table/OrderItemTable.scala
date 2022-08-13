package repo.table

import models.OrderItem
import repo.DatabaseProfile
import slick.lifted.ProvenShape

object OrderItemTable {

  import DatabaseProfile.profile.api._

  private[repo] final class OrderItems(tag: Tag) extends Table[OrderItem](tag, "order_items") {
    def orderId:  Rep[Long] = column("order_id")
    def itemId:   Rep[Long] = column("item_id")

    override def * : ProvenShape[OrderItem] = (
      orderId, itemId
    ) <> ((OrderItem.apply _).tupled, OrderItem.unapply)

  }

  val OrderItems: TableQuery[OrderItems] = TableQuery[OrderItems]

}
