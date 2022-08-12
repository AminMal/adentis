package models

case class OrderWithItemIds(
                           oderId: Long,
                           itemIds: Seq[Long]
                           )