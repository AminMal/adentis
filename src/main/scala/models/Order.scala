package models

import java.time.LocalDateTime

case class Order(
                id: Option[Long] = None,
                customerName: String,
                customerContact: String,
                shippingAddress: String,
                grandTotal: Double,
//                itemIds: Seq[Long],
                registeredDateTime: Option[LocalDateTime]
                )
