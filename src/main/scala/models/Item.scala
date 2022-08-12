package models

import java.time.LocalDateTime

case class Item(
                 id: Option[Long] = None,
                 taxAmount: Double,
                 shippingFee: Double,
                 productId: Long,
                 registeredDate: Option[LocalDateTime]
               )
